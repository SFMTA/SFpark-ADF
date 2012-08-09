package sfpark.asset.manager.application.key;

import java.util.Locale;


/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120502-01 Mark Piller - Oracle Consulting  Add METER_SCHEDULE_EDIT_EXISTING_RECORDS_SELECTED, METER_RATE_EDIT_EXISTING_RECORDS_SELECTED
 */
public enum PageFlowScopeKey {

    ERROR_TITLE,
    ERROR_MESSAGE,

    INLINE_MESSAGE_TEXT,
    INLINE_MESSAGE_CLASS,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    LONGITUDE,
    LATITUDE,
    BLOCKFACE_DO,

    PARKING_SPACE_ID,
    PARKING_SPACE_DTO,

    ACTIVE_METER_SCHEDULE_LIST,
    HISTORIC_METER_SCHEDULE_LIST,

    ACTIVE_METER_RATE_LIST,
    HISTORIC_METER_RATE_LIST,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    PARKING_SPACE_GROUPS_DO,

    BULK_PARKING_SPACE_TO,
    BULK_METER_SCHEDULE_TO,
    BULK_METER_SCHEDULE_LIST,
    BULK_METER_RATE_TO,
    BULK_METER_RATE_LIST,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    METER_SCHEDULE_TEMPLATE_TYPE,
    METER_SCHEDULE_EDIT_EXISTING_RECORDS_SELECTED,
    METER_RATE_EDIT_EXISTING_RECORDS_SELECTED;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String Key;

    private PageFlowScopeKey() {
        this.Key = "pageFlowScopeKey." + name().toLowerCase(Locale.ENGLISH);
    }

    public String getKey() {
        return Key;
    }
}
