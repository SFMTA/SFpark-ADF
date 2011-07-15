package sfpark.adf.tools.model.data.dO.rateAreas;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class RateAreasDO extends BaseDO {
    public RateAreasDO() {
        super();
    }

    private RateAreasDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setAreaName(resultSet.getString(AREA_NAME));
        this.setMCName(resultSet.getString(MC_NAME));
    }

    private static final String TableName = "RATE_AREAS";

    public static String getTableName() {
        return TableName;
    }

    public static final String AREA_NAME = "AREA_NAME";
    public static final String MC_NAME = "MC_NAME";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(AREA_NAME, MC_NAME);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static RateAreasDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new RateAreasDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String AreaName;
    private String MCName;

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setMCName(String MCName) {
        this.MCName = MCName;
    }

    public String getMCName() {
        return MCName;
    }
}
