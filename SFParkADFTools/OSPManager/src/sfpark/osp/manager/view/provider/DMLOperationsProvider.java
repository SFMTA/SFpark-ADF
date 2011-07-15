package sfpark.osp.manager.view.provider;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.garageOPHours.GarageOPHoursDTO;
import sfpark.adf.tools.model.data.dto.garageRates.GarageRatesDTO;
import sfpark.adf.tools.model.data.dto.ospInventory.OSPInventoryDTO;
import sfpark.adf.tools.model.data.helper.GarageRatesDisplayGroup;
import sfpark.adf.tools.model.data.helper.GarageRatesQualifier;
import sfpark.adf.tools.model.data.helper.GarageRatesTimeBand;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.provider.GarageOPHoursProvider;
import sfpark.adf.tools.model.provider.GarageRatesProvider;
import sfpark.adf.tools.model.provider.OSPInventoryProvider;
import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.status.OperationStatus;
import sfpark.adf.tools.utilities.constants.WeekDays;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public final class DMLOperationsProvider {

    private static final String CLASSNAME =
        DMLOperationsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private DMLOperationsProvider() {
        super();
    }

    public static final DMLOperationsProvider INSTANCE =
        new DMLOperationsProvider();

    private static final String LAST_UPDATED_PROGRAM = "OSP Manager Tool";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public GarageRatesDTO getNewGarageRatesDTO(GarageRatesDisplayGroup displayGroup,
                                               String ospID,
                                               int displaySequence) {

        LOGGER.entering(CLASSNAME, "getNewGarageRatesDTO");

        GarageRatesDTO DTO = new GarageRatesDTO();

        DTO.setGarageRateID(null);
        DTO.setOSPID(ospID);
        DTO.setOverride(false);

        DTO.setRateStatus("A");

        DTO.setDisplayGroup(displayGroup);

        DTO.setDisplaySequence(displaySequence);

        DTO.setEffectiveFromDate(SQLDateUtil.getTodaysDate());
        DTO.setEffectiveToDate(SQLDateUtil.getMaximumDate());

        DTO.setRate(0.00f);

        DTO.setDisplayCode("Y");

        switch (displayGroup) {

        case HOURLY_RATES:
            {
                // Hourly Rates
                //    --- RATE_QUALIFIER is always Per hour
                //    --- MAX_AMT is always NULL
                //    --- RATE_RESTRICTIONS is always NULL
                DTO.setEditableRateQualifier(false);
                DTO.setRateQualifier(GarageRatesQualifier.PER_HOUR);

                DTO.setEditableMaximumAmount(false);
                DTO.setMaximumAmount(null);

                DTO.setEditableRateRestrictions(false);
                DTO.setRateRestrictions(null);

                DTO.setEditableTimeBand(true);
                DTO.setTimeBand(GarageRatesTimeBand.BAND_1);

            }
            break;

        case MONTHLY_RATES:
            {
                // Monthly Rates
                //    --- RATE_QUALIFIER is always Per month
                //    --- MAX_AMT is always NULL
                //    --- FROM_TIME is always NULL
                //    --- TO_TIME is always NULL
                DTO.setEditableRateQualifier(false);
                DTO.setRateQualifier(GarageRatesQualifier.PER_MONTH);

                DTO.setEditableMaximumAmount(false);
                DTO.setMaximumAmount(null);

                DTO.setEditableRateRestrictions(true);
                DTO.setRateRestrictions(null);

                DTO.setEditableTimeBand(false);
                DTO.setTimeBand(GarageRatesTimeBand.NULL);
            }
            break;

        case FLAT_RATES_OR_DISCOUNTS:
            {
                // Flat Rates / Discounts
                //    --- FROM_TIME is always NULL
                //    --- TO_TIME is always NULL
                DTO.setEditableRateQualifier(true);
                DTO.setRateQualifier(GarageRatesQualifier.FLAT_RATE);

                DTO.setEditableMaximumAmount(true);
                DTO.setMaximumAmount(null);

                DTO.setEditableRateRestrictions(true);
                DTO.setRateRestrictions(null);

                DTO.setEditableTimeBand(false);
                DTO.setTimeBand(GarageRatesTimeBand.NULL);
            }
            break;

        }

        LOGGER.exiting(CLASSNAME, "getNewGarageRatesDTO");

        return DTO;
    }

    //    public GarageRatesDTO getNewGarageRatesDTO(String ospID, int sequence) {
    //
    //        LOGGER.entering(CLASSNAME, "getNewGarageRatesDTO");
    //
    //        GarageRatesDTO DTO = new GarageRatesDTO();
    //
    //        DTO.setGarageRateID(null);
    //        DTO.setOSPID(ospID);
    //        DTO.setOverride(false);
    //
    //        DTO.setRateStatus("A");
    //        // TO DO DTO.setDisplayGroup(1);
    //        DTO.setDisplaySequence(sequence);
    //
    //        DTO.setEffectiveFromDate(SQLDateUtil.getTodaysDate());
    //        DTO.setEffectiveToDate(SQLDateUtil.getMaximumDate());
    //
    //        DTO.setRateDescription(null);
    //        // TO DO DTO.setTimeBand(GarageRatesDTO.TimeBand.BAND_1);
    //        DTO.setMaximumAmount(null);
    //
    //        DTO.setRate(0.00f);
    //        // TO DO DTO.setRateQualifier("Flat rate");
    //
    //        DTO.setDisplayCode("Y");
    //
    //        LOGGER.exiting(CLASSNAME, "getNewGarageRatesDTO");
    //
    //        return DTO;
    //    }

    public GarageOPHoursDTO getNewGarageOPHoursDTO(String ospID,
                                                   int sequence) {

        LOGGER.entering(CLASSNAME, "getNewGarageOPHoursDTO");

        GarageOPHoursDTO DTO = new GarageOPHoursDTO();

        DTO.setGarageOPHoursID(null);
        DTO.setOSPID(ospID);
        DTO.setOverride(false);

        DTO.setScheduleStatus("A");

        DTO.setScheduleSequence(sequence);

        DTO.setEffectiveFromDate(SQLDateUtil.getTodaysDate());
        DTO.setEffectiveToDate(SQLDateUtil.getMaximumDate());

        DTO.setFromDay(WeekDays.Mo.getWeekDayName());
        DTO.setToDay(null);

        DTO.setFromTime(TimeDisplayUtil.extractFromTimeForDisplay(0));
        DTO.setToTime(TimeDisplayUtil.extractToTimeForDisplay(30));

        LOGGER.exiting(CLASSNAME, "getNewGarageOPHoursDTO");

        return DTO;
    }

    /**
     * Applies the necessary business logic for the Edit Mode of OSP Inventory.
     *
     * This method returns NULL when no changes are made to the original data
     *
     * @param ospInventoryDTO
     * @return
     */
    public OperationStatus editMeteredOSP(OSPInventoryDTO ospInventoryDTO) {
        LOGGER.entering(CLASSNAME, "editMeteredOSP");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        OSPInventoryDTO originalDTO =
            OSPInventoryProvider.INSTANCE.checkForOSP(ospInventoryDTO.getOSPID()).getDTO();

        if (!ospInventoryDTO.isSameAs(originalDTO)) {
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             ospInventoryDTO));
        }

        LOGGER.exiting(CLASSNAME, "editMeteredOSP");

        return performOperation(tableRecords);
    }

    public OperationStatus editUnMeteredOSP(OSPInventoryDTO ospInventoryDTO,
                                            List<GarageRatesDTO> garageRatesDTOs,
                                            List<GarageOPHoursDTO> garageOPHoursDTOs) {
        LOGGER.entering(CLASSNAME, "editUnMeteredOSP");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Unmetered OSP

        OSPInventoryDTO originalDTO =
            OSPInventoryProvider.INSTANCE.checkForOSP(ospInventoryDTO.getOSPID()).getDTO();

        if (!ospInventoryDTO.isSameAs(originalDTO)) {
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             ospInventoryDTO));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Garage Rates

        if (!garageRatesDTOs.isEmpty()) {
            // Add the necessary Garage Rate Records
            for (GarageRatesDTO garageRatesDTO : garageRatesDTOs) {

                if (garageRatesDTO.isNewRecord()) {
                    LOGGER.debug("This is a NEW record. So just add.");
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     garageRatesDTO));

                } else {
                    LOGGER.debug("This is an Existing record. Apply business logic before update.");

                    if (garageRatesDTO.isOverride()) {
                        LOGGER.debug("User override encountered. Simply update the record without archiving.");
                        tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                         garageRatesDTO));

                    } else {
                        LOGGER.debug("Perform archiving.");

                        GarageRatesDTO originalGarageRatesDTO =
                            GarageRatesProvider.INSTANCE.getGarageRatesDTO(garageRatesDTO.getGarageRateID());

                        switch (garageRatesDTO.getWhatChanged(originalGarageRatesDTO)) {

                        case BOTH_DATES:
                            {
                                // Update originalDTO with TO date as previous date to modifiedDTO's FROM date
                                // Insert modifiedDTO as new record

                                java.sql.Date newToDate =
                                    SQLDateUtil.getPreviousDateFor(garageRatesDTO.getEffectiveFromDate());
                                java.sql.Date fromDate =
                                    originalGarageRatesDTO.getEffectiveFromDate();
                                if (newToDate.before(fromDate)) {
                                    newToDate = fromDate;
                                }

                                originalGarageRatesDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalGarageRatesDTO));
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 garageRatesDTO));
                            }
                            break;

                        case ONLY_FROM_DATE:
                            {
                                // Update originalDTO with TO date as previous date to modifiedDTO's FROM date
                                // Insert modifiedDTO as new record

                                java.sql.Date newToDate =
                                    SQLDateUtil.getPreviousDateFor(garageRatesDTO.getEffectiveFromDate());
                                java.sql.Date fromDate =
                                    originalGarageRatesDTO.getEffectiveFromDate();
                                if (newToDate.before(fromDate)) {
                                    newToDate = fromDate;
                                }

                                originalGarageRatesDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalGarageRatesDTO));
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 garageRatesDTO));
                            }
                            break;

                        case ONLY_TO_DATE:
                            {
                                // Update modifiedDTO
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 garageRatesDTO));
                            }
                            break;

                        case NOT_DATES:
                            {
                                // Update originalDTO with TO date as yesterday
                                // Insert modifiedDTO as new record with FROM date as today

                                java.sql.Date newToDate =
                                    SQLDateUtil.getYesterdaysDate();
                                java.sql.Date newFromDate =
                                    SQLDateUtil.getTodaysDate();

                                java.sql.Date fromDate =
                                    originalGarageRatesDTO.getEffectiveFromDate();
                                java.sql.Date toDate =
                                    originalGarageRatesDTO.getEffectiveToDate();

                                if (newToDate.before(fromDate)) {
                                    newToDate = fromDate;
                                }

                                if (newToDate.after(toDate)) {
                                    newToDate = toDate;
                                }

                                if (newFromDate.after(toDate)) {
                                    newFromDate = toDate;
                                }

                                originalGarageRatesDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalGarageRatesDTO));

                                garageRatesDTO.setEffectiveFromDate(newFromDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 garageRatesDTO));
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
        // Garage OP Hours

        if (!garageOPHoursDTOs.isEmpty()) {
            // Add the necessary Garage Rate Records
            for (GarageOPHoursDTO garageOPHoursDTO : garageOPHoursDTOs) {

                if (garageOPHoursDTO.isNewRecord()) {
                    LOGGER.debug("This is NEW record. So just add.");
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     garageOPHoursDTO));

                } else {
                    LOGGER.debug("This is an Existing record. Apply business logic before update.");

                    if (garageOPHoursDTO.isOverride()) {
                        LOGGER.debug("User override encountered. Simply update the record without archiving.");
                        tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                         garageOPHoursDTO));

                    } else {
                        LOGGER.debug("Perform archiving.");

                        GarageOPHoursDTO originalGarageOPHoursDTO =
                            GarageOPHoursProvider.INSTANCE.getGarageOPHoursDTO(garageOPHoursDTO.getGarageOPHoursID());

                        switch (garageOPHoursDTO.getWhatChanged(originalGarageOPHoursDTO)) {

                        case BOTH_DATES:
                            {
                                // Update originalDTO with TO date as previous date to modifiedDTO's FROM date
                                // Insert modifiedDTO as new record

                                java.sql.Date newToDate =
                                    SQLDateUtil.getPreviousDateFor(garageOPHoursDTO.getEffectiveFromDate());
                                java.sql.Date fromDate =
                                    originalGarageOPHoursDTO.getEffectiveFromDate();
                                if (newToDate.before(fromDate)) {
                                    newToDate = fromDate;
                                }

                                originalGarageOPHoursDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalGarageOPHoursDTO));
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 garageOPHoursDTO));
                            }
                            break;

                        case ONLY_FROM_DATE:
                            {
                                // Update originalDTO with TO date as previous date to modifiedDTO's FROM date
                                // Insert modifiedDTO as new record

                                java.sql.Date newToDate =
                                    SQLDateUtil.getPreviousDateFor(garageOPHoursDTO.getEffectiveFromDate());
                                java.sql.Date fromDate =
                                    originalGarageOPHoursDTO.getEffectiveFromDate();
                                if (newToDate.before(fromDate)) {
                                    newToDate = fromDate;
                                }

                                originalGarageOPHoursDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalGarageOPHoursDTO));
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 garageOPHoursDTO));
                            }
                            break;

                        case ONLY_TO_DATE:
                            {
                                // Update modifiedDTO
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 garageOPHoursDTO));
                            }
                            break;

                        case NOT_DATES:
                            {
                                // Update originalDTO with TO date as yesterday
                                // Insert modifiedDTO as new record with FROM date as today

                                java.sql.Date newToDate =
                                    SQLDateUtil.getYesterdaysDate();
                                java.sql.Date newFromDate =
                                    SQLDateUtil.getTodaysDate();

                                java.sql.Date fromDate =
                                    originalGarageOPHoursDTO.getEffectiveFromDate();
                                java.sql.Date toDate =
                                    originalGarageOPHoursDTO.getEffectiveToDate();

                                if (newToDate.before(fromDate)) {
                                    newToDate = fromDate;
                                }

                                if (newToDate.after(toDate)) {
                                    newToDate = toDate;
                                }

                                if (newFromDate.after(toDate)) {
                                    newFromDate = toDate;
                                }

                                originalGarageOPHoursDTO.setEffectiveToDate(newToDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                                 originalGarageOPHoursDTO));

                                garageOPHoursDTO.setEffectiveFromDate(newFromDate);
                                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                                 garageOPHoursDTO));
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

        LOGGER.exiting(CLASSNAME, "editUnMeteredOSP");

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
