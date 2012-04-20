package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum MeterSmartFlag {
    YES("Y", "Yes"),
    NO("N", "No");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static MeterSmartFlag extract(String smartFlagString) {
        for (MeterSmartFlag smartFlag : values()) {
            if (StringUtil.areEqual(smartFlag.getStringForTable(),
                                    smartFlagString)) {
                return smartFlag;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return NO;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private MeterSmartFlag(String StringForTable, String StringForDisplay) {
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
