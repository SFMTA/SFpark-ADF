package sfpark.adf.tools.model.exception;

public enum ExceptionType {
    UNIQUE_CONSTRAINT,

    DTO_INSERT,
    DTO_UPDATE,
    DTO_DELETE,

    DTO_SQL,

    SQL,
    GENERAL;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static ExceptionType getExceptionType(Exception exception) {

        if (exception.getMessage().indexOf("ORA-00001: unique constraint") !=
            -1) {
            return UNIQUE_CONSTRAINT;
        }

        if (exception instanceof DTOInsertException) {
            return DTO_INSERT;
        }

        if (exception instanceof DTOUpdateException) {
            return DTO_UPDATE;
        }

        if (exception instanceof DTODeleteException) {
            return DTO_DELETE;
        }

        if (exception instanceof DTOSQLException) {
            return DTO_SQL;
        }

        return GENERAL;
    }
}
