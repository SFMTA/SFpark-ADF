package sfpark.adf.tools.model.data.dto.meterOPSchedule;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.EffectiveDateBaseDTO;
import sfpark.adf.tools.model.data.helper.MeterScheduleType;
import sfpark.adf.tools.utilities.constants.WeekDays;
import sfpark.adf.tools.utilities.generic.ObjectUtil;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class MeterOPScheduleDTO extends EffectiveDateBaseDTO {
    public MeterOPScheduleDTO() {
        super();
    }

    private MeterOPScheduleDTO(MeterOPScheduleDTO DTO) {
        super(DTO);

        this.setParkingSpaceID(DTO.getParkingSpaceID());
        this.setOverride(DTO.isOverride());
        this.setScheduleType(DTO.getScheduleType());
        this.setSchedulePriority(DTO.getSchedulePriority());
        this.setColorRuleApplied(DTO.getColorRuleApplied());
        this.setAlternateAddlDesc(DTO.getAlternateAddlDesc());
        this.setFromTime(DTO.getFromTime());
        this.setToTime(DTO.getToTime());
        this.setDaysApplied(DTO.getDaysApplied());
        this.setTimeLimit(DTO.getTimeLimit());
        this.setPrePaymentTime(DTO.getPrePaymentTime());
        this.setEditableDaysApplied(DTO.isEditableDaysApplied());
        this.setWeekDaysApplied(DTO.getWeekDaysApplied());
        this.setEditableColorRuleApplied(DTO.isEditableColorRuleApplied());
    }

    private MeterOPScheduleDTO(ResultSet resultSet,
                               boolean override) throws SQLException {
        super(resultSet);

        this.setMeterOPSchedID(resultSet.getString(METER_OP_SCHED_ID));
        this.setParkingSpaceID(resultSet.getString(PARKING_SPACE_ID));
        this.setOverride(override);
        this.setScheduleType(MeterScheduleType.valueOf(resultSet.getString(SCHED_TYPE)));
        this.setSchedulePriority(resultSet.getInt(SCHED_PRIORITY));
        this.setColorRuleApplied(resultSet.getString(COLOR_RULE_APPLIED));
        this.setAlternateAddlDesc(resultSet.getString(ALT_ADDL_DESC));
        this.setFromTime(TimeDisplayUtil.extractFromTimeForDisplay(resultSet.getString(FROM_TIME)));
        this.setToTime(TimeDisplayUtil.extractToTimeForDisplay(resultSet.getString(TO_TIME)));
        this.setDaysApplied(resultSet.getString(DAYS_APPLIED));
        this.setTimeLimit(resultSet.getInt(TIME_LIMIT));
        this.setPrePaymentTime(TimeDisplayUtil.extractFromTimeForDisplay(resultSet.getString(PREPAYMENT_TIME)));

        // Check to see if the Days Applied contains weekdays or not
        if (WeekDays.isWeekDayable(this.getDaysApplied())) {
            this.setEditableDaysApplied(true);
            this.setWeekDaysApplied(StringUtil.convertStringToList(this.getDaysApplied()));
        } else {
            this.setEditableDaysApplied(false);
        }

        switch (this.getScheduleType()) {

        case TOW:
            {
                // TOW Schedule
                //     ---Color is always NULL
                //     ---If ALT_ADDL_DESC == NULL
                //           ---Weekdays
                //     ---If ALT_ADDL_DESC != NULL
                //           ---Text
                //     ---TIME_LIMIT is read-only
                //     ---PREPAYMENT_TIME is read-only
                this.setEditableColorRuleApplied(false);
            }
            break;

        case OP:
            {
                // OP Schedule
                //    ---Color is always read-only
                //    ---Alt Additional Description is always NULL
                //    --- Days Applied is always Weekdays
                this.setEditableColorRuleApplied(false);
            }
            break;

        case ALT:
            {
                // ALT Schedule
                //     ---If ALT_ADDL_DESC == NULL
                //           ---Color is editable
                //     ---If ALT_ADDL_DESC != NULL
                //           ---Color is read-only
                //     ---PREPAYMENT_TIME is read-only
                this.setEditableColorRuleApplied(StringUtil.isBlank(this.getAlternateAddlDesc()));
            }
            break;
        }

    }

    private static final String TableName = "METER_OP_SCHED";

    public static String getTableName() {
        return TableName;
    }

    public static final String METER_OP_SCHED_ID = "METER_OP_SCHED_ID";
    public static final String PARKING_SPACE_ID = "PARKING_SPACE_ID";
    public static final String SCHED_TYPE = "SCHED_TYPE";
    public static final String SCHED_PRIORITY = "SCHED_PRIORITY";
    public static final String COLOR_RULE_APPLIED = "COLOR_RULE_APPLIED";
    public static final String ALT_ADDL_DESC = "ALT_ADDL_DESC";
    public static final String FROM_TIME = "FROM_TIME";
    public static final String TO_TIME = "TO_TIME";
    public static final String DAYS_APPLIED = "DAYS_APPLIED";
    public static final String TIME_LIMIT = "TIME_LIMIT";
    public static final String PREPAYMENT_TIME = "PREPAYMENT_TIME";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(METER_OP_SCHED_ID, PARKING_SPACE_ID, SCHED_TYPE,
                      SCHED_PRIORITY, EFF_FROM_DT, EFF_TO_DT,
                      COLOR_RULE_APPLIED, ALT_ADDL_DESC, FROM_TIME, TO_TIME,
                      DAYS_APPLIED, TIME_LIMIT, PREPAYMENT_TIME, CREATED_DT,
                      LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(PARKING_SPACE_ID, SCHED_TYPE, SCHED_PRIORITY,
                      EFF_FROM_DT, EFF_TO_DT, COLOR_RULE_APPLIED,
                      ALT_ADDL_DESC, FROM_TIME, TO_TIME, DAYS_APPLIED,
                      TIME_LIMIT, PREPAYMENT_TIME, LAST_UPD_USER,
                      LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(PARKING_SPACE_ID, SCHED_TYPE, SCHED_PRIORITY,
                      EFF_FROM_DT, EFF_TO_DT, COLOR_RULE_APPLIED,
                      ALT_ADDL_DESC, FROM_TIME, TO_TIME, DAYS_APPLIED,
                      TIME_LIMIT, PREPAYMENT_TIME, LAST_UPD_USER,
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

    public static MeterOPScheduleDTO extract(ResultSet resultSet) throws SQLException {
        return extract(resultSet, false);
    }

    public static MeterOPScheduleDTO extract(ResultSet resultSet,
                                             boolean historic) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new MeterOPScheduleDTO(resultSet, historic);
    }

    public static MeterOPScheduleDTO copy(MeterOPScheduleDTO DTO) {

        if (DTO == null) {
            return null;
        }

        return new MeterOPScheduleDTO(DTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(MeterOPScheduleDTO originalDTO) {

        if ((this.getScheduleType().equals(originalDTO.getScheduleType())) &&
            (this.getSchedulePriority() ==
             originalDTO.getSchedulePriority()) &&
            SQLDateUtil.areEqual(this.getEffectiveFromDate(),
                                 originalDTO.getEffectiveFromDate()) &&
            SQLDateUtil.areEqual(this.getEffectiveToDate(),
                                 originalDTO.getEffectiveToDate()) &&
            StringUtil.areEqual(this.getColorRuleApplied(),
                                originalDTO.getColorRuleApplied()) &&
            StringUtil.areEqual(this.getAlternateAddlDesc(),
                                originalDTO.getAlternateAddlDesc()) &&
            StringUtil.areEqual(this.getFromTime(),
                                originalDTO.getFromTime()) &&
            StringUtil.areEqual(this.getToTime(), originalDTO.getToTime()) &&
            StringUtil.areEqual(this.getActualDaysApplied(),
                                originalDTO.getActualDaysApplied()) &&
            (ObjectUtil.getNullSafe(this.getTimeLimit()) ==
             ObjectUtil.getNullSafe(originalDTO.getTimeLimit())) &&
            StringUtil.areEqual(this.getPrePaymentTime(),
                                originalDTO.getPrePaymentTime())) {

            return true;
        }

        return false;
    }

    public WhatChanged getWhatChanged(MeterOPScheduleDTO originalDTO) {

        if (this.isSameAs(originalDTO)) {
            return WhatChanged.NOTHING;
        }

        return super.getWhatChanged(originalDTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    private static final List<String> specificALTStrings =
        Arrays.asList("As Posted");

    public static List<String> getSpecificALTStrings() {
        return specificALTStrings;
    }

    private boolean Override;
    private List<String> WeekDaysApplied;
    private boolean EditableColorRuleApplied;
    private boolean EditableDaysApplied;

    public void setOverride(boolean Override) {
        this.Override = Override;
    }

    public boolean isOverride() {
        return Override;
    }

    public boolean isNewRecord() {
        return (MeterOPSchedID == null);
    }

    public boolean isEditableFromTime() {
        return !isAlternateAddlDescFixedToSpecificValues();
    }

    public boolean isEditableToTime() {
        return !isAlternateAddlDescFixedToSpecificValues();
    }

    public boolean isEditableTimeLimit() {
        return (getScheduleType().isScheduleOP() ||
                getScheduleType().isScheduleALT());
    }

    public boolean isEditablePrePaymentTime() {
        return getScheduleType().isScheduleOP();
    }

    public boolean isAlternateAddlDescFixedToSpecificValues() {
        for (String string : specificALTStrings) {
            if (StringUtil.areEqual(this.getAlternateAddlDesc(), string)) {
                return true;
            }
        }

        return false;
    }

    public void setWeekDaysApplied(List<String> WeekDaysApplied) {
        this.WeekDaysApplied = WeekDaysApplied;
    }

    public List<String> getWeekDaysApplied() {
        return WeekDaysApplied;
    }

    public void setEditableColorRuleApplied(boolean EditableColorRuleApplied) {
        this.EditableColorRuleApplied = EditableColorRuleApplied;
    }

    public boolean isEditableColorRuleApplied() {
        return EditableColorRuleApplied;
    }

    public void setEditableDaysApplied(boolean EditableDaysApplied) {
        this.EditableDaysApplied = EditableDaysApplied;
    }

    public boolean isEditableDaysApplied() {
        return EditableDaysApplied;
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

    private String MeterOPSchedID;
    private String ParkingSpaceID;
    private MeterScheduleType ScheduleType;
    private int SchedulePriority;
    private String ColorRuleApplied;
    private String AlternateAddlDesc;
    private String FromTime;
    private String ToTime;
    private String DaysApplied;
    private int TimeLimit;
    private String PrePaymentTime;

    public void setMeterOPSchedID(String MeterOPSchedID) {
        this.MeterOPSchedID = MeterOPSchedID;
    }

    public String getMeterOPSchedID() {
        return MeterOPSchedID;
    }

    public void setParkingSpaceID(String ParkingSpaceID) {
        this.ParkingSpaceID = ParkingSpaceID;
    }

    public String getParkingSpaceID() {
        return ParkingSpaceID;
    }

    public void setColorRuleApplied(String ColorRuleApplied) {
        this.ColorRuleApplied = ColorRuleApplied;
    }

    public String getColorRuleApplied() {
        return ColorRuleApplied;
    }

    public void setAlternateAddlDesc(String AlternateAddlDesc) {
        this.AlternateAddlDesc = AlternateAddlDesc;
    }

    public String getAlternateAddlDesc() {
        return AlternateAddlDesc;
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

    public void setTimeLimit(int TimeLimit) {
        this.TimeLimit = TimeLimit;
    }

    public int getTimeLimit() {
        return TimeLimit;
    }

    public void setPrePaymentTime(String PrePaymentTime) {
        this.PrePaymentTime = PrePaymentTime;
    }

    public String getPrePaymentTime() {
        return PrePaymentTime;
    }

    public void setScheduleType(MeterScheduleType ScheduleType) {
        this.ScheduleType = ScheduleType;
    }

    public MeterScheduleType getScheduleType() {
        return ScheduleType;
    }

    public void setSchedulePriority(int SchedulePriority) {
        this.SchedulePriority = SchedulePriority;
    }

    public int getSchedulePriority() {
        return SchedulePriority;
    }
}
