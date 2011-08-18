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
import sfpark.adf.tools.model.data.helper.PMDistrictAreaType;
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

    public List<PMDistrictsDO> getPMDistrictsFor(PMDistrictAreaType areaType) {
        LOGGER.entering(CLASSNAME, "getPMDistrictsFor");

        List<PMDistrictsDO> pmDistrictsDOs = new ArrayList<PMDistrictsDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForAreaType());
            preparedStatement.setString(1, areaType.getStringForTable());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PMDistrictsDO DO = PMDistrictsDO.extract(resultSet);

                pmDistrictsDOs.add(DO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);
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

    private String getSelectStatementForAreaType() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForAreaType");

        String Attributes =
            StringUtil.convertListToString(PMDistrictsDO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(PMDistrictsDO.AREA_TYPE);

        String OrderBy = PMDistrictsDO.PM_DISTRICT_NAME;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForAreaType");

        return StatementGenerator.selectStatement(Attributes,
                                                  PMDistrictsDO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }
}
