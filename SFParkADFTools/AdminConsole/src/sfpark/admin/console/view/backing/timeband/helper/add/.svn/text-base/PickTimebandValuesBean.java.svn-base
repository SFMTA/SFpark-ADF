package sfpark.admin.console.view.backing.timeband.helper.add;

import java.util.ArrayList;
import java.util.List;

import java.util.TreeSet;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dto.timeBandModel.TimeBandModelDTO;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;
import sfpark.adf.tools.utilities.ui.TimeUI;
import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.backing.timeband.TimebandAbstractBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;
import sfpark.admin.console.view.helper.TimeBandModelWrapper;

public class PickTimebandValuesBean extends TimebandAbstractBean implements ListBeanInterface,
                                                                            PropertiesBeanInterface {

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
    // LIST VALUES

    public List<SelectItem> getListTime() {
        return TimeUI.ANY_TIME_LIST;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void addButtonHandler(ActionEvent event) {

        int chosenTimeband = getChooseTimeband();

        // Add the chosen Timeband to the Table
        TreeSet<Integer> chosenTimebandTreeSet =
            new TreeSet<Integer>((List<Integer>)getChosenTimebandsTable().getValue());
        chosenTimebandTreeSet.add(chosenTimeband);
        getChosenTimebandsTable().setValue(new ArrayList<Integer>(chosenTimebandTreeSet));
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

            // Remove the chosen Timeband from the Table
            TreeSet<Integer> chosenTimebandTreeSet =
                new TreeSet<Integer>((List<Integer>)getChosenTimebandsTable().getValue());
            chosenTimebandTreeSet.remove(integer);
            getChosenTimebandsTable().setValue(new ArrayList<Integer>(chosenTimebandTreeSet));

        }

        // Update the table of chosen Timebands
        getChosenTimebandsTable().getSelectedRowKeys().clear();
        addPartialTarget(getChosenTimebandsTable());

        // Disable the button
        getRemoveButton().setDisabled(true);
        addPartialTarget(getRemoveButton());

        // Update the iterator display
        addPartialTarget(getIteratorUI());

    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        boolean disable =
            (getChosenTimebandsTable().getSelectedRowKeys().size() != 1);

        getRemoveButton().setDisabled(disable);
        addPartialTarget(getRemoveButton());
    }

    public void saveButtonHandler(ActionEvent event) {

        // Clear out old values
        removePageFlowScopeValue(PageFlowScopeKey.ADD_TIME_BAND_MODEL_DTO_LIST.getKey());

        // Create empty list
        List<TimeBandModelDTO> DTOs = new ArrayList<TimeBandModelDTO>();

        // Get the timebands
        List<String> timebands = getTimebands();

        // Retrieve Transfer object
        TimeBandModelWrapper wrapper = getTimeBandModelWrapper();

        // Insert into DTOs
        for (int i = 0; i < timebands.size() - 1; i++) {

            TimeBandModelDTO DTO = new TimeBandModelDTO();

            DTO.setMeterClass(wrapper.getMeterClass());
            DTO.setDateType(wrapper.getDateType());

            DTO.setTimeBandID(i + 1);

            DTO.setTimeBandFrom(timebands.get(i));
            DTO.setTimeBandTo(timebands.get(i + 1));

            DTOs.add(DTO);
        }

        setCurrentPageMode(NavigationMode.ADD);

        removePageFlowScopeValue(PageFlowScopeKey.TIMEBAND_PICKER_CHOSEN_LIST.getKey());

        setPageFlowScopeValue(PageFlowScopeKey.ADD_TIME_BAND_MODEL_DTO_LIST.getKey(),
                              DTOs);

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.ADD_TIME_BAND_MODEL.name());

    }

    public void cancelButtonHandler(ActionEvent event) {
        clearPageFlowScopeCache();

        setCurrentPageMode(NavigationMode.READ_ONLY);
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.TIME_BAND_MODEL_LIST.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private List<String> getTimebands() {
        List<String> timebands = new ArrayList<String>();

        timebands.add("Open");

        List<Integer> pickedTimebands =
            (List<Integer>)getChosenTimebandsTable().getValue();

        if (pickedTimebands != null && !pickedTimebands.isEmpty()) {
            for (Integer timeband : pickedTimebands) {
                timebands.add(timeband.toString());
            }
        }

        timebands.add("Close");

        return timebands;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<String> getDisplayIteratorRows() {
        List<String> rows = new ArrayList<String>();

        List<String> timebands = getTimebands();

        for (int i = 0; i < timebands.size() - 1; i++) {

            String fromTimeString = timebands.get(i);
            String toTimeString = timebands.get(i + 1);

            String displayFromTime =
                (StringUtil.isDigitsONLY(fromTimeString)) ?
                TimeDisplayUtil.extractAnyTimeForDisplay(Integer.parseInt(fromTimeString)) :
                fromTimeString;

            String displayToTime =
                (StringUtil.isDigitsONLY(toTimeString)) ? TimeDisplayUtil.extractAnyTimeForDisplay(Integer.parseInt(toTimeString)) :
                toTimeString;

            rows.add(String.format("%s - %s", displayFromTime, displayToTime));
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
