package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum GarageRatesTimeBand {

    NULL("", ""),
    BAND_1("00:00", "09:00"),
    BAND_2("09:00", "12:00"),
    BAND_3("12:00", "15:00"),
    BAND_4("15:00", "18:00"),
    BAND_5("18:00", "24:00");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isNULL() {
        return (this == NULL);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static GarageRatesTimeBand extractTimeBand(String fromTime,
                                                      String toTime) {
        for (GarageRatesTimeBand timeBand : GarageRatesTimeBand.values()) {
            if (StringUtil.areEqual(timeBand.getFromTime(), fromTime) &&
                StringUtil.areEqual(timeBand.getToTime(), toTime)) {
                return timeBand;
            }
        }

        return NULL;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String FromTime;
    private final String ToTime;

    private GarageRatesTimeBand(String FromTime, String ToTime) {
        this.FromTime = FromTime;
        this.ToTime = ToTime;
    }

    public String getFromTime() {
        return FromTime;
    }

    public String getToTime() {
        return ToTime;
    }
}
