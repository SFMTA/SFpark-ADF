package sfpark.adf.tools.utilities.constants;

public enum RequestParameter {

    ADF_CTRL_STATE("_adf.ctrl-state");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String Key;

    private RequestParameter(String Key) {
        this.Key = Key;
    }

    public String getKey() {
        return Key;
    }
}
