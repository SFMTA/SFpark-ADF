package sfpark.adf.tools.helper;

import java.security.Principal;

import javax.security.auth.Subject;

import sfpark.adf.tools.utilities.constants.SecurityGroup;

import weblogic.security.Security;
import weblogic.security.principal.WLSGroupImpl;

public final class SignedInUser {

    /**
     * To avoid instantiation
     */
    private SignedInUser() {
        super();
    }

    private static final boolean USE_SECURITY_MODEL =
        DeveloperMode.DEPLOYED_ON_SERVER;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ASSET MANAGER HELPERS

    public static boolean canAddParkingSpace() {
        return hasMetersAccess();
    }

    public static boolean canEditOrReadOnlyParkingSpace() {
        return (hasMetersAccess() || hasMetersReadOnlyAccess());
    }

    public static boolean canReadOnlyParkingSpace() {
        return hasMetersReadOnlyAccess();
    }

    public static boolean canEditBulkParkingSpace() {
        return (hasMetersAccess() || hasGaragesAccess());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private static boolean hasMetersAccess() {
        if (USE_SECURITY_MODEL) {

            if (isUserInGroup(SecurityGroup.METERS_READ_ONLY)) {
                return false;
            }

            return isUserInGroup(SecurityGroup.METERS);

        }

        return true;
    }

    private static boolean hasMetersReadOnlyAccess() {
        if (USE_SECURITY_MODEL) {

            return isUserInGroup(SecurityGroup.METERS_READ_ONLY);

        }

        return true;
    }

    private static boolean hasGaragesAccess() {
        if (USE_SECURITY_MODEL) {

            return isUserInGroup(SecurityGroup.GARAGES);

        }

        return true;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

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
