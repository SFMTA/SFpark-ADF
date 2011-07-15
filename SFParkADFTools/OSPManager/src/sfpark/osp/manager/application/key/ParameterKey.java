package sfpark.osp.manager.application.key;

public enum ParameterKey {
    OSP_ID("ospID"),
    LONGITUDE("longitude"),
    LATITUDE("latitude");

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
