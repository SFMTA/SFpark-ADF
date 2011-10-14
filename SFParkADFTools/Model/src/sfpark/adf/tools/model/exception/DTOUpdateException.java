package sfpark.adf.tools.model.exception;

public final class DTOUpdateException extends DTOSQLException {
    private static final long serialVersionUID = 1L;

    private static final String OPERATION = "updating";
    private static final String PREPOSITION = "in";

    public DTOUpdateException() {
        super(OPERATION);
    }

    public DTOUpdateException(String TableName) {
        super(OPERATION, PREPOSITION, TableName);
    }
}
