package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class UnknownParameterTypeException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UnknownParameterTypeException(final Metadata metadata) {
        super(ErrorCode.UNKNOWN_PARAMETER_TYPE, metadata);
    }
}
