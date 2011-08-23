package sfpark.adf.tools.model.exception;

public final class RateChangeHeaderInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Rate Change Header insertion.";

    public RateChangeHeaderInsertException() {
        super(MESSAGE);
    }
}
