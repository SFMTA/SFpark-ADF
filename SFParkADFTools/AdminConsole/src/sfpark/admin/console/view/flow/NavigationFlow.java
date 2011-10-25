package sfpark.admin.console.view.flow;

public enum NavigationFlow {

    ERROR("/View/jsffFiles/common/errorsPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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
