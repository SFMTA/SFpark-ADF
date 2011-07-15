package sfpark.event.manager.application.key;

import java.util.Locale;

public enum SessionScopeKey {

    NAVIGATION_INFO;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String Key;

    private SessionScopeKey() {
        this.Key = "sessionScopeKey." + name().toLowerCase(Locale.ENGLISH);
    }

    public String getKey() {
        return Key;
    }

    /*
    public static final String SUB_HEADER_TEXT =
        "sessionScopeKey.subHeaderText";

    // REALM KEYS

    public static final String REALM_SAVED_MESSAGE =
        "sessionScopeKey.realmSavedMessage";

    public static final String DB_REALM_ID_FOR_PROPS =
        "sessionScopeKey.dbRealmIDForProps";
    public static final String LDAP_REALM_ID_FOR_PROPS =
        "sessionScopeKey.ldapRealmIDForProps";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // USER KEYS

    public static final String USER_SAVED_MESSAGE =
        "sessionScopeKey.userSavedMessage";

    public static final String USER_ID_FOR_PROPS =
        "sessionScopeKey.userIDForProps";

    public static final String USER_DELETED_MESSAGE =
        "sessionScopeKey.userDeletedMessage";

    public static final String USER_DELETED_TYPE =
        "sessionScopeKey.userDeletedType";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // GADGET KEYS

    public static final String GADGET_SAVED_MESSAGE =
        "sessionScopeKey.gadgetSavedMessage";

    public static final String GADGET_ID_FOR_PROPS =
        "sessionScopeKey.gadgetIDForProps";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // FEATURE KEYS

    public static final String FEATURE_SAVED_MESSAGE =
        "sessionScopeKey.featureSavedMessage";

    public static final String FEATURE_ID_FOR_PROPS =
        "sessionScopeKey.featureIDForProps";

    public static final String FEATURE_FILE_ID_FOR_PROPS =
        "sessionScopeKey.featureFileIDForProps";
    public static final String FEATURE_NAME_FOR_FILE_PROPS =
        "sessionScopeKey.featureNameForFileProps";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // REPOSITORY KEYS

    public static final String REPOSITORY_SAVED_MESSAGE =
        "sessionScopeKey.repositorySavedMessage";

    public static final String REPOSITORY_ID_FOR_PROPS =
        "sessionScopeKey.repositoryIDForProps";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // GROUP KEYS

    public static final String GROUP_ID_FOR_PROPS =
        "sessionScopeKey.groupIDForProps";

     */

}
