package sfpark.adf.tools.model.data.helper;

import sfpark.adf.tools.utilities.generic.StringUtil;
/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111031-01 Mark Piller - Oracle Consulting  change "Off per hour" to "Off per hr"
 */
public enum GarageRatesQualifier {

    NULL(""),
    FLAT_RATE("Flat rate"),
    OFF_TOTAL("Off total"),
    OFF_PER_HOUR("Off per hr"),  // 20111031-01
    PER_DAY("Per day"),
    PER_HOUR("Per hour"),
    PER_MONTH("Per month");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public static GarageRatesQualifier extractQualifier(String qualifierStr) {
        for (GarageRatesQualifier qualifier : GarageRatesQualifier.values()) {
            if (StringUtil.areEqual(qualifier.getRateQualifierText(),
                                    qualifierStr)) {
                return qualifier;
            }
        }

        // Should never reach here
        // Added this to avoid compilation errors
        return NULL;
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String RateQualifierText;

    private GarageRatesQualifier(String RateQualifierText) {
        this.RateQualifierText = RateQualifierText;
    }

    public String getRateQualifierText() {
        return RateQualifierText;
    }
}
