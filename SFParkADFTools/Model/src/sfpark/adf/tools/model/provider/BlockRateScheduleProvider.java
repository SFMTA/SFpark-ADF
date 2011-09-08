package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.blockRateSchedule.BlockRateScheduleDTO;
import sfpark.adf.tools.model.helper.dto.BlockRateScheduleDTOStatus;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class BlockRateScheduleProvider {

    private static final String CLASSNAME =
        BlockRateScheduleProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private BlockRateScheduleProvider() {
        super();
    }

    public static final BlockRateScheduleProvider INSTANCE =
        new BlockRateScheduleProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    /**
     * Checks for the existence of the Block Rate Schedule ID. Returns a Block
     * Rate Schedule Status Object which consolidates all the necessary
     * information as a single record
     *
     * @param blockRateSchedID
     * @return
     */
    public BlockRateScheduleDTOStatus checkForBlockRateSchedID(String blockRateSchedID) {
        LOGGER.entering(CLASSNAME, "checkForBlockRateSchedID");

        BlockRateScheduleDTO DTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, blockRateSchedID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = BlockRateScheduleDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForBlockRateSchedID");

        return new BlockRateScheduleDTOStatus(DTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       BlockRateScheduleDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(this.getUpdateStatementForBlockRateSchedID(DTO.getBlockRateSchedID()));

        preparedStatement.setFloat(getUpdateIndexOf(BlockRateScheduleDTO.ADJUSTED_RATE),
                                   DTO.getAdjustedRate());
        preparedStatement.setString(getUpdateIndexOf(BlockRateScheduleDTO.ADJUSTMENT_REASON),
                                    DTO.getAdjustmentReason());
        preparedStatement.setFloat(getUpdateIndexOf(BlockRateScheduleDTO.FINAL_RATE),
                                   DTO.getFinalRate());
        preparedStatement.setString(getUpdateIndexOf(BlockRateScheduleDTO.FINAL_JUSTIFICATION),
                                    DTO.getFinalJustification());

        preparedStatement.setString(getUpdateIndexOf(BlockRateScheduleDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(BlockRateScheduleDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
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
            StringUtil.convertListToString(BlockRateScheduleDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(BlockRateScheduleDTO.BLOCK_RATE_SCHED_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  BlockRateScheduleDTO.getDatabaseTableName(),
                                                  Where);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForBlockRateSchedID(String blockRateSchedID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForBlockRateSchedID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(BlockRateScheduleDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String RHS = "\'" + blockRateSchedID + "\'";

        String Where =
            StatementGenerator.equalToOperator(BlockRateScheduleDTO.BLOCK_RATE_SCHED_ID,
                                               RHS);

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForBlockRateSchedID");

        return StatementGenerator.updateStatement(BlockRateScheduleDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (BlockRateScheduleDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}
