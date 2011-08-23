package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum RateChangeStatus {
    WORKING("Working", "Working"),
    SUBMITTED("Submitted", "Submitted"),
    APPROVED("Approved", "Approved");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static RateChangeStatus extract(String statusString) {
        for (RateChangeStatus rateChangeStatus : values()) {
            if (StringUtil.areEqual(rateChangeStatus.getStringForTable(),
                                    statusString)) {
                return rateChangeStatus;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return WORKING;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isWorking() {
        return (this == WORKING);
    }

    public boolean isSubmitted() {
        return (this == SUBMITTED);
    }

    public boolean isApproved() {
        return (this == APPROVED);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForTable;
    private final String StringForDisplay;

    private RateChangeStatus(String StringForTable, String StringForDisplay) {
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
