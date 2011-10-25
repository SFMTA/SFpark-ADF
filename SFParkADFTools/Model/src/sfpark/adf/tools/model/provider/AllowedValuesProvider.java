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
import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class AllowedValuesProvider {

    private static final String CLASSNAME =
        AllowedValuesProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private AllowedValuesProvider() {
        super();
    }

    public static final AllowedValuesProvider INSTANCE =
        new AllowedValuesProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<AllowedValuesDTO> getAllAllowedValuesDTOs() {
        LOGGER.entering(CLASSNAME, "getAllAllowedValuesDTOs");

        List<AllowedValuesDTO> DTOs = new ArrayList<AllowedValuesDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AllowedValuesDTO DTO = AllowedValuesDTO.extract(resultSet);

                DTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }


        LOGGER.exiting(CLASSNAME, "getAllAllowedValuesDTOs");

        return DTOs;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       AllowedValuesDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(AllowedValuesDTO.TABLE_NAME),
                                    DTO.getTableName());
        preparedStatement.setString(getInsertIndexOf(AllowedValuesDTO.COLUMN_NAME),
                                    DTO.getColumnName());
        preparedStatement.setString(getInsertIndexOf(AllowedValuesDTO.COLUMN_VALUE),
                                    DTO.getColumnValue());
        preparedStatement.setString(getInsertIndexOf(AllowedValuesDTO.DESCRIPTION),
                                    DTO.getDescription());

        preparedStatement.setString(getInsertIndexOf(AllowedValuesDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(AllowedValuesDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       AllowedValuesDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatement(DTO.getTableName(),
                                                           DTO.getColumnName(),
                                                           DTO.getColumnValue()));

        preparedStatement.setString(getUpdateIndexOf(AllowedValuesDTO.DESCRIPTION),
                                    DTO.getDescription());

        preparedStatement.setString(getUpdateIndexOf(AllowedValuesDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(AllowedValuesDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareDeleteStatement(Connection connection,
                                                       AllowedValuesDTO DTO) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getDeleteStatement());

        preparedStatement.setString(1, DTO.getTableName());
        preparedStatement.setString(2, DTO.getColumnName());
        preparedStatement.setString(3, DTO.getColumnValue());

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
            StringUtil.convertListToString(AllowedValuesDTO.getAttributeListForSelect());

        String OrderBy =
            StatementGenerator.commaOperator(AllowedValuesDTO.TABLE_NAME,
                                             AllowedValuesDTO.COLUMN_NAME,
                                             AllowedValuesDTO.COLUMN_VALUE);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  AllowedValuesDTO.getDatabaseTableName(),
                                                  null, OrderBy);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(AllowedValuesDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", AllowedValuesDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(AllowedValuesDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (AllowedValuesDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatement(String tableName, String columnName,
                                      String columnValue) {
        LOGGER.entering(CLASSNAME, "getUpdateStatement");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(AllowedValuesDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String RHS1 = "\'" + tableName + "\'";
        String RHS2 = "\'" + columnName + "\'";
        String RHS3 = "\'" + columnValue + "\'";

        String string1 =
            StatementGenerator.equalToOperator(AllowedValuesDTO.TABLE_NAME,
                                               RHS1);
        String string2 =
            StatementGenerator.equalToOperator(AllowedValuesDTO.COLUMN_NAME,
                                               RHS2);
        String string3 =
            StatementGenerator.equalToOperator(AllowedValuesDTO.COLUMN_VALUE,
                                               RHS3);

        String Where =
            StatementGenerator.andOperator(string1, string2, string3);

        LOGGER.exiting(CLASSNAME, "getUpdateStatement");

        return StatementGenerator.updateStatement(AllowedValuesDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (AllowedValuesDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    private String getDeleteStatement() {
        LOGGER.entering(CLASSNAME, "getDeleteStatement");

        String string1 =
            StatementGenerator.equalToOperator(AllowedValuesDTO.TABLE_NAME);
        String string2 =
            StatementGenerator.equalToOperator(AllowedValuesDTO.COLUMN_NAME);
        String string3 =
            StatementGenerator.equalToOperator(AllowedValuesDTO.COLUMN_VALUE);

        String Where =
            StatementGenerator.andOperator(string1, string2, string3);

        LOGGER.exiting(CLASSNAME, "getDeleteStatement");

        return StatementGenerator.deleteStatement(AllowedValuesDTO.getDatabaseTableName(),
                                                  Where);
    }
}
