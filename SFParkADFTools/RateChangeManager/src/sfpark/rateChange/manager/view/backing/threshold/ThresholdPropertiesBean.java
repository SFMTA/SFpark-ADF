package sfpark.rateChange.manager.view.backing.threshold;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeRulesDTO;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.RateChangeRulesProvider;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.RateChangeManagerBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class ThresholdPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                 RequestScopeBeanInterface {

    private Date EffectiveFromDate;

    private RichTable RateChangeRulesTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public ThresholdPropertiesBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
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

    public String getRateChangeType() {

        String rateChangeType =
            (String)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_RULE_TYPE.getKey());

        if (rateChangeType == null) {
            rateChangeType = "";
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_RULE_TYPE.getKey(),
                                  rateChangeType);
        }

        return rateChangeType;
    }

    public List<RateChangeRulesDTO> getApplicableRateChangeRules() {

        List<RateChangeRulesDTO> rateChangeRulesDTOs =
            (List<RateChangeRulesDTO>)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_RULES_DTO_LIST.getKey());

        if (rateChangeRulesDTOs == null) {
            rateChangeRulesDTOs = new ArrayList<RateChangeRulesDTO>();
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_RULES_DTO_LIST.getKey(),
                                  rateChangeRulesDTOs);
        }

        return rateChangeRulesDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void pickButtonHandler(ActionEvent event) {
        //
        // Reuse as various buttons
        //

        String ID = event.getComponent().getId();

        if (ID.contains("pickThreshold")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickThresholdValues.name());
        }

    }

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Table should not be empty
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            List<RateChangeRulesDTO> checkList =
                (List<RateChangeRulesDTO>)getRateChangeRulesTable().getValue();

            if (checkList == null || checkList.isEmpty()) {
                allValid = false;
                setInlineMessageText("There are no thresholds to save."); // TODO
            }
        }

        printLog("After check list = " + allValid);

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

            if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                printLog("EDIT Mode");

                String rateChangeType = getRateChangeType();
                Date effectiveFromDate = getEffectiveFromDate();
                List<RateChangeRulesDTO> applyList =
                    (List<RateChangeRulesDTO>)getRateChangeRulesTable().getValue();
                List<RateChangeRulesDTO> replaceList =
                    getReplaceableRateChangeRules();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.editRateChangeRules(rateChangeType,
                                                                       effectiveFromDate,
                                                                       applyList,
                                                                       replaceList);

                if (operationStatus.isSuccess()) {
                    // Move on to the next page
                    // Reuse the ERROR_TITLE and ERROR_MESSAGE variables
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                          TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.string_title_edit_operation_successful));
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.string_message_edit_rate_change_rules_successful,
                                                                                           getRateChangeType()));
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.AfterEditThreshold.name());

                } else {
                    printLog("ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_edit_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }

            }

        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }
    }

    public void cancelButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    private Date MinimumDate;

    public Date getMinimumDate() {

        if (MinimumDate == null) {
            MinimumDate =
                    RateChangeRulesProvider.INSTANCE.getNextEffectiveFromDate(getRateChangeType());
        }

        return MinimumDate;
    }

    public List<RateChangeRulesDTO> getReplaceableRateChangeRules() {
        return RateChangeRulesProvider.INSTANCE.getReplaceableRateChangeRules(getRateChangeType());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setEffectiveFromDate(Date EffectiveFromDate) {
        this.EffectiveFromDate = EffectiveFromDate;
    }

    public Date getEffectiveFromDate() {
        return EffectiveFromDate;
    }

    public void setRateChangeRulesTable(RichTable RateChangeRulesTable) {
        this.RateChangeRulesTable = RateChangeRulesTable;
    }

    public RichTable getRateChangeRulesTable() {
        return RateChangeRulesTable;
    }
}
