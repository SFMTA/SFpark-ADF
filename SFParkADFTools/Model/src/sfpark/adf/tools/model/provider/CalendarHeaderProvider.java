package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.logging.ADFLogger;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.helper.dto.CalendarHeaderDTOStatus;
import sfpark.adf.tools.utilities.generic.StringUtil;

/**
 * Description:
 * This class is used to perform database operations
 * 
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120418-01 Mark Piller - Oracle Consulting  add selectStmt for return of generated SQL Select Statement
 * 20120418-02 Mark Piller - Oracle Consulting  added adfLogger
 * 20120418-03 Mark Piller - Oracle Consulting  add insertStmt for return of generated SQL Insert Statement
 * 20120418-04 Mark Piller - Oracle Consulting  add updateStmt for return of generated SQL Update Statement
 * 20120418-05 Mark Piller - Oracle Consulting  add deleteStmt for return of generated SQL Delete Statement
 * 20120419-01 Mark Piller - Oracle Consulting  change query to use only 2 parameters (removed date parameter)
 * 20120419-02 Mark Piller - Oracle Constulting Created this method for 2 parameters: Calendar Name and Calendar Type
 */

public class CalendarHeaderProvider {

    private static final String CLASSNAME =
        CalendarHeaderProvider.class.getName();

    // 20120418-02 setup ADF logger
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(CalendarHeaderProvider.class);

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
            adfLogger.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        return new CalendarHeaderDTOStatus(DTO);
    }

    public CalendarHeaderDTOStatus checkForCalendarNameAndType(String calendarName,
                                                               String calendarType) {
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
            adfLogger.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        return new CalendarHeaderDTOStatus(DTO);
    }

    // 20120419-01 change query to use only 2 parameters (removed date parameter)
    public List<CalendarHeaderDTO> getCalendarHeaderDTOs(String searchString,
                                                         String searchType) {
        // 20120419-01                                                 Date searchDate) {
        adfLogger.info("DEBUG  >> Parameter > searchString = " + searchString);
        adfLogger.info("DEBUG  >> Parameter >   searchType = " + searchType);
        // 20120419-01 adfLogger.info("DEBUG  >> Parameter >   searchDate = " + searchDate);

        List<CalendarHeaderDTO> calendarHeaderDTOs =
            new ArrayList<CalendarHeaderDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            // 20120419-01 
            // preparedStatement =
            //         connection.prepareStatement(getSelectStatementForCalendarNameLikeAndDate());

            // change to use method getSelectStatementForCalendarNameLikeAndType()
            preparedStatement =
                    connection.prepareStatement(getSelectStatementForCalendarNameLikeAndType());

            preparedStatement.setString(1, searchString);
            preparedStatement.setString(2, searchType);
            // 20120419-01 
            // preparedStatement.setDate(3, searchDate);

            resultSet = preparedStatement.executeQuery();
            adfLogger.info("DEBUG  >> resultSet Statement = " + resultSet.getStatement());
            int iRowCount = 0;            
            while (resultSet.next()) {
                CalendarHeaderDTO DTO = CalendarHeaderDTO.extract(resultSet);
                // adfLogger.info("DEBUG  >> Calendar row count: " + (++iRowCount));

                calendarHeaderDTOs.add(DTO);
                // adfLogger.info("DEBUG  >> calendarHeaderDTOs size = " + calendarHeaderDTOs.size());
            }

        } catch (SQLException e) {
            adfLogger.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

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
        String selectStmt = null;  // 20120418-01 added

        String Attributes =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_ID);

        adfLogger.info("DEBUG  >> selectStmt = " + selectStmt);
        selectStmt = StatementGenerator.selectStatement(Attributes,
                                                  CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where);

        // 20120418-01 replace the following with selectStmt
