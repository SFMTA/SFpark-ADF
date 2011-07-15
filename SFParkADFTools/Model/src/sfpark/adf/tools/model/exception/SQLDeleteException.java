package sfpark.adf.tools.model.exception;

import java.sql.SQLException;

public class SQLDeleteException extends SQLException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during deletion.";


    public SQLDeleteException() {
        super(MESSAGE);
    }

    public SQLDeleteException(String string) {
        super(string);
    }
}
