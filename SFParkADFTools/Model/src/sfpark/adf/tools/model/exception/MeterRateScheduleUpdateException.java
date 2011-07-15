package sfpark.adf.tools.model.exception;

public final class MeterRateScheduleUpdateException extends SQLUpdateException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Meter Rate Schedule updation.";

    public MeterRateScheduleUpdateException() {
        super(MESSAGE);
    }
}
