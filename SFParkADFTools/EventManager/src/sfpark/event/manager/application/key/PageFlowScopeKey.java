package sfpark.event.manager.application.key;

import java.util.Locale;

public enum PageFlowScopeKey {

    ERROR_MESSAGE,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT CALENDAR KEYS

    BACK_FROM_ADD_EVENT_CALENDAR_DATE,
    ADD_EVENT_CALENDAR_DATE_CURRENT_DATE,
    ADD_EVENT_CALENDAR_DATE_DISABLED_DATES,
    ADD_EVENT_CALENDAR_DATE_LIST,

    EVENT_CALENDAR_SAVED_MESSAGE,

    EVENT_CALENDAR_LIST,
    SELECTED_EVENT_CALENDAR_FROM_LIST,

    EVENT_CALENDAR_NAME_FOR_PROPS,
    EVENT_CALENDAR_NAME_FOR_ADDITION,
    EVENT_CALENDAR_DATE_LIST;

    /*
  public static final String NEW_LDAP_REALM_DTO =
      "pageFlowScopeKey.newLDAPRealmStep1";
  public static final String NEW_LDAP_REALM_SEARCH_CONFIG_DTO =
      "pageFlowScopeKey.newLDAPRealmStep2";
  public static final String NEW_LDAP_REALM_ATTR_MAPPINGS_DTO =
      "pageFlowScopeKey.newLDAPRealmStep3";

  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // GROUP KEYS

  public static final String GROUP_SEARCH_RESULTS =
      "pageFlowScopeKey.groupSearchResults";

  public static final String SELECTED_GROUP_FROM_LIST =
      "pageFlowScopeKey.selectedGroupFromList";

  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // USER KEYS

  public static final String USER_SEARCH_RESULTS =
      "pageFlowScopeKey.userSearchResults";

  public static final String SELECTED_USER_FROM_LIST =
      "pageFlowScopeKey.selectedUserFromList";

  public static final String USER_ADD_SEARCH_RESULTS =
      "pageFlowScopeKey.userAddSearchResults";

  public static final String SELECTED_USER_FROM_ADD_USER_LIST =
      "pageFlowScopeKey.selectedUserFromAddUserList";

  public static final String USER_TO_CREATE =
      "pageFlowScopeKey.userToCreate";

  public static final String USER_TO_REMOVE =
      "pageFlowScopeKey.userToRemove";

  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // GADGET KEYS

  public static final String GADGET_LIST = "pageFlowScopeKey.gadgetList";
  public static final String SELECTED_GADGET_FROM_LIST =
      "pageFlowScopeKey.selectedGadgetFromList";

  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // FEATURE KEYS

  public static final String FEATURE_LIST = "pageFlowScopeKey.featureList";
  public static final String SELECTED_FEATURE_FROM_LIST =
      "pageFlowScopeKey.selectedFeatureFromList";

  public static final String FEATURE_FILE_LIST =
      "pageFlowScopeKey.featureFileList";
  public static final String SELECTED_FEATURE_FILE_FROM_LIST =
      "pageFlowScopeKey.selectedFeatureFileFromList";

  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // REPOSITORY KEYS

  public static final String REPOSITORY_LIST =
      "pageFlowScopeKey.repositoryList";
  public static final String SELECTED_REPOSITORY_FROM_LIST =
      "pageFlowScopeKey.selectedRepositoryFromList";

  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  // CONFERENCE KEYS

  public static final String CONFERENCE_LIST =
      "pageFlowScopeKey.conferenceList";

  public static final String SELECTED_CONFERENCE_FROM_LIST =
      "pageFlowScopeKey.selectedConferenceFromList";

   */

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
