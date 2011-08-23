package sfpark.adf.tools.model.data.dto.rateChange;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.pmDistricts.PMDistrictsDO;
import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.model.data.helper.PMDistrictAreaType;
import sfpark.adf.tools.model.data.helper.RateChangeStatus;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

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

    private RateChangeHeaderDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setRateChangeReferenceID(resultSet.getString(RATE_CHG_REF_ID));
        this.setRateChangeReference(resultSet.getString(RATE_CHG_REF));
        this.setRateChangeDescription(resultSet.getString(RATE_CHG_DESC));
        this.setPMDistricts(resultSet.getString(PM_DISTRICTS));
        this.setAreaType(PMDistrictAreaType.extract(resultSet.getString(AREA_TYPE)));
        this.setGroupID(resultSet.getString(GROUP_ID));
        this.setCalendarID(resultSet.getString(CALENDAR_ID));
        this.setRateChangePolicy(resultSet.getString(RATE_CHG_POLICY));
        this.setPlannedChangeEffectiveDate(resultSet.getDate(PLANNED_CHG_EFF_DT));
        this.setStatus(RateChangeStatus.extract(resultSet.getString(STATUS)));
        this.setSubmittedBy(resultSet.getString(SUBMITTED_BY));
        this.setSubmittedOn(resultSet.getDate(SUBMITTED_DT));
        this.setApprovedBy(resultSet.getString(APPROVED_BY));
        this.setApprovedOn(resultSet.getDate(APPROVED_DT));

    }

    public static final String RATE_CHG_REF_ID = "RATE_CHG_REF_ID";
    public static final String RATE_CHG_REF = "RATE_CHG_REF";
    public static final String RATE_CHG_DESC = "RATE_CHG_DESC";
    public static final String PM_DISTRICTS = "PM_DISTRICTS";
    public static final String AREA_TYPE = "AREA_TYPE";
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
        Arrays.asList(RATE_CHG_REF_ID, RATE_CHG_REF, RATE_CHG_DESC,
                      PM_DISTRICTS, AREA_TYPE, GROUP_ID, CALENDAR_ID,
                      RATE_CHG_POLICY, PLANNED_CHG_EFF_DT, STATUS,
                      SUBMITTED_BY, SUBMITTED_DT, APPROVED_BY, APPROVED_DT,
                      CREATED_DT, LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(RATE_CHG_REF, RATE_CHG_DESC, PM_DISTRICTS, AREA_TYPE,
                      CALENDAR_ID, RATE_CHG_POLICY, PLANNED_CHG_EFF_DT, STATUS,
                      LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(STATUS, SUBMITTED_BY, SUBMITTED_DT, APPROVED_BY,
                      APPROVED_DT, LAST_UPD_USER, LAST_UPD_PGM);

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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(RateChangeHeaderDTO originalDTO) {

        if (StringUtil.areEqual(this.getRateChangeReferenceID(),
                                originalDTO.getRateChangeReferenceID()) &&
            StringUtil.areEqual(this.getRateChangeReference(),
                                originalDTO.getRateChangeReference()) &&
            StringUtil.areEqual(this.getRateChangeDescription(),
                                originalDTO.getRateChangeDescription()) &&
            StringUtil.areEqual(this.getPMDistricts(),
                                originalDTO.getPMDistricts()) &&
            this.getAreaType().equals(originalDTO.getAreaType()) &&
            StringUtil.areEqual(this.getCalendarID(),
                                originalDTO.getCalendarID()) &&
            StringUtil.areEqual(this.getRateChangePolicy(),
                                originalDTO.getRateChangePolicy()) &&
            SQLDateUtil.areEqual(this.getPlannedChangeEffectiveDate(),
                                 originalDTO.getPlannedChangeEffectiveDate()) &&
            StringUtil.areEqual(this.getSubmittedBy(),
                                originalDTO.getSubmittedBy()) &&
            SQLDateUtil.areEqual(this.getSubmittedOn(),
                                 originalDTO.getSubmittedOn()) &&
            StringUtil.areEqual(this.getApprovedBy(),
                                originalDTO.getApprovedBy()) &&
            SQLDateUtil.areEqual(this.getApprovedOn(),
                                 originalDTO.getApprovedOn())) {

            return true;
        }

        return false;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    private String DisplayCalendarName;
    private Date MinimumAllowedDate;
    private List<PMDistrictsDO> PMDistrictDOs;

    public void setDisplayCalendarName(String CalendarName) {
        this.DisplayCalendarName = CalendarName;
    }

    public String getDisplayCalendarName() {
        return DisplayCalendarName;
    }

    public void setMinimumAllowedDate(Date MinimumAllowedDate) {
        this.MinimumAllowedDate = MinimumAllowedDate;
    }

    public Date getMinimumAllowedDate() {
        return MinimumAllowedDate;
    }

    public void setPMDistrictDOs(List<PMDistrictsDO> PMDistrictDOs) {
        this.PMDistrictDOs = PMDistrictDOs;

        if (PMDistrictDOs != null) {
            List<String> tempList = new ArrayList<String>();

            for (PMDistrictsDO DO : PMDistrictDOs) {
                tempList.add(DO.getPMDistrictID());
            }

            setPMDistricts(StringUtil.convertListToString(tempList));
        }
    }

    private List<PMDistrictsDO> getPMDistrictDOs() {
        return PMDistrictDOs;
    }

    public String getDisplayPMDistricts() {

        if (getPMDistrictDOs() == null) {
            return "";
        }

        List<String> tempList = new ArrayList<String>();

        for (PMDistrictsDO DO : getPMDistrictDOs()) {
            tempList.add(DO.getPMDistrictName());
        }

        return StringUtil.convertListToString(tempList,
                                              StringUtil.SEPARATOR.COMMA_WITH_TRAILING_SPACE);
    }

    public int getColumnsRateChangeReference() {
        return getMaximumLengthRateChangeReference() + 1;
    }

    public int getMaximumLengthRateChangeReference() {
        return 20;
    }

    public int getMaximumLengthRateChangeDescription() {
        return 100;
    }

    public int getColumnsSubmittedBy() {
        return getMaximumLengthSubmittedBy() + 1;
    }

    public int getMaximumLengthSubmittedBy() {
        return 20;
    }

    public int getColumnsApprovedBy() {
        return getMaximumLengthApprovedBy() + 1;
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
    private String PMDistricts;
    private PMDistrictAreaType AreaType;
    private String GroupID;
    private String CalendarID;
    private String RateChangePolicy;
    private Date PlannedChangeEffectiveDate;
    private RateChangeStatus Status;
    private String SubmittedBy;
    private Date SubmittedOn;
    private String ApprovedBy;
    private Date ApprovedOn;

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

    public void setPMDistricts(String PMDistricts) {
        this.PMDistricts = PMDistricts;
    }

    public String getPMDistricts() {
        return PMDistricts;
    }

    public void setAreaType(PMDistrictAreaType AreaType) {
        this.AreaType = AreaType;
    }

    public PMDistrictAreaType getAreaType() {
        return AreaType;
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

    public void setRateChangePolicy(String RateChangePolicy) {
        this.RateChangePolicy = RateChangePolicy;
    }

    public String getRateChangePolicy() {
        return RateChangePolicy;
    }

    public void setPlannedChangeEffectiveDate(Date PlannedChangeEffectiveDate) {
        this.PlannedChangeEffectiveDate = PlannedChangeEffectiveDate;
    }

    public Date getPlannedChangeEffectiveDate() {
        return PlannedChangeEffectiveDate;
    }

    public void setStatus(RateChangeStatus Status) {
        this.Status = Status;
    }

    public RateChangeStatus getStatus() {
        return Status;
    }

    public void setSubmittedBy(String SubmittedBy) {
        this.SubmittedBy = SubmittedBy;
    }

    public String getSubmittedBy() {
        return SubmittedBy;
    }

    public void setSubmittedOn(Date SubmittedOn) {
        this.SubmittedOn = SubmittedOn;
    }

    public Date getSubmittedOn() {
        return SubmittedOn;
    }

    public void setApprovedBy(String ApprovedBy) {
        this.ApprovedBy = ApprovedBy;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedOn(Date ApprovedOn) {
        this.ApprovedOn = ApprovedOn;
    }

    public Date getApprovedOn() {
        return ApprovedOn;
    }
}
