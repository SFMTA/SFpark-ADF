package sfpark.adf.tools.model.data.dO.blocks;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class BlocksDO extends BaseDO {

    public static String getDatabaseTableName() {
        return "BLOCKS";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public BlocksDO() {
        super();
    }

    private BlocksDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setBlockID(resultSet.getString(BLOCK_ID));
        this.setPMDistrictID(resultSet.getInt(PM_DISTRICT_ID));
        this.setStreetName(resultSet.getString(STREET_NAME));

    }

    public static final String BLOCK_ID = "BLOCK_ID";
    public static final String PM_DISTRICT_ID = "PM_DISTRICT_ID";
    public static final String STREET_NAME = "STREET_NAME";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(BLOCK_ID, PM_DISTRICT_ID, STREET_NAME);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static BlocksDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new BlocksDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String BlockID;
    private int PMDistrictID;
    private String StreetName;

    public void setBlockID(String BlockID) {
        this.BlockID = BlockID;
    }

    public String getBlockID() {
        return BlockID;
    }

    public void setPMDistrictID(int PMDistrictID) {
        this.PMDistrictID = PMDistrictID;
    }

    public int getPMDistrictID() {
        return PMDistrictID;
    }

    public void setStreetName(String StreetName) {
        this.StreetName = StreetName;
    }

    public String getStreetName() {
        return StreetName;
    }
}
