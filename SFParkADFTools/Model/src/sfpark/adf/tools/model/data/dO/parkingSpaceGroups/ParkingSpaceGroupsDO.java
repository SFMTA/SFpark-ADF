package sfpark.adf.tools.model.data.dO.parkingSpaceGroups;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class ParkingSpaceGroupsDO extends BaseDO {
    public ParkingSpaceGroupsDO() {
        super();
    }

    public ParkingSpaceGroupsDO(String ParkingSpaceGroupID,
                                List<String> ParkingSpaceIDList) {
        super();
        
        this.ParkingSpaceGroupID = ParkingSpaceGroupID;
        this.ParkingSpaceIDList = ParkingSpaceIDList;
    }

    /*
    private ParkingSpaceGroupsDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setParkingSpaceGroupID(resultSet.getString(PS_GROUP_ID));
        this.setParkingSpaceID(resultSet.getString(PARKING_SPACE_ID));
    }
    */

    public static final String PS_GROUP_ID = "PS_GROUP_ID";
    public static final String PARKING_SPACE_ID = "PARKING_SPACE_ID";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(PS_GROUP_ID, PARKING_SPACE_ID);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /*
    public static ParkingSpaceGroupsDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new ParkingSpaceGroupsDO(resultSet);
    }
    */

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String ParkingSpaceGroupID;
    private List<String> ParkingSpaceIDList;

    public void setParkingSpaceGroupID(String ParkingSpaceGroupID) {
        this.ParkingSpaceGroupID = ParkingSpaceGroupID;
    }

    public String getParkingSpaceGroupID() {
        return ParkingSpaceGroupID;
    }

    public void setParkingSpaceIDList(List<String> ParkingSpaceIDList) {
        this.ParkingSpaceIDList = ParkingSpaceIDList;
    }

    public List<String> getParkingSpaceIDList() {
        return ParkingSpaceIDList;
    }
}
