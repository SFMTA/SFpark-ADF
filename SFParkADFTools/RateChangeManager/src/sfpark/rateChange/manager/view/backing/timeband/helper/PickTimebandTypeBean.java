package sfpark.rateChange.manager.view.backing.timeband.helper;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dto.timeBandModel.TimeBandModelDTO;
import sfpark.adf.tools.model.provider.TimeBandModelProvider;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.timeband.TimebandAbstractBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.helper.ADFUIHelper;

public class PickTimebandTypeBean extends TimebandAbstractBean implements PropertiesBeanInterface,
                                                                          RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickTimebandTypeBean() {
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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListMeterClass() {
        return ADFUIHelper.getMeterClassDisplayList();
    }

    public List<SelectItem> getListDateType() {
        return ADFUIHelper.getDateTypeDisplayList();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
        //
        // Reuse as 'Continue'
        //

        NavigationMode currentPageMode = getCurrentPageMode();

        if (currentPageMode.isAddMode()) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ADD Mode
            printLog("ADD Mode");

            List<TimeBandModelDTO> TimeBandModelDTOs =
                TimeBandModelProvider.INSTANCE.getTimeBandModelDTOsFor(getBlockTimeBandsWrapper().getMeterClass(),
                                                                       getBlockTimeBandsWrapper().getDateType());

            if (TimeBandModelDTOs != null && !TimeBandModelDTOs.isEmpty()) {
                // Template exists
                // Go to Options page
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.PickTimebandOption.name());

            } else {
                // NO Template exists
                // Go to Values page
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.PickTimebandValues.name());

            }

        } else if (currentPageMode.isDeleteMode()) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // DELETE Mode
            printLog("DELETE Mode");

            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.DeleteTimeband.name());

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
}
