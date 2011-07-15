package sfpark.adf.tools.model.exception;

public final class GarageRatesUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Garage Rates updation.";

    public GarageRatesUpdateException() {
        super(MESSAGE);
    }
}
