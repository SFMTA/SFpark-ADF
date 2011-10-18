package sfpark.rateChange.manager.view.provider;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.blockRateSchedule.BlockRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeRulesDTO;
import sfpark.adf.tools.model.data.helper.CalendarStatus;
import sfpark.adf.tools.model.data.helper.PMDistrictAreaType;
import sfpark.adf.tools.model.data.helper.RateChangeProcessStepStartFlag;
import sfpark.adf.tools.model.data.helper.RateChangeProcessTimeLimitOption;
import sfpark.adf.tools.model.data.helper.RateChangeStatus;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;
import sfpark.adf.tools.model.provider.CalendarHeaderProvider;
import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.provider.RateChangeHeaderProvider;
import sfpark.adf.tools.model.provider.RateChangeProcessControlProvider;

import sfpark.adf.tools.model.provider.StoredProcedureProvider;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;

import sfpark.rateChange.manager.view.helper.BlockTimeBandTypeTO;

public class DMLOperationsProvider {

    private static final String CLASSNAME =
        DMLOperationsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private DMLOperationsProvider() {
        super();
    }

    public static final DMLOperationsProvider INSTANCE =
        new DMLOperationsProvider();

    private static final String LAST_UPDATED_PROGRAM =
        "Rate Change Manager Tool";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Methods to create new DTOs

    public BlockTimeBandTypeTO getNewBlockTimeBandTypeTO() {
        LOGGER.entering(CLASSNAME, "getNewBlockTimeBandTypeTO");

        BlockTimeBandTypeTO TO = new BlockTimeBandTypeTO();

        TO.setMeterClass(AllowedValuesRetriever.getMeterClassDefaultValue());
        TO.setDateType(AllowedValuesRetriever.getDateTypeDefaultValue());

        LOGGER.exiting(CLASSNAME, "getNewBlockTimeBandTypeTO");

        return TO;
    }

    /**
     * Generates a new Rate Change Header DTO with the necessary defaults
     *
     * @return
     */
    public RateChangeHeaderDTO getNewRateChangeHeaderDTO() {
        LOGGER.entering(CLASSNAME, "getNewRateChangeHeaderDTO");

        RateChangeHeaderDTO DTO = new RateChangeHeaderDTO();

        DTO.setAreaType(PMDistrictAreaType.PILOT);
        DTO.setCalendarID("0");
        DTO.setRateChangePolicy(AllowedValuesRetriever.getRateChgPolicyDefaultValue());

        Date minimumAllowedDate = SQLDateUtil.getNumOfDaysAfterToday(7);
        DTO.setPlannedChangeEffectiveDate(minimumAllowedDate);
        DTO.setMinimumAllowedDate(minimumAllowedDate);

        DTO.setStatus(RateChangeStatus.WORKING);

        LOGGER.exiting(CLASSNAME, "getNewRateChangeHeaderDTO");

        return DTO;
    }

    public RateChangeProcessControlDTO getNewRateChangeProcessControlDTO(RateChangeHeaderDTO rateChangeHeaderDTO) {
        LOGGER.entering(CLASSNAME, "getNewRateChangeProcessControlDTO");

        RateChangeProcessControlDTO DTO = new RateChangeProcessControlDTO();

        DTO.setLabelRateChangeReference(rateChangeHeaderDTO.getRateChangeReference());
        DTO.setRateChangeReferenceID(rateChangeHeaderDTO.getRateChangeReferenceID());
        DTO.setEffectiveFromDate(rateChangeHeaderDTO.getPlannedChangeEffectiveDate());
        DTO.setTimeLimitOption(RateChangeProcessTimeLimitOption.YES);
        DTO.setProcessStep(AllowedValuesRetriever.getProcessStepDefaultValue());
        DTO.setStepStartFlag(RateChangeProcessStepStartFlag.HOLD);
        DTO.setStepExecStatus(AllowedValuesRetriever.getProcessStepExecStatusDefaultValue());

        LOGGER.exiting(CLASSNAME, "getNewRateChangeProcessControlDTO");

        return DTO;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Actual operations

    public OperationStatus editRateChangeRules(String rateChangeType,
                                               Date effectiveFromDate,
                                               List<RateChangeRulesDTO> applyList,
                                               List<RateChangeRulesDTO> replaceList) {
        LOGGER.entering(CLASSNAME, "editRateChangeRules");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        for (RateChangeRulesDTO DTO : applyList) {
            DTO.setRateChangeType(rateChangeType);
            DTO.setEffectiveFromDate(effectiveFromDate);
            DTO.setEffectiveToDate(SQLDateUtil.getMaximumDate());

            tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                             DTO));
        }

