package sfpark.adf.tools.model.data.helper;

public enum MeterRateType {
    B("Base Rate"),
    H("Hourly Rate");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isRateTypeB() {
        return (this == B);
    }

    public boolean isRateTypeH() {
        return (this == H);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String Description;

    private MeterRateType(String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return Description;
    }
}
