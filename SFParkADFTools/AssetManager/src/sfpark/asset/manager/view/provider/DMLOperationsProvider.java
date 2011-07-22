package sfpark.asset.manager.view.provider;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.blockface.BlockfaceDO;
import sfpark.adf.tools.model.data.dO.meterSchedValidations.MeterSchedValidationsDO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.meterRateSchedule.MeterRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryBulkDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.data.helper.EffectiveDateCalculator;
import sfpark.adf.tools.model.data.helper.MeterRateType;
import sfpark.adf.tools.model.data.tO.meterOPSchedule.MeterOPScheduleBulkTO;
import sfpark.adf.tools.model.data.tO.meterRateSchedule.MeterRateScheduleBulkTO;
import sfpark.adf.tools.model.data.tO.parkingSpaceInventory.ParkingSpaceInventoryBulkTO;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.helper.dO.PCOBeatsDOStatus;
import sfpark.adf.tools.model.helper.dO.RateAreasDOStatus;
import sfpark.adf.tools.model.provider.MeterOPScheduleProvider;
import sfpark.adf.tools.model.provider.MeterRateScheduleProvider;
import sfpark.adf.tools.model.provider.PCOBeatsProvider;
import sfpark.adf.tools.model.provider.ParkingSpaceInventoryProvider;
import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.provider.RateAreasProvider;
import sfpark.adf.tools.model.status.OperationStatus;

import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;

import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;
import sfpark.adf.tools.utilities.ui.DayUI;

import sfpark.asset.manager.view.util.DataRepositoryUtil;

public final class DMLOperationsProvider {

    private static final String CLASSNAME =
        DMLOperationsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private DMLOperationsProvider() {
        super();
    }

    public static final DMLOperationsProvider INSTANCE =
        new DMLOperationsProvider();

    private static final String LAST_UPDATED_PROGRAM = "Asset Manager Tool";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Methods to create new DTOs

    /**
     * Generates a new Parking Space Inventory DTO with the necessary defaults
     * --- Uses BlockfaceDO to get the Street information
     * --- Sets Longitude & Latitude values
     *
     * @param blockfaceDO
     * @param longitude
     * @param latitude
     * @return
     */
    public ParkingSpaceInventoryDTO getNewParkingSpaceInventoryDTO(BlockfaceDO blockfaceDO,
                                                                   String longitude,
                                                                   String latitude) {
        LOGGER.entering(CLASSNAME, "getNewParkingSpaceInventoryDTO");

        ParkingSpaceInventoryDTO DTO = new ParkingSpaceInventoryDTO();

        String tempPostID =
            CommonUtils.generateWorkablePostIDFromBlockfaceID(blockfaceDO.getBlockfaceID());

        DTO.setPostID(tempPostID);

        DTO.setSensorFlag(DataRepositoryUtil.getSensorFlagDefaultValue());
        DTO.setOnOffStreetType("ON");
        DTO.setOSPID("0");
        DTO.setJurisdiction(DataRepositoryUtil.getJurisdictionDefaultValue());
        DTO.setCapColor(DataRepositoryUtil.getCapColorDefaultValue());

        DTO.setActiveMeterFlag(DataRepositoryUtil.getActiveMeterFlagDefaultValue());
        DTO.setReasonCode(DataRepositoryUtil.getReasonCodeDefaultValue());

        DTO.setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDONULLValue());

        DTO.setOldRateArea("-");
        DTO.setPCOBeat("-");

        DTO.setLongitude(longitude);
        DTO.setLatitude(latitude);

        DTO.setBlockfaceID(blockfaceDO.getBlockfaceID());
        DTO.setBlockfaceDO(blockfaceDO);

        DTO.setParityDigitPosition(2);

        DTO.setCNNID("0");

        DTO.setStreetNum(CommonUtils.generateStreetNumFromPostID(tempPostID));

