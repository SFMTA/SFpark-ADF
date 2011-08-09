package sfpark.adf.tools.model.data.dto.calendar;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;

public class CalendarDetailDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "CALENDAR_DETAIL";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public CalendarDetailDTO() {
        super();
    }

    public CalendarDetailDTO(String CalendarID, Date DateDT) {
        super();

        this.CalendarID = CalendarID;
        this.DateDT = DateDT;
    }

    private CalendarDetailDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setCalendarID(resultSet.getString(CALENDAR_ID));
        this.setDateDT(resultSet.getDate(DATE_DT));

    }

    public static final String CALENDAR_ID = "CALENDAR_ID";
    public static final String DATE_DT = "DATE_DT";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(CALENDAR_ID, DATE_DT, CREATED_DT, LAST_UPD_DT,
                      LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(CALENDAR_ID, DATE_DT, LAST_UPD_USER, LAST_UPD_PGM);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    public static List<String> getAttributeListForInsert() {
        return AttributeListForInsert;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static CalendarDetailDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new CalendarDetailDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String CalendarID;
    private Date DateDT;

    public void setCalendarID(String CalendarID) {
        this.CalendarID = CalendarID;
    }

    public String getCalendarID() {
        return CalendarID;
    }

    public void setDateDT(Date DateDT) {
        this.DateDT = DateDT;
    }

    public Date getDateDT() {
        return DateDT;
    }
}
