package sfpark.adf.tools.model.data.helper;

public enum CalendarType {
    RATE_CHANGE("RateChg", "Rate Change"),
    SPECIAL_EVENT("SpecEvt", "Special Event");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static CalendarType extract(String typeString) {
        for (CalendarType calendarType : values()) {
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

    public boolean isRateChange() {
        return (this == RATE_CHANGE);
    }

    public boolean isSpecialEvent() {
        return (this == SPECIAL_EVENT);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private CalendarType(String StringForTable, String StringForDisplay) {
        this.StringForTable = StringForTable;
        this.StringForDisplay = StringForDisplay;
    }

    public String getStringForTable() {
        return StringForTable;
    }

    public String getStringForDisplay() {
        return StringForDisplay;
    }
}
