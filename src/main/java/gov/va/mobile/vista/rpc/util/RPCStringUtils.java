package gov.va.mobile.vista.rpc.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility methods for preparing and parsing request and response {@link String} values for VistA RPC calls.
 *
 * @author cody@apothesource.com
 * @since 1.0
 */
@UtilityClass
@SuppressWarnings({"PMD.GodClass", "PMD.TooManyMethods", "PMD.ShortVariable", "PMD.CyclomaticComplexity"})
public final class RPCStringUtils {

    public static final Charset CP_1252 = Charset.forName("Cp1252");

    public static final String CR = "\n";
    public static final String CRLF = "\r\n";
    public static final String CARET = "^";
    public static final String STICK = "|";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String COMMA = ",";
    public static final String PERIOD = ".";
    public static final String SLASH = "/";
    public static final String SPACE = " ";
    public static final String EQUALS = "=";
    public static final String AMPERSAND = "&";
    public static final String ATSIGN = "@";
    public static final String DASH = "-";
    public static final String TILDE = "~";
    public static final String RECORD_SECTION_FIELD_SEPARATOR = "&#94;";
    public static final String VERSION = "V";

    private static final int TOKEN_CHUNK_SIZE = 200;

    private static final Pattern NON_PRINT_CHARS = Pattern.compile("[\\p{C}]");
    private static final Pattern CONTROL_CHARS = Pattern.compile("[\\p{Cntrl}&&[^\\r\\n\\t]]");
    private static final Pattern EOL_CHAR_GROUP = Pattern.compile("[\r\n]");
    private static final Pattern MULTI_EOL_CHARS = Pattern.compile("\r\n");

    @SuppressWarnings("checkstyle:IllegalTokenText")
    private static final Pattern REMOVE_CTRL_CHARS_COMPILED_PATTERN = Pattern.compile("[^\\u0009\\u000A\\u000B\\u0020-\\u00BE]");

