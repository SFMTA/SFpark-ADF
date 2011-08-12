package sfpark.event.manager.view.provider;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.model.data.dto.eventCalendar.EventCalendarDTO;
import sfpark.adf.tools.model.helper.TableRecord;

import sfpark.adf.tools.model.provider.ProviderWrapper;
import sfpark.adf.tools.model.status.OperationStatus;

import sfpark.event.manager.view.provider.helper.eventCalendar.EventCalendarDateDAO;

public final class DMLOperationsProvider {

    private DMLOperationsProvider() {
        super();
    }

    public static final DMLOperationsProvider INSTANCE =
        new DMLOperationsProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public List<EventCalendarDateDAO> getEventCalendarDateDAOs(String calendarName) {

        List<EventCalendarDateDAO> eventCalendarDateDAOs =
            new ArrayList<EventCalendarDateDAO>();

        if (calendarName != null) {
            List<EventCalendarDTO> eventCalendarDTOs = new ArrayList<EventCalendarDTO>();

            for (EventCalendarDTO eventCalendarDTO : eventCalendarDTOs) {
                EventCalendarDateDAO eventCalendarDateDAO =
                    new EventCalendarDateDAO(false,
                                             EventCalendarDateDAO.DateType.NO_OP,
                                             eventCalendarDTO.getEventDate());

                eventCalendarDateDAOs.add(eventCalendarDateDAO);
            }
        }

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

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // Event Calendar Name

        if (!originalCalendarName.equals(currentCalendarName)) {
//            EventCalendarNameDTO eventCalendarNameDTO =
//                new EventCalendarNameDTO(originalCalendarName,
//                                         currentCalendarName);
//
//            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
//                                             eventCalendarNameDTO));
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++

        return performOperation(tableRecords);
    }


    private OperationStatus updateEventCalendarTable(String calendarName,
                                                     List<EventCalendarDateDAO> eventCalendarDateDAOs) {

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
                    // LOGGER.debug("This is a NEW date. So add.");
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                     eventCalendarDTO));
                } else if (dao.getDateType().isDelete()) {
                    // LOGGER.debug("Delete date.");
                    tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                                     eventCalendarDTO));
                }
            }
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++

        return performOperation(tableRecords);
    }

    private OperationStatus performOperation(List<TableRecord> tableRecords) {
        OperationStatus operationStatus = null;

        if (!tableRecords.isEmpty()) {
            operationStatus =
                    ProviderWrapper.INSTANCE.performOperationOnRecords(tableRecords,
                                                                       "UNKNOWN",
                                                                       "UNKNOWN");
        }

        return operationStatus;
    }
}
