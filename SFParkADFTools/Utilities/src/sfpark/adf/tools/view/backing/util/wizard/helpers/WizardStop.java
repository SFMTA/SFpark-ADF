package sfpark.adf.tools.view.backing.util.wizard.helpers;

public final class WizardStop {

    public WizardStop(String StopTitle, String StopViewID,
                      String StopBeanClassName) {
        super();

        this.StopTitle = StopTitle;
        this.StopViewID = StopViewID;
        this.StopBeanClassName = StopBeanClassName;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private int StopNumber;
    private String StopTitle;
    private String StopViewID;
    private String StopBeanClassName;
    private boolean StopVisited;

    protected void setStopNumber(int StopNumber) {
        this.StopNumber = StopNumber;
    }

    public int getStopNumber() {
        return StopNumber;
    }

    public String getStopTitle() {
        return StopTitle;
    }

    public String getStopViewID() {
        return StopViewID;
    }

    public String getStopBeanClassName() {
        return StopBeanClassName;
    }

    protected void setStopVisited(boolean StopVisited) {
        this.StopVisited = StopVisited;
    }

    public boolean isStopVisited() {
        return StopVisited;
    }
}
