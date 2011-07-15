package sfpark.osp.manager.view.flow;

public enum NavigationFlow {
    ERROR("/View/jsffFiles/common/ErrorPage.jsff"),

    EditOSPInventory("/View/jsffFiles/offStreetParkingManagement/offStreetParkingManagementPage.jsff");
    // EditOSPInventory("/View/jsffFiles/ospManagement/OSPManagementPage.jsff");

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
