package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class InvalidRpcParametersException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidRpcParametersException(final Metadata metadata) {
        super(ErrorCode.INVALID_RPC_PARAMETERS, metadata);
    }
}
