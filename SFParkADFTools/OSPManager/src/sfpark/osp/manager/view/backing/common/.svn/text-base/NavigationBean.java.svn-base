package sfpark.osp.manager.view.backing.common;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.model.data.dto.ospInventory.OSPInventoryDTO;
import sfpark.adf.tools.model.helper.dto.OSPInventoryDTOStatus;
import sfpark.adf.tools.model.provider.OSPInventoryProvider;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.RequestParameter;
import sfpark.adf.tools.utilities.generic.GeoSpaceUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.osp.manager.application.key.PageFlowScopeKey;
import sfpark.osp.manager.application.key.ParameterKey;
import sfpark.osp.manager.application.key.SessionScopeKey;
import sfpark.osp.manager.view.backing.BaseBean;
import sfpark.osp.manager.view.flow.NavigationFlow;
import sfpark.osp.manager.view.flow.NavigationMode;

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

    private void decideCurrentNavFlow() {
        LOGGER.entering(CLASSNAME, "decideCurrentNavFlow");

        HttpServletRequest request = getHttpRequest();
        String httpMethod = request.getMethod();
        String ADFCtrlCurrent = getCurrentADFControl();
        String ADFCtrlFromURL =
            getRequestParameterValue(RequestParameter.ADF_CTRL_STATE.getKey());

        if (
            // If HTTP Method is GET
            httpMethod.equalsIgnoreCase("GET") &&

            // It is a new Faces Context Control State
            !StringUtil.areEqual(ADFCtrlCurrent, ADFCtrlFromURL)) {

            // A new request has been received. So start all processes from scratch

            // Clear all cache
            clearAllCache();

            // Set the ADF Control Value
            setCurrentADFControl(ADFCtrlFromURL);

            String ospID =
                getRequestParameterValue(ParameterKey.OSP_ID.getKey());

            String longitude =
                getRequestParameterValue(ParameterKey.LONGITUDE.getKey());
            String latitude =
                getRequestParameterValue(ParameterKey.LATITUDE.getKey());

            if (StringUtil.isDigitsONLY(ospID) &&
                GeoSpaceUtil.isLongitudeValid(longitude) &&
                GeoSpaceUtil.isLatitudeValid(latitude)) {

                OSPInventoryDTOStatus ospStatus =
                    OSPInventoryProvider.INSTANCE.checkForOSP(ospID);

                if (ospStatus.existsDTO()) {

                    OSPInventoryDTO ospInventoryDTO = ospStatus.getDTO();

                    ospInventoryDTO.setMainEntranceLatLong(latitude,
                                                           longitude);

                    setCurrentPageMode(NavigationMode.EDIT);

                    setPageFlowScopeValue(PageFlowScopeKey.OSP_INVENTORY_DTO.getKey(),
                                          ospInventoryDTO);
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.EditOSPInventory.name());

                } else {
                    LOGGER.debug("OSP does not exist");
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          "OSP does not exist");
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.ERROR.name());
                }

            } else {
                LOGGER.debug("Invalid Parameters");
                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_osp_manager_parameters,
                                                                           ospID,
                                                                           longitude,
                                                                           latitude));
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.ERROR.name());
            }

        } else if (
            // Http Method is POST
            httpMethod.equalsIgnoreCase("POST")) {
            setCurrentADFControl(ADFCtrlFromURL);
        }

        LOGGER.exiting(CLASSNAME, "decideCurrentNavFlow");
    }

    private void clearAllCache() {

        for (PageFlowScopeKey pfsKey : PageFlowScopeKey.values()) {
            removePageFlowScopeValue(pfsKey.getKey());
        }
    }
}
