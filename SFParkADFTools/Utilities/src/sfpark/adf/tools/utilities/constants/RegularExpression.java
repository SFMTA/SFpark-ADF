package sfpark.adf.tools.utilities.constants;

public enum RegularExpression {

    SINGLE_DIGIT_REGEX("([0-9])"),
    DIGITS_REGEX("[0-9]+"),
    POSITIVE_INTEGER("[0-9]+([.][0-9]{1,2})?"),

    POST_ID_REGEX("([0-9]{3}-[0-9]{5})"),

    US_PHONE_NUMBER("\\(([2-9][0-9][0-9])\\) ([2-9][0-9]{2})-([0-9]{4})"),
    WEB_SITE_URL("((http|https)://)(www.)?([\\w\\-]+\\.)+([a-zA-Z]{2,4})"),
    
    TIME_REGEX("(([0-1]*[0-9]|[2][0-3]):([0-5][0-9]))|(([2][4]):([0][0]))"),
    FROM_TIME_REGEX("([0-1]*[0-9]|[2][0-3]):([0-5][0-9])"),
    TO_TIME_REGEX("(([0]|[0][0]):([0-5][1-9]|[1-5][0-9]))|(([1-9]|[0-1][1-9]|[2][0-3]):([0-5][0-9]))|(([2][4]):([0][0]))"),

    LONGITUDE_REGEX("(^\\+?1[0-7]\\d(\\.\\d+)?$)|(^\\+?([1-9])?\\d(\\.\\d+)?$)|(^-180$)|(^-1[1-7]\\d(\\.\\d+)?$)|(^-[1-9]\\d(\\.\\d+)?$)|(^\\-\\d(\\.\\d+)?$)"),
    LATITUDE_REGEX("(^\\+?([1-8])?\\d(\\.\\d+)?$)|(^-90$)|(^-(([1-8])?\\d(\\.\\d+)?$))");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String RegEx;

    private RegularExpression(String regEx) {
        this.RegEx = regEx;
    }

    public String getRegEx() {
        return RegEx;
    }

}
