package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.garageOPHours.GarageOPHoursDTO;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class GarageOPHoursProvider {

    private static final String CLASSNAME =
        GarageOPHoursProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "GARAGE_OP_HOURS";

    private GarageOPHoursProvider() {
        super();
    }

    public static final GarageOPHoursProvider INSTANCE =
        new GarageOPHoursProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<GarageOPHoursDTO> getActiveGarageOPHoursDTOs(String ospID) {
        LOGGER.in(CLASSNAME, "getActiveGarageOPHoursDTOs");
        return getGarageOPHoursDTOs(ospID, true);
    }

    public List<GarageOPHoursDTO> getHistoricGarageOPHoursDTOs(String ospID) {
        LOGGER.in(CLASSNAME, "getHistoricGarageOPHoursDTOs");
        return getGarageOPHoursDTOs(ospID, false);
    }

    public GarageOPHoursDTO getGarageOPHoursDTO(String garageOPHoursID) {
        LOGGER.entering(CLASSNAME, "getGarageOPHoursDTO");

        GarageOPHoursDTO garageOPHoursDTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForGarageOPHoursID());
            preparedStatement.setString(1, garageOPHoursID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                garageOPHoursDTO = GarageOPHoursDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getGarageOPHoursDTO");
        return garageOPHoursDTO;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       GarageOPHoursDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {
        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(GarageOPHoursDTO.OSP_ID),
                                    DTO.getOSPID());
        preparedStatement.setString(getInsertIndexOf(GarageOPHoursDTO.SCHED_STATUS),
                                    DTO.getScheduleStatus());

        preparedStatement.setDate(getInsertIndexOf(GarageOPHoursDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getInsertIndexOf(GarageOPHoursDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());

        preparedStatement.setInt(getInsertIndexOf(GarageOPHoursDTO.SCHED_SEQ),
                                 DTO.getScheduleSequence());

        preparedStatement.setString(getInsertIndexOf(GarageOPHoursDTO.FROM_DAY),
                                    DTO.getFromDay());
        preparedStatement.setString(getInsertIndexOf(GarageOPHoursDTO.TO_DAY),
                                    DTO.getToDay());

        preparedStatement.setString(getInsertIndexOf(GarageOPHoursDTO.FROM_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getFromTime()));
        preparedStatement.setString(getInsertIndexOf(GarageOPHoursDTO.TO_TIME),
                                    TimeDisplayUtil.extractToTimeForUpdate(DTO.getToTime()));

        preparedStatement.setString(getInsertIndexOf(GarageOPHoursDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getInsertIndexOf(GarageOPHoursDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       GarageOPHoursDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForGarageOPHoursID(DTO.getGarageOPHoursID()));

        preparedStatement.setString(getUpdateIndexOf(GarageOPHoursDTO.OSP_ID),
                                    DTO.getOSPID());
        preparedStatement.setString(getUpdateIndexOf(GarageOPHoursDTO.SCHED_STATUS),
                                    DTO.getScheduleStatus());

        preparedStatement.setDate(getUpdateIndexOf(GarageOPHoursDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getUpdateIndexOf(GarageOPHoursDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());

        preparedStatement.setInt(getUpdateIndexOf(GarageOPHoursDTO.SCHED_SEQ),
                                 DTO.getScheduleSequence());

        preparedStatement.setString(getUpdateIndexOf(GarageOPHoursDTO.FROM_DAY),
                                    DTO.getFromDay());
        preparedStatement.setString(getUpdateIndexOf(GarageOPHoursDTO.TO_DAY),
                                    DTO.getToDay());

        preparedStatement.setString(getUpdateIndexOf(GarageOPHoursDTO.FROM_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getFromTime()));
        preparedStatement.setString(getUpdateIndexOf(GarageOPHoursDTO.TO_TIME),
                                    TimeDisplayUtil.extractToTimeForUpdate(DTO.getToTime()));

        preparedStatement.setString(getUpdateIndexOf(GarageOPHoursDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getUpdateIndexOf(GarageOPHoursDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<GarageOPHoursDTO> getGarageOPHoursDTOs(String ospID,
                                                        boolean activeRecords) {
        LOGGER.entering(CLASSNAME, "getGarageOPHoursDTOs");

        List<GarageOPHoursDTO> garageOPHoursDTOs =
            new ArrayList<GarageOPHoursDTO>();

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
                GarageOPHoursDTO DTO = GarageOPHoursDTO.extract(resultSet);

                garageOPHoursDTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getGarageOPHoursDTOs");

        return garageOPHoursDTOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForOSPID(boolean activeRecords) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForOSPID");

        String Attributes =
            StringUtil.convertListToString(GarageOPHoursDTO.getAttributeListForSelect());

        String Where =
            GarageOPHoursDTO.OSP_ID + " = ?" + " AND " + GarageOPHoursDTO.SCHED_STATUS +
            ((activeRecords) ? " = 'A'" : " = 'H'");

        String OrderBy =
            GarageOPHoursDTO.SCHED_SEQ + ", " + GarageOPHoursDTO.EFF_FROM_DT;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForOSPID");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where, OrderBy);
    }

    private String getSelectStatementForGarageOPHoursID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForGarageOPHoursID");

        String Attributes =
            StringUtil.convertListToString(GarageOPHoursDTO.getAttributeListForSelect());
        String Where = GarageOPHoursDTO.GARAGE_OP_HOURS_ID + " = ?";

        LOGGER.exiting(CLASSNAME, "getSelectStatementForGarageOPHoursID");

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
            StringUtil.convertListToString(GarageOPHoursDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", GarageOPHoursDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(TABLE_NAME, Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (GarageOPHoursDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForGarageOPHoursID(String garageOPHoursID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForGarageOPHoursID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(GarageOPHoursDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String Where =
            GarageOPHoursDTO.GARAGE_OP_HOURS_ID + "=\'" + garageOPHoursID +
            "\'";

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForGarageOPHoursID");

        return StatementGenerator.updateStatement(TABLE_NAME, SetColumnValues,
                                                  Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (GarageOPHoursDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}
