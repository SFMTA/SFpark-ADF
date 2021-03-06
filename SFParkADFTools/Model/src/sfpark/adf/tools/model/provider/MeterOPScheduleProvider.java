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
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.helper.MeterOPScheduleType;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class MeterOPScheduleProvider {

    private static final String CLASSNAME =
        MeterOPScheduleProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private MeterOPScheduleProvider() {
        super();
    }

    public static final MeterOPScheduleProvider INSTANCE =
        new MeterOPScheduleProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<MeterOPScheduleDTO> getActiveMeterOPScheduleDTOs(String parkingSpaceID) {
        LOGGER.in(CLASSNAME, "getActiveMeterScheduleDTOs");
        return getMeterOPScheduleDTOs(parkingSpaceID, false);
    }

    public List<MeterOPScheduleDTO> getHistoricMeterOPScheduleDTOs(String parkingSpaceID) {
        LOGGER.in(CLASSNAME, "getHistoricMeterOPScheduleDTOs");
        return getMeterOPScheduleDTOs(parkingSpaceID, true);
    }

    public MeterOPScheduleDTO getMeterScheduleFor(String meterOPScheduleID) {
        LOGGER.entering(CLASSNAME, "getMeterScheduleFor");

        MeterOPScheduleDTO meterOPScheduleDTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

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
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getMeterScheduleFor");
        return meterOPScheduleDTO;
    }

    public Date getNextEffectiveFromDate(List<String> parkingSpaceIDs,
                                         MeterOPScheduleType meterOPScheduleType) {
        LOGGER.entering(CLASSNAME, "getNextEffectiveFromDate");

        Date maxDate = SQLDateUtil.getYesterdaysDate();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForMaximumDate(parkingSpaceIDs));
            preparedStatement.setString(1,
                                        meterOPScheduleType.getStringForTable());

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

    public List<MeterOPScheduleDTO> getMeterOPScheduleDTOs(List<String> parkingSpaceIDs) {
        return getMeterOPScheduleDTOs(parkingSpaceIDs, MeterOPScheduleType.OP);
    }

    public List<MeterOPScheduleDTO> getMeterTOWScheduleDTOs(List<String> parkingSpaceIDs) {
        return getMeterOPScheduleDTOs(parkingSpaceIDs,
                                      MeterOPScheduleType.TOW);
    }

    public List<MeterOPScheduleDTO> getMeterALTScheduleDTOs(List<String> parkingSpaceIDs) {
        return getMeterOPScheduleDTOs(parkingSpaceIDs,
                                      MeterOPScheduleType.ALT);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       MeterOPScheduleDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.PARKING_SPACE_ID),
                                    DTO.getParkingSpaceID());
        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.SCHED_TYPE),
                                    DTO.getScheduleType().getStringForTable());
        preparedStatement.setInt(getInsertIndexOf(MeterOPScheduleDTO.SCHED_PRIORITY),
                                 DTO.getSchedulePriority());
        preparedStatement.setDate(getInsertIndexOf(MeterOPScheduleDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getInsertIndexOf(MeterOPScheduleDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());
        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.COLOR_RULE_APPLIED),
                                    DTO.getColorRuleApplied());
        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.ALT_ADDL_DESC),
                                    DTO.getAlternateAddlDesc());
        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.FROM_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getFromTime()));

        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.TO_TIME),
                                    TimeDisplayUtil.extractAnyTimeForUpdate(DTO.getToTime()));

        String daysAppliedString = DTO.getDaysApplied();
        if (DTO.isEditableDaysApplied()) {
            // Weekdays
            daysAppliedString =
                    StringUtil.convertListToString(DTO.getWeekDaysApplied());
        }
        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.DAYS_APPLIED),
                                    daysAppliedString);

        preparedStatement.setInt(getInsertIndexOf(MeterOPScheduleDTO.TIME_LIMIT),
                                 DTO.getTimeLimit());
        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.PREPAYMENT_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getPrePaymentTime()));

        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(MeterOPScheduleDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       MeterOPScheduleDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {
        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForMeterOPScheduleID(DTO.getMeterOPSchedID()));

        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.PARKING_SPACE_ID),
                                    DTO.getParkingSpaceID());
        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.SCHED_TYPE),
                                    DTO.getScheduleType().getStringForTable());
        preparedStatement.setInt(getUpdateIndexOf(MeterOPScheduleDTO.SCHED_PRIORITY),
                                 DTO.getSchedulePriority());
        preparedStatement.setDate(getUpdateIndexOf(MeterOPScheduleDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getUpdateIndexOf(MeterOPScheduleDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());
        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.COLOR_RULE_APPLIED),
                                    DTO.getColorRuleApplied());
        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.ALT_ADDL_DESC),
                                    DTO.getAlternateAddlDesc());
        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.FROM_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getFromTime()));

        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.TO_TIME),
                                    TimeDisplayUtil.extractAnyTimeForUpdate(DTO.getToTime()));

        String daysAppliedString = DTO.getDaysApplied();
        if (DTO.isEditableDaysApplied()) {
            // Weekdays
            daysAppliedString =
                    StringUtil.convertListToString(DTO.getWeekDaysApplied());
        }
        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.DAYS_APPLIED),
                                    daysAppliedString);

        preparedStatement.setInt(getUpdateIndexOf(MeterOPScheduleDTO.TIME_LIMIT),
                                 DTO.getTimeLimit());
        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.PREPAYMENT_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getPrePaymentTime()));

        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(MeterOPScheduleDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<MeterOPScheduleDTO> getMeterOPScheduleDTOs(String parkingSpaceID,
                                                            boolean historic) {
        LOGGER.entering(CLASSNAME, "getMeterOPScheduleDTOs");

        List<MeterOPScheduleDTO> meterOPScheduleDTOs =
            new ArrayList<MeterOPScheduleDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForParkingSpaceIDANDEffToDate(historic));
            preparedStatement.setString(1, parkingSpaceID);
            preparedStatement.setDate(2, SQLDateUtil.getTodaysDate());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                MeterOPScheduleDTO meterOPScheduleDTO =
                    MeterOPScheduleDTO.extract(resultSet, historic);

                meterOPScheduleDTOs.add(meterOPScheduleDTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getMeterOPScheduleDTOs");

        return meterOPScheduleDTOs;
    }

    private List<MeterOPScheduleDTO> getMeterOPScheduleDTOs(List<String> parkingSpaceIDs,
                                                            MeterOPScheduleType meterOPScheduleType) {
        LOGGER.entering(CLASSNAME, "getMeterOPScheduleDTOs");

        List<MeterOPScheduleDTO> meterOPScheduleDTOs =
            new ArrayList<MeterOPScheduleDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForMeterOPScheduleDTOs(parkingSpaceIDs));
            preparedStatement.setString(1,
                                        meterOPScheduleType.getStringForTable());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MeterOPScheduleDTO meterOPScheduleDTO =
                    MeterOPScheduleDTO.extract(resultSet);

                meterOPScheduleDTOs.add(meterOPScheduleDTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getMeterOPScheduleDTOs");

        return meterOPScheduleDTOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForMaximumDate(List<String> parkingSpaceIDs) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForMaximumDate");

        String Attributes =
            StatementGenerator.maxFunction(MeterOPScheduleDTO.EFF_FROM_DT);

        String string1 =
            StatementGenerator.equalToOperator(MeterOPScheduleDTO.SCHED_TYPE);
        String string2 =
            StatementGenerator.inOperator(MeterOPScheduleDTO.PARKING_SPACE_ID,
                                          StringUtil.convertListToString(parkingSpaceIDs));

        String Where = StatementGenerator.andOperator(string1, string2);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForMaximumDate");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterOPScheduleDTO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatementForMeterOPScheduleDTOs(List<String> parkingSpaceIDs) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForMeterOPScheduleDTOs");

        String Attributes =
            StringUtil.convertListToString(MeterOPScheduleDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.equalToOperator(MeterOPScheduleDTO.SCHED_TYPE);
        String string2 =
            StatementGenerator.inOperator(MeterOPScheduleDTO.PARKING_SPACE_ID,
                                          StringUtil.convertListToString(parkingSpaceIDs));

        String Where = StatementGenerator.andOperator(string1, string2);

        String OrderBy =
            StatementGenerator.commaOperator(MeterOPScheduleDTO.SCHED_PRIORITY,
                                             MeterOPScheduleDTO.EFF_FROM_DT);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForMeterOPScheduleDTOs");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterOPScheduleDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

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

    private String getSelectStatementForParkingSpaceIDANDEffToDate(boolean historic) {
        LOGGER.entering(CLASSNAME,
                        "getSelectStatementForParkingSpaceIDANDEffToDate");

        String Attributes =
            StringUtil.convertListToString(MeterOPScheduleDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.equalToOperator(MeterOPScheduleDTO.PARKING_SPACE_ID);

        String string2 =
            historic ? StatementGenerator.lessThanOperator(MeterOPScheduleDTO.EFF_TO_DT) :
            StatementGenerator.greaterThanOrEqualToOperator(MeterOPScheduleDTO.EFF_TO_DT);

        String Where = StatementGenerator.andOperator(string1, string2);

        String OrderBy = getOrderByString();

        LOGGER.exiting(CLASSNAME,
                       "getSelectStatementForParkingSpaceIDANDEffToDate");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterOPScheduleDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    private String getOrderByString() {
        StringBuffer OrderBySB = new StringBuffer();
        OrderBySB.append(" CASE " + MeterOPScheduleDTO.SCHED_TYPE);
        OrderBySB.append(" WHEN 'OP' THEN 1 ");
        OrderBySB.append(" WHEN 'TOW' THEN 2 ");
        OrderBySB.append(" WHEN 'ALT' THEN 3 ");
        OrderBySB.append(" ELSE 99 END ");
        OrderBySB.append(" , ");
        OrderBySB.append(MeterOPScheduleDTO.SCHED_PRIORITY);
        OrderBySB.append(" , ");
        OrderBySB.append(MeterOPScheduleDTO.EFF_FROM_DT);

        return OrderBySB.toString();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(MeterOPScheduleDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", MeterOPScheduleDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(MeterOPScheduleDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (MeterOPScheduleDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForMeterOPScheduleID(String meterOPScheduleID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForMeterOPScheduleID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(MeterOPScheduleDTO.getAttributeListForUpdate(),
                                                                                    "=?"));
        String RHS = "\'" + meterOPScheduleID + "\'";

        String Where =
            StatementGenerator.equalToOperator(MeterOPScheduleDTO.METER_OP_SCHED_ID,
                                               RHS);

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForMeterOPScheduleID");

        return StatementGenerator.updateStatement(MeterOPScheduleDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (MeterOPScheduleDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}
