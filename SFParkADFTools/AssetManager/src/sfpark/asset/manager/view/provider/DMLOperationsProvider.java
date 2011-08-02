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
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.data.helper.EffectiveDateCalculator;
import sfpark.adf.tools.model.data.helper.MeterRateType;
import sfpark.adf.tools.model.data.tO.meterOPSchedule.MeterOPScheduleBulkTO;
import sfpark.adf.tools.model.data.tO.meterRateSchedule.MeterRateScheduleBulkTO;
import sfpark.adf.tools.model.data.tO.parkingSpaceInventory.ParkingSpaceInventoryBulkTO;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.helper.dO.PCOBeatsDOStatus;
import sfpark.adf.tools.model.helper.dO.RateAreasDOStatus;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
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

        DTO.setSensorFlag(AllowedValuesProvider.getSensorFlagDefaultValue());
        DTO.setOnOffStreetType("ON");
        DTO.setOSPID("0");
        DTO.setJurisdiction(AllowedValuesProvider.getJurisdictionDefaultValue());
        DTO.setCapColor(AllowedValuesProvider.getCapColorDefaultValue());

        DTO.setActiveMeterFlag(AllowedValuesProvider.getActiveMeterFlagDefaultValue());
        DTO.setReasonCode(AllowedValuesProvider.getReasonCodeDefaultValue());

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
            DTO.setColorRuleApplied(AllowedValuesProvider.getColorRuleAppliedDefaultValue());

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

        if (StringUtil.isNotBlank(blockfaceID)) {
            DTO.setBlockID(CommonUtils.extractBlockIDFromBlockfaceID(blockfaceID));
        }

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

        TO.setAllBoolean(false);

        TO.setSensorFlag(AllowedValuesProvider.getSensorFlagDefaultValue());
        TO.setCapColor(AllowedValuesProvider.getCapColorDefaultValue());

        TO.setActiveMeterFlag(AllowedValuesProvider.getActiveMeterFlagBulkDefaultValue());
        TO.setReasonCode(AllowedValuesProvider.getReasonCodeDefaultValue());
        TO.setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDODefaultValue());

        LOGGER.exiting(CLASSNAME, "getNewParkingSpaceInventoryBulkTO");

        return TO;
    }

    public MeterOPScheduleBulkTO getNewMeterOPScheduleBulkTO() {
        LOGGER.entering(CLASSNAME, "getNewMeterOPScheduleBulkTO");

        MeterOPScheduleBulkTO TO = new MeterOPScheduleBulkTO();

        TO.setAllBoolean(false);

        LOGGER.exiting(CLASSNAME, "getNewMeterOPScheduleBulkTO");

        return TO;
    }

    public MeterRateScheduleBulkTO getNewMeterRateScheduleBulkTO() {
        LOGGER.entering(CLASSNAME, "getNewMeterRateScheduleBulkTO");

        MeterRateScheduleBulkTO TO = new MeterRateScheduleBulkTO();

        TO.setAllBoolean(false);

        LOGGER.exiting(CLASSNAME, "getNewMeterRateScheduleBulkTO");

        return TO;
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

    /**
     * Applies the necessary business logic for the Bulk Edit Mode of Parking Space
     *
     * Common Validity Tests:
     * =====================
     *    1. Check for Cap Color
     *       ---Changing from Black/Brown is NOT allowed
     *    2. Check for Active Meter Status
     *       ---Changing from U to anything other than U is NOT allowed
     *    3. Check for Meter Details
     *       ---Changing when Active Meter Status = U is NOT allowed
     *    4. Check for MS Pay Station ID
     *       ---Combination of Post ID and MS Pay Station ID should work
     *
     * Common Steps:
     * ============
     *    1. Sensor Flag to be updated
     *       ---Set Sensor Flag
     *    2. Active Meter Flag to be updated
     *       ---Set Active Meter Flag
     *    3. Reason Code to be updated
     *       ---Set Reason Code
     *    4. Cap Color to be updated
     *       ---Set Cap Color
     *    5. Legislation to be updated
     *       ---Set Legislation Reference
     *       ---Set Legislation Date
     *    6. Work Order to be updated
     *       ---Set Work Order
     *    7. Comments to be updated
     *       ---Set Comments
     *    8. Meter Details to be updated
     *       ---Set Meter Details
     *       ---Set MS PayStation ID accordingly
     *    9. Retrieve OLD_RATE_AREA
     *   10. Retrieve PCO_BEAT
     *
     * @param parkingSpaceInventoryBulkTO
     * @param meterOPScheduleBulkTO
     * @param meterSchedules
     * @param meterRateScheduleBulkTO
     * @param meterRates
     * @return
     */
    public OperationStatus editBulkParkingSpace(ParkingSpaceInventoryBulkTO parkingSpaceInventoryBulkTO,
                                                MeterOPScheduleBulkTO meterOPScheduleBulkTO,
                                                List<MeterOPScheduleDTO> meterSchedules,
                                                MeterRateScheduleBulkTO meterRateScheduleBulkTO,
                                                List<MeterRateScheduleDTO> meterRates) {

        LOGGER.entering(CLASSNAME, "editBulkParkingSpace");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Retrieving Parking Spaces

        List<ParkingSpaceInventoryDTO> parkingSpaceInventoryDTOs =
            ParkingSpaceInventoryProvider.INSTANCE.getParkingSpaceInventoryDTOs(parkingSpaceInventoryBulkTO.getParkingSpaceIDList());

        if (parkingSpaceInventoryDTOs.isEmpty()) {
            return new OperationStatus(OperationStatus.Type.BATCH_FAILURE,
                                       new Exception(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_bulk_could_not_load_parking_spaces)));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Parking Space

        if (!parkingSpaceInventoryBulkTO.isSameAs(new ParkingSpaceInventoryBulkTO())) {

            // For each Parking Space
            for (ParkingSpaceInventoryDTO DTO : parkingSpaceInventoryDTOs) {

                ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
                    ParkingSpaceInventoryDTO.copy(DTO);

                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // Validate Parking Space values

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedCapColor() &&
                    parkingSpaceInventoryBulkTO.isInvalidCapColor(parkingSpaceInventoryDTO.getCapColor())) {

                    LOGGER.debug("CAP_COLOR can NOT be changed");
                    return new OperationStatus(OperationStatus.Type.BATCH_FAILURE,
                                               new Exception(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_bulk_meter_invalid_from_cap_color)));

                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedActiveMeterFlag() &&
                    parkingSpaceInventoryDTO.isUnmetered() &&
                    !parkingSpaceInventoryBulkTO.isUnmetered()) {

                    LOGGER.debug("ACTIVE_METER_FLAG can NOT be changed");
                    return new OperationStatus(OperationStatus.Type.BATCH_FAILURE,
                                               new Exception(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_bulk_meter_invalid_from_active_meter_status)));

                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedMeterDetails() &&
                    parkingSpaceInventoryDTO.isUnmetered()) {

                    LOGGER.debug("Meter Details can NOT be changed as parking space is unmetered");
                    return new OperationStatus(OperationStatus.Type.BATCH_FAILURE,
                                               new Exception(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_bulk_meter_invalid_meter_details)));

                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedMeterDetails() &&
                    parkingSpaceInventoryBulkTO.getMeterDetails().isMeterMultiSpace() &&
                    !CommonUtils.willPostIDAndPayStationIDWork(parkingSpaceInventoryDTO.getPostID(),
                                                               parkingSpaceInventoryBulkTO.getMSPayStationID(),
                                                               parkingSpaceInventoryDTO.getOnOffStreetType())) {

                    LOGGER.debug("Combination of POST_ID and MS_PAYSTATION_ID does NOT work");
                    return new OperationStatus(OperationStatus.Type.BATCH_FAILURE,
                                               new Exception(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_work_postid_and_mspaystationid)));

                }

                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // Update Parking Space Values

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedSensorFlag()) {
                    parkingSpaceInventoryDTO.setSensorFlag(parkingSpaceInventoryBulkTO.getSensorFlag());
                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedActiveMeterFlag()) {
                    parkingSpaceInventoryDTO.setActiveMeterFlag(parkingSpaceInventoryBulkTO.getActiveMeterFlag());
                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedReasonCode()) {
                    parkingSpaceInventoryDTO.setReasonCode(parkingSpaceInventoryBulkTO.getReasonCode());
                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedCapColor()) {
                    parkingSpaceInventoryDTO.setCapColor(parkingSpaceInventoryBulkTO.getCapColor());
                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedLegislation()) {
                    parkingSpaceInventoryDTO.setLegislationReference(parkingSpaceInventoryBulkTO.getLegislationReference());
                    parkingSpaceInventoryDTO.setLegislationDate(parkingSpaceInventoryBulkTO.getLegislationDate());
                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedWorkOrder()) {
                    parkingSpaceInventoryDTO.setWorkOrder(parkingSpaceInventoryBulkTO.getWorkOrder());
                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedComments()) {
                    parkingSpaceInventoryDTO.setComments(parkingSpaceInventoryBulkTO.getComments());
                }

                if (parkingSpaceInventoryBulkTO.isToBeUpdatedMeterDetails()) {
                    parkingSpaceInventoryDTO.setMeterDetails(parkingSpaceInventoryBulkTO.getMeterDetails());

                    String msPayStationID =
                        parkingSpaceInventoryBulkTO.getMeterDetails().isMeterMultiSpace() ?
                        parkingSpaceInventoryBulkTO.getMSPayStationID() : "-";

                    parkingSpaceInventoryDTO.setMSPayStationID(msPayStationID);
                }

                String oldRateArea = getOldRateArea(parkingSpaceInventoryDTO);
                parkingSpaceInventoryDTO.setOldRateArea(oldRateArea);

                String pcoBeat = getPCOBeat(parkingSpaceInventoryDTO);
                parkingSpaceInventoryDTO.setPCOBeat(pcoBeat);

                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // Append it to the records list

                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                 parkingSpaceInventoryDTO));

            }

        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Retrieve Meter Schedules

        List<MeterOPScheduleDTO> deleteMeterSchedules =
            new ArrayList<MeterOPScheduleDTO>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter OP Schedules

        if (meterOPScheduleBulkTO.isDeleteAllOPSchedules()) {
            // All previous meter OP schedules should be deleted
            List<MeterOPScheduleDTO> originalMeterOPSchedules =
                MeterOPScheduleProvider.INSTANCE.getMeterOPScheduleDTOs(parkingSpaceInventoryBulkTO.getParkingSpaceIDList());

            deleteMeterSchedules.addAll(originalMeterOPSchedules);
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter ALT Schedules

        if (meterOPScheduleBulkTO.isDeleteAllALTSchedules()) {
            // All previous meter ALT schedules should be deleted
            List<MeterOPScheduleDTO> originalMeterALTSchedules =
                MeterOPScheduleProvider.INSTANCE.getMeterALTScheduleDTOs(parkingSpaceInventoryBulkTO.getParkingSpaceIDList());

            deleteMeterSchedules.addAll(originalMeterALTSchedules);
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter TOW Schedules

        if (meterOPScheduleBulkTO.isDeleteAllTOWSchedules()) {
            // All previous meter TOW schedules should be deleted
            List<MeterOPScheduleDTO> originalMeterTOWSchedules =
                MeterOPScheduleProvider.INSTANCE.getMeterTOWScheduleDTOs(parkingSpaceInventoryBulkTO.getParkingSpaceIDList());

            deleteMeterSchedules.addAll(originalMeterTOWSchedules);
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Update Meter Schedules

        for (MeterOPScheduleDTO deleteMeterSchedule : deleteMeterSchedules) {

            Date newToDate =
                EffectiveDateCalculator.getEffectiveToDateWhenDeleting(deleteMeterSchedule.getEffectiveFromDate(),
                                                                       deleteMeterSchedule.getEffectiveToDate());

            deleteMeterSchedule.setEffectiveToDate(newToDate);
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             deleteMeterSchedule));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Insert New Meter Schedules (if any)

        if (meterSchedules != null && !meterSchedules.isEmpty()) {

            for (ParkingSpaceInventoryDTO parkingSpaceInventoryDTO :
                 parkingSpaceInventoryDTOs) {

                for (MeterOPScheduleDTO meterSchedule : meterSchedules) {

                    MeterOPScheduleDTO meterOPScheduleDTO =
                        MeterOPScheduleDTO.copy(meterSchedule);

                    meterOPScheduleDTO.setParkingSpaceID(parkingSpaceInventoryDTO.getParkingSpaceID());

                    if (meterOPScheduleDTO.getScheduleType().isScheduleOP()) {

                        String colorRuleApplied =
                            (parkingSpaceInventoryBulkTO.isToBeUpdatedCapColor()) ?
                            parkingSpaceInventoryBulkTO.getCapColor() :
                            parkingSpaceInventoryDTO.getCapColor();

                        meterOPScheduleDTO.setColorRuleApplied(colorRuleApplied);

                    }

                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     meterOPScheduleDTO));

                }

            }

        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Retrieve Meter Rates

        List<MeterRateScheduleDTO> deleteMeterRates =
            new ArrayList<MeterRateScheduleDTO>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter Base Rates

        if (meterRateScheduleBulkTO.isDeleteAllBaseRates()) {
            // All previous meter base rates should be deleted
            List<MeterRateScheduleDTO> originalMeterBaseRates =
                MeterRateScheduleProvider.INSTANCE.getMeterBRateScheduleDTOs(parkingSpaceInventoryBulkTO.getParkingSpaceIDList());

            deleteMeterRates.addAll(originalMeterBaseRates);
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Meter Hourly Rates

        if (meterRateScheduleBulkTO.isDeleteAllHourlyRates()) {
            // All previous meter hourly rates should be deleted
            List<MeterRateScheduleDTO> originalMeterHourlyRates =
                MeterRateScheduleProvider.INSTANCE.getMeterHRateScheduleDTOs(parkingSpaceInventoryBulkTO.getParkingSpaceIDList());

            deleteMeterRates.addAll(originalMeterHourlyRates);
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Update Meter Rates

        for (MeterRateScheduleDTO deleteMeterRate : deleteMeterRates) {
            Date newToDate =
                EffectiveDateCalculator.getEffectiveToDateWhenDeleting(deleteMeterRate.getEffectiveFromDate(),
                                                                       deleteMeterRate.getEffectiveToDate());

            deleteMeterRate.setEffectiveToDate(newToDate);
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             deleteMeterRate));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Insert New Meter Rates (if any)

        if (meterRates != null && !meterRates.isEmpty()) {

            for (ParkingSpaceInventoryDTO parkingSpaceInventoryDTO :
                 parkingSpaceInventoryDTOs) {

                for (MeterRateScheduleDTO meterRate : meterRates) {

                    MeterRateScheduleDTO meterRateScheduleDTO =
                        MeterRateScheduleDTO.copy(meterRate);

                    meterRateScheduleDTO.setParkingSpaceID(parkingSpaceInventoryDTO.getParkingSpaceID());

                    meterRateScheduleDTO.setBlockID(CommonUtils.extractBlockIDFromBlockfaceID(parkingSpaceInventoryDTO.getBlockfaceID()));

                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     meterRateScheduleDTO));

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

    /**
     * Retrieves the OLD_RATE_AREA
     *    ---ACTIVE_METER_FLAG
     *       ---If U, then -
     *    ---CAP_COLOR
     *       ---If Brown, then Tour Bus
     *       ---If Black, then MCName
     *    ---LONGITUDE
     *    ---LATITUDE
     *
     *
     * @param DTO
     * @return
     */
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

    /**
     * Retrieves PCO Beat based on the latitude and longitude
     *
     * @param DTO
     * @return
     */
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
