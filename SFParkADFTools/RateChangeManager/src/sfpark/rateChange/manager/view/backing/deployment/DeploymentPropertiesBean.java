package sfpark.rateChange.manager.view.backing.deployment;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.data.helper.RateChangeProcessTimeLimitOption;
import sfpark.adf.tools.model.exception.DTOUpdateException;
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dto.RateChangeProcessControlDTOStatus;
import sfpark.adf.tools.model.provider.RateChangeProcessControlProvider;

import sfpark.adf.tools.model.provider.RateProcessStepProvider;
import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.helper.ADFUIHelper;
import sfpark.rateChange.manager.view.helper.ODIWebServiceHelper;
import sfpark.rateChange.manager.view.helper.WebServiceHelper;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- --------------------------------------------------------
 * 20111123-01 Mark Piller - Oracle Consulting  Added logic to process ODI Web Services
 * 20111129-01 Mark Piller - Oracle Consulting  Added logic to process according to value in PROCESS_STEP
 * 20120320-01 Mark Piller - Oracle Consulting  Change logger debug level to info level
 */
public class DeploymentPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                  RequestScopeBeanInterface {

    private static final String CLASSNAME = DeploymentPropertiesBean.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);
    private static final String APPLY_RATE = "APPLY_RATE";
    private static final String RECONCILE = "RECONCILE";
    private static final String UPDT_EFF_DT = "UPDT_EFF_DT";

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
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(), DTO);
        }

        return DTO;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALIDATORS

    public void effectiveDateValidator(FacesContext facesContext, UIComponent uiComponent,
                                       Object object) {

        if (getRateChangeProcessControlDTO().isValidateEffectiveFromDate()) {

            int numOfDays = 16;
            Date initialDate = getRateChangeProcessControlDTO().getInitialEffectiveFromDate();

            Date begRange = SQLDateUtil.getPreviousDateFor(initialDate, numOfDays);
            Date endRange = SQLDateUtil.getNextDateFor(initialDate, numOfDays);

            Date selectedDate = (Date)object;

            if (selectedDate.after(begRange) && selectedDate.before(endRange)) {
                // Do nothing. Everything is fine

            } else {
                // Display warning information

                FacesMessage facesMessage =
                    new FacesMessage(FacesMessage.SEVERITY_WARN, TranslationUtil.getCommonBundleString(CommonBundleKey.warning_title_finalised_eff_date),
                                     TranslationUtil.getCommonBundleString(CommonBundleKey.warning_message_finalised_eff_date));

                facesContext.addMessage(uiComponent.getClientId(facesContext), facesMessage);
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
        LOGGER.entering(CLASSNAME, "saveButtonHandler");

        boolean allValid = true;
        // 20111123-01 added flags to control flow
        boolean meterPricingRequestWebServiceSuccessful = false;
        boolean odiWebServiceSuccessful = false;
        boolean databaseUpdateSuccessful = false;

        boolean odiWebServicesWereExecuted = false;
        boolean meterPricingWebServiceWasExecuted = false;

        boolean noErrorsOccurredInProcess = true;

        String processID = null;
        String processStep = null;
        String odiWebServiceMessage = null;

        RateProcessStepProvider rateProcessStepProvider =
            new RateProcessStepProvider(); // this is used to determine the current Process Step
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

        // create a variable for the Meter Pricing Web Service results
        OperationStatus webServiceStatus = null;

        if (allValid) {
            printLog("All entries are Valid. Proceed");

            if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                printLog("EDIT Mode");

                RateChangeProcessControlDTO currentDTO = getRateChangeProcessControlDTO();

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

                        case DTO_UPDATE:
                            {
                                String tableName =
                                    ((DTOUpdateException)operationStatus.getException()).getTableName();

                                if (StringUtil.areEqual(tableName,
                                                        RateChangeProcessControlDTO.getDatabaseTableName())) {
                                    errorMessage =
                                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_rate_change_process_control_update);
                                } else {
                                    errorMessage =
                                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                                }
                            }
                            break;

                        default:
                            {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                            }
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

                RateChangeProcessControlDTO currentDTO = getRateChangeProcessControlDTO();

                // 20111123-01 added to pass on to ApplyRate Web Service
                processID = currentDTO.getProcessID();

                // Set the STEP_START_FLAG to 'Y' in table RATE_CHG_PROCESS_CONTROL
                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.executeRateChangeProcessControl(currentDTO);

                if (operationStatus.isSuccess()) {
                    databaseUpdateSuccessful = true;

                    // get the current PROCESS_STEP value in table RATE_CHG_PROCESS_CONTROL
                    processStep = rateProcessStepProvider.checkForProcessID(processID);
                    LOGGER.info("Process step is: " + processStep); // 20120320-01


                    // evaluate the Process Step to determine which web service is called
                    int iProcessStep = Integer.parseInt(processStep);
                    switch (iProcessStep) {
                    case 10:
                        odiWebServicesWereExecuted = true;
                        OperationStatus applyRateWebServiceStatus = null;
                        LOGGER.info("Calling ApplyRateWebService() for PROCESS_STEP = " + processStep); // 20120320-01
                        applyRateWebServiceStatus =
                                ODIWebServiceHelper.callODIWebService(processID, APPLY_RATE);
                        LOGGER.info("Completed call to ApplyRateWebService()"); // 20120320-01
                        if (applyRateWebServiceStatus.isSuccess()) {
                            odiWebServiceSuccessful = true;
                        } else {
                            odiWebServiceMessage =
                                    applyRateWebServiceStatus.getException().getMessage();
                        }
                        break;

                    case 60:
                        odiWebServicesWereExecuted = true;
                        OperationStatus reconcileRateWebServiceStatus = null;
                        LOGGER.info("Calling ReconcileRateWebService() for PROCESS_STEP = " + processStep); // 20120320-01
                        reconcileRateWebServiceStatus =
                                ODIWebServiceHelper.callODIWebService(processID, RECONCILE);
                        LOGGER.info("Completed call to ReconcileRateWebService()"); // 20120320-01
                        if (reconcileRateWebServiceStatus.isSuccess()) {
                            odiWebServiceSuccessful = true;
                        } else {
                            odiWebServiceMessage =
                                    reconcileRateWebServiceStatus.getException().getMessage();
                        }
                        break;

                    case 70:
                        odiWebServicesWereExecuted = true;
                        OperationStatus updtEffDtWebServiceStatus = null;
                        LOGGER.info("Calling UpdtEffDtWebService() for PROCESS_STEP = " + processStep);  // 20120320-01
                        updtEffDtWebServiceStatus =
                                ODIWebServiceHelper.callODIWebService(processID, UPDT_EFF_DT);
                        LOGGER.info("Completed call to UpdtEffDtWebService()");  // 20120320-01
                        if (updtEffDtWebServiceStatus.isSuccess()) {
                            odiWebServiceSuccessful = true;
                        } else {
                            odiWebServiceMessage =
                                    updtEffDtWebServiceStatus.getException().getMessage();
                        }
                        break;

                    case 20:
                    case 30:
                    case 40:
                    case 50:
                        meterPricingWebServiceWasExecuted = true;
                        LOGGER.info("Calling Meter Pricing Web Service for PROCESS_STEP = " + processStep);  // 20120320-01
                        webServiceStatus =
                                WebServiceHelper.callWebService(currentDTO.getProcessID());
                        LOGGER.info("Completed Meter Pricing Web Service");  // 20120320-01

                        // test success of MeterPricing Web Service
                        if (webServiceStatus.isSuccess()) {
                            LOGGER.info("Meter Pricing Web Service was successful - setting flag"); // 20120320-01
                            meterPricingRequestWebServiceSuccessful = true;
                        }
                        break;
                    } // end switch statement

                } // end if operationStatus.isSuccess() -- database update success test

                // 20111123-01
                // Evaluate executed processes and control flow according to success/failure
                LOGGER.info("Evaluating database update success"); // 20120320-01
                if (!databaseUpdateSuccessful) {
                    noErrorsOccurredInProcess = false;
                    LOGGER.info("DB Operation failed... display the error"); // 20120320-01
                    // database operation failed
                    printLog("EXECUTE operation failed during the saving of the DTO");

                    String errorMessage = "";

                    switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                    case DTO_UPDATE:
                        {
                            String tableName =
                                ((DTOUpdateException)operationStatus.getException()).getTableName();

                            if (StringUtil.areEqual(tableName,
                                                    RateChangeProcessControlDTO.getDatabaseTableName())) {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_rate_change_process_control_update);
                            } else {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                            }
                        }
                        break;

                    default:
                        {
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                        }
                        break;

                    }

                    setInlineMessageText(errorMessage);
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                } else {
                  LOGGER.info("Database update was successful - do not display errors"); // 20120320-01
                } // end test for database update execution

                LOGGER.info("Evaluating Meter Pricing WS success"); // 20120320-01
                if (meterPricingWebServiceWasExecuted && noErrorsOccurredInProcess) {
                    LOGGER.info("Meter Pricing WS was executed"); // 20120320-01
                    // see if Meter Pricing Web Service executed successfully
                    if (!meterPricingRequestWebServiceSuccessful) {
                        noErrorsOccurredInProcess = false;
                        LOGGER.info("Meter Pricing Request WS failed... display the error"); // 20120320-01
                        // Meter Pricing Web Service failed
                        printLog("EXECUTE operation failed during the Meter Pricing Web Service Call");
                        String extraInfo = webServiceStatus.getException().getMessage();
                        printLog(extraInfo);

                        String errorMessage =
                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_rate_change_process_control_execute,
                                                                 extraInfo);

                        setInlineMessageText(errorMessage);
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    } else {
                      LOGGER.info("Meter Pricing WS was successful - do not display errors"); // 20120320-01
                    }
                } // end if (meterPricingWebServiceWasExecuted && noErrorsOccurredInProcess)

                LOGGER.info("Evaluating ODI WS success"); // 20120320-01
                if (odiWebServicesWereExecuted && noErrorsOccurredInProcess) {
                    LOGGER.info("ODI WS was executed"); // 20120320-01
                    if (!odiWebServiceSuccessful) {
                        noErrorsOccurredInProcess = false;
                        LOGGER.info("ODI Web Service failed... display the error");  // 20120320-01
                        // ODI Web Service Failed
                        // 20111123-01
                        printLog("EXECUTE operation failed during the ODI Web Service Call");
                        String extraInfo = odiWebServiceMessage;
                        printLog(extraInfo);

                        String errorMessage =
                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_rate_change_process_control_execute,
                                                                 extraInfo);

                        setInlineMessageText(errorMessage);
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    } else {
                      LOGGER.info("ODI WS was successful - do not display errors"); // 20120320-01
                    }
                } // if (odiWebServicesWereExecuted && noErrorsOccurredInProcess)


                LOGGER.debug("No Errors occurred in the database or WS updates - navigate to the next screen");
                if (noErrorsOccurredInProcess) {
                    LOGGER.debug("Database update, Meter Pricing WS and ODI WS successful - navigate page flow");

                    // Move on to the next page
                    // Reuse the ERROR_TITLE and ERROR_MESSAGE variables
                    clearPageFlowScopeCache();

                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                          TranslationUtil.getCommonBundleString(CommonBundleKey.string_title_execute_operation_successful));
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          TranslationUtil.getCommonBundleString(CommonBundleKey.string_message_execute_operation_successful,
                                                                                currentDTO.getRateChangeReference()));

                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.AfterEditRateChangeProcessProperties.name());

                } // end if (noErrorsOccurredInProcess)

            } // end else Execute Mode

        } else {
            LOGGER.debug("Setting In Line Message Class to display the error");
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }
    }

    public void cancelButtonHandler(ActionEvent event) {
        // Do nothing
    }

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
}
