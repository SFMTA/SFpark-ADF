package sfpark.adf.tools.model.data.dto.rateChange;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;

public class RateChangeControlDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "RATE_CHG_CONTROL";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public RateChangeControlDTO() {
        super();
    }

    private RateChangeControlDTO(RateChangeControlDTO DTO) {
        super(DTO);

        this.setRateChangeReferenceID(DTO.getRateChangeReferenceID());
        this.setRateChangeReference(DTO.getRateChangeReference());
        this.setPMDistrictID(DTO.getPMDistrictID());
        this.setGroupID(DTO.getGroupID());
        this.setAreaType(DTO.getAreaType());
        this.setPlannedChangeEffectiveDate(DTO.getPlannedChangeEffectiveDate());
        this.setStatus(DTO.getStatus());
        this.setSubmittedBy(DTO.getSubmittedBy());
        this.setSubmittedDate(DTO.getSubmittedDate());
        this.setApprovedBy(DTO.getApprovedBy());
        this.setApprovedDate(DTO.getApprovedDate());

    }

    private RateChangeControlDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setRateChangeReferenceID(resultSet.getString(RATE_CHG_REF_ID));
        this.setRateChangeReference(resultSet.getString(RATE_CHG_REF));
        this.setPMDistrictID(resultSet.getInt(PM_DISTRICT_ID));
        this.setGroupID(resultSet.getString(GROUP_ID));
        this.setAreaType(resultSet.getString(AREA_TYPE));
        this.setPlannedChangeEffectiveDate(resultSet.getDate(PLANNED_CHG_EFF_DT));
        this.setStatus(resultSet.getString(STATUS));
        this.setSubmittedBy(resultSet.getString(SUBMITTED_BY));
        this.setSubmittedDate(resultSet.getDate(SUBMITTED_DT));
        this.setApprovedBy(resultSet.getString(APPROVED_BY));
        this.setApprovedDate(resultSet.getDate(APPROVED_DT));

    }

    public static final String RATE_CHG_REF_ID = "RATE_CHG_REF_ID";
    public static final String RATE_CHG_REF = "RATE_CHG_REF";
    public static final String PM_DISTRICT_ID = "PM_DISTRICT_ID";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String AREA_TYPE = "AREA_TYPE";
    public static final String PLANNED_CHG_EFF_DT = "PLANNED_CHG_EFF_DT";
    public static final String STATUS = "STATUS";
    public static final String SUBMITTED_BY = "SUBMITTED_BY";
    public static final String SUBMITTED_DT = "SUBMITTED_DT";
    public static final String APPROVED_BY = "APPROVED_BY";
    public static final String APPROVED_DT = "APPROVED_DT";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(RATE_CHG_REF_ID, RATE_CHG_REF, PM_DISTRICT_ID, GROUP_ID,
                      AREA_TYPE, PLANNED_CHG_EFF_DT, STATUS, SUBMITTED_BY,
                      SUBMITTED_DT, APPROVED_BY, APPROVED_DT, CREATED_DT,
                      LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(RATE_CHG_REF, PM_DISTRICT_ID, GROUP_ID, AREA_TYPE,
                      PLANNED_CHG_EFF_DT, STATUS, SUBMITTED_BY, SUBMITTED_DT,
                      APPROVED_BY, APPROVED_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(RATE_CHG_REF, PM_DISTRICT_ID, GROUP_ID, AREA_TYPE,
                      PLANNED_CHG_EFF_DT, STATUS, SUBMITTED_BY, SUBMITTED_DT,
                      APPROVED_BY, APPROVED_DT, LAST_UPD_USER, LAST_UPD_PGM);

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

    public static RateChangeControlDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new RateChangeControlDTO(resultSet);
    }

    public static RateChangeControlDTO copy(RateChangeControlDTO DTO) {

        if (DTO == null) {
            return null;
        }

        return new RateChangeControlDTO(DTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    public int getMaximumLengthRateChangeReference() {
        return 20;
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
    private int PMDistrictID;
    private String GroupID;
    private String AreaType;
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

    public void setPMDistrictID(int PMDistrictID) {
        this.PMDistrictID = PMDistrictID;
    }

    public int getPMDistrictID() {
        return PMDistrictID;
    }

    public void setGroupID(String GroupID) {
        this.GroupID = GroupID;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setAreaType(String AreaType) {
        this.AreaType = AreaType;
    }

    public String getAreaType() {
        return AreaType;
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
