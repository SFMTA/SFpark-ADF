package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.blockRateSchedule.BlockRateScheduleDTO;
import sfpark.adf.tools.model.helper.dto.BlockRateScheduleDTOStatus;
import sfpark.adf.tools.utilities.generic.StringUtil;

/**
 * Description:
 * This class performs database transactions (SQL, connection)
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120313-01 Mark Piller - Oracle Consulting  change Adjusted Rate from float to string to allow for null entries
 * 20120313-02 Mark Piller - Oracle Consulting  add function to strip $ from data pulled from UI
 * 20120320-01 Mark Piller - Oracle Consulting  change Final Rate from float to string to allow for null entries
 */
public final class BlockRateScheduleProvider {

    private static final String CLASSNAME = BlockRateScheduleProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private BlockRateScheduleProvider() {
        super();
    }

    public static final BlockRateScheduleProvider INSTANCE = new BlockRateScheduleProvider();

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

            preparedStatement = connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, blockRateSchedID);

            /**
             *  JUST FOR DEBUGGING PURPOSES
             *
            ParameterMetaData parameterMetatData = preparedStatement.getParameterMetaData();
            System.out.println("*** DUMP OF PARAMETER META DATA ***");
            int paramCount = parameterMetatData.getParameterCount();
            System.out.println("Parameter Count is " + paramCount);
            for (int i = 1; i < parameterMetatData.getParameterCount(); i++) {
                System.out.println("Parameter number " + i);
                System.out.println("  Class name is " + parameterMetatData.getParameterClassName(i));
                // Note: Mode relates to input, output or inout
                System.out.println("  Mode is " + parameterMetatData.getParameterClassName(i));
                System.out.println("  Type is " + parameterMetatData.getParameterType(i));
                System.out.println("  Type name is " + parameterMetatData.getParameterTypeName(i));
                System.out.println("  Precision is " + parameterMetatData.getPrecision(i));
                System.out.println("  Scale is " + parameterMetatData.getScale(i));
                System.out.println("  Nullable? is " + parameterMetatData.isNullable(i));
                System.out.println("  Signed? is " + parameterMetatData.isSigned(i));
            }

            // DUMP of resultSet meta data
            ResultSetMetaData resultSetMetaData = preparedStatement.getMetaData();
            int colCount = resultSetMetaData.getColumnCount();
            System.out.println("*** DUMP OF RESULT SET META DATA ***");
            for (int i = 1; i <= colCount; i++) {
                System.out.println("Information about column " + i);
                System.out.println("   Name..........: " + resultSetMetaData.getColumnName(i));
                System.out.println("   Data Type.....: " + resultSetMetaData.getColumnType(i) + " ( " +
                                   resultSetMetaData.getColumnTypeName(i) + " )");
                System.out.println("   Precision.....: " + resultSetMetaData.getPrecision(i));
                System.out.println("   Scale.........: " + resultSetMetaData.getScale(i));
                System.out.print("   Allows Nulls..: ");
                if (resultSetMetaData.isNullable(i) == 0)
                    System.out.println("false");
                else
                    System.out.println("true");
            }
            **/

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = BlockRateScheduleDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement, connection);
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

        // 20120313-01 - replace this setFloat statement with a setString statement
        // preparedStatement.setFloat(getUpdateIndexOf(BlockRateScheduleDTO.ADJUSTED_RATE), DTO.getAdjustedRate());

        // 20120313-01 - this replaces the above statement to allow null values persited in the database
        // also - need to remove the "$" if it is in the string to be saved correctly in the database number column
        preparedStatement.setString(getUpdateIndexOf(BlockRateScheduleDTO.ADJUSTED_RATE),
                                    removeDollarSign(DTO.getAdjustedRate()));

        preparedStatement.setString(getUpdateIndexOf(BlockRateScheduleDTO.ADJUSTMENT_REASON),
                                    DTO.getAdjustmentReason());

        // 20120320-01 - replace this setFloat statement with a setString statement
        // preparedStatement.setFloat(getUpdateIndexOf(BlockRateScheduleDTO.FINAL_RATE), DTO.getFinalRate());
        // 20120313-01 - this replaces the above statement to allow null values persited in the database
        // also - need to remove the "$" if it is in the string to be saved correctly in the database number column
        preparedStatement.setString(getUpdateIndexOf(BlockRateScheduleDTO.FINAL_RATE),
                                    removeDollarSign(DTO.getFinalRate()));


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
            StatementGenerator.equalToOperator(BlockRateScheduleDTO.BLOCK_RATE_SCHED_ID, RHS);

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForBlockRateSchedID");

        return StatementGenerator.updateStatement(BlockRateScheduleDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (BlockRateScheduleDTO.getAttributeListForUpdate().indexOf(indexFor) + 1);
    }

    private String removeDollarSign(String value) {
        // strip the dollar sign if it exists in the adjustedRate
        if (value.indexOf('$') > -1) {
            // assuming the $ is the first string
            value = value.substring(1);
        }
        return value;
    }
}
