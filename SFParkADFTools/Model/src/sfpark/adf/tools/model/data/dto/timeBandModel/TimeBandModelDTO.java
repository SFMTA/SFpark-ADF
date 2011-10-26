package sfpark.adf.tools.model.data.dto.timeBandModel;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class TimeBandModelDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "TIME_BAND_MODEL";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public TimeBandModelDTO() {
        super();
    }

    private TimeBandModelDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setMeterClass(resultSet.getString(METER_CLASS));
        this.setDateType(resultSet.getString(DATE_TYPE));
        this.setTimeBandID(resultSet.getInt(TIME_BAND_ID));
        this.setTimeBandFrom(resultSet.getString(TIME_BAND_FROM));
        this.setTimeBandTo(resultSet.getString(TIME_BAND_TO));

    }

    public static final String METER_CLASS = "METER_CLASS";
    public static final String DATE_TYPE = "DATE_TYPE";
    public static final String TIME_BAND_ID = "TIME_BAND_ID";
    public static final String TIME_BAND_FROM = "TIME_BAND_FROM";
    public static final String TIME_BAND_TO = "TIME_BAND_TO";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(METER_CLASS, DATE_TYPE, TIME_BAND_ID, TIME_BAND_FROM,
                      TIME_BAND_TO, CREATED_DT, LAST_UPD_DT, LAST_UPD_USER,
                      LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(METER_CLASS, DATE_TYPE, TIME_BAND_ID, TIME_BAND_FROM,
                      TIME_BAND_TO, LAST_UPD_USER, LAST_UPD_PGM);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    public static List<String> getAttributeListForInsert() {
        return AttributeListForInsert;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static TimeBandModelDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new TimeBandModelDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    public String getDisplayTimeBandFrom() {
        return getDisplayTimeBand(getTimeBandFrom(), true);
    }

    public String getDisplayTimeBandTo() {
        return getDisplayTimeBand(getTimeBandTo(), false);
    }

    private String getDisplayTimeBand(String value, boolean from) {

        if (StringUtil.isDigitsONLY(value)) {
            return (from) ? TimeDisplayUtil.extractFromTimeForDisplay(value) :
                   TimeDisplayUtil.extractToTimeForDisplay(value);
        }

        return value;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String MeterClass;
    private String DateType;
    private int TimeBandID;
    private String TimeBandFrom;
    private String TimeBandTo;

    public void setMeterClass(String MeterClass) {
        this.MeterClass = MeterClass;
    }

    public String getMeterClass() {
        return MeterClass;
    }

    public void setDateType(String DateType) {
        this.DateType = DateType;
    }

    public String getDateType() {
        return DateType;
    }

    public void setTimeBandID(int TimeBandID) {
        this.TimeBandID = TimeBandID;
    }

    public int getTimeBandID() {
        return TimeBandID;
    }

    public void setTimeBandFrom(String TimeBandFrom) {
        this.TimeBandFrom = TimeBandFrom;
    }

    public String getTimeBandFrom() {
        return TimeBandFrom;
    }

    public void setTimeBandTo(String TimeBandTo) {
        this.TimeBandTo = TimeBandTo;
    }

    public String getTimeBandTo() {
        return TimeBandTo;
    }
}
