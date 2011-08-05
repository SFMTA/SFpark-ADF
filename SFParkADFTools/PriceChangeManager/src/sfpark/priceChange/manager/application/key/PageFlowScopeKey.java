package sfpark.priceChange.manager.application.key;

import java.util.Locale;

public enum PageFlowScopeKey {

    ERROR_MESSAGE,

    INLINE_MESSAGE_TEXT,
    INLINE_MESSAGE_CLASS,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    ACTIVE_DEPLOYMENT_PROCESS_LIST,

    SELECTED_ACTIVE_DEPLOYMENT_PROCESS_FROM_LIST,

    HISTORIC_DEPLOYMENT_PROCESS_LIST,

    /*
    public static final String NEW_LDAP_REALM_DTO =
        "pageFlowScopeKey.newLDAPRealmStep1";
    public static final String NEW_LDAP_REALM_SEARCH_CONFIG_DTO =
        "pageFlowScopeKey.newLDAPRealmStep2";
    public static final String NEW_LDAP_REALM_ATTR_MAPPINGS_DTO =
        "pageFlowScopeKey.newLDAPRealmStep3";

     */

    METER_SCHEDULE_TEMPLATE_TYPE;

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
