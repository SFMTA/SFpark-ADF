package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.constants.TimeToUpdate;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class AllowedValuesProvider {

    private static final String CLASSNAME =
        AllowedValuesProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "ALLOWED_VALUES";

    private AllowedValuesProvider() {
        super();
    }

    public static final AllowedValuesProvider INSTANCE =
        new AllowedValuesProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<AllowedValuesDTO> getAllowedValuesForParkingSpaceJurisdiction() {
        return getAllowedValuesFor(ParkingSpaceInventoryDTO.getTableName(),
                                   ParkingSpaceInventoryDTO.JURISDICTION);
    }

    public List<AllowedValuesDTO> getAllowedValuesForParkingSpaceSensorFlag() {
        return getAllowedValuesFor(ParkingSpaceInventoryDTO.getTableName(),
                                   ParkingSpaceInventoryDTO.SENSOR_FLAG);
    }

    public List<AllowedValuesDTO> getAllowedValuesForParkingSpaceActiveMeterFlag() {
        return getAllowedValuesFor(ParkingSpaceInventoryDTO.getTableName(),
                                   ParkingSpaceInventoryDTO.ACTIVE_METER_FLAG);
    }

    public List<AllowedValuesDTO> getAllowedValuesForParkingSpaceReasonCode() {
        return getAllowedValuesFor(ParkingSpaceInventoryDTO.getTableName(),
                                   ParkingSpaceInventoryDTO.REASON_CODE);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private List<AllowedValuesDTO> getAllowedValuesFor(final String TableName,
                                                       final String ColumnName) {
        LOGGER.entering(CLASSNAME, "getAllowedValuesFor");

        List<AllowedValuesDTO> allowedValuesDTOs =
            new ArrayList<AllowedValuesDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();
            preparedStatement =
                    connection.prepareStatement(getSelectStatementForTableAndColumnName());

            preparedStatement.setString(1, TableName);
            preparedStatement.setString(2, ColumnName);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AllowedValuesDTO allowedValuesDTO =
                    AllowedValuesDTO.extract(resultSet);

                allowedValuesDTOs.add(allowedValuesDTO);
            }


        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getAllowedValuesFor");

        return allowedValuesDTOs;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForTableAndColumnName() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForTableAndColumnName");

        String Attributes =
            StringUtil.convertListToString(AllowedValuesDTO.getAttributeListForSelect());

        String string1 = AllowedValuesDTO.TABLE_NAME + "=?";
        String string2 = AllowedValuesDTO.COLUMN_NAME + "=?";

        String Where = StatementGenerator.andOperator(string1, string2);

        String OrderBy = AllowedValuesDTO.COLUMN_VALUE;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForTableAndColumnName");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where, OrderBy);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // STATIC CLASSES

    public static class Color {

        private static final long TIME_TO_UPDATE =
            TimeToUpdate.FOUR_HOURS.getTimeInMillis();

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Cap Color Util

        private static long TimeOfLastCapColorRetrieve = -1;
        private static List<AllowedValuesDTO> capColorList = null;

        public static synchronized List<AllowedValuesDTO> getCapColorList() {

            if (capColorList == null ||
                ((System.currentTimeMillis() - TimeOfLastCapColorRetrieve) >
                 TIME_TO_UPDATE)) {
                capColorList =
                        INSTANCE.getAllowedValuesFor(ParkingSpaceInventoryDTO.getTableName(),
                                                     ParkingSpaceInventoryDTO.CAP_COLOR);

                TimeOfLastCapColorRetrieve = System.currentTimeMillis();
            }

            return capColorList;
        }

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Color Rule Applied Util

        private static long TimeOfLastColorRuleAppliedRetrieve = -1;
        private static List<AllowedValuesDTO> colorRuleAppliedList = null;

        public static synchronized List<AllowedValuesDTO> getColorRuleAppliedList() {

            if (colorRuleAppliedList == null ||
                ((System.currentTimeMillis() - TimeOfLastColorRuleAppliedRetrieve) >
                 TIME_TO_UPDATE)) {
                colorRuleAppliedList =
                        INSTANCE.getAllowedValuesFor(MeterOPScheduleDTO.getTableName(),
                                                     MeterOPScheduleDTO.COLOR_RULE_APPLIED);

                TimeOfLastColorRuleAppliedRetrieve =
                        System.currentTimeMillis();
            }

            return colorRuleAppliedList;
        }

        public static String getDisplayDescriptionForColorRuleApplied(final String colorRuleApplied) {

            if (StringUtil.isBlank(colorRuleApplied)) {
                throw new IllegalArgumentException("NULL COLOR_RULE_APPLIED NOT allowed");
            }

            List<AllowedValuesDTO> list = getColorRuleAppliedList();

            StringBuffer displayDescription =
                new StringBuffer(colorRuleApplied);

            for (AllowedValuesDTO DTO : list) {
                if (StringUtil.areEqual(colorRuleApplied,
                                        DTO.getColumnValue())) {
                    displayDescription.append(" - ");
                    displayDescription.append(DTO.getDescription());

                    break;
                }
            }

            return displayDescription.toString();
        }
    }
}
