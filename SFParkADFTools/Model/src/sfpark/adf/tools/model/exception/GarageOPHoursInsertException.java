package sfpark.adf.tools.model.exception;

public final class GarageOPHoursInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Garage OP Hours insertion.";

    public GarageOPHoursInsertException() {
        super(MESSAGE);
    }
}
