package sfpark.event.manager.view.backing.eventCalendar;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import sfpark.adf.tools.model.helper.dO.EventCalendarDOStatus;
import sfpark.adf.tools.model.provider.EventCalendarProvider;
import sfpark.adf.tools.model.status.OperationStatus;
import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.event.manager.application.key.PageFlowScopeKey;
import sfpark.event.manager.application.key.SessionScopeKey;
import sfpark.event.manager.view.backing.BaseBean;
import sfpark.event.manager.view.backing.BaseBeanInterface;
import sfpark.event.manager.view.backing.helper.PropertiesBeanInterface;
import sfpark.event.manager.view.flow.NavigationFlow;
import sfpark.event.manager.view.provider.DMLOperationsProvider;

public class EditEventCalendarNameBean extends BaseBean implements BaseBeanInterface,
                                                                   PropertiesBeanInterface {

    private String inlineMessageText;
    private String inlineMessageClass;

    private String currentCalendarName;

    public static EditEventCalendarNameBean getInstance() {
        return (EditEventCalendarNameBean)getCurrentInstanceFor("editEventCalendarNameBean");
    }

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_NAME_FOR_PROPS.getKey());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public EditEventCalendarNameBean() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    /**
     * Validates and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Calendar Name should NOT exist
     *
     * When all is valid, perform the following
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;

        boolean checkForCalendarNameExistance =
            !StringUtil.areEqual(getOriginalCalendarName(),
                                 getCurrentCalendarName());

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid && checkForCalendarNameExistance) {
            String currentCalendarName = getCurrentCalendarName();

            EventCalendarDOStatus eventCalendarDOStatus =
                EventCalendarProvider.INSTANCE.checkForCalendarName(currentCalendarName);

            if (eventCalendarDOStatus.existsDO()) {
                allValid = false;
                setInlineMessageText("Calendar Name already exists");
            }
        }

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

        if (allValid) {
            String originalCalendarName = getOriginalCalendarName().trim();
            String currentCalendarName = getCurrentCalendarName().trim();

            OperationStatus operationStatus =
                DMLOperationsProvider.INSTANCE.changeEventCalendarName(originalCalendarName,
                                                                       currentCalendarName);

            if (operationStatus == null) {
                setInlineMessageText("There were no changes. So nothing was saved.");
                setInlineMessageClass("");

            } else {
                if (operationStatus.getType().isSuccess()) {
                    String saveInlineMessage =
                        "Successfully changed calendar name";

                    setPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_SAVED_MESSAGE.getKey(),
                                          saveInlineMessage);

                    clearPageFlowScopeCache();
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.LIST_EVENT_CALENDAR.name());

                } else {
                    setInlineMessageText("Failed to save event calendar");
                    setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);

                }
            }

        } else {
            setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
        }
    }

    public void cancelButtonHandler(ActionEvent event) {
        clearPageFlowScopeCache();
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.LIST_EVENT_CALENDAR.name());
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS EXTRA

    public String getOriginalCalendarName() {
        String originalCalendarName =
            (String)getPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_NAME_FOR_PROPS.getKey());

        return originalCalendarName;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    public void setInlineMessageText(String inlineMessageText) {
        this.inlineMessageText = inlineMessageText;
    }

    public String getInlineMessageText() {
        return inlineMessageText;
    }

    public void setInlineMessageClass(String inlineMessageClass) {
        this.inlineMessageClass = inlineMessageClass;
    }

    public String getInlineMessageClass() {
        return inlineMessageClass;
    }

    public void setCurrentCalendarName(String currentCalendarName) {
        this.currentCalendarName = currentCalendarName;
    }

    public String getCurrentCalendarName() {
        return currentCalendarName;
    }
}
