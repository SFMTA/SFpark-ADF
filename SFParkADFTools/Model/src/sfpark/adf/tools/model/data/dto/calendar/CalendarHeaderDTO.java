package sfpark.adf.tools.model.data.dto.calendar;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.model.data.helper.CalendarType;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class CalendarHeaderDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "CALENDAR_HEADER";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public CalendarHeaderDTO() {
        super();
    }

    private CalendarHeaderDTO(CalendarHeaderDTO DTO) {
        super(DTO);

        this.setCalendarID(DTO.getCalendarID());
        this.setCalendarName(DTO.getCalendarName());
        this.setCalendarType(DTO.getCalendarType());
        this.setStatus(DTO.getStatus());

    }

    private CalendarHeaderDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setCalendarID(resultSet.getString(CALENDAR_ID));
        this.setCalendarName(resultSet.getString(CALENDAR_NAME));
        this.setCalendarType(CalendarType.extractType(resultSet.getString(CALENDAR_TYPE)));
        this.setStatus(resultSet.getString(STATUS));

    }

    public static final String CALENDAR_ID = "CALENDAR_ID";
    public static final String CALENDAR_TYPE = "CALENDAR_TYPE";
    public static final String CALENDAR_NAME = "CALENDAR_NAME";
    public static final String STATUS = "STATUS";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(CALENDAR_ID, CALENDAR_TYPE, CALENDAR_NAME, STATUS,
                      CREATED_DT, LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(CALENDAR_TYPE, CALENDAR_NAME, STATUS, LAST_UPD_USER,
                      LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(CALENDAR_TYPE, CALENDAR_NAME, STATUS, LAST_UPD_USER,
                      LAST_UPD_PGM);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    public static List<String> getAttributeListForInsert() {
        return AttributeListForInsert;
    }

    public static List<String> getAttributeListForUpdate() {
        return AttributeListForUpdate;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static CalendarHeaderDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new CalendarHeaderDTO(resultSet);
    }

    public static CalendarHeaderDTO copy(CalendarHeaderDTO DTO) {

        if (DTO == null) {
            return null;
        }

        return new CalendarHeaderDTO(DTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    public boolean isLocked() {
        return StringUtil.areEqual(getStatus(), "L");
    }

    public int getColumnsCalendarName() {
        return (getMaximumLengthCalendarName() + 1);
    }

    public int getMaximumLengthCalendarName() {
        return 20;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String CalendarID;
    private CalendarType calendarType;
    private String CalendarName;
    private String Status;

    public void setCalendarID(String CalendarID) {
        this.CalendarID = CalendarID;
    }

    public String getCalendarID() {
        return CalendarID;
    }

    public void setCalendarType(CalendarType calendarType) {
        this.calendarType = calendarType;
    }

    public CalendarType getCalendarType() {
        return calendarType;
    }

    public void setCalendarName(String CalendarName) {
        this.CalendarName = CalendarName;
    }

    public String getCalendarName() {
        return CalendarName;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getStatus() {
        return Status;
    }
}
