package gov.va.mobile.acheron.v1.services.rpc;


import gov.va.mobile.vista.rpc.util.RPCStringUtils;

public class VistaResponse {
    private final String rawResponse;

    public VistaResponse(final String rawResponse) {
        this.rawResponse = rawResponse;
    }

    /**
     * Gets the raw string response of the executed vista query.
     * @return string response
     */
    public String getRawResponse() {
        return rawResponse;
    }

    public String getPiece(final String delimiter, final int pieceNumber) {
        return RPCStringUtils.piece(rawResponse, delimiter, pieceNumber);
    }

    @Override
    public String toString() {
        return rawResponse;
    }
}
