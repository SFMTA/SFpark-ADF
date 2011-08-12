package sfpark.adf.tools.model.data.helper;

public enum CalendarStatus {
    UNLOCKED(" ", "Unlocked"),
    LOCKED("L", "Locked");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static CalendarStatus extract(String statusString) {
        for (CalendarStatus calendarStatus : values()) {
            if (calendarStatus.getStringForTable().equalsIgnoreCase(statusString)) {
                return calendarStatus;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return UNLOCKED;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isUnlocked() {
        return (this == UNLOCKED);
    }

    public boolean isLocked() {
        return (this == LOCKED);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private CalendarStatus(String StringForTable, String StringForDisplay) {
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
