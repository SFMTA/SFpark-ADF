package sfpark.adf.tools.model.exception;

public final class MeterOPScheduleInsertException extends SQLInsertException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE =
        "Unexpected Exception during Meter OP Schedule insertion.";

    public MeterOPScheduleInsertException() {
        super(MESSAGE);
    }
}
