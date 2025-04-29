package gov.va.mobile.vista.rpc.query;

import gov.va.mobile.vista.rpc.util.RPCStringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Builds an RPC query to a VistA system, including the RPC name and parameters used to build a single {@link String}
 * message to write to the TCP socket connection.
 * <p>
 * Important Note: When adding parameters using the various "add" methods, it is important to consider any previously
 * added parameters, including those via constructor. RPC Parameters must be added in the exact order they are expected
 * to appear in the generated RPC Broker query {@link String}.
 *
 * @author cody@apothesource.com
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class VistaQuery {

    /**
     * The name of the RPC invoked in the VistA system call.
     */
    private final String rpcName;

    /**
     * The envelope size used for creating the RPC query string, for 'lpack'ing.
     * <p>
     * Default: 3
     */
    @Setter
    @Accessors(fluent = true, chain = true)
    private int envelopeSize = 3;

    /**
     * The RPC parameters.
     */
    @Setter
    @Accessors(fluent = true, chain = true)
    private List<RpcParameter> parameters = new ArrayList<>();

    /**
     * Add a new {@link LiteralRpcParameter} to the parameter list with the provided value.
     *
     * @param value
     *         the value of the parameter to add
     *
     * @return the query instance
     *
     * @see LiteralRpcParameter
     */
    public VistaQuery addLiteral(final String value) {
        parameters.add(LiteralRpcParameter.ofLiteral(value));
        return this;
    }

    /**
     * Add a new {@link EncryptedLiteralRpcParameter} to the parameter list with the provided value.
     *
     * @param value
     *         the value of the parameter to add
     *
     * @return the query instance
     *
     * @see EncryptedLiteralRpcParameter
     */
    public VistaQuery addEncryptedLiteral(final String value) {
        parameters.add(EncryptedLiteralRpcParameter.ofEncryptedLiteral(value));
        return this;
    }

    /**
     * Add a new {@link ReferenceRpcParameter} to the parameter list with the provided value.
     *
     * @param value
     *         the value of the parameter to add
     *
     * @return the query instance
     *
     * @see ReferenceRpcParameter
     */
    public VistaQuery addReference(final String value) {
        parameters.add(ReferenceRpcParameter.ofReference(value));
        return this;
    }

    /**
     * Add the provided {@link ListRpcParameter} to the parameter list.
     *
     * @param parameter
     *         the {@link ListRpcParameter} to add
     *
     * @return the query instance
     *
     * @see ListRpcParameter
     */
    public VistaQuery addList(final ListRpcParameter parameter) {
        parameters.add(parameter);
        return this;
    }

    /**
     * Add a new {@link ListRpcParameter} to the parameter list with the provided {@link List} of values.
     * <p>
     * The parameters will be added with an index key and the parameter value.
     *
     * @param values
     *         the {@link List} of values for the {@link ListRpcParameter} to add
     *
     * @return the query instance
     *
     * @see ListRpcParameter
     */
    public VistaQuery addList(final List<RpcParameter> values) {
        parameters.add(ListRpcParameter.ofList(values));
        return this;
    }

    /**
     * Add a new {@link ListRpcParameter} to the parameter list with the provided {@link Map} of keys and value.
     * <p>
     * The parameters will be added with the key and the parameter value directly, escaping the key with
     * {@literal ""}s.
     *
     * @param values
     *         the {@link List} of values for the {@link ListRpcParameter} to add
     *
     * @return the query instance
     *
     * @see ListRpcParameter
     */
    public VistaQuery addList(final Map<String, RpcParameter> values) {
        parameters.add(ListRpcParameter.ofList(values));
        return this;
    }

    /**
     * Add the provided {@link GlobalRpcParameter} to the parameter list.
     *
     * @param parameter
     *         the {@link GlobalRpcParameter} to add
     *
     * @return the query instance
     *
     * @see GlobalRpcParameter
     */
    public VistaQuery addGlobal(final GlobalRpcParameter parameter) {
        parameters.add(parameter);
        return this;
    }

    /**
     * Add a new {@link GlobalRpcParameter} to the parameter list with the provided {@link List} of values.
     * <p>
     * The parameters will be added with an index key and the parameter value.
     *
     * @param values
     *         the {@link List} of values for the {@link GlobalRpcParameter} to add
     *
     * @return the query instance
     *
     * @see GlobalRpcParameter
     */
    public VistaQuery addGlobal(final List<RpcParameter> values) {
        parameters.add(GlobalRpcParameter.ofGlobal(values));
        return this;
    }

    /**
     * Add a new {@link GlobalRpcParameter} to the parameter list with the provided {@link Map} of keys and value.
     * <p>
     * The parameters will be added with the key and the parameter value directly, escaping the key with
     * {@literal ""}s.
     *
     * @param values
     *         the {@link List} of values for the {@link GlobalRpcParameter} to add
     *
     * @return the query instance
     *
     * @see GlobalRpcParameter
     */
    public VistaQuery addGlobal(final Map<String, RpcParameter> values) {
        parameters.add(GlobalRpcParameter.ofGlobal(values));
        return this;
    }

    /**
     * Add a new {@link EmptyRpcParameter} to the parameter list.
     *
     * @return the query instance
     *
     * @see EmptyRpcParameter
     */
    public VistaQuery addEmpty() {
        parameters.add(EmptyRpcParameter.ofEmpty());
        return this;
    }

    /**
     * Add a new {@link StreamRpcParameter} to the parameter list with the provided value.
     *
     * @param value
     *         the value of the parameter to add
     *
     * @return the query instance
     *
     * @see StreamRpcParameter
     */
    public VistaQuery addStream(final String value) {
        parameters.add(StreamRpcParameter.ofStream(value));
        return this;
    }

    /**
     * Build the RPC query message to send to the VistA system.
     * <p>
     * Examples:
     * <table style="border: 1px solid;">
     * <caption>VistA Query Examples</caption>
     * <thead style="border: 1px solid;">
     * <tr>
     *     <th style="border: 1px solid;">Parameter Type</th>
     *     <th style="border: 1px solid;">Query String</th>
     * </tr>
     * </thead>
     * <tbody style="border: 1px solid;">
     * <tr style="border: 1px solid;">
     *     <td style="border: 1px solid; padding: 3px;">No Parameters</td>
     *     <td style="border: 1px solid; padding: 3px;">{@literal "[XWB]113022.0RPC NAME54f"}</td>
     * </tr>
     * <tr style="border: 1px solid;">
     *     <td style="border: 1px solid; padding: 3px;">Literal Parameter</td>
     *     <td style="border: 1px solid; padding: 3px;">{@literal "[XWB]113022.0RPC NAME50005valuef"}</td>
     * </tr>
     * <tr style="border: 1px solid;">
     *     <td style="border: 1px solid; padding: 3px;">Encrypted Parameter</td>
     *     <td style="border: 1px solid; padding: 3px;">{@literal "[XWB]113022.0RPC NAME50007&A,wn01f"}</td>
     * </tr>
     * <tr style="border: 1px solid;">
     *     <td style="border: 1px solid; padding: 3px;">List Parameter (Index Key)</td>
     *     <td style="border: 1px solid; padding: 3px;">{@literal "[XWB]113022.0RPC NAME520011005valuef"}</td>
     * </tr>
     * <tr style="border: 1px solid;">
     *     <td style="border: 1px solid; padding: 3px;">List Parameter (Name Key)</td>
     *     <td style="border: 1px solid; padding: 3px;">{@literal "[XWB]113022.0RPC NAME52005\"key\"005valuef"}</td>
     * </tr>
     * <tr style="border: 1px solid;">
     *     <td style="border: 1px solid; padding: 3px;">Multi-Level List Parameter</td>
     *     <td style="border: 1px solid; padding: 3px;">{@literal "[XWB]113022.0RPC NAME520031,10140011006value1ff"}</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @return the RPC query message to send to the VistA system
     */
    public String buildMessage() {
        final StringBuilder params = new StringBuilder("5");

        for (final RpcParameter parameter : parameters) {
            params.append(parameter.getParamValue(envelopeSize));
        }

        if ("5".contentEquals(params)) {
            params.append("4f");
        }

        return buildProtocolHeader()
                + RPCStringUtils.pack("2.0")
                + RPCStringUtils.pack(rpcName)
                + params
                + '\u0004';
    }

    /**
     * Builds the RPC Broker Protocol Header, setting the configured {@link #envelopeSize}.
     *
     * @return the RPC Broker Protocol Header
     *
     * @see #envelopeSize
     */
    private String buildProtocolHeader() {
        return String.format("[XWB]11%s02", envelopeSize);
    }
}