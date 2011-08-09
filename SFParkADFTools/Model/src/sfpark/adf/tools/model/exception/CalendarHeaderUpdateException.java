package sfpark.adf.tools.model.exception;

public final class CalendarHeaderUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Calendar Header updation.";

    public CalendarHeaderUpdateException() {
        super(MESSAGE);
    }
}
