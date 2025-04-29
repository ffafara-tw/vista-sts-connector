package gov.va.mobile.acheron.v1.services.rpc.connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.mobile.acheron.v1.model.AcheronApiException.Metadata;
import gov.va.mobile.acheron.v1.model.CreateContextFailException;
import gov.va.mobile.acheron.v1.model.InvalidRpcResponseException;
import gov.va.mobile.acheron.v1.model.SignOnSetupFailException;
import gov.va.mobile.acheron.v1.model.SsoValidateFailException;
import gov.va.mobile.acheron.v1.services.rpc.RPCException;
import gov.va.mobile.acheron.v1.services.rpc.RpcEncryptedLiteralValue;
import gov.va.mobile.acheron.v1.services.rpc.RpcGlobalValue;
import gov.va.mobile.acheron.v1.services.rpc.RpcLiteralValue;
import gov.va.mobile.acheron.v1.services.rpc.SocketFactory;
import gov.va.mobile.acheron.v1.services.rpc.VistaQuery;
import gov.va.mobile.acheron.v1.services.rpc.VistaResponse;
import gov.va.mobile.acheron.v1.utils.rpc.RPCStringUtils;
import gov.va.mobile.acheron.v1.utils.rpc.RpcNameConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class around a TCP Socket connection to an external VistA instance. This class handles connecting and
 * disconnecting from the remote instance, as well as performing RPC queries for external interactions with a VistA
 * system.
 *
 * @author scott.thompson@apothesource.com
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class VistaConnection implements AutoCloseable {

    /**
     * End-Of-Transmission (EOT) character indicating the conclusion of a transmission message.
     */
    private static final char EOT = 0x04;
    public static final String ACHERON_SYSTEM = "Acheron System";


    /**
     * The hostname of the connected VistA instance.
     */
    private final String hostname;

    /**
     * The port of the connected VistA instance.
     */
    private final int port;

    /**
     * {@link SocketFactory} used for creating remote {@link Socket} connections to a VistA instance.
     *
     * @see SocketFactory
     */
    private final SocketFactory socketFactory;

    Instant connectionCreationTimestamp = Instant.now();

    private final String siteKey;

    private final boolean skipAutoSignOn;

    @Getter
    private boolean authenticated;


    /**
     * The remote {@link Socket} connection to a VistA instance.
     * <p>
     * This connection is lazily established via the {@link #connect()} method.
     *
     * @see Socket
     */
    private Socket socket;

    public VistaConnection(final VistaConnectionInfo connectionInfo, final SocketFactory socketFactory, final boolean skipAutoSignOn) {
        this.hostname = connectionInfo.getHost();
        this.siteKey = connectionInfo.getSite();
        this.port = connectionInfo.getPort();
        this.socketFactory = socketFactory;
        this.skipAutoSignOn = skipAutoSignOn;
    }

    /**
     * Connect to VistA through RPC broker {@link #hostname} and {@link #port}, establishing a {@link Socket} that is
     * kept alive for future VistA RPC queries. If a connection is already established, no action is performed.
     *
     * @throws RPCException when an error occurs making the connection
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void connect() throws RPCException {
        if (isConnected()) {
            log.trace("Already connected. Using existing Socket connection");
            return;
        }

        try {
            log.trace("Attempting to createForLegacyRpcRequest Socket");
            final InetAddress localInetAddress = socketFactory.getLocalHost();
            socket = socketFactory.create(localInetAddress, hostname, port);
            if (socket.isConnected()) {
                log.trace("Socket created. Attempting to establish TCP connection");
                final String connectString = VistaConnectionUtils.tcpConnectString(localInetAddress);
                write(connectString);

                final String data = read();
                if (!"accept".equals(data.trim())) {
                    socket.close();
                    throw new RPCException(String.format("TCP connection not accepted by %s:%s", hostname, port));
                }
                log.atTrace().log(String.format("Socket connected to %s:%s", hostname, port));
                connectionCreationTimestamp = Instant.now();
            } else {
                socket.close();
                socket = null; // Remove socket from connection before closing to ensure proper object de-reference

                throw new RPCException(String.format("Socket unable to connect to %s:%s", hostname, port));
            }
        } catch (final IOException e) {
            throw new RPCException(e);
        }
    }

    /**
     * Perform a "login" to the VistA system for this connection. The following steps are performed:
     * <ul>
     *     <li>Initialize user sign-on process</li>
     *     <li>Validate SSOi token and authenticate</li>
     *     <li>Establish RPC context</li>
     * </ul>
     *
     * @param ssoiToken the system saml token for vista
     * @param context   the RPC context
     */
    public void login(final String ssoiToken, final String context) {
        signOnSetup();
        ssoValidate(ssoiToken);
        createContext(context);
        authenticated = true;
    }

    /**
     * Verify if {@link #socket} is connected to a VistA instance.
     *
     * @return {@code true} if the {@link #socket} is connected; {@code false} otherwise
     */
    boolean isConnected() {
        return (socket != null) && socket.isConnected() && socket.isBound() && !socket.isClosed();
    }

    /**
     * Disconnect from the remote VistA instance, closing the {@link #socket} in the process. If the connection is
     * already closed, no action is performed.
     */
    @Override
    @SuppressWarnings("try")
    public void close() {
        if (isConnected()) {

            try {
                log.trace("Attempting to disconnect from Socket");
                final String disconnectString = VistaConnectionUtils.tcpDisconnectString();
                query(disconnectString);

                log.atTrace().log(String.format("Socket disconnected from %s:%s", hostname, port));

            } catch (final RPCException e) {
                log.warn("Error disconnecting Socket connection");
            } finally {
                socket = null; // Remove socket from connection before closing to ensure proper object de-reference
            }
        } else {
            log.trace("Socket already closed");
        }
    }

    /**
     * Execute an RPC request to the external VistA instance.
     *
     * @param query contains the RPC data to execute
     * @return the RPC response
     * @throws RPCException when an error occurs executing the RPC
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public VistaResponse query(final VistaQuery query) throws RPCException {
        final String rpcName = query.getRpcName();

        log.atTrace().log(String.format("Performing query for RPC: '%s'", rpcName));
        final String result = query(query.buildMessage());
        if (rpcName.startsWith("SDES")) {
            final String responseJson = RPCStringUtils.assembleResponse(result);
            List<String> errorsList = getErrors(responseJson, rpcName);
            if (!errorsList.isEmpty()) {
                log.atWarn().log(() -> "Error from Vista: Site: %s, RPC: %s JSON Errors: %s".formatted(siteKey, rpcName, String.join(", ", errorsList)));
            }
        }
        log.atTrace().log(String.format("Successful query response for RPC: '%s'", rpcName));
        return new VistaResponse(result);
    }

    /**
     * Performs an RPC query to the connected VistA instance. If the response is determined to be an error response
     * according to {@link VistaConnectionUtils#isErrorResponse(String)}, an {@link RPCException} is thrown.
     *
     * @param request the RPC query to execute
     * @return the response from VistA if the response is a non-error response
     * @throws RPCException if the response from VistA is an error response or if there is a failure to query the VistA instance
     */
    private String query(final String request) throws RPCException {
        try {
            write(request);
            final String data = read();

            final String response = VistaConnectionUtils.processData(data);
            if (VistaConnectionUtils.isErrorResponse(response)) {
                throw new RPCException(response);
            }
            return response;
        } catch (final IOException e) {
            throw new RPCException(e);
        }
    }

    /**
     * Write the provided {@link String} data to the external VistA instance.
     *
     * @param data the data to send to the VistA system
     * @throws IOException if an error occurs writing the data
     */
    @SuppressWarnings("PMD.CloseResource")
    private void write(final String data) throws IOException {
        final byte[] bytes = data.getBytes(RPCStringUtils.CP_1252);
        final OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
    }

    /**
     * Reads data from the external VistA instance, reading the data from the {@link #socket} until either no data is
     * read or the {@link #EOT} is reached.
     *
     * @return the fully read response {@link String} from the VistA system
     * @throws IOException if an error occurs reading the data
     */
    @SuppressWarnings("PMD.CloseResource")
    private String read() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        final byte[] byteBuffer = new byte[256];
        int readIndex;
        int endIndex = -1;
        do {
            readIndex = socket.getInputStream().read(byteBuffer);
            if (readIndex > -1) {
                String data = RPCStringUtils.toString(byteBuffer, readIndex);
                endIndex = data.indexOf(EOT);
                if (endIndex > -1) {
                    data = RPCStringUtils.substring(data, 0, endIndex);
                }
                stringBuilder.append(data);
            }
        } while ((readIndex != -1) && (endIndex == -1));

        return stringBuilder.toString();
    }

    /**
     * Validates and Authenticates and user with the provided SSOi Token using the {@literal XUS ESSO VALIDATE} RPC. The
     * response must include a non-0 DUZ and no error message to signify a successful authentication.
     *
     * @param ssoiToken the user SSOi Token
     */
    public void ssoValidate(final String ssoiToken) {
        final List<String> tokenizedTokenList = RPCStringUtils.tokenize(ssoiToken);
        RpcGlobalValue ssoiTokenGlobal = new RpcGlobalValue();
        ssoiTokenGlobal.addAllLiterals(tokenizedTokenList);

        final VistaQuery vistaQuery = new VistaQuery(RpcNameConstants.XUS_ESSO_VALIDATE, ACHERON_SYSTEM);
        vistaQuery.addGlobal(ssoiTokenGlobal);

        final VistaResponse response;
        try {
            response = query(vistaQuery);
        } catch (final RPCException e) {
            throw new SsoValidateFailException(Metadata.builder().detail("Error executing validation RPC").cause(e).source(siteKey).build());
        }

        final String userDuz = VistaConnectionUtils.parseDuz(response);
        if (userDuz == null) {
            final String detail = String.format("error=%s", "Provider DUZ cannot be an empty string");
            throw new SsoValidateFailException(Metadata.builder().detail(detail).source(siteKey).build());
        }

        final String msg = VistaConnectionUtils.parseMessage(response);
        if ("0".equals(userDuz) && (msg != null)) {
            final String detail = String.format("error=%s", msg);
            throw new SsoValidateFailException(Metadata.builder().detail(detail).source(siteKey).build());
        }
        log.debug("Established connection for user with duz: {}", userDuz);
    }

    /**
     * Performs the VistA sign-on setup operation to prepare for SSO validation using the {@literal XUS SIGNON SETUP}
     * RPC.
     */
    public void signOnSetup() {
        try {
            final VistaQuery vistaQuery = new VistaQuery(RpcNameConstants.XUS_SIGNON_SETUP, ACHERON_SYSTEM);
            // Optional - Broker Security Enhancement (BSE) token
            vistaQuery.addLiteral(new RpcLiteralValue(StringUtils.EMPTY));
            // Optional - Set ASOSKIP=1 to skip the Auto Sign-On check
            vistaQuery.addLiteral(new RpcLiteralValue(BooleanUtils.toIntegerObject(skipAutoSignOn).toString()));
            query(vistaQuery);
            log.debug("Initiated sign-on setup");
        } catch (final RPCException e) {
            throw new SignOnSetupFailException(Metadata.builder().detail("Sign On Setup Failure").cause(e).source(siteKey).build());
        }
    }

    /**
     * Establishes the XWB Context for future RPC calls using the {@literal XWB CREATE CONTEXT} RPC. The response must
     * equal {@code "1"}, signifying a successful RPC response.
     *
     * @param rpcContext the RPC Context to establish
     */
    public void createContext(final String rpcContext) {
        final VistaQuery query = new VistaQuery(RpcNameConstants.XWB_CREATE_CONTEXT, ACHERON_SYSTEM);
        query.addEncryptedParameter(new RpcEncryptedLiteralValue(rpcContext));

        final VistaResponse response;
        try {
            response = query(query);
        } catch (final RPCException e) {
            final String detail = String.format("rpcContext=%s, error=%s", rpcContext, e.getMessage());
            throw new CreateContextFailException(Metadata.builder().detail(detail).cause(e).source(siteKey).build());
        }

        if (!"1".equals(response.getRawResponse())) {
            final String detail = String.format("rpcContext=%s", rpcContext);
            throw new CreateContextFailException(Metadata.builder().detail(detail).source(siteKey).build());
        }
        log.atDebug().log(String.format("Established RPC context: %s", rpcContext));
    }

    public List<String> getErrors(final String responseJson, final String rpcName) {
        List<String> errors = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
            JsonNode rootNode = mapper.readTree(responseJson);
            if (rootNode.has("Error")) {
                JsonNode errorsNode = rootNode.get("Error");
                if (errorsNode.isArray()) {
                    for (JsonNode errorNode : errorsNode) {
                        errors.add(errorNode.asText());
                    }
                }
            }
        } catch (JsonProcessingException e) {
            log.atDebug().log(String.format("Error parsing RPC JSON response: rpcName=%s, error=%s", rpcName, e.getMessage()));
            log.atDebug().log(String.format("invalidJson=%s", responseJson));
            throw new InvalidRpcResponseException(Metadata.builder().detail(String.format("rpcName=%s", rpcName)).cause(e).build());
        }
        return errors;
    }
}