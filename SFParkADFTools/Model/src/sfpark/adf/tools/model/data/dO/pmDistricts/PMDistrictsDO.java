package sfpark.adf.tools.model.data.dO.pmDistricts;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class PMDistrictsDO extends BaseDO {

    public static String getDatabaseTableName() {
        return "PM_DISTRICTS";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public PMDistrictsDO() {
        super();
    }

    private PMDistrictsDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setPMDistrictID(resultSet.getString(PM_DISTRICT_ID));
        this.setPMDistrictName(resultSet.getString(PM_DISTRICT_NAME));

    }

    public static final String PM_DISTRICT_ID = "PM_DISTRICT_ID";
    public static final String PM_DISTRICT_NAME = "PM_DISTRICT_NAME";
    public static final String AREA_TYPE = "AREA_TYPE";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(PM_DISTRICT_ID, PM_DISTRICT_NAME);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static PMDistrictsDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new PMDistrictsDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String PMDistrictID;
    private String PMDistrictName;

    public void setPMDistrictID(String PMDistrictID) {
        this.PMDistrictID = PMDistrictID;
    }

    public String getPMDistrictID() {
        return PMDistrictID;
    }

    public void setPMDistrictName(String PMDistrictName) {
        this.PMDistrictName = PMDistrictName;
    }

    public String getPMDistrictName() {
        return PMDistrictName;
    }
}
