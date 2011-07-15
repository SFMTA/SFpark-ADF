package sfpark.adf.tools.model.exception;

public final class OSPInventoryInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during OSP Inventory insertion.";

    public OSPInventoryInsertException() {
        super(MESSAGE);
    }
}
