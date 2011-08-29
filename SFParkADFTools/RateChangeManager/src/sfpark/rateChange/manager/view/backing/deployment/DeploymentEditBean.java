package sfpark.rateChange.manager.view.backing.deployment;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;

public class DeploymentEditBean extends BaseBean implements PropertiesBeanInterface,
                                                            RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public DeploymentEditBean() {
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
    //
    //    public boolean isReadOnlySubmittedInfo() {
    //        return (getCurrentPageMode().isReadOnlyMode() ||
    //                getRateChangeHeaderDTO().getStatus().isSubmitted() ||
    //                getRateChangeHeaderDTO().getStatus().isApproved());
    //    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL VISIBLE INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    //    public boolean isRenderButtons() {
    //        return (!getCurrentPageMode().isReadOnlyMode());
    //    }
    //
    //    public boolean isRenderApprovedInfo() {
    //        return ((getCurrentPageMode().isUpdateMode() &&
    //                 getRateChangeHeaderDTO().getStatus().isSubmitted()) ||
    //                (getCurrentPageMode().isReadOnlyMode() &&
    //                 getRateChangeHeaderDTO().getStatus().isApproved()));
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

    //    public Date getMinimumAllowedDate() {
    //        return SQLDateUtil.getTodaysDate();
    //    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS
}

/*
     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1.
     *
     * @param event
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;

        RateChangeHeaderDTO rateChangeHeaderDTO = getRateChangeHeaderDTO();

        boolean validateApprovedDate = false;

        if (rateChangeHeaderDTO.getStatus().isSubmitted()) {
            validateApprovedDate = true;
        }

        printLog("Validate Approved Date = " + validateApprovedDate);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid && validateApprovedDate) {
            Date submittedOn = rateChangeHeaderDTO.getSubmittedOn();
            Date approvedOn = rateChangeHeaderDTO.getApprovedOn();

            if (approvedOn.before(submittedOn)) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_date_approved_before_submitted));
            }
        }

        printLog("After validate date = " + allValid);

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

            if (getCurrentPageMode().isUpdateMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // UPDATE Mode
                printLog("UPDATE Mode");

                RateChangeHeaderDTO currentDTO = getRateChangeHeaderDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.updateRateChangeHeader(currentDTO);

                if (operationStatus == null) {
                    printLog("There were no changes. So nothing was saved");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_nothing_to_save));
                    setInlineMessageClass("");

                } else {
                    if (operationStatus.getType().isSuccess()) {
                        printLog("UPDATE operation was successful");
                        setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                        setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);

                        RateChangeHeaderDTOStatus rateChangeHeaderStatus =
                            RateChangeHeaderProvider.INSTANCE.checkForRateChangeReferenceID(currentDTO.getRateChangeReferenceID());

                        setCurrentPageMode(NavigationMode.READ_ONLY);
                        setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                              rateChangeHeaderStatus.getDTO());

                        clearPageFlowScopeCache();

                    } else {
                        printLog("UPDATE operation failed");

                        String errorMessage = "";

                        switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                        case RATE_CHANGE_HEADER_UPDATE:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_rate_change_update);
                            break;

                        default:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                            break;

                        }

                        setInlineMessageText(errorMessage);
                        setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);

                    }

                }

            }

        } else {
            setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
        }
    }


}

 */