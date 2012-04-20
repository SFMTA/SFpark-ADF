package sfpark.rateChange.manager.view.flow;


/**
 * Description:
 * This class is used to define which jsff pages are called from the backing beans supporting the jsff pages
 * 
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111129-01 Mark Piller - Oracle Consulting  add FinalizeRateChange(), AfterFinalizeRateChange()
 * 20111130-01 Mark Piller - Oracle Consulting  add ResetRateChange(), AfterResetRateChangeProcess()
 */
public enum NavigationFlow {
    ERROR("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // THRESHOLD related

    AddThreshold("/View/jsffFiles/threshold/thresholdAddPage.jsff"),

    EditThreshold("/View/jsffFiles/threshold/thresholdPropertiesPage.jsff"),
    AfterEditThreshold("/View/jsffFiles/common/errorsPage.jsff"),

    PickThresholdValues("/View/jsffFiles/threshold/helper/pickThresholdValuesPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // BLOCKTIMEBAND related

    EditTimeband("/View/jsffFiles/blockTimeband/blockTimebandPropertiesPage.jsff"),

    PickTimebandType("/View/jsffFiles/blockTimeband/helper/pickTimebandTypePage.jsff"),

    PickTimebandOption("/View/jsffFiles/blockTimeband/helper/add/pickTimebandOptionPage.jsff"),
    PickTimebandValues("/View/jsffFiles/blockTimeband/helper/add/pickTimebandValuesPage.jsff"),
    AddTimeband("/View/jsffFiles/blockTimeband/blockTimebandAddPage.jsff"),

    DeleteTimeband("/View/jsffFiles/blockTimeband/blockTimebandDeletePage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DEPLOYMENT related

    EditRateChangeProcess("/View/jsffFiles/deployment/deploymentEditPage.jsff"),
    EditRateChangeProcessProperties("/View/jsffFiles/deployment/deploymentPropertiesPage.jsff"),
    AfterEditRateChangeProcessProperties("/View/jsffFiles/common/errorsPage.jsff"),
    DeleteRateChangeProcess("/View/jsffFiles/deployment/deploymentDeletePage.jsff"),
    AfterDeleteRateChangeProcess("/View/jsffFiles/common/errorsPage.jsff"),
    // 20111130-01 added
    ResetRateChange("/View/jsffFiles/deployment/deploymentResetPage.jsff"),
    AfterResetRateChangeProcess("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // MAIN Rate Change related

    PickAreaType("/View/jsffFiles/rateChange/helper/pickAreaTypePage.jsff"),
    PickCalendar("/View/jsffFiles/rateChange/helper/pickCalendarPage.jsff"),

    PickMeter("/View/jsffFiles/rateChange/helper/pickMeterPage.jsff"),
    PickPMDistrictsAndBlocks("/View/jsffFiles/rateChange/helper/pickPMDistrictsAndBlocksPage.jsff"),

    AddRateChange("/View/jsffFiles/rateChange/rateChangePropertiesPage.jsff"),

    // 20111129-01 added
    FinalizeRateChange("/View/jsffFiles/rateChange/rateChangeFinalizePage.jsff"),
    AfterFinalizeRateChange("/View/jsffFiles/common/errorsPage.jsff"),

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