        LOGGER.exiting(CLASSNAME, "getNewParkingSpaceInventoryDTO");

        return DTO;
    }

    /**
     * Important Points
     *    ---SCHED_TYPE
     *       ---OP   COLOR_RULE_APPLIED = CAP_COLOR
     *       ---TOW  COLOR_RULE_APPLIED = NULL
     *       ---ALT  COLOR_RULE_APPLIED = Either fixed or GREY
     *
     * @param meterSchedValidationsDO
     * @param parkingSpaceID
     * @param priority
     * @param effectiveFromDate
     * @param colorRuleAppliedForOP
     * @return
     */
    public MeterOPScheduleDTO getNewMeterScheduleDTO(MeterSchedValidationsDO meterSchedValidationsDO,
                                                     String parkingSpaceID,
                                                     int priority,
                                                     Date effectiveFromDate,
                                                     String colorRuleAppliedForOP) {
        LOGGER.entering(CLASSNAME, "getNewMeterScheduleDTO");

        MeterOPScheduleDTO DTO = new MeterOPScheduleDTO();

        DTO.setMeterOPSchedID(null);
        DTO.setParkingSpaceID(parkingSpaceID);
        DTO.setOverride(false);

        DTO.setScheduleType(meterSchedValidationsDO.getScheduleType());
        DTO.setSchedulePriority(priority);

        DTO.setAlternateAddlDesc(meterSchedValidationsDO.getAltAddlDesc());

        DTO.setEffectiveFromDate(effectiveFromDate);
        DTO.setMinimumEffectiveFromDateForBulk(effectiveFromDate);
        DTO.setEffectiveToDate(SQLDateUtil.getMaximumDate());

        DTO.setFromTime(TimeDisplayUtil.extractFromTimeForDisplay(0));
        if (DTO.isEditableToTime()) {
            DTO.setToTime(TimeDisplayUtil.extractToTimeForDisplay(30));
        } else {
            DTO.setToTime(TimeDisplayUtil.extractToTimeForDisplay(0));
        }

        DTO.setTimeLimit(0);
        DTO.setPrePaymentTime("");

        if (meterSchedValidationsDO.isColorRuleAppliedEditable()) {

            DTO.setEditableColorRuleApplied(true);
            DTO.setColorRuleApplied(DataRepositoryUtil.getColorRuleAppliedDefaultValue());

        } else {

            DTO.setEditableColorRuleApplied(false);

            if (meterSchedValidationsDO.getScheduleType().isScheduleOP()) {
                DTO.setColorRuleApplied(colorRuleAppliedForOP);
            } else {
                DTO.setColorRuleApplied(meterSchedValidationsDO.getColorRuleApplied());
            }
        }

        if (StringUtil.isBlank(meterSchedValidationsDO.getDaysAppliedTxt())) {
            DTO.setEditableDaysApplied(true);
            DTO.setWeekDaysApplied(DayUI.DAYS_APPLIED_SINGLE_STRING_LIST);

        } else {
            DTO.setEditableDaysApplied(false);
            DTO.setDaysApplied(meterSchedValidationsDO.getDaysAppliedTxt());

        }

        LOGGER.exiting(CLASSNAME, "getNewMeterScheduleDTO");

        return DTO;
    }

    public MeterRateScheduleDTO getNewMeterRateScheduleDTO(MeterRateType rateType,
                                                           String parkingSpaceID,
                                                           int priority,
                                                           Date effectiveFromDate,
                                                           String blockfaceID) {
        LOGGER.entering(CLASSNAME, "getNewMeterRateScheduleDTO");

        MeterRateScheduleDTO DTO = new MeterRateScheduleDTO();

        DTO.setMeterRateSchedID(null);
        DTO.setParkingSpaceID(parkingSpaceID);
        DTO.setOverride(false);

        DTO.setRateType(rateType);
        DTO.setSchedulePriority(priority);

        DTO.setEffectiveFromDate(effectiveFromDate);
        DTO.setMinimumEffectiveFromDateForBulk(effectiveFromDate);
        DTO.setEffectiveToDate(SQLDateUtil.getMaximumDate());

        DTO.setFromTime(TimeDisplayUtil.extractFromTimeForDisplay(0));
        if (DTO.isEditableToTime()) {
            DTO.setToTime(TimeDisplayUtil.extractToTimeForDisplay(30));
        } else {
            DTO.setToTime(TimeDisplayUtil.extractToTimeForDisplay(0));
        }

        DTO.setRate(0f);

        DTO.setPSGroupID("0");
        DTO.setEventID("0");

        DTO.setBlockID(CommonUtils.extractBlockIDFromBlockfaceID(blockfaceID));

        if (rateType.isRateTypeH()) {
            DTO.setEditableDaysApplied(true);
            DTO.setWeekDaysApplied(DayUI.DAYS_APPLIED_SINGLE_STRING_LIST);
        } else {
            DTO.setEditableDaysApplied(false);
            DTO.setDaysApplied(" ");
        }

        LOGGER.exiting(CLASSNAME, "getNewMeterRateScheduleDTO");

        return DTO;
    }

    public ParkingSpaceInventoryBulkTO getNewParkingSpaceInventoryBulkTO() {
        LOGGER.entering(CLASSNAME, "getNewParkingSpaceInventoryBulkTO");

        ParkingSpaceInventoryBulkTO TO = new ParkingSpaceInventoryBulkTO();

        TO.setSensorFlag(DataRepositoryUtil.getSensorFlagDefaultValue());
        TO.setCapColor(DataRepositoryUtil.getCapColorDefaultValue());

        TO.setActiveMeterFlag(DataRepositoryUtil.getActiveMeterFlagBulkDefaultValue());
        TO.setReasonCode(DataRepositoryUtil.getReasonCodeDefaultValue());
        TO.setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDODefaultValue());

        LOGGER.exiting(CLASSNAME, "getNewParkingSpaceInventoryBulkTO");

        return TO;
    }

    public MeterOPScheduleBulkTO getNewMeterOPScheduleBulkTO() {
        LOGGER.entering(CLASSNAME, "getNewMeterOPScheduleBulkTO");

        MeterOPScheduleBulkTO TO = new MeterOPScheduleBulkTO();

        TO.setDeleteAllOPSchedules(false);
        TO.setDeleteAllALTSchedules(false);
        TO.setDeleteAllTOWSchedules(false);

        LOGGER.exiting(CLASSNAME, "getNewMeterOPScheduleBulkTO");

        return TO;
    }

    public MeterRateScheduleBulkTO getNewMeterRateScheduleBulkTO() {
        LOGGER.entering(CLASSNAME, "getNewMeterRateScheduleBulkTO");

        MeterRateScheduleBulkTO TO = new MeterRateScheduleBulkTO();

        TO.setDeleteAllBaseRates(false);
        TO.setDeleteAllHourlyRates(false);

        LOGGER.exiting(CLASSNAME, "getNewMeterRateScheduleBulkTO");

        return TO;
    }

    public String getOldRateArea(ParkingSpaceInventoryDTO DTO) {

        String activeMeterStatusFlag = DTO.getActiveMeterFlag();
        String capColor = DTO.getCapColor();
        String longitude = DTO.getLongitude();
        String latitude = DTO.getLatitude();

        if (StringUtil.areEqual(activeMeterStatusFlag, "U")) {
            return "-";
        }

        if (StringUtil.areEqual(capColor, "Brown")) {
            return "Tour Bus";
        }

        RateAreasDOStatus rateAreasDOStatus =
            RateAreasProvider.INSTANCE.checkForRateArea(longitude, latitude);

        if (!rateAreasDOStatus.existsDO()) {
            LOGGER.warning("Retrieved Old Rate Area was null. Should not have happened. Returning -.");
            return "-";
        }

        if (StringUtil.areEqual(capColor, "Black")) {
            return rateAreasDOStatus.getDO().getMCName();
        }

        return rateAreasDOStatus.getDO().getAreaName();
    }

    public String getPCOBeat(ParkingSpaceInventoryDTO DTO) {

        String longitude = DTO.getLongitude();
        String latitude = DTO.getLatitude();

        PCOBeatsDOStatus pcoBeatsDOStatus =
            PCOBeatsProvider.INSTANCE.checkForPCOBeat(longitude, latitude);

        if (!pcoBeatsDOStatus.existsDO()) {
            LOGGER.warning("Retrieved PCO Beat was null. Should not have happened. Returning -.");
            return "-";
        }

        return pcoBeatsDOStatus.getDO().getBeatName();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Actual operations

    /**
     * Adds the Parking Space
     *
     * @param parkingSpaceInventoryDTO
     * @return
     */
    public OperationStatus addParkingSpace(ParkingSpaceInventoryDTO parkingSpaceInventoryDTO) {
        LOGGER.entering(CLASSNAME, "addParkingSpace");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                         parkingSpaceInventoryDTO));

        LOGGER.exiting(CLASSNAME, "addParkingSpace");

        return performOperation(tableRecords);
    }

    /**
     * Applies the necessary business logic for the Edit Mode of Parking Space.
     *
     * This method returns NULL when no changes are made to the original data
     *
     * @param parkingSpaceInventoryDTO
     * @param meterOPScheduleDTOs
     * @param meterRateScheduleDTOs
     * @return
     */
    public OperationStatus editParkingSpace(ParkingSpaceInventoryDTO parkingSpaceInventoryDTO,
                                            List<MeterOPScheduleDTO> meterOPScheduleDTOs,
                                            List<MeterRateScheduleDTO> meterRateScheduleDTOs) {
        LOGGER.entering(CLASSNAME, "editParkingSpace");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Parking Space

        ParkingSpaceInventoryDTO originalParkingSpaceInventoryDTO =
            ParkingSpaceInventoryProvider.INSTANCE.checkForParkingSpace(parkingSpaceInventoryDTO.getParkingSpaceID()).getDTO();

        if (!parkingSpaceInventoryDTO.isSameAs(originalParkingSpaceInventoryDTO)) {
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             parkingSpaceInventoryDTO));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter OP Schedules

        if (meterOPScheduleDTOs != null && !meterOPScheduleDTOs.isEmpty()) {
            // Add the necessary Meter OP Schedule Records
            for (MeterOPScheduleDTO meterOPScheduleDTO : meterOPScheduleDTOs) {

                if (meterOPScheduleDTO.isNewRecord()) {
                    LOGGER.debug("This is a NEW record. So just add.");
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     meterOPScheduleDTO));

                } else {
                    LOGGER.debug("This is an Existing record. Apply business logic before update.");

                    if (meterOPScheduleDTO.isOverride()) {
                        LOGGER.debug("User override encountered. Simply update the record without archiving.");
                        tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                         meterOPScheduleDTO));

                    } else {
                        LOGGER.debug("Perform archiving.");

                        MeterOPScheduleDTO originalMeterOPScheduleDTO =
                            MeterOPScheduleProvider.INSTANCE.getMeterScheduleFor(meterOPScheduleDTO.getMeterOPSchedID());

                        switch (meterOPScheduleDTO.getWhatChanged(originalMeterOPScheduleDTO)) {

                        case BOTH_DATES:
                            {
                                // Update original DTO with TO date as previous date to modifiedDTO's FROM date
                                // Insert modifiedDTO as new record

                                java.sql.Date newToDate =
                                    EffectiveDateCalculator.getEffectiveToDateWhenWhatChangedIsBothDates(meterOPScheduleDTO.getEffectiveFromDate(),
                                                                                                         originalMeterOPScheduleDTO.getEffectiveFromDate());

                                originalMeterOPScheduleDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalMeterOPScheduleDTO));
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 meterOPScheduleDTO));
                            }
                            break;

                        case ONLY_FROM_DATE:
                            {
                                // Update original DTO with TO date as previous date to modifiedDTO's FROM date
                                // Insert modifiedDTO as new record

                                java.sql.Date newToDate =
                                    EffectiveDateCalculator.getEffectiveToDateWhenWhatChangedIsOnlyFromDate(meterOPScheduleDTO.getEffectiveFromDate(),
                                                                                                            originalMeterOPScheduleDTO.getEffectiveFromDate());

                                originalMeterOPScheduleDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalMeterOPScheduleDTO));
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 meterOPScheduleDTO));
                            }
                            break;

                        case ONLY_TO_DATE:
                            {
                                // Update modifiedDTO

                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 meterOPScheduleDTO));
                            }
                            break;

                        case NOT_DATES:
                            {
                                // Update original DTO with TO date as yesterday
                                // Insert modifiedDTO as new record with FROM date as today

                                EffectiveDateCalculator.EffectiveDates effectiveDates =
                                    EffectiveDateCalculator.getEffectiveDatesWhenWhatChangedIsNotDates(originalMeterOPScheduleDTO.getEffectiveFromDate(),
                                                                                                       originalMeterOPScheduleDTO.getEffectiveToDate());

                                originalMeterOPScheduleDTO.setEffectiveToDate(effectiveDates.getNewToDate());
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalMeterOPScheduleDTO));

                                meterOPScheduleDTO.setEffectiveFromDate(effectiveDates.getNewFromDate());
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 meterOPScheduleDTO));
                            }
                            break;

                        case NOTHING:
                            // Do nothing
                            break;
                        }
                    }
                }
            }
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter Rate Schedules

        if (meterRateScheduleDTOs != null &&
            !meterRateScheduleDTOs.isEmpty()) {
            // Add the necessary Meter Rate Schedule Records
            for (MeterRateScheduleDTO meterRateScheduleDTO :
                 meterRateScheduleDTOs) {

                if (meterRateScheduleDTO.isNewRecord()) {
                    LOGGER.debug("This is a NEW Rate record. So just add.");
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     meterRateScheduleDTO));

                } else {
                    LOGGER.debug("This is an Existing Rate record. Apply business logic before update.");
                    if (meterRateScheduleDTO.isOverride()) {
                        LOGGER.debug("User override encountered. Simply update the record without archiving.");
                        tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                         meterRateScheduleDTO));

                    } else {
                        LOGGER.debug("Perform archiving.");

                        MeterRateScheduleDTO originalMeterRateScheduleDTO =
                            MeterRateScheduleProvider.INSTANCE.getMeterRateScheduleFor(meterRateScheduleDTO.getMeterRateSchedID());

                        switch (meterRateScheduleDTO.getWhatChanged(originalMeterRateScheduleDTO)) {

                        case BOTH_DATES:
                            {
                                // Update original DTO with TO date as previous date to modified DTO's FROM date
                                // Insert modifiedDTO as new record

                                java.sql.Date newToDate =
                                    EffectiveDateCalculator.getEffectiveToDateWhenWhatChangedIsBothDates(meterRateScheduleDTO.getEffectiveFromDate(),
                                                                                                         originalMeterRateScheduleDTO.getEffectiveFromDate());

                                originalMeterRateScheduleDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalMeterRateScheduleDTO));
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 meterRateScheduleDTO));
                            }
                            break;

                        case ONLY_FROM_DATE:
                            {
                                // Update original DTO with TO date as previous date to modifiedDTO's FROM date
                                // Insert modifiedDTO as new record

                                java.sql.Date newToDate =
                                    EffectiveDateCalculator.getEffectiveToDateWhenWhatChangedIsOnlyFromDate(meterRateScheduleDTO.getEffectiveFromDate(),
                                                                                                            originalMeterRateScheduleDTO.getEffectiveFromDate());

                                originalMeterRateScheduleDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalMeterRateScheduleDTO));
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 meterRateScheduleDTO));
                            }
                            break;

                        case ONLY_TO_DATE:
                            {
                                // Update modifiedDTO

                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 meterRateScheduleDTO));
                            }
                            break;

                        case NOT_DATES:
                            {
                                // Update original DTO with TO date as yesterday
                                // Insert modifiedDTO as new record with FROM date as today

                                EffectiveDateCalculator.EffectiveDates effectiveDates =
                                    EffectiveDateCalculator.getEffectiveDatesWhenWhatChangedIsNotDates(originalMeterRateScheduleDTO.getEffectiveFromDate(),
                                                                                                       originalMeterRateScheduleDTO.getEffectiveToDate());

                                originalMeterRateScheduleDTO.setEffectiveToDate(effectiveDates.getNewToDate());
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalMeterRateScheduleDTO));

                                meterRateScheduleDTO.setEffectiveFromDate(effectiveDates.getNewFromDate());
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 meterRateScheduleDTO));
                            }
                            break;

                        case NOTHING:
                            // Do nothing
                            break;
                        }
                    }
                }
            }
        }

        LOGGER.exiting(CLASSNAME, "editParkingSpace");

        return performOperation(tableRecords);
    }

    public OperationStatus editBulkParkingSpace(ParkingSpaceInventoryBulkTO parkingSpaceInventoryBulkTO,
                                                MeterOPScheduleBulkTO meterOPScheduleBulkTO,
                                                List<MeterOPScheduleDTO> meterSchedules,
                                                MeterRateScheduleBulkTO meterRateScheduleBulkTO,
                                                List<MeterRateScheduleDTO> meterRates) {
        return null; // TODO This is faulty
    }

    /**
     * Applies the necessary business logic for the Bulk Edit Mode of Parking Space.
     *
     * This method returns NULL when no changes are made to the original data
     *
     * @deprecated
     * @param parkingSpaceInventoryBulkDTO
     * @param meterOPScheduleBulkTO
     * @param meterSchedules
     * @return
     */
    public OperationStatus editBulkParkingSpace(ParkingSpaceInventoryBulkDTO parkingSpaceInventoryBulkDTO,
                                                MeterOPScheduleBulkTO meterOPScheduleBulkTO,
                                                List<MeterOPScheduleDTO> meterSchedules) {
        LOGGER.entering(CLASSNAME, "editBulkParkingSpace");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Retrieving Parking Spaces

        List<ParkingSpaceInventoryDTO> originalParkingSpaceInventoryDTOs =
            ParkingSpaceInventoryProvider.INSTANCE.getParkingSpaceInventoryDTOs(parkingSpaceInventoryBulkDTO.getParkingSpaceIDList());

        if (originalParkingSpaceInventoryDTOs.isEmpty()) {
            return new OperationStatus(OperationStatus.Type.BATCH_FAILURE,
                                       new Exception("Could not load Parking Spaces"));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Parking Space

        // TODO
        ParkingSpaceInventoryBulkDTO originalParkingSpaceInventoryBulkDTO =
            new ParkingSpaceInventoryBulkDTO();

        if (!parkingSpaceInventoryBulkDTO.isSameAs(originalParkingSpaceInventoryBulkDTO)) {

            if (parkingSpaceInventoryBulkDTO.isToBeUpdatedMeterDetails()) {
                // When Meter Details are being updated, then check for Multi Space

                if (parkingSpaceInventoryBulkDTO.getMeterDetails().getMeterType().contains("MS")) {
                    // Check to see if POST_ID and MS_PAY_STATION_ID work

                    for (ParkingSpaceInventoryDTO originalParkingSpaceInventoryDTO :
                         originalParkingSpaceInventoryDTOs) {
                        if (!CommonUtils.willPostIDAndPayStationIDWork(originalParkingSpaceInventoryDTO.getPostID(),
                                                                       parkingSpaceInventoryBulkDTO.getMSPayStationID(),
                                                                       originalParkingSpaceInventoryDTO.getOnOffStreetType())) {

                            LOGGER.debug("Combination does NOT work");
                            return new OperationStatus(OperationStatus.Type.UPDATE_FAILURE,
                                                       new Exception(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_work_postid_and_mspaystationid)));
                        }
                    }

                }

            }

            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             parkingSpaceInventoryBulkDTO));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter Schedules

        List<MeterOPScheduleDTO> deleteMeterSchedules =
            new ArrayList<MeterOPScheduleDTO>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter OP Schedules

        if (meterOPScheduleBulkTO.isDeleteAllOPSchedules()) {
            // All previous meter OP schedules should be deleted
            List<MeterOPScheduleDTO> originalMeterOPSchedules =
                MeterOPScheduleProvider.INSTANCE.getMeterOPScheduleDTOs(parkingSpaceInventoryBulkDTO.getParkingSpaceIDList());

            deleteMeterSchedules.addAll(originalMeterOPSchedules);
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter ALT Schedules

        if (meterOPScheduleBulkTO.isDeleteAllALTSchedules()) {
            // All previous meter ALT schedules should be deleted
            List<MeterOPScheduleDTO> originalMeterALTSchedules =
                MeterOPScheduleProvider.INSTANCE.getMeterALTScheduleDTOs(parkingSpaceInventoryBulkDTO.getParkingSpaceIDList());

            deleteMeterSchedules.addAll(originalMeterALTSchedules);
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter TOW Schedules

        if (meterOPScheduleBulkTO.isDeleteAllTOWSchedules()) {
            // All previous meter TOW schedules should be deleted
            List<MeterOPScheduleDTO> originalMeterTOWSchedules =
                MeterOPScheduleProvider.INSTANCE.getMeterTOWScheduleDTOs(parkingSpaceInventoryBulkDTO.getParkingSpaceIDList());

            deleteMeterSchedules.addAll(originalMeterTOWSchedules);
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter Schedules

        // DELETE old meter schedules
        for (MeterOPScheduleDTO deleteMeterSchedule : deleteMeterSchedules) {

            java.sql.Date newToDate =
                EffectiveDateCalculator.getBulkEffectiveToDateWhenDeleting(deleteMeterSchedule.getEffectiveFromDate(),
                                                                           deleteMeterSchedule.getEffectiveToDate());

            deleteMeterSchedule.setEffectiveToDate(newToDate);
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             deleteMeterSchedule));
        }

        // ADD current meter schedules to all Parking Spaces
        if (meterSchedules != null && !meterSchedules.isEmpty()) {

            for (ParkingSpaceInventoryDTO originalParkingSpaceInventoryDTO :
                 originalParkingSpaceInventoryDTOs) {

                for (MeterOPScheduleDTO meterSchedule : meterSchedules) {

                    MeterOPScheduleDTO DTO =
                        MeterOPScheduleDTO.copy(meterSchedule);

                    DTO.setParkingSpaceID(originalParkingSpaceInventoryDTO.getParkingSpaceID());

                    if (DTO.getScheduleType().isScheduleOP()) {
                        if (parkingSpaceInventoryBulkDTO.isToBeUpdatedCapColor()) {
                            DTO.setColorRuleApplied(parkingSpaceInventoryBulkDTO.getCapColor());
                        } else {
                            DTO.setColorRuleApplied(originalParkingSpaceInventoryDTO.getCapColor());
                        }
                    }

                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     DTO));
                }
            }
        }

        LOGGER.exiting(CLASSNAME, "editBulkParkingSpace");

        return performOperation(tableRecords);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

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
