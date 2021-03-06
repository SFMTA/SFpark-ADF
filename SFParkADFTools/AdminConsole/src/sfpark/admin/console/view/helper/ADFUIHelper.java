package sfpark.admin.console.view.helper;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;

/**
 * This ADFUIDHelper is ONLY for the AdminConsole Project
 * 
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111212-01 Mark Piller - Oracle Consulting  Added getAllowedValuesTablesList(), getAllowedValuesColumnsDisplayList()
 */

public final class ADFUIHelper {

    /**
     * To avoid instantiation
     */
    private ADFUIHelper() {
        super();
    }

    // 20111212-01 Added
    public static List<SelectItem> getAllowedValuesTablesDisplayList() {
        return getAllowedValuesTablesList(AllowedValuesRetriever.getAllowedValuesTables());
    }

    // 20111212-01 Added
    public static List<SelectItem> getAllowedValuesColumnsDisplayList(String tableName) {
        return getAllowedValuesColumnsList(AllowedValuesRetriever.getAllowedValuesColumns(tableName));
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Meter Class

    public static List<SelectItem> getMeterClassDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getMeterClassList());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Date Type

    public static List<SelectItem> getDateTypeDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getDateTypeList());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    // 20111212-01
    private static List<SelectItem> getAllowedValuesTablesList(List<String> tablesList) {
        List<SelectItem> displayList = new ArrayList<SelectItem>();
        for (String allowedValueTable : tablesList) {
            displayList.add(new SelectItem(allowedValueTable));
        }
        
        return displayList;
    }

    //20111212-01
    private static List<SelectItem> getAllowedValuesColumnsList(List<String> columnsList) {
        List<SelectItem> displayList = new ArrayList<SelectItem>();
        for (String allowedValueColumn : columnsList) {
            displayList.add(new SelectItem(allowedValueColumn));
        }
        
        return displayList;
    }

    private static List<SelectItem> getAllowedValuesDisplayList(List<AllowedValuesDTO> allowedValuesList) {
        List<SelectItem> displayList = new ArrayList<SelectItem>();

        for (AllowedValuesDTO allowedValue : allowedValuesList) {

            StringBuffer label =
                new StringBuffer(allowedValue.getColumnValue());
            label.append(" - ");
            label.append(allowedValue.getDescription());

            displayList.add(new SelectItem(allowedValue.getColumnValue(),
                                           label.toString()));

        }

        return displayList;
    }
}
