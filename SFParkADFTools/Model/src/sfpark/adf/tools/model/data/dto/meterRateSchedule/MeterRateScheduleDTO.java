package sfpark.adf.tools.model.data.dto.meterRateSchedule;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.EffectiveDateBaseDTO;
import sfpark.adf.tools.model.data.helper.MeterRateType;
import sfpark.adf.tools.utilities.constants.WeekDays;
import sfpark.adf.tools.utilities.generic.CurrencyUtil;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class MeterRateScheduleDTO extends EffectiveDateBaseDTO {
    public MeterRateScheduleDTO() {
        super();
    }

    private MeterRateScheduleDTO(MeterRateScheduleDTO DTO) {
        super(DTO);

        this.setParkingSpaceID(DTO.getParkingSpaceID());
        this.setOverride(DTO.isOverride());
        this.setRateType(DTO.getRateType());
        this.setSchedulePriority(DTO.getSchedulePriority());
        this.setFromTime(DTO.getFromTime());
        this.setToTime(DTO.getToTime());
        this.setDaysApplied(DTO.getDaysApplied());
        this.setRate(DTO.getRate());
        this.setRateChangeReference(DTO.getRateChangeReference());
        this.setPSGroupID(DTO.getPSGroupID());
        this.setEventID(DTO.getEventID());
        this.setBlockID(DTO.getBlockID());

        this.setEditableDaysApplied(DTO.isEditableDaysApplied());
        this.setWeekDaysApplied(DTO.getWeekDaysApplied());
    }

    private MeterRateScheduleDTO(ResultSet resultSet,
                                 boolean override) throws SQLException {
        super(resultSet);

        this.setMeterRateSchedID(resultSet.getString(METER_RATE_SCHED_ID));
        this.setParkingSpaceID(resultSet.getString(PARKING_SPACE_ID));
        this.setOverride(override);
        this.setRateType(MeterRateType.valueOf(resultSet.getString(RATE_TYPE)));
        this.setSchedulePriority(resultSet.getInt(SCHED_PRIORITY));
        this.setFromTime(TimeDisplayUtil.extractFromTimeForDisplay(resultSet.getString(FROM_TIME)));
        this.setToTime(TimeDisplayUtil.extractToTimeForDisplay(resultSet.getString(TO_TIME)));
        this.setDaysApplied(resultSet.getString(DAYS_APPLIED));
        this.setRate(resultSet.getFloat(RATE));
        this.setRateChangeReference(resultSet.getString(RATE_CHG_REF));
        this.setPSGroupID(resultSet.getString(PS_GROUP_ID));
        this.setEventID(resultSet.getString(EVENT_ID));
        this.setBlockID(resultSet.getString(BLOCK_ID));

        // Check to see if the Days Applied contains weekdays or not
        if (WeekDays.isWeekDayable(this.getDaysApplied())) {
            this.setEditableDaysApplied(true);
            this.setWeekDaysApplied(StringUtil.convertStringToList(this.getDaysApplied()));
        } else {
            this.setEditableDaysApplied(false);
        }

        // B Rate
        //     ---FROM_TIME = 0
        //     ---TO_TIME = 0
        //     ---DAYS_APPLIED = blank

    }

    private static final String TableName = "METER_RATE_SCHED";

    public static String getTableName() {
        return TableName;
    }

    public static final String METER_RATE_SCHED_ID = "METER_RATE_SCHED_ID";
    public static final String PARKING_SPACE_ID = "PARKING_SPACE_ID";
    public static final String RATE_TYPE = "RATE_TYPE";
    public static final String SCHED_PRIORITY = "SCHED_PRIORITY";
    public static final String FROM_TIME = "FROM_TIME";
    public static final String TO_TIME = "TO_TIME";
    public static final String DAYS_APPLIED = "DAYS_APPLIED";
    public static final String RATE = "RATE";
    public static final String RATE_CHG_REF = "RATE_CHG_REF";
    public static final String PS_GROUP_ID = "PS_GROUP_ID";
    public static final String EVENT_ID = "EVENT_ID";
    public static final String BLOCK_ID = "BLOCK_ID";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(METER_RATE_SCHED_ID, PARKING_SPACE_ID, RATE_TYPE,
                      SCHED_PRIORITY, EFF_FROM_DT, EFF_TO_DT, FROM_TIME,
                      TO_TIME, DAYS_APPLIED, RATE, RATE_CHG_REF, PS_GROUP_ID,
                      EVENT_ID, BLOCK_ID, CREATED_DT, LAST_UPD_DT,
                      LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(PARKING_SPACE_ID, RATE_TYPE, SCHED_PRIORITY, EFF_FROM_DT,
                      EFF_TO_DT, FROM_TIME, TO_TIME, DAYS_APPLIED, RATE,
                      RATE_CHG_REF, PS_GROUP_ID, EVENT_ID, BLOCK_ID,
                      LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(PARKING_SPACE_ID, RATE_TYPE, SCHED_PRIORITY, EFF_FROM_DT,
                      EFF_TO_DT, FROM_TIME, TO_TIME, DAYS_APPLIED, RATE,
                      RATE_CHG_REF, PS_GROUP_ID, EVENT_ID, BLOCK_ID,
                      LAST_UPD_USER, LAST_UPD_PGM);

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

    public static MeterRateScheduleDTO extract(ResultSet resultSet) throws SQLException {
        return extract(resultSet, false);
    }

    public static MeterRateScheduleDTO extract(ResultSet resultSet,
                                               boolean historic) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new MeterRateScheduleDTO(resultSet, historic);
    }

    public static MeterRateScheduleDTO copy(MeterRateScheduleDTO meterRateScheduleDTO) {

        if (meterRateScheduleDTO == null) {
            return null;
        }

        return new MeterRateScheduleDTO(meterRateScheduleDTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(MeterRateScheduleDTO originalDTO) {

        if ((this.getRateType().equals(originalDTO.getRateType())) &&
            (this.getSchedulePriority() ==
             originalDTO.getSchedulePriority()) &&
            SQLDateUtil.areEqual(this.getEffectiveFromDate(),
                                 originalDTO.getEffectiveFromDate()) &&
            SQLDateUtil.areEqual(this.getEffectiveToDate(),
                                 originalDTO.getEffectiveToDate()) &&
            StringUtil.areEqual(this.getFromTime(),
                                originalDTO.getFromTime()) &&
            StringUtil.areEqual(this.getToTime(), originalDTO.getToTime()) &&
            StringUtil.areEqual(this.getActualDaysApplied(),
                                originalDTO.getActualDaysApplied()) &&
            CurrencyUtil.areEqual(this.getRate(), originalDTO.getRate()) &&
            StringUtil.areEqual(this.getRateChangeReference(),
                                originalDTO.getRateChangeReference()) &&
            StringUtil.areEqual(this.getPSGroupID(),
                                originalDTO.getPSGroupID()) &&
            StringUtil.areEqual(this.getEventID(), originalDTO.getEventID()) &&
            StringUtil.areEqual(this.getBlockID(), originalDTO.getBlockID())) {

            return true;
        }

        return false;
    }

    public WhatChanged getWhatChanged(MeterRateScheduleDTO originalDTO) {

        if (this.isSameAs(originalDTO)) {
            return WhatChanged.NOTHING;
        }

        return super.getWhatChanged(originalDTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    private boolean Override;
    private List<String> WeekDaysApplied;
    private boolean EditableDaysApplied;

    public void setOverride(boolean Override) {
        this.Override = Override;
    }

    public boolean isOverride() {
        return Override;
    }

    public void setWeekDaysApplied(List<String> WeekDaysApplied) {
        this.WeekDaysApplied = WeekDaysApplied;
    }

    public List<String> getWeekDaysApplied() {
        return WeekDaysApplied;
    }

    public void setEditableDaysApplied(boolean EditableDaysApplied) {
        this.EditableDaysApplied = EditableDaysApplied;
    }

    public boolean isEditableDaysApplied() {
        return EditableDaysApplied;
    }

    public boolean isNewRecord() {
        return (MeterRateSchedID == null);
    }

    public boolean isEditableFromTime() {
        return getRateType().isRateTypeH();
    }

    public boolean isEditableToTime() {
        return getRateType().isRateTypeH();
    }

    private String getActualDaysApplied() {
        if (isEditableDaysApplied()) {
            return StringUtil.convertListToString(getWeekDaysApplied());
        }

        return getDaysApplied();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR BULK DISPLAY PURPOSES

    private Date MinimumEffectiveFromDateForBulk;

    public void setMinimumEffectiveFromDateForBulk(Date MinimumEffectiveFromDateForBulk) {
        this.MinimumEffectiveFromDateForBulk = MinimumEffectiveFromDateForBulk;
    }

    public Date getMinimumEffectiveFromDateForBulk() {
        return MinimumEffectiveFromDateForBulk;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String MeterRateSchedID;
    private String ParkingSpaceID;
    private MeterRateType RateType;
    private int SchedulePriority;
    private String FromTime;
    private String ToTime;
    private String DaysApplied;
    private float Rate;
    private String RateChangeReference;
    private String PSGroupID;
    private String EventID;
    private String BlockID;

    public void setMeterRateSchedID(String MeterRateSchedID) {
        this.MeterRateSchedID = MeterRateSchedID;
    }

    public String getMeterRateSchedID() {
        return MeterRateSchedID;
    }

    public void setParkingSpaceID(String ParkingSpaceID) {
        this.ParkingSpaceID = ParkingSpaceID;
    }

    public String getParkingSpaceID() {
        return ParkingSpaceID;
    }

    public void setRateType(MeterRateType RateType) {
        this.RateType = RateType;
    }

    public MeterRateType getRateType() {
        return RateType;
    }

    public void setSchedulePriority(int SchedulePriority) {
        this.SchedulePriority = SchedulePriority;
    }

    public int getSchedulePriority() {
        return SchedulePriority;
    }

    public void setFromTime(String FromTime) {
        this.FromTime = FromTime;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setToTime(String ToTime) {
        this.ToTime = ToTime;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setDaysApplied(String DaysApplied) {
        this.DaysApplied = DaysApplied;
    }

    public String getDaysApplied() {
        return DaysApplied;
    }

    public void setRate(float Rate) {
        this.Rate = Rate;
    }

    public float getRate() {
        return Rate;
    }

    public void setRateChangeReference(String RateChangeReference) {
        this.RateChangeReference = RateChangeReference;
    }

    public String getRateChangeReference() {
        return RateChangeReference;
    }

    public void setPSGroupID(String PSGroupID) {
        this.PSGroupID = PSGroupID;
    }

    public String getPSGroupID() {
        return PSGroupID;
    }

    public void setEventID(String EventID) {
        this.EventID = EventID;
    }

    public String getEventID() {
        return EventID;
    }

    public void setBlockID(String BlockID) {
        this.BlockID = BlockID;
    }

    public String getBlockID() {
        return BlockID;
    }
}
