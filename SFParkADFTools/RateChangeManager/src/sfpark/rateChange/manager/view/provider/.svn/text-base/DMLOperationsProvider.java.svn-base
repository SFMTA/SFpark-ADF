package sfpark.rateChange.manager.view.provider;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import oracle.adf.share.logging.ADFLogger;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.blockRateSchedule.BlockRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.blockTimeBands.BlockTimeBandsDTO;
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
import sfpark.adf.tools.model.provider.BlockTimeBandsProvider;
import sfpark.adf.tools.model.provider.CalendarHeaderProvider;
import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.provider.RateChangeHeaderProvider;
import sfpark.adf.tools.model.provider.RateChangeProcessControlProvider;

import sfpark.adf.tools.model.provider.StoredProcedureProvider;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;

import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

import sfpark.rateChange.manager.view.helper.BlockTimeBandsWrapper;

/**
 * Description:
 * This class provides methods to select, update, and delete database data.
 * Some of the functionality in this makes calls to ODI functions (StoredProcedureProvider)
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111129-01 Mark Piller - Oracle Consulting  add finalizeRateChange()
 * 20111130-01 Mark Piller - Oracle Consulting  add resetRateChange()
 * 20120125-01 Mark Piller - Oracle Consulting  corrected bug - no status given when user clicks
 *                                              save and there were not changes to be persisted in the database
 * 20120316-01 Mark Piller - Oracle Consulting  corrected but - was using Reference ID but should have used Process ID                                             
 * 20120419-01 Mark Piller - Oracle Consulting  Chg LOGGER to ADFLogger
 * 20120419-02 Mark Piller - Oracle Consulting  Chg deleteRateChange from 1 parameter to 3 parameters
 */
public class DMLOperationsProvider {

    private static final String CLASSNAME =
        DMLOperationsProvider.class.getName();
    // 20120419-01  private static final Logger LOGGER = Logger.getLogger(CLASSNAME);
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(DMLOperationsProvider.class);

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

    public BlockTimeBandsWrapper getNewBlockTimeBandsWrapper(String blockID) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering getNewBlockTimeBandsWrapper");

        BlockTimeBandsWrapper wrapper = new BlockTimeBandsWrapper(blockID);

        wrapper.setMeterClass(AllowedValuesRetriever.getMeterClassDefaultValue());
        wrapper.setDateType(AllowedValuesRetriever.getDateTypeDefaultValue());
        wrapper.setOpenTime(TimeDisplayUtil.extractAnyTimeForDisplay(0));
        wrapper.setCloseTime(TimeDisplayUtil.extractAnyTimeForDisplay(2400));

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting getNewBlockTimeBandsWrapper");

