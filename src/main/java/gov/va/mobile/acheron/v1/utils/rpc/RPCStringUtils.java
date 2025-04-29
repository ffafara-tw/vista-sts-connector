package gov.va.mobile.acheron.v1.utils.rpc;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility methods for preparing and parsing request and response {@link String} values for VistA RPC calls.
 *
 * @author scott.thompson@apothesource.com
 * @since 1.0
 */
@UtilityClass
@SuppressWarnings("PMD.ShortVariable")
public final class RPCStringUtils {

    public static final String CR = "\n";

    public static final Charset CP_1252 = Charset.forName("Cp1252");

    private static final int TOKEN_CHUNK_SIZE = 200;

    private static final Pattern CONTROL_CHARS = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");



    /**
     * Remove control characters, including non-printable characters from the provided {@link String}.
     *
     * @param str
     *         the {@link String} to remove control characters from
     *
     * @return the cleansed {@link String}
     */
    public static String removeControlChars(final String str) {
        if (StringUtils.isEmpty(str)) {
            return StringUtils.EMPTY;
        }
        return CONTROL_CHARS.matcher(str).replaceAll(StringUtils.EMPTY);
    }

    /**
     * Tokenize the provided {@link String} into a chunked {@link List} of substrings. The chunk size is controlled by
     * {@link #TOKEN_CHUNK_SIZE}.
     * <p>
     * If the provided {@link String} is "empty" after removing all control characters, a {@link List} with a single
     * "empty" string is returned.
     *
     * @param str
     *         the {@link String} to tokenize into a {@link List} of substrings
     *
     * @return the tokenized {@link List} of {@link String} chunks
     *
     * @see #TOKEN_CHUNK_SIZE
     */
    public static List<String> tokenize(final String str) {
        final String formatted = removeControlChars(str);
        if (formatted.isEmpty()) {
            return List.of(StringUtils.EMPTY);
        }

        final int length = formatted.length();

        final List<String> results = new ArrayList<>();
        for (int i = 0; i < length; i += TOKEN_CHUNK_SIZE) {
            results.add(formatted.substring(i, Math.min(length, i + TOKEN_CHUNK_SIZE)));
        }
        return results;
    }

    /**
     * Format the provided {@link String} by pre-pending its length and padding it with the {@code "0"} character 3
     * times for VistA RPC input.
     *
     * @param str
     *         the {@link String} to format
     *
     * @return the formatted {@link String}
     */
    public static String rpcPad(final String str) {
        return pad(str, 3);
    }

    /**
     * Format the provided {@link String} by pre-pending its length and padding it with the {@code "0"} character "n"
     * times for VistA RPC input.
     *
     * @param str
     *         the {@link String} to format
     * @param digits
     *         the number of 0s to pad
     *
     * @return the formatted {@link String}
     */
    public static String pad(final String str, final int digits) {
        final String safeStr = StringUtils.defaultString(str);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(safeStr.length());

        while (stringBuilder.length() < digits) {
            stringBuilder.insert(0, '0');
        }
        stringBuilder.append(safeStr);

        return stringBuilder.toString();
    }

    /**
     * Returns a substring of the provided {@link String} from the provided {@code start} and include the number of
     * characters matching the provided {@code length}.
     *
     * @param str
     *         the {@link String} to get a substring of
     * @param start
     *         the starting index
     * @param length
     *         the number of characters to the end of the substring
     *
     * @return the {@link String} to get a substring of
     */
    public static String substring(final String str, final int start, final int length) {
        return StringUtils.substring(str, start, start + length);
    }

    /**
     * Converts a byte array to a {@link String}.
     *
     * @param byteBuffer
     *         the byte array to convert
     * @param bytesRead
     *         the number of bytes in the array to convert to {@link String}
     *
     * @return the {@link String}
     */
    public String toString(final byte[] byteBuffer, final int bytesRead) {
        return new String(byteBuffer, 0, bytesRead, CP_1252);
    }

    /**
     * Removes control characters from a {@link String}.
     *
     * @param response
     *         the response string to convert
     *
     * @return the {@link String}
     */
    public String assembleResponse(final String response) {
        if (StringUtils.isBlank(response)) {
            return "{}";
        }
        return response.replaceAll("[\\p{C}]", "");
    }
}