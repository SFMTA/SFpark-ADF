package sfpark.adf.tools.model.exception;

public final class GarageRatesInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Garage Rates insertion.";

    public GarageRatesInsertException() {
        super(MESSAGE);
    }
}
