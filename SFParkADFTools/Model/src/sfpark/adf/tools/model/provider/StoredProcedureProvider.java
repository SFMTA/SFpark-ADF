package sfpark.adf.tools.model.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.helper.OperationStatus;

/**
 * Description:
 * This class calls an ODI function that generates Parking Rates.  The call to
 * a function is similar to that of a stored procedure, thus the name of this class.
 * 
 * 
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- --------------------------------------------------------
 * 20111109-01 Mark Piller - Oracle Consulting  added messageFromGenerateRatesFunction
 * 20111109-02 Mark Piller - Oracle Consulting  added logic to receive message from ODI function
 * 20111109-03 Mark Piller - Oracle Consulting  added logic to evaluate message returned from ODI function.
 */
public final class StoredProcedureProvider {

    private static final String CLASSNAME =
        StoredProcedureProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private StoredProcedureProvider() {
        super();
    }

    public static final StoredProcedureProvider INSTANCE =
        new StoredProcedureProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public OperationStatus isCalendarDeletable(String calendarID) {
        LOGGER.in(CLASSNAME, "isCalendarDeletable");

        return callStoredProcedure(StoredProcedure.IS_CALENDAR_DELETABLE,
                                   calendarID);
    }

    public OperationStatus generateRateChange(String rateChgRefID,
                                              String lastUpdatedUser) {
        LOGGER.in(CLASSNAME, "generateRateChange");

        return callStoredProcedure(StoredProcedure.GENERATE_RATE_CHG,
                                   rateChgRefID, lastUpdatedUser);
    }

    public OperationStatus deleteRateChange(String rateChgRefID) {
        LOGGER.in(CLASSNAME, "deleteRateChange");

        return callStoredProcedure(StoredProcedure.DELETE_RATE_CHG,
                                   rateChgRefID);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    /*
     * This actually is calling a Function in ODI - not a Stored Procedure.
     * The logic still works the same.
     */
    private OperationStatus callStoredProcedure(StoredProcedure procedure,
                                                Object... args) {
        LOGGER.entering(CLASSNAME, "callStoredProcedure");

        // 20111109-01
        String messageFromGenerateRatesFunction = null;
        OperationStatus operationStatus = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();
            LOGGER.debug("Calling getPreparedStatement()");
            preparedStatement = getPreparedStatement(connection, procedure, args);

            LOGGER.debug("Executing executeQuery()");
            resultSet = preparedStatement.executeQuery();

            LOGGER.debug("Begin to pass through resultSet");
            while (resultSet.next()) {
                LOGGER.debug("Fetching resultSet from stored procedure");
                // 20111109-02
                // Get the message returned from the stored procedure.
                Object colObject = resultSet.getObject(1);
                messageFromGenerateRatesFunction = colObject.toString();
                LOGGER.debug("Results from stored procedure: " + messageFromGenerateRatesFunction);
            }

            // 20111109-03
            // evaluate the returned message from the stored procedure
            // determine if it is a success or failure
            // If the string "FAIL" is not found then there is success
            LOGGER.debug("Setting operation status to success");
            if (messageFromGenerateRatesFunction.toUpperCase().indexOf("FAIL") == -1) {
                operationStatus = OperationStatus.success(messageFromGenerateRatesFunction);
            } else {
                operationStatus = OperationStatus.failure(messageFromGenerateRatesFunction);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_STORED_PROCEDURE.getMessage(), e);
            operationStatus = OperationStatus.failure(e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "callStoredProcedure");

        return operationStatus;
    }

    private PreparedStatement getPreparedStatement(Connection connection,
                                                   StoredProcedure procedure,
                                                   Object... args) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getSelectStatement(procedure.getName()));

        for (int i = 1; i <= procedure.getNumOfArguments(); i++) {
            preparedStatement.setString(i, (String)args[i - 1]);
        }

        return preparedStatement;
    }

    private enum StoredProcedure {
        IS_CALENDAR_DELETABLE("IS_CALENDAR_DELETABLE (?)", 1),

        DELETE_RATE_CHG("DELETE_RATE_CHG (?)", 1),

        GENERATE_RATE_CHG("GENERATE_RATE_CHG (?, ?)", 2);

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++

        private final String Name;
        private final int NumOfArguments;

        private StoredProcedure(String Name, int NumOfArguments) {
            this.Name = Name;
            this.NumOfArguments = NumOfArguments;
        }

        public String getName() {
            return Name;
        }

        public int getNumOfArguments() {
            return NumOfArguments;
        }
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatement(String procedure) {
        String Attributes = procedure;
        return StatementGenerator.selectStatement(Attributes, "DUAL");
    }
}
