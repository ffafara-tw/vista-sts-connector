package gov.va.mobile.acheron.v1.services.rpc.connection;

import gov.va.mobile.acheron.v1.services.rpc.VistaResponse;
import gov.va.mobile.acheron.v1.utils.rpc.RPCStringUtils;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;

@UtilityClass
public class VistaConnectionUtils {

    public static final String M_ERROR = "M  ERROR";
    public static final String SECURITY_ERROR = "Security Error";
    public static final String APPLICATION_ERROR = "Application Error";


    /**
     * Parse the first piece of the SSO response, expected to contain the user DUZ.
     *
     * @param vistaResponse
     *         the {@link VistaResponse} to piece apart
     *
     * @return the user DUZ, or {@code null} if not found
     *
     * @see VistaResponse
     * @see VistaResponse#getPiece(String, int)
     */
    public static String parseDuz(final VistaResponse vistaResponse) {
        return StringUtils.trimToNull(vistaResponse.getPiece("\r\n", 1));
    }

    /**
     * Parse the fourth piece of the SSO response, expected to contain a possible error message.
     *
     * @param vistaResponse
     *         the {@link VistaResponse} to piece apart
     *
     * @return the message, or {@code null} if not found
     *
     * @see VistaResponse
     * @see VistaResponse#getPiece(String, int)
     */
    public static String parseMessage(final VistaResponse vistaResponse) {
        return StringUtils.trimToNull(vistaResponse.getPiece("\r\n", 4));
    }
    
    /**
     * Creates the {@link String} used to connect to a remote VistA instance over TCP.
     *
     * @param localInetAddress
     *         the {@link InetAddress} of the machine the Acheron application is running
     *
     * @return the {@link String} used to connect to a remote VistA instance over TCP
     */
    public static String tcpConnectString(final InetAddress localInetAddress) {
        return "[XWB]10304\nTCPConnect50"
                + RPCStringUtils.rpcPad(localInetAddress.getHostAddress())
                + "f0"
                + RPCStringUtils.rpcPad("0")
                + "f0"
                + RPCStringUtils.rpcPad(localInetAddress.getCanonicalHostName())
                + 'f'
                + '\u0004';
    }

    /**
     * Creates the {@link String} used to disconnect from a remote VistA instance over TCP.
     *
     * @return the {@link String} used to disconnect from a remote VistA instance over TCP
     */
    @SuppressWarnings("java:S3400") // Suppress SonarQube warning
    public static String tcpDisconnectString() {
        return "[XWB]10304\u0005#BYE#\u0004";
    }



    /**
     * Processes the response data {@link String} from the VistA query. The responses from VistA vary based on success
     * or failure.
     * <p>
     * Successful responses start with {@code "\u0000\u0000"} control characters followed by the actual response.
     * <p>
     * Application errors start with only {@code "\u0000"} and another possible control character, followed by the error
     * response.
     * <p>
     * Security errors start with a character whose value must be within the length of the response {@link String}.
     * <p>
     * The other cases, such as "empty", "invalid length", or an invalid character for the security response normally
     * would never happen, so the default behavior is to return the response unmodified to avoid any application
     * errors.
     *
     * @param data
     *         the VistA query response {@link String}
     *
     * @return the processed VistA query response, removing starting control characters and/or prepending error message
     *         identifiers
     */
    public static String processData(final String data) {
        if (StringUtils.isEmpty(data) || (StringUtils.length(data) < 2)) {
            return data;
        }
        final char firstCharacter = data.charAt(0);
        if (firstCharacter > data.length()) {
            return data;
        }

        if (firstCharacter == 0) {
            if (data.charAt(1) == 0) {
                return data.substring(2);
            }
            return APPLICATION_ERROR + ": " + data.substring(2);
        }
        final String msg = RPCStringUtils.substring(data, 1, firstCharacter);
        return SECURITY_ERROR + ": " + msg;
    }

    /**
     * Determines if the provided {@code message} is an "error" response from the VistA system. An error response either
     * starts with {@link #SECURITY_ERROR} or {@link #APPLICATION_ERROR}, or contains {@link #M_ERROR} in the message.
     *
     * @param message
     *         the response from the VistA system to check for error
     *
     * @return {@code true} if the response is an "error"; {@code false} otherwise
     */
    public static boolean isErrorResponse(final String message) {
        return StringUtils.startsWithAny(message, SECURITY_ERROR, APPLICATION_ERROR)
                || StringUtils.contains(message, M_ERROR);
    }
}
