package sfpark.admin.console.view.backing.common;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.RequestParameter;

import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.ParameterKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.application.value.OperationValue;
import sfpark.admin.console.view.backing.BaseBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;

public class NavigationBean extends BaseBean {

    private static final String CLASSNAME = NavigationBean.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RichRegion ContentRichRegion;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public NavigationBean() {
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

            // Convert operation String into OperationValue
            OperationValue operation = OperationValue.extract(operationStr);

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++

            if ( // VALID Parameters
                !operation.isUnknown()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++

                if ( // ALLOWED_VALUES
                    operation.isAllowedValues()) {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    LOGGER.debug("ALLOWED_VALUES");

                    setCurrentPageMode(NavigationMode.READ_ONLY);
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.ALLOWED_VALUES_LIST.name());

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
                LOGGER.warning("Invalid Parameters");

                setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_invalid_parameters));

                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_invalid_admin_console_parameters,
                                                                           StringUtil.convertListToString(OperationValue.getValidValues())));
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

    protected void setCurrentADFControl(String currentADFControl) {
        this.currentADFControl = currentADFControl;
    }

    protected String getCurrentADFControl() {
        return currentADFControl;
    }
}
