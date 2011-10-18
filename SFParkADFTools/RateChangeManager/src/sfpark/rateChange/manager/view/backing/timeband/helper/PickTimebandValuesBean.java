package sfpark.rateChange.manager.view.backing.timeband.helper;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.helper.TimebandDDO;

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

    public List<TimebandDDO> getChosenTimebands() {
        List<TimebandDDO> chosenTimebands =
            (List<TimebandDDO>)getPageFlowScopeValue(PageFlowScopeKey.TIMEBAND_PICKER_CHOSEN_LIST.getKey());

        if (chosenTimebands == null) {
            chosenTimebands = new ArrayList<TimebandDDO>();
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
        // TODO
        /*
        int chosenThreshold = getChooseThreshold();

        // Add the chosen threshold to the Table
        TreeSet<Integer> chosenThresholdTreeSet =
            new TreeSet<Integer>((List<Integer>)getChosenThresholdsTable().getValue());
        chosenThresholdTreeSet.add(chosenThreshold);
        getChosenThresholdsTable().setValue(new ArrayList<Integer>(chosenThresholdTreeSet));
        addPartialTarget(getChosenThresholdsTable());
        addPartialTarget(getIteratorUI());
         */

    }

    public void editButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void deleteButtonHandler(ActionEvent event) {
        // TODO
        /*
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
         */

    }

    public void selectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        // TODO
        /*
        boolean disable =
            (getChosenThresholdsTable().getSelectedRowKeys().size() != 1);

        getRemoveButton().setDisabled(disable);
        addPartialTarget(getRemoveButton());
         */
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

    /*

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
 */

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<String> getDisplayIteratorRows() {
        List<String> rows = new ArrayList<String>();

        /*
        List<Integer> thresholds = getThresholds();

        for (int i = 0; i < thresholds.size() - 1; i++) {
            rows.add(String.format("%3d - %3d", thresholds.get(i),
                                   thresholds.get(i + 1)));
        }
        */

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
