package gov.va.mobile.acheron.v1.services.rpc;

public class RpcEncryptedLiteralValue extends RpcLiteralValue {

    public RpcEncryptedLiteralValue(final String value) {
        super(value);
    }

    @Override
    public String serialize() {
        return EncryptionUtils.encrypt(super.serialize());
    }
}