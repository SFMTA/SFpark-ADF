package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum MeterType {
    NONE("-", "-"),
    SINGLE_SPACE("SS", "Single Space"),
    MULTI_SPACE("MS", "Multi Space");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static MeterType extract(String meterTypeString) {
        for (MeterType meterType : values()) {
            if (StringUtil.areEqual(meterType.getStringForTable(),
                                    meterTypeString)) {
                return meterType;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return NONE;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isNone() {
        return (this == NONE);
    }

    public boolean isSingleSpace() {
        return (this == SINGLE_SPACE);
    }

    public boolean isMultiSpace() {
        return (this == MULTI_SPACE);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private MeterType(String StringForTable, String StringForDisplay) {
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
