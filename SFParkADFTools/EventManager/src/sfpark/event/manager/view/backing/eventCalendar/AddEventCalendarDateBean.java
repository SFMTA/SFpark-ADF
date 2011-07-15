package sfpark.event.manager.view.backing.eventCalendar;

import java.sql.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichChooseDate;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.DateListProvider;

import sfpark.event.manager.application.key.PageFlowScopeKey;
import sfpark.event.manager.application.key.SessionScopeKey;
import sfpark.event.manager.view.backing.BaseBean;
import sfpark.event.manager.view.backing.BaseBeanInterface;
import sfpark.event.manager.view.backing.helper.ListBeanInterface;
import sfpark.event.manager.view.backing.helper.PropertiesBeanInterface;
import sfpark.event.manager.view.provider.helper.eventCalendar.EventCalendarDateDAO;

public class AddEventCalendarDateBean extends BaseBean implements BaseBeanInterface,
                                                                  PropertiesBeanInterface,
                                                                  ListBeanInterface {

    private RichChooseDate CalendarDisplay;

    private RichCommandButton RemoveButton;
    private RichTable ChosenDatesTable;

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_DISABLED_DATES.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_CURRENT_DATE.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_LIST.getKey());
    }

    public AddEventCalendarDateBean() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<Date> getChosenDates() {
        List<Date> chosenDates =
            (List<Date>)getPageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_LIST.getKey());

        if (chosenDates == null) {
            chosenDates = new ArrayList<Date>();
            setPageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_LIST.getKey(),
                                  chosenDates);
        }

        return chosenDates;
    }

    private List<java.util.Date> getDisabledDates() {
        List<java.util.Date> disabledDates =
            (List<java.util.Date>)getPageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_DISABLED_DATES.getKey());

        if (disabledDates == null) {
            disabledDates = new ArrayList<java.util.Date>();
            setPageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_DISABLED_DATES.getKey(),
                                  disabledDates);
        }

        return disabledDates;
    }

    private void setDisabledDates(List<java.util.Date> disabledDates) {
        setPageFlowScopeValue(PageFlowScopeKey.ADD_EVENT_CALENDAR_DATE_DISABLED_DATES.getKey(),
                              disabledDates);
    }

    public DateListProvider getDateListProvider() {
        return new DateListProvider() {
            public List<java.util.Date> getDateList(FacesContext facesContext,
                                                    Calendar calendar,
                                                    java.util.Date date,
                                                    java.util.Date date1) {
                return getDisabledDates();
            }
        };
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void chosenDateValidator(FacesContext facesContext,
                                    UIComponent uiComponent, Object object) {
        // Since the date field is not editable through users, there is no real
        // need to validate the date as of now. Just add it to the table

        java.util.Date chosenDate = (java.util.Date)object;

        // Disable the date to avoid further addition
        List<java.util.Date> disabledDates = getDisabledDates();
        if (!disabledDates.contains(chosenDate)) {
            disabledDates.add(chosenDate);
            setDisabledDates(disabledDates);

            setCalendarDisplayDate(chosenDate);
            addPartialTarget(getCalendarDisplay());
        }

        Date chosenSQLDate = new Date(chosenDate.getTime());

        // Add the chosen date to the Table
        List<Date> chosenDates = (List<Date>)getChosenDatesTable().getValue();
        if (!chosenDates.contains(chosenSQLDate)) {
            chosenDates.add(chosenSQLDate);
            Collections.sort(chosenDates);
            getChosenDatesTable().setValue(chosenDates);
            addPartialTarget(getChosenDatesTable());
        }
    }

    public void saveButtonHandler(ActionEvent event) {

        List<Date> chosenDates = (List<Date>)getChosenDatesTable().getValue();

        if (!chosenDates.isEmpty()) {
            List<EventCalendarDateDAO> eventCalendarDateDAOs =
                (List<EventCalendarDateDAO>)getPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_DATE_LIST.getKey());

            if (eventCalendarDateDAOs == null) {
                // Shouldn't happen
                eventCalendarDateDAOs = new ArrayList<EventCalendarDateDAO>();
            }

            for (Date date : chosenDates) {
                eventCalendarDateDAOs.add(new EventCalendarDateDAO(true,
                                                                   EventCalendarDateDAO.DateType.INSERT,
                                                                   date));
            }

            Collections.sort(eventCalendarDateDAOs,
                             new Comparator<EventCalendarDateDAO>() {
                    public int compare(EventCalendarDateDAO o1,
                                       EventCalendarDateDAO o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });

            setPageFlowScopeValue(PageFlowScopeKey.EVENT_CALENDAR_DATE_LIST.getKey(),
                                  eventCalendarDateDAOs);
        }

        moveOn();
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    public void addButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void editButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void deleteButtonHandler(ActionEvent event) {
        if (getChosenDatesTable().getSelectedRowKeys().size() == 1) {
            Date date = (Date)getChosenDatesTable().getSelectedRowData();

            // Enable the date to allow addition
            List<java.util.Date> disabledDates = getDisabledDates();
            disabledDates.remove(date);
            setDisabledDates(disabledDates);

            // Remove the chosen date from the Table
            List<Date> chosenDates =
                (List<Date>)getChosenDatesTable().getValue();
            chosenDates.remove(date);
            getChosenDatesTable().setValue(chosenDates);
        }

        getChosenDatesTable().getSelectedRowKeys().clear();
        getRemoveButton().setDisabled(true);

        addPartialTarget(getRemoveButton());
        addPartialTarget(getChosenDatesTable());
    }

    public void tableRowSelectionHandler(SelectionEvent event) {

        boolean enable =
            (getChosenDatesTable().getSelectedRowKeys().size() == 1);

        getRemoveButton().setDisabled(!enable);
        addPartialTarget(getRemoveButton());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {
        clearPageFlowScopeCache();
        String backTo =
            (String)getPageFlowScopeValue(PageFlowScopeKey.BACK_FROM_ADD_EVENT_CALENDAR_DATE.getKey());
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(), backTo);
    }

    private void setCalendarDisplayDate(java.util.Date value) {
        getCalendarDisplay().getAttributes().put("value", value);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    public void setChosenDatesTable(RichTable ChosenDatesTable) {
        this.ChosenDatesTable = ChosenDatesTable;
    }

    public RichTable getChosenDatesTable() {
        return ChosenDatesTable;
    }

    public void setCalendarDisplay(RichChooseDate CalendarDisplay) {
        this.CalendarDisplay = CalendarDisplay;
    }

    public RichChooseDate getCalendarDisplay() {
        return CalendarDisplay;
    }

    public void setRemoveButton(RichCommandButton RemoveButton) {
        this.RemoveButton = RemoveButton;
    }

    public RichCommandButton getRemoveButton() {
        return RemoveButton;
    }
}
