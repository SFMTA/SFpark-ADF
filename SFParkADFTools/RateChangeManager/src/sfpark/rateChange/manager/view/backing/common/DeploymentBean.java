package sfpark.rateChange.manager.view.backing.common;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.helper.dto.RateChangeProcessControlDTOStatus;
import sfpark.adf.tools.model.provider.RateChangeProcessControlProvider;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.RequestParameter;

import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.ParameterKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.application.value.OperationValue;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;

public class DeploymentBean extends BaseBean {

    private static final String CLASSNAME = DeploymentBean.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RichRegion ContentRichRegion;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public DeploymentBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS EXTRA

    public RegionModel getContentRegionModel() {
        RegionModel model = new RegionModel() {
            public String getViewId(FacesContext facesContext) {

                decideCurrentNavFlow();

                String currentNav =
                    (String)getSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey());

                if (currentNav == null) {
                    currentNav = NavigationFlow.ERROR.name();
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         currentNav);
                }

                NavigationFlow currentNavFlow =
                    NavigationFlow.valueOf(currentNav);

                return currentNavFlow.getFileName();
            }

            @Override
            public void processBeginRegion(FacesContext facesContext,
                                           RegionSite regionSite) {
                // RegionModel throws UnsupportedOperationException here, so
                // we need to override it.
            }

            @Override
            public void processEndRegion(FacesContext facesContext,
                                         RegionSite regionSite) {
                // RegionModel throws UnsupportedOperationException here, so
                // we need to override it.
            }

        };

        return model;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    public void setContentRichRegion(RichRegion ContentRichRegion) {
        this.ContentRichRegion = ContentRichRegion;
    }

    public RichRegion getContentRichRegion() {
        return ContentRichRegion;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void decideCurrentNavFlow() {
        LOGGER.entering(CLASSNAME, "decideCurrentNavFlow");

        HttpServletRequest request = getHttpRequest();
        String httpMethod = request.getMethod();
        String ADFCtrlCurrent = getCurrentADFControl();
        String ADFCtrlFromURL =
            getRequestParameterValue(RequestParameter.ADF_CTRL_STATE.getKey());

        if ( // HTTP Method is GET
            httpMethod.equalsIgnoreCase("GET") &&

            // It is a new Faces Context Control State
            !StringUtil.areEqual(ADFCtrlCurrent, ADFCtrlFromURL)) {

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // NEW Request encountered
            // Start all processes from scratch

            // Clear all cache
            clearAllCache();

            // Set the ADF Control Value
            setCurrentADFControl(ADFCtrlFromURL);

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // Retrieve the parameters

            String operationStr =
                getRequestParameterValue(ParameterKey.OPERATION.getKey());
            String processID =
                getRequestParameterValue(ParameterKey.PROCESS_ID.getKey());

            // Interpret NULL calendar ID as ZERO
            if (StringUtil.isBlank(processID)) {
                processID = "0";
            }

            // Convert operation String into OperationValue
            OperationValue operation = OperationValue.extract(operationStr);

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++

            if ( // VALID Parameters
                !operation.isUnknown() && StringUtil.isDigitsONLY(processID)) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++

                RateChangeProcessControlDTOStatus rateChangeProcessControl =
                    RateChangeProcessControlProvider.INSTANCE.checkForProcessID(processID);

                if ( // Rate Change Process Control EXISTS
                    rateChangeProcessControl.existsDTO()) {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++

                    RateChangeProcessControlDTO rateChangeProcessControlDTO =
                        rateChangeProcessControl.getDTO();

                    if ( // EDIT Mode
                        operation.isEdit()) {
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++

                        if ( // EDITABLE
                            rateChangeProcessControlDTO.isEditable()) {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            LOGGER.debug("EDIT Mode");
                            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
                                                  rateChangeProcessControlDTO);
                            setCurrentPageMode(NavigationMode.EDIT);
                            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                 NavigationFlow.EditRateChangeProcess.name());
                        } else {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            LOGGER.warning("Unsupported Operation - Can not edit, already completed");
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_unsupported_operation));
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_unsupported_rate_change_process_edit_operation,
                                                                                       processID));
                            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                 NavigationFlow.ERROR.name());
                        }

                    } else if ( // DELETE Mode
                        operation.isDelete()) {
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++

                        if ( // DELETABLE
                            rateChangeProcessControlDTO.isDeletable()) {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            LOGGER.debug("DELETE Mode");
                            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
                                                  rateChangeProcessControlDTO);
                            setCurrentPageMode(NavigationMode.DELETE);
                            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                 NavigationFlow.DeleteRateChangeProcess.name());
                        } else {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            LOGGER.warning("Unsupported Operation - Can not delete");
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_unsupported_operation));
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_unsupported_rate_change_process_delete_operation,
                                                                                       processID));
                            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                 NavigationFlow.ERROR.name());
                        }

                    } else {
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        LOGGER.warning("Unsupported Operation");
                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                              TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_unsupported_operation));
                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                              TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_unsupported_operation,
                                                                                   operationStr));
                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                             NavigationFlow.ERROR.name());
                    }

                } else {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    LOGGER.warning("Rate Change Reference does not exist");
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_nonexistent_resource));
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_nonexistent_process_id_resource,
                                                                               processID));
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.ERROR.name());
                }

            } else {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                LOGGER.warning("Invalid Parameters");
                setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_invalid_parameters));

                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_invalid_rate_change_manager_deploy_parameters,
                                                                           operationStr,
                                                                           processID));
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.ERROR.name());
            }

        } else if ( // HTTP Method is POST
            httpMethod.equalsIgnoreCase("POST")) {

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++

            setCurrentADFControl(ADFCtrlFromURL);
        }

        LOGGER.exiting(CLASSNAME, "decideCurrentNavFlow");
    }

    private void clearAllCache() {

        for (PageFlowScopeKey pfsKey : PageFlowScopeKey.values()) {
            removePageFlowScopeValue(pfsKey.getKey());
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CURRENT ADF CONTROL MECHANISM

    private String currentADFControl = "";

    private void setCurrentADFControl(String currentADFControl) {
        this.currentADFControl = currentADFControl;
    }

    private String getCurrentADFControl() {
        return currentADFControl;
    }
}
