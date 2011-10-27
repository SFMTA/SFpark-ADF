package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum OSPFacilityType {
    GARAGE("G", "Garage"),
    LOT("L", "Lot");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static OSPFacilityType extract(String ospFacilityTypeString) {
        for (OSPFacilityType ospFacilityType : values()) {
            if (StringUtil.areEqual(ospFacilityType.getStringForTable(),
                                    ospFacilityTypeString)) {
                return ospFacilityType;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return GARAGE;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isGarage() {
        return (this == GARAGE);
    }

    public boolean isLot() {
        return (this == LOT);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private OSPFacilityType(String StringForTable, String StringForDisplay) {
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
