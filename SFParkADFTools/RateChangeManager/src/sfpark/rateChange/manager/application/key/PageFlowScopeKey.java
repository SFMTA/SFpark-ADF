package sfpark.rateChange.manager.application.key;

import java.util.Locale;

public enum PageFlowScopeKey {
    ERROR_TITLE,
    ERROR_MESSAGE,

    INLINE_MESSAGE_TEXT,
    INLINE_MESSAGE_CLASS,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    BLOCK_ID,
    BLOCK_TIME_BANDS_DTO_LIST,

    BLOCK_TIME_BANDS_TO,
    TIME_BAND_MODEL_DO_LIST,

    DELETABLE_TIME_BANDS_LIST,
    TIMEBAND_PICKER_CHOSEN_LIST,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    RATE_CHANGE_RULE_TYPE,
    RATE_CHANGE_RULES_DTO_LIST,
    THRESHOLD_PICKER_CHOSEN_LIST,

    RATE_CHANGE_HEADER_DTO,
    RATE_CHANGE_PROCESS_CONTROL_DTO,

    BLOCK_RATE_SCHED_DTO,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    NULL_NULL;

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
