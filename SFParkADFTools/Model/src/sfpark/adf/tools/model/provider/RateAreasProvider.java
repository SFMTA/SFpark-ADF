package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.rateAreas.RateAreasDO;
import sfpark.adf.tools.model.helper.dO.RateAreasDOStatus;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class RateAreasProvider {

    private static final String CLASSNAME = RateAreasProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RateAreasProvider() {
        super();
    }

    public static final RateAreasProvider INSTANCE = new RateAreasProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    /**
     * Checks whether a Rate Area exists based on the Longitude and Latitude
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public RateAreasDOStatus checkForRateArea(String longitude,
                                              String latitude) {
        LOGGER.entering(CLASSNAME, "checkForRateArea");

        RateAreasDO rateAreasDO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForLongitudeAndLatitude());
            preparedStatement.setString(1, longitude);
            preparedStatement.setString(2, latitude);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rateAreasDO = RateAreasDO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForRateArea");

        return new RateAreasDOStatus(rateAreasDO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForLongitudeAndLatitude() {
        LOGGER.entering(CLASSNAME,
                        "getSelectStatementForLongitudeAndLatitude");

        String Attributes =
            StringUtil.convertListToString(RateAreasDO.getAttributeListForSelect());

        String Where = StatementGenerator.getSDORelateFunctionString();

        LOGGER.exiting(CLASSNAME, "getSelectStatementForLongitudeAndLatitude");

        return StatementGenerator.selectStatement(Attributes,
                                                  RateAreasDO.getDatabaseTableName(),
                                                  Where);
    }
}
