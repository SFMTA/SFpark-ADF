package sfpark.adf.tools.model.exception;

public final class DTOInsertException extends DTOSQLException {
    private static final long serialVersionUID = 1L;

    private static final String OPERATION = "inserting";
    private static final String PREPOSITION = "into";

    public DTOInsertException() {
        super(OPERATION);
    }

    public DTOInsertException(String TableName) {
        super(OPERATION, PREPOSITION, TableName);
    }
}
