package sfpark.adf.tools.model.exception;

public final class RateChangeRulesUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Rate Change Rules updation.";

    public RateChangeRulesUpdateException() {
        super(MESSAGE);
    }
}
