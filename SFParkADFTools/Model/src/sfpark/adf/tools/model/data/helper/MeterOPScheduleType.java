package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum MeterOPScheduleType {
    OP("OP", "OP"),
    ALT("ALT", "ALT"),
    TOW("TOW", "TOW");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static MeterOPScheduleType extract(String meterOPScheduleTypeString) {
        for (MeterOPScheduleType meterOPScheduleType : values()) {
            if (StringUtil.areEqual(meterOPScheduleType.getStringForTable(),
                                    meterOPScheduleTypeString)) {
                return meterOPScheduleType;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return OP;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isOP() {
        return (this == OP);
    }

    public boolean isALT() {
        return (this == ALT);
    }

    public boolean isTOW() {
        return (this == TOW);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private MeterOPScheduleType(String StringForTable,
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
