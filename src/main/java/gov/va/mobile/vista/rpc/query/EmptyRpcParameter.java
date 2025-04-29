package gov.va.mobile.vista.rpc.query;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static gov.va.mobile.vista.rpc.util.RPCStringUtils.pad;

/**
 * {@link RpcParameter} implementation used to hold an empty parameter value for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @see RpcParameter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmptyRpcParameter extends RpcParameter {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    String getParamValue(final int envelopeSize) {
        return "4f";
    }

    @Override
    protected String serialize(final int envelopeSize) {
        return StringUtils.EMPTY;
    }

    @Override
    protected void accumulate(final StringBuilder builder, final String keyPrefix, final int envelopeSize) {
        final String serialized = StringUtils.defaultIfEmpty(serialize(envelopeSize), EMPTY_VALUE);
        builder.append(pad(keyPrefix, envelopeSize))
               .append(pad(serialized, envelopeSize))
               .append('t');
    }

    /*
    Builders
     */

    /**
     * Build a new instance.
     *
     * @return a new instance
     */
    public static EmptyRpcParameter ofEmpty() {
        return new EmptyRpcParameter();
    }
}