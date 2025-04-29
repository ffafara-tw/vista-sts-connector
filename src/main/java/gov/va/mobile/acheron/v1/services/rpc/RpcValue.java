package gov.va.mobile.acheron.v1.services.rpc;

public abstract class RpcValue {
    public abstract String serialize();

    @Override
    public abstract String toString();
}