package sfpark.adf.tools.model.exception;

public final class MeterRateScheduleInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Meter Rate Schedule insertion.";

    public MeterRateScheduleInsertException() {
        super(MESSAGE);
    }
}
