package sfpark.rateChange.manager.view.provider;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.data.helper.PMDistrictAreaType;
import sfpark.adf.tools.model.data.helper.RateChangeProcessStepStartFlag;
import sfpark.adf.tools.model.data.helper.RateChangeProcessTimeLimitOption;
import sfpark.adf.tools.model.data.helper.RateChangeStatus;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.provider.RateChangeHeaderProvider;
import sfpark.adf.tools.model.provider.RateChangeProcessControlProvider;

import sfpark.adf.tools.utilities.generic.SQLDateUtil;

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
        DTO.setRateChangePolicy(AllowedValuesProvider.getRateChgPolicyDefaultValue());

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
        DTO.setProcessStep(AllowedValuesProvider.getProcessStepDefaultValue());
        DTO.setStepStartFlag(RateChangeProcessStepStartFlag.HOLD);
        DTO.setStepExecStatus(AllowedValuesProvider.getProcessStepExecStatusDefaultValue());

        LOGGER.exiting(CLASSNAME, "getNewRateChangeProcessControlDTO");

        return DTO;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Actual operations

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
                    // TODO Change to Submitted
                }
                break;

            case SUBMITTED:
                {
                    rateChangeHeaderDTO.setStatus(RateChangeStatus.APPROVED);
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                                     rateChangeHeaderDTO));
                    // TODO Lock the calendar
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

/*


    **
     * Applies the necessary business logic for the Edit Mode of Calendar.
     *
     * This method returns NULL when no changes are made to the original data
     *
     * @param calendarHeaderDTO
     * @param calendarDetailDDOs
     * @return
     *
    public OperationStatus editCalendar(CalendarHeaderDTO calendarHeaderDTO,
                                        List<CalendarDetailDDO> calendarDetailDDOs) {
        LOGGER.entering(CLASSNAME, "editCalendar");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Calendar Detail

        if (calendarDetailDDOs != null && !calendarDetailDDOs.isEmpty()) {
            // Add/Delete the necessary Calendar Detail Records
            for (CalendarDetailDDO DDO : calendarDetailDDOs) {

                CalendarDetailDTO calendarDetailDTO =
                    new CalendarDetailDTO(calendarHeaderDTO.getCalendarID(),
                                          DDO.getDisplayDateDT());

                switch (DDO.getOperationType()) {

                case INSERT:
                    {
                        tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                         calendarDetailDTO));
                    }
                    break;

                case DELETE:
                    {
                        tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                                         calendarDetailDTO));
                    }
                    break;

                default:
                    {
                        // Do nothing
                    }
                    break;
                }

            }
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Calendar Header

        CalendarHeaderDTO originalCalendarHeaderDTO =
            CalendarHeaderProvider.INSTANCE.checkForCalendarID(calendarHeaderDTO.getCalendarID()).getDTO();

        if (!calendarHeaderDTO.isSameAs(originalCalendarHeaderDTO)) {
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             calendarHeaderDTO));
        }

        LOGGER.exiting(CLASSNAME, "editCalendar");

        return performOperation(tableRecords);
    }

    **
     * Applies the necessary business logic for the Delete Mode of Calendar.
     *
     * @param calendarHeaderDTO
     * @return
     *
    public OperationStatus deleteCalendar(CalendarHeaderDTO calendarHeaderDTO) {
        LOGGER.entering(CLASSNAME, "deleteCalendar");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Calendar Detail

        List<CalendarDetailDTO> calendarDetailDTOs =
            CalendarDetailProvider.INSTANCE.getCalendarDetailDTOs(calendarHeaderDTO.getCalendarID());

        if (!calendarDetailDTOs.isEmpty()) {
            // Delete all records
            for (CalendarDetailDTO DTO : calendarDetailDTOs) {

                tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                                 DTO));
            }
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Calendar Header

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                         calendarHeaderDTO));

        LOGGER.exiting(CLASSNAME, "deleteCalendar");

        return performOperation(tableRecords);
    }
 */