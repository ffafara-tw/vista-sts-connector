package gov.va.mobile.cocytus.client;


import gov.va.mobile.vista.rpc.query.EncryptedLiteralRpcParameter;
import gov.va.mobile.vista.rpc.query.ListRpcParameter;
import gov.va.mobile.vista.rpc.query.LiteralRpcParameter;
import gov.va.mobile.vista.rpc.query.ReferenceRpcParameter;
import gov.va.mobile.vista.rpc.query.RpcParameter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Builder expanding the default builder methods for an {@link RpcRequest} object, adding common support methods for
 * constructing a new request.
 *
 * @author cody@apothesource.com
 * @see RpcRequest
 * @since 1.0
 */
@NoArgsConstructor
public final class RpcRequestBuilder {

    /**
     * The {@link RpcRequest} being built and populated.
     *
     * @see RpcRequest
     */
    private final RpcRequest request = new RpcRequest();

    /**
     * Populates the "rpc-context" request value.
     *
     * @param rpcContext
     *         the "rpc-context" value
     *
     * @return the builder instance
     *
     */
    public RpcRequestBuilder rpcContext(final String rpcContext) {
        request.setRpcContext(rpcContext);
        return this;
    }

    /**
     * Populates all "parameters" request value.
     *
     * @param parameters
     *         the "parameters" value
     *
     * @return the builder instance
     *
     */
    public RpcRequestBuilder parameters(final List<RpcParameter> parameters) {
        request.setParameters(parameters);
        return this;
    }

    /**
     * Adds an individual {@link RpcParameter} to the request parameter list.
     *
     * @param parametersItem
     *         the next {@link RpcParameter} value
     *
     * @return the builder instance
     *
     * @see RpcParameter
     */
    public RpcRequestBuilder addParametersItem(final RpcParameter parametersItem) {
        request.getParameters().add(parametersItem);
        return this;
    }

    /**
     * Adds the provided {@link String} value as a new {@link LiteralRpcParameter} to the request parameter list.
     *
     * @param value
     *         the parameter value
     *
     * @return the builder instance
     *
     * @see LiteralRpcParameter
     */
    public RpcRequestBuilder addLiteralParam(final String value) {
        final LiteralRpcParameter parameter = LiteralRpcParameter.ofLiteral(value);
        return addParametersItem(parameter);
    }

    /**
     * Adds the provided {@link String} value as a new encrypted {@link EncryptedLiteralRpcParameter} to the request parameter
     * list.
     *
     * @param value
     *         the parameter value
     *
     * @return the builder instance
     *
     */
    public RpcRequestBuilder addEncryptedParam(final String value) {
        final EncryptedLiteralRpcParameter parameter = EncryptedLiteralRpcParameter.ofEncryptedLiteral(value);
        return addParametersItem(parameter);
    }

    /**
     * Adds the provided {@link String} value as a new {@link ReferenceRpcParameter} to the request parameter list.
     *
     * @param value
     *         the parameter value
     *
     * @return the builder instance
     *
     * @see ReferenceRpcParameter
     */
    public RpcRequestBuilder addReferenceParam(final String value) {
        final ReferenceRpcParameter parameter = ReferenceRpcParameter.ofReference(value);
        return addParametersItem(parameter);
    }

    /**
     * Adds the provided {@link List} of values as a new {@link ListRpcParameter} to the request parameter list, using
     * index-based keys.
     *
     * @param values
     *         the parameter values
     *
     * @return the builder instance
     *
     * @see ListRpcParameter
     */
    public RpcRequestBuilder addIndexedListParam(final List<String> values) {
        final ListRpcParameter parameter = ListRpcParameter.ofList(
                values.stream()
                        .map(LiteralRpcParameter::ofLiteral)
                        .map(value -> (RpcParameter) value)
                        .toList());
        return addParametersItem(parameter);
    }

    /**
     * Adds the provided {@link Map} of key/value entries as a new {@link ListRpcParameter} to the request parameter list.
     *
     * @param values
     *         the parameter keys/values
     *
     * @return the builder instance
     *
     * @see ListRpcParameter
     */
    public RpcRequestBuilder addListParam(final Map<String, String> values) {
        final ListRpcParameter parameter = ListRpcParameter.ofList();
        for (final Map.Entry<String, String> entry : values.entrySet()) {
            parameter.add(entry.getKey(), LiteralRpcParameter.ofLiteral(entry.getValue()));
        }
        return addParametersItem(parameter);
    }

    /**
     * Populates the "envelope-size" request value.
     *
     * @param envelopeSize
     *         the "envelope-size" value
     *
     * @return the builder instance
     *
     */
    public RpcRequestBuilder envelopeSize(final Integer envelopeSize) {
        request.setEnvelopeSize(envelopeSize);
        return this;
    }

    /**
     * Populates the "ssoi-token" request value.
     *
     * @param ssoiToken
     *         the "ssoi-token" value
     *
     * @return the builder instance
     *
     */
    public RpcRequestBuilder ssoiToken(final String ssoiToken) {
        request.setSsoiToken(ssoiToken);
        return this;
    }

    /**
     * Populates the "transaction-id" request value.
     *
     * @param transactionId
     *         the "transaction-id" value
     *
     * @return the builder instance
     *
     */
    public RpcRequestBuilder transactionId(final UUID transactionId) {
        request.setTransactionId(transactionId);
        return this;
    }


    /**
     * Returns the built and populated {@link RpcRequest}.
     *
     * @return the built and populated {@link RpcRequest}
     *
     * @see RpcRequest
     */
    public RpcRequest build() {
        return request;
    }

    /**
     * Returns a new request builder instance.
     *
     * @return a new request builder instance
     */
    public static RpcRequestBuilder newBuilder() {
        return new RpcRequestBuilder();
    }
}