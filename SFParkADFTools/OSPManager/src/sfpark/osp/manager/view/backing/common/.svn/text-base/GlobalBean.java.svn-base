package sfpark.osp.manager.view.backing.common;

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

    public String getWebsiteURLPattern() {
        return RegularExpression.WEB_SITE_URL.getRegEx();
    }

    public String getUSPhoneNumberPattern() {
        return RegularExpression.US_PHONE_NUMBER.getRegEx();
    }

    public String getMaxAmountPattern() {
        return RegularExpression.POSITIVE_INTEGER.getRegEx();
    }
}
