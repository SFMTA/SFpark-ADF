package sfpark.asset.manager.application.key;

import java.util.Locale;


/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120522-01 Mark Piller - Oracle Consulting  Created this class file
 * 
 */

public enum ViewScopeKey {
    METER_TYPE,
    SMART_METER_FLAG,
    MS_PAY_STATION_ID,
    BLOCKFACE_ID,
    MS_NUMBER,
    ACTION,
    PRESERVE_MS_PAY_STATION_ID;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String Key;

    private ViewScopeKey() {
        this.Key = "viewScopeKey." + name().toLowerCase(Locale.ENGLISH);
    }

    public String getKey() {
        return Key;
    }
} // ViewScopeKey
