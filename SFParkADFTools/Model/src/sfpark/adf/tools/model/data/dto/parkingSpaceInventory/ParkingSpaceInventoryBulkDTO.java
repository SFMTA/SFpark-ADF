package sfpark.adf.tools.model.data.dto.parkingSpaceInventory;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;
import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.utilities.generic.ObjectUtil;

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
    public static final String CAP_COLOR = ParkingSpaceInventoryDTO.CAP_COLOR;
    public static final String PCO_BEAT = ParkingSpaceInventoryDTO.PCO_BEAT;
    public static final String OLD_RATE_AREA =
        ParkingSpaceInventoryDTO.OLD_RATE_AREA;
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

        if (this.isToBeUpdatedCapColor()) {
            AttributeListForUpdate.add(CAP_COLOR);
        }

        if (this.isToBeUpdatedPCOBeat()) {
            AttributeListForUpdate.add(PCO_BEAT);
        }

        if (this.isToBeUpdatedOldRateArea()) {
            AttributeListForUpdate.add(OLD_RATE_AREA);
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
            (ObjectUtil.getNullSafe(this.isToBeUpdatedCapColor()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedCapColor())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedPCOBeat()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedPCOBeat())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedOldRateArea()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedOldRateArea())) &&
            (ObjectUtil.getNullSafe(this.isToBeUpdatedMeterDetails()) ==
             ObjectUtil.getNullSafe(originalDTO.isToBeUpdatedMeterDetails()))) {

            return true;
        }

        return false;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DISPLAY PURPOSES ONLY

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

    private boolean ToBeUpdatedCapColor;
    private String CapColor;

    private boolean ToBeUpdatedPCOBeat;
    private String PCOBeat;

    private boolean ToBeUpdatedOldRateArea;
    private String OldRateArea;

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

    public void setToBeUpdatedPCOBeat(boolean ToBeUpdatedPCOBeat) {
        this.ToBeUpdatedPCOBeat = ToBeUpdatedPCOBeat;
    }

    public boolean isToBeUpdatedPCOBeat() {
        return ToBeUpdatedPCOBeat;
    }

    public void setPCOBeat(String PCOBeat) {
        this.PCOBeat = PCOBeat;
    }

    public String getPCOBeat() {
        return PCOBeat;
    }

    public void setToBeUpdatedOldRateArea(boolean ToBeUpdatedOldRateArea) {
        this.ToBeUpdatedOldRateArea = ToBeUpdatedOldRateArea;
    }

    public boolean isToBeUpdatedOldRateArea() {
        return ToBeUpdatedOldRateArea;
    }

    public void setOldRateArea(String OldRateArea) {
        this.OldRateArea = OldRateArea;
    }

    public String getOldRateArea() {
        return OldRateArea;
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
