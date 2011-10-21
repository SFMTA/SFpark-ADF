package sfpark.rateChange.manager.view.backing.timeband.helper.add;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dO.timeBandModel.TimeBandModelDO;
import sfpark.adf.tools.model.data.dto.blockTimeBands.BlockTimeBandsDTO;
import sfpark.adf.tools.model.provider.TimeBandModelProvider;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.timeband.TimebandAbstractBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;

public class PickTimebandOptionBean extends TimebandAbstractBean implements PropertiesBeanInterface,
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
        super.clearPageFlowScopeCache();
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

    //    public BlockTimeBandsWrapper getBlockTimeBandsWrapper() {
    //        BlockTimeBandsWrapper wrapper =
    //            (BlockTimeBandsWrapper)getPageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BANDS_WRAPPER.getKey());
    //
    //        if (wrapper == null) {
    //            wrapper =
    //                    DMLOperationsProvider.INSTANCE.getNewBlockTimeBandsWrapper(getBlockID());
    //            setPageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BANDS_WRAPPER.getKey(),
    //                                  wrapper);
    //        }
    //
    //        return wrapper;
    //    }

    public List<TimeBandModelDO> getTimeBandModelDOs() {
        return TimeBandModelProvider.INSTANCE.getTimeBandModelDOsFor(getBlockTimeBandsWrapper().getMeterClass(),
                                                                     getBlockTimeBandsWrapper().getDateType());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListOpenTime() {
        List<SelectItem> list = new ArrayList<SelectItem>();

        int latestOpenTime = Integer.parseInt(getLatestOpenTime());

        for (int i = 0, time = 0; time < latestOpenTime; i++) {
            list.add(new SelectItem(TimeDisplayUtil.extractAnyTimeForDisplay(time)));
            time += (i % 2 == 0) ? 30 : 70;
        }

        return list;
    }

    public List<SelectItem> getListCloseTime() {
        List<SelectItem> list = new ArrayList<SelectItem>();

        int earliestCloseTime = Integer.parseInt(getEarliestCloseTime());

        int startTime =
            earliestCloseTime + (((earliestCloseTime % 100) == 30) ? 70 : 30);

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

        // Clear out old values
        removePageFlowScopeValue(PageFlowScopeKey.ADD_BLOCK_TIME_BANDS_DTO_LIST.getKey());

        String ID = event.getComponent().getId();

        if (ID.contains("pickValues")) {

            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickTimebandValues.name());

        } else if (ID.contains("pickTemplate")) {
            // Define the list
            List<BlockTimeBandsDTO> DTOs = new ArrayList<BlockTimeBandsDTO>();

            // Get the Time Band Model
            List<TimeBandModelDO> timeBandModel = getTimeBandModelDOs();

            // Get the Open and Close time
            int openTime =
                Integer.parseInt(TimeDisplayUtil.extractFromTimeForUpdate(getBlockTimeBandsWrapper().getOpenTime()));
            int closeTime =
                Integer.parseInt(TimeDisplayUtil.extractToTimeForUpdate(getBlockTimeBandsWrapper().getCloseTime()));

            for (TimeBandModelDO DO : timeBandModel) {
                BlockTimeBandsDTO DTO = BlockTimeBandsDTO.extract(DO);
                DTO.setBlockID(getBlockTimeBandsWrapper().getBlockID());

                int fromTime =
                    (isStringOpen(DO.getTimeBandFrom())) ? openTime :
                    Integer.parseInt(DO.getTimeBandFrom());
                DTO.setFromTime(fromTime);

                int toTime =
                    (isStringClose(DO.getTimeBandTo())) ? closeTime : Integer.parseInt(DO.getTimeBandTo());
                DTO.setToTime(toTime);

                DTOs.add(DTO);
            }

            setPageFlowScopeValue(PageFlowScopeKey.ADD_BLOCK_TIME_BANDS_DTO_LIST.getKey(),
                                  DTOs);

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
            if (isStringOpen(DO.getTimeBandFrom())) {
                return DO.getTimeBandTo();
            }
        }

        return "2400"; // Maximum allowed value
    }

    private String getEarliestCloseTime() {

        for (TimeBandModelDO DO : getTimeBandModelDOs()) {
            if (isStringClose(DO.getTimeBandTo())) {
                return DO.getTimeBandFrom();
            }
        }

        return "0"; // Minimum allowed value
    }

    private boolean isStringOpen(String string) {
        return StringUtil.areEqual("Open", string);
    }

    private boolean isStringClose(String string) {
        return StringUtil.areEqual("Close", string);
    }
}
