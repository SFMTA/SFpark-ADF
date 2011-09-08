package sfpark.adf.tools.model.data.dto.blockRateSchedule;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class BlockRateScheduleDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "BLOCK_RATE_SCHED";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public BlockRateScheduleDTO() {
        super();
    }

    private BlockRateScheduleDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setBlockRateSchedID(resultSet.getString(BLOCK_RATE_SCHED_ID));
        this.setBlockID(resultSet.getString(BLOCK_ID));
        this.setMeterClass(resultSet.getString(METER_CLASS));
        this.setDateType(resultSet.getString(DATE_TYPE));
        this.setTimeBandID(resultSet.getInt(TIME_BAND_ID));
        this.setRateChangeReferenceID(resultSet.getString(RATE_CHG_REF_ID));
        this.setRateChangeReference(resultSet.getString(RATE_CHG_REF));
        this.setPlannedChangeEffectiveDate(resultSet.getDate(PLANNED_CHG_EFF_DT));
        this.setLastEffectiveFromDate(resultSet.getDate(LAST_EFF_FROM_DT));
        this.setLastFromTime(TimeDisplayUtil.extractFromTimeForDisplay(resultSet.getString(LAST_FROM_TIME)));
        this.setLastToTime(TimeDisplayUtil.extractToTimeForDisplay(resultSet.getString(LAST_TO_TIME)));
        this.setLastRate(resultSet.getFloat(LAST_RATE));
        this.setNewFromTime(TimeDisplayUtil.extractFromTimeForDisplay(resultSet.getString(NEW_FROM_TIME)));
        this.setNewToTime(TimeDisplayUtil.extractToTimeForDisplay(resultSet.getString(NEW_TO_TIME)));
        this.setOccupancyPercentage(resultSet.getInt(OCCUPANCY_PCT));
        this.setProposedRateChange(resultSet.getFloat(PROPOSED_RATE_CHG));
        this.setNewRate(resultSet.getFloat(NEW_RATE));
        this.setAdjustedRate(resultSet.getFloat(ADJUSTED_RATE));
        this.setAdjustmentReason(resultSet.getString(ADJUSTMENT_REASON));
        this.setFinalRate(resultSet.getFloat(FINAL_RATE));
        this.setFinalRateEffectiveDate(resultSet.getDate(FINAL_RATE_EFF_DT));
        this.setFinalJustification(resultSet.getString(FINAL_JUSTIFICATION));

    }

    public static final String BLOCK_RATE_SCHED_ID = "BLOCK_RATE_SCHED_ID";
    public static final String BLOCK_ID = "BLOCK_ID";
    public static final String METER_CLASS = "METER_CLASS";
    public static final String DATE_TYPE = "DATE_TYPE";
    public static final String TIME_BAND_ID = "TIME_BAND_ID";
    public static final String RATE_CHG_REF_ID = "RATE_CHG_REF_ID";
    public static final String RATE_CHG_REF = "RATE_CHG_REF";
    public static final String PLANNED_CHG_EFF_DT = "PLANNED_CHG_EFF_DT";
    public static final String LAST_EFF_FROM_DT = "LAST_EFF_FROM_DT";
    public static final String LAST_FROM_TIME = "LAST_FROM_TIME";
    public static final String LAST_TO_TIME = "LAST_TO_TIME";
    public static final String LAST_RATE = "LAST_RATE";
    public static final String NEW_FROM_TIME = "NEW_FROM_TIME";
    public static final String NEW_TO_TIME = "NEW_TO_TIME";
    public static final String OCCUPANCY_PCT = "OCCUPANCY_PCT";
    public static final String PROPOSED_RATE_CHG = "PROPOSED_RATE_CHG";
    public static final String NEW_RATE = "NEW_RATE";
    public static final String ADJUSTED_RATE = "ADJUSTED_RATE";
    public static final String ADJUSTMENT_REASON = "ADJUSTMENT_REASON";
    public static final String FINAL_RATE = "FINAL_RATE";
    public static final String FINAL_RATE_EFF_DT = "FINAL_RATE_EFF_DT";
    public static final String FINAL_JUSTIFICATION = "FINAL_JUSTIFICATION";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(BLOCK_RATE_SCHED_ID, BLOCK_ID, METER_CLASS, DATE_TYPE,
                      TIME_BAND_ID, RATE_CHG_REF_ID, RATE_CHG_REF,
                      PLANNED_CHG_EFF_DT, LAST_EFF_FROM_DT, LAST_FROM_TIME,
                      LAST_TO_TIME, LAST_RATE, NEW_FROM_TIME, NEW_TO_TIME,
                      OCCUPANCY_PCT, PROPOSED_RATE_CHG, NEW_RATE,
                      ADJUSTED_RATE, ADJUSTMENT_REASON, FINAL_RATE,
                      FINAL_RATE_EFF_DT, FINAL_JUSTIFICATION, CREATED_DT,
                      LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(ADJUSTED_RATE, ADJUSTMENT_REASON, FINAL_RATE,
                      FINAL_JUSTIFICATION, LAST_UPD_USER, LAST_UPD_PGM);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    public static List<String> getAttributeListForUpdate() {
        return AttributeListForUpdate;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static BlockRateScheduleDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new BlockRateScheduleDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    public int getMaximumLengthAdjustmentReason() {
        return 100;
    }

    public int getMaximumLengthFinalJustification() {
        return 100;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String BlockRateSchedID;
    private String BlockID;
    private String MeterClass;
    private String DateType;
    private int TimeBandID;
    private String RateChangeReferenceID;
    private String RateChangeReference;
    private Date PlannedChangeEffectiveDate;
    private Date LastEffectiveFromDate;
    private String LastFromTime;
    private String LastToTime;
    private float LastRate;
    private String NewFromTime;
    private String NewToTime;
    private int OccupancyPercentage;
    private float ProposedRateChange;
    private float NewRate;
    private float AdjustedRate;
    private String AdjustmentReason;
    private float FinalRate;
    private Date FinalRateEffectiveDate;
    private String FinalJustification;

    public void setBlockRateSchedID(String BlockRateSchedID) {
        this.BlockRateSchedID = BlockRateSchedID;
    }

    public String getBlockRateSchedID() {
        return BlockRateSchedID;
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

    public void setRateChangeReferenceID(String RateChangeReferenceID) {
        this.RateChangeReferenceID = RateChangeReferenceID;
    }

    public String getRateChangeReferenceID() {
        return RateChangeReferenceID;
    }

    public void setRateChangeReference(String RateChangeReference) {
        this.RateChangeReference = RateChangeReference;
    }

    public String getRateChangeReference() {
        return RateChangeReference;
    }

    public void setPlannedChangeEffectiveDate(Date PlannedChangeEffectiveDate) {
        this.PlannedChangeEffectiveDate = PlannedChangeEffectiveDate;
    }

    public Date getPlannedChangeEffectiveDate() {
        return PlannedChangeEffectiveDate;
    }

    public void setLastEffectiveFromDate(Date LastEffectiveFromDate) {
        this.LastEffectiveFromDate = LastEffectiveFromDate;
    }

    public Date getLastEffectiveFromDate() {
        return LastEffectiveFromDate;
    }

    public void setLastFromTime(String LastFromTime) {
        this.LastFromTime = LastFromTime;
    }

    public String getLastFromTime() {
        return LastFromTime;
    }

    public void setLastToTime(String LastToTime) {
        this.LastToTime = LastToTime;
    }

    public String getLastToTime() {
        return LastToTime;
    }

    public void setLastRate(float LastRate) {
        this.LastRate = LastRate;
    }

    public float getLastRate() {
        return LastRate;
    }

    public void setNewFromTime(String NewFromTime) {
        this.NewFromTime = NewFromTime;
    }

    public String getNewFromTime() {
        return NewFromTime;
    }

    public void setNewToTime(String NewToTime) {
        this.NewToTime = NewToTime;
    }

    public String getNewToTime() {
        return NewToTime;
    }

    public void setOccupancyPercentage(int OccupancyPercentage) {
        this.OccupancyPercentage = OccupancyPercentage;
    }

    public int getOccupancyPercentage() {
        return OccupancyPercentage;
    }

    public void setProposedRateChange(float ProposedRateChange) {
        this.ProposedRateChange = ProposedRateChange;
    }

    public float getProposedRateChange() {
        return ProposedRateChange;
    }

    public void setNewRate(float NewRate) {
        this.NewRate = NewRate;
    }

    public float getNewRate() {
        return NewRate;
    }

    public void setAdjustedRate(float AdjustedRate) {
        this.AdjustedRate = AdjustedRate;
    }

    public float getAdjustedRate() {
        return AdjustedRate;
    }

    public void setAdjustmentReason(String AdjustmentReason) {
        this.AdjustmentReason = AdjustmentReason;
    }

    public String getAdjustmentReason() {
        return AdjustmentReason;
    }

    public void setFinalRate(float FinalRate) {
        this.FinalRate = FinalRate;
    }

    public float getFinalRate() {
        return FinalRate;
    }

    public void setFinalRateEffectiveDate(Date FinalRateEffectiveDate) {
        this.FinalRateEffectiveDate = FinalRateEffectiveDate;
    }

    public Date getFinalRateEffectiveDate() {
        return FinalRateEffectiveDate;
    }

    public void setFinalJustification(String FinalJustification) {
        this.FinalJustification = FinalJustification;
    }

    public String getFinalJustification() {
        return FinalJustification;
    }
}
