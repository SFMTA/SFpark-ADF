package sfpark.adf.tools.model.data.dto.rateChange;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class RateChangeProcessControlDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "RATE_CHG_PROCESS_CONTROL";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public RateChangeProcessControlDTO() {
        super();
    }

    private RateChangeProcessControlDTO(RateChangeProcessControlDTO DTO) {
        super(DTO);

        this.setProcessID(DTO.getProcessID());
        this.setRateChangeReference(DTO.getRateChangeReference());
        this.setRateChangeReferenceID(DTO.getRateChangeReferenceID());
        this.setComments(DTO.getComments());
        this.setPMDistrictID(DTO.getPMDistrictID());
        this.setMeterVendor(DTO.getMeterVendor());
        this.setMeterModel(DTO.getMeterModel());
        this.setBlockSelection(DTO.getBlockSelection());
        this.setOutputXMLFileName(DTO.getOutputXMLFileName());
        this.setInputXMLFileName(DTO.getInputXMLFileName());
        this.setEffectiveFromDate(DTO.getEffectiveFromDate());
        this.setTimeLimitOption(DTO.getTimeLimitOption());
        this.setProcessStep(DTO.getProcessStep());
        this.setStepStartFlag(DTO.getStepStartFlag());
        this.setStepExecutionStatus(DTO.getStepExecutionStatus());
        this.setStatusMessage(DTO.getStatusMessage());
        this.setStepStartDate(this.getStepStartDate());
        this.setStepEndDate(DTO.getStepEndDate());

    }

    private RateChangeProcessControlDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setProcessID(resultSet.getString(PROCESS_ID));
        this.setRateChangeReference(resultSet.getString(RATE_CHG_REF));
        this.setRateChangeReferenceID(resultSet.getString(RATE_CHG_REF_ID));
        this.setComments(resultSet.getString(COMMENTS));
        this.setPMDistrictID(resultSet.getInt(PM_DISTRICT_ID));
        this.setMeterVendor(resultSet.getString(METER_VENDOR));
        this.setMeterModel(resultSet.getString(METER_MODEL));
        this.setBlockSelection(resultSet.getString(BLOCK_SELECTION));
        this.setOutputXMLFileName(resultSet.getString(XML_OUTPUT_FILE_NAME));
        this.setInputXMLFileName(resultSet.getString(XML_INPUT_FILE_NAME));
        this.setEffectiveFromDate(resultSet.getDate(EFF_FROM_DT));
        this.setTimeLimitOption(resultSet.getString(TIME_LIMIT_OPTION));
        this.setProcessStep(resultSet.getString(PROCESS_STEP));
        this.setStepStartFlag(resultSet.getString(STEP_START_FLAG));
        this.setStepExecutionStatus(resultSet.getString(STEP_EXEC_STATUS));
        this.setStatusMessage(resultSet.getString(STATUS_MESSAGE));
        this.setStepStartDate(resultSet.getTimestamp(STEP_START_DT));
        this.setStepEndDate(resultSet.getTimestamp(STEP_END_DT));

    }

    public static final String PROCESS_ID = "PROCESS_ID";
    public static final String RATE_CHG_REF = "RATE_CHG_REF";
    public static final String RATE_CHG_REF_ID = "RATE_CHG_REF_ID";
    public static final String COMMENTS = "COMMENTS";
    public static final String PM_DISTRICT_ID = "PM_DISTRICT_ID";
    public static final String METER_VENDOR = "METER_VENDOR";
    public static final String METER_MODEL = "METER_MODEL";
    public static final String BLOCK_SELECTION = "BLOCK_SELECTION";
    public static final String XML_OUTPUT_FILE_NAME = "XML_OUTPUT_FILE_NAME";
    public static final String XML_INPUT_FILE_NAME = "XML_INPUT_FILE_NAME";
    public static final String EFF_FROM_DT = "EFF_FROM_DT";
    public static final String TIME_LIMIT_OPTION = "TIME_LIMIT_OPTION";
    public static final String PROCESS_STEP = "PROCESS_STEP";
    public static final String STEP_START_FLAG = "STEP_START_FLAG";
    public static final String STEP_EXEC_STATUS = "STEP_EXEC_STATUS";
    public static final String STATUS_MESSAGE = "STATUS_MESSAGE";
    public static final String STEP_START_DT = "STEP_START_DT";
    public static final String STEP_END_DT = "STEP_END_DT";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(PROCESS_ID, RATE_CHG_REF, RATE_CHG_REF_ID, COMMENTS,
                      PM_DISTRICT_ID, METER_VENDOR, METER_MODEL,
                      BLOCK_SELECTION, XML_OUTPUT_FILE_NAME,
                      XML_INPUT_FILE_NAME, EFF_FROM_DT, TIME_LIMIT_OPTION,
                      PROCESS_STEP, STEP_START_FLAG, STEP_EXEC_STATUS,
                      STATUS_MESSAGE, STEP_START_DT, STEP_END_DT, CREATED_DT,
                      LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(RATE_CHG_REF, RATE_CHG_REF_ID, COMMENTS, PM_DISTRICT_ID,
                      METER_VENDOR, METER_MODEL, BLOCK_SELECTION,
                      XML_INPUT_FILE_NAME, EFF_FROM_DT, TIME_LIMIT_OPTION,
                      PROCESS_STEP, STEP_START_FLAG, LAST_UPD_USER,
                      LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(RATE_CHG_REF, RATE_CHG_REF_ID, COMMENTS,
                      XML_INPUT_FILE_NAME, EFF_FROM_DT, TIME_LIMIT_OPTION,
                      PROCESS_STEP, STEP_START_FLAG, LAST_UPD_USER,
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

    public static RateChangeProcessControlDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new RateChangeProcessControlDTO(resultSet);
    }

    public static RateChangeProcessControlDTO copy(RateChangeProcessControlDTO DTO) {

        if (DTO == null) {
            return null;
        }

        return new RateChangeProcessControlDTO(DTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String ProcessID;
    private String RateChangeReference;
    private String RateChangeReferenceID;
    private String Comments;
    private int PMDistrictID;
    private String MeterVendor;
    private String MeterModel;
    private String BlockSelection;
    private String OutputXMLFileName;
    private String InputXMLFileName;
    private Date EffectiveFromDate;
    private String TimeLimitOption;
    private String ProcessStep;
    private String StepStartFlag;
    private String StepExecutionStatus;
    private String StatusMessage;
    private Timestamp StepStartDate;
    private Timestamp StepEndDate;

    public void setProcessID(String ProcessID) {
        this.ProcessID = ProcessID;
    }

    public String getProcessID() {
        return ProcessID;
    }

    public void setRateChangeReference(String RateChangeReference) {
        this.RateChangeReference = RateChangeReference;
    }

    public String getRateChangeReference() {
        return RateChangeReference;
    }

    public void setRateChangeReferenceID(String RateChangeReferenceID) {
        this.RateChangeReferenceID = RateChangeReferenceID;
    }

    public String getRateChangeReferenceID() {
        return RateChangeReferenceID;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getComments() {
        return Comments;
    }

    public void setPMDistrictID(int PMDistrictID) {
        this.PMDistrictID = PMDistrictID;
    }

    public int getPMDistrictID() {
        return PMDistrictID;
    }

    public void setMeterVendor(String MeterVendor) {
        this.MeterVendor = MeterVendor;
    }

    public String getMeterVendor() {
        return MeterVendor;
    }

    public void setMeterModel(String MeterModel) {
        this.MeterModel = MeterModel;
    }

    public String getMeterModel() {
        return MeterModel;
    }

    public void setBlockSelection(String BlockSelection) {
        this.BlockSelection = BlockSelection;
    }

    public String getBlockSelection() {
        return BlockSelection;
    }

    public void setOutputXMLFileName(String OutputXMLFileName) {
        this.OutputXMLFileName = OutputXMLFileName;
    }

    public String getOutputXMLFileName() {
        return OutputXMLFileName;
    }

    public void setInputXMLFileName(String InputXMLFileName) {
        this.InputXMLFileName = InputXMLFileName;
    }

    public String getInputXMLFileName() {
        return InputXMLFileName;
    }

    public void setEffectiveFromDate(Date EffectiveFromDate) {
        this.EffectiveFromDate = EffectiveFromDate;
    }

    public Date getEffectiveFromDate() {
        return EffectiveFromDate;
    }

    public void setTimeLimitOption(String TimeLimitOption) {
        this.TimeLimitOption = TimeLimitOption;
    }

    public String getTimeLimitOption() {
        return TimeLimitOption;
    }

    public void setProcessStep(String ProcessStep) {
        this.ProcessStep = ProcessStep;
    }

    public String getProcessStep() {
        return ProcessStep;
    }

    public void setStepStartFlag(String StepStartFlag) {
        this.StepStartFlag = StepStartFlag;
    }

    public String getStepStartFlag() {
        return StepStartFlag;
    }

    public void setStepExecutionStatus(String StepExecutionStatus) {
        this.StepExecutionStatus = StepExecutionStatus;
    }

    public String getStepExecutionStatus() {
        return StepExecutionStatus;
    }

    public void setStatusMessage(String StatusMessage) {
        this.StatusMessage = StatusMessage;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStepStartDate(Timestamp StepStartDate) {
        this.StepStartDate = StepStartDate;
    }

    public Timestamp getStepStartDate() {
        return StepStartDate;
    }

    public void setStepEndDate(Timestamp StepEndDate) {
        this.StepEndDate = StepEndDate;
    }

    public Timestamp getStepEndDate() {
        return StepEndDate;
    }
}
