package gov.va.mobile.cocytus.client;

import gov.va.mobile.vista.rpc.query.RpcParameter;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class RpcRequest {
    private String rpcContext;
    private List<RpcParameter> parameters;
    private Integer envelopeSize;
    private String ssoiToken;
    private UUID transactionId;
    private Map<String, String> auditFields;
}