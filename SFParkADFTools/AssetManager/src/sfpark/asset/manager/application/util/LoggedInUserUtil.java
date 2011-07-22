package sfpark.asset.manager.application.util;

import java.security.Principal;

import javax.security.auth.Subject;

import sfpark.adf.tools.helper.DeveloperMode;
import sfpark.adf.tools.utilities.constants.SecurityGroup;

import weblogic.security.Security;
import weblogic.security.principal.WLSGroupImpl;

public final class LoggedInUserUtil {

    /**
     * To avoid instantiation
     */
    private LoggedInUserUtil() {
        super();
    }

    private static final boolean USE_SECURITY_MODEL =
        DeveloperMode.DEPLOYED_ON_SERVER;

    public static boolean canAddParkingSpace() {
        return hasWriteAccess();
    }

    public static boolean canEditOrReadOnlyParkingSpace() {
        return (hasWriteAccess() || hasReadOnlyAccess());
    }

    public static boolean canReadOnlyParkingSpace() {
        return hasReadOnlyAccess();
    }

    public static boolean canEditBulkParkingSpace() {
        return hasWriteAccess();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private static boolean hasWriteAccess() {
        if (USE_SECURITY_MODEL) {

            if (isUserInGroup(SecurityGroup.METERS_READ_ONLY)) {
                return false;
            }

            return isUserInGroup(SecurityGroup.METERS);

        }

        return true;
    }

    private static boolean hasReadOnlyAccess() {
        return isUserInGroup(SecurityGroup.METERS_READ_ONLY);
    }

    private static boolean isUserInGroup(SecurityGroup securityGroup) {

        Subject subject = Security.getCurrentSubject();

        for (Principal principal : subject.getPrincipals()) {

            if (principal instanceof WLSGroupImpl) {
                if (principal.getName().equalsIgnoreCase(securityGroup.getGroupName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
