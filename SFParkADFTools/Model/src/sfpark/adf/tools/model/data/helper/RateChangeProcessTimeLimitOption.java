package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum RateChangeProcessTimeLimitOption {
    YES("Y", "Should be reconciled"),
    NO("N", "Should NOT be reconciled");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static RateChangeProcessTimeLimitOption extract(String timeLimitOptionString) {
        for (RateChangeProcessTimeLimitOption timeLimitOption : values()) {
            if (StringUtil.areEqual(timeLimitOption.getStringForTable(),
                                    timeLimitOptionString)) {
                return timeLimitOption;
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

    private RateChangeProcessTimeLimitOption(String StringForTable,
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
