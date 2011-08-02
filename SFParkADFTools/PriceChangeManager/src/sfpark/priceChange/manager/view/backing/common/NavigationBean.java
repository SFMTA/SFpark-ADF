package sfpark.priceChange.manager.view.backing.common;

import sfpark.priceChange.manager.view.backing.BaseBean;

public class NavigationBean extends BaseBean {
    public NavigationBean() {
        super();
    }
    
    /*
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

        / *
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ACCESSORS

        public static NavigationBean getInstance() {
            return (NavigationBean)getCurrentInstanceFor("navigationBean");
        }

        public void refreshNavPanel() {
            addPartialTarget(this.getNavigationPanel());
        }

        public void refreshRegionContent() {
            addPartialTarget(this.getNavigationRichRegion());
        }
        * /

     */
}
