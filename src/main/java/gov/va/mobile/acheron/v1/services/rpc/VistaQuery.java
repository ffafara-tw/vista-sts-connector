package gov.va.mobile.acheron.v1.services.rpc;

import gov.va.mobile.vista.rpc.util.RPCStringUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * VistA query using the new parameter objects
 */
@Getter
public class VistaQuery {
    private final String rpcName;
    private final String issuer;

    public enum ParameterType {
        LITERAL,
        REFERENCE,
        LIST,
        GLOBAL
    }

    private final List<Parameter> parameters;

    public VistaQuery(final String rpcName, final String issuer) {
        this.rpcName = rpcName;
        this.issuer = issuer;
        this.parameters = new ArrayList<>();
    }

    public String getRpcName() {
        return rpcName;
    }

    @Override
    public String toString() {
        return buildMessage();
    }

    public void addLiteral(final RpcLiteralValue value) {
        parameters.add(new Parameter(ParameterType.LITERAL, value));
    }

    public void addEncryptedParameter(final RpcEncryptedLiteralValue value) {
        parameters.add(new Parameter(ParameterType.LITERAL, value));
    }

    public void addList(final RpcListValue value) {
        parameters.add(new Parameter(ParameterType.LIST, value));
    }

    public void addGlobal(final RpcGlobalValue value) {
        parameters.add(new Parameter(ParameterType.GLOBAL, value));
    }

    public String buildMessage() {
        StringBuilder params = new StringBuilder("5");

        for (Parameter parameter : parameters) {
            ParameterType type = parameter.getType();
            if (type == ParameterType.LITERAL) {
                params.append('0').append(RPCStringUtils.rpcPad(parameter.getValue().serialize())).append('f');
            } else if (type == ParameterType.REFERENCE) {
                params.append('1').append(RPCStringUtils.rpcPad(parameter.getValue().serialize())).append('f');
            } else if (type == ParameterType.LIST) {
                params.append('2').append(parameter.getValue().serialize());
            } else if (type == ParameterType.GLOBAL) {
                params.append('3').append(parameter.getValue().serialize());
            }
        }

        // If no parameters were serialized and appended "4f" must be appended.
        if (params.length() == 1) {
            params.append("4f");
        }

        return "[XWB]11302"
                + RPCStringUtils.pack("2.0")
                + RPCStringUtils.pack(rpcName)
                + params
                + '\u0004';
    }

    public static class Parameter {
        ParameterType type;
        RpcValue value;

        public Parameter(final ParameterType type, final RpcValue value) {
            this.type = type;
            this.value = value;
        }

        public ParameterType getType() {
            return type;
        }

        public RpcValue getValue() {
            return value;
        }
    }

}
