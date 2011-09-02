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
import sfpark.adf.tools.model.data.dO.ospInventory.OSPInventoryDO;
import sfpark.adf.tools.model.data.dto.ospInventory.OSPInventoryDTO;
import sfpark.adf.tools.model.helper.dto.OSPInventoryDTOStatus;
import sfpark.adf.tools.util.SQLObjectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class OSPInventoryProvider {

    private static final String CLASSNAME =
        OSPInventoryProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "OSP_INVENTORY";

    private OSPInventoryProvider() {
        super();
    }

    public static final OSPInventoryProvider INSTANCE =
        new OSPInventoryProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    /**
     * Returns the OSP Inventory IDs and Facility Names in a List to be
     * displayed in the dropdown list
     *
     * @return
     */
    public List<OSPInventoryDO> getOSPInventoryDOs() {
        LOGGER.entering(CLASSNAME, "getOSPInventoryDOs");
        List<OSPInventoryDO> ospInventoryDOs = new ArrayList<OSPInventoryDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OSPInventoryDO ospInventoryDO =
                    OSPInventoryDO.extract(resultSet);

                ospInventoryDOs.add(ospInventoryDO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);

        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getOSPInventoryDOs");
        return ospInventoryDOs;
    }

    public OSPInventoryDTOStatus checkForOSP(String ospID) {
        LOGGER.entering(CLASSNAME, "checkForOSP");

        OSPInventoryDTO DTO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = OracleDBConnection.getConnection();
            preparedStatement =
                    connection.prepareStatement(getSelectStatementForOSPID());
            preparedStatement.setString(1, ospID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DTO = OSPInventoryDTO.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForOSP");

        return new OSPInventoryDTOStatus(DTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       OSPInventoryDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {
        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForOSPID(DTO.getOSPID()));

        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.FACILITY_NAME),
                                    DTO.getFacilityName());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.STREET_ADDRESS),
                                    DTO.getStreetAddress());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.LOCATION),
                                    DTO.getLocation());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.PHONE),
                                    DTO.getPhone());

        preparedStatement.setInt(getUpdateIndexOf(OSPInventoryDTO.PM_DISTRICT_ID),
                                 DTO.getPMDistrictID());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.AREA_TYPE),
                                    DTO.getAreaType());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.BLOCKFACE_ID),
                                    DTO.getBlockfaceID());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.STREET_SEG_CTRLN_ID),
                                    DTO.getCNNID());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.FACILITY_TYPE),
                                    DTO.getFacilityType());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.OWNER),
                                    DTO.getOwner());

        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.SENSOR_FLAG),
                                    DTO.getInternalSensorFlag());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.METER_FLAG),
                                    DTO.getInternalMeterFlag());

        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.DATA_FEED_FLAG),
                                    DTO.getDataFeedFlag());

        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.SERVICES),
                                    DTO.getInternalServices());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.WEB_SITE),
                                    DTO.getWebSite());

        preparedStatement.setInt(getUpdateIndexOf(OSPInventoryDTO.VEH_ENTRY_LANES),
                                 DTO.getNumberOfVehicleEntryLanes());
        preparedStatement.setInt(getUpdateIndexOf(OSPInventoryDTO.VEH_EXIT_LANES),
                                 DTO.getNumberOfVehicleExitLanes());
        preparedStatement.setInt(getUpdateIndexOf(OSPInventoryDTO.MC_ENTRY_LANES),
                                 DTO.getNumberOfMotorcycleEntryLanes());
        preparedStatement.setInt(getUpdateIndexOf(OSPInventoryDTO.MC_EXIT_LANES),
                                 DTO.getNumberOfMotorcycleExitLanes());

        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.SYSTEM),
                                    DTO.getSystemName());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.HI_VOL_DISC_FLAG),
                                    DTO.getInternalHighVolumeDiscountFlag());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.VALIDATION_PGM),
                                    DTO.getValidationProgram());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.SP_EVT_RATES),
                                    DTO.getInternalSpecialEventRates());

        preparedStatement.setFloat(getUpdateIndexOf(OSPInventoryDTO.ACTIVATION_FEE),
                                   DTO.getActivationFee());
        preparedStatement.setFloat(getUpdateIndexOf(OSPInventoryDTO.CARD_REPLACE_FEE),
                                   DTO.getCardReplacementFee());
        preparedStatement.setFloat(getUpdateIndexOf(OSPInventoryDTO.LATE_FEE),
                                   DTO.getLateFee());
        preparedStatement.setFloat(getUpdateIndexOf(OSPInventoryDTO.REOPEN_FEE),
                                   DTO.getReopeningGarageFee());
        preparedStatement.setFloat(getUpdateIndexOf(OSPInventoryDTO.NO_KEY_VALET_FEE),
                                   DTO.getValetServiceWithoutKeyFee());

        preparedStatement.setInt(getUpdateIndexOf(OSPInventoryDTO.CAPACITY),
                                 DTO.getCapacity());

        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.MAIN_ENTRANCE_LONG),
                                    DTO.getMainEntranceLongitude());
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.MAIN_ENTRANCE_LAT),
                                    DTO.getMainEntranceLatitude());

        preparedStatement.setObject(getUpdateIndexOf(OSPInventoryDTO.MAIN_ENTRANCE_GEOM),
                                    SQLObjectUtil.getGEOMObject(connection,
                                                                DTO.getMainEntranceLongitude(),
                                                                DTO.getMainEntranceLatitude()));

        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.LAST_UPD_USER),
                                    lastUpdatedUser);
        preparedStatement.setString(getUpdateIndexOf(OSPInventoryDTO.LAST_UPD_PGM),
                                    lastUpdatedProgram);

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
            StringUtil.convertListToString(OSPInventoryDO.getAttributeListForSelect());

        String OrderBy = OSPInventoryDO.OSP_ID;

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME, null,
                                                  OrderBy);
    }

    private String getSelectStatementForOSPID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForOSPID");

        String Attributes =
            StringUtil.convertListToString(OSPInventoryDTO.getAttributeListForSelect());
        String Where = OSPInventoryDTO.OSP_ID + " = ?";

        LOGGER.exiting(CLASSNAME, "getSelectStatementForOSPID");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private String getUpdateStatementForOSPID(String ospID) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForOSPID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(OSPInventoryDTO.getAttributeListForUpdate(),
                                                                                    "=?"));

        String Where = OSPInventoryDTO.OSP_ID + "=\'" + ospID + "\'";

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForOSPID");

        return StatementGenerator.updateStatement(TABLE_NAME, SetColumnValues,
                                                  Where);
    }

    private int getUpdateIndexOf(String indexFor) {
        return (OSPInventoryDTO.getAttributeListForUpdate().indexOf(indexFor) +
                1);
    }
}
