package sfpark.adf.tools.model.exception;

public final class EventCalendarInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Event Calendar insertion.";

    public EventCalendarInsertException() {
        super(MESSAGE);
    }
}
