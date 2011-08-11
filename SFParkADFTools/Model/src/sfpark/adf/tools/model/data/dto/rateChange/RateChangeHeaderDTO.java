package sfpark.adf.tools.model.data.dto.rateChange;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;

public class RateChangeHeaderDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "RATE_CHG_HEADER";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public RateChangeHeaderDTO() {
        super();
    }

    //    private RateChangeHeaderDTO(RateChangeHeaderDTO DTO) {
    //        super(DTO);
    //    }

    private RateChangeHeaderDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setRateChangeReferenceID(resultSet.getString(RATE_CHG_REF_ID));
        this.setRateChangeReference(resultSet.getString(RATE_CHG_REF));
        this.setRateChangeDescription(resultSet.getString(RATE_CHG_DESC));
        this.setGroupID(resultSet.getString(GROUP_ID));
        this.setCalendarID(resultSet.getString(CALENDAR_ID));
        this.setRateChangePolicy(resultSet.getInt(RATE_CHG_POLICY));
        this.setPlannedChangeEffectiveDate(resultSet.getDate(PLANNED_CHG_EFF_DT));
        this.setStatus(resultSet.getString(STATUS));
        this.setSubmittedBy(resultSet.getString(SUBMITTED_BY));
        this.setSubmittedDate(resultSet.getDate(SUBMITTED_DT));
        this.setApprovedBy(resultSet.getString(APPROVED_BY));
        this.setApprovedDate(resultSet.getDate(APPROVED_DT));

    }

    public static final String RATE_CHG_REF_ID = "RATE_CHG_REF_ID";
    public static final String RATE_CHG_REF = "RATE_CHG_REF";
    public static final String RATE_CHG_DESC = "RATE_CHG_DESC";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String CALENDAR_ID = "CALENDAR_ID";
    public static final String RATE_CHG_POLICY = "RATE_CHG_POLICY";
    public static final String PLANNED_CHG_EFF_DT = "PLANNED_CHG_EFF_DT";
    public static final String STATUS = "STATUS";
    public static final String SUBMITTED_BY = "SUBMITTED_BY";
    public static final String SUBMITTED_DT = "SUBMITTED_DT";
    public static final String APPROVED_BY = "APPROVED_BY";
    public static final String APPROVED_DT = "APPROVED_DT";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(RATE_CHG_REF_ID, RATE_CHG_REF, RATE_CHG_DESC, GROUP_ID,
                      CALENDAR_ID, RATE_CHG_POLICY, PLANNED_CHG_EFF_DT, STATUS,
                      SUBMITTED_BY, SUBMITTED_DT, APPROVED_BY, APPROVED_DT,
                      CREATED_DT, LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(RATE_CHG_REF, RATE_CHG_DESC, GROUP_ID, CALENDAR_ID,
                      RATE_CHG_POLICY, PLANNED_CHG_EFF_DT, STATUS,
                      LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(RATE_CHG_REF, RATE_CHG_DESC, GROUP_ID, CALENDAR_ID,
                      RATE_CHG_POLICY, PLANNED_CHG_EFF_DT, STATUS,
                      SUBMITTED_BY, SUBMITTED_DT, APPROVED_BY, APPROVED_DT,
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

    public static RateChangeHeaderDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new RateChangeHeaderDTO(resultSet);
    }

    //    public static RateChangeHeaderDTO copy(RateChangeHeaderDTO DTO) {
    //
    //        if (DTO == null) {
    //            return null;
    //        }
    //
    //        return new RateChangeHeaderDTO(DTO);
    //    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    public int getMaximumLengthRateChangeReference() {
        return 20;
    }

    public int getMaximumLengthRateChangeDescription() {
        return 100;
    }

    public int getMaximumLengthSubmittedBy() {
        return 20;
    }

    public int getMaximumLengthApprovedBy() {
        return 20;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String RateChangeReferenceID;
    private String RateChangeReference;
    private String RateChangeDescription;
    private String GroupID;
    private String CalendarID;
    private int RateChangePolicy;
    private Date PlannedChangeEffectiveDate;
    private String Status;
    private String SubmittedBy;
    private Date SubmittedDate;
    private String ApprovedBy;
    private Date ApprovedDate;

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

    public void setRateChangeDescription(String RateChangeDescription) {
        this.RateChangeDescription = RateChangeDescription;
    }

    public String getRateChangeDescription() {
        return RateChangeDescription;
    }

    public void setGroupID(String GroupID) {
        this.GroupID = GroupID;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setCalendarID(String CalendarID) {
        this.CalendarID = CalendarID;
    }

    public String getCalendarID() {
        return CalendarID;
    }

    public void setRateChangePolicy(int RateChangePolicy) {
        this.RateChangePolicy = RateChangePolicy;
    }

    public int getRateChangePolicy() {
        return RateChangePolicy;
    }

    public void setPlannedChangeEffectiveDate(Date PlannedChangeEffectiveDate) {
        this.PlannedChangeEffectiveDate = PlannedChangeEffectiveDate;
    }

    public Date getPlannedChangeEffectiveDate() {
        return PlannedChangeEffectiveDate;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getStatus() {
        return Status;
    }

    public void setSubmittedBy(String SubmittedBy) {
        this.SubmittedBy = SubmittedBy;
    }

    public String getSubmittedBy() {
        return SubmittedBy;
    }

    public void setSubmittedDate(Date SubmittedDate) {
        this.SubmittedDate = SubmittedDate;
    }

    public Date getSubmittedDate() {
        return SubmittedDate;
    }

    public void setApprovedBy(String ApprovedBy) {
        this.ApprovedBy = ApprovedBy;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedDate(Date ApprovedDate) {
        this.ApprovedDate = ApprovedDate;
    }

    public Date getApprovedDate() {
        return ApprovedDate;
    }
}
