package sfpark.adf.tools.model.exception;

import java.sql.SQLException;

import java.text.MessageFormat;

public abstract class DTOSQLException extends SQLException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE[] =
    { "Unexpected exception during SQL Operation",
      "Unexpected exception during {0}", "Unexpected exception during {0} {1}",
      "Unexpected exception during {0} {1} {2}" };

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public DTOSQLException(String Operation) {
        super(getFormattedMessage(Operation));
    }

    public DTOSQLException(String Operation, String Preposition,
                           String TableName) {
        super(getFormattedMessage(Operation, Preposition, TableName));

        this.TableName = TableName;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String TableName;

    public String getTableName() {
        return TableName;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private String getFormattedMessage(String... strings) {
        int length = strings.length;

        return MessageFormat.format(MESSAGE[length], strings);
    }
}
