package sfpark.rateChange.manager.view.backing.timeband.helper.add;

import java.util.ArrayList;
import java.util.List;

import java.util.TreeSet;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;
import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;

public class PickTimebandValuesBean extends BaseBean implements ListBeanInterface,
                                                                PropertiesBeanInterface,
                                                                RequestScopeBeanInterface {

    private int ChooseTimeband;

    private UIComponent IteratorUI;

    private RichCommandButton RemoveButton;
    private RichTable ChosenTimebandsTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickTimebandValuesBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.TIMEBAND_PICKER_CHOSEN_LIST.getKey());
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

    public List<Integer> getChosenTimebands() {
        List<Integer> chosenTimebands =
            (List<Integer>)getPageFlowScopeValue(PageFlowScopeKey.TIMEBAND_PICKER_CHOSEN_LIST.getKey());

        if (chosenTimebands == null) {
            chosenTimebands = new ArrayList<Integer>();
            setPageFlowScopeValue(PageFlowScopeKey.TIMEBAND_PICKER_CHOSEN_LIST.getKey(),
                                  chosenTimebands);
        }

        return chosenTimebands;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void addButtonHandler(ActionEvent event) {

        int chosenThreshold = getChooseTimeband();

        // Add the chosen threshold to the Table
        TreeSet<Integer> chosenThresholdTreeSet =
            new TreeSet<Integer>((List<Integer>)getChosenTimebandsTable().getValue());
        chosenThresholdTreeSet.add(chosenThreshold);
        getChosenTimebandsTable().setValue(new ArrayList<Integer>(chosenThresholdTreeSet));
        addPartialTarget(getChosenTimebandsTable());
        addPartialTarget(getIteratorUI());

    }

    public void editButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void deleteButtonHandler(ActionEvent event) {

        if (getChosenTimebandsTable().getSelectedRowKeys().size() == 1) {
            Integer integer =
                (Integer)getChosenTimebandsTable().getSelectedRowData();

            // Remove the chosen threshold from the Table
            TreeSet<Integer> chosenThresholdTreeSet =
                new TreeSet<Integer>((List<Integer>)getChosenTimebandsTable().getValue());
            chosenThresholdTreeSet.remove(integer);
            getChosenTimebandsTable().setValue(new ArrayList<Integer>(chosenThresholdTreeSet));

        }

        // To update the table of chosen thresholds
        getChosenTimebandsTable().getSelectedRowKeys().clear();
        addPartialTarget(getChosenTimebandsTable());

        // To disable the button
        getRemoveButton().setDisabled(true);
        addPartialTarget(getRemoveButton());

        // To update the iterator display
        addPartialTarget(getIteratorUI());

    }

    public void selectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        boolean disable =
            (getChosenTimebandsTable().getSelectedRowKeys().size() != 1);

        getRemoveButton().setDisabled(disable);
        addPartialTarget(getRemoveButton());
    }

    public void saveButtonHandler(ActionEvent event) {
        // TODO
        /*
        // Clear out old values
        removePageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_RULES_DTO_LIST.getKey());

        // Define the list
        List<RateChangeRulesDTO> DTOs = new ArrayList<RateChangeRulesDTO>();

        // Get the thresholds
        List<Integer> thresholds = getThresholds();

        for (int i = 0; i < thresholds.size() - 1; i++) {
            RateChangeRulesDTO DTO = new RateChangeRulesDTO();
            DTO.setFromOccupancy(thresholds.get(i));
            DTO.setToOccupancy(thresholds.get(i + 1));
            DTOs.add(DTO);
        }

        setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_RULES_DTO_LIST.getKey(),
                              DTOs);

        moveOn();
         */

    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {
        clearPageFlowScopeCache();

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.EditTimeband.name());
    }

    private List<Integer> getTimebands() {
        List<Integer> timebands = new ArrayList<Integer>();

        timebands.add(new Integer(0)); // TODO Get OPEN value

        List<Integer> pickedTimebands =
            (List<Integer>)getChosenTimebandsTable().getValue();

        if (pickedTimebands != null && !pickedTimebands.isEmpty()) {
            for (Integer timeband : pickedTimebands) {
                timebands.add(timeband);
            }
        }

        timebands.add(new Integer(2400)); // TODO Get CLOSE value

        return timebands;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<SelectItem> getListTimeband() {
        List<SelectItem> timebandList = new ArrayList<SelectItem>();

        // TODO

        return timebandList;
    }

    public List<String> getDisplayIteratorRows() {
        List<String> rows = new ArrayList<String>();

        List<Integer> timebands = getTimebands();

        for (int i = 0; i < timebands.size() - 1; i++) {
            rows.add(String.format("%s - %s",
                                   TimeDisplayUtil.extractAnyTimeForDisplay(timebands.get(i)),
                                   TimeDisplayUtil.extractAnyTimeForDisplay(timebands.get(i +
                                                                                          1))));
        }

        return rows;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setChooseTimeband(int ChooseTimeband) {
        this.ChooseTimeband = ChooseTimeband;
    }

    public int getChooseTimeband() {
        return ChooseTimeband;
    }

    public void setIteratorUI(UIComponent IteratorUI) {
        this.IteratorUI = IteratorUI;
    }

    public UIComponent getIteratorUI() {
        return IteratorUI;
    }

    public void setRemoveButton(RichCommandButton RemoveButton) {
        this.RemoveButton = RemoveButton;
    }

    public RichCommandButton getRemoveButton() {
        return RemoveButton;
    }

    public void setChosenTimebandsTable(RichTable ChosenTimebandsTable) {
        this.ChosenTimebandsTable = ChosenTimebandsTable;
    }

    public RichTable getChosenTimebandsTable() {
        return ChosenTimebandsTable;
    }
}
