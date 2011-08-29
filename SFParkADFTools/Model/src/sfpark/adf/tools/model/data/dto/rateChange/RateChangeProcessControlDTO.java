package sfpark.adf.tools.model.data.dto.rateChange;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.blocks.BlocksDO;
import sfpark.adf.tools.model.data.dO.pmDistricts.PMDistrictsDO;
import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.model.data.helper.RateChangeProcessStepStartFlag;
import sfpark.adf.tools.model.data.helper.RateChangeProcessTimeLimitOption;
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

    private RateChangeProcessControlDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setProcessID(resultSet.getString(PROCESS_ID));
        this.setRateChangeReference(resultSet.getString(RATE_CHG_REF));
        this.setRateChangeReferenceID(resultSet.getString(RATE_CHG_REF_ID));
        this.setComments(resultSet.getString(COMMENTS));
        this.setPMDistricts(resultSet.getString(PM_DISTRICTS));
        this.setMeterVendor(resultSet.getString(METER_VENDOR));
        this.setMeterModel(resultSet.getString(METER_MODEL));
        this.setBlockSelection(resultSet.getString(BLOCK_SELECTION));
        this.setXMLOutputFileName(resultSet.getString(XML_OUTPUT_FILE_NAME));
        this.setXMLInputFileName(resultSet.getString(XML_INPUT_FILE_NAME));
        this.setEffectiveFromDate(resultSet.getDate(EFF_FROM_DT));
        this.setTimeLimitOption(RateChangeProcessTimeLimitOption.extract(resultSet.getString(TIME_LIMIT_OPTION)));
        this.setProcessStep(resultSet.getString(PROCESS_STEP));
        this.setStepStartFlag(RateChangeProcessStepStartFlag.extract(resultSet.getString(STEP_START_FLAG)));
        this.setStepExecStatus(resultSet.getString(STEP_EXEC_STATUS));
        this.setStatusMessage(resultSet.getString(STATUS_MESSAGE));
        this.setStepStartDate(resultSet.getTimestamp(STEP_START_DT));
        this.setStepEndDate(resultSet.getTimestamp(STEP_END_DT));

    }

    public static final String PROCESS_ID = "PROCESS_ID";
    public static final String RATE_CHG_REF = "RATE_CHG_REF";
    public static final String RATE_CHG_REF_ID = "RATE_CHG_REF_ID";
    public static final String COMMENTS = "COMMENTS";
    public static final String PM_DISTRICTS = "PM_DISTRICTS";
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
                      PM_DISTRICTS, METER_VENDOR, METER_MODEL, BLOCK_SELECTION,
                      XML_OUTPUT_FILE_NAME, XML_INPUT_FILE_NAME, EFF_FROM_DT,
                      TIME_LIMIT_OPTION, PROCESS_STEP, STEP_START_FLAG,
                      STEP_EXEC_STATUS, STATUS_MESSAGE, STEP_START_DT,
                      STEP_END_DT, CREATED_DT, LAST_UPD_DT, LAST_UPD_USER,
                      LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(RATE_CHG_REF, RATE_CHG_REF_ID, COMMENTS, PM_DISTRICTS,
                      METER_VENDOR, METER_MODEL, BLOCK_SELECTION, EFF_FROM_DT,
                      TIME_LIMIT_OPTION, PROCESS_STEP, STEP_START_FLAG,
                      STEP_EXEC_STATUS, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(COMMENTS, XML_INPUT_FILE_NAME, EFF_FROM_DT,
                      TIME_LIMIT_OPTION, PROCESS_STEP, STEP_START_FLAG,
                      STEP_EXEC_STATUS, LAST_UPD_USER, LAST_UPD_PGM);

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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    private String LabelRateChangeReference;
    private String ValueRateChangeReference;
    private List<PMDistrictsDO> PMDistrictDOs;
    private List<BlocksDO> BlockDOs;

    public void setLabelRateChangeReference(String LabelRateChangeReference) {
        this.LabelRateChangeReference = LabelRateChangeReference;
    }

    public String getLabelRateChangeReference() {
        return LabelRateChangeReference;
    }

    public void setValueRateChangeReference(String ValueRateChangeReference) {
        this.ValueRateChangeReference = ValueRateChangeReference;

        if (StringUtil.isNotBlank(ValueRateChangeReference)) {
            setRateChangeReference(getLabelRateChangeReference() +
                                   ValueRateChangeReference);
        }
    }

    public String getValueRateChangeReference() {
        return ValueRateChangeReference;
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

    public List<PMDistrictsDO> getPMDistrictDOs() {
        return PMDistrictDOs;
    }

    public String getDisplayPMDistricts() {

        if (getPMDistrictDOs() == null || getPMDistrictDOs().isEmpty()) {
            return "";
        }

        List<String> tempList = new ArrayList<String>();

        for (PMDistrictsDO DO : getPMDistrictDOs()) {
            tempList.add(DO.getPMDistrictName());
        }

        return StringUtil.convertListToString(tempList,
                                              StringUtil.SEPARATOR.COMMA_WITH_TRAILING_SPACE);
    }

    public void setBlockDOs(List<BlocksDO> BlockDOs) {
        this.BlockDOs = BlockDOs;

        if (BlockDOs != null) {
            List<String> tempList = new ArrayList<String>();

            for (BlocksDO DO : BlockDOs) {
                tempList.add(DO.getBlockID());
            }

            setBlockSelection(StringUtil.convertListToString(tempList));
        }
    }

    public List<BlocksDO> getBlockDOs() {
        return BlockDOs;
    }

    public String getDisplayBlockSelection() {

        if (getBlockDOs() == null || getBlockDOs().isEmpty()) {
            return "";
        }

        List<String> tempList = new ArrayList<String>();

        for (BlocksDO DO : getBlockDOs()) {
            tempList.add(DO.getBlockID());
        }

        return StringUtil.convertListToString(tempList,
                                              StringUtil.SEPARATOR.COMMA_WITH_TRAILING_SPACE);
    }

    public int getColumnsValueRateChangeReference() {
        return (getMaximumLengthValueRateChangeReference() + 1);
    }

    public int getMaximumLengthValueRateChangeReference() {
        return (getMaximumLengthRateChangeReference() -
                getLabelRateChangeReference().length());
    }

    private int getMaximumLengthRateChangeReference() {
        return 20;
    }

    public int getMaximumLengthComments() {
        return 50;
    }

    public int getMaximumLengthXMLInputFileName() {
        return 300;
    }

    public boolean isDeletable() {
        return StringUtil.areEqual(getProcessStep(), "10");
    }

    public boolean isEditable() {
        return !(StringUtil.areEqual(getProcessStep(), "70") ||
                 StringUtil.areEqual(getProcessStep(), "99"));
    }

    public boolean isEditableEffectiveFromDate() {
        return (StringUtil.areEqual(getProcessStep(), "10") ||
                StringUtil.areEqual(getProcessStep(), "70"));
    }

    public boolean isEditableXMLInputFileName() {
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
    private String PMDistricts;
    private String MeterVendor;
    private String MeterModel;
    private String BlockSelection;
    private String XMLOutputFileName;
    private String XMLInputFileName;
    private Date EffectiveFromDate;
    private RateChangeProcessTimeLimitOption TimeLimitOption;
    private String ProcessStep;
    private RateChangeProcessStepStartFlag StepStartFlag;
    private String StepExecStatus;
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

    public void setPMDistricts(String PMDistricts) {
        this.PMDistricts = PMDistricts;
    }

    public String getPMDistricts() {
        return PMDistricts;
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

    public void setXMLOutputFileName(String XMLOutputFileName) {
        this.XMLOutputFileName = XMLOutputFileName;
    }

    public String getXMLOutputFileName() {
        return XMLOutputFileName;
    }

    public void setXMLInputFileName(String XMLInputFileName) {
        this.XMLInputFileName = XMLInputFileName;
    }

    public String getXMLInputFileName() {
        return XMLInputFileName;
    }

    public void setEffectiveFromDate(Date EffectiveFromDate) {
        this.EffectiveFromDate = EffectiveFromDate;
    }

    public Date getEffectiveFromDate() {
        return EffectiveFromDate;
    }

    public void setTimeLimitOption(RateChangeProcessTimeLimitOption TimeLimitOption) {
        this.TimeLimitOption = TimeLimitOption;
    }

    public RateChangeProcessTimeLimitOption getTimeLimitOption() {
        return TimeLimitOption;
    }

    public void setProcessStep(String ProcessStep) {
        this.ProcessStep = ProcessStep;
    }

    public String getProcessStep() {
        return ProcessStep;
    }

    public void setStepStartFlag(RateChangeProcessStepStartFlag StepStartFlag) {
        this.StepStartFlag = StepStartFlag;
    }

    public RateChangeProcessStepStartFlag getStepStartFlag() {
        return StepStartFlag;
    }

    public void setStepExecStatus(String StepExecStatus) {
        this.StepExecStatus = StepExecStatus;
    }

    public String getStepExecStatus() {
        return StepExecStatus;
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
