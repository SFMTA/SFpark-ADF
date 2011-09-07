package sfpark.rateChange.manager.view.backing.common;

import sfpark.adf.tools.utilities.constants.PatternString;

import sfpark.adf.tools.utilities.constants.RegularExpression;

import sfpark.rateChange.manager.view.backing.BaseBean;

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

    public String getDatePattern() {
        return PatternString.DATE.getPattern();
    }

    public String getFileNamePattern() {
        return RegularExpression.FILE_NAME.getRegEx();
    }
}
