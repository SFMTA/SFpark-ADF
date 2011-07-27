package sfpark.adf.tools.model.data.dO.parkingSpaceGroups;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class ParkingSpaceGroupsDO extends BaseDO {

    public ParkingSpaceGroupsDO(ParkingSpaceGroupsDO.GROUP_TYPE GroupType,
                                String IdentifyingGroupID,
                                List<String> ParkingSpaceIDList) {
        super();

        this.GroupType = GroupType;
        this.IdentifyingGroupID = IdentifyingGroupID;
        this.ParkingSpaceIDList = ParkingSpaceIDList;
    }

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

    private GROUP_TYPE GroupType;
    private String IdentifyingGroupID;
    private List<String> ParkingSpaceIDList;

    public void setGroupType(ParkingSpaceGroupsDO.GROUP_TYPE GroupType) {
        this.GroupType = GroupType;
    }

    public ParkingSpaceGroupsDO.GROUP_TYPE getGroupType() {
        return GroupType;
    }

    public void setIdentifyingGroupID(String IdentifyingGroupID) {
        this.IdentifyingGroupID = IdentifyingGroupID;
    }

    public String getIdentifyingGroupID() {
        return IdentifyingGroupID;
    }

    public void setParkingSpaceIDList(List<String> ParkingSpaceIDList) {
        this.ParkingSpaceIDList = ParkingSpaceIDList;
    }

    public List<String> getParkingSpaceIDList() {
        return ParkingSpaceIDList;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public enum GROUP_TYPE {
        PARKING_SPACE_GROUP,
        OSP_ID_GROUP;
    }
}
