package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum OSPDataFeedFlag {
    YES("Y", "Yes"),
    NO("N", "No"),
    SUSPEND("S", "Suspend");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static OSPDataFeedFlag extract(String ospDataFeedFlagString) {
        for (OSPDataFeedFlag ospDataFeedFlag : values()) {
            if (StringUtil.areEqual(ospDataFeedFlag.getStringForTable(),
                                    ospDataFeedFlagString)) {
                return ospDataFeedFlag;
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

    private OSPDataFeedFlag(String StringForTable, String StringForDisplay) {
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
