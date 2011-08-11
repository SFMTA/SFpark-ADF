package sfpark.priceChange.manager.view.backing.common;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.RequestParameter;

import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.adf.tools.view.backing.util.ADFUtil;
import sfpark.adf.tools.view.backing.util.dialog.DialogBeanCallback;

import sfpark.priceChange.manager.application.key.PageFlowScopeKey;
import sfpark.priceChange.manager.application.key.SessionScopeKey;
import sfpark.priceChange.manager.view.backing.BaseBean;
import sfpark.priceChange.manager.view.backing.util.dialog.DialogBean;
import sfpark.priceChange.manager.view.flow.NavigationFlow;

public class NavigationBean extends BaseBean {

    private static final String CLASSNAME = NavigationBean.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RichRegion ContentRichRegion;
    private RichPanelGroupLayout NavigationPanel;

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
    // ACCESSORS

    public static NavigationBean getInstance() {
        return (NavigationBean)ADFUtil.getCurrentInstanceFor("navigationBean");
    }

    public void refreshNavigationPanel() {
        addPartialTarget(getNavigationPanel());
    }

    public void refreshContentRichRegion() {
        addPartialTarget(getContentRichRegion());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    public boolean isRenderApprovalProcessLink() {
        return true;
    }

    public boolean isRenderDeploymentProcessLink() {
        return true;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    public boolean isDisableApprovalProcessLink() {
        return getCurrentNavFlow().getSection().isPriceApprovalProcess();
    }

    public boolean isDisableDeploymentProcessLink() {
        return getCurrentNavFlow().getSection().isPriceDeploymentProcess();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void navigationCommandLinkClickHandler(ActionEvent event) {
        String ID = event.getComponent().getId();

        if (ID.equalsIgnoreCase("approvalProcess")) {
            performClickAction(NavigationFlow.HOME); // TODO Update to Approval Process

        } else if (ID.equalsIgnoreCase("deploymentProcess")) {
            performClickAction(NavigationFlow.DEPLOYMENT_PROCESS);

        } else {
            // Default case for all other clicks
            performClickAction(NavigationFlow.HOME);
        }

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
                    currentNav = NavigationFlow.HOME.name();
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         currentNav);
                }

                NavigationFlow currentNavFlow =
                    NavigationFlow.valueOf(currentNav);

                clearNotRequiredCache(currentNavFlow);

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

    public void setNavigationPanel(RichPanelGroupLayout NavigationPanel) {
        this.NavigationPanel = NavigationPanel;
    }

    public RichPanelGroupLayout getNavigationPanel() {
        return NavigationPanel;
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

            // Set the Home Page
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.HOME.name());

        } else if ( // HTTP Method is POST
            httpMethod.equalsIgnoreCase("POST")) {

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++

            setCurrentADFControl(ADFCtrlFromURL);
        }

        LOGGER.exiting(CLASSNAME, "decideCurrentNavFlow");
    }

    /*
        private void setProperPageMode() {

            if (LoggedInUserUtil.canReadOnlyParkingSpace()) {
                setCurrentPageMode(NavigationMode.READ_ONLY);
            } else {
                setCurrentPageMode(NavigationMode.EDIT);
            }

        }
     */

    private void clearAllCache() {
        for (PageFlowScopeKey pfsKey : PageFlowScopeKey.values()) {
            removePageFlowScopeValue(pfsKey.getKey());
        }
    }

    private void clearNotRequiredCache(NavigationFlow currentNavFlow) {

        // TODO Add more Bean clear methods as needed

        /*
        if (!currentNavFlow.getSection().isManageApplication()) {
            if (HiveApplicationPropertiesBean.getInstance() != null) {
                HiveApplicationPropertiesBean.getInstance().clearPageFlowScopeCache();
            }
        }

        if (!currentNavFlow.getSection().isManageUsers()) {
            if (UserListBean.getInstance() != null) {
                UserListBean.getInstance().clearPageFlowScopeCache();
            }

            if (UserPropertiesBean.getInstance() != null) {
                UserPropertiesBean.getInstance().clearPageFlowScopeCache();
            }

            if (CreateUserBean.getInstance() != null) {
                CreateUserBean.getInstance().clearPageFlowScopeCache();
            }
        }
         */

    }

    private NavigationFlow getCurrentNavFlow() {
        NavigationFlow currentNavFlow = NavigationFlow.HOME;

        String currentNav =
            (String)getSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey());

        if (currentNav != null) {
            currentNavFlow = NavigationFlow.valueOf(currentNav);
        }

        return currentNavFlow;
    }

    private void performClickAction(final NavigationFlow navigationFlow) {

        DialogBeanCallback callback = new DialogBeanCallback() {
            public void okButtonHandler() {
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     navigationFlow.name());

                setCurrentPageSaved(Boolean.TRUE);

                refreshContentRichRegion();
                refreshNavigationPanel();
            }

            public void cancelButtonHandler() {
                // Do nothing
            }
        };

        if (!isCurrentPageSaved()) {
            DialogBean.getInstance().ConfirmationDialog(TranslationUtil.getCommonBundleString(CommonBundleKey.feedback_unsaved_data_title),
                                                        TranslationUtil.getCommonBundleString(CommonBundleKey.feedback_unsaved_data_text),
                                                        callback);

        } else {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 navigationFlow.name());

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

/*
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

 */