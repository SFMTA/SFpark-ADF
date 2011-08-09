package sfpark.adf.tools.model.exception;

public final class CalendarHeaderDeleteException extends SQLDeleteException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Calendar Header deletion.";

    public CalendarHeaderDeleteException() {
        super(MESSAGE);
    }
}
