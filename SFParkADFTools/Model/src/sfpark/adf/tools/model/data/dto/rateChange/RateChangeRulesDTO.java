package sfpark.adf.tools.model.data.dto.rateChange;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.EffectiveDateBaseDTO;

public class RateChangeRulesDTO extends EffectiveDateBaseDTO {

    public static String getDatabaseTableName() {
        return "RATE_CHANGE_RULES";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public RateChangeRulesDTO() {
        super();
    }

    private RateChangeRulesDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setRuleID(resultSet.getString(RULE_ID));
        this.setRateChangeType(resultSet.getString(RATE_CHG_TYPE));
        this.setFromOccupancy(resultSet.getInt(FROM_OCCUPANCY));
        this.setToOccupancy(resultSet.getInt(TO_OCCUPANCY));
        this.setChangeAmount(resultSet.getFloat(CHANGE_AMT));

    }

    public static final String RULE_ID = "RULE_ID";
    public static final String RATE_CHG_TYPE = "RATE_CHG_TYPE";
    public static final String FROM_OCCUPANCY = "FROM_OCCUPANCY";
    public static final String TO_OCCUPANCY = "TO_OCCUPANCY";
    public static final String CHANGE_AMT = "CHANGE_AMT";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(RULE_ID, RATE_CHG_TYPE, FROM_OCCUPANCY, TO_OCCUPANCY,
                      EFF_FROM_DT, EFF_TO_DT, CHANGE_AMT, CREATED_DT,
                      LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(RATE_CHG_TYPE, FROM_OCCUPANCY, TO_OCCUPANCY, EFF_FROM_DT,
                      EFF_TO_DT, CHANGE_AMT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(RATE_CHG_TYPE, FROM_OCCUPANCY, TO_OCCUPANCY, EFF_FROM_DT,
                      EFF_TO_DT, CHANGE_AMT, LAST_UPD_USER, LAST_UPD_PGM);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    public static List<String> getAttributeListForInsert() {
        return AttributeListForInsert;
    }

    public static List<String> getAttributeListForUpdate() {
        return AttributeListForUpdate;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static RateChangeRulesDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new RateChangeRulesDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String RuleID;
    private String RateChangeType;
    private int FromOccupancy;
    private int ToOccupancy;
    private float ChangeAmount;

    public void setRuleID(String RuleID) {
        this.RuleID = RuleID;
    }

    public String getRuleID() {
        return RuleID;
    }

    public void setRateChangeType(String RateChangeType) {
        this.RateChangeType = RateChangeType;
    }

    public String getRateChangeType() {
        return RateChangeType;
    }

    public void setFromOccupancy(int FromOccupancy) {
        this.FromOccupancy = FromOccupancy;
    }

    public int getFromOccupancy() {
        return FromOccupancy;
    }

    public void setToOccupancy(int ToOccupancy) {
        this.ToOccupancy = ToOccupancy;
    }

    public int getToOccupancy() {
        return ToOccupancy;
    }

    public void setChangeAmount(float ChangeAmount) {
        this.ChangeAmount = ChangeAmount;
    }

    public float getChangeAmount() {
        return ChangeAmount;
    }
}
