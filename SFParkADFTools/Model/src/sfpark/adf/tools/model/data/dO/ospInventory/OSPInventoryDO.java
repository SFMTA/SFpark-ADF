package sfpark.adf.tools.model.data.dO.ospInventory;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class OSPInventoryDO extends BaseDO {
    public OSPInventoryDO() {
        super();
    }

    private OSPInventoryDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setOSPID(resultSet.getString(OSP_ID));
        this.setFacilityName(resultSet.getString(FACILITY_NAME));
        this.setCNNID(resultSet.getString(CNN));
    }

    public static final String OSP_ID = "OSP_ID";
    public static final String FACILITY_NAME = "FACILITY_NAME";
    public static final String CNN = "STREET_SEG_CTRLN_ID";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(OSP_ID, FACILITY_NAME, CNN);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static OSPInventoryDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new OSPInventoryDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String OSPID;
    private String FacilityName;
    private String CNNID;

    public void setOSPID(String OSPID) {
        this.OSPID = OSPID;
    }

    public String getOSPID() {
        return OSPID;
    }

    public void setFacilityName(String FacilityName) {
        this.FacilityName = FacilityName;
    }

    public String getFacilityName() {
        return FacilityName;
    }

    public void setCNNID(String CNNID) {
        this.CNNID = CNNID;
    }

    public String getCNNID() {
        return CNNID;
    }
}
