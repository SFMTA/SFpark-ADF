package sfpark.asset.manager.view.util;

import java.util.List;

import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;
import sfpark.adf.tools.model.data.dO.ospInventory.OSPInventoryDO;
import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.model.provider.MeterModelsProvider;
import sfpark.adf.tools.model.provider.OSPInventoryProvider;
import sfpark.adf.tools.utilities.constants.TimeToUpdate;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class DataRepositoryUtil {

    /**
     * To avoid instantiation
     */
    private DataRepositoryUtil() {
        super();
    }

    private static final long TIME_TO_UPDATE =
        TimeToUpdate.FOUR_HOURS.getTimeInMillis();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // OSP Inventory Util

    private static long TimeOfLastOSPInventoryRetrieve = -1;
    private static List<OSPInventoryDO> ospInventoryDOList = null;

    protected static synchronized List<OSPInventoryDO> getOSPInventoryDOList() {

        if (ospInventoryDOList == null ||
            ((System.currentTimeMillis() - TimeOfLastOSPInventoryRetrieve) >
             TIME_TO_UPDATE)) {
            ospInventoryDOList =
                    OSPInventoryProvider.INSTANCE.getOSPInventoryDOs();

            TimeOfLastOSPInventoryRetrieve = System.currentTimeMillis();
        }

        return ospInventoryDOList;
    }

    public static OSPInventoryDO getOSPInventoryDODefaultValue() {
        return getOSPInventoryDOList().get(0);
    }

    public static String getCNNIDForOSPID(String ospID) {

        String CNNID = "0";

        for (OSPInventoryDO DO : getOSPInventoryDOList()) {
            if (StringUtil.areEqual(DO.getOSPID(), ospID)) {
                CNNID = DO.getCNNID();
                break;
            }
        }

        return CNNID;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Meter Models Util

    private static long TimeOfLastMeterModelsRetrieve = -1;
    private static List<MeterModelsDO> meterModelDOs = null;

    public static synchronized List<MeterModelsDO> getMeterModelsDOList() {

        if (meterModelDOs == null ||
            ((System.currentTimeMillis() - TimeOfLastMeterModelsRetrieve) >
             TIME_TO_UPDATE)) {

            meterModelDOs = MeterModelsProvider.INSTANCE.getMeterModelsDOs();

            TimeOfLastMeterModelsRetrieve = System.currentTimeMillis();
        }

        return meterModelDOs;
    }

    public static MeterModelsDO getMeterModelsDODefaultValue() {
        return getMeterModelsDOList().get(0);
    }

    public static MeterModelsDO getMeterModelsDONULLValue() {
        return MeterModelsProvider.INSTANCE.getNullMeterModelsDO();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Jurisdiction Util

    private static long TimeOfLastJurisdictionRetrieve = -1;
    private static List<AllowedValuesDTO> jurisdictionList = null;

    protected static synchronized List<AllowedValuesDTO> getJurisdictionList() {

        if (jurisdictionList == null ||
            ((System.currentTimeMillis() - TimeOfLastJurisdictionRetrieve) >
             TIME_TO_UPDATE)) {
            jurisdictionList =
                    AllowedValuesProvider.INSTANCE.getAllowedValuesForParkingSpaceJurisdiction();

            TimeOfLastJurisdictionRetrieve = System.currentTimeMillis();
        }

        return jurisdictionList;
    }

    public static String getJurisdictionDefaultValue() {
        return getDefaultValue(getJurisdictionList(), "SFMTA");
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Sensor Status Util

    private static long TimeOfLastSensorFlagRetrieve = -1;
    private static List<AllowedValuesDTO> sensorFlagList = null;

    protected static synchronized List<AllowedValuesDTO> getSensorFlagList() {

        if (sensorFlagList == null ||
            ((System.currentTimeMillis() - TimeOfLastSensorFlagRetrieve) >
             TIME_TO_UPDATE)) {
            sensorFlagList =
                    AllowedValuesProvider.INSTANCE.getAllowedValuesForParkingSpaceSensorFlag();

            TimeOfLastSensorFlagRetrieve = System.currentTimeMillis();
        }

        return sensorFlagList;
    }

    public static String getSensorFlagDefaultValue() {
        return getDefaultValue(getSensorFlagList(), "N");
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Active Meter Status Util

    private static long TimeOfLastActiveMeterFlagRetrieve = -1;
    private static List<AllowedValuesDTO> activeMeterFlagList = null;
    
    protected static synchronized List<AllowedValuesDTO> getActiveMeterFlagList() {

        if (activeMeterFlagList == null ||
            ((System.currentTimeMillis() - TimeOfLastActiveMeterFlagRetrieve) >
             TIME_TO_UPDATE)) {
            activeMeterFlagList =
                    AllowedValuesProvider.INSTANCE.getAllowedValuesForParkingSpaceActiveMeterFlag();

            TimeOfLastActiveMeterFlagRetrieve = System.currentTimeMillis();
        }

        return activeMeterFlagList;
    }

    protected static List<AllowedValuesDTO> getActiveMeterFlagBulkList() {
        
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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Reason Code Util

    private static long TimeOfLastReasonCodeRetrieve = -1;
    private static List<AllowedValuesDTO> reasonCodeList = null;

    protected static synchronized List<AllowedValuesDTO> getReasonCodeList() {

        if (reasonCodeList == null ||
            ((System.currentTimeMillis() - TimeOfLastReasonCodeRetrieve) >
             TIME_TO_UPDATE)) {
            reasonCodeList =
                    AllowedValuesProvider.INSTANCE.getAllowedValuesForParkingSpaceReasonCode();

            TimeOfLastReasonCodeRetrieve = System.currentTimeMillis();
        }

        return reasonCodeList;
    }

    public static String getReasonCodeDefaultValue() {
        return getDefaultValue(getReasonCodeList(), "-");
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Cap Color Util

    protected static List<AllowedValuesDTO> getCapColorList() {
        return AllowedValuesProvider.Color.getCapColorList();
    }

    public static String getCapColorDefaultValue() {
        return getDefaultValue(AllowedValuesProvider.Color.getCapColorList(),
                               "Grey");
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Color Rule Applied Util

    protected static List<AllowedValuesDTO> getColorRuleAppliedList() {
        return AllowedValuesProvider.Color.getColorRuleAppliedList();
    }

    public static String getColorRuleAppliedDefaultValue() {
        return getDefaultValue(AllowedValuesProvider.Color.getColorRuleAppliedList(),
                               "Yellow");
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private static String getDefaultValue(List<AllowedValuesDTO> allowedValues,
                                          String value) {

        for (AllowedValuesDTO allowedValue : allowedValues) {
            if (StringUtil.areEqual(allowedValue.getColumnValue(), value)) {
                return allowedValue.getColumnValue();
            }
        }

        return allowedValues.get(0).getColumnValue();
    }

}
