package sfpark.priceChange.manager.view.flow;

public enum NavigationSection {
    COMMON("/common"),

    PRICE_APPROVAL_PROCESS(""),
    PRICE_DEPLOYMENT_PROCESS("");

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isPriceApprovalProcess() {
        return (this == PRICE_APPROVAL_PROCESS);
    }

    public boolean isPriceDeploymentProcess() {
        return (this == PRICE_DEPLOYMENT_PROCESS);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public String getJSFFFileRef(String fileName) {
        return jsffFolder + fileName;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String jsffFolder;

    private NavigationSection(String jsffFolder) {
        this.jsffFolder = "/View/jsffFiles" + jsffFolder;
    }

}
