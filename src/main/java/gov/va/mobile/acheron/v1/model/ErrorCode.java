package gov.va.mobile.acheron.v1.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCode {
    VISTA_RPC_FAILED(1000, 500, "Could not execute Vista RPC"),
    VISTA_SITE_NOT_FOUND(1001, 400, "Vista site not found"),
    VISTA_CONNECTION_FAIL(1002, 500, "Vista connection failed"),


    UNSUPPORTED_RPC(1005, 400, "Unsupported RPC"),
    INVALID_RPC_PARAMETERS(1006, 400, "Invalid RPC parameters"),
    INVALID_RPC_RESPONSE(1007, 500, "RPC response contains invalid JSON"),



    SITE_NOT_FOUND(1011, 400, "VistA site not found"),
    VISTA_CONNECTION_POOL_FAIL(1012, 500, "Failed to retrieve VistA connection from pool"),

    SIGN_ON_SETUP_FAIL(1013, 500, "Failed to initiate sign-on setup"),
    SSO_VALIDATE_FAIL(1014, 500, "Failed to validate SSOi token"),
    CREATE_CONTEXT_FAIL(1015, 500, "Failed to establish XWB context"),


    UNKNOWN_PARAMETER_TYPE(1018, 400, "unknown parameter type"),


    ENDPOINT_DEPRECATED(1021, 410, "Endpoint is deprecated"),
    VISTA_SITE_VERSION_CONFLICT(1022, 409, "Target VistA site has an SDES patch number lower than requireSdesPatch"),

    UNKNOWN(9999, 500, "Unknown Acheron error");

    private final int value;
    private final int httpStatus;
    private final String message;

    ErrorCode(final int value, final int httpStatus, final String message) {
        this.value = value;
        this.httpStatus = httpStatus;
        this.message = message;
    }
    
    /**
     * Return a string representation of this error code.
     */
    @Override
    public String toString() {
        return this.value + " " + name();
    }

    /**
     * Retrieves the matching ErrorCode for the provided {@code code} value. If no match can be found, {@link #UNKNOWN}
     * is returned.
     *
     * @param code
     *         the code to match to an existing ErrorCode
     *
     * @return the matching ErrorCode, or {@link #UNKNOWN}
     */
    public static ErrorCode fromErrorCode(final Integer code) {
        if (code == null) {
            return UNKNOWN;
        }
        return Arrays.stream(values())
                .filter(current -> current.value == code)
                .findFirst()
                .orElse(UNKNOWN);
    }
}