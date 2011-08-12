package sfpark.calendar.manager.view.flow;

public enum NavigationFlow {
    ERROR("/View/jsffFiles/common/errorsPage.jsff"),

    PickCalendarDates("/View/jsffFiles/calendar/pickCalendarDatesPage.jsff"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    AddCalendar("/View/jsffFiles/calendar/calendarPropertiesPage.jsff"),

    EditCalendar("/View/jsffFiles/calendar/calendarPropertiesPage.jsff"),

    ReadOnlyCalendar("/View/jsffFiles/calendar/calendarPropertiesPage.jsff"),

    DeleteCalendar("/View/jsffFiles/calendar/calendarDeletePage.jsff");

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
