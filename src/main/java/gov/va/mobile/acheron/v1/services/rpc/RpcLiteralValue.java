package gov.va.mobile.acheron.v1.services.rpc;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RpcLiteralValue extends RpcValue {
    private final String value;

    public RpcLiteralValue(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String serialize() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}