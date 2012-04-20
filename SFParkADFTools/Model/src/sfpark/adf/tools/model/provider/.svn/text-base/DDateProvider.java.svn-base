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
import sfpark.adf.tools.model.data.dO.dTable.DDateDO;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class DDateProvider {

    private static final String CLASSNAME = DDateProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private DDateProvider() {
        super();
    }

    public static final DDateProvider INSTANCE = new DDateProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<DDateDO> getDDateDOs(String calendarID) {
        LOGGER.entering(CLASSNAME, "getDDateDOs");

        List<DDateDO> dDateDOs = new ArrayList<DDateDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, calendarID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DDateDO DO = DDateDO.extract(resultSet);

                dDateDOs.add(DO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getDDateDOs");

        return dDateDOs;
    }

    public List<DDateDO> getDDateDOs(List<Date> chosenDatesList) {
        LOGGER.entering(CLASSNAME, "getDDateDOs");

        List<DDateDO> dDateDOs = new ArrayList<DDateDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement(chosenDatesList.size()));
            for (int i = 0; i < chosenDatesList.size(); i++) {
                preparedStatement.setDate((i + 1), chosenDatesList.get(i));
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DDateDO DO = DDateDO.extract(resultSet);

                dDateDOs.add(DO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getDDateDOs");

        return dDateDOs;
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
            StringUtil.convertListToString(DDateDO.getAttributeListForSelect());

        String Where =
            StatementGenerator.inOperator(DDateDO.DATE_DT, CalendarDetailProvider.INSTANCE.getSelectStatement());

        String OrderBy = DDateDO.DATE_DT;

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  DDateDO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    private String getSelectStatement(int size) {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(DDateDO.getAttributeListForSelect());

        String Where =
            StatementGenerator.inOperator(DDateDO.DATE_DT, StringUtil.generateStringWithRepetition("?",
                                                                                                   size));

        String OrderBy = DDateDO.DATE_DT;

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  DDateDO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }
}
