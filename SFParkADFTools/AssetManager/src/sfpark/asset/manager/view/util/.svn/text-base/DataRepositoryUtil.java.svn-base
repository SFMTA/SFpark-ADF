package sfpark.asset.manager.view.util;

import java.util.List;

import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;
import sfpark.adf.tools.model.data.dO.ospInventory.OSPInventoryDO;
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
}
