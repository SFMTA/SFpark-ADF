package sfpark.asset.manager.view.backing.common;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.model.helper.dO.ParkingSpaceGroupsDOStatus;
import sfpark.adf.tools.model.provider.ParkingSpaceGroupsProvider;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.RequestParameter;

import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.asset.manager.application.key.PageFlowScopeKey;
import sfpark.asset.manager.application.key.ParameterKey;
import sfpark.asset.manager.application.key.SessionScopeKey;
import sfpark.asset.manager.application.util.LoggedInUserUtil;
import sfpark.asset.manager.view.backing.BaseBean;
import sfpark.asset.manager.view.flow.NavigationFlow;
import sfpark.asset.manager.view.flow.NavigationMode;
import sfpark.asset.manager.view.flow.NavigationType;

public class BulkNavigationBean extends BaseBean {

    private static final String CLASSNAME = BulkNavigationBean.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RichRegion ContentRichRegion;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public BulkNavigationBean() {
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

                decideCurrentBulkNavFlow();

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
    // HELPER METHODS

    private void decideCurrentBulkNavFlow() {
        LOGGER.entering(CLASSNAME, "decideCurrentBulkNavFlow");

        HttpServletRequest request = getHttpRequest();
        String httpMethod = request.getMethod();
        String ADFCtrlCurrent = getCurrentADFControl();
        String ADFCtrlFromURL =
            getRequestParameterValue(RequestParameter.ADF_CTRL_STATE.getKey());

        if (
            // HTTP Method is GET
            httpMethod.equalsIgnoreCase("GET") &&

            // It is a new Faces Context Control State
            !StringUtil.areEqual(ADFCtrlCurrent, ADFCtrlFromURL)) {

            // A new request has been received. So start all processes from scratch

            // Clear all cache
            clearAllCache();

            // Set the ADF Control Value
            setCurrentADFControl(ADFCtrlFromURL);

            String parkingSpaceGroupID =
                getRequestParameterValue(ParameterKey.PARKING_SPACE_GROUP_ID.getKey());

            if (StringUtil.isDigitsONLY(parkingSpaceGroupID)) {

                ParkingSpaceGroupsDOStatus parkingSpaceGroupStatus =
                    ParkingSpaceGroupsProvider.INSTANCE.checkForParkingSpaceGroupID(parkingSpaceGroupID);

                if (parkingSpaceGroupStatus.existsDO()) {

                    if (!LoggedInUserUtil.canReadOnlyParkingSpace()) {
                        setCurrentPageMode(NavigationMode.EDIT);
                        setCurrentPageType(NavigationType.BULK);

                        setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_GROUPS_DO.getKey(),
                                              parkingSpaceGroupStatus.getDO());
                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                             NavigationFlow.BulkEditParkingSpace.name());

                    } else {
                        LOGGER.warning("Insufficient Privileges");
                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                              TranslationUtil.getErrorBundleString(ErrorBundleKey.error_insufficient_privileges));
                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                             NavigationFlow.ERROR.name());
                    }

                } else {
                    LOGGER.warning("Parking space group does not exist");
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_exists_parking_space_group,
                                                                               parkingSpaceGroupID));
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.ERROR.name());
                }

            } else {
                LOGGER.debug("Invalid Parameters");
                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_asset_manager_bulk_parameters,
                                                                           parkingSpaceGroupID));
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.ERROR.name());
            }

        } else if (
            // Http Method is POST
            httpMethod.equalsIgnoreCase("POST")) {

            setCurrentADFControl(ADFCtrlFromURL);
        }

        LOGGER.exiting(CLASSNAME, "decideCurrentBulkNavFlow");
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
