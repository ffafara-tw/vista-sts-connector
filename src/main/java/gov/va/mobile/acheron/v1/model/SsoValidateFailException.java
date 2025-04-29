package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class SsoValidateFailException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SsoValidateFailException(final Metadata metadata) {
        super(ErrorCode.SSO_VALIDATE_FAIL, metadata);
    }
}
