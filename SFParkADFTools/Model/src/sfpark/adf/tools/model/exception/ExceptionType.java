package sfpark.adf.tools.model.exception;

import java.sql.SQLException;

public enum ExceptionType {
    UNIQUE_CONTRAINT,

    SQL,
    GENERAL;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static ExceptionType getExceptionType(Exception exception) {

        if (exception.getMessage().indexOf("ORA-00001: unique constraint") !=
            -1) {
            return UNIQUE_CONTRAINT;
        }

        if (exception instanceof SQLException) {
            return SQL;
        }

        return GENERAL;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /*
     * public static ExceptionType getExceptionType(Exception exception) {
     *
     *      if (exception.getMessage().indexOf("ORA-00001: unique constraint") != -1) {
     *            return UNIQUE_CONTRAINT;
     *      }
     *
     *      for (ExceptionType exceptionType : ExceptionType.values()) {
     *
     *            switch (exceptionType) {
     *
     *            case UNIQUE_CONSTRAINT:
     *                  // Do Nothing
     *                  break;
     *
     *            default:
     *                  if (exception.getClass().isAssignableFrom(exceptionType.getExceptionClass())) {
     *                        return exceptionType;
     *                  }
     *                  break;
     *            }
     *      }
     *
     *      return ExceptionType.GENERAL;
     * }
     *
     *
     * private final Class exceptionClass;
     *
     * private ExceptionType(Class exceptionClass) {
     *      this.exceptionClass = exceptionClass;
     * }
     *
     * private Class getExceptionClass() {
     *      return exceptionClass;
     * }
     */
}
