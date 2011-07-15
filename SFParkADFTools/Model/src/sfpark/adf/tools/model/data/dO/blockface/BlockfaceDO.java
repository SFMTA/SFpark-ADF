package sfpark.adf.tools.model.data.dO.blockface;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class BlockfaceDO extends BaseDO {
    public BlockfaceDO() {
        super();
    }

    private BlockfaceDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setBlockfaceID(resultSet.getString(BLOCKFACE_ID));
        this.setPMDistrictID(resultSet.getInt(PM_DISTRICT_ID));
        this.setStreetID(resultSet.getInt(STREET_ID));
        this.setStreetName(resultSet.getString(STREET_NAME));
        this.setBlockID(resultSet.getInt(BLOCK_ID));
        this.setBlockNumber(resultSet.getInt(BLOCK_NUM));
        this.setBlockfaceOrientation(resultSet.getString(BLOCKFACE_ORIENTATION));

    }

    public static final String BLOCKFACE_ID = "BLOCKFACE_ID";
    public static final String PM_DISTRICT_ID = "PM_DISTRICT_ID";
    public static final String BLOCK_NUM = "BLOCK_NUM";
    public static final String BLOCK_ID = "BLOCK_ID";
    public static final String STREET_ID = "STREET_ID";
    public static final String STREET_NAME = "STREET_NAME";
    public static final String BLOCKFACE_ORIENTATION = "BLOCKFACE_ORIENTATION";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(BLOCKFACE_ID, PM_DISTRICT_ID, BLOCK_NUM, BLOCK_ID,
                      STREET_ID, STREET_NAME, BLOCKFACE_ORIENTATION);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static BlockfaceDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new BlockfaceDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String BlockfaceID;
    private int PMDistrictID;
    private int BlockNumber;
    private int BlockID;
    private int StreetID;
    private String StreetName;
    private String BlockfaceOrientation;

    public void setBlockfaceID(String BlockfaceID) {
        this.BlockfaceID = BlockfaceID;
    }

    public String getBlockfaceID() {
        return BlockfaceID;
    }

    public void setPMDistrictID(int PMDistrictID) {
        this.PMDistrictID = PMDistrictID;
    }

    public int getPMDistrictID() {
        return PMDistrictID;
    }

    public void setBlockNumber(int BlockNumber) {
        this.BlockNumber = BlockNumber;
    }

    public int getBlockNumber() {
        return BlockNumber;
    }

    public void setBlockID(int BlockID) {
        this.BlockID = BlockID;
    }

    public int getBlockID() {
        return BlockID;
    }

    public void setStreetID(int StreetID) {
        this.StreetID = StreetID;
    }

    public int getStreetID() {
        return StreetID;
    }

    public void setStreetName(String StreetName) {
        this.StreetName = StreetName;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setBlockfaceOrientation(String BlockfaceOrientation) {
        this.BlockfaceOrientation = BlockfaceOrientation;
    }

    public String getBlockfaceOrientation() {
        return BlockfaceOrientation;
    }
}
