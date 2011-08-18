package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.calendar.CalendarDetailDTO;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class CalendarDetailProvider {

    private static final String CLASSNAME =
        CalendarDetailProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private CalendarDetailProvider() {
        super();
    }

    public static final CalendarDetailProvider INSTANCE =
        new CalendarDetailProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<CalendarDetailDTO> getCalendarDetailDTOs(String calendarID) {
        LOGGER.entering(CLASSNAME, "getCalendarDetailDTOs");

        List<CalendarDetailDTO> calendarDetailDTOs =
            new ArrayList<CalendarDetailDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectAllStatement());
            preparedStatement.setString(1, calendarID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CalendarDetailDTO DTO = CalendarDetailDTO.extract(resultSet);

                calendarDetailDTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getCalendarDetailDTOs");

        return calendarDetailDTOs;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected String getSelectStatement() {

        String Attributes = CalendarDetailDTO.DATE_DT;

        String Where =
            StatementGenerator.equalToOperator(CalendarDetailDTO.CALENDAR_ID);

        return StatementGenerator.selectStatement(Attributes,
                                                  CalendarDetailDTO.getDatabaseTableName(),
                                                  Where);
    }

    protected String getSelectStatementForDate() {

        String Attributes =
            StatementGenerator.distinctFunction(CalendarDetailDTO.CALENDAR_ID);

        String Where =
            StatementGenerator.lessThanOrEqualToOperator(CalendarDetailDTO.DATE_DT);

        return StatementGenerator.selectStatement(Attributes,
                                                  CalendarDetailDTO.getDatabaseTableName(),
                                                  Where);
    }

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       CalendarDetailDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(CalendarDetailDTO.CALENDAR_ID),
                                    DTO.getCalendarID());
        preparedStatement.setDate(getInsertIndexOf(CalendarDetailDTO.DATE_DT),
                                  DTO.getDateDT());

        preparedStatement.setString(getInsertIndexOf(CalendarDetailDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getInsertIndexOf(CalendarDetailDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    protected PreparedStatement prepareDeleteStatement(Connection connection,
                                                       CalendarDetailDTO DTO) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getDeleteStatement());

        preparedStatement.setString(1, DTO.getCalendarID());
        preparedStatement.setDate(2, DTO.getDateDT());

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

    private String getSelectAllStatement() {
        LOGGER.entering(CLASSNAME, "getSelectAllStatement");

        String Attributes =
            StringUtil.convertListToString(CalendarDetailDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(CalendarDetailDTO.CALENDAR_ID);

        String OrderBy = CalendarDetailDTO.DATE_DT;

        LOGGER.exiting(CLASSNAME, "getSelectAllStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  CalendarDetailDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(CalendarDetailDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", CalendarDetailDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(CalendarDetailDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (CalendarDetailDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    private String getDeleteStatement() {
        LOGGER.entering(CLASSNAME, "getDeleteStatement");

        String string1 =
            StatementGenerator.equalToOperator(CalendarDetailDTO.CALENDAR_ID);
        String string2 =
            StatementGenerator.equalToOperator(CalendarDetailDTO.DATE_DT);

        String Where = StatementGenerator.andOperator(string1, string2);

        LOGGER.exiting(CLASSNAME, "getDeleteStatement");

        return StatementGenerator.deleteStatement(CalendarDetailDTO.getDatabaseTableName(),
                                                  Where);
    }
}
