package sfpark.adf.tools.utilities.generic;

import java.util.ArrayList;
import java.util.List;

public final class StringUtil {
    /**
     * To avoid instantiation
     */
    private StringUtil() {
    }

    public static final String EMPTY_STRING = "";

    public enum SEPARATOR {
        EMPTY(""),
        SPACE_ONLY(" "),
        COMMA_ONLY(","),
        COMMA_WITH_TRAILING_SPACE(", ");

        // ++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++

        private final String separator;

        private SEPARATOR(String separator) {
            this.separator = separator;
        }

        public String getSeparator() {
            return separator;
        }

    }

    /**
     * Checks if the String is empty, null or whitespace only.
     *
     * StringUtil.isBlank(null) = true
     * StringUtil.isBlank("") = true
     * StringUtil.isBlank(" ") = true
     * StringUtil.isBlank("something ") = false
     *
     * @param str to check
     * @return whether the string is null/empty/whitespace
     */
    public static boolean isBlank(String str) {
        int strLen;

        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the String is not empty, not null and not whitespace only.
     *
     * StringUtil.isNotBlank(null) = false
     * StringUtil.isNotBlank("") = false
     * StringUtil.isNotBlank(" ") = false
     * StringUtil.isNotBlank("something ") = true
     *
     * @param str to check
     * @return whether the string is NOT null/empty/whitespace
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Checks if the String is strictly digits only.
     *
     * StringUtil.isDigitsONLY(null) = false
     * StringUtil.isDigitsONLY("") = false
     * StringUtil.isDigitsONLY(" ") = false
     * StringUtil.isDigitsONLY("something ") = false
     * StringUtil.isDigitsONLY("11.67") = false
     * StringUtil.isDigitsONLY("17650") = true
     *
     * @param str to check
     * @return whether the string is strictly digits only
     */
    public static boolean isDigitsONLY(String str) {
        if (isBlank(str)) {
            return false;
        }

        return str.matches("\\d+");
    }

    /**
     * Checks if the String is of the Version type.
     *
     * StringUtil.isVersionString(null) = false
     * StringUtil.isVersionString("") = false
     * StringUtil.isVersionString(" ") = false
     * StringUtil.isVersionString("something ") = false
     * StringUtil.isVersionString("11.67") = true
     * StringUtil.isVersionString("17650") = true
     * StringUtil.isVersionString("1.0.0") = true
     *
     * @param str to check
     * @return whether the string is of the version type
     */
    public static boolean isVersionString(String str) {
        if (isBlank(str)) {
            return false;
        }

        return str.matches("(\\d(\\.\\d)*)+");
    }

    /**
     * Converts the list of strings into a single comma separated string
     *
     * @param strList To be converted
     * @return Single Comma Separated String
     */
    public static String convertListToString(List<String> strList) {
        return convertListToString(strList, SEPARATOR.COMMA_ONLY);
    }

    /**
     * Converts the list of strings into a single user-selected separator
     * separated string
     *
     * @param strList To be converted
     * @param separator To be used
     * @return Single User-Selected Separator Separated String
     */
    public static String convertListToString(List<String> strList,
                                             SEPARATOR separator) {
        StringBuffer stringBuffer = new StringBuffer();

        if (!strList.isEmpty()) {
            int length = strList.size();

            for (int i = 0; i < length - 1; i++) {
                stringBuffer.append(strList.get(i)).append(separator.getSeparator());
            }

            stringBuffer.append(strList.get(length - 1));
        }

        return stringBuffer.toString();
    }

    /**
     * Converts a single comma separated string into a list of strings
     *
     * @param string To be converted
     * @return List of Strings
     */
    public static List<String> convertStringToList(String string) {
        return convertStringToList(string, SEPARATOR.COMMA_ONLY);
    }

    /**
     * Converts a single user-selected separator separated string into a list
     * of strings
     *
     * @param string To be converted
     * @param separator To be used
     * @return List of Strings
     */
    public static List<String> convertStringToList(String string,
                                                   SEPARATOR separator) {
        List<String> strList = new ArrayList<String>();

        if (isNotBlank(string)) {
            String[] stringSplit = string.split(separator.getSeparator());

            for (String str : stringSplit) {
                strList.add(str);
            }
        }

        return strList;
    }

    /**
     * Concatenates the strings into a single string separated by a single space
     * character
     *
     * @param strings To be concatenated
     * @return Concatenated String
     */
    public static String concatenate(String... strings) {
        return concatenate(SEPARATOR.SPACE_ONLY, strings);
    }

    /**
     * Concatenates the strings into a single string separated by the user-selected
     * character
     *
     * @param separator To be used
     * @param strings To be concatenated
     * @return Concatenated String
     */
    public static String concatenate(SEPARATOR separator, String... strings) {
        StringBuffer finalString = new StringBuffer();

        int numOfStrings = strings.length;

        for (int i = 0; i < numOfStrings - 1; i++) {
            if (isNotBlank(strings[i])) {
                finalString.append(strings[i]);
                finalString.append(separator.getSeparator());
            }
        }

        if (isNotBlank(strings[numOfStrings - 1])) {
            finalString.append(strings[numOfStrings - 1]);
        }

        return finalString.toString().trim();
    }

    /**
     * Generates a single repeating comma separated string
     *
     * @param toRepeat To be repeated
     * @param times To be repeated
     * @return Repeating String
     */
    public static String generateStringWithRepetition(String toRepeat,
                                                      int times) {
        return generateStringWithRepetition(toRepeat, times,
                                            SEPARATOR.COMMA_ONLY);
    }

    /**
     * Generates a single repeating user-selected separator separated string
     *
     * @param toRepeat To be repeated
     * @param times To be repeated
     * @param separator To be used
     * @return Repeating String
     */
    public static String generateStringWithRepetition(String toRepeat,
                                                      int times,
                                                      SEPARATOR separator) {

        if (times < 1) {
            throw new IllegalArgumentException("The string should appear at least once.");
        }

        if (isBlank(toRepeat)) {
            throw new IllegalArgumentException("Null/Empty strings are not allowed.");
        }

        StringBuffer finalString = new StringBuffer();

        for (int i = 0; i < times - 1; i++) {
            finalString.append(toRepeat);
            finalString.append(separator.getSeparator());
        }

        finalString.append(toRepeat);

        return finalString.toString();
    }

    /**
     * Concatenates the string to every string in the list
     *
     * @param toModify
     * @param toConcatenate
     * @return
     */
    public static List<String> concatenateToAllStringsInList(List<String> toModify,
                                                             String toConcatenate) {
        return concatenateToAllStringsInList(toModify, toConcatenate,
                                             SEPARATOR.EMPTY);
    }

    /**
     * Concatenates the string to every string in the list separated by the
     * user-selected separator
     *
     * @param toModify
     * @param toConcatenate
     * @param separator
     * @return
     */
    public static List<String> concatenateToAllStringsInList(List<String> toModify,
                                                             String toConcatenate,
                                                             SEPARATOR separator) {

        List<String> modifiedList = new ArrayList<String>();

        for (String temp : toModify) {
            modifiedList.add(concatenate(separator, temp, toConcatenate));
        }

        return modifiedList;
    }

    public static boolean areEqual(String str1, String str2) {
        if (isBlank(str1) && isBlank(str2)) {
            return true;
        }

        if (isBlank(str1) || isBlank(str2)) {
            return false;
        }

        if (str1.trim().equalsIgnoreCase(str2.trim())) {
            return true;
        }

        return false;
    }
}
