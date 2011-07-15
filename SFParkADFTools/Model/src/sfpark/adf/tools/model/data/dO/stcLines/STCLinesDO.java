package sfpark.adf.tools.model.data.dO.stcLines;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class STCLinesDO extends BaseDO {
    public STCLinesDO() {
        super();
    }

    private STCLinesDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setCNNID(resultSet.getString(CNN));
        this.setStreetName(resultSet.getString(STREETNAME));
        this.setDistance(resultSet.getString(DISTANCE));
        this.setOddAddress(resultSet.getString(ODD));
        this.setEvenAddress(resultSet.getString(EVEN));
    }

    public static final String CNN = "CNN";
    public static final String STREETNAME = "STREETNAME";
    public static final String DISTANCE = "DISTANCE";
    public static final String ODD = "ODD";
    public static final String EVEN = "EVEN";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static STCLinesDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new STCLinesDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String CNNID;
    private String StreetName;
    private String Distance;
    private String OddAddress;
    private String EvenAddress;

    public void setCNNID(String CNNID) {
        this.CNNID = CNNID;
    }

    public String getCNNID() {
        return CNNID;
    }

    public void setStreetName(String StreetName) {
        this.StreetName = StreetName;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setDistance(String Distance) {
        this.Distance = Distance;
    }

    public String getDistance() {
        return Distance;
    }

    public void setOddAddress(String OddAddress) {
        this.OddAddress = OddAddress;
    }

    public String getOddAddress() {
        return OddAddress;
    }

    public void setEvenAddress(String EvenAddress) {
        this.EvenAddress = EvenAddress;
    }

    public String getEvenAddress() {
        return EvenAddress;
    }
}
