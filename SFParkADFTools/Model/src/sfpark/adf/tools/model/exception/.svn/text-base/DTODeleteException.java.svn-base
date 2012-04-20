package sfpark.adf.tools.model.exception;

public final class DTODeleteException extends DTOSQLException {
    private static final long serialVersionUID = 1L;

    private static final String OPERATION = "deleting";
    private static final String PREPOSITION = "from";

    public DTODeleteException() {
        super(OPERATION);
    }

    public DTODeleteException(String TableName) {
        super(OPERATION, PREPOSITION, TableName);
    }
}
