package gov.va.mobile.vista.rpc.query;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * {@link MultiValueRpcParameter} implementation used to hold 'global' parameter values for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @see MultiValueRpcParameter
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class GlobalRpcParameter extends MultiValueRpcParameter {

    @Override
    public GlobalRpcParameter add(final int index, final RpcParameter value) {
        super.add(index, value);
        return this;
    }

    @Override
    public GlobalRpcParameter add(final String key, final RpcParameter value) {
        super.add(key, value);
        return this;
    }

    @Override
    public GlobalRpcParameter addAll(final List<RpcParameter> values) {
        super.addAll(values);
        return this;
    }

    @Override
    public GlobalRpcParameter addAll(final Map<String, RpcParameter> values) {
        super.addAll(values);
        return this;
    }

    @Override
    String getParamValue(final int envelopeSize) {
        return '3' + serialize(envelopeSize);
    }

    /*
    Builders
     */

    /**
     * Build a new instance.
     *
     * @return a new instance
     */
    public static GlobalRpcParameter ofGlobal() {
        return new GlobalRpcParameter();
    }

    /**
     * Build a new instance for the provided {@link List} of values.
     *
     * @param values
     *         the {@link List} of values
     *
     * @return a new instance
     */
    public static GlobalRpcParameter ofGlobal(final List<RpcParameter> values) {
        final GlobalRpcParameter parameter = new GlobalRpcParameter();
        parameter.addAll(values);
        return parameter;
    }

    /**
     * Build a new instance for the provided {@link Map} of values.
     *
     * @param values
     *         the {@link Map} of values
     *
     * @return a new instance
     */
    public static GlobalRpcParameter ofGlobal(final Map<String, RpcParameter> values) {
        final GlobalRpcParameter parameter = new GlobalRpcParameter();
        parameter.addAll(values);
        return parameter;
    }
}