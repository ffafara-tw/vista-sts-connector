package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class VistaSiteNotFoundException extends AcheronApiException {
  @Serial
  private static final long serialVersionUID = 1L;

  public VistaSiteNotFoundException(final Metadata metadata) {
      super(ErrorCode.VISTA_SITE_NOT_FOUND, metadata);
  }
}
