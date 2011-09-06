package sfpark.adf.tools.model.exception;

public final class RateChangeProcessControlDeleteException extends SQLDeleteException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Rate Change Process Control deletion.";

    public RateChangeProcessControlDeleteException() {
        super(MESSAGE);
    }
}
