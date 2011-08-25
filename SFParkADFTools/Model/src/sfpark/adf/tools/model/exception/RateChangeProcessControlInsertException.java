package sfpark.adf.tools.model.exception;

public final class RateChangeProcessControlInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Rate Change Process Control insertion.";

    public RateChangeProcessControlInsertException() {
        super(MESSAGE);
    }
}
