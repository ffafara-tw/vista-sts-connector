package gov.va.mobile.cocytus.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;

import java.io.IOException;

/**
 * Client used to execute RPC execution request to the 'Cocytus' system for a specified VistA site.
 */
@Slf4j
@RequiredArgsConstructor
public class RpcClient {
    private final String baseUrl;
    private final String siteId;
    private final String jwt;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RpcResponse executeRpc(final String rpcName, final RpcRequest rpcRequest) {
        String url = String.format("%s/sites/%s/rpc/%s", baseUrl, siteId, rpcName);

        try {
            String responseBody = Request.post(url)
                    .addHeader("x-vamf-jwt", jwt)
                    .bodyString(serializeRequest(rpcRequest), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            return validateResponse(deserializeResponse(responseBody));
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute RPC request", e);
        }
    }

    private String serializeRequest(RpcRequest rpcRequest) {
        try {
            return objectMapper.writeValueAsString(rpcRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize RpcRequest to JSON", e);
        }
    }

    private RpcResponse deserializeResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, RpcResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to RpcResponse", e);
        }
    }

    private static <T> T validateResponse(T response) {
        if (response == null) {
            throw new RuntimeException("Empty response returned from Cocytus");
        }
        return response;
    }
}