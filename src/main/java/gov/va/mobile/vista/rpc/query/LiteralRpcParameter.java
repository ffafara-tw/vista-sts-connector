package gov.va.mobile.vista.rpc.query;

import static gov.va.mobile.vista.rpc.util.RPCStringUtils.pad;

/**
 * {@link SingleValueRpcParameter} implementation used to hold 'literal' parameter values for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @see SingleValueRpcParameter
 * @since 1.0
 */
public class LiteralRpcParameter extends SingleValueRpcParameter {

    /**
     * Constructs a new parameter with a single value.
     *
     * @param value
     *         the parameter value
     */
    protected LiteralRpcParameter(final String value) {
        super(value);
    }

    @Override
    String getParamValue(final int envelopeSize) {
        return '0' + pad(serialize(envelopeSize), envelopeSize) + 'f';
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
    public static LiteralRpcParameter ofLiteral(final String value) {
        return new LiteralRpcParameter(value);
    }
}