package sfpark.rateChange.manager.view.backing.deployment;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;

public class DeploymentPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                  RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public DeploymentPropertiesBean() {
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

    public RateChangeProcessControlDTO getRateChangeProcessControlDTO() {

        RateChangeProcessControlDTO DTO =
            (RateChangeProcessControlDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey());

        if (DTO == null) {
            // Should NOT happen
            // Just in case
            DTO = new RateChangeProcessControlDTO();
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL READ-ONLY INFORMATION

    //    public boolean isReadOnly() {
    //        return (getCurrentPageMode().isReadOnlyMode());
    //    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    //    public boolean isRenderButtons() {
    //        return (!getCurrentPageMode().isReadOnlyMode());
    //    }
    //
    //    public boolean isRenderCenterFormPanel() {
    //        return (!getCurrentPageMode().isReadOnlyMode());
    //    }
    //
    //    public boolean isRenderPassiveInfo() {
    //        return getCurrentPageMode().isReadOnlyMode();
    //    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    //    public List<SelectItem> getListRateChgPolicy() {
    //        List<SelectItem> listRateChgPolicy = new ArrayList<SelectItem>();
    //
    //        for (AllowedValuesDTO allowedValuesDTO :
    //             AllowedValuesProvider.getRateChgPolicyList()) {
    //            listRateChgPolicy.add(new SelectItem(allowedValuesDTO.getColumnValue(),
    //                                                 allowedValuesDTO.getDescription()));
    //        }
    //
    //        return listRateChgPolicy;
    //    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
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
    // HELPER METHODS

    private void printLog(String message) {
        System.out.println(message);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

}

/*

    public void pickButtonHandler(ActionEvent event) {
        //
        // Reuse as various buttons
        //

        String ID = event.getComponent().getId();

        if (ID.contains("areaType")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickAreaType.name());
        } else if (ID.contains("calendar")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickCalendar.name());
        }
    }

     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Calendar ID should not be ZERO
     *    2. Rate Change Reference should NOT exist
     *
     * @param event
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        RateChangeHeaderDTO rateChangeHeaderDTO = getRateChangeHeaderDTO();

        boolean checkForRateChgRefUniqueness = false;

        if (currentPageMode.isAddMode()) {
            checkForRateChgRefUniqueness = true;
        }

        printLog("Check for RateChgRef = " + checkForRateChgRefUniqueness);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            String calendarID = rateChangeHeaderDTO.getCalendarID();

            if (StringUtil.isBlank(calendarID) ||
                StringUtil.areEqual(calendarID, "0")) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_calendar_id));
            }

        }

        printLog("After Calendar ID = " + allValid);

        if (allValid && checkForRateChgRefUniqueness) {
            RateChangeHeaderDTOStatus rateChangeHeaderStatus =
                RateChangeHeaderProvider.INSTANCE.checkForRateChangeReference(rateChangeHeaderDTO.getRateChangeReference());

            if (rateChangeHeaderStatus.existsDTO()) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exists_already_rate_change_reference));
            }
        }

        printLog("After Rate Chg Ref check = " + allValid);

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

                RateChangeHeaderDTO currentDTO = getRateChangeHeaderDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addRateChangeHeader(currentDTO);

                if (operationStatus.getType().isSuccess()) {
                    RateChangeHeaderDTOStatus rateChangeHeaderStatus =
                        RateChangeHeaderProvider.INSTANCE.checkForRateChangeReference(currentDTO.getRateChangeReference());

                    if (rateChangeHeaderStatus.existsDTO()) {
                        printLog("ADD operation was successful");

                        // TODO Call stored procedure

                        setInlineMessageText(TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.info_create_success));
                        setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);

                        clearPageFlowScopeCache();
                        setCurrentPageMode(NavigationMode.READ_ONLY);
                        setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                              rateChangeHeaderStatus.getDTO());

                    } else {
                        printLog("ADD operation failed");
                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_rate_change_reference_failure));
                        setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
                    }
                } else {
                    printLog("ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_rate_change_reference_failure));
                    setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
                }

            }

        } else {
            setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
        }
    }
 */