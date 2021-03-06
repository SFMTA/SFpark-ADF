package sfpark.asset.manager.view.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.ospInventory.OSPInventoryDO;
import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

/**
 * This ADFUIDisplayUtil is ONLY for the AssetManager Project
 * 
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111122-01 Mark Piller - Oracle Consulting  added 540 minutes to Time Limit array
 * 20111207-01 Mark Piller - Oracle Consulting  Added logic to use database Allowed Values for Time Limit
 * 20111208-01 Mark Piller - Oracle Consulting  Add new method to create Allowed Values with integer type
 */
public final class ADFUIDisplayUtil {

    private static final String CLASSNAME =
        ADFUIDisplayUtil.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    /**
     * To avoid instantiation
     */
    private ADFUIDisplayUtil() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // OSP Inventory Util

    public static List<SelectItem> getGeneralOSPInventoryDisplayList() {

        List<SelectItem> ospInventorySIList = new ArrayList<SelectItem>();
        List<OSPInventoryDO> ospInventoryDOs =
            DataRepositoryUtil.getOSPInventoryDOList();

        for (OSPInventoryDO ospInventoryDO : ospInventoryDOs) {
            String label =
                ospInventoryDO.getOSPID() + " - " + ospInventoryDO.getFacilityName();

            ospInventorySIList.add(new SelectItem(ospInventoryDO.getOSPID(),
                                                  label));
        }

        return ospInventorySIList;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Jurisdiction Util

    public static List<SelectItem> getJurisdictionDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getJurisdictionList(),
                                           false);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Sensor Status Util

    public static List<SelectItem> getSensorFlagDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getSensorFlagList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Cap Color Util

    public static List<SelectItem> getCapColorDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getCapColorList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Color Rule Applied Util

    public static List<SelectItem> getColorRuleAppliedDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getColorRuleAppliedList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Active Meter Status Util

    public static List<SelectItem> getActiveMeterFlagDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getActiveMeterFlagList(),
                                           true);
    }

    public static List<SelectItem> getActiveMeterFlagBulkDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getActiveMeterFlagBulkList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Reason Code Util

    public static List<SelectItem> getReasonCodeDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getReasonCodeList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Street Type Util

    public static final List<SelectItem> STREET_TYPE_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("ON"));
                add(new SelectItem("OFF"));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Parity Digit Position Util

    public static final List<SelectItem> PARITY_DIGIT_POSITION_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem(new Integer(2)));
                add(new SelectItem(new Integer(3)));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Time Limit Util
    //20111207-01 Added
    public static List<SelectItem> getTimeLimitDisplayList() {
        // return getAllowedValuesDisplayList(AllowedValuesRetriever.getTimeLimitList(), false);
        // 20111208-01 use new method to return integer values list
        LOGGER.debug("getMyTimeLimitDisplayList() - calling getIntergerAllowedValuesDisplayList");
        return getIntegerAllowedValuesDisplayList(AllowedValuesRetriever.getTimeLimitList());
    }

/*
    public static final List<SelectItem> TIME_LIMIT_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem(new Integer(0)));
                add(new SelectItem(new Integer(10)));
                add(new SelectItem(new Integer(15)));
                add(new SelectItem(new Integer(20)));
                add(new SelectItem(new Integer(30)));
                add(new SelectItem(new Integer(60)));
                add(new SelectItem(new Integer(120)));
                add(new SelectItem(new Integer(180)));
                add(new SelectItem(new Integer(240)));
                add(new SelectItem(new Integer(540))); // 20111122-01
                add(new SelectItem(new Integer(600)));
                add(new SelectItem(new Integer(720)));
                add(new SelectItem(new Integer(1440)));
            }
        });
*/
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Pre Payment Time Util

    public static List<SelectItem> getPrepaymentTimeList() {
        List<SelectItem> prepaymentTimeList = new ArrayList<SelectItem>();

        prepaymentTimeList.add(new SelectItem("", "--"));

        for (AllowedValuesDTO allowedValue :
             AllowedValuesRetriever.getPrepaymentTimeList()) {

            prepaymentTimeList.add(new SelectItem(TimeDisplayUtil.extractFromTimeForDisplay(allowedValue.getColumnValue())));

        }

        return prepaymentTimeList;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS
    
    // 20111208-01 Added 
    private static List<SelectItem> getIntegerAllowedValuesDisplayList(List<AllowedValuesDTO> allowedValuesList) {

        LOGGER.debug("getIntergerAllowedValuesDisplayList() is called");
        List<SelectItem> displayList = new ArrayList<SelectItem>();

        for (AllowedValuesDTO allowedValue : allowedValuesList) {
            Integer intValue = Integer.valueOf(allowedValue.getColumnValue());
            LOGGER.debug("intValue is: " + intValue.toString());
            displayList.add(new SelectItem(intValue));
        }
        return displayList;
    }

    private static List<SelectItem> getAllowedValuesDisplayList(List<AllowedValuesDTO> allowedValuesList,
                                                                boolean addDescription) {
        List<SelectItem> displayList = new ArrayList<SelectItem>();

        for (AllowedValuesDTO allowedValue : allowedValuesList) {

            StringBuffer label =
                new StringBuffer(allowedValue.getColumnValue());

            if (addDescription) {
                label.append(" - ");
                label.append(allowedValue.getDescription());
            }

            displayList.add(new SelectItem(allowedValue.getColumnValue(),
                                           label.toString()));

        }

        return displayList;
    }
}
