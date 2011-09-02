package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;

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

    public boolean isRateChangeReferenceDeletable(String rateChgRefID) {
        LOGGER.entering(CLASSNAME, "isRateChangeReferenceDeletable");

        boolean rateChangeReferenceDeletable = (rateChgRefID != null); // TODO

        //        Connection connection = null;
        //        PreparedStatement preparedStatement = null;
        //        ResultSet resultSet = null;
        //
        //        try {
        //            connection = OracleDBConnection.getConnection();
        //
        //            preparedStatement =
        //                    connection.prepareStatement(getSelectStatement("IS_CALENDAR_DELETABLE(?)")); // TODO
        //            preparedStatement.setString(1, rateChgRefID);
        //
        //            resultSet = preparedStatement.executeQuery();
        //
        //            while (resultSet.next()) {
        //                rateChangeReferenceDeletable = (resultSet.getInt(1) == 1);
        //            }
        //
        //        } catch (SQLException e) {
        //            LOGGER.warning(ErrorMessage.SELECT_STORED_PROCEDURE.getMessage(),
        //                           e);
        //        } finally {
        //            OracleDBConnection.closeAll(resultSet, preparedStatement, connection);
        //        }

        LOGGER.exiting(CLASSNAME, "isRateChangeReferenceDeletable");

        return rateChangeReferenceDeletable;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

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
