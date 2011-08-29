package sfpark.rateChange.manager.application.key;

public enum ParameterKey {

    OPERATION("operation"),
    RATE_CHG_REF_ID("rateChgRefID"),
    PROCESS_ID("processID");

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
