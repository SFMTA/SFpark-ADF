package sfpark.adf.tools.utilities.constants;

import java.util.List;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum WeekDays {
    Mo("Monday"),
    Tu("Tuesday"),
    We("Wednesday"),
    Th("Thursday"),
    Fr("Friday"),
    Sa("Saturday"),
    Su("Sunday");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static boolean isWeekDayable(String string) {

        if (StringUtil.isBlank(string)) {
            return false;
        }

        List<String> weekdays = StringUtil.convertStringToList(string);

        for (String weekday : weekdays) {
            try {
                WeekDays.valueOf(weekday);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String WeekDayName;

    private WeekDays(String WeekDayName) {
        this.WeekDayName = WeekDayName;
    }

    public String getWeekDayName() {
        return WeekDayName;
    }
}
