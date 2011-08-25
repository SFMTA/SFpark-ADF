package sfpark.rateChange.manager.view.flow;

public enum NavigationFlow {
    ERROR("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    PickAreaType("/View/jsffFiles/rateChange/helper/pickAreaTypePage.jsff"),
    PickCalendar("/View/jsffFiles/rateChange/helper/pickCalendarPage.jsff"),

    PickPMDistrictsAndBlocks("/View/jsffFiles/rateChange/helper/pickPMDistrictsAndBlocksPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    AddRateChange("/View/jsffFiles/rateChange/rateChangePropertiesPage.jsff"),

    UpdateRateChange("/View/jsffFiles/rateChange/rateChangeUpdatePage.jsff"),

    DeleteRateChange("/View/jsffFiles/common/errorsPage.jsff"),
    AfterDeleteRateChange("/View/jsffFiles/common/errorsPage.jsff"),

    DeployRateChange("/View/jsffFiles/rateChange/rateChangeDeployPage.jsff");

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
