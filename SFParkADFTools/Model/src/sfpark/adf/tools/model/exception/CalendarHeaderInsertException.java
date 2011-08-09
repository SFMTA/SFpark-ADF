package sfpark.adf.tools.model.exception;

public final class CalendarHeaderInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Calendar Header insertion.";

    public CalendarHeaderInsertException() {
        super(MESSAGE);
    }
}
