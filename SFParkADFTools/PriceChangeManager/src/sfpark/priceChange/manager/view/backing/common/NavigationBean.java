package sfpark.priceChange.manager.view.backing.common;

public class NavigationBean {

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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    /*
    public boolean isDisableApprovalProcessLink() {
        return getCurrentNavFlow().getSection().isPriceApprovalProcess();
    }

    public boolean isDisableDeploymentProcessLink() {
        return getCurrentNavFlow().getSection().isPriceDeploymentProcess();
    }
    // */

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    /*
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
    // */

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS EXTRA

    /*
    public RegionModel getContentRegionModel() {
        RegionModel model = new RegionModel() {
            public String getViewId(FacesContext facesContext) {

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
    // */

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    /*
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
    // */
}
