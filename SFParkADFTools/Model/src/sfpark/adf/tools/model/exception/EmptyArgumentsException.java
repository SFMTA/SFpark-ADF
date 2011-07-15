package sfpark.adf.tools.model.exception;

public final class EmptyArgumentsException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Empty arguments received.";

    public EmptyArgumentsException() {
        super(MESSAGE);
    }
}
