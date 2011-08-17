package sfpark.adf.tools.model.data.dto.rateChange;

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

    private RateChangeHeaderDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        // TODO
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
    // PURELY FOR DISPLAY PURPOSES

    /*
    private String LabelRateChangeReference;

    public void setLabelRateChangeReference(String LabelRateChangeReference) {
        this.LabelRateChangeReference = LabelRateChangeReference;
    }

    public String getLabelRateChangeReference() {
        return LabelRateChangeReference;
    }

    public int getMaximumLengthValueRateChangeReference() {
        return (getMaximumLengthRateChangeReference() -
                getLabelRateChangeReference().length());
    }

    public int getMaximumLengthRateChangeReference() {
        return 20;
    }

    public int getMaximumLengthComments() {
        return 50;
    }

    public int getMaximumLengthInputXMLFileName() {
        return 300;
    }

    public boolean isEditableEffectiveFromDate() {
        return (StringUtil.areEqual(getProcessStep(), "10") ||
                StringUtil.areEqual(getProcessStep(), "70"));
    }

    public boolean isEditableInputXMLFileName() {
        return (StringUtil.areEqual(getProcessStep(), "40"));
    }

    public boolean isEditableTimeLimitOption() {
        return (StringUtil.areEqual(getProcessStep(), "60"));
    }

    public String getDisplayProcessStep() {
        String processStep = getProcessStep();

        if (StringUtil.isBlank(processStep)) {
            return "-";
        }

        return AllowedValuesProvider.getProcessStepTreeMap().get(processStep);
    }

    public String getDisplayStepExecutionStatus() {
        String stepExecutionStatus = getStepExecutionStatus();

        if (StringUtil.isBlank(stepExecutionStatus)) {
            return "-";
        }

        return AllowedValuesProvider.getProcessStepExecStatusTreeMap().get(stepExecutionStatus);
    }

 */
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


}
/*
RATE_CHG_HEADER
===============

Index     = 1
Name      = RATE_CHG_REF_ID
Type      = NUMBER
Precision = 0
Nullable  = NO


Index     = 2
Name      = RATE_CHG_REF
Type      = VARCHAR2
Precision = 20
Nullable  = YES


Index     = 3
Name      = RATE_CHG_DESC
Type      = VARCHAR2
Precision = 100
Nullable  = YES


Index     = 4
Name      = PM_DISTRICTS
Type      = VARCHAR2
Precision = 500
Nullable  = YES


Index     = 5
Name      = AREA_TYPE
Type      = VARCHAR2
Precision = 10
Nullable  = YES


Index     = 6
Name      = GROUP_ID
Type      = NUMBER
Precision = 0
Nullable  = YES


Index     = 7
Name      = CALENDAR_ID
Type      = NUMBER
Precision = 0
Nullable  = YES


Index     = 8
Name      = RATE_CHG_POLICY
Type      = NUMBER
Precision = 0
Nullable  = YES


Index     = 9
Name      = PLANNED_CHG_EFF_DT
Type      = DATE
Precision = 7
Nullable  = YES


Index     = 10
Name      = STATUS
Type      = VARCHAR2
Precision = 10
Nullable  = YES


Index     = 11
Name      = SUBMITTED_BY
Type      = VARCHAR2
Precision = 20
Nullable  = YES


Index     = 12
Name      = SUBMITTED_DT
Type      = DATE
Precision = 7
Nullable  = YES


Index     = 13
Name      = APPROVED_BY
Type      = VARCHAR2
Precision = 20
Nullable  = YES


Index     = 14
Name      = APPROVED_DT
Type      = DATE
Precision = 7
Nullable  = YES


Index     = 15
Name      = CREATED_DT
Type      = DATE
Precision = 7
Nullable  = YES


Index     = 16
Name      = LAST_UPD_DT
Type      = DATE
Precision = 7
Nullable  = YES


Index     = 17
Name      = LAST_UPD_USER
Type      = VARCHAR2
Precision = 30
Nullable  = YES


Index     = 18
Name      = LAST_UPD_PGM
Type      = VARCHAR2
Precision = 30
Nullable  = YES



 */