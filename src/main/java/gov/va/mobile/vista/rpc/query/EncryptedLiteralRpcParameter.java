package gov.va.mobile.vista.rpc.query;

/**
 * {@link LiteralRpcParameter} implementation used to hold encrypted 'literal' parameter values for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @see LiteralRpcParameter
 * @since 1.0
 */
public final class EncryptedLiteralRpcParameter extends LiteralRpcParameter {

    /**
     * Constructs a new parameter with a single value.
     *
     * @param value
     *         the parameter value
     */
    private EncryptedLiteralRpcParameter(final String value) {
        super(value);
    }

    @Override
    protected String serialize(final int envelopeSize) {
        return EncryptionUtils.encrypt(super.serialize(envelopeSize));
    }

    /*
    Builders
     */

    /**
     * Build a new instance for the provided {@code value}.
     *
     * @param value
     *         the value
     *
     * @return a new instance
     */
    public static EncryptedLiteralRpcParameter ofEncryptedLiteral(final String value) {
        return new EncryptedLiteralRpcParameter(value);
    }
}