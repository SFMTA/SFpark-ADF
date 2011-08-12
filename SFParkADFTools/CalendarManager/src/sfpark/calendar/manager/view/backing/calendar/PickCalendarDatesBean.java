package sfpark.calendar.manager.view.backing.calendar;

import java.sql.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import sfpark.calendar.manager.view.provider.DMLOperationsProvider;
import sfpark.calendar.manager.view.provider.helper.CalendarDetailDDO;

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
        removePageFlowScopeValue(PageFlowScopeKey.DATE_PICKER_CHOSEN_DATES_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.DATE_PICKER_DISABLED_DATES_SET.getKey());
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
    // EVENT HANDLERS

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
            TreeSet<Date> disabledDateTreeSet = getDisabledDateTreeSet();
            disabledDateTreeSet.remove(date);
            setDisabledDateTreeSet(disabledDateTreeSet);

            // Remove the chosen date from the Table
            TreeSet<Date> chosenDateTreeSet =
                new TreeSet<Date>((List<Date>)getChosenDatesTable().getValue());
            chosenDateTreeSet.remove(date);
            getChosenDatesTable().setValue(new ArrayList<Date>(chosenDateTreeSet));
        }

        // To update the table of chosen dates
        getChosenDatesTable().getSelectedRowKeys().clear();
        addPartialTarget(getChosenDatesTable());

        // To disable the button
        getRemoveButton().setDisabled(true);
        addPartialTarget(getRemoveButton());

        // To update the enabled dates
        addPartialTarget(getCalendarDisplay());
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

        List<Date> chosenDates = (List<Date>)getChosenDatesTable().getValue();

        if (!chosenDates.isEmpty()) {
            List<CalendarDetailDDO> calendarDetailDDOs =
                (List<CalendarDetailDDO>)getPageFlowScopeValue(PageFlowScopeKey.CALENDAR_DETAIL_DDO_LIST.getKey());

            if (calendarDetailDDOs == null) {
                // Shouldn't happen
                calendarDetailDDOs = new ArrayList<CalendarDetailDDO>();
            }

            calendarDetailDDOs.addAll(DMLOperationsProvider.INSTANCE.getCalendarDetailDDOs(chosenDates));

            Collections.sort(calendarDetailDDOs,
                             new Comparator<CalendarDetailDDO>() {
                    public int compare(CalendarDetailDDO o1,
                                       CalendarDetailDDO o2) {
                        return o1.getDisplayDateDT().compareTo(o2.getDisplayDateDT());
                    }
                });

            setPageFlowScopeValue(PageFlowScopeKey.CALENDAR_DETAIL_DDO_LIST.getKey(),
                                  calendarDetailDDOs);
        }

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
        getChosenDatesTable().setValue(new ArrayList<Date>(chosenDateTreeSet));
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

                return new ArrayList<java.util.Date>(getDisabledDateTreeSet());
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
