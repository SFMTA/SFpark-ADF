package sfpark.event.manager.view.provider;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.eventCalendar.EventCalendarDTO;
import sfpark.adf.tools.model.data.dto.eventCalendar.EventCalendarNameDTO;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.provider.EventCalendarProvider;

import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.status.OperationStatus;

import sfpark.event.manager.view.provider.helper.eventCalendar.EventCalendarDateDAO;

public final class DMLOperationsProvider {

    private static final String CLASSNAME =
        DMLOperationsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private DMLOperationsProvider() {
        super();
    }

    public static final DMLOperationsProvider INSTANCE =
        new DMLOperationsProvider();

    private static final String LAST_UPDATED_PROGRAM = "Event Manager Tool";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public List<EventCalendarDateDAO> getEventCalendarDateDAOs(String calendarName) {
        LOGGER.entering(CLASSNAME, "getEventCalendarDateDAOs");

        List<EventCalendarDateDAO> eventCalendarDateDAOs =
            new ArrayList<EventCalendarDateDAO>();

        if (calendarName != null) {
            List<EventCalendarDTO> eventCalendarDTOs =
                EventCalendarProvider.INSTANCE.getEventCalendarDTOs(calendarName);

            for (EventCalendarDTO eventCalendarDTO : eventCalendarDTOs) {
                EventCalendarDateDAO eventCalendarDateDAO =
                    new EventCalendarDateDAO(false,
                                             EventCalendarDateDAO.DateType.NO_OP,
                                             eventCalendarDTO.getEventDate());

                eventCalendarDateDAOs.add(eventCalendarDateDAO);
            }
        }

        LOGGER.exiting(CLASSNAME, "getEventCalendarDateDAOs");
        return eventCalendarDateDAOs;
    }

    public OperationStatus addEventCalendar(String calendarName,
                                            List<EventCalendarDateDAO> eventCalendarDateDAOs) {
        return updateEventCalendarTable(calendarName, eventCalendarDateDAOs);
    }

    public OperationStatus editEventCalendar(String calendarName,
                                             List<EventCalendarDateDAO> eventCalendarDateDAOs) {
        return updateEventCalendarTable(calendarName, eventCalendarDateDAOs);
    }

    public OperationStatus changeEventCalendarName(String originalCalendarName,
                                                   String currentCalendarName) {
        return updateEventCalendarTable(originalCalendarName,
                                        currentCalendarName);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private OperationStatus updateEventCalendarTable(String originalCalendarName,
                                                     String currentCalendarName) {
        LOGGER.entering(CLASSNAME, "updateEventCalendarTable");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Event Calendar Name

        if (!originalCalendarName.equals(currentCalendarName)) {
            EventCalendarNameDTO eventCalendarNameDTO =
                new EventCalendarNameDTO(originalCalendarName,
                                         currentCalendarName);

            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             eventCalendarNameDTO));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++

        LOGGER.exiting(CLASSNAME, "updateEventCalendarTable");
        return performOperation(tableRecords);
    }


    private OperationStatus updateEventCalendarTable(String calendarName,
                                                     List<EventCalendarDateDAO> eventCalendarDateDAOs) {
        LOGGER.entering(CLASSNAME, "updateEventCalendarTable");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Event Calendar Dates

        if (!eventCalendarDateDAOs.isEmpty()) {
            // Add the necessary Event Calendar Records
            for (EventCalendarDateDAO dao : eventCalendarDateDAOs) {

                EventCalendarDTO eventCalendarDTO =
                    new EventCalendarDTO(calendarName, dao.getDate());

                if (dao.getDateType().isInsert()) {
                    LOGGER.debug("This is a NEW date. So add.");
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     eventCalendarDTO));
                } else if (dao.getDateType().isDelete()) {
                    LOGGER.debug("Delete date.");
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                                     eventCalendarDTO));
                }
            }
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++

        LOGGER.exiting(CLASSNAME, "updateEventCalendarTable");
        return performOperation(tableRecords);
    }

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
