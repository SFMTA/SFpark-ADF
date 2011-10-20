package sfpark.rateChange.manager.view.helper;

public final class BlockTimeBandDetail {
    public BlockTimeBandDetail() {
        super();
    }

    private String BlockID;
    private String MeterClass;
    private String DateType;
    private String OpenTime;
    private String CloseTime;

    public void setBlockID(String BlockID) {
        this.BlockID = BlockID;
    }

    public String getBlockID() {
        return BlockID;
    }

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

    public void setOpenTime(String OpenTime) {
        this.OpenTime = OpenTime;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setCloseTime(String CloseTime) {
        this.CloseTime = CloseTime;
    }

    public String getCloseTime() {
        return CloseTime;
    }
}
