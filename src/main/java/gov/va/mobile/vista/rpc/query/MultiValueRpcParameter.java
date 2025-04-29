package gov.va.mobile.vista.rpc.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static gov.va.mobile.vista.rpc.util.RPCStringUtils.pad;

/**
 * {@link RpcParameter} implementation used to hold multiple parameter values for VistA RPC calls.
 * <p>
 * Important Note: When adding parameters using the various "add" methods, it is important to consider any previously
 * added parameters. The internal dictionary of keys (indexes) and values are processed in insertion order. This means
 * that adding RPC Parameters must be added in the exact order they are expected to appear in the generated RPC Broker
 * query {@link String}.
 *
 * @author cody@apothesource.com
 * @see RpcParameter
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MultiValueRpcParameter extends RpcParameter {

    /**
     * The dictionary {@link List} of {@link Map.Entry} objects.
     *
     * @see List
     * @see Map.Entry
     */
    private final List<Map.Entry<String, RpcParameter>> dictionary = new ArrayList<>();

    /**
     * Retrieves the first {@link RpcParameter} with the provided {@code key} (case-sensitive) from the
     * {@link #dictionary}.
     *
     * @param key
     *         the key of the {@link RpcParameter} to find
     *
     * @return the {@link RpcParameter} value
     *
     * @see RpcParameter
     */
    public Optional<RpcParameter> find(final String key) {
        return dictionary.stream()
                         .filter(entry -> StringUtils.equals(entry.getKey(), key))
                         .map(Map.Entry::getValue)
                         .findFirst();
    }

    /**
     * Add a new entry for the dictionary list, with the {@code index} as the key and the {@link RpcParameter} as the
     * value.
     *
     * @param index
     *         the index, used as the entry key
     * @param value
     *         the {@link RpcParameter}  entry value
     *
     * @return the parameter instance
     *
     * @see RpcParameter
     */
    public MultiValueRpcParameter add(final int index, final RpcParameter value) {
        dictionary.add(Map.entry(Integer.toString(index), value));
        return this;
    }

    /**
     * Add a new entry for the dictionary list.
     *
     * @param key
     *         the entry key
     * @param value
     *         the entry value
     *
     * @return the parameter instance
     */
    public MultiValueRpcParameter add(final String key, final RpcParameter value) {
        dictionary.add(Map.entry(key, value));
        return this;
    }

    /**
     * Adds the provided {@link List} of {@link RpcParameter} values to the end of the current dictionary list, ensuring
     * the index (key) starts at the current dictionary size (+1).
     *
     * @param values
     *         the {@link RpcParameter} values to add
     *
     * @return the parameter instance
     *
     * @see RpcParameter
     */
    public MultiValueRpcParameter addAll(final List<RpcParameter> values) {
        // Indexes start at "1", not "0"
        for (final RpcParameter value : values) {
            final int index = dictionary.size() + 1;
            add(index, value);
        }
        return this;
    }

    /**
     * Adds the provided {@link Map} of {@link RpcParameter} entries to the end of the current dictionary list.
     *
     * @param values
     *         the {@link RpcParameter} entries to add
     *
     * @return the parameter instance
     *
     * @see RpcParameter
     */
    public MultiValueRpcParameter addAll(final Map<String, RpcParameter> values) {
        for (final Map.Entry<String, RpcParameter> entry : values.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    protected String serialize(final int envelopeSize) {
        if (dictionary.isEmpty()) {
            return pad(StringUtils.EMPTY, envelopeSize) + 'f';
        }

        final StringBuilder builder = new StringBuilder();

        for (final Map.Entry<String, RpcParameter> entry : dictionary) {
            final String key = entry.getKey();
            final RpcParameter value = entry.getValue();

            value.accumulate(builder, key, envelopeSize);
        }

        // Remove the last character, which will be the `t` separator between entries
        return builder.deleteCharAt(builder.length() - 1)
                      .append('f')
                      .toString();
    }

    @Override
    protected void accumulate(final StringBuilder builder, final String keyPrefix, final int envelopeSize) {
        for (final Map.Entry<String, RpcParameter> entry : dictionary) {
            final String newKey = keyPrefix + ',' + entry.getKey();

            final String serialized = StringUtils.defaultIfEmpty(entry.getValue().serialize(envelopeSize), EMPTY_VALUE);
            builder.append(pad(newKey, envelopeSize))
                   .append(pad(serialized, envelopeSize))
                   .append('t');
        }
    }
}