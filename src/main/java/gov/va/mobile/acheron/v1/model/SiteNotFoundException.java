package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class SiteNotFoundException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SiteNotFoundException(final Metadata metadata) {
        super(ErrorCode.SITE_NOT_FOUND, metadata);
    }
}
