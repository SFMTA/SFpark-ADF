package sfpark.adf.tools.model.exception;

public final class RateChangeProcessControlUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Rate Change Process Control updation.";

    public RateChangeProcessControlUpdateException() {
        super(MESSAGE);
    }
}
