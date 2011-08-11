package sfpark.adf.tools.utilities.constants;

public enum SecurityGroup {
    METERS("MetersGroup"),
    METERS_READ_ONLY("MetersROGroup"),

    GARAGES("GaragesGroup"),

    RATE_CHANGE("RateChangeGroup"),

    SUPER_USER("SFParkADFToolsAdminGroup");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String GroupName;

    private SecurityGroup(String GroupName) {
        this.GroupName = GroupName;
    }

    public String getGroupName() {
        return GroupName;
    }
}
