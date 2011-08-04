package sfpark.adf.tools.view.backing.util.wizard.helpers;

import java.beans.IntrospectionException;

import java.util.List;

import org.apache.myfaces.trinidad.model.ProcessMenuModel;

public final class WizardModel extends ProcessMenuModel {

    public WizardModel(List<WizardStop> WizardStopList) throws IntrospectionException {
        super(WizardStopList, "viewId");

        this.WizardStopList = WizardStopList;
        this.CurrentWizardStop = WizardStopList.get(0);

        for (int i = 0; i < WizardStopList.size(); i++) {
            WizardStopList.get(i).setStopNumber(i + 1);
        }

    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // METHODS FOR EASE

    public WizardStop getPreviousWizardStopFor(WizardStop current) {
        int previousStopNumber = Math.max(current.getStopNumber() - 1, 1) - 1;

        return getWizardStopList().get(previousStopNumber);
    }

    public WizardStop getNextWizardStopFor(WizardStop current) {
        int nextStopNumber =
            Math.min(current.getStopNumber() + 1, getWizardStopList().size()) -
            1;

        return getWizardStopList().get(nextStopNumber);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final List<WizardStop> WizardStopList;
    private WizardStop CurrentWizardStop;

    public List<WizardStop> getWizardStopList() {
        return WizardStopList;
    }

    public void setCurrentWizardStop(WizardStop CurrentWizardStop) {
        this.CurrentWizardStop = CurrentWizardStop;
        this.CurrentWizardStop.setStopVisited(true);
    }

    public WizardStop getCurrentWizardStop() {
        return CurrentWizardStop;
    }
}

/*
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // OTHER METHODS

    public boolean isCurrentStopFirst() {
        return this.isThisStopFirst(this.getCurrentStop());
    }

    public boolean isCurrentStopLast() {
        return this.isThisStopLast(this.getCurrentStop());
    }

    public boolean isThisStopFirst(WizardStop stop) {
        return (stop.getStopNumber() == 1);
    }

    public boolean isThisStopLast(WizardStop stop) {
        return (stop.getStopNumber() == this.getWizardStops().size());
    }

    public WizardStop getNextStopFor(WizardStop stop) {
        int stopNumber =
            Math.min(stop.getStopNumber() + 1, this.getWizardStops().size());

        return this.getWizardStops().get(stopNumber - 1);
    }

    public WizardStop getPreviousStopFor(WizardStop stop) {
        int stopNumber = Math.max(stop.getStopNumber() - 1, 1);

        return this.getWizardStops().get(stopNumber - 1);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void initialiseAllStops() {

        this.setCurrentStop(wizardStops.get(0));

        for (int i = 0; i < wizardStops.size(); i++) {
            WizardStop wizardStop = wizardStops.get(i);

            wizardStop.setStopNumber(i + 1);
        }
    }

 */