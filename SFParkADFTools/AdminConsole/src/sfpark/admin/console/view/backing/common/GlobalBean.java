package sfpark.admin.console.view.backing.common;

import sfpark.admin.console.view.backing.BaseBean;

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
}
