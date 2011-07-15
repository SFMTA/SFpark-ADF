package sfpark.adf.tools.model.exception;

import java.sql.SQLException;

public class SQLUpdateException extends SQLException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during updation.";

    public SQLUpdateException() {
        super(MESSAGE);
    }

    public SQLUpdateException(String string) {
        super(string);
    }
}
