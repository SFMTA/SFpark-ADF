package sfpark.adf.tools.model.data.dto.eventCalendar;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;

public class EventCalendarDTO extends BaseDTO {
    public EventCalendarDTO() {
        super();
    }

    public EventCalendarDTO(String CalendarName, Date EventDate) {
        super();

        this.setCalendarName(CalendarName);
        this.setEventDate(EventDate);
    }

    private EventCalendarDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setCalendarName(resultSet.getString(CALENDAR_NAME));
        this.setEventDate(resultSet.getDate(EVENT_DATE));

    }

    public static final String CALENDAR_NAME = "CALENDAR_NAME";
    public static final String EVENT_DATE = "EVENT_DATE";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(CALENDAR_NAME, EVENT_DATE, CREATED_DT, LAST_UPD_DT,
                      LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(CALENDAR_NAME, EVENT_DATE, LAST_UPD_USER, LAST_UPD_PGM);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    public static List<String> getAttributeListForInsert() {
        return AttributeListForInsert;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static EventCalendarDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new EventCalendarDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String CalendarName;
    private Date EventDate;

    public void setCalendarName(String CalendarName) {
        this.CalendarName = CalendarName;
    }

    public String getCalendarName() {
        return CalendarName;
    }

    public void setEventDate(Date EventDate) {
        this.EventDate = EventDate;
    }

    public Date getEventDate() {
        return EventDate;
    }
}
