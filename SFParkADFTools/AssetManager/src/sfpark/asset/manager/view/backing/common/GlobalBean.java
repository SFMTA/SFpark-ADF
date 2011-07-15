package sfpark.asset.manager.view.backing.common;

import sfpark.adf.tools.utilities.constants.PatternString;
import sfpark.adf.tools.utilities.constants.RegularExpression;

import sfpark.asset.manager.view.backing.BaseBean;

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

    public String getPostIDPattern() {
        return RegularExpression.POST_ID_REGEX.getRegEx();
    }

    public String getFromTimePattern() {
        return RegularExpression.FROM_TIME_REGEX.getRegEx();
    }

    public String getToTimePattern() {
        return RegularExpression.TO_TIME_REGEX.getRegEx();
    }

    /*
     * Returns an Inline Style string for general buttons
     *
     * @return Inline Style for general buttons
     *


     * Returns an Inline Style string for buttons of the type 'commit'
     * E.g., Buttons that perform operations such as 'Save', 'Finish', 'Ok'
     *
     * @return Inline Style for 'commit' type buttons
     *
    public String getCommitButtonInlineStyle() {

        String inlineStyle =
            this.getGeneralButtonInlineStyle() + " font-weight: bold;";

        return inlineStyle;
    }


     * Returns an Inline Style string to make sure ADF components are stretched
     * breadth-wise
     *
     * @return Inline Style for ADF tags
     *
    public String getStretchWidthInlineStyle() {

        String inlineStyle = "width: 100%;";

        return inlineStyle;
    }
     */
}
