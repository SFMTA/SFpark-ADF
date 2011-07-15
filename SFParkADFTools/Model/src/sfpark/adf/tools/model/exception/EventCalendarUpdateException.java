package sfpark.adf.tools.model.exception;

public final class EventCalendarUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Event Calendar updation.";

    public EventCalendarUpdateException() {
        super(MESSAGE);
    }
}
