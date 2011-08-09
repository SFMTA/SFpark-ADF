package sfpark.adf.tools.model.exception;

public final class CalendarDetailInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Calendar Detail insertion.";

    public CalendarDetailInsertException() {
        super(MESSAGE);
    }
}
