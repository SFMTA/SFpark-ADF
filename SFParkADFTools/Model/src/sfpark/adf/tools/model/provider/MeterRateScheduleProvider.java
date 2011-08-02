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
import sfpark.adf.tools.model.data.dto.meterRateSchedule.MeterRateScheduleDTO;
import sfpark.adf.tools.model.data.helper.MeterRateType;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public class MeterRateScheduleProvider {

    private static final String CLASSNAME =
        MeterRateScheduleProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private MeterRateScheduleProvider() {
        super();
    }

    public static final MeterRateScheduleProvider INSTANCE =
        new MeterRateScheduleProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<MeterRateScheduleDTO> getActiveMeterRateScheduleDTOs(String parkingSpaceID) {
        LOGGER.in(CLASSNAME, "getActiveMeterRateScheduleDTOs");
        return getMeterRateScheduleDTOs(parkingSpaceID, false);
    }

    public List<MeterRateScheduleDTO> getHistoricMeterRateScheduleDTOs(String parkingSpaceID) {
        LOGGER.in(CLASSNAME, "getHistoricMeterRateScheduleDTOs");
        return getMeterRateScheduleDTOs(parkingSpaceID, true);
    }

    public MeterRateScheduleDTO getMeterRateScheduleFor(String meterRateScheduleID) {
        LOGGER.entering(CLASSNAME, "getMeterRateScheduleFor");

        MeterRateScheduleDTO meterRateScheduleDTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForMeterRateScheduleID());
            preparedStatement.setString(1, meterRateScheduleID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                meterRateScheduleDTO = MeterRateScheduleDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getMeterRateScheduleFor");

        return meterRateScheduleDTO;
    }

    public Date getNextEffectiveFromDate(List<String> parkingSpaceIDs,
                                         MeterRateType rateType) {
        LOGGER.entering(CLASSNAME, "getNextEffectiveFromDate");

        Date maxDate = SQLDateUtil.getYesterdaysDate();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForMaximumDate(rateType,
                                                                                 parkingSpaceIDs));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                maxDate = resultSet.getDate(1);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_MAX_DATE.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getNextEffectiveFromDate");

        Date today = SQLDateUtil.getTodaysDate();

        if (maxDate == null) {
            return today;
        }

        return ((maxDate.before(today)) ? today :
                SQLDateUtil.getNextDateFor(maxDate));
    }

    public List<MeterRateScheduleDTO> getMeterBRateScheduleDTOs(List<String> parkingSpaceIDs) {
        return getMeterRateScheduleDTOs(parkingSpaceIDs, MeterRateType.B);
    }

    public List<MeterRateScheduleDTO> getMeterHRateScheduleDTOs(List<String> parkingSpaceIDs) {
        return getMeterRateScheduleDTOs(parkingSpaceIDs, MeterRateType.H);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       MeterRateScheduleDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.PARKING_SPACE_ID),
                                    DTO.getParkingSpaceID());

        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.RATE_TYPE),
                                    DTO.getRateType().name());
        preparedStatement.setInt(getInsertIndexOf(MeterRateScheduleDTO.SCHED_PRIORITY),
                                 DTO.getSchedulePriority());
        preparedStatement.setDate(getInsertIndexOf(MeterRateScheduleDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getInsertIndexOf(MeterRateScheduleDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());

        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.FROM_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getFromTime()));

        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.TO_TIME),
                                    TimeDisplayUtil.extractAnyTimeForUpdate(DTO.getToTime()));

        String daysAppliedString = DTO.getDaysApplied();
        if (DTO.isEditableDaysApplied()) {
            // Weekdays
            daysAppliedString =
                    StringUtil.convertListToString(DTO.getWeekDaysApplied());
        }
        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.DAYS_APPLIED),
                                    daysAppliedString);

        preparedStatement.setFloat(getInsertIndexOf(MeterRateScheduleDTO.RATE),
                                   DTO.getRate());
        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.RATE_CHG_REF),
                                    DTO.getRateChangeReference());

        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.PS_GROUP_ID),
                                    DTO.getPSGroupID());
        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.EVENT_ID),
                                    DTO.getEventID());
        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.BLOCK_ID),
                                    DTO.getBlockID());

        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(MeterRateScheduleDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       MeterRateScheduleDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForMeterRateScheduleID(DTO.getMeterRateSchedID()));

        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.PARKING_SPACE_ID),
                                    DTO.getParkingSpaceID());
        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.RATE_TYPE),
                                    DTO.getRateType().name());
        preparedStatement.setInt(getUpdateIndexOf(MeterRateScheduleDTO.SCHED_PRIORITY),
                                 DTO.getSchedulePriority());
        preparedStatement.setDate(getUpdateIndexOf(MeterRateScheduleDTO.EFF_FROM_DT),
                                  DTO.getEffectiveFromDate());
        preparedStatement.setDate(getUpdateIndexOf(MeterRateScheduleDTO.EFF_TO_DT),
                                  DTO.getEffectiveToDate());

        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.FROM_TIME),
                                    TimeDisplayUtil.extractFromTimeForUpdate(DTO.getFromTime()));

        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.TO_TIME),
                                    TimeDisplayUtil.extractAnyTimeForUpdate(DTO.getToTime()));

        String daysAppliedString = DTO.getDaysApplied();
        if (DTO.isEditableDaysApplied()) {
            // Weekdays
            daysAppliedString =
                    StringUtil.convertListToString(DTO.getWeekDaysApplied());
        }
        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.DAYS_APPLIED),
                                    daysAppliedString);

        preparedStatement.setFloat(getUpdateIndexOf(MeterRateScheduleDTO.RATE),
                                   DTO.getRate());
        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.RATE_CHG_REF),
                                    DTO.getRateChangeReference());

        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.PS_GROUP_ID),
                                    DTO.getPSGroupID());
        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.EVENT_ID),
                                    DTO.getEventID());
        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.BLOCK_ID),
                                    DTO.getBlockID());

        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(MeterRateScheduleDTO.LAST_UPD_USER),
                                    lastUpdatedUser);


        return preparedStatement;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<MeterRateScheduleDTO> getMeterRateScheduleDTOs(String parkingSpaceID,
                                                                boolean historic) {
        LOGGER.entering(CLASSNAME, "getMeterRateScheduleDTOs");

        List<MeterRateScheduleDTO> meterRateScheduleDTOs =
            new ArrayList<MeterRateScheduleDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForParkingSpaceIDANDEffToDate(historic));
            preparedStatement.setString(1, parkingSpaceID);
            preparedStatement.setDate(2, SQLDateUtil.getTodaysDate());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MeterRateScheduleDTO meterRateScheduleDTO =
                    MeterRateScheduleDTO.extract(resultSet, historic);

                meterRateScheduleDTOs.add(meterRateScheduleDTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }


        LOGGER.exiting(CLASSNAME, "getMeterRateScheduleDTOs");

        return meterRateScheduleDTOs;
    }

    private List<MeterRateScheduleDTO> getMeterRateScheduleDTOs(List<String> parkingSpaceIDs,
                                                                MeterRateType rateType) {
        LOGGER.entering(CLASSNAME, "getMeterRateScheduleDTOs");

        List<MeterRateScheduleDTO> meterRateScheduleDTOs =
            new ArrayList<MeterRateScheduleDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForMeterRateScheduleDTOs(rateType,
                                                                                           parkingSpaceIDs));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MeterRateScheduleDTO meterRateScheduleDTO =
                    MeterRateScheduleDTO.extract(resultSet);

                meterRateScheduleDTOs.add(meterRateScheduleDTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getMeterRateScheduleDTOs");

        return meterRateScheduleDTOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForParkingSpaceIDANDEffToDate(boolean historic) {
        LOGGER.entering(CLASSNAME,
                        "getSelectStatementForParkingSpaceIDANDEffToDate");

        String Attributes =
            StringUtil.convertListToString(MeterRateScheduleDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.equalToOperator(MeterRateScheduleDTO.PARKING_SPACE_ID);

        String string2 =
            historic ? StatementGenerator.lessThanOperator(MeterRateScheduleDTO.EFF_TO_DT) :
            StatementGenerator.greaterThanOrEqualToOperator(MeterRateScheduleDTO.EFF_TO_DT);

        String Where = StatementGenerator.andOperator(string1, string2);

        String OrderBy = getOrderByString();

        LOGGER.exiting(CLASSNAME,
                       "getSelectStatementForParkingSpaceIDANDEffToDate");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterRateScheduleDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    private String getOrderByString() {
        StringBuffer OrderBySB = new StringBuffer();
        OrderBySB.append(" CASE " + MeterRateScheduleDTO.RATE_TYPE);
        OrderBySB.append(" WHEN 'B' THEN 1 ");
        OrderBySB.append(" WHEN 'H' THEN 2 ");
        OrderBySB.append(" ELSE 99 END ");
        OrderBySB.append(" , ");
        OrderBySB.append(MeterRateScheduleDTO.SCHED_PRIORITY);
        OrderBySB.append(" , ");
        OrderBySB.append(MeterRateScheduleDTO.EFF_FROM_DT);

        return OrderBySB.toString();
    }

    private String getSelectStatementForMeterRateScheduleID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForMeterRateScheduleID");

        String Attributes =
            StringUtil.convertListToString(MeterRateScheduleDTO.getAttributeListForSelect());

        String Where = MeterRateScheduleDTO.METER_RATE_SCHED_ID + " = ?";

        LOGGER.exiting(CLASSNAME, "getSelectStatementForMeterRateScheduleID");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterRateScheduleDTO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatementForMaximumDate(MeterRateType rateType,
                                                    List<String> parkingSpaceIDs) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForMaximumDate");

        String Attributes =
            StatementGenerator.maxFunction(MeterRateScheduleDTO.EFF_FROM_DT);

        String string1 =
            MeterRateScheduleDTO.RATE_TYPE + " = \'" + rateType.name() + "\'";

        String string2 =
            StatementGenerator.inOperator(MeterRateScheduleDTO.PARKING_SPACE_ID,
                                          StringUtil.convertListToString(parkingSpaceIDs));

        String Where = StatementGenerator.andOperator(string1, string2);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForMaximumDate");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterRateScheduleDTO.getDatabaseTableName(),
                                                  Where);
    }

    private String getSelectStatementForMeterRateScheduleDTOs(MeterRateType rateType,
                                                              List<String> parkingSpaceIDs) {
        LOGGER.entering(CLASSNAME,
                        "getSelectStatementForMeterRateScheduleDTOs");

        String Attributes =
            StringUtil.convertListToString(MeterRateScheduleDTO.getAttributeListForSelect());

        String string1 =
            MeterRateScheduleDTO.RATE_TYPE + " = \'" + rateType.name() + "\'";

        String string2 =
            StatementGenerator.inOperator(MeterRateScheduleDTO.PARKING_SPACE_ID,
                                          StringUtil.convertListToString(parkingSpaceIDs));

        String Where = StatementGenerator.andOperator(string1, string2);

        StringBuffer OrderBySB = new StringBuffer();
        OrderBySB.append(MeterRateScheduleDTO.SCHED_PRIORITY);
        OrderBySB.append(" , ");
        OrderBySB.append(MeterRateScheduleDTO.EFF_FROM_DT);

        String OrderBy = OrderBySB.toString();

        LOGGER.exiting(CLASSNAME,
                       "getSelectStatementForMeterRateScheduleDTOs");

        return StatementGenerator.selectStatement(Attributes,
                                                  MeterRateScheduleDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private String getInsertStatement() {
        LOGGER.entering(CLASSNAME, "getInsertStatement");

        String Columns =
            StringUtil.convertListToString(MeterRateScheduleDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", MeterRateScheduleDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");

        return StatementGenerator.insertStatement(MeterRateScheduleDTO.getDatabaseTableName(),
                                                  Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (MeterRateScheduleDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForMeterRateScheduleID(String meterRateScheduleID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForMeterRateScheduleID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(MeterRateScheduleDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String Where =
            MeterRateScheduleDTO.METER_RATE_SCHED_ID + " = \'" + meterRateScheduleID +
            "\'";

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForMeterRateScheduleID");

        return StatementGenerator.updateStatement(MeterRateScheduleDTO.getDatabaseTableName(),
                                                  SetColumnValues, Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (MeterRateScheduleDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}
