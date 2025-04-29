package gov.va.mobile.acheron.v1.services.rpc;

import gov.va.mobile.vista.rpc.util.RPCStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A list value for sending to the VistA RPC broker. The list may be indexed by integers (starting at 1), or by strings.
 */
public class RpcListValue extends RpcValue {
    public static final String CONTROL_CHARACTER = "\u0001";
    private final List<Map.Entry<String, RpcValue>> list = new ArrayList<>();

    /**
     * Return the number of entries in the list.
     * @return the size of the list
     */
    public int size() {
        return list.size();
    }

    /**
     * Get an entry from the list. Note that index is *not* the entry 'key' value.
     * @param index the position in the list to return
     * @return
     */
    public Map.Entry<String, RpcValue> get(final int index) {
        return list.get(index);
    }

    /**
     * Add an integer-indexed entry to the list.
     * @param index the index for the value
     * @param value the value
     */
    public RpcListValue add(final int index, final RpcValue value) {
        list.add(Map.entry(String.valueOf(index), value));
        return this;
    }

    /**
     * Add a string-indexed entry to the list.
     * @param index the index for the value
     * @param value the value
     */
    public RpcListValue add(final String index, final RpcValue value) {
        list.add(Map.entry(index, value));
        return this;
    }

    public RpcListValue add(final int index, final String value) {
        add(String.valueOf(index), new RpcLiteralValue(value));
        return this;
    }

    public RpcListValue add(final String index, final String value) {
        add(index, new RpcLiteralValue(value));
        return this;
    }

    /**
     * Adds the provided {@link List} of {@link String} values to the end of the current dictionary list as
     * {@link RpcLiteralValue}s, ensuring the index (key) starts at the current dictionary size (+1).
     * @param values the {@link String} values to add
     * @return the parameter instance
     */
    public RpcListValue addAllLiterals(final List<String> values) {
        // Indexes start at "1", not "0"
        for (final String value : values) {
            final int index = list.size() + 1;
            add(index, value);
        }
        return this;
    }

    /**
     * Append each of this list's key-value pairs into the accumulator, prefixing each entry key with keyPrefix. This
     * method should only be called by {@link #serialize()}.
     * @param accumulator the accumulator list, not null
     * @param keyPrefix the key prefix, not null
     */
    private void accumulate(final List<Map.Entry<String, String>> accumulator, final String keyPrefix) {
        list.forEach(entry -> {
            //TODO see if we even need the null path
            if (entry.getValue() == null) {
                accumulator.add(Map.entry(keyPrefix + "," + entry.getKey(), CONTROL_CHARACTER));

            } else if (entry.getValue() instanceof RpcLiteralValue) {
                String value = entry.getValue().serialize();
                if (value == null || value.isEmpty()) {
                    value = CONTROL_CHARACTER;
                }
                accumulator.add(Map.entry(keyPrefix + "," + entry.getKey(), value));

            } else if (entry.getValue() instanceof RpcListValue) {
                ((RpcListValue) entry.getValue()).accumulate(accumulator, keyPrefix + "," + entry.getKey());
            }
        });
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    @Override
    public String serialize() {
        if (list.isEmpty()) {
            return RPCStringUtils.rpcPad(StringUtils.EMPTY) + 'f';
        } else {
            final StringBuilder msg = new StringBuilder();

            for (Map.Entry<String, RpcValue> entry : list) {
                String key = entry.getKey();

                String value;
                //TODO see if we even need the null path
                if (entry.getValue() == null) {
                    value = CONTROL_CHARACTER;
                    msg.append(RPCStringUtils.rpcPad(key))
                            .append(RPCStringUtils.rpcPad(value))
                            .append('t');

                } else if (entry.getValue() instanceof RpcLiteralValue) {
                    value = entry.getValue().serialize();
                    if (StringUtils.isEmpty(value)) {
                        value = CONTROL_CHARACTER;
                    }
                    msg.append(RPCStringUtils.rpcPad(key))
                            .append(RPCStringUtils.rpcPad(value))
                            .append('t');

                } else if (entry.getValue() instanceof RpcListValue) {
                    List<Map.Entry<String, String>> subList = new ArrayList<>();
                    ((RpcListValue) entry.getValue()).accumulate(subList, key);

                    subList.forEach(item -> msg.append(RPCStringUtils.rpcPad(item.getKey()))
                            .append(RPCStringUtils.rpcPad(item.getValue())).append('t'));

                }
            }

            return RPCStringUtils.substring(msg.toString(), 0, msg.length() - 1) + 'f';
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("list", list).toString();
    }
}