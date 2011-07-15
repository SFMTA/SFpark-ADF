package sfpark.adf.tools.model.exception;

import java.sql.SQLException;

public class SQLInsertException extends SQLException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during insertion.";

    public SQLInsertException() {
        super(MESSAGE);
    }

    public SQLInsertException(String string) {
        super(string);
    }
}
