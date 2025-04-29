package gov.va.mobile.acheron.v1.model;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class AcheronApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;
    private final Metadata metadata;

    public AcheronApiException(final ErrorCode errorCode, final Metadata metadata) {
        super(String.format("errorCode=%d, msg=%s detail=%s eventId=%s source=%s",
                        errorCode.getValue(),
                        errorCode.getMessage(),
                        metadata.detail,
                        metadata.eventId,
                        metadata.source),
                metadata.cause);

        this.errorCode = errorCode;
        this.metadata = metadata;
    }

    @Builder
    @Getter
    public static class Metadata implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private String detail;
        private String eventId;
        @Builder.Default
        private String source = "Acheron";
        private Throwable cause;
    }
}
