package sfpark.adf.tools.model.exception;

public final class OSPInventoryUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during OSP Inventory updation.";

    public OSPInventoryUpdateException() {
        super(MESSAGE);
    }
}
