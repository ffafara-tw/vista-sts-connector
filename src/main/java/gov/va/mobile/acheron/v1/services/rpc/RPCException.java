package gov.va.mobile.acheron.v1.services.rpc;

public class RPCException extends Exception {

    private static final long serialVersionUID = 1L;

    public RPCException(final Exception exception) {
        super(exception);
    }

    public RPCException(final String error) {
        super(error);
    }

    public RPCException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
