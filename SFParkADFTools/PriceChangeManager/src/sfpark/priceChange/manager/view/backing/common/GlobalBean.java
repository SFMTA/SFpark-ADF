package sfpark.priceChange.manager.view.backing.common;

import sfpark.adf.tools.utilities.constants.PatternString;

import sfpark.adf.tools.utilities.constants.RegularExpression;

import sfpark.priceChange.manager.view.backing.BaseBean;

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

    /**
     * Returns an Inline Style string for Section Headers used for displaying
     * headers such as 'Application', 'Realms', 'Gadgets', etc
     *
     * @return Inline Style for Section Headers
     */
    public String getSectionHeaderInlineStyle() {
        String inlineStyle = "font-size: 16px; font-weight: bold;";

        return inlineStyle;
    }

    public String getBreadCrumbInlineStyle() {
        String inlineStyle = "font-size: 16px; font-weight: bold;";

        return inlineStyle;
    }

    public String getGeneralButtonInlineStyle() {

        String inlineStyle = "width: 80px;";

        return inlineStyle;
    }

    public String getDateTimePattern() {
        return PatternString.DATE_TIME.getPattern();
    }

    public String getNumericPattern() {
        return RegularExpression.DIGITS_REGEX.getRegEx();
    }

    public String getSingleDigitPattern() {
        return RegularExpression.SINGLE_DIGIT_REGEX.getRegEx();
    }
}
