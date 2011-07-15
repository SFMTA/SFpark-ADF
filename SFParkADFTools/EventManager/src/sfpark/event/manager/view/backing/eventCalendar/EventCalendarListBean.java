package sfpark.event.manager.view.backing.eventCalendar;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dO.eventCalendar.EventCalendarDO;
import sfpark.adf.tools.model.provider.EventCalendarProvider;
import sfpark.adf.tools.model.status.OperationStatus;
import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.event.manager.application.key.PageFlowScopeKey;
import sfpark.event.manager.application.key.SessionScopeKey;
import sfpark.event.manager.i18n.ResourceBundleUtil;
import sfpark.event.manager.view.backing.BaseBean;
import sfpark.event.manager.view.backing.BaseBeanInterface;
import sfpark.event.manager.view.backing.helper.ListBeanInterface;
import sfpark.event.manager.view.backing.util.dialog.DialogBean;
import sfpark.event.manager.view.backing.util.dialog.DialogBeanCallback;
import sfpark.event.manager.view.flow.NavigationFlow;

public class EventCalendarListBean extends BaseBean implements BaseBeanInterface,
                                                               ListBeanInterface {

    private String inlineMessageText;
    private String inlineMessageClass;

    private RichTable eventCalendarTable;

    private RichCommandButton deleteEventCalendarButton;

    private RichCommandButton editCalendarDetailsButton;
    private RichCommandButton editCalendarNameButton;

    public EventCalendarListBean() {
        super();

        String eventCalendarSavedMessage =
            (String)removePageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_SAVED_MESSAGE.getKey());

        setInlineMessageText((StringUtil.isNotBlank(eventCalendarSavedMessage)) ?
                             eventCalendarSavedMessage : "");

        setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);
    }

    public static EventCalendarListBean getInstance() {
        return (EventCalendarListBean)getCurrentInstanceFor("eventCalendarListBean");
    }

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_LIST.getKey());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<EventCalendarDO> getEventCalendarDOs() {
        List<EventCalendarDO> eventCalendarDOs =
            (List<EventCalendarDO>)getPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_LIST.getKey());

        if (eventCalendarDOs == null) {
            eventCalendarDOs =
                    EventCalendarProvider.INSTANCE.getEventCalendarDOs();
            setPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_LIST.getKey(),
                                  eventCalendarDOs);
        }

        return eventCalendarDOs;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void addButtonHandler(ActionEvent event) {
        clearPageFlowScopeCache();
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.ADD_EVENT_CALENDAR.name());
    }

    public void editButtonHandler(ActionEvent event) {
        clearPageFlowScopeCache();

        EventCalendarDO selectedEventCalendarDO =
            (EventCalendarDO)getPageFlowScopeValue(PageFlowScopeKey.SELECTED_EVENT_CALENDAR_FROM_LIST.getKey());

        if (selectedEventCalendarDO != null) {

            setPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_NAME_FOR_PROPS.getKey(),
                                  selectedEventCalendarDO.getCalendarName());
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.EDIT_EVENT_CALENDAR.name());

        }
    }

    public void editCalendarNameButtonHandler(ActionEvent event) {
        clearPageFlowScopeCache();

        EventCalendarDO selectedEventCalendarDO =
            (EventCalendarDO)getPageFlowScopeValue(PageFlowScopeKey.SELECTED_EVENT_CALENDAR_FROM_LIST.getKey());

        if (selectedEventCalendarDO != null) {

            setPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_NAME_FOR_PROPS.getKey(),
                                  selectedEventCalendarDO.getCalendarName());
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.EDIT_EVENT_CALENDAR_NAME.name());

        }
    }

    public void deleteButtonHandler(ActionEvent event) {

        final EventCalendarDO selectedEventCalendarDO =
            (EventCalendarDO)getPageFlowScopeValue(PageFlowScopeKey.SELECTED_EVENT_CALENDAR_FROM_LIST.getKey());

        if (selectedEventCalendarDO != null) {

            DialogBeanCallback deleteCallback = new DialogBeanCallback() {
                public void okButtonHandler() {
                    deleteOkButtonCallback(selectedEventCalendarDO);
                }

                public void cancelButtonHandler() {
                    // Do nothing
                }
            };

            DialogBean.getInstance().WarningDialog(null,
                                                   ResourceBundleUtil.getApplicationBundleString("string_warning_delete_text",
                                                                                                 selectedEventCalendarDO.getCalendarName()),
                                                   deleteCallback);

        }
    }

    public void tableRowSelectionHandler(SelectionEvent event) {

        boolean disabled;

        if (getEventCalendarTable().getSelectedRowKeys().size() == 1) {
            EventCalendarDO selectedEventCalendarDO =
                (EventCalendarDO)getEventCalendarTable().getSelectedRowData();

            setPageFlowScopeValue(PageFlowScopeKey.SELECTED_EVENT_CALENDAR_FROM_LIST.getKey(),
                                  selectedEventCalendarDO);

            disabled = false;
        } else {
            removePageFlowScopeValue(PageFlowScopeKey.SELECTED_EVENT_CALENDAR_FROM_LIST.getKey());

            disabled = true;
        }

        updateAllButtons(disabled);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void updateAllButtons(boolean disabled) {
        getDeleteEventCalendarButton().setDisabled(disabled);

        getEditCalendarDetailsButton().setDisabled(disabled);
        getEditCalendarNameButton().setDisabled(disabled);

        addPartialTarget(getDeleteEventCalendarButton());

        addPartialTarget(getEditCalendarDetailsButton());
        addPartialTarget(getEditCalendarNameButton());
    }

    private void deleteOkButtonCallback(EventCalendarDO eventCalendarDO) {

        if (eventCalendarDO != null) {

            OperationStatus operationStatus =
                EventCalendarProvider.INSTANCE.deleteEventCalendar(eventCalendarDO.getCalendarName());

            if (operationStatus.getType().isSuccess()) {
                // Clear out the inline feedback
                setInlineMessageText("Successfully deleted");
                setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);

            } else {
                setInlineMessageText(ResourceBundleUtil.getApplicationBundleString("error_delete_failure",
                                                                                   eventCalendarDO.getCalendarName()));
                setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);

            }

        }

        clearPageFlowScopeCache();
        removePageFlowScopeValue(PageFlowScopeKey.SELECTED_EVENT_CALENDAR_FROM_LIST.getKey());

        getEventCalendarTable().getSelectedRowKeys().clear();
        addPartialTarget(getEventCalendarTable());
        updateAllButtons(true);
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

    public void setEventCalendarTable(RichTable eventCalendarTable) {
        this.eventCalendarTable = eventCalendarTable;
    }

    public RichTable getEventCalendarTable() {
        return eventCalendarTable;
    }

    public void setDeleteEventCalendarButton(RichCommandButton deleteEventCalendarButton) {
        this.deleteEventCalendarButton = deleteEventCalendarButton;
    }

    public RichCommandButton getDeleteEventCalendarButton() {
        return deleteEventCalendarButton;
    }

    public void setEditCalendarDetailsButton(RichCommandButton editCalendarDetailsButton) {
        this.editCalendarDetailsButton = editCalendarDetailsButton;
    }

    public RichCommandButton getEditCalendarDetailsButton() {
        return editCalendarDetailsButton;
    }

    public void setEditCalendarNameButton(RichCommandButton editCalendarNameButton) {
        this.editCalendarNameButton = editCalendarNameButton;
    }

    public RichCommandButton getEditCalendarNameButton() {
        return editCalendarNameButton;
    }
}
