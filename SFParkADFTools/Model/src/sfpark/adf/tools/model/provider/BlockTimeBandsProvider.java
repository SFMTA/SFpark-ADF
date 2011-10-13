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
import sfpark.adf.tools.model.data.dto.blockTimeBands.BlockTimeBandsDTO;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class BlockTimeBandsProvider {

    private static final String CLASSNAME =
        BlockTimeBandsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private BlockTimeBandsProvider() {
        super();
    }

    public static final BlockTimeBandsProvider INSTANCE =
        new BlockTimeBandsProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<BlockTimeBandsDTO> getAllBlockTimeBandsDTOs(String blockID) {
        LOGGER.in(CLASSNAME, "getAllBlockTimeBandsDTOs");
        return getBlockTimeBandsDTOs(false, blockID);
    }

    public List<BlockTimeBandsDTO> getToBeDeletedBlockTimeBandsDTOs(String blockID,
                                                                    String meterClass,
                                                                    String dateType) {
        LOGGER.in(CLASSNAME, "getToBeDeletedBlockTimeBandsDTOs");
        return getBlockTimeBandsDTOs(true, blockID, meterClass, dateType);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       BlockTimeBandsDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(BlockTimeBandsDTO.BLOCK_ID),
                                    DTO.getBlockID());
        preparedStatement.setString(getInsertIndexOf(BlockTimeBandsDTO.METER_CLASS),
                                    DTO.getMeterClass());
        preparedStatement.setString(getInsertIndexOf(BlockTimeBandsDTO.DATE_TYPE),
                                    DTO.getDateType());
        preparedStatement.setInt(getInsertIndexOf(BlockTimeBandsDTO.TIME_BAND_ID),
                                 DTO.getTimeBandID());
        preparedStatement.setString(getInsertIndexOf(BlockTimeBandsDTO.TIME_BAND_FROM),
                                    DTO.getTimeBandFrom());
        preparedStatement.setString(getInsertIndexOf(BlockTimeBandsDTO.TIME_BAND_TO),
                                    DTO.getTimeBandTo());
        preparedStatement.setInt(getInsertIndexOf(BlockTimeBandsDTO.FROM_TIME),
                                 DTO.getFromTime());
        preparedStatement.setInt(getInsertIndexOf(BlockTimeBandsDTO.TO_TIME),
                                 DTO.getToTime());
        preparedStatement.setFloat(getInsertIndexOf(BlockTimeBandsDTO.CURRENT_RATE),
                                   DTO.getCurrentRate());
        preparedStatement.setDate(getInsertIndexOf(BlockTimeBandsDTO.LAST_RATE_CHG_DT),
                                  DTO.getLastRateChangeDate());

        preparedStatement.setString(getInsertIndexOf(BlockTimeBandsDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(BlockTimeBandsDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       BlockTimeBandsDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForBlockTimeBandID(DTO.getBlockTimeBandID()));

        preparedStatement.setFloat(getUpdateIndexOf(BlockTimeBandsDTO.CURRENT_RATE),
                                   DTO.getCurrentRate());
        preparedStatement.setDate(getUpdateIndexOf(BlockTimeBandsDTO.LAST_RATE_CHG_DT),
                                  DTO.getLastRateChangeDate());

        preparedStatement.setString(getUpdateIndexOf(BlockTimeBandsDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(BlockTimeBandsDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareDeleteStatement(Connection connection,
                                                       BlockTimeBandsDTO DTO) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getDeleteStatement());

        preparedStatement.setString(1, DTO.getBlockTimeBandID());

        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<BlockTimeBandsDTO> getBlockTimeBandsDTOs(boolean toBeDeleted,
                                                          String... strings) {
        LOGGER.entering(CLASSNAME, "getBlockTimeBandsDTOs");

        List<BlockTimeBandsDTO> blockTimeBandsDTOs =
            new ArrayList<BlockTimeBandsDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement(toBeDeleted));
            preparedStatement.setString(1, strings[0]);

            if (toBeDeleted) {
                preparedStatement.setString(2, strings[1]);
                preparedStatement.setString(3, strings[2]);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BlockTimeBandsDTO DTO = BlockTimeBandsDTO.extract(resultSet);

                blockTimeBandsDTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getBlockTimeBandsDTOs");

        return blockTimeBandsDTOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatement(boolean toBeDeleted) {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(BlockTimeBandsDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(BlockTimeBandsDTO.BLOCK_ID);

        if (toBeDeleted) {
            String string1 =
                StatementGenerator.equalToOperator(BlockTimeBandsDTO.METER_CLASS);
            String string2 =
                StatementGenerator.equalToOperator(BlockTimeBandsDTO.DATE_TYPE);

            Where = StatementGenerator.andOperator(Where, string1, string2);
        }

        String OrderBy =
            StatementGenerator.commaOperator(BlockTimeBandsDTO.METER_CLASS,
                                             BlockTimeBandsDTO.DATE_TYPE,
                                             BlockTimeBandsDTO.TIME_BAND_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  BlockTimeBandsDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(BlockTimeBandsDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", BlockTimeBandsDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(BlockTimeBandsDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (BlockTimeBandsDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForBlockTimeBandID(String blockTimeBandID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForBlockTimeBandID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(BlockTimeBandsDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String RHS = "\'" + blockTimeBandID + "\'";

        String Where =
            StatementGenerator.equalToOperator(BlockTimeBandsDTO.BLOCK_TIME_BAND_ID,
                                               RHS);

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForBlockTimeBandID");

        return StatementGenerator.updateStatement(BlockTimeBandsDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (BlockTimeBandsDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    private String getDeleteStatement() {
        LOGGER.entering(CLASSNAME, "getDeleteStatement");

        String Where =
            StatementGenerator.equalToOperator(BlockTimeBandsDTO.BLOCK_TIME_BAND_ID);

        LOGGER.exiting(CLASSNAME, "getDeleteStatement");

        return StatementGenerator.deleteStatement(BlockTimeBandsDTO.getDatabaseTableName(),
                                                  Where);
    }
}
