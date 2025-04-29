package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class InvalidRpcResponseException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidRpcResponseException(final Metadata metadata) {
        super(ErrorCode.INVALID_RPC_RESPONSE, metadata);
    }
}
