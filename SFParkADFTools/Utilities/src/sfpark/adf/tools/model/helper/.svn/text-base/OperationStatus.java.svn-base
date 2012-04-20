package sfpark.adf.tools.model.helper;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- --------------------------------------------------------
 * 20111109-01 Mark Piller - Oracle Consulting  added Message to OperationStatus parameters.
 */
public final class OperationStatus {

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private OperationStatus(boolean Success, Exception exception, String Message) {
        super();

        this.Success = Success;
        this.exception = exception;
        this.Message = Message; // 20111109-01
    }


    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static OperationStatus success() {
        return new OperationStatus(true, null, null);
    }

    // 20111109-01
    public static OperationStatus success(String Message) {
        return new OperationStatus(true, null, Message);
    }

    public static OperationStatus failure(Exception exception) {
        return new OperationStatus(false, exception, null);
    }

    // 20111109-01
    public static OperationStatus failure(String Message) {
        return new OperationStatus(false, null, Message);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final boolean Success;
    private final Exception exception;
    private final String Message; // 20111109-01

    public boolean isSuccess() {
        return Success;
    }

    public Exception getException() {
        return exception;
    }
    
    // 20111109-01
    public String getMessage() {
        return Message;
    }
}
