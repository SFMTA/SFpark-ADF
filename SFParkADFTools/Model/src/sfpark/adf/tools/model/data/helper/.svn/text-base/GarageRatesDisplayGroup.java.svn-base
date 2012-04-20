package sfpark.adf.tools.model.data.helper;

public enum GarageRatesDisplayGroup {
    HOURLY_RATES(1, "1 - Hourly Rates"),
    FLAT_RATES_OR_DISCOUNTS(2, "2 - Flat Rates / Discounts"),
    MONTHLY_RATES(3, "3 - Monthly Rates");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isHourlyRates() {
        return (this == HOURLY_RATES);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static GarageRatesDisplayGroup extractGroup(int groupInt) {
        for (GarageRatesDisplayGroup group :
             GarageRatesDisplayGroup.values()) {
            if (group.getDisplayGroupInt() == groupInt) {
                return group;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return HOURLY_RATES;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final int DisplayGroupInt;
    private final String DisplayGroupText;

    private GarageRatesDisplayGroup(int DisplayGroupInt,
                                    String DisplayGroupText) {
        this.DisplayGroupInt = DisplayGroupInt;
        this.DisplayGroupText = DisplayGroupText;
    }

    public int getDisplayGroupInt() {
        return DisplayGroupInt;
    }

    public String getDisplayGroupText() {
        return DisplayGroupText;
    }
}
