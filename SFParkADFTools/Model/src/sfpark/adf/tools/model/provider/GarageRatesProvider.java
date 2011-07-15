package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.garageRates.GarageRatesDTO;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class GarageRatesProvider {

    private static final String CLASSNAME =
        GarageRatesProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "GARAGE_RATES";

    private GarageRatesProvider() {
        super();
    }

    public static final GarageRatesProvider INSTANCE =
        new GarageRatesProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<GarageRatesDTO> getActiveGarageRatesDTOs(String ospID) {
        LOGGER.in(CLASSNAME, "getActiveGarageRatesDTOs");
        return getGarageRatesDTOs(ospID, true);
    }

    public List<GarageRatesDTO> getHistoricGarageRatesDTOs(String ospID) {
        LOGGER.in(CLASSNAME, "getHistoricGarageRatesDTOs");
        return getGarageRatesDTOs(ospID, false);
    }

    public GarageRatesDTO getGarageRatesDTO(String garageRateID) {
        LOGGER.entering(CLASSNAME, "getGarageRatesDTO");

        GarageRatesDTO garageRatesDTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForGarageRateID());
            preparedStatement.setString(1, garageRateID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                garageRatesDTO = GarageRatesDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getGarageRatesDTO");
        return garageRatesDTO;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       GarageRatesDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.OSP_ID),
                                    DTO.getOSPID());
        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.RATE_STATUS),
                                    DTO.getRateStatus());

        preparedStatement.setDate(getInsertIndexOf(GarageRatesDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getInsertIndexOf(GarageRatesDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());

        preparedStatement.setInt(getInsertIndexOf(GarageRatesDTO.DISPLAY_GROUP),
                                 DTO.getDisplayGroup().getDisplayGroupInt());
        preparedStatement.setInt(getInsertIndexOf(GarageRatesDTO.DISPLAY_SEQ),
                                 DTO.getDisplaySequence());

        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.RATE_DESC),
                                    DTO.getRateDescription());

        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.FROM_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getTimeBand().getFromTime()));
        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.TO_TIME),
                                    TimeDisplayUtil.extractToTimeForUpdate(DTO.getTimeBand().getToTime()));

        preparedStatement.setFloat(getInsertIndexOf(GarageRatesDTO.RATE),
                                   DTO.getRate());
        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.RATE_QUALIFIER),
                                    DTO.getRateQualifier().getRateQualifierText());
        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.MAX_AMT),
                                    DTO.getMaximumAmount());

        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.RATE_RESTRICTIONS),
                                    DTO.getRateRestrictions());
        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.DISPLAY_CODE),
                                    DTO.getDisplayCode());
        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.RATE_CHG_REF),
                                    DTO.getRateChangeReference());

        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getInsertIndexOf(GarageRatesDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       GarageRatesDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForGarageRateID(DTO.getGarageRateID()));

        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.OSP_ID),
                                    DTO.getOSPID());
        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.RATE_STATUS),
                                    DTO.getRateStatus());

        preparedStatement.setDate(getUpdateIndexOf(GarageRatesDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getUpdateIndexOf(GarageRatesDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());

        preparedStatement.setInt(getUpdateIndexOf(GarageRatesDTO.DISPLAY_GROUP),
                                 DTO.getDisplayGroup().getDisplayGroupInt());
        preparedStatement.setInt(getUpdateIndexOf(GarageRatesDTO.DISPLAY_SEQ),
                                 DTO.getDisplaySequence());

        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.RATE_DESC),
                                    DTO.getRateDescription());

        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.FROM_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getTimeBand().getFromTime()));
        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.TO_TIME),
                                    TimeDisplayUtil.extractToTimeForUpdate(DTO.getTimeBand().getToTime()));

        preparedStatement.setFloat(getUpdateIndexOf(GarageRatesDTO.RATE),
                                   DTO.getRate());

        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.RATE_QUALIFIER),
                                    DTO.getRateQualifier().getRateQualifierText());
        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.MAX_AMT),
                                    DTO.getMaximumAmount());

        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.RATE_RESTRICTIONS),
                                    DTO.getRateRestrictions());

        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.DISPLAY_CODE),
                                    DTO.getDisplayCode());
        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.RATE_CHG_REF),
                                    DTO.getRateChangeReference());

        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getUpdateIndexOf(GarageRatesDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<GarageRatesDTO> getGarageRatesDTOs(String ospID,
                                                    boolean activeRecords) {
        LOGGER.entering(CLASSNAME, "getGarageRatesDTOs");

        List<GarageRatesDTO> garageRatesDTOs = new ArrayList<GarageRatesDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForOSPID(activeRecords));
            preparedStatement.setString(1, ospID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                GarageRatesDTO DTO = GarageRatesDTO.extract(resultSet);

                garageRatesDTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getGarageRatesDTOs");

        return garageRatesDTOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForOSPID(boolean activeRecords) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForOSPID");

        String Attributes =
            StringUtil.convertListToString(GarageRatesDTO.getAttributeListForSelect());
        String Where =
            GarageRatesDTO.OSP_ID + " = ?" + " AND " + GarageRatesDTO.RATE_STATUS +
            ((activeRecords) ? " = 'A'" : " = 'H'");

        String OrderBy =
            GarageRatesDTO.DISPLAY_GROUP + ", " + GarageRatesDTO.DISPLAY_SEQ +
            ", " + GarageRatesDTO.EFF_FROM_DT;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForOSPID");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where, OrderBy);
    }

    private String getSelectStatementForGarageRateID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForGarageRatesID");

        String Attributes =
            StringUtil.convertListToString(GarageRatesDTO.getAttributeListForSelect());
        String Where = GarageRatesDTO.GARAGE_RATE_ID + " = ?";

        LOGGER.exiting(CLASSNAME, "getSelectStatementForGarageRatesID");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(GarageRatesDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", GarageRatesDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(TABLE_NAME, Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (GarageRatesDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForGarageRateID(String garageRateID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForOSPID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(GarageRatesDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String Where =
            GarageRatesDTO.GARAGE_RATE_ID + "=\'" + garageRateID + "\'";

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForOSPID");

        return StatementGenerator.updateStatement(TABLE_NAME, SetColumnValues,
                                                  Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (GarageRatesDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}
