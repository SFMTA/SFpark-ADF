package sfpark.admin.console.view.helper;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;

public final class ADFUIHelper {

    /**
     * To avoid instantiation
     */
    private ADFUIHelper() {
        super();
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
