package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum PMDistrictAreaType {
    PILOT("Pilot", "Pilot"),
    CONTROL("Control", "Control");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static PMDistrictAreaType extract(String areaTypeString) {
        for (PMDistrictAreaType PMDistrictAreaType : values()) {
            if (StringUtil.areEqual(PMDistrictAreaType.getStringForTable(),
                                    areaTypeString)) {
                return PMDistrictAreaType;
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

    private PMDistrictAreaType(String StringForTable,
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
