package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class VistaConnectionFailException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;


    public VistaConnectionFailException(final Metadata metadata) {
        super(ErrorCode.VISTA_CONNECTION_FAIL, metadata);
    }
}
