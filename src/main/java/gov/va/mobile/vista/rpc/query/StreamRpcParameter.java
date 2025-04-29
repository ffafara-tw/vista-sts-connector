package gov.va.mobile.vista.rpc.query;

import static gov.va.mobile.vista.rpc.util.RPCStringUtils.pad;

/**
 * {@link SingleValueRpcParameter} implementation used to hold 'stream' parameter values for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @see SingleValueRpcParameter
 * @since 1.0
 */
public final class StreamRpcParameter extends SingleValueRpcParameter {

    /**
     * Constructor used to initialize the value member.
     *
     * @param value
     *         the value to initialize
     */
    private StreamRpcParameter(final String value) {
        super(value);
    }

    @Override
    String getParamValue(final int envelopeSize) {
        return '5' + pad(serialize(envelopeSize), envelopeSize) + 'f';
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
    public static StreamRpcParameter ofStream(final String value) {
        return new StreamRpcParameter(value);
    }
}