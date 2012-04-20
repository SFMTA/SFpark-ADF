package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum GarageRatesDisplayCode {
    YES("Y", "Normal Display using RATE_DESC and RATE_RESTRICTIONS"),
    NO("N", "Do not use RATE_RESTRICTIONS"),
    OVERRIDE("O",
             "Override the RATE_DESC with the value in RATE_RESTRICTIONS");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static GarageRatesDisplayCode extract(String garageRatesDisplayCodeString) {
        for (GarageRatesDisplayCode garageRatesDisplayCode : values()) {
            if (StringUtil.areEqual(garageRatesDisplayCode.getStringForTable(),
                                    garageRatesDisplayCodeString)) {
                return garageRatesDisplayCode;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return YES;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private GarageRatesDisplayCode(String StringForTable,
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
