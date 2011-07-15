package sfpark.asset.manager.application.key;

public enum ParameterKey {
    PARKING_SPACE_ID("parkingSpaceID"),
    LONGITUDE("longitude"),
    LATITUDE("latitude"),

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    PARKING_SPACE_GROUP_ID("parkingSpaceGroupID");

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String Key;

    private ParameterKey(String Key) {
        this.Key = Key;
    }

    public String getKey() {
        return Key;
    }
}
