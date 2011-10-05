package sfpark.rateChange.manager.view.flow;

public enum NavigationFlow {
    ERROR("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // THRESHOLD related

    AddThreshold("/View/jsffFiles/threshold/thresholdAddPage.jsff"),

    EditThreshold("/View/jsffFiles/threshold/thresholdPropertiesPage.jsff"),

    PickThresholdValues("/View/jsffFiles/threshold/helper/pickThresholdValuesPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DEPLOYMENT related

    EditRateChangeProcess("/View/jsffFiles/deployment/deploymentEditPage.jsff"),
    EditRateChangeProcessProperties("/View/jsffFiles/deployment/deploymentPropertiesPage.jsff"),
    AfterEditRateChangeProcessProperties("/View/jsffFiles/common/errorsPage.jsff"),
    DeleteRateChangeProcess("/View/jsffFiles/deployment/deploymentDeletePage.jsff"),
    AfterDeleteRateChangeProcess("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // MAIN Rate Change related

    PickAreaType("/View/jsffFiles/rateChange/helper/pickAreaTypePage.jsff"),
    PickCalendar("/View/jsffFiles/rateChange/helper/pickCalendarPage.jsff"),

    PickMeter("/View/jsffFiles/rateChange/helper/pickMeterPage.jsff"),
    PickPMDistrictsAndBlocks("/View/jsffFiles/rateChange/helper/pickPMDistrictsAndBlocksPage.jsff"),

    AddRateChange("/View/jsffFiles/rateChange/rateChangePropertiesPage.jsff"),

    UpdateRateChange("/View/jsffFiles/rateChange/rateChangeUpdatePage.jsff"),

    DeleteRateChange("/View/jsffFiles/rateChange/rateChangeDeletePage.jsff"),
    AfterDeleteRateChange("/View/jsffFiles/common/errorsPage.jsff"),

    DeployRateChange("/View/jsffFiles/rateChange/rateChangeDeployPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // MODIFICATION related

    ModifyBlockRateSched("/View/jsffFiles/modification/modificationPropertiesPage.jsff");

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
