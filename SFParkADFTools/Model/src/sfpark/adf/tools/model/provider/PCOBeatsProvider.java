package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.pcoBeats.PCOBeatsDO;
import sfpark.adf.tools.model.helper.dO.PCOBeatsDOStatus;
import sfpark.adf.tools.model.util.ConnectUtil;

public final class PCOBeatsProvider {

    private static final String CLASSNAME = PCOBeatsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = PCOBeatsDO.getDatabaseTableName();

    private PCOBeatsProvider() {
        super();
    }

    public static final PCOBeatsProvider INSTANCE = new PCOBeatsProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    /**
     * Checks whether a PCO Beat exists based on the Longitude and Latitude
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public PCOBeatsDOStatus checkForPCOBeat(String longitude,
                                            String latitude) {
        LOGGER.entering(CLASSNAME, "checkForPCOBeat");

        PCOBeatsDO pcoBeatsDO = null;

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
                pcoBeatsDO = PCOBeatsDO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForPCOBeat");

        return new PCOBeatsDOStatus(pcoBeatsDO);
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

        String Attributes = " DISTINCT " + PCOBeatsDO.BEATNAME;

        String Where = StatementGenerator.getSDORelateFunctionString();

        LOGGER.exiting(CLASSNAME, "getSelectStatementForLongitudeAndLatitude");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where);
    }
}
