package sfpark.adf.tools.model.helper;

public final class OperationStatus {

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private OperationStatus(boolean Success, Exception exception) {
        super();

        this.Success = Success;
        this.exception = exception;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static OperationStatus success() {
        return new OperationStatus(true, null);
    }

    public static OperationStatus failure(Exception exception) {
        return new OperationStatus(false, exception);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final boolean Success;
    private final Exception exception;

    public boolean isSuccess() {
        return Success;
    }

    public Exception getException() {
        return exception;
    }
}
