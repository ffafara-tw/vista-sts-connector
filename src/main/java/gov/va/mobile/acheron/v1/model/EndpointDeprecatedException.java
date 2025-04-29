package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class EndpointDeprecatedException extends AcheronApiException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EndpointDeprecatedException(final Metadata metadata) {
          super(ErrorCode.ENDPOINT_DEPRECATED, metadata);
      }
}
