package sfpark.asset.manager.view.flow;

public enum NavigationFlow {
    ERROR("/View/jsffFiles/common/error.jsff"),

    ChangeCNNIDPage("/View/jsffFiles/meterSpaceManagement/changeCNNIDPage.jsff"),

    MeterModelsPage("/View/jsffFiles/shared/meterModelsPage.jsff"),

    MeterScheduleTemplatePage("/View/jsffFiles/shared/meterScheduleTemplatePage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    AddParkingSpace("/View/jsffFiles/meterSpaceManagement/meterSpaceManagementPage.jsff"),

    EditParkingSpace("/View/jsffFiles/meterSpaceManagement/meterSpaceManagementPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    BulkEditParkingSpace("/View/jsffFiles/bulkMeterSpaceManagement/bulkMeterSpaceManagementPage.jsff");

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
