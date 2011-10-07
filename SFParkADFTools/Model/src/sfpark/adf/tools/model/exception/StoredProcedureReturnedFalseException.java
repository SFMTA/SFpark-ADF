package sfpark.adf.tools.model.exception;

import java.sql.SQLException;

public final class StoredProcedureReturnedFalseException extends SQLException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Stored procedure returned false.";

    public StoredProcedureReturnedFalseException() {
        super(MESSAGE);
    }
}
