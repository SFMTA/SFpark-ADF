package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum RateChangeProcessStepStartFlag {
    HOLD("-", "Hold"),
    INITIATE("Y", "Initiate");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static RateChangeProcessStepStartFlag extract(String stepStartFlagString) {
        for (RateChangeProcessStepStartFlag stepStartFlag : values()) {
            if (StringUtil.areEqual(stepStartFlag.getStringForTable(),
                                    stepStartFlagString)) {
                return stepStartFlag;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return HOLD;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isHold() {
        return (this == HOLD);
    }

    public boolean isInitiate() {
        return (this == INITIATE);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private RateChangeProcessStepStartFlag(String StringForTable,
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
