package sfpark.event.manager.view.flow;

public enum NavigationSection {

    ERROR("/common"),
    EVENT_CALENDAR("/eventCalendar");

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isEventCalendar() {
        return (this == EVENT_CALENDAR);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public String getJSFFFileRef(String fileName) {
        return jsffFolder + fileName;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String jsffFolder;

    private NavigationSection(String jsffFolder) {
        this.jsffFolder = "/View/jsffFiles" + jsffFolder;
    }
}
