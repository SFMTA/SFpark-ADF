package sfpark.adf.tools.model.status;

public class OperationStatus {

    public static final String STYLECLASS_SUCCESSFUL = "inlineMessageSuccess";
    public static final String STYLECLASS_FAILURE = "inlineMessageFailure";

    public enum Type {
        GENERAL_SUCCESSFUL(true),
        GENERAL_FAILURE(false),

        INSERT_SUCCESSFUL(true),
        INSERT_FAILURE(false),

        UPDATE_SUCCESSFUL(true),
        UPDATE_FAILURE(false),

        DELETE_SUCCESSFUL(true),
        DELETE_FAILURE(false),

        BATCH_SUCCESSFUL(true),
        BATCH_FAILURE(false);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        private final boolean success;

        private Type(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }

        public boolean isFailure() {
            return !success;
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private Type type;
    private Exception exception;

    public OperationStatus(OperationStatus.Type type) {
        super();
        this.type = type;
    }

    public OperationStatus(OperationStatus.Type type, Exception exception) {
        super();
        this.type = type;
        this.exception = exception;
    }

    public OperationStatus.Type getType() {
        return type;
    }

    public Exception getException() {
        return exception;
    }
}
