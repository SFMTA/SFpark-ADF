package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.meterSchedValidations.MeterSchedValidationsDO;
import sfpark.adf.tools.model.data.helper.MeterScheduleType;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class MeterScheduleValidationsProvider {

    private static final String CLASSNAME =
        MeterScheduleValidationsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "METER_SCHED_VALIDATIONS";

    private MeterScheduleValidationsProvider() {
        super();
    }

    public static final MeterScheduleValidationsProvider INSTANCE =
        new MeterScheduleValidationsProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<MeterSchedValidationsDO> getMeterSchedValidationDOs(MeterScheduleType scheduleType) {
        LOGGER.entering(CLASSNAME, "getMeterSchedValidationDOs");

        List<MeterSchedValidationsDO> meterSchedValidationsDOs =
            new ArrayList<MeterSchedValidationsDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, scheduleType.name());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MeterSchedValidationsDO meterSchedValidationsDO =
                    MeterSchedValidationsDO.extract(resultSet);

                meterSchedValidationsDOs.add(meterSchedValidationsDO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);

        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getMeterSchedValidationDOs");
        return meterSchedValidationsDOs;
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
            StringUtil.convertListToString(MeterSchedValidationsDO.getAttributeListForSelect());
        String Where = MeterSchedValidationsDO.SCHED_TYPE + " = ?";

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where);
    }
}
