package sfpark.adf.tools.model.exception;

public final class EventCalendarDeleteException extends SQLDeleteException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Event Calendar deletion.";

    public EventCalendarDeleteException() {
        super(MESSAGE);
    }
}
