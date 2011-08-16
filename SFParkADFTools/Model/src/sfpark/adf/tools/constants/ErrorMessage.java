package sfpark.adf.tools.constants;

public enum ErrorMessage {
    DELETE("Could not delete. "),

    SELECT_STORED_PROCEDURE("Could not call stored procedure. "),

    SELECT_MAX_DATE("Could not perform select query to retrieve maximum Date. "),

    SELECT_DTO("Could not perform select query to retrieve the DTO. "),
    SELECT_DTO_LIST("Could not perform select query to retrieve the DTO List. "),

    SELECT_DO("Could not perform select query to retrieve the DO. "),
    SELECT_DO_LIST("Could not perform select query to retrieve the DO List. ");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
