package sfpark.adf.tools.model.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.util.List;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryBulkDTO;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class ParkingSpaceInventoryBulkProvider {

    private static final String CLASSNAME =
        ParkingSpaceInventoryBulkProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "PARKING_SPACE_INVENTORY";

    private ParkingSpaceInventoryBulkProvider() {
        super();
    }

    public static final ParkingSpaceInventoryBulkProvider INSTANCE =
        new ParkingSpaceInventoryBulkProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    protected PreparedStatement prepareUpdateStatement(Connection connection,
                                                       ParkingSpaceInventoryBulkDTO DTO,
                                                       final String lastUpdatedUser,
                                                       final String lastUpdatedProgram) throws SQLException {

        List<String> attributeList = DTO.getAttributeListForUpdate();

        PreparedStatement preparedStatement =
            connection.prepareStatement(getUpdateStatementForParkingSpaceID(DTO.getParkingSpaceIDList(),
                                                                            attributeList));

        if (DTO.isToBeUpdatedSensorFlag()) {
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.SENSOR_FLAG,
                                                         attributeList),
                                        DTO.getSensorFlag());
        }

        if (DTO.isToBeUpdatedCapColor()) {
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.CAP_COLOR,
                                                         attributeList),
                                        DTO.getCapColor());
        }

        if (DTO.isToBeUpdatedActiveMeterFlag()) {
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.ACTIVE_METER_FLAG,
                                                         attributeList),
                                        DTO.getActiveMeterFlag());
        }

        if (DTO.isToBeUpdatedMeterDetails()) {
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.METER_VENDOR,
                                                         attributeList),
                                        DTO.getMeterDetails().getMeterVendor());
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.METER_MODEL,
                                                         attributeList),
                                        DTO.getMeterDetails().getMeterModel());
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.METER_TYPE,
                                                         attributeList),
                                        DTO.getMeterDetails().getMeterType());
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.SMART_METER_FLAG,
                                                         attributeList),
                                        DTO.getMeterDetails().getSmartMeterFlag());
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.MS_PAY_STATION_ID,
                                                         attributeList),
                                        DTO.getMSPayStationID());
        }

        if (DTO.isToBeUpdatedOldRateArea()) {
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.OLD_RATE_AREA,
                                                         attributeList),
                                        DTO.getOldRateArea());
        }

        if (DTO.isToBeUpdatedPCOBeat()) {
            preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.PCO_BEAT,
                                                         attributeList),
                                        DTO.getPCOBeat());
        }

        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.LAST_UPD_PGM,
                                                     attributeList),
                                    lastUpdatedProgram);
        preparedStatement.setString(getUpdateIndexOf(ParkingSpaceInventoryBulkDTO.LAST_UPD_USER,
                                                     attributeList),
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
    // UPDATE HELPERS

    private String getUpdateStatementForParkingSpaceID(List<String> parkingSpaceIDs,
                                                       List<String> attributeList) {
        LOGGER.entering(CLASSNAME, "getUpdateStatementForParkingSpaceID");

        String SetColumnValues =
            StringUtil.convertListToString(StringUtil.concatenateToAllStringsInList(attributeList,
                                                                                    "=?"));

        String Where =
            ParkingSpaceInventoryBulkDTO.PARKING_SPACE_ID + " IN (" +
            StringUtil.convertListToString(parkingSpaceIDs) + ")";

        LOGGER.exiting(CLASSNAME, "getUpdateStatementForParkingSpaceID");

        return StatementGenerator.updateStatement(TABLE_NAME, SetColumnValues,
                                                  Where);
    }

    private int getUpdateIndexOf(String indexFor, List<String> inList) {
        return (inList.indexOf(indexFor) + 1);
    }
}
