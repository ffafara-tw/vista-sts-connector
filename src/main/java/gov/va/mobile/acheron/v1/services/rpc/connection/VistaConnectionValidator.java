package gov.va.mobile.acheron.v1.services.rpc.connection;

import gov.va.mobile.acheron.v1.AppProperties;
import gov.va.mobile.acheron.v1.services.rpc.RPCException;
import gov.va.mobile.acheron.v1.services.rpc.SocketFactory;
import gov.va.mobile.acheron.v1.services.rpc.VistaQuery;
import gov.va.mobile.acheron.v1.services.rpc.VistaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

/**
 * Validator used to check connections can be established to a VistA system and verify if active {@link VistaConnection}
 * instances can execute RPC calls.
 *
 * @author scott.thompson@apothesource.com
 * @see VistaConnection
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class VistaConnectionValidator {

    /**
     * The Factory used when creating remote socket connections to the external VistA system.
     *
     * @see SocketFactory
     */
    private final SocketFactory socketFactory;

    /**
     * The application properties containing configuration for the validation RPC name.
     *
     * @see AppProperties
     */
    private final AppProperties properties;


    /**
     * Verify a VistA connection can be established with an external VistA system. The created {@link VistaConnection}
     * is temporary for the life of this method, and closed after verification is performed.
     * <p>
     * Once a connection is established, a query using the "PING" RPC is executed to check and cache the latest SDES patch
     * number for the respective VistA site.
     *
     * @param connectionInfo the {@link VistaConnectionInfo} with connection information used to establish a temporary
     *                       {@link VistaConnection}
     * @return {@code true} if the connection is valid; {@code false} if the connection is invalid or undetermined
     * @see VistaConnectionInfo
     * @see VistaConnection
     */
    public boolean isValid(final VistaConnectionInfo connectionInfo) throws RPCException {
        try (VistaConnection connection = new VistaConnection(
                connectionInfo.getHost(), connectionInfo.getPort(),
                socketFactory, connectionInfo.getSite(), properties.isSkipAutoSignOn()
        )) {
            connection.connect();
            return isValid(connection);
        }
    }

    /**
     * Verify the VistA connection is a "valid" connection, executing a query using the "PING" RPC to check that VistA
     * RPC queries can be performed appropriately. If the PING response is {@code "1"}, the connection is determined to
     * be valid. If the PING response is anything other than {@code "1"}, the connection is determined to be invalid.
     *
     * @param connection the VistA connection to test
     * @return {@code true} if the connection is valid; {@code false} if the connection is invalid or undetermined
     * @see VistaConnection
     */
    public boolean isValid(final VistaConnection connection) {
        if (!connection.isConnected()) {
            log.trace("Connection is closed and invalid");
            return false;
        }

        final String pingRpc = properties.getVistaPingRpc();
        final VistaQuery vistaQuery = new VistaQuery(pingRpc, VistaConnection.ACHERON_SYSTEM);
        try {
            log.atTrace().log(String.format("Checking connection for validity using RPC: '%s'", pingRpc));
            // Note: PING RPC does not require authentication
            final VistaResponse vistaResponse = connection.query(vistaQuery);
            final String result = vistaResponse.getRawResponse();
            log.atTrace().log(String.format("Ping RPC response: %s", result));

            if (!"1".equals(result)) {
                log.debug("Invalid connection; Closing TCP Socket");
                return false;
            }

            log.trace("Connection is valid");
            return true;
        } catch (final RPCException e) {
            log.atWarn().setCause(e).log(String.format("Failed to validate connection using RPC: '%s'", pingRpc));
            return false;
        }
    }


    /**
     * Verify the VistA connection is a "valid" connection, executing a query using the "PING" RPC to check that VistA
     * RPC queries can be performed appropriately. If the PING response is {@code "1"}, the connection is determined to
     * be valid. If the PING response is anything other than {@code "1"}, the connection is determined to be invalid.
     *
     * @param connection the VistA connection to test
     * @return {@code true} if the connection is valid; {@code false} if the connection is invalid or undetermined
     * @see VistaConnection
     */
    public boolean isValid(final VistaConnection connection, final VistaConnectionInfo connectionInfo) {

        return connection.isConnected() && validateTimeout(connection, connectionInfo);

    }

    boolean validateTimeout(final VistaConnection connection, final VistaConnectionInfo connectionInfo) {

        Duration timeoutDuration = connectionInfo.getTimeoutDuration();
        Instant creationTimestamp = connection.connectionCreationTimestamp;

        if (timeoutDuration != null && timeoutDuration.isPositive() && creationTimestamp != null) {
            Duration elapsedTime = Duration.between(Instant.now(), creationTimestamp);

            if (elapsedTime.compareTo(timeoutDuration) >= 0) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Connection has timed out at site %s with elapsed time of %s",
                            connectionInfo.getSite(), elapsedTime));
                }
                return false;
            }
        }
        return true;
    }
}