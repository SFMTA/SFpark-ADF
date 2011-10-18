package sfpark.rateChange.manager.view.helper;

public class BlockTimeBandTypeTO {
    public BlockTimeBandTypeTO() {
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
