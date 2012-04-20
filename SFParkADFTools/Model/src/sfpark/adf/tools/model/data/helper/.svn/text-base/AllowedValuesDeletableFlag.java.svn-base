package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum AllowedValuesDeletableFlag {
    YES("Y", "Yes"),
    NO("N", "No");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static AllowedValuesDeletableFlag extract(String deletableFlagString) {
        for (AllowedValuesDeletableFlag deletableFlag : values()) {
            if (StringUtil.areEqual(deletableFlag.getStringForTable(),
                                    deletableFlagString)) {
                return deletableFlag;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return NO;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isDeletable() {
        return (this == YES);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private AllowedValuesDeletableFlag(String StringForTable,
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
