package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.helper.dto.CalendarHeaderDTOStatus;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class CalendarHeaderProvider {

    private static final String CLASSNAME =
        CalendarHeaderProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private CalendarHeaderProvider() {
        super();
    }

    public static final CalendarHeaderProvider INSTANCE =
        new CalendarHeaderProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public CalendarHeaderDTOStatus checkForCalendarID(String calendarID) {
        LOGGER.entering(CLASSNAME, "checkForCalendarID");

        CalendarHeaderDTO DTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, calendarID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = CalendarHeaderDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForCalendarID");

        return new CalendarHeaderDTOStatus(DTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       CalendarHeaderDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(CalendarHeaderDTO.CALENDAR_TYPE),
                                    DTO.getCalendarType().getStringForTable());
        preparedStatement.setString(getInsertIndexOf(CalendarHeaderDTO.CALENDAR_NAME),
                                    DTO.getCalendarName());
        preparedStatement.setString(getInsertIndexOf(CalendarHeaderDTO.STATUS),
                                    DTO.getStatus().getStringForTable());

        preparedStatement.setString(getInsertIndexOf(CalendarHeaderDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getInsertIndexOf(CalendarHeaderDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       CalendarHeaderDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {
        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForCalendarID(DTO.getCalendarID()));

        preparedStatement.setString(getUpdateIndexOf(CalendarHeaderDTO.CALENDAR_TYPE),
                                    DTO.getCalendarType().getStringForTable());
        preparedStatement.setString(getUpdateIndexOf(CalendarHeaderDTO.CALENDAR_NAME),
                                    DTO.getCalendarName());
        preparedStatement.setString(getUpdateIndexOf(CalendarHeaderDTO.STATUS),
                                    DTO.getStatus().getStringForTable());

        preparedStatement.setString(getUpdateIndexOf(CalendarHeaderDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getUpdateIndexOf(CalendarHeaderDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    protected PreparedStatement prepareDeleteStatement(Connection connection,
                                                       CalendarHeaderDTO DTO) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getDeleteStatement());

        preparedStatement.setString(1, DTO.getCalendarID());

        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatement() {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", CalendarHeaderDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(CalendarHeaderDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (CalendarHeaderDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForCalendarID(String calendarID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForCalendarID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(CalendarHeaderDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String RHS = "\'" + calendarID + "\'";

        String Where =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_ID,
                                               RHS);

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForCalendarID");

        return StatementGenerator.updateStatement(CalendarHeaderDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (CalendarHeaderDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    private String getDeleteStatement() {
        LOGGER.entering(CLASSNAME, "getDeleteStatement");

        String Where =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_ID);

        LOGGER.exiting(CLASSNAME, "getDeleteStatement");

        return StatementGenerator.deleteStatement(CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where);
    }
}
