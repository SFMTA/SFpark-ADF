package sfpark.event.manager.view.backing.eventCalendar;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.status.OperationStatus;

import sfpark.event.manager.view.provider.DMLOperationsProvider;
import sfpark.event.manager.view.provider.helper.eventCalendar.EventCalendarDateDAO;

public class EventCalendarPropertiesBean {

    private RichCommandButton deleteEventDateButton;
    private RichCommandButton undoDeleteEventDateButton;

    private RichTable eventDateTable;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public EventCalendarPropertiesBean() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void addButtonHandler(ActionEvent event) {

//        NavigationFlow currentNavFlow = NavigationFlow.ADD_EVENT_CALENDAR;

//        if (currentNavFlow.getMode().isAddMode()) {
//            setPageFlowScopeValue(PageFlowScopeKey.BACK_FROM_ADD_EVENT_CALENDAR_DATE.getKey(),
//                                  NavigationFlow.ADD_EVENT_CALENDAR.name());
//        } else if (currentNavFlow.getMode().isEditMode()) {
//            setPageFlowScopeValue(PageFlowScopeKey.BACK_FROM_ADD_EVENT_CALENDAR_DATE.getKey(),
//                                  NavigationFlow.EDIT_EVENT_CALENDAR.name());
//        } else {
//            setPageFlowScopeValue(PageFlowScopeKey.BACK_FROM_ADD_EVENT_CALENDAR_DATE.getKey(),
//                                  NavigationFlow.LIST_EVENT_CALENDAR.name());
//        }

//        setPageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_DISABLED_DATES.getKey(),
//                              DAOHelper.retrieveDatesFrom((List<EventCalendarDateDAO>)getEventDateTable().getValue()));
//        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
//                             NavigationFlow.ADD_EVENT_CALENDAR_DATE.name());
    }

    public void editButtonHandler(ActionEvent event) {
        // Use this for UNDO function
        if (getEventDateTable().getSelectedRowKeys().size() == 1) {
            EventCalendarDateDAO dao =
                (EventCalendarDateDAO)getEventDateTable().getSelectedRowData();

            if (dao.isNewDate()) {
                // This will not be reached
                dao.setDateType(EventCalendarDateDAO.DateType.INSERT);
            } else {
                dao.setDateType(EventCalendarDateDAO.DateType.NO_OP);
            }
        }

        getEventDateTable().getSelectedRowKeys().clear();
        resetTableButtons();
    }

    public void deleteButtonHandler(ActionEvent event) {
        if (getEventDateTable().getSelectedRowKeys().size() == 1) {
            EventCalendarDateDAO dao =
                (EventCalendarDateDAO)getEventDateTable().getSelectedRowData();

            if (dao.isNewDate()) {
                ((List<EventCalendarDateDAO>)getEventDateTable().getValue()).remove(dao);
            } else {
                dao.setDateType(EventCalendarDateDAO.DateType.DELETE);
            }
        }

        getEventDateTable().getSelectedRowKeys().clear();
        resetTableButtons();
    }

    public void tableRowSelectionHandler(SelectionEvent event) {

        boolean disableDelete;
        boolean disableUndo;

        if (getEventDateTable().getSelectedRowKeys().size() == 1) {
            EventCalendarDateDAO dao =
                (EventCalendarDateDAO)getEventDateTable().getSelectedRowData();

            disableDelete = dao.getDateType().isDelete();
            disableUndo = !dao.getDateType().isDelete();
        } else {
            disableDelete = true;
            disableUndo = true;
        }

        getDeleteEventDateButton().setDisabled(disableDelete);
        getUndoDeleteEventDateButton().setDisabled(disableUndo);

//        addPartialTarget(getDeleteEventDateButton());
//        addPartialTarget(getUndoDeleteEventDateButton());
    }

