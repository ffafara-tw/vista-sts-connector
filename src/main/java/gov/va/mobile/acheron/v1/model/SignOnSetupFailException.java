package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class SignOnSetupFailException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SignOnSetupFailException(final Metadata metadata) {
        super(ErrorCode.SIGN_ON_SETUP_FAIL, metadata);
    }
}
