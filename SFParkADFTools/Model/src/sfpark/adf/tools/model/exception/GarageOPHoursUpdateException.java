package sfpark.adf.tools.model.exception;

public final class GarageOPHoursUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Garage OP Hours updation.";

    public GarageOPHoursUpdateException() {
        super(MESSAGE);
    }
}
