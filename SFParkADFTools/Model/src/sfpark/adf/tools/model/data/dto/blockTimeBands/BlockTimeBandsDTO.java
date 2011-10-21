package sfpark.adf.tools.model.data.dto.blockTimeBands;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.timeBandModel.TimeBandModelDO;
import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.utilities.generic.CurrencyUtil;
import sfpark.adf.tools.utilities.generic.ObjectUtil;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class BlockTimeBandsDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "BLOCK_TIME_BANDS";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public BlockTimeBandsDTO() {
        super();
    }

    private BlockTimeBandsDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setBlockTimeBandID(resultSet.getString(BLOCK_TIME_BAND_ID));
        this.setBlockID(resultSet.getString(BLOCK_ID));
        this.setMeterClass(resultSet.getString(METER_CLASS));
        this.setDateType(resultSet.getString(DATE_TYPE));
        this.setTimeBandID(resultSet.getInt(TIME_BAND_ID));
        this.setTimeBandFrom(resultSet.getString(TIME_BAND_FROM));
        this.setTimeBandTo(resultSet.getString(TIME_BAND_TO));
        this.setFromTime(resultSet.getInt(FROM_TIME));
        this.setToTime(resultSet.getInt(TO_TIME));
        this.setCurrentRate(resultSet.getFloat(CURRENT_RATE));
        this.setLastRateChangeDate(resultSet.getDate(LAST_RATE_CHG_DT));

    }

    private BlockTimeBandsDTO(TimeBandModelDO timeBandModelDO) {
        super();

        this.setMeterClass(timeBandModelDO.getMeterClass());
        this.setDateType(timeBandModelDO.getDateType());
        this.setTimeBandID(timeBandModelDO.getTimeBandID());
        this.setTimeBandFrom(timeBandModelDO.getTimeBandFrom());
        this.setTimeBandTo(timeBandModelDO.getTimeBandTo());

    }

    public static final String BLOCK_TIME_BAND_ID = "BLOCK_TIME_BAND_ID";
    public static final String BLOCK_ID = "BLOCK_ID";
    public static final String METER_CLASS = "METER_CLASS";
    public static final String DATE_TYPE = "DATE_TYPE";
    public static final String TIME_BAND_ID = "TIME_BAND_ID";
    public static final String TIME_BAND_FROM = "TIME_BAND_FROM";
    public static final String TIME_BAND_TO = "TIME_BAND_TO";
    public static final String FROM_TIME = "FROM_TIME";
    public static final String TO_TIME = "TO_TIME";
    public static final String CURRENT_RATE = "CURRENT_RATE";
    public static final String LAST_RATE_CHG_DT = "LAST_RATE_CHG_DT";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(BLOCK_TIME_BAND_ID, BLOCK_ID, METER_CLASS, DATE_TYPE,
                      TIME_BAND_ID, TIME_BAND_FROM, TIME_BAND_TO, FROM_TIME,
                      TO_TIME, CURRENT_RATE, LAST_RATE_CHG_DT, CREATED_DT,
                      LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(BLOCK_ID, METER_CLASS, DATE_TYPE, TIME_BAND_ID,
                      TIME_BAND_FROM, TIME_BAND_TO, FROM_TIME, TO_TIME,
                      CURRENT_RATE, LAST_RATE_CHG_DT, LAST_UPD_USER,
                      LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(CURRENT_RATE, LAST_RATE_CHG_DT, LAST_UPD_USER,
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

    public static BlockTimeBandsDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new BlockTimeBandsDTO(resultSet);
    }

    public static BlockTimeBandsDTO extract(TimeBandModelDO timeBandModelDO) {

        if (timeBandModelDO == null) {
            return null;
        }

        return new BlockTimeBandsDTO(timeBandModelDO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(BlockTimeBandsDTO originalDTO) {

        if (StringUtil.areEqual(this.getBlockID(), originalDTO.getBlockID()) &&
            StringUtil.areEqual(this.getMeterClass(),
                                originalDTO.getMeterClass()) &&
            StringUtil.areEqual(this.getDateType(),
                                originalDTO.getDateType()) &&
            (this.getTimeBandID() == originalDTO.getTimeBandID()) &&
            StringUtil.areEqual(this.getTimeBandFrom(),
                                originalDTO.getTimeBandFrom()) &&
            StringUtil.areEqual(this.getTimeBandTo(),
                                originalDTO.getTimeBandTo()) &&
            (ObjectUtil.getNullSafe(this.getFromTime()) ==
             ObjectUtil.getNullSafe(originalDTO.getFromTime())) &&
            (ObjectUtil.getNullSafe(this.getToTime()) ==
             ObjectUtil.getNullSafe(originalDTO.getToTime())) &&
            CurrencyUtil.areEqual(this.getCurrentRate(),
                                  originalDTO.getCurrentRate()) &&
            SQLDateUtil.areEqual(this.getLastRateChangeDate(),
                                 originalDTO.getLastRateChangeDate())) {

            return true;
        }

        return false;
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

    public String getDisplayFromTime() {
        return TimeDisplayUtil.extractAnyTimeForDisplay(getFromTime());
    }

    public String getDisplayToTime() {
        return TimeDisplayUtil.extractAnyTimeForDisplay(getToTime());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String BlockTimeBandID;
    private String BlockID;
    private String MeterClass;
    private String DateType;
    private int TimeBandID;
    private String TimeBandFrom;
    private String TimeBandTo;
    private int FromTime;
    private int ToTime;
    private float CurrentRate;
    private Date LastRateChangeDate;

    public void setBlockTimeBandID(String BlockTimeBandID) {
        this.BlockTimeBandID = BlockTimeBandID;
    }

    public String getBlockTimeBandID() {
        return BlockTimeBandID;
    }

    public void setBlockID(String BlockID) {
        this.BlockID = BlockID;
    }

    public String getBlockID() {
        return BlockID;
    }

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

    public void setFromTime(int FromTime) {
        this.FromTime = FromTime;
    }

    public int getFromTime() {
        return FromTime;
    }

    public void setToTime(int ToTime) {
        this.ToTime = ToTime;
    }

    public int getToTime() {
        return ToTime;
    }

    public void setCurrentRate(float CurrentRate) {
        this.CurrentRate = CurrentRate;
    }

    public float getCurrentRate() {
        return CurrentRate;
    }

    public void setLastRateChangeDate(Date LastRateChangeDate) {
        this.LastRateChangeDate = LastRateChangeDate;
    }

    public Date getLastRateChangeDate() {
        return LastRateChangeDate;
    }
}
