package sfpark.rateChange.manager.view.backing.deployment;

import javax.faces.event.ActionEvent;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.helper.ADFUIHelper;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class DeploymentDeleteBean extends BaseBean implements RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public DeploymentDeleteBean() {
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
    // EVENT HANDLERS

    public void deleteButtonHandler(ActionEvent event) {
        RateChangeProcessControlDTO DTO = getRateChangeProcessControlDTO();

        OperationStatus operationStatus =
            DMLOperationsProvider.INSTANCE.deleteRateChangeProcessControl(DTO);

        if (operationStatus.isSuccess()) {
            // Move on to the next page
            // Reuse the ERROR_TITLE and ERROR_MESSAGE variables
            setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                  TranslationUtil.getCommonBundleString(CommonBundleKey.string_title_delete_operation_successful));
            setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                  TranslationUtil.getCommonBundleString(CommonBundleKey.string_message_delete_rate_change_process_control_successful,
                                                                        DTO.getRateChangeReference()));
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.AfterDeleteRateChangeProcess.name());

        } else {
            setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_delete_failure));
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);

        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public String getDisplayCurrentExecutionStatus() {
        return ADFUIHelper.getDisplayableStepExecutionStatus(getRateChangeProcessControlDTO().getStepExecStatus());
    }

    public String getDisplayCurrentProcessStep() {
        return ADFUIHelper.getDisplayableProcessStep(getRateChangeProcessControlDTO().getProcessStep());
    }
}
