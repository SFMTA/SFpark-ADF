package sfpark.rateChange.manager.view.backing.threshold.helper;

import java.util.ArrayList;
import java.util.List;

import java.util.TreeSet;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeRulesDTO;
import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;

public class PickThresholdValuesBean extends BaseBean implements ListBeanInterface,
                                                                 PropertiesBeanInterface,
                                                                 RequestScopeBeanInterface {

    private int ChooseThreshold;

    private UIComponent IteratorUI;

    private RichCommandButton RemoveButton;
    private RichTable ChosenThresholdsTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickThresholdValuesBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.THRESHOLD_PICKER_CHOSEN_LIST.getKey());
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

    public List<Integer> getChosenThresholds() {
        List<Integer> chosenThresholds =
            (List<Integer>)getPageFlowScopeValue(PageFlowScopeKey.THRESHOLD_PICKER_CHOSEN_LIST.getKey());

        if (chosenThresholds == null) {
            chosenThresholds = new ArrayList<Integer>();
            setPageFlowScopeValue(PageFlowScopeKey.THRESHOLD_PICKER_CHOSEN_LIST.getKey(),
                                  chosenThresholds);
        }

        return chosenThresholds;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void addButtonHandler(ActionEvent event) {

        int chosenThreshold = getChooseThreshold();

        // Add the chosen threshold to the Table
        TreeSet<Integer> chosenThresholdTreeSet =
            new TreeSet<Integer>((List<Integer>)getChosenThresholdsTable().getValue());
        chosenThresholdTreeSet.add(chosenThreshold);
        getChosenThresholdsTable().setValue(new ArrayList<Integer>(chosenThresholdTreeSet));
        addPartialTarget(getChosenThresholdsTable());
        addPartialTarget(getIteratorUI());

    }

    public void editButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void deleteButtonHandler(ActionEvent event) {

        if (getChosenThresholdsTable().getSelectedRowKeys().size() == 1) {
            Integer integer =
                (Integer)getChosenThresholdsTable().getSelectedRowData();

            // Remove the chosen threshold from the Table
            TreeSet<Integer> chosenThresholdTreeSet =
                new TreeSet<Integer>((List<Integer>)getChosenThresholdsTable().getValue());
            chosenThresholdTreeSet.remove(integer);
            getChosenThresholdsTable().setValue(new ArrayList<Integer>(chosenThresholdTreeSet));

        }

        // To update the table of chosen thresholds
        getChosenThresholdsTable().getSelectedRowKeys().clear();
        addPartialTarget(getChosenThresholdsTable());

        // To disable the button
        getRemoveButton().setDisabled(true);
        addPartialTarget(getRemoveButton());

        // To update the iterator display
        addPartialTarget(getIteratorUI());

    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        boolean disable =
            (getChosenThresholdsTable().getSelectedRowKeys().size() != 1);

        getRemoveButton().setDisabled(disable);
        addPartialTarget(getRemoveButton());
    }

    public void saveButtonHandler(ActionEvent event) {

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
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // DO nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {
        clearPageFlowScopeCache();

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.EditThreshold.name());
    }

    private List<Integer> getThresholds() {
        List<Integer> thresholds = new ArrayList<Integer>();

        thresholds.add(new Integer(0));

        List<Integer> pickedThresholds =
            (List<Integer>)getChosenThresholdsTable().getValue();

        if (pickedThresholds != null && !pickedThresholds.isEmpty()) {
            for (Integer threshold : pickedThresholds) {
                thresholds.add(threshold);
            }
        }

        thresholds.add(new Integer(100));

        return thresholds;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<String> getDisplayIteratorRows() {
        List<String> rows = new ArrayList<String>();

        List<Integer> thresholds = getThresholds();

        for (int i = 0; i < thresholds.size() - 1; i++) {
            rows.add(String.format("%3d - %3d", thresholds.get(i),
                                   thresholds.get(i + 1)));
        }

        return rows;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setChooseThreshold(int ChooseThreshold) {
        this.ChooseThreshold = ChooseThreshold;
    }

    public int getChooseThreshold() {
        return ChooseThreshold;
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

    public void setChosenThresholdsTable(RichTable ChosenThresholdsTable) {
        this.ChosenThresholdsTable = ChosenThresholdsTable;
    }

    public RichTable getChosenThresholdsTable() {
        return ChosenThresholdsTable;
    }
}
