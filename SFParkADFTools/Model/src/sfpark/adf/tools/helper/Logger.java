package sfpark.adf.tools.helper;

import weblogic.i18n.logging.NonCatalogLogger;

public final class Logger extends NonCatalogLogger {

    private Logger(String string) {
        super(string);
    }

    /**
     * Used to get the instance for a particular class
     *
     * @param className
     * @return
     */
    public static Logger getLogger(String className) {
        return new Logger(className);
    }

    /**
     * Used to log the class entering operation
     *
     * @param className
     * @param methodName
     */
    public void entering(String className, String methodName) {
        this.trace("ENTERING :: " + className + " :: " + methodName);
    }

    /**
     * Used to log the class exiting operation
     *
     * @param className
     * @param methodName
     */
    public void exiting(String className, String methodName) {
        this.trace("EXITING :: " + className + " :: " + methodName);
    }

    /**
     * Used to log the class in operation
     *
     * @param className
     * @param methodName
     */
    public void in(String className, String methodName) {
        this.trace("IN :: " + className + " :: " + methodName);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // TODO: REMOVE LATER

    @Override
    public void alert(String string) {
        System.out.println(string);
        super.alert(string);
    }

    @Override
    public void alert(String string, Throwable throwable) {
        System.out.println(string);
        super.alert(string, throwable);
    }

    @Override
    public void critical(String string) {
        System.out.println(string);
        super.critical(string);
    }

    @Override
    public void critical(String string, Throwable throwable) {
        System.out.println(string);
        super.critical(string, throwable);
    }

    @Override
    public void debug(String string) {
        System.out.println(string);
        super.debug(string);
    }

    @Override
    public void debug(String string, Throwable throwable) {
        System.out.println(string);
        super.debug(string, throwable);
    }

    @Override
    public void emergency(String string) {
        System.out.println(string);
        super.emergency(string);
    }

    @Override
    public void emergency(String string, Throwable throwable) {
        System.out.println(string);
        super.emergency(string, throwable);
    }

    @Override
    public void error(String string) {
        System.out.println(string);
        super.error(string);
    }

    @Override
    public void error(String string, Throwable throwable) {
        System.out.println(string);
        super.error(string, throwable);
    }

    @Override
    public void info(String string) {
        System.out.println(string);
        super.info(string);
    }

    @Override
    public void info(String string, Throwable throwable) {
        System.out.println(string);
        super.info(string, throwable);
    }

    @Override
    public void notice(String string) {
        System.out.println(string);
        super.notice(string);
    }

    @Override
    public void notice(String string, Throwable throwable) {
        System.out.println(string);
        super.notice(string, throwable);
    }

    @Override
    public void warning(String string) {
        System.out.println(string);
        super.warning(string);
    }

    @Override
    public void warning(String string, Throwable throwable) {
        System.out.println(string);
        super.warning(string, throwable);
    }
}
