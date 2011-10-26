package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.timeBandModel.TimeBandModelDTO;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class TimeBandModelProvider {

    private static final String CLASSNAME =
        TimeBandModelProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private TimeBandModelProvider() {
        super();
    }

    public static final TimeBandModelProvider INSTANCE =
        new TimeBandModelProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<TimeBandModelDTO> getTimeBandModelDTOsFor(String meterClass,
                                                          String dateType) {
        LOGGER.entering(CLASSNAME, "getTimeBandModelDTOsFor");

        List<TimeBandModelDTO> TimeBandModelDTOs =
            new ArrayList<TimeBandModelDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, meterClass);
            preparedStatement.setString(2, dateType);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TimeBandModelDTO DO = TimeBandModelDTO.extract(resultSet);

                TimeBandModelDTOs.add(DO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);

        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getTimeBandModelDTOsFor");

        return TimeBandModelDTOs;
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
            StringUtil.convertListToString(TimeBandModelDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.equalToOperator(TimeBandModelDTO.METER_CLASS);
        String string2 =
            StatementGenerator.equalToOperator(TimeBandModelDTO.DATE_TYPE);

        String Where = StatementGenerator.andOperator(string1, string2);

        String OrderBy =
            StatementGenerator.commaOperator(TimeBandModelDTO.METER_CLASS,
                                             TimeBandModelDTO.DATE_TYPE,
                                             TimeBandModelDTO.TIME_BAND_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  TimeBandModelDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }
}
