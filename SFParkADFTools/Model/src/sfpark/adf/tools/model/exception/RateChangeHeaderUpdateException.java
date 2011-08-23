package sfpark.adf.tools.model.exception;

public final class RateChangeHeaderUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Rate Change Header updation.";

    public RateChangeHeaderUpdateException() {
        super(MESSAGE);
    }
}
