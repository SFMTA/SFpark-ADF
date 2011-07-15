package sfpark.adf.tools.model.exception;

public final class ParkingSpaceInventoryInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Parking Space Inventory insertion.";

    public ParkingSpaceInventoryInsertException() {
        super(MESSAGE);
    }
}
