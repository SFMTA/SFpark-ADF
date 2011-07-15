package sfpark.event.manager.view.backing.common;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.utilities.constants.RequestParameter;
import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.event.manager.application.key.PageFlowScopeKey;
import sfpark.event.manager.application.key.ParameterKey;
import sfpark.event.manager.application.key.SessionScopeKey;
import sfpark.event.manager.view.backing.BaseBean;
import sfpark.event.manager.view.flow.NavigationFlow;

public class NavigationBean extends BaseBean {

    private static final String CLASSNAME = NavigationBean.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static int testCounter = 0;

    private RichRegion ContentRichRegion;

    public static NavigationBean getInstance() {
        return (NavigationBean)getCurrentInstanceFor("navigationBean");
    }

    public NavigationFlow getCurrentNavigationFlow() {
        String currentNav =
            (String)getSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey());

        if (currentNav == null) {
            currentNav = NavigationFlow.ERROR.name();
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 currentNav);
        }

        return NavigationFlow.valueOf(currentNav);
    }

    public void refreshContentRegion() {
        addPartialTarget(getContentRichRegion());
    }

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
    // UI CONNECTIONS

    public void setContentRichRegion(RichRegion ContentRichRegion) {
        this.ContentRichRegion = ContentRichRegion;
    }

    public RichRegion getContentRichRegion() {
        return ContentRichRegion;
    }

    public RegionModel getContentRegionModel() {
        RegionModel model = new RegionModel() {
            public String getViewId(FacesContext facesContext) {

                decideCurrentNavFlow();

                return getCurrentNavigationFlow().getFileName();
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
    // HELPER METHODS

    private void decideCurrentNavFlow() {
        LOGGER.entering(CLASSNAME, "decideCurrentNavFlow");

        HttpServletRequest request = getHttpRequest();
        String httpMethod = request.getMethod();
        String ADFCtrlCurrent = getCurrentADFControl();
        String ADFCtrlFromURL =
            getRequestParameterValue(RequestParameter.ADF_CTRL_STATE.getKey());

        LOGGER.debug("Visited = " + (testCounter++) + "\t" + httpMethod);

        if (
            // If HTTP Method is GET
            httpMethod.equalsIgnoreCase("GET") &&

            // It is a new Faces Context Control State
            !StringUtil.areEqual(ADFCtrlCurrent, ADFCtrlFromURL)) {

            // A new request has been received. So start all processes from scratch

            setCurrentADFControl(ADFCtrlFromURL);

            /*
            OSPManagementBean ospManagementBean =
                OSPManagementBean.getInstance();

            if (ospManagementBean != null) {
                ospManagementBean.clearPageFlowScopeCache();
            }

            */

            String operation =
                getRequestParameterValue(ParameterKey.OPERATION.getKey());

            if (operation != null) {
                if (operation.equals("eventCalendar")) {
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.LIST_EVENT_CALENDAR.name());

                } else {
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          "Not yet implemented");
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.ERROR.name());
                }
            } else {
                LOGGER.debug("Invalid Parameters");
                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                      "Invalid Parameters");
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.ERROR.name());
            }

        } else if (
            // Http Method is POST
            httpMethod.equalsIgnoreCase("POST")) {

            setCurrentADFControl(ADFCtrlFromURL);

        } else if ( // If HTTP Method is GET
            httpMethod.equalsIgnoreCase("GET") &&

            // It is the same Faces Context Control State
            StringUtil.areEqual(ADFCtrlCurrent, ADFCtrlFromURL)) {

            setCurrentADFControl(ADFCtrlFromURL);

        }

        LOGGER.exiting(CLASSNAME, "decideCurrentNavFlow");
    }
}
