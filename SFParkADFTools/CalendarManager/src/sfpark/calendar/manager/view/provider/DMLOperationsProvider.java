package sfpark.calendar.manager.view.provider;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.dTable.DDateDO;
import sfpark.adf.tools.model.data.dto.calendar.CalendarDetailDTO;
import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.data.helper.CalendarStatus;
import sfpark.adf.tools.model.data.helper.CalendarType;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.provider.CalendarDetailProvider;
import sfpark.adf.tools.model.provider.CalendarHeaderProvider;
import sfpark.adf.tools.model.provider.DDateProvider;
import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.status.OperationStatus;

import sfpark.calendar.manager.view.provider.helper.CalendarDetailDDO;
import sfpark.calendar.manager.view.provider.helper.CalendarDetailOperationType;

public class DMLOperationsProvider {

    private static final String CLASSNAME =
        DMLOperationsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private DMLOperationsProvider() {
        super();
    }

    public static final DMLOperationsProvider INSTANCE =
        new DMLOperationsProvider();

    private static final String LAST_UPDATED_PROGRAM = "Calendar Manager Tool";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Methods to create new DTOs

    /**
     * Generates a new Calendar Header DTO with the necessary defaults
     * --- Sets the Calendar Type
     * --- Sets the Status
     *
     * @param calendarType
     * @return
     */
    public CalendarHeaderDTO getNewCalendarHeaderDTO(CalendarType calendarType) {
        LOGGER.entering(CLASSNAME, "getNewCalendarHeaderDTO");

        CalendarHeaderDTO DTO = new CalendarHeaderDTO();

        DTO.setCalendarType(calendarType);
        DTO.setStatus(CalendarStatus.UNLOCKED);

        LOGGER.exiting(CLASSNAME, "getNewCalendarHeaderDTO");

        return DTO;
    }

    /**
     * Retrieves the DDateDO List and converts into into CalendarDetailDDO List
     * to be understood by the UI
     *
     * @param calendarID
     * @return
     */
    public List<CalendarDetailDDO> getCalendarDetailDDOs(String calendarID) {
        List<CalendarDetailDDO> calendarDetailDDOs =
            new ArrayList<CalendarDetailDDO>();

        if (calendarID != null) {
            List<DDateDO> dDateDOs =
                DDateProvider.INSTANCE.getDDateDOs(calendarID);

            for (DDateDO DO : dDateDOs) {
                CalendarDetailDDO DDO =
                    new CalendarDetailDDO(false, CalendarDetailOperationType.NO_OP,
                                          DO.getDateDT(), DO.getDateType());

                calendarDetailDDOs.add(DDO);
            }
        }

        return calendarDetailDDOs;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Actual operations

    /**
     * Adds the Calendar by creating a new CalendarHeader row
     *
     * @param calendarHeaderDTO
     * @return
     */
    public OperationStatus addCalendar(CalendarHeaderDTO calendarHeaderDTO) {
        LOGGER.entering(CLASSNAME, "addCalendar");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                         calendarHeaderDTO));

        LOGGER.exiting(CLASSNAME, "addCalendar");

        return performOperation(tableRecords);
    }

    /**
     * Applies the necessary business logic for the Edit Mode of Calendar.
     *
     * This method returns NULL when no changes are made to the original data
     *
     * @param calendarHeaderDTO
     * @param calendarDetailDDOs
     * @return
     */
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

    /**
     * Applies the necessary business logic for the Delete Mode of Calendar.
     *
     * @param calendarHeaderDTO
     * @return
     */
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
