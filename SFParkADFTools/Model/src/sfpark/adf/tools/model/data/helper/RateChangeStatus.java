package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111109-01 Mark Piller - Oracle Consulting  added Generating Details, Failure
 *                                              isGeneratingDetails(), isFailure()
 */
public enum RateChangeStatus {
    // 20111109-01
    GENERATINGDETAILS("Generating Details","Generating Details"),
    FAILURE("Failure","Failure"),
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

    // 20111109-01
    public boolean isGeneratingDetails() {
        return (this == GENERATINGDETAILS);
    }

    public boolean isFailure() {
        return (this == FAILURE);
    }

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
