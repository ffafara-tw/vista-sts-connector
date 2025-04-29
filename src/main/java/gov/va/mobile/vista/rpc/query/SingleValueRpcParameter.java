package gov.va.mobile.vista.rpc.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static gov.va.mobile.vista.rpc.util.RPCStringUtils.pad;

/**
 * {@link RpcParameter} implementation used to hold single parameter values for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @see RpcParameter
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SingleValueRpcParameter extends RpcParameter {

    /**
     * The parameter value, if single value.
     */
    protected final String value;

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    protected String serialize(final int envelopeSize) {
        return value;
    }

    @Override
    protected void accumulate(final StringBuilder builder, final String keyPrefix, final int envelopeSize) {
        final String serialized = StringUtils.defaultIfEmpty(serialize(envelopeSize), EMPTY_VALUE);
        builder.append(pad(keyPrefix, envelopeSize))
               .append(pad(serialized, envelopeSize))
               .append('t');
    }
}