package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.helper.dto.CalendarHeaderDTOStatus;
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
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForCalendarID());
            preparedStatement.setString(1, calendarID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = CalendarHeaderDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForCalendarID");

        return new CalendarHeaderDTOStatus(DTO);
    }

    public CalendarHeaderDTOStatus checkForCalendarNameAndType(String calendarName,
                                                               String calendarType) {
        LOGGER.entering(CLASSNAME, "checkForCalendarNameAndType");

        CalendarHeaderDTO DTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForCalendarNameAndType());
            preparedStatement.setString(1, calendarName);
            preparedStatement.setString(2, calendarType);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = CalendarHeaderDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForCalendarNameAndType");

        return new CalendarHeaderDTOStatus(DTO);
    }

    public List<CalendarHeaderDTO> getCalendarHeaderDTOs(String searchString,
                                                         String searchType,
                                                         Date searchDate) {
        LOGGER.entering(CLASSNAME, "getCalendarHeaderDTOs");

        List<CalendarHeaderDTO> calendarHeaderDTOs =
            new ArrayList<CalendarHeaderDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForCalendarNameLikeAndDate());
            preparedStatement.setString(1, searchString);
            preparedStatement.setString(2, searchType);
            preparedStatement.setDate(3, searchDate);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CalendarHeaderDTO DTO = CalendarHeaderDTO.extract(resultSet);

                calendarHeaderDTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getCalendarHeaderDTOs");

        return calendarHeaderDTOs;
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
                                    DTO.getCalendarType());
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
                                    DTO.getCalendarType());
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

    private String getSelectStatementForCalendarID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForCalendarID");

        String Attributes =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForCalendarID");

        return StatementGenerator.selectStatement(Attributes,
                                                  CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatementForCalendarNameAndType() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForCalendarNameAndType");

        String Attributes =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_NAME);
        String string2 =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_TYPE);
        String Where = StatementGenerator.andOperator(string1, string2);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForCalendarNameAndType");

        return StatementGenerator.selectStatement(Attributes,
                                                  CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatementForCalendarNameLikeAndDate() {
        LOGGER.entering(CLASSNAME,
                        "getSelectStatementForCalendarNameLikeAndDate");

        String Attributes =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.likeOperator(CalendarHeaderDTO.CALENDAR_NAME);
        String string2 =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_TYPE);

        String RHS =
            " ( " + CalendarDetailProvider.INSTANCE.getSelectStatementForDate() +
            " ) ";
        String string3 = StatementGenerator.greaterThanOperator("?", RHS);

        String Where =
            StatementGenerator.andOperator(string1, string2, string3);

        String OrderBy = CalendarHeaderDTO.CALENDAR_NAME;

        LOGGER.exiting(CLASSNAME,
                       "getSelectStatementForCalendarNameLikeAndDate");

        LOGGER.debug("Calendar Header ::: " +
                     StatementGenerator.selectStatement(Attributes,
                                                        CalendarHeaderDTO.getDatabaseTableName(),
                                                        Where, OrderBy));

        return StatementGenerator.selectStatement(Attributes,
                                                  CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    /*
  SELECT CALENDAR_ID, CALENDAR_NAME
  FROM SFPARK_ODS.CALENDAR_HEADER
  WHERE CALENDAR_NAME LIKE '%'
  AND CALENDAR_TYPE = 'RateChg'
  AND to_date('2011-08-24', 'yyyy-mm-dd') > (
                                              SELECT MAX (DATE_DT)
                                              FROM SFPARK_ODS.CALENDAR_DETAIL
                                              WHERE CALENDAR_HEADER.CALENDAR_ID = CALENDAR_ID
                                            )
  ORDER BY CALENDAR_NAME;
   */

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
