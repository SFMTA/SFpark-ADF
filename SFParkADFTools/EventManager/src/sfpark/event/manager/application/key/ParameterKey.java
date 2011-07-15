package sfpark.event.manager.application.key;

import java.util.Locale;

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
