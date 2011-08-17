package sfpark.priceChange.manager.view.flow;

public enum NavigationFlow {

    HOME(NavigationSection.COMMON,
         NavigationSection.COMMON.getJSFFFileRef("/home.jsff")),

    ERROR(NavigationSection.COMMON,
          NavigationSection.COMMON.getJSFFFileRef("/error.jsff")),

    DEPLOYMENT_PROCESS(NavigationSection.PRICE_DEPLOYMENT_PROCESS,
                       NavigationSection.PRICE_DEPLOYMENT_PROCESS.getJSFFFileRef("/deploymentProcessListPage.jsff")),

    EDIT_DEPLOYMENT_PROCESS(NavigationSection.PRICE_DEPLOYMENT_PROCESS,
                            NavigationSection.PRICE_DEPLOYMENT_PROCESS.getJSFFFileRef("/deploymentProcessPropertiesPage.jsff"));

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final NavigationSection section;
    private final String fileName;

    private NavigationFlow(NavigationSection section, String fileName) {
        this.section = section;
        this.fileName = fileName;
    }

    public NavigationSection getSection() {
        return section;
    }

    public String getFileName() {
        return fileName;
    }
}
