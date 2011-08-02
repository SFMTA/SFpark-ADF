package sfpark.priceChange.manager.application.key;

import java.util.Locale;

public enum SessionScopeKey {

    NAVIGATION_INFO;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String Key;

    private SessionScopeKey() {
        this.Key = "sessionScopeKey." + name().toLowerCase(Locale.ENGLISH);
    }

    public String getKey() {
        return Key;
    }

}