    /**
     * Validates and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Calendar Table should NOT be empty
     *
     * ADD Mode Tests:
     * ==============
     *    1. Calendar Name should NOT exist
     *
     * EDIT Mode Tests:
     * ===============
     *    1. Calendar Table should NOT contain ALL deleted dates
     *
     * When all is valid, perform the following
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {

        boolean allValid = true;
        boolean currentNavFlowMode = true;

        boolean checkForCalendarNameExistance = currentNavFlowMode;
        boolean checkForAllDeletedDates = currentNavFlowMode;

        System.out.println("Check for Calendar Name = " +
                           checkForCalendarNameExistance);

        System.out.println("Check for Deleted Dates = " +
                           checkForAllDeletedDates);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            List<EventCalendarDateDAO> eventCalendarDateDAOs =
                (List<EventCalendarDateDAO>)getEventDateTable().getValue();

            if (eventCalendarDateDAOs.isEmpty()) {
                allValid = false;
//                setInlineMessageText("Dates Table should NOT be empty");
            }
        }

        System.out.println("After Table check = " + allValid);

        if (allValid && checkForCalendarNameExistance) {
            String currentCalendarName = getCurrentCalendarName();

//            EventCalendarDOStatus eventCalendarDOStatus = new EventCalendarDOStatus(null);
//
//            if (eventCalendarDOStatus.existsDO()) {
//                allValid = false;
//                setInlineMessageText("Calendar Name already exists");
//            }
        }

        System.out.println("After Calendar Name check = " + allValid);

        if (allValid && checkForAllDeletedDates) {
            List<EventCalendarDateDAO> eventCalendarDateDAOs =
                (List<EventCalendarDateDAO>)getEventDateTable().getValue();

            boolean allDelete = true;

            for (EventCalendarDateDAO dao : eventCalendarDateDAOs) {
                allDelete = (allDelete && dao.getDateType().isDelete());
            }

            if (allDelete) {
                allValid = false;
//                setInlineMessageText("Calendar Table should not have all deleted dates");
            }
        }

        System.out.println("After ALL Delete check = " + allValid);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        //        allValid = false;
        //        String tttt =
        //            StringUtil.isBlank(getInlineMessageText()) ? "All are valid" :
        //            getInlineMessageText();
        //        setInlineMessageText(tttt);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        Boolean currentPageSaved = Boolean.FALSE;
        OperationStatus operationStatus = null;

        if (allValid) {

            List<EventCalendarDateDAO> DAOs =
                (List<EventCalendarDateDAO>)getEventDateTable().getValue();

            if (currentNavFlowMode) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ADD Mode
                String currentCalendarName = getCurrentCalendarName().trim();
                operationStatus =
                        DMLOperationsProvider.INSTANCE.addEventCalendar(currentCalendarName,
                                                                        DAOs);
            } else if (currentNavFlowMode) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                String calendarName = "";
//                    (String)getPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_NAME_FOR_PROPS.getKey());

                operationStatus =
                        DMLOperationsProvider.INSTANCE.editEventCalendar(calendarName,
                                                                         DAOs);
            }

            if (operationStatus == null) {
//                setInlineMessageText("There were no changes. So nothing was saved.");
//                setInlineMessageClass("");
                currentPageSaved = Boolean.TRUE;

            } else {
                if (operationStatus.getType().isSuccess()) {
                    String saveInlineMessage = "Successfully saved calendar";

//                    setPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_SAVED_MESSAGE.getKey(),
//                                          saveInlineMessage);

//                    clearPageFlowScopeCache();
                    currentPageSaved = Boolean.TRUE;
//                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
//                                         NavigationFlow.LIST_EVENT_CALENDAR.name());

                } else {
//                    setInlineMessageText("Failed to save event calendar");
//                    setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
                    currentPageSaved = Boolean.FALSE;

                }
            }

//            setCurrentPageSaved(currentPageSaved);
        } else {
//            setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void resetTableButtons() {
        getDeleteEventDateButton().setDisabled(true);
        getUndoDeleteEventDateButton().setDisabled(true);
    }

    private void ignoreAndMoveOn() {
//        clearPageFlowScopeCache();
//        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
//                             NavigationFlow.LIST_EVENT_CALENDAR.name());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS EXTRA

    public String getBreadCrumbPageTitle() {
        String calendarName = "";
//            (String)getPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_NAME_FOR_PROPS.getKey());

        return (calendarName == null) ? "ADD Event Calendar" : calendarName;
    }

    public void setCurrentCalendarName(String CurrentCalendarName) {
//        this.setPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_NAME_FOR_ADDITION.getKey(),
//                                   CurrentCalendarName);
    }

    public String getCurrentCalendarName() {
        return "";// (String)getPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_NAME_FOR_ADDITION.getKey());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    public RichCommandButton getDeleteEventDateButton() {
        return deleteEventDateButton;
    }

    public RichCommandButton getUndoDeleteEventDateButton() {
        return undoDeleteEventDateButton;
    }

    public RichTable getEventDateTable() {
        return eventDateTable;
    }
}