    /**
     * Remove all non-printable characters from the provided {@link String}.
     *
     * @param str
     *         the {@link String} to remove non-printable characters from
     *
     * @return the cleansed {@link String}
     */
    public static String removeNonPrintable(final String str) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        return NON_PRINT_CHARS.matcher(str).replaceAll(StringUtils.EMPTY);
    }

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
     * Format the incoming {@link String} by pre-pending its length.
     *
     * @param str
     *         the {@link String} to format
     *
     * @return the formatted {@link String}
     */
    public static String pack(final String str) {
        final String safeStr = StringUtils.defaultString(str);
        return (char) safeStr.length() + safeStr;
    }

    public static String piece(final String str, final int index) {
        return piece(str, "^", index);
    }

    /**
     * Returns a particular (at {@code index} location) substring piece of the provided {@link String}, separating by
     * the provided delimiter.
     * <p>
     * If the provided {@link String} is {@code null} or the index is less than {@code 1}, an "empty" string is
     * returned.
     * <p>
     * If the provided index is beyond the number of parsed pieces, an "empty" string is returned.
     *
     * @param str
     *         the {@link String} to extract a substring piece from
     * @param delimiter
     *         the delimiter used to separate pieces
     * @param index
     *         the index of the piece to return
     *
     * @return the extracted piece of the provided {@link String}
     */
    public static String piece(final String str, final String delimiter, final int index) {
        if ((str == null) || (index < 1)) {
            return StringUtils.EMPTY;
        }
        final String[] fields = split(str, delimiter);
        return (index > fields.length) ? StringUtils.EMPTY : fields[index - 1];
    }

    public static String[] split(final String str, final char delimiter) {
        return split(str, String.valueOf(delimiter));
    }

    /**
     * Splits the provided {@link String} with the provided regex delimiter. The regex delimiter is formatted so that
     * regex meta characters are properly escaped before splitting the {@link String}.
     *
     * @param str
     *         the {@link String} to split
     * @param delimiter
     *         the delimiter to use when splitting
     *
     * @return the array of {@link String} objects split out
     *
     * @see #escapeRegExMetaChars(String)
     */
    public static String[] split(final String str, final String delimiter) {
        final String safeStr = StringUtils.defaultString(str);
        return safeStr.split(escapeRegExMetaChars(delimiter), -1);
    }

    /**
     * Escapes regex meta characters in the provided {@link String}, using {@link #isRegExMetaChar(char)}.
     *
     * @param str
     *         the {@link String} to escape regex meta characters
     *
     * @return the formatted {@link String}
     *
     * @see #isRegExMetaChar(char)
     */
    private static String escapeRegExMetaChars(final String str) {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < str.length(); ++i) {
            if (isRegExMetaChar(str.charAt(i))) {
                builder.append('\\');
            }
            builder.append(str.charAt(i));
        }

        return builder.toString();
    }

    /**
     * Determines if the provided character is a regex meta character. The following characters are regex meta
     * characters:
     * <ul>
     *     <li>$</li>
     *     <li>*</li>
     *     <li>+</li>
     *     <li>-</li>
     *     <li>.</li>
     *     <li>?</li>
     *     <li>^</li>
     *     <li>|</li>
     * </ul>
     *
     * @param metaChar
     *         the character to determine is a regex meta character
     *
     * @return {@code true} if the character is a regex meta character; {@code false} otherwise
     */
    private static boolean isRegExMetaChar(final char metaChar) {
        switch (metaChar) {
            case '$':
            case '*':
            case '+':
            case '-':
            case '.':
            case '?':
            case '^':
            case '|':
                return true;
            default:
                return false;
        }
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
     * Converts a byte array to a {@link String}, using {@link StandardCharsets#UTF_8}.
     *
     * @param byteBuffer
     *         the byte array to convert
     * @param bytesRead
     *         the number of bytes in the array to convert to {@link String}
     *
     * @return the {@link String}
     */
    public String toString(final byte[] byteBuffer, final int bytesRead) {
        return toString(byteBuffer, bytesRead, StandardCharsets.UTF_8);
    }

    /**
     * Converts a byte array to a {@link String}, using the provided {@link Charset}.
     *
     * @param byteBuffer
     *         the byte array to convert
     * @param bytesRead
     *         the number of bytes in the array to convert to {@link String}
     * @param charset
     *         the {@link Charset} to use when converting the {@link String}
     *
     * @return the {@link String}
     */
    public String toString(final byte[] byteBuffer, final int bytesRead, final Charset charset) {
        return new String(byteBuffer, 0, bytesRead, charset);
    }

    @SuppressWarnings("PMD.UseVarargs")
    public static String[] trimArray(final String[] strings) {
        int i;
        for (i = strings.length - 1; i >= 0; i--) {
            if (StringUtils.isNotEmpty(strings[i])) {
                break;
            }
        }

        final String[] result = new String[i + 1];
        final int len = i + 1;
        System.arraycopy(strings, 0, result, 0, len);

        return result;
    }

    public static String removeNonNumericChars(final String str) {
        if (str == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) >= '0') && (str.charAt(i) <= '9')) {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static String filteredString(final String str) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            final int asciiChar = asciiAt(str, i);
            if (asciiChar == 9) {
                result.append("        ");
            } else if (((asciiChar >= 32) && (asciiChar <= 127)) || ((asciiChar >= 161) && (asciiChar <= 255))) {
                result.append(str.charAt(i));
            } else if ((asciiChar >= 128) && (asciiChar <= 159)) {
                result.append('?');
            } else if ((asciiChar == 10) || (asciiChar == 13) || (asciiChar == 160)) {
                result.append(' ');
            }
        }
        return result.toString();
    }

    public static int asciiAt(final String str, final int index) {
        return str.charAt(index);
    }

    /**
     * Return a string with all invalid XML 1.0 characters (per W3C standard: <a
     * href="http://www.w3.org/TR/REC-xml/#charsets">W3C Charsets</a>) removed
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static String stripInvalidXmlCharacters(final String str) {
        if (StringUtils.isEmpty(str)) {
            return StringUtils.EMPTY;
        }

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            final char current = str.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))) {
                builder.append(current);
            }
        }
        return builder.toString();
    }

    /**
     * Check the response string and standardized EOF characters. The response string may have come from different
     * environments, this method uses a pattern to replace a variety of EOF possibilities with a standard EOF that
     * subsequent parsing code can use to determine line separation.
     *
     * @param response
     *         - an RPC response string
     *
     * @return a new string with end of line characters (generally '\n' or '\r' or '\r\n') replaced with a linux
     *         standard '\r\n'
     */
    public static String replaceEOLCharacters(final String response) {
        return replaceEOLCharacters(response, CR);

        // From VIA:
//        return EOL_CHAR_GROUP.matcher(response).replaceAll(CRLF);
    }

    public static String replaceEOLCharacters(final String response, final String eol) {
        if (StringUtils.isEmpty(response)) {
            return StringUtils.EMPTY;
        }
        // Replace \r, \n, and \r\n with \n
        final String replaceMultiEolChars = MULTI_EOL_CHARS.matcher(response).replaceAll(CR);
        return EOL_CHAR_GROUP.matcher(replaceMultiEolChars).replaceAll(eol);

        // From VIA:
//        return EOL_CHAR_GROUP.matcher(response).replaceAll(CRLF);
    }

    public static String[] getLines(final String response) {
        final String cleanResponse = replaceEOLCharacters(response);
        // Split on \n
        return split(cleanResponse, CR);

        // From VIA:
//        return split(cleanResponse, CRLF);
    }

    public static String adjustForNameSearch(final String target) {
        if (StringUtils.isEmpty(target)) {
            return StringUtils.EMPTY;
        }
        final int lth = target.length();
        char c = target.charAt(lth - 1);
        final int asciiCode = (byte) c - 1;
        c = (char) asciiCode;
        String rtn = target.substring(0, lth - 1);
        rtn = rtn + c + '~';
        return rtn;
    }

    public static String adjustForNumericSearch(final String target) {
        if (!StringUtils.isNumeric(target)) {
            return target;
        }
        final long iTarget = Long.parseLong(target);
        return String.valueOf(iTarget - 1);
    }

    public static String getDirectionParam(final String direction) {
        if (StringUtils.isEmpty(direction)) {
            return "1";
        }
        if (!"1".equals(direction) && !"-1".equals(direction)) {
            throw new IllegalArgumentException("Invalid direction.  Must be 1 or -1.");
        }
        return direction;
    }

    public static String responseOrOk(final String response) {
        if (StringUtils.isNotEmpty(response)) {
            return response;
        }
        return "OK";
    }

    public static String errMsgOrOK(final String response) {
        final String[] flds = split(response, CARET);
        if (!"1".equals(flds[0])) {
            return flds[1];
        }
        return "OK";
    }

    /**
     * @param response
     *
     * @return "OK" if first part of caret-separated response string is zero, otherwise returns the second part of
     *         response string as error message
     */
    public static String errMsgOrZero(final String response) {
        final String[] flds = split(response, CARET);
        if (!"0".equals(flds[0])) {
            return flds[1];
        }
        return "OK";
    }

    public static String removeCtlChars(final String s) {
        if (StringUtils.isBlank(s)) {
            return StringUtils.EMPTY;
        }

        /*
         * From previous VIA code (only wanted the following chars):
         *    if (c == 9 || c == 10 || c == 13 || (c > 31 && c < 127))
         *
         * https://en.wikipedia.org/wiki/List_of_Unicode_characters
         * c==9:  evaluates to u0028
         * c==10: evaluates to u0029
         * c==13: evaluates to u002C
         * c==32 to c==126: evaluates to u003F to u00BE
         *
         * Additionally added:
         *  u0009 Horizontal tab HT
         *  u000A Line feed LF
         *  u000B Vertical tab VT
         */
        return REMOVE_CTRL_CHARS_COMPILED_PATTERN.matcher(s).replaceAll(StringUtils.EMPTY);
    }
}