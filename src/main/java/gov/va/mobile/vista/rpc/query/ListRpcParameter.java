package gov.va.mobile.vista.rpc.query;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * {@link MultiValueRpcParameter} implementation used to hold 'list' parameter values for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @see MultiValueRpcParameter
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ListRpcParameter extends MultiValueRpcParameter {

    @Override
    public ListRpcParameter add(final int index, final RpcParameter value) {
        super.add(index, value);
        return this;
    }

    @Override
    public ListRpcParameter add(final String key, final RpcParameter value) {
        super.add(key, value);
        return this;
    }

    @Override
    public ListRpcParameter addAll(final List<RpcParameter> values) {
        super.addAll(values);
        return this;
    }

    @Override
    public ListRpcParameter addAll(final Map<String, RpcParameter> values) {
        super.addAll(values);
        return this;
    }

    @Override
    String getParamValue(final int envelopeSize) {
        return '2' + serialize(envelopeSize);
    }

    /*
    Builders
     */

    /**
     * Build a new instance.
     *
     * @return a new instance
     */
    public static ListRpcParameter ofList() {
        return new ListRpcParameter();
    }

    /**
     * Build a new instance for the provided {@link List} of values.
     *
     * @param values
     *         the {@link List} of values
     *
     * @return a new instance
     */
    public static ListRpcParameter ofList(final List<RpcParameter> values) {
        final ListRpcParameter parameter = new ListRpcParameter();
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
    public static ListRpcParameter ofList(final Map<String, RpcParameter> values) {
        final ListRpcParameter parameter = new ListRpcParameter();
        parameter.addAll(values);
        return parameter;
    }
}