        if (replaceList != null && !replaceList.isEmpty()) {
            Date effectiveToDate =
                SQLDateUtil.getPreviousDateFor(effectiveFromDate);

            for (RateChangeRulesDTO DTO : replaceList) {
                DTO.setEffectiveToDate(effectiveToDate);

                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                 DTO));
            }
        }

        LOGGER.exiting(CLASSNAME, "editRateChangeRules");

        return performOperation(tableRecords);
    }

    /**
     * Adds the Rate Change Header
     *
     * @param rateChangeHeaderDTO
     * @return
     */
    public OperationStatus addRateChangeHeader(RateChangeHeaderDTO rateChangeHeaderDTO) {
        LOGGER.entering(CLASSNAME, "addRateChangeHeader");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                         rateChangeHeaderDTO));

        LOGGER.exiting(CLASSNAME, "addRateChangeHeader");

        return performOperation(tableRecords);
    }

    public OperationStatus generateRateChange(RateChangeHeaderDTO rateChangeHeaderDTO) {
        LOGGER.in(CLASSNAME, "generateRateChange");

        return StoredProcedureProvider.INSTANCE.generateRateChange(rateChangeHeaderDTO.getRateChangeReferenceID(),
                                                                   getLoggedInUsername());
    }

    public OperationStatus deleteRateChange(RateChangeHeaderDTO rateChangeHeaderDTO) {
        LOGGER.in(CLASSNAME, "deleteRateChange");

        return StoredProcedureProvider.INSTANCE.deleteRateChange(rateChangeHeaderDTO.getRateChangeReferenceID());
    }

    public OperationStatus updateRateChangeHeader(RateChangeHeaderDTO rateChangeHeaderDTO) {
        LOGGER.entering(CLASSNAME, "editRateChangeHeader");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        RateChangeHeaderDTO originalDTO =
            RateChangeHeaderProvider.INSTANCE.checkForRateChangeReferenceID(rateChangeHeaderDTO.getRateChangeReferenceID()).getDTO();

        if (!rateChangeHeaderDTO.isSameAs(originalDTO)) {

            switch (rateChangeHeaderDTO.getStatus()) {
            case WORKING:
                {
                    rateChangeHeaderDTO.setStatus(RateChangeStatus.SUBMITTED);
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                     rateChangeHeaderDTO));

                }
                break;

            case SUBMITTED:
                {
                    rateChangeHeaderDTO.setStatus(RateChangeStatus.APPROVED);
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                     rateChangeHeaderDTO));

                    CalendarHeaderDTO calendarHeaderDTO =
                        CalendarHeaderProvider.INSTANCE.checkForCalendarID(rateChangeHeaderDTO.getCalendarID()).getDTO();

                    calendarHeaderDTO.setStatus(CalendarStatus.LOCKED);

                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                     calendarHeaderDTO));

                }
                break;

            default:
                {
                    // Do nothing
                }
                break;
            }

        }

        LOGGER.exiting(CLASSNAME, "editRateChangeHeader");

        return performOperation(tableRecords);
    }

    public OperationStatus modifyBlockRateSchedule(BlockRateScheduleDTO blockRateScheduleDTO) {
        LOGGER.entering(CLASSNAME, "modifyBlockRateSchedule");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                         blockRateScheduleDTO));

        LOGGER.exiting(CLASSNAME, "modifyBlockRateSchedule");

        return performOperation(tableRecords);
    }

    /**
     * Adds the Rate Change Process Control
     *
     * @param rateChangeProcessControlDTO
     * @return
     */
    public OperationStatus addRateChangeProcessControl(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        LOGGER.entering(CLASSNAME, "addRateChangeProcessControl");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                         rateChangeProcessControlDTO));

        LOGGER.exiting(CLASSNAME, "addRateChangeProcessControl");

        return performOperation(tableRecords);
    }

    /**
     * Edits the Rate Change Process Control
     *
     * @param rateChangeProcessControlDTO
     * @return
     */
    public OperationStatus editRateChangeProcessControl(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        LOGGER.entering(CLASSNAME, "editRateChangeProcessControl");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        RateChangeProcessControlDTO originalDTO =
            RateChangeProcessControlProvider.INSTANCE.checkForProcessID(rateChangeProcessControlDTO.getProcessID()).getDTO();

        if (!rateChangeProcessControlDTO.isSameAs(originalDTO)) {
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             rateChangeProcessControlDTO));
        }

        LOGGER.exiting(CLASSNAME, "editRateChangeProcessControl");

        return performOperation(tableRecords);
    }

    /**
     * Executes the Rate Change Process Control
     *
     * @param rateChangeProcessControlDTO
     * @return
     */
    public OperationStatus executeRateChangeProcessControl(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        LOGGER.entering(CLASSNAME, "executeRateChangeProcessControl");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        rateChangeProcessControlDTO.setStepStartFlag(RateChangeProcessStepStartFlag.INITIATE);

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                         rateChangeProcessControlDTO));

        LOGGER.exiting(CLASSNAME, "executeRateChangeProcessControl");

        return performOperation(tableRecords);
    }

    public OperationStatus deleteRateChangeProcessControl(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        LOGGER.entering(CLASSNAME, "deleteRateChangeProcessControl");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                         rateChangeProcessControlDTO));

        LOGGER.exiting(CLASSNAME, "deleteRateChangeProcessControl");

        return performOperation(tableRecords);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private OperationStatus performOperation(List<TableRecord> tableRecords) {
        OperationStatus operationStatus = null;

        if (!tableRecords.isEmpty()) {

            operationStatus =
                    ProviderWrapper.INSTANCE.performOperationOnRecords(tableRecords,
                                                                       getLoggedInUsername(),
                                                                       LAST_UPDATED_PROGRAM);
        }

        return operationStatus;
    }

    private String getLoggedInUsername() {
        return ADFContext.getCurrent().getSecurityContext().getUserName();
    }
}
