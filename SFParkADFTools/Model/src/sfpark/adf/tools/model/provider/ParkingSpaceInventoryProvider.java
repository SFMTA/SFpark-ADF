package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.parkingSpaceGroups.ParkingSpaceGroupsDO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.helper.dO.ParkingSpaceGroupsDOStatus;
import sfpark.adf.tools.model.helper.dto.ParkingSpaceInventoryDTOStatus;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.util.SQLObjectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class ParkingSpaceInventoryProvider {

    private static final String CLASSNAME =
        ParkingSpaceInventoryProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME =
        ParkingSpaceInventoryDTO.getTableName();

    private ParkingSpaceInventoryProvider() {
        super();
    }

    public static final ParkingSpaceInventoryProvider INSTANCE =
        new ParkingSpaceInventoryProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    /**
     * Checks for the existence of the Parking Space recognised by the Parking
     * Space ID. Returns a Parking Space Status Object which consolidates all
     * the necessary information as a single record
     *
     * @param parkingSpaceID
     * @return Consolidated Parking Space Status Object
     */
    public ParkingSpaceInventoryDTOStatus checkForParkingSpace(String parkingSpaceID) {
        LOGGER.entering(CLASSNAME, "checkForParkingSpace");

        ParkingSpaceInventoryDTO DTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForParkingSpaceID());
            preparedStatement.setString(1, parkingSpaceID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = ParkingSpaceInventoryDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForParkingSpace");

        return new ParkingSpaceInventoryDTOStatus(DTO);
    }

    /**
     * Checks for the existence of the Parking Space recognised by the Post ID.
     * Returns a Parking Space Status Object which consolidates all the
     * necessary information as a single record
     *
     * @param postID
     * @return
     */
    public ParkingSpaceInventoryDTOStatus checkForPostID(String postID) {
        LOGGER.entering(CLASSNAME, "checkForPostID");

        ParkingSpaceInventoryDTO DTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = ConnectUtil.getConnection();
            preparedStatement =
                    connection.prepareStatement(getSelectStatementForPostID());
            preparedStatement.setString(1, postID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = ParkingSpaceInventoryDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning("Could not perform select query to retrieve the DTO. ",
                           e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForPostID");
        return new ParkingSpaceInventoryDTOStatus(DTO);
    }

    /**
     * Checks for the existence of Parking Spaces recognised by the OSP ID.
     * Returns a Parking Space Groups Status Object which consolidates all the
     * necessary information as a single record
     *
     * @param ospID
     * @return
     */
    public ParkingSpaceGroupsDOStatus checkForOSPID(String ospID) {
        LOGGER.entering(CLASSNAME, "checkForOSPID");

        ParkingSpaceGroupsDO DO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForOSPID());

            preparedStatement.setString(1, ospID);

            resultSet = preparedStatement.executeQuery();

            List<String> parkingSpaceIDList = new ArrayList<String>();

            while (resultSet.next()) {
                String parkingSpaceID =
                    resultSet.getString(ParkingSpaceInventoryDTO.PARKING_SPACE_ID);

                parkingSpaceIDList.add(parkingSpaceID);
            }

            if (!parkingSpaceIDList.isEmpty()) {
                DO =
 new ParkingSpaceGroupsDO(ParkingSpaceGroupsDO.GROUP_TYPE.OSP_ID_GROUP, ospID,
                          parkingSpaceIDList);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForOSPID");

        return new ParkingSpaceGroupsDOStatus(DO);
    }

    public List<ParkingSpaceInventoryDTO> getParkingSpaceInventoryDTOs(List<String> parkingSpaceIDs) {
        LOGGER.entering(CLASSNAME, "getParkingSpaceInventoryDTOs");

        List<ParkingSpaceInventoryDTO> parkingSpaceInventoryDTOs =
            new ArrayList<ParkingSpaceInventoryDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();
            preparedStatement =
                    connection.prepareStatement(getSelectStatementForBulkParkingSpaceID(parkingSpaceIDs));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
                    ParkingSpaceInventoryDTO.extract(resultSet);

                parkingSpaceInventoryDTOs.add(parkingSpaceInventoryDTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getParkingSpaceInventoryDTOs");

        return parkingSpaceInventoryDTOs;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareInsertStatement(Connection connection,
                                                       ParkingSpaceInventoryDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {
        PreparedStatement preparedStatement =
            connection.prepareStatement(getInsertStatement());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.POST_ID),
                                    DTO.getPostID());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.ON_OFFSTREET_TYPE),
                                    DTO.getOnOffStreetType());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.OSP_ID),
                                    DTO.getOSPID());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.JURISDICTION),
                                    DTO.getJurisdiction());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.SENSOR_FLAG),
                                    DTO.getSensorFlag());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.CAP_COLOR),
                                    DTO.getCapColor());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.ACTIVE_METER_FLAG),
                                    DTO.getActiveMeterFlag());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.REASON_CODE),
                                    DTO.getReasonCode());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.METER_VENDOR),
                                    DTO.getMeterDetails().getMeterVendor());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.METER_MODEL),
                                    DTO.getMeterDetails().getMeterModel());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.METER_TYPE),
                                    DTO.getMeterDetails().getMeterType());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.SMART_METER_FLAG),
                                    DTO.getMeterDetails().getSmartMeterFlag());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.MS_PAY_STATION_ID),
                                    DTO.getMSPayStationID());
        preparedStatement.setInt(getInsertIndexOf(ParkingSpaceInventoryDTO.MS_SPACE_NUM),
                                 DTO.getMSSpaceNum());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.OLD_RATE_AREA),
                                    DTO.getOldRateArea());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.PCO_BEAT),
                                    DTO.getPCOBeat());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.BLOCKFACE_ID),
                                    DTO.getBlockfaceID());
        preparedStatement.setInt(getInsertIndexOf(ParkingSpaceInventoryDTO.STREET_ID),
                                 DTO.getStreetID());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.STREET_NAME),
                                    DTO.getStreetName());
        preparedStatement.setInt(getInsertIndexOf(ParkingSpaceInventoryDTO.PM_DISTRICT_ID),
                                 DTO.getPMDistrictID());
        preparedStatement.setInt(getInsertIndexOf(ParkingSpaceInventoryDTO.ORIENTATION),
                                 DTO.getOrientation());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.STREET_SEG_CTRLN_ID),
                                    DTO.getCNNID());

        preparedStatement.setInt(getInsertIndexOf(ParkingSpaceInventoryDTO.STREET_NUM),
                                 DTO.getStreetNum());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.LONGITUDE),
                                    DTO.getLongitude());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.LATITUDE),
                                    DTO.getLatitude());

        preparedStatement.setObject(getInsertIndexOf(ParkingSpaceInventoryDTO.GEOM),
                                    SQLObjectUtil.getGEOMObject(connection,
                                                                DTO.getLongitude(),
                                                                DTO.getLatitude()));

        preparedStatement.setInt(getInsertIndexOf(ParkingSpaceInventoryDTO.PARITY_DIGIT_POSITION),
                                 DTO.getParityDigitPosition());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.LEGISLATION_REF),
                                    DTO.getLegislationReference());
        preparedStatement.setDate(getInsertIndexOf(ParkingSpaceInventoryDTO.LEGISLATION_DT),
                                  DTO.getLegislationDate());

        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.WORK_ORDER),
                                    DTO.getWorkOrder());
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.COMMENTS),
                                    DTO.getComments());


        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getInsertIndexOf(ParkingSpaceInventoryDTO.LAST_UPD_USER),
                                    lastUpdatedUser);

        return preparedStatement;
    }

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       ParkingSpaceInventoryDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {
        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForParkingSpaceID(DTO.getParkingSpaceID()));

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.POST_ID),
                                    DTO.getPostID());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.SENSOR_FLAG),
                                    DTO.getSensorFlag());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.CAP_COLOR),
                                    DTO.getCapColor());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.ACTIVE_METER_FLAG),
                                    DTO.getActiveMeterFlag());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.REASON_CODE),
                                    DTO.getReasonCode());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.METER_VENDOR),
                                    DTO.getMeterDetails().getMeterVendor());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.METER_MODEL),
                                    DTO.getMeterDetails().getMeterModel());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.METER_TYPE),
                                    DTO.getMeterDetails().getMeterType());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.SMART_METER_FLAG),
                                    DTO.getMeterDetails().getSmartMeterFlag());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.MS_PAY_STATION_ID),
                                    DTO.getMSPayStationID());
        preparedStatement.setInt(getUpdateIndexOf(ParkingSpaceInventoryDTO.MS_SPACE_NUM),
                                 DTO.getMSSpaceNum());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.OLD_RATE_AREA),
                                    DTO.getOldRateArea());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.PCO_BEAT),
                                    DTO.getPCOBeat());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.BLOCKFACE_ID),
                                    DTO.getBlockfaceID());
        preparedStatement.setInt(getUpdateIndexOf(ParkingSpaceInventoryDTO.STREET_ID),
                                 DTO.getStreetID());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.STREET_NAME),
                                    DTO.getStreetName());
        preparedStatement.setInt(getUpdateIndexOf(ParkingSpaceInventoryDTO.PM_DISTRICT_ID),
                                 DTO.getPMDistrictID());
        preparedStatement.setInt(getUpdateIndexOf(ParkingSpaceInventoryDTO.ORIENTATION),
                                 DTO.getOrientation());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.STREET_SEG_CTRLN_ID),
                                    DTO.getCNNID());

        preparedStatement.setInt(getUpdateIndexOf(ParkingSpaceInventoryDTO.STREET_NUM),
                                 DTO.getStreetNum());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.LONGITUDE),
                                    DTO.getLongitude());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.LATITUDE),
                                    DTO.getLatitude());

        preparedStatement.setObject(getUpdateIndexOf(ParkingSpaceInventoryDTO.GEOM),
                                    SQLObjectUtil.getGEOMObject(connection,
                                                                DTO.getLongitude(),
                                                                DTO.getLatitude()));

        preparedStatement.setInt(getUpdateIndexOf(ParkingSpaceInventoryDTO.PARITY_DIGIT_POSITION),
                                 DTO.getParityDigitPosition());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.LEGISLATION_REF),
                                    DTO.getLegislationReference());
        preparedStatement.setDate(getUpdateIndexOf(ParkingSpaceInventoryDTO.LEGISLATION_DT),
                                  DTO.getLegislationDate());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.WORK_ORDER),
                                    DTO.getWorkOrder());
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.COMMENTS),
                                    DTO.getComments());

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryDTO.LAST_UPD_USER),
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

    private String getSelectStatementForParkingSpaceID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForParkingSpaceID");

        String Attributes =
            StringUtil.convertListToString(ParkingSpaceInventoryDTO.getAttributeListForSelect());

        String Where = ParkingSpaceInventoryDTO.PARKING_SPACE_ID + " = ?";

        LOGGER.exiting(CLASSNAME, "getSelectStatementForParkingSpaceID");
        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where);
    }

    private String getSelectStatementForPostID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForParkingSpaceID");

        String Attributes =
            StringUtil.convertListToString(ParkingSpaceInventoryDTO.getAttributeListForSelect());

        String Where = ParkingSpaceInventoryDTO.POST_ID + " = ?";

        LOGGER.exiting(CLASSNAME, "getSelectStatementForParkingSpaceID");
        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where);
    }

    private String getSelectStatementForOSPID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForOSPID");

        String Attributes =
            ParkingSpaceInventoryDTO.OSP_ID + " , " + ParkingSpaceInventoryDTO.PARKING_SPACE_ID;

        String Where = ParkingSpaceInventoryDTO.OSP_ID + " = ?";

        String OrderBy = ParkingSpaceInventoryDTO.PARKING_SPACE_ID;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForOSPID");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where, OrderBy);
    }

    private String getSelectStatementForBulkParkingSpaceID(List<String> parkingSpaceIDs) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForBulkParkingSpaceID");

        String Attributes =
            StringUtil.convertListToString(ParkingSpaceInventoryDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.inOperator(ParkingSpaceInventoryDTO.PARKING_SPACE_ID,
                                          StringUtil.convertListToString(parkingSpaceIDs));

        LOGGER.exiting(CLASSNAME, "getSelectStatementForBulkParkingSpaceID");

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
            StringUtil.convertListToString(ParkingSpaceInventoryDTO.getAttributeListForInsert());

        String Values =
            StringUtil.generateStringWithRepetition("?", ParkingSpaceInventoryDTO.getAttributeListForInsert().size());

        LOGGER.exiting(CLASSNAME, "getInsertStatement");
        return StatementGenerator.insertStatement(TABLE_NAME, Columns, Values);
    }

    private int getInsertIndexOf(String indexFor) {
        return (ParkingSpaceInventoryDTO.getAttributeListForInsert().indexOf(indexFor) +
                1);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForParkingSpaceID(String parkingSpaceID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForParkingSpaceID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(ParkingSpaceInventoryDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String Where =
            ParkingSpaceInventoryDTO.PARKING_SPACE_ID + "=\'" + parkingSpaceID +
            "\'";

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForParkingSpaceID");
        return StatementGenerator.updateStatement(TABLE_NAME, SetColumnValues,
                                                  Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (ParkingSpaceInventoryDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}
