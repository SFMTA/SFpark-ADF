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
import sfpark.adf.tools.model.data.dto.timeBandModel.TimeBandModelDTO;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class TimeBandModelProvider {

    private static final String CLASSNAME =
        TimeBandModelProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private TimeBandModelProvider() {
        super();
    }

    public static final TimeBandModelProvider INSTANCE =
        new TimeBandModelProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<TimeBandModelDTO> getAllTimeBandModelDTOs() {
        LOGGER.in(CLASSNAME, "getAllTimeBandModelDTOs");
        return getTimeBandModelDTOs(false);
    }

    public List<TimeBandModelDTO> getTimeBandModelDTOsFor(String meterClass,
                                                          String dateType) {
        LOGGER.in(CLASSNAME, "getToBeDeletedTimeBandModelDTOs");
        return getTimeBandModelDTOs(true, meterClass, dateType);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       TimeBandModelDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(TimeBandModelDTO.METER_CLASS),
                                    DTO.getMeterClass());
        preparedStatement.setString(getInsertIndexOf(TimeBandModelDTO.DATE_TYPE),
                                    DTO.getDateType());
        preparedStatement.setInt(getInsertIndexOf(TimeBandModelDTO.TIME_BAND_ID),
                                 DTO.getTimeBandID());
        preparedStatement.setString(getInsertIndexOf(TimeBandModelDTO.TIME_BAND_FROM),
                                    DTO.getTimeBandFrom());
        preparedStatement.setString(getInsertIndexOf(TimeBandModelDTO.TIME_BAND_TO),
                                    DTO.getTimeBandTo());

        preparedStatement.setString(getInsertIndexOf(TimeBandModelDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(TimeBandModelDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareDeleteStatement(Connection connection,
                                                       TimeBandModelDTO DTO) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getDeleteStatement());

        preparedStatement.setString(1, DTO.getMeterClass());
        preparedStatement.setString(2, DTO.getDateType());

        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<TimeBandModelDTO> getTimeBandModelDTOs(boolean toBeDeleted,
                                                        String... strings) {
        LOGGER.entering(CLASSNAME, "getTimeBandModelDTOsFor");

        List<TimeBandModelDTO> TimeBandModelDTOs =
            new ArrayList<TimeBandModelDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement(toBeDeleted));

            if (toBeDeleted) {
                preparedStatement.setString(1, strings[0]);
                preparedStatement.setString(2, strings[1]);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TimeBandModelDTO DO = TimeBandModelDTO.extract(resultSet);

                TimeBandModelDTOs.add(DO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);

        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getTimeBandModelDTOsFor");

        return TimeBandModelDTOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatement(boolean toBeDeleted) {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(TimeBandModelDTO.getAttributeListForSelect());

        String Where = null;

        if (toBeDeleted) {
            String string1 =
                StatementGenerator.equalToOperator(TimeBandModelDTO.METER_CLASS);
            String string2 =
                StatementGenerator.equalToOperator(TimeBandModelDTO.DATE_TYPE);

            Where = StatementGenerator.andOperator(string1, string2);
        }

        String OrderBy =
            StatementGenerator.commaOperator(TimeBandModelDTO.METER_CLASS,
                                             TimeBandModelDTO.DATE_TYPE,
                                             TimeBandModelDTO.TIME_BAND_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes,
                                                  TimeBandModelDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(TimeBandModelDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", TimeBandModelDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(TimeBandModelDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (TimeBandModelDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    private String getDeleteStatement() {
        LOGGER.entering(CLASSNAME, "getDeleteStatement");

        String string1 =
            StatementGenerator.equalToOperator(TimeBandModelDTO.METER_CLASS);
        String string2 =
            StatementGenerator.equalToOperator(TimeBandModelDTO.DATE_TYPE);

        String Where = StatementGenerator.andOperator(string1, string2);

        LOGGER.exiting(CLASSNAME, "getDeleteStatement");

        return StatementGenerator.deleteStatement(TimeBandModelDTO.getDatabaseTableName(),
                                                  Where);
    }
}
