package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.eventCalendar.EventCalendarDO;
import sfpark.adf.tools.model.data.dto.eventCalendar.EventCalendarDTO;
import sfpark.adf.tools.model.data.dto.eventCalendar.EventCalendarNameDTO;
import sfpark.adf.tools.model.exception.EventCalendarDeleteException;
import sfpark.adf.tools.model.helper.dO.EventCalendarDOStatus;
import sfpark.adf.tools.model.status.OperationStatus;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class EventCalendarProvider {

    private static final String CLASSNAME =
        EventCalendarProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "EVENT_CALENDAR";

    private EventCalendarProvider() {
        super();
    }

    public static final EventCalendarProvider INSTANCE =
        new EventCalendarProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public EventCalendarDOStatus checkForCalendarName(String calendarName) {
        LOGGER.entering(CLASSNAME, "checkForCalendarName");

        EventCalendarDO DO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForCalendarName(calendarName.trim()));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int numberOfDates =
                    resultSet.getInt(EventCalendarDO.NUMBER_OF_DATES);

                if (numberOfDates > 0) {
                    DO = new EventCalendarDO(calendarName, numberOfDates);
                }
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForCalendarName");

        return new EventCalendarDOStatus(DO);
    }

    public OperationStatus deleteEventCalendar(String calendarName) {
        LOGGER.entering(CLASSNAME, "deleteEventCalendar");

        OperationStatus operationStatus = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();
            connection.setAutoCommit(false);

            preparedStatement =
                    connection.prepareStatement(getDeleteStatementForCalendarName());
            preparedStatement.setString(1, calendarName);

            int executeResult = preparedStatement.executeUpdate();

            if (executeResult == 0) {
                throw new EventCalendarDeleteException();
            }

            connection.commit();

            operationStatus =
                    new OperationStatus(OperationStatus.Type.DELETE_SUCCESSFUL);

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.DELETE.getMessage(), e);

            ConnectUtil.handleRollback(connection);

            operationStatus =
                    new OperationStatus(OperationStatus.Type.DELETE_FAILURE,
                                        e);

        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "deleteEventCalendar");

        return operationStatus;
    }

    public List<EventCalendarDO> getEventCalendarDOs() {
        LOGGER.entering(CLASSNAME, "getEventCalendarDOs");

        List<EventCalendarDO> eventCalendarDOs =
            new ArrayList<EventCalendarDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForDOs());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EventCalendarDO DO = EventCalendarDO.extract(resultSet);

                eventCalendarDOs.add(DO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getEventCalendarDOs");
        return eventCalendarDOs;
    }

    public List<EventCalendarDTO> getEventCalendarDTOs(String calendarName) {
        LOGGER.entering(CLASSNAME, "getEventCalendarDTOs");

        List<EventCalendarDTO> eventCalendarDTOs =
            new ArrayList<EventCalendarDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForDTOs(calendarName));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EventCalendarDTO DTO = EventCalendarDTO.extract(resultSet);

                eventCalendarDTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getEventCalendarDTOs");
        return eventCalendarDTOs;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       EventCalendarDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(EventCalendarDTO.CALENDAR_NAME),
                                    DTO.getCalendarName());
        preparedStatement.setDate(getInsertIndexOf(EventCalendarDTO.EVENT_DATE),
                                  DTO.getEventDate());

        preparedStatement.setString(getInsertIndexOf(EventCalendarDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getInsertIndexOf(EventCalendarDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       EventCalendarNameDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForCalendarName(DTO.getOriginalCalendarName()));

        preparedStatement.setString(getUpdateIndexOf(EventCalendarNameDTO.CURRENT_CALENDAR_NAME),
                                    DTO.getCurrentCalendarName());

        preparedStatement.setString(getUpdateIndexOf(EventCalendarNameDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getUpdateIndexOf(EventCalendarNameDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    protected PreparedStatement prepareDeleteStatement(Connection connection,
                                                       EventCalendarDTO DTO) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getDeleteStatement());

        preparedStatement.setString(1, DTO.getCalendarName());
        preparedStatement.setDate(2, DTO.getEventDate());

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

    private String getSelectStatementForCalendarName(String CalendarName) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForCalendarName");

        String Attributes = EventCalendarDO.NUMBER_OF_DATES;

        String LHS =
            StatementGenerator.upperFunction(EventCalendarDO.CALENDAR_NAME);
        String RHS =
            StatementGenerator.upperFunction("\'" + CalendarName + "\'");

        String Where = StatementGenerator.likeOperator(LHS, RHS);

        String OrderBy = EventCalendarDTO.EVENT_DATE;

        LOGGER.entering(CLASSNAME, "getSelectStatementForCalendarName");
        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where, OrderBy);
    }

    private String getSelectStatementForDOs() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForDOs");

        String Attributes =
            StringUtil.convertListToString(EventCalendarDO.getAttributeListForSelect());

        String GroupBy = EventCalendarDO.CALENDAR_NAME;

        String OrderBy = EventCalendarDO.CALENDAR_NAME;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForDOs");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME, null,
                                                  OrderBy, GroupBy);
    }

    private String getSelectStatementForDTOs(String CalendarName) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForCalendarName");

        String Attributes =
            StringUtil.convertListToString(EventCalendarDTO.getAttributeListForSelect());

        String Where =
            EventCalendarDTO.CALENDAR_NAME + "=\'" + CalendarName + "\'";

        String OrderBy = EventCalendarDTO.EVENT_DATE;

        LOGGER.entering(CLASSNAME, "getSelectStatementForCalendarName");
        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where, OrderBy);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(EventCalendarDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", EventCalendarDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(TABLE_NAME, Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (EventCalendarDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForCalendarName(String OriginalCalendarName) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForCalendarName");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(EventCalendarNameDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String Where =
            EventCalendarNameDTO.ORIGINAL_CALENDAR_NAME + "=\'" + OriginalCalendarName +
            "\'";

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForCalendarName");

        return StatementGenerator.updateStatement(TABLE_NAME, SetColumnValues,
                                                  Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (EventCalendarNameDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    private String getDeleteStatement() {
        LOGGER.entering(CLASSNAME, "getDeleteStatement");

        String Where =
            EventCalendarDTO.CALENDAR_NAME + "=?" + " AND " + EventCalendarDTO.EVENT_DATE +
            "=?";

        LOGGER.exiting(CLASSNAME, "getDeleteStatement");

        return StatementGenerator.deleteStatement(TABLE_NAME, Where);
    }

    private String getDeleteStatementForCalendarName() {
        LOGGER.entering(CLASSNAME, "getDeleteStatementForCalendarName");

        String Where = EventCalendarDTO.CALENDAR_NAME + "=?";

        LOGGER.exiting(CLASSNAME, "getDeleteStatementForCalendarName");

        return StatementGenerator.deleteStatement(TABLE_NAME, Where);
    }

}
