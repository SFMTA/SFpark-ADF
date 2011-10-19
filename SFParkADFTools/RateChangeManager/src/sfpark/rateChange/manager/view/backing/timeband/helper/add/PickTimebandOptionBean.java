package sfpark.rateChange.manager.view.backing.timeband.helper.add;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dO.timeBandModel.TimeBandModelDO;
import sfpark.adf.tools.model.provider.TimeBandModelProvider;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.helper.BlockTimeBandTypeTO;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class PickTimebandOptionBean extends BaseBean implements PropertiesBeanInterface,
                                                                RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickTimebandOptionBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BAND_TYPE_TO.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_DO_LIST.getKey());
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

    public BlockTimeBandTypeTO getBlockTimeBandsTypeTO() {
        BlockTimeBandTypeTO TO =
            (BlockTimeBandTypeTO)getPageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BAND_TYPE_TO.getKey());

        if (TO == null) {
            TO = DMLOperationsProvider.INSTANCE.getNewBlockTimeBandTypeTO();
            setPageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BAND_TYPE_TO.getKey(),
                                  TO);
        }

        return TO;
    }

    public List<TimeBandModelDO> getTimeBandModelDOs() {
        List<TimeBandModelDO> timeBandModelDOs =
            (List<TimeBandModelDO>)getPageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_DO_LIST.getKey());

        if (timeBandModelDOs == null) {
            timeBandModelDOs =
                    TimeBandModelProvider.INSTANCE.getTimeBandModelDOsFor(getBlockTimeBandsTypeTO().getMeterClass(),
                                                                          getBlockTimeBandsTypeTO().getDateType());
            setPageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_DO_LIST.getKey(),
                                  timeBandModelDOs);
        }

        return timeBandModelDOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListOpenTime() {
        List<SelectItem> list = new ArrayList<SelectItem>();

        int openTime =
            Integer.parseInt(TimeDisplayUtil.extractAnyTimeForUpdate(getLatestOpenTime()));

        for (int i = 0, time = 0; time < openTime; i++) {
            list.add(new SelectItem(TimeDisplayUtil.extractAnyTimeForDisplay(time)));
            time += (i % 2 == 0) ? 30 : 70;
        }

        return list;
    }

    public List<SelectItem> getListCloseTime() {
        List<SelectItem> list = new ArrayList<SelectItem>();

        int closeTime =
            Integer.parseInt(TimeDisplayUtil.extractAnyTimeForUpdate(getEarliestCloseTime()));

        int startTime = closeTime + (((closeTime % 100) == 30) ? 70 : 30);

        int increment1 = ((startTime % 100) == 30) ? 70 : 30;
        int increment2 = 100 - increment1;

        for (int i = 0, time = startTime; time <= 2400; i++) {
            list.add(new SelectItem(TimeDisplayUtil.extractAnyTimeForDisplay(time)));
            time += (i % 2 == 0) ? increment1 : increment2;
        }

        return list;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
        //
        // Reuse as 'Pick' button
        //

        String ID = event.getComponent().getId();

        if (ID.contains("pickValues")) {
          removePageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_DO_LIST.getKey());
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickTimebandValues.name());
            
        } else if (ID.contains("pickTemplate")) {
            // TODO
            // Get the Open and Close values
            // Generate the DTOs
            
            

            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.AddTimeband.name());
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


    private String getLatestOpenTime() {

        for (TimeBandModelDO DO : getTimeBandModelDOs()) {
            if (StringUtil.areEqual("Open", DO.getTimeBandFrom())) {
                return DO.getTimeBandTo();
            }
        }

        return "2330"; // Maximum allowed value for Open time
    }

    private String getEarliestCloseTime() {

        for (TimeBandModelDO DO : getTimeBandModelDOs()) {
            if (StringUtil.areEqual("Close", DO.getTimeBandTo())) {
                return DO.getTimeBandFrom();
            }
        }

        return "30"; // Minimum allowed value for Close time
    }

}

/*
    private int ChooseTimeband;

    private UIComponent IteratorUI;

    private RichCommandButton RemoveButton;
    private RichTable ChosenTimebandsTable;

    public void saveButtonHandler(ActionEvent event) {
        //
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
         //

    }



    private void moveOn() {
        clearPageFlowScopeCache();

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.EditTimeband.name());
    }

    private List<Integer> getTimebands() {
        List<Integer> timebands = new ArrayList<Integer>();

        timebands.add(new Integer(0));

        List<Integer> pickedTimebands =
            (List<Integer>)getChosenTimebandsTable().getValue();

        if (pickedTimebands != null && !pickedTimebands.isEmpty()) {
            for (Integer timeband : pickedTimebands) {
                timebands.add(timeband);
            }
        }

        timebands.add(new Integer(2400));

        return timebands;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<SelectItem> getListTimeband() {
        List<SelectItem> timebandList = new ArrayList<SelectItem>();


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

 */