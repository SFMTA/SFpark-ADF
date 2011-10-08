package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.util.TreeMap;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeRulesDTO;
import sfpark.adf.tools.utilities.constants.TimeToUpdate;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class AllowedValuesProvider {

    private static final String CLASSNAME =
        AllowedValuesProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final long TIME_TO_UPDATE =
        TimeToUpdate.DEFAULT.getTimeInMillis();

    /**
     * To avoid instantiation
     */
    private AllowedValuesProvider() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // JURISDICTION HELPERS

    private static long TimeOfLastJurisdictionRetrieve = -1;
    private static List<AllowedValuesDTO> jurisdictionList = null;

    public static synchronized List<AllowedValuesDTO> getJurisdictionList() {

        if (jurisdictionList == null ||
            ((System.currentTimeMillis() - TimeOfLastJurisdictionRetrieve) >
             TIME_TO_UPDATE)) {
            jurisdictionList =
                    getAllowedValuesFor(ParkingSpaceInventoryDTO.getDatabaseTableName(),
                                        ParkingSpaceInventoryDTO.JURISDICTION);

            TimeOfLastJurisdictionRetrieve = System.currentTimeMillis();
        }

        return jurisdictionList;
    }

    public static String getJurisdictionDefaultValue() {
        return getDefaultValue(getJurisdictionList(), "SFMTA");
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SENSOR STATUS HELPERS

    private static long TimeOfLastSensorFlagRetrieve = -1;
    private static List<AllowedValuesDTO> sensorFlagList = null;

    public static synchronized List<AllowedValuesDTO> getSensorFlagList() {

        if (sensorFlagList == null ||
            ((System.currentTimeMillis() - TimeOfLastSensorFlagRetrieve) >
             TIME_TO_UPDATE)) {
            sensorFlagList =
                    getAllowedValuesFor(ParkingSpaceInventoryDTO.getDatabaseTableName(),
                                        ParkingSpaceInventoryDTO.SENSOR_FLAG);

            TimeOfLastSensorFlagRetrieve = System.currentTimeMillis();
        }

        return sensorFlagList;
    }

    public static String getSensorFlagDefaultValue() {
        return getDefaultValue(getSensorFlagList(), "N");
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ACTIVE METER STATUS HELPERS

    private static long TimeOfLastActiveMeterFlagRetrieve = -1;
    private static List<AllowedValuesDTO> activeMeterFlagList = null;

    public static synchronized List<AllowedValuesDTO> getActiveMeterFlagList() {

        if (activeMeterFlagList == null ||
            ((System.currentTimeMillis() - TimeOfLastActiveMeterFlagRetrieve) >
             TIME_TO_UPDATE)) {
            activeMeterFlagList =
                    getAllowedValuesFor(ParkingSpaceInventoryDTO.getDatabaseTableName(),
                                        ParkingSpaceInventoryDTO.ACTIVE_METER_FLAG);

            TimeOfLastActiveMeterFlagRetrieve = System.currentTimeMillis();
        }

        return activeMeterFlagList;
    }

    public static List<AllowedValuesDTO> getActiveMeterFlagBulkList() {

        List<AllowedValuesDTO> list = getActiveMeterFlagList();

        for (AllowedValuesDTO allowedValue : list) {
            if (StringUtil.areEqual(allowedValue.getColumnValue(), "U")) {
                list.remove(allowedValue);
                break;
            }
        }

        return list;
    }

    public static String getActiveMeterFlagDefaultValue() {
        return getDefaultValue(getActiveMeterFlagList(), "U");
    }

    public static String getActiveMeterFlagBulkDefaultValue() {
        return getDefaultValue(getActiveMeterFlagBulkList(), "M");
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // REASON CODE HELPERS

    private static long TimeOfLastReasonCodeRetrieve = -1;
    private static List<AllowedValuesDTO> reasonCodeList = null;

    public static synchronized List<AllowedValuesDTO> getReasonCodeList() {

        if (reasonCodeList == null ||
            ((System.currentTimeMillis() - TimeOfLastReasonCodeRetrieve) >
             TIME_TO_UPDATE)) {
            reasonCodeList =
                    getAllowedValuesFor(ParkingSpaceInventoryDTO.getDatabaseTableName(),
                                        ParkingSpaceInventoryDTO.REASON_CODE);

            TimeOfLastReasonCodeRetrieve = System.currentTimeMillis();
        }

        return reasonCodeList;
    }

    public static String getReasonCodeDefaultValue() {
        return getDefaultValue(getReasonCodeList(), "-");
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // CAP COLOR HELPERS

    private static long TimeOfLastCapColorRetrieve = -1;
    private static List<AllowedValuesDTO> capColorList = null;

    public static synchronized List<AllowedValuesDTO> getCapColorList() {

        if (capColorList == null ||
            ((System.currentTimeMillis() - TimeOfLastCapColorRetrieve) >
             TIME_TO_UPDATE)) {
            capColorList =
                    getAllowedValuesFor(ParkingSpaceInventoryDTO.getDatabaseTableName(),
                                        ParkingSpaceInventoryDTO.CAP_COLOR);

            TimeOfLastCapColorRetrieve = System.currentTimeMillis();
        }

        return capColorList;
    }

    public static String getCapColorDefaultValue() {
        return getDefaultValue(getCapColorList(), "Grey");
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // COLOR RULE APPLIED HELPERS

    private static long TimeOfLastColorRuleAppliedRetrieve = -1;
    private static List<AllowedValuesDTO> colorRuleAppliedList = null;

    public static synchronized List<AllowedValuesDTO> getColorRuleAppliedList() {

        if (colorRuleAppliedList == null ||
            ((System.currentTimeMillis() - TimeOfLastColorRuleAppliedRetrieve) >
             TIME_TO_UPDATE)) {
            colorRuleAppliedList =
                    getAllowedValuesFor(MeterOPScheduleDTO.getDatabaseTableName(),
                                        MeterOPScheduleDTO.COLOR_RULE_APPLIED);

            TimeOfLastColorRuleAppliedRetrieve = System.currentTimeMillis();
        }

        return colorRuleAppliedList;
    }

    public static String getDisplayDescriptionForColorRuleApplied(final String colorRuleApplied) {

        if (StringUtil.isBlank(colorRuleApplied)) {
            throw new IllegalArgumentException("NULL COLOR_RULE_APPLIED NOT allowed");
        }

        List<AllowedValuesDTO> list = getColorRuleAppliedList();

        StringBuffer displayDescription = new StringBuffer(colorRuleApplied);

        for (AllowedValuesDTO DTO : list) {
            if (StringUtil.areEqual(colorRuleApplied, DTO.getColumnValue())) {
                displayDescription.append(" - ");
                displayDescription.append(DTO.getDescription());

                break;
            }
        }

        return displayDescription.toString();
    }

    public static String getColorRuleAppliedDefaultValue() {
        return getDefaultValue(getColorRuleAppliedList(), "Yellow");
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // PREPAYMENT TIME HELPERS

    private static long TimeOfLastPrepaymentTimeRetrieve = -1;
    private static List<AllowedValuesDTO> prepaymentTimeList = null;

    public static synchronized List<AllowedValuesDTO> getPrepaymentTimeList() {

        if (prepaymentTimeList == null ||
            ((System.currentTimeMillis() - TimeOfLastPrepaymentTimeRetrieve) >
             TIME_TO_UPDATE)) {
            prepaymentTimeList =
                    getAllowedValuesFor(MeterOPScheduleDTO.getDatabaseTableName(),
                                        MeterOPScheduleDTO.PREPAYMENT_TIME);

            TimeOfLastPrepaymentTimeRetrieve = System.currentTimeMillis();
        }

        return prepaymentTimeList;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // CALENDAR_TYPE HELPERS

    private static long TimeOfLastCalendarTypeRetrieve = -1;
    private static List<AllowedValuesDTO> calendarTypeList = null;

    public static synchronized List<AllowedValuesDTO> getCalendarTypeList() {

        if (calendarTypeList == null ||
            ((System.currentTimeMillis() - TimeOfLastCalendarTypeRetrieve) >
             TIME_TO_UPDATE)) {
            calendarTypeList =
                    getAllowedValuesFor(CalendarHeaderDTO.getDatabaseTableName(),
                                        CalendarHeaderDTO.CALENDAR_TYPE);

            TimeOfLastCalendarTypeRetrieve = System.currentTimeMillis();
        }

        return calendarTypeList;
    }

    public static String getCalendarTypeDefaultValue() {
        return getCalendarTypeList().get(0).getColumnValue();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // RATE_CHG_TYPE HELPERS

    private static long TimeOfLastRateChgTypeRetrieve = -1;
    private static List<AllowedValuesDTO> rateChgTypeList = null;

    public static synchronized List<AllowedValuesDTO> getRateChgTypeList() {

        if (rateChgTypeList == null ||
            ((System.currentTimeMillis() - TimeOfLastRateChgTypeRetrieve) >
             TIME_TO_UPDATE)) {
            rateChgTypeList =
                    getAllowedValuesFor(RateChangeRulesDTO.getDatabaseTableName(),
                                        RateChangeRulesDTO.RATE_CHG_TYPE);

            TimeOfLastRateChgTypeRetrieve = System.currentTimeMillis();
        }

        return rateChgTypeList;
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // RATE_CHG_POLICY HELPERS

    private static long TimeOfLastRateChgPolicyRetrieve = -1;
    private static List<AllowedValuesDTO> rateChgPolicyList = null;

    public static synchronized List<AllowedValuesDTO> getRateChgPolicyList() {

        if (rateChgPolicyList == null ||
            ((System.currentTimeMillis() - TimeOfLastRateChgPolicyRetrieve) >
             TIME_TO_UPDATE)) {
            rateChgPolicyList =
                    getAllowedValuesFor(RateChangeHeaderDTO.getDatabaseTableName(),
                                        RateChangeHeaderDTO.RATE_CHG_POLICY);

            TimeOfLastRateChgPolicyRetrieve = System.currentTimeMillis();
        }

        return rateChgPolicyList;
    }

    public static String getRateChgPolicyDefaultValue() {
        return getRateChgPolicyList().get(0).getColumnValue();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // PROCESS STEP HELPERS

    private static long TimeOfLastProcessStepRetrieve = -1;
    private static TreeMap<String, String> processStepTreeMap = null;

    public static synchronized TreeMap<String, String> getProcessStepTreeMap() {

        if (processStepTreeMap == null ||
            ((System.currentTimeMillis() - TimeOfLastProcessStepRetrieve) >
             TIME_TO_UPDATE)) {

            processStepTreeMap = new TreeMap<String, String>();

            List<AllowedValuesDTO> list =
                getAllowedValuesFor(RateChangeProcessControlDTO.getDatabaseTableName(),
                                    RateChangeProcessControlDTO.PROCESS_STEP);

            for (AllowedValuesDTO processStep : list) {

                String key = processStep.getColumnValue();
                String value = processStep.getDescription();

                processStepTreeMap.put(key, value);
            }

            TimeOfLastProcessStepRetrieve = System.currentTimeMillis();
        }

        return processStepTreeMap;
    }

    public static String getProcessStepDefaultValue() {
        return getProcessStepTreeMap().firstKey();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // STEP EXEC STATUS HELPERS

    private static long TimeOfLastProcessStepExecStatusRetrieve = -1;
    private static TreeMap<String, String> processStepExecStatusTreeMap = null;

    public static synchronized TreeMap<String, String> getProcessStepExecStatusTreeMap() {

        if (processStepExecStatusTreeMap == null ||
            ((System.currentTimeMillis() -
              TimeOfLastProcessStepExecStatusRetrieve) > TIME_TO_UPDATE)) {

            processStepExecStatusTreeMap = new TreeMap<String, String>();

            List<AllowedValuesDTO> list =
                getAllowedValuesFor(RateChangeProcessControlDTO.getDatabaseTableName(),
                                    RateChangeProcessControlDTO.STEP_EXEC_STATUS);

            for (AllowedValuesDTO stepExecStatus : list) {

                String key = stepExecStatus.getColumnValue();
                String value = stepExecStatus.getDescription();

                processStepExecStatusTreeMap.put(key, value);
            }

            TimeOfLastProcessStepExecStatusRetrieve =
                    System.currentTimeMillis();
        }

        return processStepExecStatusTreeMap;
    }

    public static String getProcessStepExecStatusDefaultValue() {
        return getProcessStepExecStatusTreeMap().firstKey();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private static synchronized List<AllowedValuesDTO> getAllowedValuesFor(final String TableName,
                                                                           final String ColumnName) {
        LOGGER.entering(CLASSNAME, "getAllowedValuesFor");

        List<AllowedValuesDTO> allowedValuesDTOs =
            new ArrayList<AllowedValuesDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();
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
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "getAllowedValuesFor");

        return allowedValuesDTOs;
    }

    private static String getDefaultValue(List<AllowedValuesDTO> allowedValues,
                                          String value) {

        for (AllowedValuesDTO allowedValue : allowedValues) {
            if (StringUtil.areEqual(allowedValue.getColumnValue(), value)) {
                return allowedValue.getColumnValue();
            }
        }

        return allowedValues.get(0).getColumnValue();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private static String getSelectStatementForTableAndColumnName() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForTableAndColumnName");

        String Attributes =
            StringUtil.convertListToString(AllowedValuesDTO.getAttributeListForSelect());

        String string1 =
            StatementGenerator.equalToOperator(AllowedValuesDTO.TABLE_NAME);
        String string2 =
            StatementGenerator.equalToOperator(AllowedValuesDTO.COLUMN_NAME);

        String Where = StatementGenerator.andOperator(string1, string2);

        String OrderBy = AllowedValuesDTO.COLUMN_VALUE;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForTableAndColumnName");

        return StatementGenerator.selectStatement(Attributes,
                                                  AllowedValuesDTO.getDatabaseTableName(),
                                                  Where, OrderBy);
    }
}
