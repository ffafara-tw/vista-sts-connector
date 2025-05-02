package gov.va.cdsp;

import gov.va.mobile.acheron.v1.AppProperties;
import gov.va.mobile.acheron.v1.services.rpc.RPCException;
import gov.va.mobile.cocytus.client.RpcClient;
import gov.va.mobile.cocytus.client.RpcRequest;
import gov.va.mobile.cocytus.client.RpcRequestBuilder;
import gov.va.mobile.cocytus.client.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CocytusTester {
    public static void main(String[] args) throws RPCException, IOException {
        AppProperties appProperties = new AppProperties("stsToken.xml");
        RpcClient client = new RpcClient("", "500", null);
        RpcRequest rpcRequest = new RpcRequestBuilder()
                .rpcContext("CDSP RPC CONTEXT")
                .ssoiToken(appProperties.getStsToken())
                .build();
        RpcResponse response = client.executeRpc("ORWPT LIST ALL", rpcRequest);
        log.info("Response: {}", response);
    }
}
