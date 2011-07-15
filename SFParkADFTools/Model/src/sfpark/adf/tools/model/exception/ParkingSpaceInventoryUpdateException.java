package sfpark.adf.tools.model.exception;

public final class ParkingSpaceInventoryUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Parking Space Inventory updation.";

    public ParkingSpaceInventoryUpdateException() {
        super(MESSAGE);
    }
}
