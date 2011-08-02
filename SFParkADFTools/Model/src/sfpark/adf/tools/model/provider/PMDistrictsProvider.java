package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.pmDistricts.PMDistrictsDO;
import sfpark.adf.tools.model.data.dto.blockRateSchedule.BlockRateScheduleDTO;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class PMDistrictsProvider {

    private static final String CLASSNAME =
        PMDistrictsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private PMDistrictsProvider() {
        super();
    }

    public static final PMDistrictsProvider INSTANCE =
        new PMDistrictsProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<PMDistrictsDO> getPMDistrictsFor(String RateChangeReferenceID) {
        LOGGER.entering(CLASSNAME, "getPMDistrictsFor");

        List<PMDistrictsDO> pmDistrictsDOs = new ArrayList<PMDistrictsDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForRateChangeReferenceID());
            preparedStatement.setString(1, RateChangeReferenceID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PMDistrictsDO DO = PMDistrictsDO.extract(resultSet);

                pmDistrictsDOs.add(DO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getPMDistrictsFor");

        return pmDistrictsDOs;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForRateChangeReferenceID() {
        LOGGER.entering(CLASSNAME,
                        "getSelectStatementForRateChangeReferenceID");

        String Attributes =
            StringUtil.convertListToString(PMDistrictsDO.getAttributeListForSelect());

        String Where =
            StatementGenerator.inOperator(PMDistrictsDO.PM_DISTRICT_ID,
                                          getNestedSelectStatementForRateChangeReferenceID());

        String OrderBy = PMDistrictsDO.PM_DISTRICT_ID;

        LOGGER.exiting(CLASSNAME,
                       "getSelectStatementForRateChangeReferenceID");

        return StatementGenerator.selectStatement(Attributes,
                                                  PMDistrictsDO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    private String getNestedSelectStatementForRateChangeReferenceID() {
        LOGGER.entering(CLASSNAME,
                        "getNestedSelectStatementForRateChangeReferenceID");

        String Attributes =
            StatementGenerator.distinctFunction(BlockRateScheduleDTO.PM_DISTRICT_ID);

        String Where =
            StatementGenerator.equalToOperator(BlockRateScheduleDTO.RATE_CHG_REF_ID);

        LOGGER.exiting(CLASSNAME,
                       "getNestedSelectStatementForRateChangeReferenceID");

        return StatementGenerator.selectStatement(Attributes,
                                                  BlockRateScheduleDTO.getDatabaseTableName(),
                                                  Where);
    }
}
