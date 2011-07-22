package sfpark.adf.tools.model.data.dto.parkingSpaceInventory;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;
import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.utilities.generic.ObjectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

@Deprecated
public class ParkingSpaceInventoryBulkDTO extends BaseDTO {
    public ParkingSpaceInventoryBulkDTO() {
        super();
    }

    public static final String PARKING_SPACE_ID =
        ParkingSpaceInventoryDTO.PARKING_SPACE_ID;
    public static final String SENSOR_FLAG =
        ParkingSpaceInventoryDTO.SENSOR_FLAG;
    public static final String ACTIVE_METER_FLAG =
        ParkingSpaceInventoryDTO.ACTIVE_METER_FLAG;
    public static final String REASON_CODE =
        ParkingSpaceInventoryDTO.REASON_CODE;
    public static final String CAP_COLOR = ParkingSpaceInventoryDTO.CAP_COLOR;

    public static final String LEGISLATION_REF =
        ParkingSpaceInventoryDTO.LEGISLATION_REF;
    public static final String LEGISLATION_DT =
        ParkingSpaceInventoryDTO.LEGISLATION_DT;
    public static final String WORK_ORDER =
        ParkingSpaceInventoryDTO.WORK_ORDER;
    public static final String COMMENTS = ParkingSpaceInventoryDTO.COMMENTS;

    public static final String SMART_METER_FLAG =
        ParkingSpaceInventoryDTO.SMART_METER_FLAG;
    public static final String METER_TYPE =
        ParkingSpaceInventoryDTO.METER_TYPE;
    public static final String METER_VENDOR =
        ParkingSpaceInventoryDTO.METER_VENDOR;
    public static final String METER_MODEL =
        ParkingSpaceInventoryDTO.METER_MODEL;
    public static final String MS_PAY_STATION_ID =
        ParkingSpaceInventoryDTO.MS_PAY_STATION_ID;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public List<String> getAttributeListForUpdate() {

        List<String> AttributeListForUpdate = new ArrayList<String>();

        if (this.isToBeUpdatedSensorFlag()) {
            AttributeListForUpdate.add(SENSOR_FLAG);
        }

        if (this.isToBeUpdatedActiveMeterFlag()) {
            AttributeListForUpdate.add(ACTIVE_METER_FLAG);
        }

        if (this.isToBeUpdatedReasonCode()) {
            AttributeListForUpdate.add(REASON_CODE);
        }

        if (this.isToBeUpdatedCapColor()) {
            AttributeListForUpdate.add(CAP_COLOR);
        }

        if (this.isToBeUpdatedLegislation()) {
            AttributeListForUpdate.add(LEGISLATION_REF);
            AttributeListForUpdate.add(LEGISLATION_DT);
        }

        if (this.isToBeUpdatedWorkOrder()) {
            AttributeListForUpdate.add(WORK_ORDER);
        }

        if (this.isToBeUpdatedComments()) {
            AttributeListForUpdate.add(COMMENTS);
        }

        if (this.isToBeUpdatedMeterDetails()) {
            AttributeListForUpdate.add(SMART_METER_FLAG);
            AttributeListForUpdate.add(METER_TYPE);
            AttributeListForUpdate.add(METER_VENDOR);
            AttributeListForUpdate.add(METER_MODEL);
            AttributeListForUpdate.add(MS_PAY_STATION_ID);
        }

        AttributeListForUpdate.add(LAST_UPD_PGM);
        AttributeListForUpdate.add(LAST_UPD_USER);

        return AttributeListForUpdate;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(ParkingSpaceInventoryBulkDTO originalDTO) {

        if ((ObjectUtil.getNullSafe(this.isToBeUpdatedSensorFlag()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedSensorFlag())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedActiveMeterFlag()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedActiveMeterFlag())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedReasonCode()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedReasonCode())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedCapColor()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedCapColor())) &&
            (ObjectUtil.getNullSafe

            (this.isToBeUpdatedLegislation()) ==
            ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedLegislation())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedWorkOrder()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedWorkOrder())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedComments()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedComments())) &&
            (ObjectUtil.

            getNullSafe(this.isToBeUpdatedMeterDetails()) ==
            ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedMeterDetails()))) {

            return true;
        }

        return false;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DISPLAY PURPOSES ONLY

    public boolean isUnmetered() {
        return StringUtil.areEqual(getActiveMeterFlag(), "U");
    }

    public void setDisplayMeterDetails(MeterModelsDO meterModelsDO) {

        setMeterDetails(meterModelsDO);

        if (meterModelsDO.getMeterType().contains("MS")) {
            // So that the UI does not complain about invalid data
            setMSPayStationID("000-00000");
        } else {
            setMSPayStationID("-");
        }
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
