package sfpark.osp.manager.view.backing.common;

import sfpark.adf.tools.utilities.constants.PatternString;
import sfpark.adf.tools.utilities.constants.RegularExpression;

import sfpark.osp.manager.view.backing.BaseBean;

public final class GlobalBean extends BaseBean {

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

    public String getDateTimePattern() {
        return PatternString.DATE_TIME.getPattern();
    }

    public String getWebsiteURLPattern() {
        return RegularExpression.WEB_SITE_URL.getRegEx();
    }

    public String getUSPhoneNumberPattern() {
        return RegularExpression.US_PHONE_NUMBER.getRegEx();
    }

    public String getFromTimePattern() {
        return RegularExpression.FROM_TIME_REGEX.getRegEx();
    }

    public String getToTimePattern() {
        return RegularExpression.TO_TIME_REGEX.getRegEx();
    }

    public String getMaxAmountPattern() {
        return RegularExpression.POSITIVE_INTEGER.getRegEx();
    }
}
