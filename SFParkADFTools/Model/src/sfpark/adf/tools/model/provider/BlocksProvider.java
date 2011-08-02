package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.blocks.BlocksDO;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class BlocksProvider {

    private static final String CLASSNAME = BlocksProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private BlocksProvider() {
        super();
    }

    public static final BlocksProvider INSTANCE = new BlocksProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<BlocksDO> getBlocksFor(int PMDistrictID) {
        LOGGER.entering(CLASSNAME, "getBlocksFor");

        List<BlocksDO> blocksDOList = new ArrayList<BlocksDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForPMDistrictID());
            preparedStatement.setInt(1, PMDistrictID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BlocksDO blocksDO = BlocksDO.extract(resultSet);

                blocksDOList.add(blocksDO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getBlocksFor");

        return blocksDOList;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForPMDistrictID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForPMDistrictID");

        String Attributes =
            StringUtil.convertListToString(BlocksDO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(BlocksDO.PM_DISTRICT_ID);

        String OrderBy = BlocksDO.BLOCK_ID;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForPMDistrictID");

        return StatementGenerator.selectStatement(Attributes,
                                                  BlocksDO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }
}
