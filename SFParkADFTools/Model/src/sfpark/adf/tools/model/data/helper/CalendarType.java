package sfpark.adf.tools.model.data.helper;

public enum CalendarType {
    RATE_CHANGE("RateChg"),
    SPECIAL_EVENT("SpecEvt");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static CalendarType extractType(String typeString) {
        for (CalendarType calendarType : CalendarType.values()) {
            if (calendarType.getStringForTable().equalsIgnoreCase(typeString)) {
                return calendarType;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return RATE_CHANGE;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;

    private CalendarType(String StringForTable) {
        this.StringForTable = StringForTable;
    }

    public String getStringForTable() {
        return StringForTable;
    }
}
