package sfpark.event.manager.view.flow;

public enum NavigationFlow {

    ERROR(NavigationSection.ERROR, NavigationMode.ERROR, "/errorsPage.jsff"),

    LIST_EVENT_CALENDAR(NavigationSection.EVENT_CALENDAR, NavigationMode.LIST,
                        "/eventCalendarListPage.jsff"),
    ADD_EVENT_CALENDAR(NavigationSection.EVENT_CALENDAR, NavigationMode.ADD,
                       "/eventCalendarPropertiesPage.jsff"),
    EDIT_EVENT_CALENDAR(NavigationSection.EVENT_CALENDAR, NavigationMode.EDIT,
                        "/eventCalendarPropertiesPage.jsff"),
    ADD_EVENT_CALENDAR_DATE(NavigationSection.EVENT_CALENDAR,
                            NavigationMode.ADD_OR_EDIT,
                            "/addEventCalendarDatePage.jsff"),
    EDIT_EVENT_CALENDAR_NAME(NavigationSection.EVENT_CALENDAR,
                             NavigationMode.EDIT,
                             "/editEventCalendarNamePage.jsff");

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final NavigationSection section;
    private final NavigationMode mode;
    private final String fileName;

    private NavigationFlow(NavigationSection section, NavigationMode mode,
                           String fileName) {
        this.section = section;
        this.mode = mode;
        this.fileName = section.getJSFFFileRef(fileName);
    }

    public NavigationSection getSection() {
        return section;
    }

    public NavigationMode getMode() {
        return mode;
    }

    public String getFileName() {
        return fileName;
    }
}
