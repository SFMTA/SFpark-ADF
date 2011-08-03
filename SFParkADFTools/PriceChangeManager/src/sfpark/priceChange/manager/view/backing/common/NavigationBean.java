package sfpark.priceChange.manager.view.backing.common;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.helper.SignedInUser;

import sfpark.priceChange.manager.application.key.PageFlowScopeKey;
import sfpark.priceChange.manager.application.key.SessionScopeKey;
import sfpark.priceChange.manager.view.backing.BaseBean;
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
        return (NavigationBean)getCurrentInstanceFor("navigationBean");
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
        return SignedInUser.canApprovePriceChange();
    }

    public boolean isRenderDeploymentProcessLink() {
        return SignedInUser.canDeployPriceChange();
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

    public RegionModel getContentRegionModel() {
        RegionModel model = new RegionModel() {
            public String getViewId(FacesContext facesContext) {
                // TODO
                // decideCurrentNavFlow();

                String currentNav =
                    (String)getSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey());

                if (currentNav == null) {
                    currentNav = NavigationFlow.HOME.name();
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         currentNav);
                }

                NavigationFlow currentNavFlow =
                    NavigationFlow.valueOf(currentNav);

                // TODO
                // clearNotRequiredCache(currentNavFlow);

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

    /*

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
                // TODO: How to clear the selection/sort state of the tables

                MeterSpaceManagementBean meterSpaceManagementBean =
                    MeterSpaceManagementBean.getInstance();

                if (meterSpaceManagementBean != null) {
                    meterSpaceManagementBean.refreshTable();
                }

                // +++++++++++++++++++++++++++++++++++++++++++++++++++++
                // +++++++++++++++++++++++++++++++++++++++++++++++++++++
                // +++++++++++++++++++++++++++++++++++++++++++++++++++++
                // Retrieve the parameters

                String parkingSpaceID =
                    getRequestParameterValue(ParameterKey.PARKING_SPACE_ID.getKey());
                String longitude =
                    getRequestParameterValue(ParameterKey.LONGITUDE.getKey());
                String latitude =
                    getRequestParameterValue(ParameterKey.LATITUDE.getKey());

                // Interpret NULL parking space ID as ZERO
                if (StringUtil.isBlank(parkingSpaceID)) {
                    parkingSpaceID = "0";
                }

                // +++++++++++++++++++++++++++++++++++++++++++++++++++++
                // +++++++++++++++++++++++++++++++++++++++++++++++++++++
                // +++++++++++++++++++++++++++++++++++++++++++++++++++++

                if ( // VALID Parameters
                    StringUtil.isDigitsONLY(parkingSpaceID) &&
                    GeoSpaceUtil.isLongitudeValid(longitude) &&
                    GeoSpaceUtil.isLatitudeValid(latitude)) {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_ID.getKey(),
                                          parkingSpaceID);
                    setPageFlowScopeValue(PageFlowScopeKey.LONGITUDE.getKey(),
                                          longitude);
                    setPageFlowScopeValue(PageFlowScopeKey.LATITUDE.getKey(),
                                          latitude);

                    if ( // ADD Mode
                        parkingSpaceID.equals("0")) {
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++

                        if ( // Can ADD
                            LoggedInUserUtil.canAddParkingSpace()) {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            BlockfaceDOStatus blockfaceStatus =
                                BlockfacesProvider.INSTANCE.checkForBlockface(longitude,
                                                                              latitude);

                            if ( // Blockface EXISTS
                                blockfaceStatus.existsDO()) {
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                LOGGER.debug("ADD Mode");
                                setPageFlowScopeValue(PageFlowScopeKey.BLOCKFACE_DO.getKey(),
                                                      blockfaceStatus.getDO());
                                setCurrentPageMode(NavigationMode.ADD);
                                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                     NavigationFlow.AddParkingSpace.name());

                            } else {
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                LOGGER.warning("Blockface does not exist");
                                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_exists_blockface_long_lat,
                                                                                           longitude,
                                                                                           latitude));
                                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                     NavigationFlow.ERROR.name());
                            }

                        } else {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            LOGGER.warning("Insuffient Privileges: User without WRITE access.");
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_insufficient_privileges));
                            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                 NavigationFlow.ERROR.name());
                        }

                    } else { // EDIT Mode
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++

                        if ( // Can EDIT or READ_ONLY
                            LoggedInUserUtil.canEditOrReadOnlyParkingSpace()) {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            ParkingSpaceInventoryDTOStatus parkingSpaceStatus =
                                ParkingSpaceInventoryProvider.INSTANCE.checkForParkingSpace(parkingSpaceID);

                            if ( // Space EXISTS
                                parkingSpaceStatus.existsDTO()) {
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
                                    parkingSpaceStatus.getDTO();

                                if ( // OFF Street
                                    parkingSpaceInventoryDTO.getOnOffStreetType().equalsIgnoreCase("OFF")) {
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    LOGGER.debug("EDIT/READ_ONLY Mode for OFF Street");
                                    setProperPageMode();
                                    parkingSpaceInventoryDTO.setLatLong(latitude,
                                                                        longitude);

                                    setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                                          parkingSpaceInventoryDTO);
                                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                         NavigationFlow.EditParkingSpace.name());

                                } else {
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    BlockfaceDOStatus blockfaceStatus =
                                        BlockfacesProvider.INSTANCE.checkForBlockface(longitude,
                                                                                      latitude);

                                    if ( // Blockface EXISTS
                                        blockfaceStatus.existsDO()) {
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        LOGGER.debug("EDIT/READ_ONLY Mode for ON Street");
                                        setProperPageMode();
                                        parkingSpaceInventoryDTO.setLatLong(latitude,
                                                                            longitude);
                                        parkingSpaceInventoryDTO.setBlockfaceDO(blockfaceStatus.getDO());

                                        setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                                              parkingSpaceInventoryDTO);
                                        setPageFlowScopeValue(PageFlowScopeKey.BLOCKFACE_DO.getKey(),
                                                              blockfaceStatus.getDO());
                                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                             NavigationFlow.EditParkingSpace.name());

                                    } else {
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        LOGGER.warning("Blockface does not exist");
                                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                              TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_exists_blockface_long_lat,
                                                                                                   longitude,
                                                                                                   latitude));
                                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                             NavigationFlow.ERROR.name());
                                    }

                                }

                            } else {
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                LOGGER.warning("Parking space does not exist");
                                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_exists_parking_space,
                                                                                           parkingSpaceID));
                                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                     NavigationFlow.ERROR.name());
                            }

                        } else {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            LOGGER.warning("Insuffient Privileges: User without ANY access.");
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_insufficient_privileges));
                            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                 NavigationFlow.ERROR.name());
                        }

                    }

                } else {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    LOGGER.warning("Invalid Parameters");
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_asset_manager_parameters,
                                                                               parkingSpaceID,
                                                                               longitude,
                                                                               latitude));
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

    private NavigationFlow getCurrentNavFlow() {
        NavigationFlow currentNavFlow = NavigationFlow.HOME;

        String currentNav =
            (String)getSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey());

        if (currentNav != null) {
            currentNavFlow = NavigationFlow.valueOf(currentNav);
        }

        return currentNavFlow;
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
public class NavigationBean extends BaseBean {
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    public RegionModel getNavigationRegionModel() {
        RegionModel model = new RegionModel() {

            public String getViewId(FacesContext facesContext) {
                String currentNav =
                    (String)getSessionScopeValue(SessionScopeKey.NAVIGATION_INFO);

                if (currentNav == null) {
                    currentNav = NavigationFlow.Introduction.name();
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO,
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
                // we need to override it. See bug 6821290.
            }

            @Override
            public void processEndRegion(FacesContext facesContext,
                                         RegionSite regionSite) {
                // RegionModel throws UnsupportedOperationException here, so
                // we need to override it. See bug 6821290.
            }

        };

        return model;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI OPERATIONS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void homeLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Introduction);
    }

    public void manageHiveLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Manage_Hive);
    }

    public void manageRealmsLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Manage_Realms);
    }

    public void manageUsersLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Manage_Users);
    }

    public void manageGroupsLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Manage_Groups);
    }

    public void manageGadgetsLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Manage_Gadgets);
    }

    public void manageFeaturesLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Manage_Features);
    }

    public void manageRepositoryLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Manage_Repositories);
    }

    public void manageConferencesLinkClickHandler(ActionEvent evt) {
        this.performClickAction(NavigationFlow.Manage_Conferences);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void performClickAction(final NavigationFlow navigationFlow) {

        DialogBeanCallback callback = new DialogBeanCallback() {
            public void okButtonHandler() {
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO,
                                     navigationFlow.name());

                setCurrentPageSaved(Boolean.TRUE);

                refreshRegionContent();
                refreshNavPanel();
            }

            public void cancelButtonHandler() {
                // Do nothing
            }
        };

        if (!isCurrentPageSaved()) {
            DialogBean.getInstance().ConfirmationDialog(TranslationUtil.getTranslatedString(BundlePropertyFiles.ApplicationBundle.getBaseName(),
                                                                                            "string_unsaved_data_title"),
                                                        TranslationUtil.getTranslatedString(BundlePropertyFiles.ApplicationBundle.getBaseName(),
                                                                                            "string_unsaved_data_text"),
                                                        callback);
        } else {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO,
                                 navigationFlow.name());
        }

    }

    private void clearNotRequiredCache(NavigationFlow currentNavFlow) {

        // TODO Add more bean clear methods

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

        if (!currentNavFlow.getSection().isManageGroups()) {
            if (GroupPropertiesBean.getInstance() != null) {
                GroupPropertiesBean.getInstance().clearPageFlowScopeCache();
            }

            if (GroupListBean.getInstance() != null) {
                GroupListBean.getInstance().clearPageFlowScopeCache();
            }
        }

        if (!currentNavFlow.getSection().isManageRealms()) {
            if (DBRealmPropertiesBean.getInstance() != null) {
                DBRealmPropertiesBean.getInstance().clearPageFlowScopeCache();
            }

            if (EditLDAPRealmPropertiesBean.getInstance() != null) {
                EditLDAPRealmPropertiesBean.getInstance().clearPageFlowScopeCache();
            }

            if (RealmListBean.getInstance() != null) {
                RealmListBean.getInstance().clearPageFlowScopeCache();
            }
        }

        if (!currentNavFlow.getSection().isManageGadgets()) {
            if (GadgetListBean.getInstance() != null) {
                GadgetListBean.getInstance().clearPageFlowScopeCache();
            }
        }

        if (!currentNavFlow.getSection().isManageFeatures()) {
            if (FeatureListBean.getInstance() != null) {
                FeatureListBean.getInstance().clearPageFlowScopeCache();
            }

            if (FeaturePropertiesBean.getInstance() != null) {
                FeaturePropertiesBean.getInstance().clearPageFlowScopeCache();
            }
        }

        if (!currentNavFlow.getSection().isManageRepositories()) {
            if (RepositoryListBean.getInstance() != null) {
                RepositoryListBean.getInstance().clearPageFlowScopeCache();
            }

            if (CreateRepositoryBean.getInstance() != null) {
                CreateRepositoryBean.getInstance().clearPageFlowScopeCache();
            }

        }

        if (!currentNavFlow.getSection().isManageConferences()) {
            if (ConferenceListBean.getInstance() != null) {
                ConferenceListBean.getInstance().clearPageFlowScopeCache();
            }
        }
    }

}

 */