        return wrapper;
    }

    /**
     * Generates a new Rate Change Header DTO with the necessary defaults
     *
     * @return
     */
    public RateChangeHeaderDTO getNewRateChangeHeaderDTO() {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering getNewRateChangeHeaderDTO");

        RateChangeHeaderDTO DTO = new RateChangeHeaderDTO();

        DTO.setAreaType(PMDistrictAreaType.PILOT);
        DTO.setCalendarID("0");
        DTO.setRateChangePolicy(AllowedValuesRetriever.getRateChgPolicyDefaultValue());

        Date minimumAllowedDate = SQLDateUtil.getNumOfDaysAfterToday(7);
        DTO.setPlannedChangeEffectiveDate(minimumAllowedDate);
        DTO.setMinimumAllowedDate(minimumAllowedDate);

        DTO.setStatus(RateChangeStatus.WORKING);

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting getNewRateChangeHeaderDTO");

        return DTO;
    }

    public RateChangeProcessControlDTO getNewRateChangeProcessControlDTO(RateChangeHeaderDTO rateChangeHeaderDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering getNewRateChangeProcessControlDTO");

        RateChangeProcessControlDTO DTO = new RateChangeProcessControlDTO();

        DTO.setLabelRateChangeReference(rateChangeHeaderDTO.getRateChangeReference());
        DTO.setRateChangeReferenceID(rateChangeHeaderDTO.getRateChangeReferenceID());
        DTO.setEffectiveFromDate(rateChangeHeaderDTO.getPlannedChangeEffectiveDate());
        DTO.setTimeLimitOption(RateChangeProcessTimeLimitOption.YES);
        DTO.setProcessStep(AllowedValuesRetriever.getProcessStepDefaultValue());
        DTO.setStepStartFlag(RateChangeProcessStepStartFlag.HOLD);
        DTO.setStepExecStatus(AllowedValuesRetriever.getProcessStepExecStatusDefaultValue());

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting getNewRateChangeProcessControlDTO");

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
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering editRateChangeRules");

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

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting editRateChangeRules");

        return performOperation(tableRecords);
    }

    public OperationStatus addBlockTimeBands(List<BlockTimeBandsDTO> toBeAddedBlockTimeBandsDTOs,
                                             List<BlockTimeBandsDTO> toBeDeletedBlockTimeBandsDTOs) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering addBlockTimeBands");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // DELETE old DTOs
        // Old rows need to be deleted before new rows can be added to avoid
        // unique constraint exception

        if (toBeDeletedBlockTimeBandsDTOs != null &&
            !toBeDeletedBlockTimeBandsDTOs.isEmpty()) {

            for (BlockTimeBandsDTO deleteDTO : toBeDeletedBlockTimeBandsDTOs) {
                tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                                 deleteDTO));
            }
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ADD new DTOs

        if (toBeAddedBlockTimeBandsDTOs != null &&
            !toBeAddedBlockTimeBandsDTOs.isEmpty()) {
            for (BlockTimeBandsDTO addDTO : toBeAddedBlockTimeBandsDTOs) {
                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                 addDTO));
            }
        }

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting addBlockTimeBands");

        return performOperation(tableRecords);
    }

    public OperationStatus editBlockTimeBands(List<BlockTimeBandsDTO> toBeEditedBlockTimeBandsDTOs) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering editBlockTimeBands");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        if (toBeEditedBlockTimeBandsDTOs != null &&
            !toBeEditedBlockTimeBandsDTOs.isEmpty()) {
            for (BlockTimeBandsDTO DTO : toBeEditedBlockTimeBandsDTOs) {

                BlockTimeBandsDTO originalDTO =
                    BlockTimeBandsProvider.INSTANCE.getBlockTimeBandsDTO(DTO.getBlockTimeBandID());

                if (!DTO.isSameAs(originalDTO)) {
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                     DTO));
                }

            }
        }

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting editBlockTimeBands");

        return performOperation(tableRecords);
    }

    public OperationStatus deleteTimeBands(List<BlockTimeBandsDTO> toBeDeletedBlockTimeBandsDTOs) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering deleteTimeBands");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        if (toBeDeletedBlockTimeBandsDTOs != null &&
            !toBeDeletedBlockTimeBandsDTOs.isEmpty()) {

            for (BlockTimeBandsDTO deleteDTO : toBeDeletedBlockTimeBandsDTOs) {
                tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                                 deleteDTO));
            }
        }

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting deleteTimeBands");

        return performOperation(tableRecords);
    }

    /**
     * Adds the Rate Change Header
     *
     * @param rateChangeHeaderDTO
     * @return
     */
    public OperationStatus addRateChangeHeader(RateChangeHeaderDTO rateChangeHeaderDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering addRateChangeHeader");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                         rateChangeHeaderDTO));

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting addRateChangeHeader");

        return performOperation(tableRecords);
    }

    public OperationStatus generateRateChange(RateChangeHeaderDTO rateChangeHeaderDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering generateRateChange");
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> generateRateChange() calling StoredProcedureProvider");
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> generateRateChange() rateChangeHeaderDTO.getRateChangeReferenceID() is " + rateChangeHeaderDTO.getRateChangeReferenceID());

        return StoredProcedureProvider.INSTANCE.generateRateChange(rateChangeHeaderDTO.getRateChangeReferenceID(),
                                                                   getLoggedInUsername());
    }


    // 20111129-01
    public OperationStatus finalizeRateChange(RateChangeHeaderDTO rateChangeHeaderDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering finalizeRateChange");
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> finalizeRateChange() calling StoredProcedureProvider");
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> finalizeRateChange() rateChangeHeaderDTO.getRateChangeReferenceID() is " + rateChangeHeaderDTO.getRateChangeReferenceID());
  
        return StoredProcedureProvider.INSTANCE.finalizeRateChange(rateChangeHeaderDTO.getRateChangeReferenceID(),
                                                                   getLoggedInUsername());
    }

    // 20111130-01
    public OperationStatus resetRateChange(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering resetRateChange");

        // 20120316-01 Corrected bug - was using Reference ID but should have been Procss ID
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> resetRateChange() calling StoredProcedureProvider with Process ID");
        // adfLogger.log(adfLogger.TRACE,"DEBUG  >> resetRateChange() rateChangeHeaderDTO.getRateChangeReferenceID() is " + rateChangeProcessControlDTO.getRateChangeReferenceID());
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> resetRateChange() rateChangeProcessControlDTO.getProcessID() is " + rateChangeProcessControlDTO.getProcessID());

        // 20120316-01 Corrected bug - was using Reference ID but should have been Procss ID
//        return StoredProcedureProvider.INSTANCE.resetRateChange(rateChangeProcessControlDTO.getRateChangeReferenceID(),
//                                                                   getLoggedInUsername());
        return StoredProcedureProvider.INSTANCE.resetRateChange(rateChangeProcessControlDTO.getProcessID(), getLoggedInUsername());
    }


    public OperationStatus deleteRateChange(RateChangeHeaderDTO rateChangeHeaderDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering deleteRateChange");

        // 20120419-02
        // return StoredProcedureProvider.INSTANCE.deleteRateChange(rateChangeHeaderDTO.getRateChangeReferenceID());
        return StoredProcedureProvider.INSTANCE.deleteRateChange(rateChangeHeaderDTO.getRateChangeReferenceID(), getLoggedInUsername(), LAST_UPDATED_PROGRAM);
    }

    public OperationStatus updateRateChangeHeader(RateChangeHeaderDTO rateChangeHeaderDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering editRateChangeHeader");

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

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting editRateChangeHeader");

        return performOperation(tableRecords);
    }

    public OperationStatus modifyBlockRateSchedule(BlockRateScheduleDTO blockRateScheduleDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering modifyBlockRateSchedule");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                         blockRateScheduleDTO));

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting modifyBlockRateSchedule");

        return performOperation(tableRecords);
    }

    /**
     * Adds the Rate Change Process Control
     *
     * @param rateChangeProcessControlDTO
     * @return
     */
    public OperationStatus addRateChangeProcessControl(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering addRateChangeProcessControl");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                         rateChangeProcessControlDTO));

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting addRateChangeProcessControl");

        return performOperation(tableRecords);
    }

    /**
     * Edits the Rate Change Process Control
     *
     * @param rateChangeProcessControlDTO
     * @return
     */
    public OperationStatus editRateChangeProcessControl(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering editRateChangeProcessControl");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        RateChangeProcessControlDTO originalDTO =
            RateChangeProcessControlProvider.INSTANCE.checkForProcessID(rateChangeProcessControlDTO.getProcessID()).getDTO();

        if (!rateChangeProcessControlDTO.isSameAs(originalDTO)) {
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             rateChangeProcessControlDTO));
        }

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting editRateChangeProcessControl");

        return performOperation(tableRecords);
    }

    /**
     * Executes the Rate Change Process Control
     * 
     * Sets STEP_START_FLAG = Y
     *
     * @param rateChangeProcessControlDTO
     * @return
     */
    public OperationStatus executeRateChangeProcessControl(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering executeRateChangeProcessControl");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        rateChangeProcessControlDTO.setStepStartFlag(RateChangeProcessStepStartFlag.INITIATE);

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                         rateChangeProcessControlDTO));

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting executeRateChangeProcessControl");

        return performOperation(tableRecords);
    }

    public OperationStatus deleteRateChangeProcessControl(RateChangeProcessControlDTO rateChangeProcessControlDTO) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> entering deleteRateChangeProcessControl");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                         rateChangeProcessControlDTO));

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> exiting deleteRateChangeProcessControl");

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
        } else {
            // table is empty - this means no changes were made to be saved
            // return successful
            // Mark Piller - 20120125-01
            operationStatus = OperationStatus.success();
        }

        return operationStatus;
    }

    private String getLoggedInUsername() {
        return ADFContext.getCurrent().getSecurityContext().getUserName();
    }
}
