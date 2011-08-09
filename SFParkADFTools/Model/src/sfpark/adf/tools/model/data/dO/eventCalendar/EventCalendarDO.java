package sfpark.adf.tools.model.data.dO.eventCalendar;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

@Deprecated
public class EventCalendarDO extends BaseDO {
    public EventCalendarDO() {
        super();
    }

    public EventCalendarDO(String CalendarName, int NumberOfDates) {
        super();
        this.CalendarName = CalendarName;
        this.NumberOfDates = NumberOfDates;
    }

    private EventCalendarDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setCalendarName(resultSet.getString(CALENDAR_NAME));
        this.setNumberOfDates(resultSet.getInt(NUMBER_OF_DATES));

    }

    public static final String CALENDAR_NAME = "CALENDAR_NAME";
    public static final String NUMBER_OF_DATES =
        "COUNT(" + CALENDAR_NAME + ")";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(CALENDAR_NAME, NUMBER_OF_DATES);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static EventCalendarDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new EventCalendarDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String CalendarName;
    private int NumberOfDates;

    public void setCalendarName(String CalendarName) {
        this.CalendarName = CalendarName;
    }

    public String getCalendarName() {
        return CalendarName;
    }

    public void setNumberOfDates(int NumberOfDates) {
        this.NumberOfDates = NumberOfDates;
    }

    public int getNumberOfDates() {
        return NumberOfDates;
    }
}
