package sfpark.adf.tools.model.data.helper;

public enum RateChangeAreaType {
    PILOT("Pilot", "Pilot"),
    CONTROL("Control", "Control");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static RateChangeAreaType extract(String areaTypeString) {
        for (RateChangeAreaType rateChangeAreaType : values()) {
            if (rateChangeAreaType.getStringForTable().equalsIgnoreCase(areaTypeString)) {
                return rateChangeAreaType;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return PILOT;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isPilot() {
        return (this == PILOT);
    }

    public boolean isControl() {
        return (this == CONTROL);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private RateChangeAreaType(String StringForTable,
                               String StringForDisplay) {
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
