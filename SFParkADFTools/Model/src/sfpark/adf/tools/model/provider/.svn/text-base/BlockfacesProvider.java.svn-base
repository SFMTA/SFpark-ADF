package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dO.blockface.BlockfaceDO;
import sfpark.adf.tools.model.helper.dO.BlockfaceDOStatus;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class BlockfacesProvider {

    private static final String CLASSNAME = BlockfacesProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private BlockfacesProvider() {
        super();
    }

    public static final BlockfacesProvider INSTANCE = new BlockfacesProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    /**
     * Checks whether a Blockface exists based on the Longitude and Latitude
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public BlockfaceDOStatus checkForBlockface(String longitude,
                                               String latitude) {
        LOGGER.entering(CLASSNAME, "checkForBlockface");

        String distance = null;
        BlockfaceDO blockfaceDO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForLongitudeAndLatitude());
            preparedStatement.setString(1, longitude);
            preparedStatement.setString(2, latitude);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                distance = resultSet.getString(1);

                blockfaceDO = BlockfaceDO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForBlockface");
        return new BlockfaceDOStatus(blockfaceDO, distance);
    }

    /**
     * Checks whether a Blockface exists based on the Blockface ID
     *
     * @param blockfaceID
     * @return
     */
    public BlockfaceDOStatus checkForBlockface(String blockfaceID) {
        LOGGER.entering(CLASSNAME, "checkForBlockface");

        BlockfaceDO blockfaceDO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForBlockfaceID());
            preparedStatement.setString(1, blockfaceID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                blockfaceDO = BlockfaceDO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForBlockface");
        return new BlockfaceDOStatus(blockfaceDO, null);
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
            "SDO_NN_DISTANCE(1), " + StringUtil.convertListToString(BlockfaceDO.getAttributeListForSelect());

        String Where =
            "sdo_nn(GEOM, sdo_geometry(2001, 8307, sdo_point_type(?, ?, null), null, null), 'sdo_num_res=1 distance=10 unit=foot', 1) = 'TRUE'";


        LOGGER.exiting(CLASSNAME, "getSelectStatementForLongitudeAndLatitude");
        return StatementGenerator.selectStatement(Attributes,
                                                  BlockfaceDO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatementForBlockfaceID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForBlockfaceID");

        String Attributes =
            StringUtil.convertListToString(BlockfaceDO.getAttributeListForSelect());

        String Where = BlockfaceDO.BLOCKFACE_ID + " = ?";

        LOGGER.exiting(CLASSNAME, "getSelectStatementForBlockfaceID");
        return StatementGenerator.selectStatement(Attributes,
                                                  BlockfaceDO.getDatabaseTableName(),
                                                  Where);
    }
}
