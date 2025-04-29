package gov.va.mobile.acheron.v1.exceptions;

/**
 * Exception thrown when there are issues with the VistA resource pool.
 *
 * @author scott.thompson@apothsource.com
 * @since 1.0
 */
public class PoolException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code PoolException} instance.
     *
     * @param msg
     *         the detail message.
     * @param cause
     *         the nested exception.
     */
    public PoolException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}