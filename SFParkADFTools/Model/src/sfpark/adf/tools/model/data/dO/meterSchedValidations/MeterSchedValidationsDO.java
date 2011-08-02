package sfpark.adf.tools.model.data.dO.meterSchedValidations;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;
import sfpark.adf.tools.model.data.helper.MeterScheduleType;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class MeterSchedValidationsDO extends BaseDO {
    public MeterSchedValidationsDO() {
        super();
    }

    public MeterSchedValidationsDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setScheduleType(MeterScheduleType.valueOf(resultSet.getString(SCHED_TYPE)));
        this.setColorRuleApplied(resultSet.getString(COLOR_RULE_APPLIED));
        this.setAltAddlDesc(resultSet.getString(ALT_ADDL_DESC));
        this.setDaysAppliedTxt(resultSet.getString(DAYS_APPLIED_TXT));

    }

    public static final String SCHED_TYPE = "SCHED_TYPE";
    public static final String COLOR_RULE_APPLIED = "COLOR_RULE_APPLIED";
    public static final String ALT_ADDL_DESC = "ALT_ADDL_DESC";
    public static final String DAYS_APPLIED_TXT = "DAYS_APPLIED_TXT";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(SCHED_TYPE, COLOR_RULE_APPLIED, ALT_ADDL_DESC,
                      DAYS_APPLIED_TXT);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static MeterSchedValidationsDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new MeterSchedValidationsDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    public boolean isColorRuleAppliedEditable() {
        if (this.getScheduleType().isScheduleTOW() ||
            this.getScheduleType().isScheduleOP()) {

            return false;
        }

        return StringUtil.isBlank(getColorRuleApplied());
    }

    public String getDisplayColorRuleApplied() {

        if (this.getScheduleType().isScheduleTOW()) {
            // No color for TOW
            return "--";
        }

        if (this.getScheduleType().isScheduleOP()) {

            return "Meter Cap Color";
        }

        String colorRuleApplied = getColorRuleApplied();

        if (StringUtil.isBlank(colorRuleApplied)) {
            return "[define color]";
        }

        return AllowedValuesProvider.getDisplayDescriptionForColorRuleApplied(colorRuleApplied);
    }

    public String getDisplayAltAddlDesc() {

        String altAddlDesc = getAltAddlDesc();

        if (StringUtil.isBlank(altAddlDesc)) {
            return "--";
        }

        return altAddlDesc;
    }

    public String getDisplayDaysAppliedTxt() {

        String daysAppliedTxt = getDaysAppliedTxt();

        if (StringUtil.isBlank(daysAppliedTxt)) {
            return "[define weekdays]";
        }

        return daysAppliedTxt;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private MeterScheduleType ScheduleType;
    private String ColorRuleApplied;
    private String AltAddlDesc;
    private String DaysAppliedTxt;

    public void setScheduleType(MeterScheduleType ScheduleType) {
        this.ScheduleType = ScheduleType;
    }

    public MeterScheduleType getScheduleType() {
        return ScheduleType;
    }

    public void setColorRuleApplied(String ColorRuleApplied) {
        this.ColorRuleApplied = ColorRuleApplied;
    }

    public String getColorRuleApplied() {
        return ColorRuleApplied;
    }

    public void setAltAddlDesc(String AltAddlDesc) {
        this.AltAddlDesc = AltAddlDesc;
    }

    public String getAltAddlDesc() {
        return AltAddlDesc;
    }

    public void setDaysAppliedTxt(String DaysAppliedTxt) {
        this.DaysAppliedTxt = DaysAppliedTxt;
    }

    public String getDaysAppliedTxt() {
        return DaysAppliedTxt;
    }
}
