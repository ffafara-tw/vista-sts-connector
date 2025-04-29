package gov.va.mobile.vista.rpc.query;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RPC Parameter wrapper, containing parameter values for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RpcParameter {

    /**
     * Empty value Unicode string used to represent an empty serialized value.
     */
    protected static final String EMPTY_VALUE = "\u0001";

    /**
     * Returns {@code true} if the parameter value is "empty".
     *
     * @return {@code true} if the parameter value is "empty"; {@code false} otherwise
     */
    public abstract boolean isEmpty();

    /**
     * Retrieves the parameter value, formatted for building the VistA RPC Query message.
     *
     * @param envelopeSize
     *         the size of the envelope, used for value packing/padding
     *
     * @return the formatted value
     */
    abstract String getParamValue(int envelopeSize);

    /**
     * Serializes the parameter value into a {@link String} used to build parts of the VistA RPC Query message.
     * <p>
     * The primary difference between the {@link #getParamValue(int)} and this method is that the former is meant to be
     * the root string appended to the query, whereas this method is used to build up the message tree, especially in
     * the case of "list" type parameters.
     *
     * @param envelopeSize
     *         the size of the envelope, used for value packing/padding
     *
     * @return the serialized parameter value {@link String}
     */
    protected abstract String serialize(int envelopeSize);

    /**
     * Accumulation helper used for parameter value serialization, particularly for complex list-based subtypes.
     *
     * @param builder
     *         the {@link StringBuilder} to append serialization values
     * @param keyPrefix
     *         the current key prefix for associated serialization values
     * @param envelopeSize
     *         the size of the envelope, used for value packing/padding
     */
    protected abstract void accumulate(StringBuilder builder, String keyPrefix, int envelopeSize);
}