package sfpark.rateChange.manager.view.flow;

public enum NavigationFlow {
    ERROR("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    PickAreaType("/View/jsffFiles/common/errorsPage.jsff"),

    PickCalendar("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    AddRateChange("/View/jsffFiles/common/errorsPage.jsff"),

    UpdateRateChange("/View/jsffFiles/common/errorsPage.jsff"),

    DeleteRateChange("/View/jsffFiles/common/errorsPage.jsff"),
    AfterDeleteRateChange("/View/jsffFiles/common/errorsPage.jsff"),

    DeployRateChange("/View/jsffFiles/common/errorsPage.jsff");

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String fileName;

    private NavigationFlow(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
