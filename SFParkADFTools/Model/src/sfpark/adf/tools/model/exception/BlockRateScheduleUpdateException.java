package sfpark.adf.tools.model.exception;

public final class BlockRateScheduleUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Block Rate Schedule updation.";

    public BlockRateScheduleUpdateException() {
        super(MESSAGE);
    }
}
