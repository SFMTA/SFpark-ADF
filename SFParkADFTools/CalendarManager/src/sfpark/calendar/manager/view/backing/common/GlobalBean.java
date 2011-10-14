package sfpark.calendar.manager.view.backing.common;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;
import sfpark.adf.tools.utilities.constants.PatternString;

import sfpark.calendar.manager.view.backing.BaseBean;

public class GlobalBean extends BaseBean {
    public GlobalBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // GETTERS

    public String getPageHeaderInlineStyle() {

        String inlineStyle = "font-size: 20px;";

        return inlineStyle;
    }

    public String getBreadCrumbInlineStyle() {
        String inlineStyle = "font-size: 16px; font-weight: bold;";

        return inlineStyle;
    }

    public String getCommitButtonInlineStyle() {

        String inlineStyle =
            getGeneralButtonInlineStyle() + " font-weight: bold;";

        return inlineStyle;
    }

    public String getGeneralButtonInlineStyle() {

        String inlineStyle = "width: 80px;";

        return inlineStyle;
    }

    public String getDateTimePattern() {
        return PatternString.DATE_TIME.getPattern();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL LIST VALUES

    public List<SelectItem> getListCalendarType() {
        List<SelectItem> displayList = new ArrayList<SelectItem>();

        for (AllowedValuesDTO allowedValuesDTO :
             AllowedValuesRetriever.getCalendarTypeList()) {
            displayList.add(new SelectItem(allowedValuesDTO.getColumnValue(),
                                           allowedValuesDTO.getDescription()));
        }

        return displayList;
    }

}
