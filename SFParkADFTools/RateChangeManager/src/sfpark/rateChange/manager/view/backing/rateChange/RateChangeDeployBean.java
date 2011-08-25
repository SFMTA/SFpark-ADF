package sfpark.rateChange.manager.view.backing.rateChange;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.helper.dto.RateChangeProcessControlDTOStatus;
import sfpark.adf.tools.model.provider.RateChangeProcessControlProvider;
import sfpark.adf.tools.model.status.OperationStatus;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.RateChangeManagerBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class RateChangeDeployBean extends BaseBean implements PropertiesBeanInterface,
                                                              RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public RateChangeDeployBean() {
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

    private RateChangeHeaderDTO getRateChangeHeaderDTO() {

        RateChangeHeaderDTO DTO =
            (RateChangeHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey());

        if (DTO == null) {
            // Should NOT happen
            // Just in case
            DTO = DMLOperationsProvider.INSTANCE.getNewRateChangeHeaderDTO();
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    public RateChangeProcessControlDTO getRateChangeProcessControlDTO() {

        RateChangeProcessControlDTO DTO =
            (RateChangeProcessControlDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey());

        if (DTO == null) {
            DTO =
DMLOperationsProvider.INSTANCE.getNewRateChangeProcessControlDTO(getRateChangeHeaderDTO());
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL READ-ONLY INFORMATION

    public boolean isReadOnly() {
        return (getCurrentPageMode().isReadOnlyMode());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    public boolean isRenderButtons() {
        return (!getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isRenderCenterFormPanel() {
        return (!getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isRenderPassiveInfo() {
        return getCurrentPageMode().isReadOnlyMode();
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

        if (ID.contains("meterVendor")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickMeter.name());
        } else if (ID.contains("pmDistricts")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickPMDistrictsAndBlocks.name());
        }

    }

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Meter Vendor should be selected
     *    2. Rate Change Reference should NOT exist
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        RateChangeProcessControlDTO rateChangeProcessControlDTO =
            getRateChangeProcessControlDTO();

        boolean checkForRateChgRefUniqueness = false;

        if (currentPageMode.isDeployMode()) {
            checkForRateChgRefUniqueness = true;
        }

        printLog("Check for RateChgRef = " + checkForRateChgRefUniqueness);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            String meterVendor = rateChangeProcessControlDTO.getMeterVendor();

            if (StringUtil.isBlank(meterVendor)) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_meter_vendor));
            }
        }

        printLog("After Meter Vendor = " + allValid);

        if (allValid && checkForRateChgRefUniqueness) {
            RateChangeProcessControlDTOStatus rateChangeProcessControlStatus =
                RateChangeProcessControlProvider.INSTANCE.checkForRateChangeReference(rateChangeProcessControlDTO.getRateChangeReference());

            if (rateChangeProcessControlStatus.existsDTO()) {
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

            if (currentPageMode.isDeployMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // DEPLOY Mode
                printLog("DEPLOY Mode");

                RateChangeProcessControlDTO currentDTO =
                    getRateChangeProcessControlDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addRateChangeProcessControl(currentDTO);

                if (operationStatus.getType().isSuccess()) {
                    RateChangeProcessControlDTOStatus rateChangeProcessControlStatus =
                        RateChangeProcessControlProvider.INSTANCE.checkForRateChangeReference(currentDTO.getRateChangeReference());

                    if (rateChangeProcessControlStatus.existsDTO()) {
                        printLog("ADD operation was successful");

                        setInlineMessageText(TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.info_create_success));
                        setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);

                        clearPageFlowScopeCache();
                        setCurrentPageMode(NavigationMode.READ_ONLY);
                        setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
                                              rateChangeProcessControlStatus.getDTO());

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
}
