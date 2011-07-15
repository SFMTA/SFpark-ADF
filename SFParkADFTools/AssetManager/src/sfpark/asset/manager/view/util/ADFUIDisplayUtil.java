package sfpark.asset.manager.view.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dO.ospInventory.OSPInventoryDO;
import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public final class ADFUIDisplayUtil {

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
        return getAllowedValuesDisplayList(DataRepositoryUtil.getJurisdictionList(),
                                           false);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Sensor Status Util

    public static List<SelectItem> getSensorFlagDisplayList() {
        return getAllowedValuesDisplayList(DataRepositoryUtil.getSensorFlagList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Cap Color Util

    public static List<SelectItem> getCapColorDisplayList() {
        return getAllowedValuesDisplayList(DataRepositoryUtil.getCapColorList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Color Rule Applied Util

    public static List<SelectItem> getColorRuleAppliedDisplayList() {
        return getAllowedValuesDisplayList(DataRepositoryUtil.getColorRuleAppliedList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Active Meter Status Util

    public static List<SelectItem> getActiveMeterFlagDisplayList() {
        return getAllowedValuesDisplayList(DataRepositoryUtil.getActiveMeterFlagList(),
                                           true);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Reason Code Util

    public static List<SelectItem> getReasonCodeDisplayList() {
        return getAllowedValuesDisplayList(DataRepositoryUtil.getReasonCodeList(),
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
                add(new SelectItem(new Integer(600)));
                add(new SelectItem(new Integer(720)));
                add(new SelectItem(new Integer(1440)));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Pre Payment Time Util

    public static final List<Integer> PREPAYMENT_TIME_INTEGER_LIST =
        Collections.unmodifiableList(new ArrayList<Integer>() {
            {
                add(new Integer(0));
                add(new Integer(300));
                add(new Integer(400));
                add(new Integer(500));
                add(new Integer(600));
                add(new Integer(700));
                add(new Integer(800));
                add(new Integer(900));
            }
        });

    public static final List<SelectItem> PREPAYMENT_TIME_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("", "--"));
                for (Integer time : PREPAYMENT_TIME_INTEGER_LIST) {
                    add(new SelectItem(TimeDisplayUtil.extractFromTimeForDisplay(time)));
                }
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}
