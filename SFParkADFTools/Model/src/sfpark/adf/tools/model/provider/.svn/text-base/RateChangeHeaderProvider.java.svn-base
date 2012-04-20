package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.helper.dto.RateChangeHeaderDTOStatus;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class RateChangeHeaderProvider {

    private static final String CLASSNAME =
        RateChangeHeaderProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RateChangeHeaderProvider() {
        super();
    }

    public static final RateChangeHeaderProvider INSTANCE =
        new RateChangeHeaderProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    /**
     * Checks for the existence of the Rate Change Reference. Returns a Rate
     * Change Header Status Object which consolidates all the necessary
     * information as a single record
     *
     * @param rateChangeReference
     * @return
     */
    public RateChangeHeaderDTOStatus checkForRateChangeReference(String rateChangeReference) {
        LOGGER.in(CLASSNAME, "checkForRateChangeReference");
        return checkForRateChangeHeader(rateChangeReference,
                                        RateChangeHeaderDTO.RATE_CHG_REF);
    }

    /**
     * Checks for the existence of the Rate Change Reference ID. Returns a Rate
     * Change Header Status Object which consolidates all the necessary
     * information as a single record
     *
     * @param rateChangeReferenceID
     * @return
     */
    public RateChangeHeaderDTOStatus checkForRateChangeReferenceID(String rateChangeReferenceID) {
        LOGGER.in(CLASSNAME, "checkForRateChangeReferenceID");
        return checkForRateChangeHeader(rateChangeReferenceID,
                                        RateChangeHeaderDTO.RATE_CHG_REF_ID);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       RateChangeHeaderDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.RATE_CHG_REF),
                                    DTO.getRateChangeReference());
        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.RATE_CHG_DESC),
                                    DTO.getRateChangeDescription());
        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.PM_DISTRICTS),
                                    DTO.getPMDistricts());
        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.AREA_TYPE),
                                    DTO.getAreaType().getStringForTable());
        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.CALENDAR_ID),
                                    DTO.getCalendarID());
        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.RATE_CHG_POLICY),
                                    DTO.getRateChangePolicy());
        preparedStatement.setDate(getInsertIndexOf(RateChangeHeaderDTO.PLANNED_CHG_EFF_DT),
                                  DTO.getPlannedChangeEffectiveDate());
        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.STATUS),
                                    DTO.getStatus().getStringForTable());

        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(RateChangeHeaderDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       RateChangeHeaderDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForRateChgRefID(DTO.getRateChangeReferenceID()));

        preparedStatement.setString(getUpdateIndexOf(RateChangeHeaderDTO.STATUS),
                                    DTO.getStatus().getStringForTable());
        preparedStatement.setString(getUpdateIndexOf(RateChangeHeaderDTO.SUBMITTED_BY),
                                    DTO.getSubmittedBy());
        preparedStatement.setDate(getUpdateIndexOf(RateChangeHeaderDTO.SUBMITTED_DT),
                                  DTO.getSubmittedOn());
        preparedStatement.setString(getUpdateIndexOf(RateChangeHeaderDTO.APPROVED_BY),
                                    DTO.getApprovedBy());
        preparedStatement.setDate(getUpdateIndexOf(RateChangeHeaderDTO.APPROVED_DT),
                                  DTO.getApprovedOn());

        preparedStatement.setString(getUpdateIndexOf(RateChangeHeaderDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(RateChangeHeaderDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    /**
     * Checks for the existence of the Rate Change Reference using either the
     * Rate Change Reference or Rate Change Reference ID. Returns a Rate
     * Change Header Status Object which consolidates all the necessary
     * information as a single record
     *
     * @param columnValue
     * @param columnName
     * @return
     */
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

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementFor(String columnName) {
        LOGGER.entering(CLASSNAME, "getSelectStatementFor");

        String Attributes =
            StringUtil.convertListToString(RateChangeHeaderDTO.getAttributeListForSelect());

        String Where = StatementGenerator.equalToOperator(columnName);

        LOGGER.exiting(CLASSNAME, "getSelectStatementFor");

        return StatementGenerator.selectStatement(Attributes,
                                                  RateChangeHeaderDTO.getDatabaseTableName(),
                                                  Where);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(RateChangeHeaderDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", RateChangeHeaderDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(RateChangeHeaderDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (RateChangeHeaderDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForRateChgRefID(String rateChgRefID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForRateChgRefID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(RateChangeHeaderDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String RHS = "\'" + rateChgRefID + "\'";

        String Where =
            StatementGenerator.equalToOperator(RateChangeHeaderDTO.RATE_CHG_REF_ID,
                                               RHS);

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForRateChgRefID");

        return StatementGenerator.updateStatement(RateChangeHeaderDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (RateChangeHeaderDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}
