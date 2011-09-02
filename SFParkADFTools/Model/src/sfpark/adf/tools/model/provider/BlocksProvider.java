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

    /*
    SELECT BLOCK_ID, STREET_NAME
    FROM SFPARK_ODS.BLOCKS
    WHERE PM_DISTRICT_ID IN (1,5)
    ORDER BY BLOCK_ID, STREET_NAME;

     */
}
