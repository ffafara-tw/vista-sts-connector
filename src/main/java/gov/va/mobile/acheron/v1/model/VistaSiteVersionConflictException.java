package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class VistaSiteVersionConflictException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public VistaSiteVersionConflictException(final Metadata metadata) {
        super(ErrorCode.VISTA_SITE_VERSION_CONFLICT, metadata);
    }
}
