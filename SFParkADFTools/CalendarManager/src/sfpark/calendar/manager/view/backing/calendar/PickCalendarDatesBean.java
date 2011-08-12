package sfpark.calendar.manager.view.backing.calendar;

import java.sql.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import java.util.TreeSet;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichChooseDate;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import org.apache.myfaces.trinidad.model.DateListProvider;

import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.calendar.manager.application.key.PageFlowScopeKey;
import sfpark.calendar.manager.application.key.SessionScopeKey;
import sfpark.calendar.manager.view.backing.BaseBean;
import sfpark.calendar.manager.view.flow.NavigationFlow;

public class PickCalendarDatesBean extends BaseBean implements ListBeanInterface,
                                                               PropertiesBeanInterface,
                                                               RequestScopeBeanInterface {

    private Date ChooseDate;

    private RichChooseDate CalendarDisplay;

    private RichCommandButton RemoveButton;
    private RichTable ChosenDatesTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickCalendarDatesBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        // TODO
    }

    public void setInlineMessageText(String inlineMessageText) {
    }

    public String getInlineMessageText() {
        return null;
    }

    public void setInlineMessageClass(String inlineMessageClass) {
    }

    public String getInlineMessageClass() {
        return null;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public List<Date> getChosenDates() {
        List<Date> chosenDates =
            (List<Date>)getPageFlowScopeValue(PageFlowScopeKey.DATE_PICKER_CHOSEN_DATES_LIST.getKey());

        if (chosenDates == null) {
            chosenDates = new ArrayList<Date>();
            setPageFlowScopeValue(PageFlowScopeKey.DATE_PICKER_CHOSEN_DATES_LIST.getKey(),
                                  chosenDates);
        }

        return chosenDates;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALIDATORS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL READ-ONLY INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL VISIBLE INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DISPLAY LIST VALUES

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALUE CHANGE HANDLERS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void addButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void editButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void deleteButtonHandler(ActionEvent event) {
        // TODO
        
        /*
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
         */
    }

    public void selectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        boolean disable =
            (getChosenDatesTable().getSelectedRowKeys().size() != 1);

        getRemoveButton().setDisabled(disable);
        addPartialTarget(getRemoveButton());
    }

    public void saveButtonHandler(ActionEvent event) {
        // TODO
        
        /*
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
         */
        
        moveOn();
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {

        Date chosenDate = (Date)event.getNewValue();

        // Disable the date to avoid further addition
        TreeSet<Date> disabledDateTreeSet = getDisabledDateTreeSet();
        disabledDateTreeSet.add(chosenDate);
        setDisabledDateTreeSet(disabledDateTreeSet);
        setCalendarDisplayDate(chosenDate);
        addPartialTarget(getCalendarDisplay());

        // Add the chosen date to the Table
        TreeSet<Date> chosenDateTreeSet =
            new TreeSet<Date>((List<Date>)getChosenDatesTable().getValue());
        chosenDateTreeSet.add(chosenDate);
        getChosenDatesTable().setValue(Arrays.asList(chosenDateTreeSet.toArray()));
        addPartialTarget(getChosenDatesTable());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void setCalendarDisplayDate(Date value) {
        getCalendarDisplay().getAttributes().put("value", value);
    }

    private void moveOn() {
        clearPageFlowScopeCache();

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.EditCalendar.name());
    }

    private void setDisabledDateTreeSet(TreeSet<Date> disabledDateTreeSet) {
        setPageFlowScopeValue(PageFlowScopeKey.DATE_PICKER_DISABLED_DATES_SET.getKey(),
                              disabledDateTreeSet);
    }

    private TreeSet<Date> getDisabledDateTreeSet() {
        TreeSet<Date> disabledDateTreeSet =
            (TreeSet<Date>)getPageFlowScopeValue(PageFlowScopeKey.DATE_PICKER_DISABLED_DATES_SET.getKey());

        if (disabledDateTreeSet == null) {
            disabledDateTreeSet = new TreeSet<Date>();
            setPageFlowScopeValue(PageFlowScopeKey.DATE_PICKER_DISABLED_DATES_SET.getKey(),
                                  disabledDateTreeSet);
        }

        return disabledDateTreeSet;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public DateListProvider getDateListProvider() {
        DateListProvider dateListProvider = new DateListProvider() {
            public List<java.util.Date> getDateList(FacesContext facesContext,
                                                    Calendar calendar,
                                                    java.util.Date date,
                                                    java.util.Date date1) {

                List<java.util.Date> disabledDates =
                    new ArrayList<java.util.Date>();

                disabledDates.addAll(getDisabledDateTreeSet());

                return disabledDates;
            }
        };

        return dateListProvider;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setChooseDate(Date ChooseDate) {
        this.ChooseDate = ChooseDate;
    }

    public Date getChooseDate() {
        return ChooseDate;
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

    public void setChosenDatesTable(RichTable ChosenDatesTable) {
        this.ChosenDatesTable = ChosenDatesTable;
    }

    public RichTable getChosenDatesTable() {
        return ChosenDatesTable;
    }
}
