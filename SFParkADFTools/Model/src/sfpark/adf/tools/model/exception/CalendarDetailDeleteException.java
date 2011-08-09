package sfpark.adf.tools.model.exception;

public final class CalendarDetailDeleteException extends SQLDeleteException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Calendar Detail deletion.";

    public CalendarDetailDeleteException() {
        super(MESSAGE);
    }
}
