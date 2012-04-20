package sfpark.adf.tools.utilities.generic;

import sfpark.adf.tools.utilities.constants.RegularExpression;

public final class TimeDisplayUtil {
    /**
     * To avoid instantiation
     */
    private TimeDisplayUtil() {
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DISPLAY HELPERS

    public static String extractAnyTimeForDisplay(int timeInt) {
        return extractTimeForDisplay(timeInt);
    }

    public static String extractFromTimeForDisplay(String timeStr) {
        return extractTimeForDisplay(timeStr, true);
    }

    public static String extractToTimeForDisplay(String timeStr) {
        return extractTimeForDisplay(timeStr, false);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    public static String extractAnyTimeForUpdate(String timeStr) {
        return extractTimeForUpdate(timeStr, RegularExpression.TIME_REGEX);
    }

    public static String extractFromTimeForUpdate(String timeStr) {
        return extractTimeForUpdate(timeStr,
                                    RegularExpression.FROM_TIME_REGEX);
    }

    public static String extractToTimeForUpdate(String timeStr) {
        return extractTimeForUpdate(timeStr, RegularExpression.TO_TIME_REGEX);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // HUMAN HELPERS

    public static String extractFromTimeForHumans(String timeStr) {
        return extractTimeForHumans(timeStr,
                                    RegularExpression.FROM_TIME_REGEX);
    }

    public static String extractToTimeForHumans(String timeStr) {
        return extractTimeForHumans(timeStr, RegularExpression.TO_TIME_REGEX);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private static String extractTimeForDisplay(String timeStr,
                                                boolean fromTime) {

        if (StringUtil.isBlank(timeStr)) {
            return null;
        }

        int timeInt;

        try {
            timeInt = Integer.parseInt(timeStr);
        } catch (NumberFormatException e) {
            timeInt = (fromTime) ? 0 : 2400;
        }

        return extractTimeForDisplay(timeInt);
    }

    private static String extractTimeForDisplay(int timeInt) {

        if (timeInt < 0) {
            throw new IllegalArgumentException("Time cannot be negative.");
        }

        if (timeInt == 0) {
            return "00:00";
        }

        int hour = timeInt / 100;
        int mins = timeInt % 100;

        String hourStr = (hour < 10) ? ("0" + hour) : ("" + hour);
        String minsStr = (mins < 10) ? ("0" + mins) : ("" + mins);

        return hourStr + ":" + minsStr;
    }

    private static String extractTimeForUpdate(String timeStr,
                                               RegularExpression regularExpression) {

        if (StringUtil.isBlank(timeStr)) {
            return null;
        }

        String regex = regularExpression.getRegEx();

        if (!timeStr.matches(regex)) {
            throw new IllegalArgumentException("Invalid Time String as per the pattern.");
        }

        String timeStrArr[] = timeStr.split(":");

        int hour = Integer.parseInt(timeStrArr[0]);
        int mins = Integer.parseInt(timeStrArr[1]);

        return Integer.toString((hour * 100) + mins);
    }

    private static String extractTimeForHumans(String timeStr,
                                               RegularExpression regularExpression) {
        if (StringUtil.isBlank(timeStr)) {
            return null;
        }

        String regex = regularExpression.getRegEx();

        if (!timeStr.matches(regex)) {
            throw new IllegalArgumentException("Invalid Time String as per the pattern.");
        }

        if (timeStr.equalsIgnoreCase("00:00") ||
            timeStr.equalsIgnoreCase("24:00")) {
            return "12:00 AM";
        }

        String timeStrArr[] = timeStr.split(":");

        int hour = Integer.parseInt(timeStrArr[0]);
        int mins = Integer.parseInt(timeStrArr[1]);

        String ampm = (hour < 12) ? "AM" : "PM";

        if (hour == 0) {
            hour = 12;
        } else if (hour > 12) {
            hour = hour - 12;
        }

        String hourStr = (hour < 10) ? ("0" + hour) : ("" + hour);
        String minsStr = (mins < 10) ? ("0" + mins) : ("" + mins);

        return hourStr + ":" + minsStr + " " + ampm;
    }
}
