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
import sfpark.adf.tools.model.data.dO.blocks.BlocksDO;
import sfpark.adf.tools.model.helper.dO.BlocksDOStatus;
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

    public BlocksDOStatus checkForBlock(String blockID) {
        LOGGER.entering(CLASSNAME, "checkForBlock");

        BlocksDO blocksDO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, blockID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                blocksDO = BlocksDO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForBlock");

        return new BlocksDOStatus(blocksDO);
    }

    public List<BlocksDO> getBlocksFor(String pmDistricts) {
        LOGGER.entering(CLASSNAME, "getBlocksFor");

        List<BlocksDO> blocksDOList = new ArrayList<BlocksDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForPMDistricts(pmDistricts));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BlocksDO blocksDO = BlocksDO.extract(resultSet);

                blocksDOList.add(blocksDO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
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

    private String getSelectStatement() {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(BlocksDO.getAttributeListForSelect());

        String Where = StatementGenerator.equalToOperator(BlocksDO.BLOCK_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  BlocksDO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatementForPMDistricts(String pmDistricts) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForPMDistricts");

        String Attributes =
            StringUtil.convertListToString(BlocksDO.getAttributeListForSelect());

        String Where =
            StatementGenerator.inOperator(BlocksDO.PM_DISTRICT_ID, pmDistricts);

        String OrderBy =
            StatementGenerator.commaOperator(BlocksDO.BLOCK_ID, BlocksDO.STREET_NAME);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForPMDistricts");

        return StatementGenerator.selectStatement(Attributes,
                                                  BlocksDO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }
}