//        return StatementGenerator.selectStatement(Attributes,
//                                                  CalendarHeaderDTO.getDatabaseTableName(),
//                                                  Where);
        return selectStmt;
    }

    private String getSelectStatementForCalendarNameAndType() {
        String selectStmt = null;  // 20120418-01 added

        String Attributes =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_NAME);
        String string2 =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_TYPE);
        String Where = StatementGenerator.andOperator(string1, string2);

        selectStmt = StatementGenerator.selectStatement(Attributes,
                                                  CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where);

        adfLogger.info("DEBUG  >> selectStmt = " + selectStmt);
        // 20120418-01 replace the following with selectStmt
//        return StatementGenerator.selectStatement(Attributes,
//                                                  CalendarHeaderDTO.getDatabaseTableName(),
//                                                  Where);
        return selectStmt;
    }

    private String getSelectStatementForCalendarNameLikeAndDate() {
        String selectStmt = null;  // 20120418-01 added
        
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


        selectStmt = StatementGenerator.selectStatement(Attributes,
                                                  CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
        adfLogger.info("DEBUG  >> selectStmt = " + selectStmt);
        adfLogger.info("DEBUG  >> The 3 parameters are (in order):  searchString, searchType, searchDate found in getCalendarHeaderDTOs()");
        // 20120418-01 replace the following with selectStmt
//        return StatementGenerator.selectStatement(Attributes,
//                                                  CalendarHeaderDTO.getDatabaseTableName(),
//                                                  Where, OrderBy);

        return selectStmt;
    }


    // 20120419-02 Created this method for 2 parameters: Calendar Name and Calendar Type
    private String getSelectStatementForCalendarNameLikeAndType() {
        String selectStmt = null;
        
        String Attributes =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.likeOperator(CalendarHeaderDTO.CALENDAR_NAME);
        String string2 =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_TYPE);

        String RHS =
            " ( " + CalendarDetailProvider.INSTANCE.getSelectStatementForDate() +
            " ) ";

        String Where =
            StatementGenerator.andOperator(string1, string2);

        String OrderBy = CalendarHeaderDTO.CALENDAR_NAME;


        selectStmt = StatementGenerator.selectStatement(Attributes,
                                                  CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
        adfLogger.info("DEBUG  >> selectStmt = " + selectStmt);
        adfLogger.info("DEBUG  >> The 2 parameters are (in order):  searchString, searchType found in getCalendarHeaderDTOs()");
        return selectStmt;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        String insertStmt = null;  // 20120418-03 added

        String Columns =
            StringUtil.convertListToString(CalendarHeaderDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", CalendarHeaderDTO.getAttributeListForInsert().size());

        insertStmt = StatementGenerator.insertStatement(CalendarHeaderDTO.getDatabaseTableName(),
                                                  Columns, Values);
        adfLogger.info("DEBUG  >> insertStmt = " + insertStmt);
//        return StatementGenerator.insertStatement(CalendarHeaderDTO.getDatabaseTableName(),
//                                                  Columns, Values);
        return insertStmt;
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
        String updateStmt = null;  // 20120418-04 added

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(CalendarHeaderDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String RHS = "\'" + calendarID + "\'";

        String Where =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_ID,
                                               RHS);

        updateStmt = StatementGenerator.updateStatement(CalendarHeaderDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
        adfLogger.info("DEBUG  >> updateStmt = " + updateStmt);
//        return StatementGenerator.updateStatement(CalendarHeaderDTO.getDatabaseTableName(),
//                                                  SetColumnValues, Where);
        return updateStmt;
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
        String deleteStmt = null;  // 20120418-05 added

        String Where =
            StatementGenerator.equalToOperator(CalendarHeaderDTO.CALENDAR_ID);

        deleteStmt = StatementGenerator.deleteStatement(CalendarHeaderDTO.getDatabaseTableName(),
                                                  Where);
        adfLogger.info("DEBUG  >> deleteStmt = " + deleteStmt);
//        return StatementGenerator.deleteStatement(CalendarHeaderDTO.getDatabaseTableName(),
//                                                  Where);
        return deleteStmt;
    }
}
