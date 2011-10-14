package sfpark.adf.tools.model.data.dto.garageOPHours;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.EffectiveDateBaseDTO;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class GarageOPHoursDTO extends EffectiveDateBaseDTO {

    public static String getDatabaseTableName() {
        return "GARAGE_OP_HOURS";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public GarageOPHoursDTO() {
        super();
    }

    private GarageOPHoursDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setGarageOPHoursID(resultSet.getString(GARAGE_OP_HOURS_ID));
        this.setOSPID(resultSet.getString(OSP_ID));
        this.setOverride(false);
        this.setScheduleStatus(resultSet.getString(SCHED_STATUS));
        this.setEffectiveFromDate(resultSet.getDate(EFF_FROM_DT));
        this.setEffectiveToDate(resultSet.getDate(EFF_TO_DT));
        this.setScheduleSequence(resultSet.getInt(SCHED_SEQ));
        this.setFromDay(resultSet.getString(FROM_DAY));
        this.setToDay(resultSet.getString(TO_DAY));
        this.setFromTime(TimeDisplayUtil.extractFromTimeForDisplay(resultSet.getString(FROM_TIME)));
        this.setToTime(TimeDisplayUtil.extractToTimeForDisplay(resultSet.getString(TO_TIME)));

    }

    public static final String GARAGE_OP_HOURS_ID = "GARAGE_OP_HOURS_ID";
    public static final String OSP_ID = "OSP_ID";
    public static final String SCHED_STATUS = "SCHED_STATUS";
    public static final String EFF_FROM_DT = "EFF_FROM_DT";
    public static final String EFF_TO_DT = "EFF_TO_DT";
    public static final String SCHED_SEQ = "SCHED_SEQ";
    public static final String FROM_DAY = "FROM_DAY";
    public static final String TO_DAY = "TO_DAY";
    public static final String FROM_TIME = "FROM_TIME";
    public static final String TO_TIME = "TO_TIME";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(GARAGE_OP_HOURS_ID, OSP_ID, SCHED_STATUS, EFF_FROM_DT,
                      EFF_TO_DT, SCHED_SEQ, FROM_DAY, TO_DAY, FROM_TIME,
                      TO_TIME, CREATED_DT, LAST_UPD_DT, LAST_UPD_USER,
                      LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(OSP_ID, SCHED_STATUS, EFF_FROM_DT, EFF_TO_DT, SCHED_SEQ,
                      FROM_DAY, TO_DAY, FROM_TIME, TO_TIME, LAST_UPD_USER,
                      LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(OSP_ID, SCHED_STATUS, EFF_FROM_DT, EFF_TO_DT, SCHED_SEQ,
                      FROM_DAY, TO_DAY, FROM_TIME, TO_TIME, LAST_UPD_USER,
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

    public static GarageOPHoursDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new GarageOPHoursDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(GarageOPHoursDTO dto) {

        if (StringUtil.areEqual(this.getScheduleStatus(),
                                dto.getScheduleStatus()) &&
            SQLDateUtil.areEqual(this.getEffectiveFromDate(),
                                 dto.getEffectiveFromDate()) &&
            SQLDateUtil.areEqual(this.getEffectiveToDate(),
                                 dto.getEffectiveToDate()) &&
            (this.getScheduleSequence() == dto.getScheduleSequence()) &&
            StringUtil.areEqual(this.getFromDay(), dto.getFromDay()) &&
            StringUtil.areEqual(this.getToDay(), dto.getToDay()) &&
            StringUtil.areEqual(this.getFromTime(), dto.getFromTime()) &&
            StringUtil.areEqual(this.getToTime(), dto.getToTime())) {

            return true;
        }

        return false;
    }

    public WhatChanged getWhatChanged(GarageOPHoursDTO originalDTO) {

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

    public void setOverride(boolean Override) {
        this.Override = Override;
    }

    public boolean isOverride() {
        return Override;
    }

    public boolean isNewRecord() {
        return (GarageOPHoursID == null);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String GarageOPHoursID;
    private String OSPID;
    private String ScheduleStatus;
    private int ScheduleSequence;
    private String FromDay;
    private String ToDay;
    private String FromTime;
    private String ToTime;

    public void setGarageOPHoursID(String GarageOPHoursID) {
        this.GarageOPHoursID = GarageOPHoursID;
    }

    public String getGarageOPHoursID() {
        return GarageOPHoursID;
    }

    public void setOSPID(String OSPID) {
        this.OSPID = OSPID;
    }

    public String getOSPID() {
        return OSPID;
    }

    public void setScheduleStatus(String ScheduleStatus) {
        this.ScheduleStatus = ScheduleStatus;
    }

    public String getScheduleStatus() {
        return ScheduleStatus;
    }

    public void setScheduleSequence(int ScheduleSequence) {
        this.ScheduleSequence = ScheduleSequence;
    }

    public int getScheduleSequence() {
        return ScheduleSequence;
    }

    public void setFromDay(String FromDay) {
        this.FromDay = FromDay;
    }

    public String getFromDay() {
        return FromDay;
    }

    public void setToDay(String ToDay) {
        this.ToDay = ToDay;
    }

    public String getToDay() {
        return ToDay;
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
}
