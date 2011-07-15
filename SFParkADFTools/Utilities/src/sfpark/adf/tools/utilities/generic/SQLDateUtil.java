package sfpark.adf.tools.utilities.generic;

import java.sql.Date;

import java.util.Calendar;
import java.util.Locale;

public final class SQLDateUtil {
    /**
     * To avoid instantiation
     */
    private SQLDateUtil() {
    }

    private static final int MAXIMUM_YEAR = 2200;
    private static final int MAXIMUM_MONTH = Calendar.DECEMBER;
    private static final int MAXIMUM_DATE = 31;

    private static final Locale LOCALE = Locale.US;

    public static boolean areEqual(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }

        if (date1 == null || date2 == null) {
            return false;
        }

        if (date1.toString().equalsIgnoreCase(date2.toString())) {
            return true;
        }

        return false;
    }

    public static Date getTodaysDate() {
        return new Date(getTodaysDateCalendarInstance().getTimeInMillis());
    }

    public static Date getYesterdaysDate() {
        Calendar calendar = getTodaysDateCalendarInstance();
        calendar.add(Calendar.DATE, -1);

        return new Date(calendar.getTimeInMillis());
    }

    public static Date getTomorrowsDate() {
        Calendar calendar = getTodaysDateCalendarInstance();
        calendar.add(Calendar.DATE, 1);

        return new Date(calendar.getTimeInMillis());
    }

    public static Date getPreviousDateFor(Date forDate) {
        Calendar calendar = getProvidedDateCalendarInstance(forDate);
        calendar.add(Calendar.DATE, -1);

        return new Date(calendar.getTimeInMillis());
    }

    public static Date getNextDateFor(Date forDate) {
        Calendar calendar = getProvidedDateCalendarInstance(forDate);
        calendar.add(Calendar.DATE, 1);

        return new Date(calendar.getTimeInMillis());
    }

    public static Date getMaximumDate() {
        Calendar calendar = Calendar.getInstance(LOCALE);

        calendar.set(MAXIMUM_YEAR, MAXIMUM_MONTH, MAXIMUM_DATE, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Date(calendar.getTimeInMillis());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private static Calendar getTodaysDateCalendarInstance() {
        Calendar calendar = Calendar.getInstance(LOCALE);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        calendar.set(year, month, date, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    private static Calendar getProvidedDateCalendarInstance(Date date) {
        Calendar calendar = Calendar.getInstance(LOCALE);

        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }
}
