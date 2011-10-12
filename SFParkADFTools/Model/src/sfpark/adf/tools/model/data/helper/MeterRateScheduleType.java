package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum MeterRateScheduleType {
    BASE("B", "Base Rate"),
    HOURLY("H", "Hourly Rate"),
    SPECIAL("S", "Special Rate");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static MeterRateScheduleType extract(String meterRateScheduleTypeString) {
        for (MeterRateScheduleType meterRateScheduleType : values()) {
            if (StringUtil.areEqual(meterRateScheduleType.getStringForTable(),
                                    meterRateScheduleTypeString)) {
                return meterRateScheduleType;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return BASE;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isBase() {
        return (this == BASE);
    }

    public boolean isHourly() {
        return (this == HOURLY);
    }

    public boolean isSpecial() {
        return (this == SPECIAL);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private MeterRateScheduleType(String StringForTable,
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
