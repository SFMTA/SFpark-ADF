package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.helper.dto.RateChangeProcessControlDTOStatus;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class RateChangeProcessControlProvider {

    private static final String CLASSNAME =
        RateChangeProcessControlProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RateChangeProcessControlProvider() {
        super();
    }

    public static final RateChangeProcessControlProvider INSTANCE =
        new RateChangeProcessControlProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<RateChangeProcessControlDTO> getActiveRateChangeProcessControlDTOs() {
        LOGGER.in(CLASSNAME, "getActiveRateChangeProcessControlDTOs");
        return getRateChangeProcessControlDTOs(false);
    }

    public List<RateChangeProcessControlDTO> getHistoricRateChangeProcessControlDTOs() {
        LOGGER.in(CLASSNAME, "getActiveRateChangeProcessControlDTOs");
        return getRateChangeProcessControlDTOs(true);
    }

    /**
     * Checks for the existence of the Rate Change Reference. Returns a Rate
     * Change Process Control Status Object which consolidates all the necessary
     * information as a single record
     *
     * @param rateChangeReference
     * @return Consolidated Rate Change Process Control Status Object
     */
    public RateChangeProcessControlDTOStatus checkForRateChangeReference(String rateChangeReference) {
        LOGGER.entering(CLASSNAME, "checkForRateChangeReference");

        RateChangeProcessControlDTO DTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForRateChangeReference());
            preparedStatement.setString(1, rateChangeReference);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = RateChangeProcessControlDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForRateChangeReference");

        return new RateChangeProcessControlDTOStatus(DTO);
    }

    /*
    public MeterOPScheduleDTO getMeterScheduleFor(String meterOPScheduleID) {
        LOGGER.entering(CLASSNAME, "getMeterScheduleFor");

        MeterOPScheduleDTO meterOPScheduleDTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForMeterOPScheduleID());
            preparedStatement.setString(1, meterOPScheduleID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                meterOPScheduleDTO = MeterOPScheduleDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getMeterScheduleFor");
        return meterOPScheduleDTO;
    }

 */

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       RateChangeProcessControlDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.RATE_CHG_REF),
                                    DTO.getRateChangeReference());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.RATE_CHG_REF_ID),
                                    DTO.getRateChangeReferenceID());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.COMMENTS),
                                    DTO.getComments());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.PM_DISTRICT_ID),
                                    DTO.getPMDistrictID());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.METER_VENDOR),
                                    DTO.getMeterVendor());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.METER_MODEL),
                                    DTO.getMeterModel());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.BLOCK_SELECTION),
                                    DTO.getBlockSelection());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.XML_INPUT_FILE_NAME),
                                    DTO.getInputXMLFileName());
        preparedStatement.setDate(getInsertIndexOf(RateChangeProcessControlDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.TIME_LIMIT_OPTION),
                                    DTO.getTimeLimitOption());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.PROCESS_STEP),
                                    DTO.getProcessStep());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.STEP_START_FLAG),
                                    DTO.getStepStartFlag());
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(RateChangeProcessControlDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       RateChangeProcessControlDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForProcessID(DTO.getProcessID()));

        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.RATE_CHG_REF),
                                    DTO.getRateChangeReference());
        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.RATE_CHG_REF_ID),
                                    DTO.getRateChangeReferenceID());
        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.COMMENTS),
                                    DTO.getComments());
        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.XML_INPUT_FILE_NAME),
                                    DTO.getInputXMLFileName());
        preparedStatement.setDate(getUpdateIndexOf(RateChangeProcessControlDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.TIME_LIMIT_OPTION),
                                    DTO.getTimeLimitOption());
        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.PROCESS_STEP),
                                    DTO.getProcessStep());
        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.STEP_START_FLAG),
                                    DTO.getStepStartFlag());
        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(RateChangeProcessControlDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<RateChangeProcessControlDTO> getRateChangeProcessControlDTOs(boolean historic) {
        LOGGER.entering(CLASSNAME, "getRateChangeProcessControlDTOs");

        List<RateChangeProcessControlDTO> rateChangeProcessControlDTOs =
            new ArrayList<RateChangeProcessControlDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement(historic));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                RateChangeProcessControlDTO DTO =
                    RateChangeProcessControlDTO.extract(resultSet);

                rateChangeProcessControlDTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getRateChangeProcessControlDTOs");

        return rateChangeProcessControlDTOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForRateChangeReference() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForRateChangeReference");

        String Attributes =
            StringUtil.convertListToString(RateChangeProcessControlDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(RateChangeProcessControlDTO.RATE_CHG_REF);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForRateChangeReference");

        return StatementGenerator.selectStatement(Attributes,
                                                  RateChangeProcessControlDTO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatement(boolean historic) {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(RateChangeProcessControlDTO.getAttributeListForSelect());

        String Where =
            historic ? StatementGenerator.equalToOperator(RateChangeProcessControlDTO.PROCESS_STEP,
                                                          "99") :
            StatementGenerator.lessThanOperator(RateChangeProcessControlDTO.PROCESS_STEP,
                                                "99");

        String OrderBy =
            StatementGenerator.commaOperator(RateChangeProcessControlDTO.PROCESS_STEP,
                                             RateChangeProcessControlDTO.STEP_EXEC_STATUS);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  RateChangeProcessControlDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(RateChangeProcessControlDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", RateChangeProcessControlDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(RateChangeProcessControlDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (RateChangeProcessControlDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForProcessID(String processID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForProcessID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(RateChangeProcessControlDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String RHS = "\'" + processID + "\'";

        String Where =
            StatementGenerator.equalToOperator(RateChangeProcessControlDTO.PROCESS_ID,
                                               RHS);

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForProcessID");

        return StatementGenerator.updateStatement(RateChangeProcessControlDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (RateChangeProcessControlDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}

/*
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForMeterOPScheduleID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForMeterOPScheduleID");

        String Attributes =
            StringUtil.convertListToString(MeterOPScheduleDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(MeterOPScheduleDTO.METER_OP_SCHED_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForMeterOPScheduleID");
        return StatementGenerator.selectStatement(Attributes,
                                                  MeterOPScheduleDTO.getDatabaseTableName(),
                                                  Where);
    }


 */