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
import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class MeterModelsProvider {

    private static final String CLASSNAME =
        MeterModelsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private MeterModelsProvider() {
        super();
    }

    public static final MeterModelsProvider INSTANCE =
        new MeterModelsProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public MeterModelsDO getNullMeterModelsDO() {
        LOGGER.entering(CLASSNAME, "getNullMeterModelDO");

        MeterModelsDO meterModelsDO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForNullDO());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                meterModelsDO = MeterModelsDO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);

        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getNullMeterModelDO");
        return meterModelsDO;
    }

    public List<MeterModelsDO> getMeterModelsDOs() {
        LOGGER.in(CLASSNAME, "getMeterModelsDOs");

        return getMeterModelsFor(false);
    }

    public List<MeterModelsDO> getMeterModelsDOsForRateChangeProcessControl() {
        LOGGER.in(CLASSNAME, "getMeterModelsDOsForRateChangeProcessControl");

        return getMeterModelsFor(true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<MeterModelsDO> getMeterModelsFor(boolean rateChangeProcessControl) {
        LOGGER.entering(CLASSNAME, "getMeterModelsFor");

        List<MeterModelsDO> meterModelsDOs = new ArrayList<MeterModelsDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement(rateChangeProcessControl));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MeterModelsDO meterModelsDO = MeterModelsDO.extract(resultSet);

                meterModelsDOs.add(meterModelsDO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);

        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getMeterModelsFor");

        return meterModelsDOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForNullDO() {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(MeterModelsDO.getAttributeListForSelect());

        String Where1 = MeterModelsDO.METER_VENDOR + " = '-' ";
        String Where2 = MeterModelsDO.METER_MODEL + " = '-' ";
        String Where3 = MeterModelsDO.METER_TYPE + " = '-' ";

        String Where = StatementGenerator.andOperator(Where1, Where2, Where3);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterModelsDO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatement(boolean rateChangeProcessControl) {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(MeterModelsDO.getAttributeListForSelect());

        String Where = "";

        if (rateChangeProcessControl) {
            String string1 =
                StatementGenerator.equalToOperator(MeterModelsDO.SMART_METER_FLAG,
                                                   "\'Y\'");
            String string2 =
                StatementGenerator.inOperator(MeterModelsDO.METER_VENDOR,
                                              "\'Duncan\', \'IPS\'");

            Where = StatementGenerator.andOperator(string1, string2);

        } else {
            Where = MeterModelsDO.METER_VENDOR + " <> '-' ";

        }

        String OrderBy =
            StatementGenerator.commaOperator(MeterModelsDO.METER_TYPE +
                                             " DESC ",
                                             MeterModelsDO.METER_VENDOR,
                                             MeterModelsDO.METER_MODEL);


        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterModelsDO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }
}
