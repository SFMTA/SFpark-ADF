package sfpark.rateChange.manager.view.provider;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.helper.RateChangeAreaType;
import sfpark.adf.tools.model.data.helper.RateChangeStatus;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.status.OperationStatus;
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

        DTO.setAreaType(RateChangeAreaType.PILOT);
        DTO.setCalendarID("0");
        DTO.setRateChangePolicy(AllowedValuesProvider.getRateChgPolicyDefaultValue());
        DTO.setPlannedChangeEffectiveDate(SQLDateUtil.getTodaysDate());
        DTO.setStatus(RateChangeStatus.WORKING);

        LOGGER.exiting(CLASSNAME, "getNewRateChangeHeaderDTO");

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