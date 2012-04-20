package sfpark.rateChange.manager.view.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;

/**
 * This ADFUIDisplayUtil is ONLY for the RateChangeManager Project
 * 
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111212-01 Mark Piller - Oracle Consulting  created this class to support Rate Change Management Screens
 *                                              Modeled this class after class files with same name in AssetManager and OSPManager
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
    // Approved By and Submitted By methods
  
    // 20111212-01 Added
    public static List<SelectItem> getApprovedByDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getApprovedByList(), false);
    }
  
    // 20111212-01 Added
    public static List<SelectItem> getSubmittedByDisplayList() {
        return getAllowedValuesDisplayList(AllowedValuesRetriever.getSubmittedByList(), false);
    }


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

}
