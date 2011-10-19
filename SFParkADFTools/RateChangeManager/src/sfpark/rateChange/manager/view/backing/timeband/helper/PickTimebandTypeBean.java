package sfpark.rateChange.manager.view.backing.timeband.helper;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dO.timeBandModel.TimeBandModelDO;
import sfpark.adf.tools.model.provider.TimeBandModelProvider;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.helper.ADFUIHelper;
import sfpark.rateChange.manager.view.helper.BlockTimeBandTypeTO;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class PickTimebandTypeBean extends BaseBean implements PropertiesBeanInterface,
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
        removePageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BAND_TYPE_TO.getKey());
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
    // ALL TO INFORMATION

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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    public boolean isRenderTimeValue() {
        return (getCurrentPageMode().isAddMode());
    }

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
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

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
                // ADD Mode
                printLog("ADD Mode");

                List<TimeBandModelDO> timeBandModelDOs =
                    TimeBandModelProvider.INSTANCE.getTimeBandModelDOsFor(getBlockTimeBandsTypeTO().getMeterClass(),
                                                                          getBlockTimeBandsTypeTO().getDateType());

                if (timeBandModelDOs != null && !timeBandModelDOs.isEmpty()) {
                    // Template exists
                    // Go to Options page
                    setPageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_DO_LIST.getKey(),
                                          timeBandModelDOs);
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.PickTimebandOptions.name());

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
}
