package gov.va.mobile.acheron.v1.exceptions;

public class SsoiStsTokenRetrievalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SsoiStsTokenRetrievalException(final String message) {
        super(message);
    }

    public SsoiStsTokenRetrievalException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
