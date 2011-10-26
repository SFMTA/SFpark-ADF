package sfpark.rateChange.manager.view.backing.threshold;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;

public class ThresholdAddBean extends BaseBean implements PropertiesBeanInterface {

    private String RateChangeType;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public ThresholdAddBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListRateChgType() {
        List<SelectItem> listRateChgType = new ArrayList<SelectItem>();

        for (AllowedValuesDTO allowedValuesDTO :
             AllowedValuesRetriever.getRateChgTypeList()) {
            String label =
                allowedValuesDTO.getColumnValue() + " - " + allowedValuesDTO.getDescription();
            listRateChgType.add(new SelectItem(allowedValuesDTO.getColumnValue(),
                                               label));
        }

        return listRateChgType;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    /**
     * REUSED as Continue button
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        NavigationMode currentPageMode = getCurrentPageMode();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (currentPageMode.isAddMode()) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // EDIT Mode
            printLog("EDIT Mode");

            String rateChangeType = getRateChangeType();

            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_RULE_TYPE.getKey(),
                                  rateChangeType);
            setCurrentPageMode(NavigationMode.EDIT);
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.EditThreshold.name());
        }
    }

    public void cancelButtonHandler(ActionEvent event) {
        // Do nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setRateChangeType(String RateChangeType) {
        this.RateChangeType = RateChangeType;
    }

    public String getRateChangeType() {
        return RateChangeType;
    }
}
