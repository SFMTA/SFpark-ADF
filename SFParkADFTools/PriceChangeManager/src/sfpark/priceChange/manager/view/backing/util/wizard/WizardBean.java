package sfpark.priceChange.manager.view.backing.util.wizard;

import javax.el.ELContext;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.view.backing.util.ADFUtil;
import sfpark.adf.tools.view.backing.util.wizard.WizardBeanCallback;
import sfpark.adf.tools.view.backing.util.wizard.WizardBeanInterface;
import sfpark.adf.tools.view.backing.util.wizard.helpers.WizardModel;

import sfpark.adf.tools.view.backing.util.wizard.helpers.WizardStop;

public class WizardBean {

    private WizardModel wizardModel;
    private WizardBeanCallback wizardBeanCallback;

    public static WizardBean getInstance() {
        return (WizardBean)ADFUtil.getCurrentInstanceFor("wizardBean");
    }

    public WizardBean() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // WIZARD API

    public void setupWizard(WizardModel wizardModel,
                            WizardBeanCallback wizardBeanCallback) {

        setWizardModel(wizardModel);
        setWizardBeanCallback(wizardBeanCallback);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    public boolean isDisablePrevious() {
        return isCurrentWizardStopFirst();
    }

    public boolean isDisableNext() {
        return isCurrentWizardStopLast();
    }

    public boolean isDisableFinish() {
        return !isCurrentWizardStopLast();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void commandLinkActionHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("cancel")) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // CANCEL

            if (getWizardBeanCallback() != null) {
                getWizardBeanCallback().cancelButtonHandler();
            }

        } else if (ID.contains("previous")) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // PREVIOUS

            boolean previousActionPossible = true;

            WizardStop current = getWizardModel().getCurrentWizardStop();

            WizardBeanInterface currentInterface =
                getWizardBeanInterfaceFor(current);

            if (currentInterface != null) {
                previousActionPossible =
                        currentInterface.onPreviousActionHandler();
            }

            if (previousActionPossible) {
                WizardStop previous =
                    getWizardModel().getPreviousWizardStopFor(current);

                getWizardModel().setCurrentWizardStop(previous);
            }

        } else if (ID.contains("next")) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // NEXT

            boolean nextActionPossible = true;

            WizardStop current = getWizardModel().getCurrentWizardStop();

            WizardBeanInterface currentInterface =
                getWizardBeanInterfaceFor(current);

            if (currentInterface != null) {
                nextActionPossible = currentInterface.onNextActionHandler();
            }

            if (nextActionPossible) {
                WizardStop next =
                    getWizardModel().getNextWizardStopFor(current);

                getWizardModel().setCurrentWizardStop(next);
            }

        } else if (ID.contains("finish")) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // FINISH

            for (WizardStop wizardStop :
                 getWizardModel().getWizardStopList()) {
                WizardBeanInterface wizardBeanInterface =
                    getWizardBeanInterfaceFor(wizardStop);

                if (wizardBeanInterface != null) {
                    wizardBeanInterface.compulsoryOperation();
                }
            }

            if (getWizardBeanCallback() != null) {
                getWizardBeanCallback().finishButtonHandler();
            }

            setWizardModel(null);
            setWizardBeanCallback(null);

        }
    }

    public void commandNavigationItemActionHandler(ActionEvent event) {

        boolean actionPossible = true;

        WizardStop current = getWizardModel().getCurrentWizardStop();

        WizardStop clicked =
            (WizardStop)event.getComponent().getAttributes().get(getStopAttributeName());

        if (current.getStopNumber() < clicked.getStopNumber()) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Moving from lower step to higher step
            // Perform all intermediary NEXT handlers

            for (int i = current.getStopNumber(); i < clicked.getStopNumber();
                 i++) {

                WizardStop wizardStop =
                    getWizardModel().getWizardStopList().get(i - 1);

                WizardBeanInterface wizardBeanInterface =
                    getWizardBeanInterfaceFor(wizardStop);

                if (wizardBeanInterface != null) {
                    actionPossible = wizardBeanInterface.onNextActionHandler();
                }

                if (!actionPossible) {
                    break;
                }
            }

        } else {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Moving from higher step to lower step
            // Perform all intermediary PREVIOUS handlers

            for (int i = current.getStopNumber(); i > clicked.getStopNumber();
                 i++) {

                WizardStop wizardStop =
                    getWizardModel().getWizardStopList().get(i - 1);

                WizardBeanInterface wizardBeanInterface =
                    getWizardBeanInterfaceFor(wizardStop);

                if (wizardBeanInterface != null) {
                    actionPossible =
                            wizardBeanInterface.onPreviousActionHandler();
                }

                if (!actionPossible) {
                    break;
                }
            }

        }

        if (actionPossible) {
            getWizardModel().setCurrentWizardStop(clicked);
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private boolean isCurrentWizardStopFirst() {
        return (getWizardModel().getCurrentWizardStop().getStopNumber() == 1);
    }

    private boolean isCurrentWizardStopLast() {
        return (getWizardModel().getCurrentWizardStop().getStopNumber() ==
                getWizardModel().getWizardStopList().size());
    }

    private <T extends WizardBeanInterface> T getWizardBeanInterfaceFor(WizardStop wizardStop) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Object BeanInstance =
            elContext.getELResolver().getValue(elContext, null,
                                               wizardStop.getStopBeanClassName());

        return (T)BeanInstance;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS EXTRA

    public String getStopAttributeName() {
        return "stop";
    }

    public RegionModel getWizardRegionModel() {
        RegionModel model = new RegionModel() {
            public String getViewId(FacesContext facesContext) {
                return getWizardModel().getCurrentWizardStop().getStopViewID();
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

    public String getDisplayStopTitle() {

        WizardStop current = getWizardModel().getCurrentWizardStop();

        return TranslationUtil.getCommonBundleString(CommonBundleKey.wizard_stop_title,
                                                     current.getStopNumber(),
                                                     getWizardModel().getWizardStopList().size(),
                                                     current.getStopTitle());

    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //

    private void setWizardModel(WizardModel wizardModel) {
        this.wizardModel = wizardModel;
    }

    private WizardModel getWizardModel() {
        return wizardModel;
    }

    private void setWizardBeanCallback(WizardBeanCallback wizardBeanCallback) {
        this.wizardBeanCallback = wizardBeanCallback;
    }

    private WizardBeanCallback getWizardBeanCallback() {
        return wizardBeanCallback;
    }
}
