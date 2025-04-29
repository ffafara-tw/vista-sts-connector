package gov.va.mobile.acheron.v1.model;

import java.io.Serial;

public class VistaRpcFailedException extends AcheronApiException {
  @Serial
  private static final long serialVersionUID = 1L;

  public VistaRpcFailedException(final Metadata metadata) {
    super(ErrorCode.VISTA_RPC_FAILED, metadata);
  }
}
