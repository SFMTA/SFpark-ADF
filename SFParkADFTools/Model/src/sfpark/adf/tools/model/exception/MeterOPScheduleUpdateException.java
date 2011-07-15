package sfpark.adf.tools.model.exception;

public final class MeterOPScheduleUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Meter OP Schedule updation.";

    public MeterOPScheduleUpdateException() {
        super(MESSAGE);
    }
}
