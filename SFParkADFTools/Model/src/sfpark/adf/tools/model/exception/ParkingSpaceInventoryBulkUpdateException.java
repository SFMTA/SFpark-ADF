package sfpark.adf.tools.model.exception;

public final class ParkingSpaceInventoryBulkUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Parking Space Inventory Bulk updation.";

    public ParkingSpaceInventoryBulkUpdateException() {
        super(MESSAGE);
    }
}
