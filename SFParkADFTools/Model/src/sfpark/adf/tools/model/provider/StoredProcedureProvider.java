package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.helper.OperationStatus;

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

    /**
     * @deprecated
     * @param calendarID
     * @return
     */
    public boolean isCalendarDeletable(String calendarID) {
        LOGGER.entering(CLASSNAME, "isCalendarDeletable");

        boolean calendarDeletable = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement("IS_CALENDAR_DELETABLE(?)"));
            preparedStatement.setString(1, calendarID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                calendarDeletable = (resultSet.getInt(1) == 1);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_STORED_PROCEDURE.getMessage(),
                           e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "isCalendarDeletable");

        return calendarDeletable;
    }

    //    public OperationStatus isCalendarDeletable(String calendarID) {
    //        LOGGER.in(CLASSNAME, "isCalendarDeletable");
    //
    //        return callStoredProcedure(StoredProcedure.IS_CALENDAR_DELETABLE,
    //                                   calendarID);
    //    }

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

    private OperationStatus callStoredProcedure(StoredProcedure procedure,
                                                Object... args) {
        LOGGER.entering(CLASSNAME, "callStoredProcedure");

        OperationStatus operationStatus = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    getPreparedStatement(connection, procedure, args);

            resultSet = preparedStatement.executeQuery();

            boolean result = false;

            while (resultSet.next()) {
                result = (resultSet.getInt(1) == 1);
            }

            operationStatus =
                    (result) ? OperationStatus.success() : OperationStatus.failure(new SQLException("Stored procedure returned false."));

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_STORED_PROCEDURE.getMessage(),
                           e);

            operationStatus = OperationStatus.failure(e);

        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "callStoredProcedure");

        return operationStatus;
    }

    private PreparedStatement getPreparedStatement(Connection connection,
                                                   StoredProcedure procedure,
                                                   Object... args) throws SQLException {
        LOGGER.entering(CLASSNAME, "getPreparedStatement");

        PreparedStatement preparedStatement =
            connection.prepareStatement(getSelectStatement(procedure.getName()));

        for (int i = 1; i <= procedure.getNumOfArguments(); i++) {
            preparedStatement.setString(i, (String)args[i - 1]);
        }

        LOGGER.exiting(CLASSNAME, "getPreparedStatement");

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
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes = procedure;

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes, "DUAL");
    }
}
