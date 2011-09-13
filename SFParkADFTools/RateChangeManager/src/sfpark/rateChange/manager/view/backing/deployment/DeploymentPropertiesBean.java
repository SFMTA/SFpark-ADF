package sfpark.rateChange.manager.view.backing.deployment;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.data.helper.RateChangeProcessTimeLimitOption;
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dto.RateChangeProcessControlDTOStatus;
import sfpark.adf.tools.model.provider.RateChangeProcessControlProvider;

import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.RateChangeManagerBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.helper.ADFUIHelper;
import sfpark.rateChange.manager.view.helper.WebServiceHelper;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

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
    // VALIDATORS

    public void effectiveDateValidator(FacesContext facesContext,
                                       UIComponent uiComponent,
                                       Object object) {

        if (getRateChangeProcessControlDTO().isValidateEffectiveFromDate()) {

            int numOfDays = 16;
            Date initialDate =
                getRateChangeProcessControlDTO().getInitialEffectiveFromDate();

            Date begRange =
                SQLDateUtil.getPreviousDateFor(initialDate, numOfDays);
            Date endRange = SQLDateUtil.getNextDateFor(initialDate, numOfDays);

            Date selectedDate = (Date)object;

            if (selectedDate.after(begRange) &&
                selectedDate.before(endRange)) {
                // Do nothing. Everything is fine

            } else {
                // Display warning information

                FacesMessage facesMessage =
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                     TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.warning_title_finalised_eff_date),
                                     TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.warning_message_finalised_eff_date));

                facesContext.addMessage(uiComponent.getClientId(facesContext),
                                        facesMessage);
            }
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL READ-ONLY INFORMATION

    public boolean isReadOnlyEffectiveFromDate() {
        return (getCurrentPageMode().isExecuteMode() ||
                !getRateChangeProcessControlDTO().isEditableEffectiveFromDate());
    }

    public boolean isReadOnlyTimeLimitOption() {
        return (getCurrentPageMode().isExecuteMode() ||
                !getRateChangeProcessControlDTO().isEditableTimeLimitOption());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    public boolean isRenderXMLInputFileName() {
        return (getCurrentPageMode().isEditMode() &&
                getRateChangeProcessControlDTO().isEditableXMLInputFileName());
    }

    public boolean isRenderComments() {
        return getCurrentPageMode().isEditMode();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    public boolean isDisableValidateAndSaveButton() {
        return getCurrentPageMode().isExecuteMode();
    }

    public boolean isDisableExecuteButton() {
        return getCurrentPageMode().isEditMode();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListTimeLimitOption() {
        List<SelectItem> listTimeLimitOption = new ArrayList<SelectItem>();

        for (RateChangeProcessTimeLimitOption timeLimitOption :
             RateChangeProcessTimeLimitOption.values()) {
            listTimeLimitOption.add(new SelectItem(timeLimitOption,
                                                   timeLimitOption.getStringForDisplay()));
        }

        return listTimeLimitOption;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Under EDIT Mode
     * ===============
     *    1. Save the DTO
     *    2. Set the Page Mode to EXECUTE
     *
     * Under EXECUTE Mode
     * ==================
     *    1. Change the STEP_START_FLAG to Y
     *    2. Save the DTO
     *    3. Call the WebService
     *    4. Move onto the next page
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
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

            if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                printLog("EDIT Mode");

                RateChangeProcessControlDTO currentDTO =
                    getRateChangeProcessControlDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.editRateChangeProcessControl(currentDTO);

                if (operationStatus == null) {
                    printLog("There were no changes. So nothing was saved");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_nothing_to_save));
                    setInlineMessageClass("");
                    setCurrentPageMode(NavigationMode.EXECUTE);

                } else {
                    if (operationStatus.isSuccess()) {
                        printLog("EDIT operation was successful");
                        setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                        RateChangeProcessControlDTOStatus rateChangeProcessControlStatus =
                            RateChangeProcessControlProvider.INSTANCE.checkForProcessID(currentDTO.getProcessID());

                        setCurrentPageMode(NavigationMode.EXECUTE);
                        setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
                                              rateChangeProcessControlStatus.getDTO());

                        clearPageFlowScopeCache();

                    } else {
                        printLog("EDIT operation failed");

                        String errorMessage = "";

                        switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                        case RATE_CHANGE_PROCESS_CONTROL_UPDATE:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_rate_change_process_control_update);
                            break;

                        default:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                            break;

                        }

                        setInlineMessageText(errorMessage);
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);

                    }
                }

            } else if (currentPageMode.isExecuteMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EXECUTE Mode
                printLog("EXECUTE Mode");

                RateChangeProcessControlDTO currentDTO =
                    getRateChangeProcessControlDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.executeRateChangeProcessControl(currentDTO);
                OperationStatus webServiceStatus =
                    WebServiceHelper.callWebService(currentDTO.getProcessID());

                if (operationStatus.isSuccess() &&
                    webServiceStatus.isSuccess()) {
                    // Move on to the next page
                    // Reuse the ERROR_TITLE and ERROR_MESSAGE variables

                    clearPageFlowScopeCache();

                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                          TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.string_title_execute_operation_successful));
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.string_message_execute_operation_successful,
                                                                                           currentDTO.getRateChangeReference()));

                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.AfterEditRateChangeProcessProperties.name());

                } else if (!operationStatus.isSuccess()) {
                    printLog("EXECUTE operation failed during the saving of the DTO");

                    String errorMessage = "";

                    switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                    case RATE_CHANGE_PROCESS_CONTROL_UPDATE:
                        errorMessage =
                                TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_rate_change_process_control_update);
                        break;

                    default:
                        errorMessage =
                                TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                        break;

                    }

                    setInlineMessageText(errorMessage);
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);

                } else {
                    printLog("EXECUTE operation failed during the Web Service Call");

                    String extraInfo =
                        webServiceStatus.getException().getMessage();

                    printLog(extraInfo);

                    String errorMessage =
                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_rate_change_process_control_execute,
                                                             extraInfo);

                    setInlineMessageText(errorMessage);
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
    // HELPER METHODS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public String getDisplayStepExecutionStatus() {
        return ADFUIHelper.getDisplayableStepExecutionStatus(getRateChangeProcessControlDTO().getStepExecStatus());
    }

    public String getDisplayProcessStep() {
        return ADFUIHelper.getDisplayableProcessStep(getRateChangeProcessControlDTO().getProcessStep());
    }

    public Date getMinimumEffectiveDate() {
        return SQLDateUtil.getTodaysDate();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

}
