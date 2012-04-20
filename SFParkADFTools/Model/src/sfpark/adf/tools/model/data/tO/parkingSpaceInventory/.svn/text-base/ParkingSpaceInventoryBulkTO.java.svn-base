package sfpark.adf.tools.model.data.tO.parkingSpaceInventory;

import java.sql.Date;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;
import sfpark.adf.tools.model.data.tO.BaseTO;
import sfpark.adf.tools.utilities.generic.ObjectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class ParkingSpaceInventoryBulkTO extends BaseTO {
    public ParkingSpaceInventoryBulkTO() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(ParkingSpaceInventoryBulkTO originalTO) {

        if ((ObjectUtil.getNullSafe(this.isToBeUpdatedSensorFlag()) ==
             ObjectUtil.getNullSafe(originalTO.isToBeUpdatedSensorFlag())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedActiveMeterFlag()) ==
             ObjectUtil.getNullSafe(originalTO.isToBeUpdatedActiveMeterFlag())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedReasonCode()) ==
             ObjectUtil.getNullSafe(originalTO.isToBeUpdatedReasonCode())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedCapColor()) ==
             ObjectUtil.getNullSafe(originalTO.isToBeUpdatedCapColor())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedLegislation()) ==
             ObjectUtil.getNullSafe(originalTO.isToBeUpdatedLegislation())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedWorkOrder()) ==
             ObjectUtil.getNullSafe(originalTO.isToBeUpdatedWorkOrder())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedComments()) ==
             ObjectUtil.getNullSafe(originalTO.isToBeUpdatedComments())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedMeterDetails()) ==
             ObjectUtil.getNullSafe(originalTO.isToBeUpdatedMeterDetails()))) {

            return true;
        }

        return false;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    private static final List<String> invalidCapColorStrings =
        Arrays.asList("Black", "Brown");

    public static List<String> getInvalidCapColorStrings() {
        return invalidCapColorStrings;
    }

    public boolean isInvalidCapColor() {
        return isInvalidCapColor(this.getCapColor());
    }

    public boolean isInvalidCapColor(String capColor) {
        for (String string : invalidCapColorStrings) {
            if (StringUtil.areEqual(capColor, string)) {
                return true;
            }
        }

        return false;
    }

    public void setAllBoolean(boolean value) {
        this.setToBeUpdatedSensorFlag(value);
        this.setToBeUpdatedActiveMeterFlag(value);
        this.setToBeUpdatedReasonCode(value);
        this.setToBeUpdatedCapColor(value);
        this.setToBeUpdatedLegislation(value);
        this.setToBeUpdatedWorkOrder(value);
        this.setToBeUpdatedComments(value);
        this.setToBeUpdatedMeterDetails(value);
    }

    public boolean isUnmetered() {
        return StringUtil.areEqual(getActiveMeterFlag(), "U");
    }

    public void setDisplayMeterDetails(MeterModelsDO meterModelsDO) {

        setMeterDetails(meterModelsDO);

        if (meterModelsDO.getMeterType().isMultiSpace()) {
            // So that the UI does not complain about invalid data
            setMSPayStationID("000-00000");
        } else {
            setMSPayStationID("-");
        }
    }

    public void updateMeterStatus(boolean updateFlag, String activeMeterFlag,
                                  MeterModelsDO meterModelsDO) {

        setToBeUpdatedActiveMeterFlag(updateFlag);
        setActiveMeterFlag(activeMeterFlag);

        setToBeUpdatedMeterDetails(updateFlag);
        setDisplayMeterDetails(meterModelsDO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private List<String> ParkingSpaceIDList;

    private boolean ToBeUpdatedSensorFlag;
    private String SensorFlag;

    private boolean ToBeUpdatedActiveMeterFlag;
    private String ActiveMeterFlag;

    private boolean ToBeUpdatedReasonCode;
    private String ReasonCode;

    private boolean ToBeUpdatedCapColor;
    private String CapColor;

    private boolean ToBeUpdatedLegislation;
    private String LegislationReference;
    private Date LegislationDate;

    private boolean ToBeUpdatedWorkOrder;
    private String WorkOrder;

    private boolean ToBeUpdatedComments;
    private String Comments;

    private boolean ToBeUpdatedMeterDetails;
    private MeterModelsDO MeterDetails;
    private String MSPayStationID;

    public void setParkingSpaceIDList(List<String> ParkingSpaceIDList) {
        this.ParkingSpaceIDList = ParkingSpaceIDList;
    }

    public List<String> getParkingSpaceIDList() {
        return ParkingSpaceIDList;
    }

    public void setToBeUpdatedSensorFlag(boolean ToBeUpdatedSensorFlag) {
        this.ToBeUpdatedSensorFlag = ToBeUpdatedSensorFlag;
    }

    public boolean isToBeUpdatedSensorFlag() {
        return ToBeUpdatedSensorFlag;
    }

    public void setSensorFlag(String SensorFlag) {
        this.SensorFlag = SensorFlag;
    }

    public String getSensorFlag() {
        return SensorFlag;
    }

    public void setToBeUpdatedActiveMeterFlag(boolean ToBeUpdatedActiveMeterFlag) {
        this.ToBeUpdatedActiveMeterFlag = ToBeUpdatedActiveMeterFlag;
    }

    public boolean isToBeUpdatedActiveMeterFlag() {
        return ToBeUpdatedActiveMeterFlag;
    }

    public void setActiveMeterFlag(String ActiveMeterFlag) {
        this.ActiveMeterFlag = ActiveMeterFlag;
    }

    public String getActiveMeterFlag() {
        return ActiveMeterFlag;
    }

    public void setToBeUpdatedReasonCode(boolean ToBeUpdatedReasonCode) {
        this.ToBeUpdatedReasonCode = ToBeUpdatedReasonCode;
    }

    public boolean isToBeUpdatedReasonCode() {
        return ToBeUpdatedReasonCode;
    }

    public void setReasonCode(String ReasonCode) {
        this.ReasonCode = ReasonCode;
    }

    public String getReasonCode() {
        return ReasonCode;
    }

    public void setToBeUpdatedCapColor(boolean ToBeUpdatedCapColor) {
        this.ToBeUpdatedCapColor = ToBeUpdatedCapColor;
    }

    public boolean isToBeUpdatedCapColor() {
        return ToBeUpdatedCapColor;
    }

    public void setCapColor(String CapColor) {
        this.CapColor = CapColor;
    }

    public String getCapColor() {
        return CapColor;
    }

    public void setToBeUpdatedLegislation(boolean ToBeUpdatedLegislation) {
        this.ToBeUpdatedLegislation = ToBeUpdatedLegislation;
    }

    public boolean isToBeUpdatedLegislation() {
        return ToBeUpdatedLegislation;
    }

    public void setLegislationReference(String LegislationReference) {
        this.LegislationReference = LegislationReference;
    }

    public String getLegislationReference() {
        return LegislationReference;
    }

    public void setLegislationDate(Date LegislationDate) {
        this.LegislationDate = LegislationDate;
    }

    public Date getLegislationDate() {
        return LegislationDate;
    }

    public void setToBeUpdatedWorkOrder(boolean ToBeUpdatedWorkOrder) {
        this.ToBeUpdatedWorkOrder = ToBeUpdatedWorkOrder;
    }

    public boolean isToBeUpdatedWorkOrder() {
        return ToBeUpdatedWorkOrder;
    }

    public void setWorkOrder(String WorkOrder) {
        this.WorkOrder = WorkOrder;
    }

    public String getWorkOrder() {
        return WorkOrder;
    }

    public void setToBeUpdatedComments(boolean ToBeUpdatedComments) {
        this.ToBeUpdatedComments = ToBeUpdatedComments;
    }

    public boolean isToBeUpdatedComments() {
        return ToBeUpdatedComments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getComments() {
        return Comments;
    }

    public void setToBeUpdatedMeterDetails(boolean ToBeUpdatedMeterDetails) {
        this.ToBeUpdatedMeterDetails = ToBeUpdatedMeterDetails;
    }

    public boolean isToBeUpdatedMeterDetails() {
        return ToBeUpdatedMeterDetails;
    }

    public void setMeterDetails(MeterModelsDO MeterDetails) {
        this.MeterDetails = MeterDetails;
    }

    public MeterModelsDO getMeterDetails() {
        return MeterDetails;
    }

    public void setMSPayStationID(String MSPayStationID) {
        this.MSPayStationID = MSPayStationID;
    }

    public String getMSPayStationID() {
        return MSPayStationID;
    }
}
