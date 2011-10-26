package sfpark.admin.console.view.flow;

public enum NavigationFlow {

    ERROR("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // BLOCK TIMEBAND related

    TIMEBAND_LIST("/View/jsffFiles/timeband/timebandListPage.jsff"),

    PICK_TIMEBAND_TYPE("/View/jsffFiles/timeband/helper/pickTimebandTypePage.jsff"),

    PICK_TIMEBAND_VALUES("/View/jsffFiles/timeband/helper/add/pickTimebandValuesPage.jsff"),
    ADD_TIMEBAND("/View/jsffFiles/timeband/timebandAddPage.jsff"),

    DELETE_TIMEBAND("/View/jsffFiles/timeband/timebandDeletePage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALLOWED VALUES related

    ALLOWED_VALUES_LIST("/View/jsffFiles/allowedValues/allowedValuesListPage.jsff"),
    ALLOWED_VALUES_PROPERTIES("/View/jsffFiles/allowedValues/allowedValuesPropertiesPage.jsff");

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
