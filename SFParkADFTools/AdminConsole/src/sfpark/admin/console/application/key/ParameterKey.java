package sfpark.admin.console.application.key;

public enum ParameterKey {
    OPERATION("operation");

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
