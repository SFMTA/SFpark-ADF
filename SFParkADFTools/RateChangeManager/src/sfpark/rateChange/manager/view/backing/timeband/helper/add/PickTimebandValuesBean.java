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

import sfpark.adf.tools.model.data.dto.blockTimeBands.BlockTimeBandsDTO;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;
import sfpark.adf.tools.utilities.ui.TimeUI;
import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.timeband.TimebandAbstractBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.helper.BlockTimeBandsWrapper;

public class PickTimebandValuesBean extends TimebandAbstractBean implements ListBeanInterface,
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
        super.clearPageFlowScopeCache();
    }

    public void setInlineMessageText(String inlineMessageText) {
        this.setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey(),
                                   inlineMessageText);
    }

    public String getInlineMessageText() {
        return (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey());
    }

    public void setInlineMessageClass(String inlineMessageClass) {
        this.setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey(),
                                   inlineMessageClass);
    }

    public String getInlineMessageClass() {
        return (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey());
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
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            List<Integer> list =
                (List<Integer>)getChosenTimebandsTable().getValue();

            if (list == null || list.isEmpty() ||
                list.size() < getMinimumTimebands()) {
                setInlineMessageText("Minimum THREE time band values required!");
                allValid = false;
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
            printLog("All entries are Valid. Proceed");

            if (currentPageMode.isAddMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                printLog("ADD Mode");

                // Clear out old values
                removePageFlowScopeValue(PageFlowScopeKey.ADD_BLOCK_TIME_BANDS_DTO_LIST.getKey());

                // Retrieve Chosen Timebands
                List<Integer> chosenTimebands =
                    (List<Integer>)getChosenTimebandsTable().getValue();

                // Retrieve Transfer Object
                BlockTimeBandsWrapper wrapper = getBlockTimeBandsWrapper();

                // Create empty list
                List<BlockTimeBandsDTO> DTOs =
                    new ArrayList<BlockTimeBandsDTO>();

                int lastIndex = chosenTimebands.size() - 1;

                // Insert into DTOs
                for (int i = 0; i < lastIndex; i++) {

                    BlockTimeBandsDTO DTO = new BlockTimeBandsDTO();

                    DTO.setBlockID(wrapper.getBlockID());
                    DTO.setMeterClass(wrapper.getMeterClass());
                    DTO.setDateType(wrapper.getDateType());

                    DTO.setTimeBandID(i + 1);

                    DTO.setTimeBandFrom((i == 0) ? "Open" :
                                        chosenTimebands.get(i).toString());
                    DTO.setTimeBandTo((i < lastIndex - 1) ?
                                      chosenTimebands.get(i + 1).toString() :
                                      "Close");

                    DTO.setFromTime(chosenTimebands.get(i));
                    DTO.setToTime(chosenTimebands.get(i + 1));

                    DTOs.add(DTO);
                }

                setCurrentPageMode(NavigationMode.ADD);

                removePageFlowScopeValue(PageFlowScopeKey.TIMEBAND_PICKER_CHOSEN_LIST.getKey());

                setPageFlowScopeValue(PageFlowScopeKey.ADD_BLOCK_TIME_BANDS_DTO_LIST.getKey(),
                                      DTOs);

                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.AddTimeband.name());

            }
        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }

    }

    public void cancelButtonHandler(ActionEvent event) {
        clearPageFlowScopeCache();

        setCurrentPageMode(NavigationMode.EDIT);
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.EditTimeband.name());
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private int getMinimumTimebands() {
        return 3;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<String> getDisplayIteratorRows() {
        List<String> rows = new ArrayList<String>();

        List<Integer> timebands =
            (List<Integer>)getChosenTimebandsTable().getValue();

        if (timebands != null && !timebands.isEmpty() &&
            timebands.size() >= getMinimumTimebands()) {
            for (int i = 0; i < timebands.size() - 1; i++) {
                rows.add(String.format("%s - %s",
                                       TimeDisplayUtil.extractAnyTimeForDisplay(timebands.get(i)),
                                       TimeDisplayUtil.extractAnyTimeForDisplay(timebands.get(i +
                                                                                              1))));
            }
        } else {
            rows.add("Minimum THREE time band values required!");
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
