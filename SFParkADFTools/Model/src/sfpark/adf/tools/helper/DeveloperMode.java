package sfpark.adf.tools.helper;

public final class DeveloperMode {

    /**
     * TODO: During development phase, set this to false.
     * TODO: Before deployment on Weblogic Server, set this to true
     */
    public static final boolean DEPLOYED_ON_SERVER = true;

    /**
     * To avoid instantiation
     */
    private DeveloperMode() {
        super();
    }
}
