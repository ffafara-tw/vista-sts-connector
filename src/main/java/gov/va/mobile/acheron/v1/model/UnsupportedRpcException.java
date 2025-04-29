package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class UnsupportedRpcException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UnsupportedRpcException(final Metadata metadata) {
        super(ErrorCode.UNSUPPORTED_RPC, metadata);
    }
}
