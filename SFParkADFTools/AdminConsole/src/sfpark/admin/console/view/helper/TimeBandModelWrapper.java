package sfpark.admin.console.view.helper;

public final class TimeBandModelWrapper {
    public TimeBandModelWrapper() {
        super();
    }

    private String MeterClass;
    private String DateType;

    public void setMeterClass(String MeterClass) {
        this.MeterClass = MeterClass;
    }

    public String getMeterClass() {
        return MeterClass;
    }

    public void setDateType(String DateType) {
        this.DateType = DateType;
    }

    public String getDateType() {
        return DateType;
    }
}
