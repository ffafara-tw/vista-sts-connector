package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class CreateContextFailException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CreateContextFailException(final Metadata metadata) {
        super(ErrorCode.CREATE_CONTEXT_FAIL, metadata);
    }
}
