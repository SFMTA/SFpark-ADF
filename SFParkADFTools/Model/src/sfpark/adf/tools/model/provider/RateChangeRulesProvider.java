package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeRulesDTO;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class RateChangeRulesProvider {

    private static final String CLASSNAME =
        RateChangeRulesProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RateChangeRulesProvider() {
        super();
    }

    public static final RateChangeRulesProvider INSTANCE =
        new RateChangeRulesProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<RateChangeRulesDTO> getReplaceableRateChangeRules(String rateChangeType) {
        LOGGER.entering(CLASSNAME, "getReplaceableRateChangeRules");

        List<RateChangeRulesDTO> rateChangeRulesDTOs =
            new ArrayList<RateChangeRulesDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, rateChangeType);
            preparedStatement.setDate(2, SQLDateUtil.getMaximumDate());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                RateChangeRulesDTO rateChangeRulesDTO =
                    RateChangeRulesDTO.extract(resultSet);

                rateChangeRulesDTOs.add(rateChangeRulesDTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getReplaceableRateChangeRules");

        return rateChangeRulesDTOs;
    }

    public Date getNextEffectiveFromDate(String rateChangeType) {

        LOGGER.entering(CLASSNAME, "getNextEffectiveFromDate");

        Date maxDate = SQLDateUtil.getYesterdaysDate();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForDate());
            preparedStatement.setString(1, rateChangeType);
            preparedStatement.setDate(2, SQLDateUtil.getMaximumDate());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                maxDate = resultSet.getDate(1);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_MAX_DATE.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getNextEffectiveFromDate");

        Date today = SQLDateUtil.getTodaysDate();

        if (maxDate == null) {
            return today;
        }

        return ((maxDate.before(today)) ? today :
                SQLDateUtil.getNextDateFor(maxDate));
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       RateChangeRulesDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(RateChangeRulesDTO.RATE_CHG_TYPE),
                                    DTO.getRateChangeType());
        preparedStatement.setInt(getInsertIndexOf(RateChangeRulesDTO.FROM_OCCUPANCY),
                                 DTO.getFromOccupancy());
        preparedStatement.setInt(getInsertIndexOf(RateChangeRulesDTO.TO_OCCUPANCY),
                                 DTO.getToOccupancy());
        preparedStatement.setDate(getInsertIndexOf(RateChangeRulesDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getInsertIndexOf(RateChangeRulesDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());
        preparedStatement.setFloat(getInsertIndexOf(RateChangeRulesDTO.CHANGE_AMT),
                                   DTO.getChangeAmount());

        preparedStatement.setString(getInsertIndexOf(RateChangeRulesDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(RateChangeRulesDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       RateChangeRulesDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForRuleID(DTO.getRuleID()));

        preparedStatement.setString(getUpdateIndexOf(RateChangeRulesDTO.RATE_CHG_TYPE),
                                    DTO.getRateChangeType());
        preparedStatement.setInt(getUpdateIndexOf(RateChangeRulesDTO.FROM_OCCUPANCY),
                                 DTO.getFromOccupancy());
        preparedStatement.setInt(getUpdateIndexOf(RateChangeRulesDTO.TO_OCCUPANCY),
                                 DTO.getToOccupancy());
        preparedStatement.setDate(getUpdateIndexOf(RateChangeRulesDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getUpdateIndexOf(RateChangeRulesDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());
        preparedStatement.setFloat(getUpdateIndexOf(RateChangeRulesDTO.CHANGE_AMT),
                                   DTO.getChangeAmount());

        preparedStatement.setString(getUpdateIndexOf(RateChangeRulesDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(RateChangeRulesDTO.LAST_UPD_USER),
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
            StringUtil.convertListToString(RateChangeRulesDTO.getAttributeListForSelect());

        String Where = getWhereString();

        String OrderBy =
            StatementGenerator.commaOperator(RateChangeRulesDTO.EFF_FROM_DT,
                                             RateChangeRulesDTO.FROM_OCCUPANCY);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  RateChangeRulesDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    private String getSelectStatementForDate() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForDate");

        String Attributes =
            StatementGenerator.maxFunction(RateChangeRulesDTO.EFF_FROM_DT);

        String Where = getWhereString();

        LOGGER.exiting(CLASSNAME, "getSelectStatementForDate");

        return StatementGenerator.selectStatement(Attributes,
                                                  RateChangeRulesDTO.getDatabaseTableName(),
                                                  Where);
    }

    private String getWhereString() {
        String string1 =
            StatementGenerator.equalToOperator(RateChangeRulesDTO.RATE_CHG_TYPE);
        String string2 =
            StatementGenerator.equalToOperator(RateChangeRulesDTO.EFF_TO_DT);

        return StatementGenerator.andOperator(string1, string2);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(RateChangeRulesDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", RateChangeRulesDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(RateChangeRulesDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (RateChangeRulesDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForRuleID(String ruleID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForRuleID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(RateChangeRulesDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String RHS = "\'" + ruleID + "\'";

        String Where =
            StatementGenerator.equalToOperator(RateChangeRulesDTO.RULE_ID,
                                               RHS);

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForRuleID");

        return StatementGenerator.updateStatement(RateChangeRulesDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (RateChangeRulesDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}

/*
     * Checks for the existence of the Rate Change Reference. Returns a Rate
     * Change Header Status Object which consolidates all the necessary
     * information as a single record
     *
     * @param rateChangeReference
     * @return
    public RateChangeHeaderDTOStatus checkForRateChangeReference(String rateChangeReference) {
        LOGGER.in(CLASSNAME, "checkForRateChangeReference");
        return checkForRateChangeHeader(rateChangeReference,
                                        RateChangeHeaderDTO.RATE_CHG_REF);
    }

     * Checks for the existence of the Rate Change Reference ID. Returns a Rate
     * Change Header Status Object which consolidates all the necessary
     * information as a single record
     *
     * @param rateChangeReferenceID
     * @return
    public RateChangeHeaderDTOStatus checkForRateChangeReferenceID(String rateChangeReferenceID) {
        LOGGER.in(CLASSNAME, "checkForRateChangeReferenceID");
        return checkForRateChangeHeader(rateChangeReferenceID,
                                        RateChangeHeaderDTO.RATE_CHG_REF_ID);
    }


    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

     * Checks for the existence of the Rate Change Reference using either the
     * Rate Change Reference or Rate Change Reference ID. Returns a Rate
     * Change Header Status Object which consolidates all the necessary
     * information as a single record
     *
     * @param columnValue
     * @param columnName
     * @return

    private RateChangeHeaderDTOStatus checkForRateChangeHeader(String columnValue,
                                                               String columnName) {
        LOGGER.entering(CLASSNAME, "checkForRateChangeHeader");

        RateChangeHeaderDTO DTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementFor(columnName));
            preparedStatement.setString(1, columnValue);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = RateChangeHeaderDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForRateChangeHeader");

        return new RateChangeHeaderDTOStatus(DTO);
    }

 */