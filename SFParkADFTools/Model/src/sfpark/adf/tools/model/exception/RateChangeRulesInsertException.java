package sfpark.adf.tools.model.exception;

public final class RateChangeRulesInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Rate Change Rules insertion.";

    public RateChangeRulesInsertException() {
        super(MESSAGE);
    }
}
