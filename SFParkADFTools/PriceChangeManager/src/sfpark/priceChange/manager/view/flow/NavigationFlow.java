package sfpark.priceChange.manager.view.flow;

public enum NavigationFlow {

    HOME(NavigationSection.COMMON,
         NavigationSection.COMMON.getJSFFFileRef("/home.jsff")),

    ERROR(NavigationSection.COMMON,
          NavigationSection.COMMON.getJSFFFileRef("/error.jsff"));

    // TODO
    /*
     * Hive Instance Pages
    Manage_Hive(NavigationSection.Manage_Application,
                NavigationSection.Manage_Application.getJSFFFileRef("/hiveApplicationProperties.jsff")),

     * Realm Pages
    Manage_Realms(NavigationSection.Manage_Realms,
                  NavigationSection.Manage_Realms.getJSFFFileRef("/realmList.jsff")),
    Realm_DB_Properties(NavigationSection.Manage_Realms,
                        NavigationSection.Manage_Realms.getJSFFFileRef("/dbRealmProperties.jsff")),

    Create_LDAP_Realm(NavigationSection.Manage_Realms,
                      NavigationSection.COMMON.getJSFFFileRef("/wizard.jsff")),
    Create_LDAP_Realm_Config(NavigationSection.Manage_Realms,
                             NavigationSection.Manage_Realms.getJSFFFileRef("/createLDAPRealm/createLDAPRealmConfig.jsff")),
    Create_LDAP_Realm_Search_Config(NavigationSection.Manage_Realms,
                                    NavigationSection.Manage_Realms.getJSFFFileRef("/createLDAPRealm/createLDAPRealmSearchConfig.jsff")),
    Create_LDAP_Realm_Attribute_Mapping(NavigationSection.Manage_Realms,
                                        NavigationSection.Manage_Realms.getJSFFFileRef("/createLDAPRealm/createLDAPRealmAttributeMapping.jsff")),


    Edit_LDAP_Realm(NavigationSection.Manage_Realms,
                    NavigationSection.Manage_Realms.getJSFFFileRef("/editLDAPRealmProperties.jsff")),
    Edit_LDAP_Realm_Config(NavigationSection.Manage_Realms,
                           NavigationSection.Manage_Realms.getJSFFFileRef("/editLDAPRealm/editLDAPRealmConfig.jsff")),
    Edit_LDAP_Realm_Search_Config(NavigationSection.Manage_Realms,
                                  NavigationSection.Manage_Realms.getJSFFFileRef("/editLDAPRealm/editLDAPRealmSearchConfig.jsff")),
    Edit_LDAP_Realm_Attribute_Mapping(NavigationSection.Manage_Realms,
                                      NavigationSection.Manage_Realms.getJSFFFileRef("/editLDAPRealm/editLDAPRealmAttributeMapping.jsff")),

     * User Pages
    Manage_Users(NavigationSection.Manage_Users,
                 NavigationSection.Manage_Users.getJSFFFileRef("/userList.jsff")),
    User_Properties(NavigationSection.Manage_Users,
                    NavigationSection.Manage_Users.getJSFFFileRef("/userProperties.jsff")),
    Add_Users(NavigationSection.Manage_Users,
              NavigationSection.Manage_Users.getJSFFFileRef("/createUser.jsff")),

     * Gadget Pages
    Manage_Gadgets(NavigationSection.Manage_Gadgets,
                   NavigationSection.Manage_Gadgets.getJSFFFileRef("/gadgetList.jsff")),
    Gadget_Properties(NavigationSection.Manage_Gadgets,
                      NavigationSection.Manage_Gadgets.getJSFFFileRef("/gadgetProperties.jsff")),
    Create_Gadget(NavigationSection.Manage_Gadgets,
                  NavigationSection.Manage_Gadgets.getJSFFFileRef("/createGadget.jsff")),

     * Feature Pages
    Manage_Features(NavigationSection.Manage_Features,
                    NavigationSection.Manage_Features.getJSFFFileRef("/featureList.jsff")),
    Feature_Properties(NavigationSection.Manage_Features,
                       NavigationSection.Manage_Features.getJSFFFileRef("/featureProperties.jsff")),
    Create_Feature(NavigationSection.Manage_Features,
                   NavigationSection.Manage_Features.getJSFFFileRef("/createFeature.jsff")),
    Feature_File_Properties(NavigationSection.Manage_Features,
                            NavigationSection.Manage_Features.getJSFFFileRef("/featureFileProperties.jsff")),

     * Repository Pages
    Manage_Repositories(NavigationSection.Manage_Repositories,
                        NavigationSection.Manage_Repositories.getJSFFFileRef("/repositoryList.jsff")),
    Repository_Properties(NavigationSection.Manage_Repositories,
                          NavigationSection.Manage_Repositories.getJSFFFileRef("/repositoryProperties.jsff")),
    Create_Repository(NavigationSection.Manage_Repositories,
                      NavigationSection.Manage_Repositories.getJSFFFileRef("/createRepository.jsff")),

     * Group Pages
    Manage_Groups(NavigationSection.Manage_Groups,
                  NavigationSection.Manage_Groups.getJSFFFileRef("/groupList.jsff")),
    Group_Properties(NavigationSection.Manage_Groups,
                     NavigationSection.Manage_Groups.getJSFFFileRef("/groupProperties.jsff")),

     * Conference Pages
    Manage_Conferences(NavigationSection.Manage_Conferences,
                       NavigationSection.Manage_Conferences.getJSFFFileRef("/conferenceList.jsff"));
 */

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final NavigationSection section;
    private final String fileName;

    private NavigationFlow(NavigationSection section, String fileName) {
        this.section = section;
        this.fileName = fileName;
    }

    public NavigationSection getSection() {
        return section;
    }

    public String getFileName() {
        return fileName;
    }
}
