package sfpark.adf.tools.helper;

import weblogic.i18n.logging.NonCatalogLogger;

public final class Logger extends NonCatalogLogger {

    private static final boolean PRINT_MESSAGE =
        !DeveloperMode.DEPLOYED_ON_SERVER;

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
        trace("ENTERING :: " + className + " :: " + methodName);
    }

    /**
     * Used to log the class exiting operation
     *
     * @param className
     * @param methodName
     */
    public void exiting(String className, String methodName) {
        trace("EXITING :: " + className + " :: " + methodName);
    }

    /**
     * Used to log the class in operation
     *
     * @param className
     * @param methodName
     */
    public void in(String className, String methodName) {
        trace("IN :: " + className + " :: " + methodName);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void alert(String string) {
        super.alert(string);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void alert(String string, Throwable throwable) {
        super.alert(string, throwable);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void critical(String string) {
        super.critical(string);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void critical(String string, Throwable throwable) {
        super.critical(string, throwable);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void debug(String string) {
        super.debug(string);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void debug(String string, Throwable throwable) {
        super.debug(string, throwable);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void emergency(String string) {
        super.emergency(string);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void emergency(String string, Throwable throwable) {
        super.emergency(string, throwable);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void error(String string) {
        super.error(string);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void error(String string, Throwable throwable) {
        super.error(string, throwable);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void info(String string) {
        super.info(string);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void info(String string, Throwable throwable) {
        super.info(string, throwable);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void notice(String string) {
        super.notice(string);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void notice(String string, Throwable throwable) {
        super.notice(string, throwable);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void warning(String string) {
        super.warning(string);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }

    @Override
    public void warning(String string, Throwable throwable) {
        super.warning(string, throwable);

        if (PRINT_MESSAGE) {
            System.out.println(string);
        }
    }
}
