package sfpark.adf.tools.model.data.dto.parkingSpaceInventory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.blockface.BlockfaceDO;
import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;
import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.utilities.generic.GeoSpaceUtil;
import sfpark.adf.tools.utilities.generic.ObjectUtil;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120522-01 Mark Piller - Oracle Consulting  Add logic to preserve Multi Space Pay Station ID and Multi Space Number
 *                                              Added a method constructor for setDisplayMeterDetails()
 */

public class ParkingSpaceInventoryDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "PARKING_SPACE_INVENTORY";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public ParkingSpaceInventoryDTO() {
        super();
    }

    private ParkingSpaceInventoryDTO(ParkingSpaceInventoryDTO DTO) {
        super(DTO);

        this.setParkingSpaceID(DTO.getParkingSpaceID());
        this.setPostID(DTO.getPostID());
        this.setMSPayStationID(DTO.getMSPayStationID());
        this.setMSSpaceNum(DTO.getMSSpaceNum());
        this.setSensorFlag(DTO.getSensorFlag());
        this.setOnOffStreetType(DTO.getOnOffStreetType());
        this.setOSPID(DTO.getOSPID());
        this.setJurisdiction(DTO.getJurisdiction());
        this.setPMDistrictID(DTO.getPMDistrictID());
        this.setBlockfaceID(DTO.getBlockfaceID());
        this.setActiveMeterFlag(DTO.getActiveMeterFlag());
        this.setReasonCode(DTO.getReasonCode());

        this.setMeterDetails(DTO.getMeterDetails());

        this.setCapColor(DTO.getCapColor());
        this.setPCOBeat(DTO.getPCOBeat());
        this.setOldRateArea(DTO.getOldRateArea());
        this.setStreetID(DTO.getStreetID());
        this.setStreetName(DTO.getStreetName());
        this.setStreetNum(DTO.getStreetNum());
        this.setParityDigitPosition(DTO.getParityDigitPosition());
        this.setCNNID(DTO.getCNNID());
        this.setOrientation(DTO.getOrientation());
        this.setLongitude(DTO.getLongitude());
        this.setLatitude(DTO.getLatitude());
        this.setLegislationReference(DTO.getLegislationReference());
        this.setLegislationDate(DTO.getLegislationDate());
        this.setWorkOrder(DTO.getWorkOrder());
        this.setComments(DTO.getComments());

    }

    private ParkingSpaceInventoryDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setParkingSpaceID(resultSet.getString(PARKING_SPACE_ID));
        this.setPostID(resultSet.getString(POST_ID));
        this.setMSPayStationID(resultSet.getString(MS_PAY_STATION_ID));
        this.setMSSpaceNum(resultSet.getInt(MS_SPACE_NUM));
        this.setSensorFlag(resultSet.getString(SENSOR_FLAG));
        this.setOnOffStreetType(resultSet.getString(ON_OFFSTREET_TYPE));
        this.setOSPID(resultSet.getString(OSP_ID));
        this.setJurisdiction(resultSet.getString(JURISDICTION));
        this.setPMDistrictID(resultSet.getInt(PM_DISTRICT_ID));
        this.setBlockfaceID(resultSet.getString(BLOCKFACE_ID));
        this.setActiveMeterFlag(resultSet.getString(ACTIVE_METER_FLAG));
        this.setReasonCode(resultSet.getString(REASON_CODE));

        this.setMeterDetails(MeterModelsDO.extract(resultSet));

        this.setCapColor(resultSet.getString(CAP_COLOR));
        this.setPCOBeat(resultSet.getString(PCO_BEAT));
        this.setOldRateArea(resultSet.getString(OLD_RATE_AREA));
        this.setStreetID(resultSet.getInt(STREET_ID));
        this.setStreetName(resultSet.getString(STREET_NAME));
        this.setStreetNum(resultSet.getInt(STREET_NUM));
        this.setParityDigitPosition(resultSet.getInt(PARITY_DIGIT_POSITION));
        this.setCNNID(resultSet.getString(STREET_SEG_CTRLN_ID));
        this.setOrientation(resultSet.getInt(ORIENTATION));
        this.setLongitude(resultSet.getString(LONGITUDE));
        this.setLatitude(resultSet.getString(LATITUDE));
        this.setLegislationReference(resultSet.getString(LEGISLATION_REF));
        this.setLegislationDate(resultSet.getDate(LEGISLATION_DT));
        this.setWorkOrder(resultSet.getString(WORK_ORDER));
        this.setComments(resultSet.getString(COMMENTS));

    }

    public static final String PARKING_SPACE_ID = "PARKING_SPACE_ID";
    public static final String POST_ID = "POST_ID";
    public static final String MS_PAY_STATION_ID = "MS_PAY_STATION_ID";
    public static final String MS_SPACE_NUM = "MS_SPACE_NUM";
    public static final String SENSOR_FLAG = "SENSOR_FLAG";
    public static final String ON_OFFSTREET_TYPE = "ON_OFFSTREET_TYPE";
    public static final String OSP_ID = "OSP_ID";
    public static final String JURISDICTION = "JURISDICTION";
    public static final String PM_DISTRICT_ID = "PM_DISTRICT_ID";
    public static final String BLOCKFACE_ID = "BLOCKFACE_ID";
    public static final String ACTIVE_METER_FLAG = "ACTIVE_METER_FLAG";
    public static final String REASON_CODE = "REASON_CODE";
    public static final String SMART_METER_FLAG = "SMART_METER_FLAG";
    public static final String METER_TYPE = "METER_TYPE";
    public static final String METER_VENDOR = "METER_VENDOR";
    public static final String METER_MODEL = "METER_MODEL";
    public static final String CAP_COLOR = "CAP_COLOR";
    public static final String PCO_BEAT = "PCO_BEAT";
    public static final String OLD_RATE_AREA = "OLD_RATE_AREA";
    public static final String STREET_ID = "STREET_ID";
    public static final String STREET_NAME = "STREET_NAME";
    public static final String STREET_NUM = "STREET_NUM";
    public static final String PARITY_DIGIT_POSITION = "PARITY_DIGIT_POSITION";
    public static final String STREET_SEG_CTRLN_ID = "STREET_SEG_CTRLN_ID";
    public static final String ORIENTATION = "ORIENTATION";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String GEOM = "GEOM";
    public static final String LEGISLATION_REF = "LEGISLATION_REF";
    public static final String LEGISLATION_DT = "LEGISLATION_DT";
    public static final String WORK_ORDER = "WORK_ORDER";
    public static final String COMMENTS = "COMMENTS";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(PARKING_SPACE_ID, POST_ID, MS_PAY_STATION_ID,
                      MS_SPACE_NUM, SENSOR_FLAG, ON_OFFSTREET_TYPE, OSP_ID,
                      JURISDICTION, PM_DISTRICT_ID, BLOCKFACE_ID,
                      ACTIVE_METER_FLAG, REASON_CODE, SMART_METER_FLAG,
                      METER_TYPE, METER_VENDOR, METER_MODEL, CAP_COLOR,
                      PCO_BEAT, OLD_RATE_AREA, STREET_ID, STREET_NAME,
                      STREET_NUM, PARITY_DIGIT_POSITION, STREET_SEG_CTRLN_ID,
                      ORIENTATION, LONGITUDE, LATITUDE, LEGISLATION_REF,
                      LEGISLATION_DT, WORK_ORDER, COMMENTS, CREATED_DT,
                      LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(POST_ID, MS_PAY_STATION_ID, MS_SPACE_NUM, SENSOR_FLAG,
                      ON_OFFSTREET_TYPE, OSP_ID, JURISDICTION, PM_DISTRICT_ID,
                      BLOCKFACE_ID, ACTIVE_METER_FLAG, REASON_CODE,
                      SMART_METER_FLAG, METER_TYPE, METER_VENDOR, METER_MODEL,
                      CAP_COLOR, PCO_BEAT, OLD_RATE_AREA, STREET_ID,
                      STREET_NAME, STREET_NUM, PARITY_DIGIT_POSITION,
                      STREET_SEG_CTRLN_ID, ORIENTATION, LONGITUDE, LATITUDE,
                      GEOM, LEGISLATION_REF, LEGISLATION_DT, WORK_ORDER,
                      COMMENTS, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(POST_ID, MS_PAY_STATION_ID, MS_SPACE_NUM, SENSOR_FLAG,
                      PM_DISTRICT_ID, BLOCKFACE_ID, ACTIVE_METER_FLAG,
                      REASON_CODE, SMART_METER_FLAG, METER_TYPE, METER_VENDOR,
                      METER_MODEL, CAP_COLOR, PCO_BEAT, OLD_RATE_AREA,
                      STREET_ID, STREET_NAME, STREET_NUM,
                      PARITY_DIGIT_POSITION, STREET_SEG_CTRLN_ID, ORIENTATION,
                      LONGITUDE, LATITUDE, GEOM, LEGISLATION_REF,
                      LEGISLATION_DT, WORK_ORDER, COMMENTS, LAST_UPD_USER,
                      LAST_UPD_PGM);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    public static List<String> getAttributeListForInsert() {
        return AttributeListForInsert;
    }

    public static List<String> getAttributeListForUpdate() {
        return AttributeListForUpdate;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static ParkingSpaceInventoryDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new ParkingSpaceInventoryDTO(resultSet);
    }

    public static ParkingSpaceInventoryDTO copy(ParkingSpaceInventoryDTO DTO) {

        if (DTO == null) {
            return null;
        }

        return new ParkingSpaceInventoryDTO(DTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void setBlockfaceDO(BlockfaceDO blockfaceDO) {
        this.setBlockfaceID(blockfaceDO.getBlockfaceID());
        this.setPMDistrictID(blockfaceDO.getPMDistrictID());
        this.setStreetID(blockfaceDO.getStreetID());
        this.setStreetName(blockfaceDO.getStreetName());
        this.setOrientation(GeoSpaceUtil.getOrientationValueFromDirection(blockfaceDO.getBlockfaceOrientation()));
    }

    public void setLatLong(String latitude, String longitude) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public boolean isSameAs(ParkingSpaceInventoryDTO originalDTO) {

        if (StringUtil.areEqual(this.getPostID(), originalDTO.getPostID()) &&
            StringUtil.areEqual(this.getOnOffStreetType(),
                                originalDTO.getOnOffStreetType()) &&
            StringUtil.areEqual(this.getOSPID(), originalDTO.getOSPID()) &&
            StringUtil.areEqual(this.getJurisdiction(),
                                originalDTO.getJurisdiction()) &&
            StringUtil.areEqual(this.getSensorFlag(),
                                originalDTO.getSensorFlag()) &&
            StringUtil.areEqual(this.getCapColor(),
                                originalDTO.getCapColor()) &&
            StringUtil.areEqual(this.getActiveMeterFlag(),
                                originalDTO.getActiveMeterFlag()) &&
            StringUtil.areEqual(this.getReasonCode(),
                                originalDTO.getReasonCode()) &&
            StringUtil.areEqual(this.getMeterDetails().getMeterVendor(),
                                originalDTO.getMeterDetails().getMeterVendor()) &&
            StringUtil.areEqual(this.getMeterDetails().getMeterModel(),
                                originalDTO.getMeterDetails().getMeterModel()) &&
            ObjectUtil.getNullSafe(this.getMeterDetails().getMeterType()).equals(ObjectUtil.getNullSafe(originalDTO.getMeterDetails().getMeterType())) &&
            ObjectUtil.getNullSafe(this.getMeterDetails().getSmartMeterFlag()).equals(ObjectUtil.getNullSafe(originalDTO.getMeterDetails().getSmartMeterFlag())) &&
            StringUtil.areEqual(this.getMSPayStationID(),
                                originalDTO.getMSPayStationID()) &&
            (ObjectUtil.getNullSafe(this.getMSSpaceNum()) ==
             ObjectUtil.getNullSafe(originalDTO.getMSSpaceNum())) &&
            StringUtil.areEqual(this.getOldRateArea(),
                                originalDTO.getOldRateArea()) &&
            StringUtil.areEqual(this.getPCOBeat(), originalDTO.getPCOBeat()) &&
            StringUtil.areEqual(this.getBlockfaceID(),
                                originalDTO.getBlockfaceID()) &&
            (ObjectUtil.getNullSafe(this.getStreetID()) ==
             ObjectUtil.getNullSafe(originalDTO.getStreetID())) &&
            StringUtil.areEqual(this.getStreetName(),
                                originalDTO.getStreetName()) &&
            (ObjectUtil.getNullSafe(this.getPMDistrictID()) ==
             ObjectUtil.getNullSafe(originalDTO.getPMDistrictID())) &&
            (ObjectUtil.getNullSafe(this.getOrientation()) ==
             ObjectUtil.getNullSafe(originalDTO.getOrientation())) &&
            StringUtil.areEqual(this.getCNNID(), originalDTO.getCNNID()) &&
            (ObjectUtil.getNullSafe(this.getStreetNum()) ==
             ObjectUtil.getNullSafe(originalDTO.getStreetNum())) &&
            (ObjectUtil.getNullSafe(this.getParityDigitPosition()) ==
             ObjectUtil.getNullSafe(originalDTO.getParityDigitPosition())) &&
            StringUtil.areEqual(this.getLegislationReference(),
                                originalDTO.getLegislationReference()) &&
            SQLDateUtil.areEqual(this.getLegislationDate(),
                                 originalDTO.getLegislationDate()) &&
            StringUtil.areEqual(this.getWorkOrder(),
                                originalDTO.getWorkOrder()) &&
            StringUtil.areEqual(this.getComments(),
                                originalDTO.getComments()) &&
            StringUtil.areEqual(this.getLongitude(),
                                originalDTO.getLongitude()) &&
            StringUtil.areEqual(this.getLatitude(),
                                originalDTO.getLatitude())) {

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

    public boolean isDeleted() {
        return StringUtil.areEqual(getCapColor(), "-");
    }

    public void setDisplayMeterDetails(MeterModelsDO meterModelsDO) {
        setMeterDetails(meterModelsDO);

        if (meterModelsDO.getMeterType().isMultiSpace()) {
            setMSPayStationID(getPostID());
            setMSSpaceNum(1);
        } else {
            setMSPayStationID("-");
            setMSSpaceNum(0);
        }
    }

    // 20120522-01 added
    // Needed additional parameters to preserve Multi Space Pay Station ID and Multi Space Number
    // whenever changing the Meter Model but the From space is Multi Space and the To space is Multi Space
    public void setDisplayMeterDetails(MeterModelsDO meterModelsDO, String msPayStationId, int msNumber){
        setMeterDetails(meterModelsDO);

        if (meterModelsDO.getMeterType().isMultiSpace()) {
            setMSPayStationID(msPayStationId);
            setMSSpaceNum(msNumber);
        } else {
            setMSPayStationID("-");
            setMSSpaceNum(0);
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String ParkingSpaceID;
    private String PostID;
    private String MSPayStationID;
    private int MSSpaceNum;
    private String SensorFlag;
    private String OnOffStreetType;
    private String OSPID;
    private String Jurisdiction;
    private int PMDistrictID;
    private String BlockfaceID;
    private String ActiveMeterFlag;
    private String ReasonCode;
    private MeterModelsDO MeterDetails;
    private String CapColor;
    private String PCOBeat;
    private String OldRateArea;
    private int StreetID;
    private String StreetName;
    private int StreetNum;
    private int ParityDigitPosition;
    private String CNNID;
    private int Orientation;
    private String Longitude;
    private String Latitude;
    private String LegislationReference;
    private Date LegislationDate;
    private String WorkOrder;
    private String Comments;

    public void setParkingSpaceID(String ParkingSpaceID) {
        this.ParkingSpaceID = ParkingSpaceID;
    }

    public String getParkingSpaceID() {
        return ParkingSpaceID;
    }

    public void setPostID(String PostID) {
        this.PostID = PostID;
    }

    public String getPostID() {
        return PostID;
    }

    public void setMSPayStationID(String MSPayStationID) {
        this.MSPayStationID = MSPayStationID;
    }

    public String getMSPayStationID() {
        return MSPayStationID;
    }

    public void setMSSpaceNum(int MSSpaceNum) {
        this.MSSpaceNum = MSSpaceNum;
    }

    public int getMSSpaceNum() {
        return MSSpaceNum;
    }

    public void setSensorFlag(String SensorFlag) {
        this.SensorFlag = SensorFlag;
    }

    public String getSensorFlag() {
        return SensorFlag;
    }

    public void setOnOffStreetType(String OnOffStreetType) {
        this.OnOffStreetType = OnOffStreetType;
    }

    public String getOnOffStreetType() {
        return OnOffStreetType;
    }

    public void setOSPID(String OSPID) {
        this.OSPID = OSPID;
    }

    public String getOSPID() {
        return OSPID;
    }

    public void setJurisdiction(String Jurisdiction) {
        this.Jurisdiction = Jurisdiction;
    }

    public String getJurisdiction() {
        return Jurisdiction;
    }

    public void setPMDistrictID(int PMDistrictID) {
        this.PMDistrictID = PMDistrictID;
    }

    public int getPMDistrictID() {
        return PMDistrictID;
    }

    public void setBlockfaceID(String BlockfaceID) {
        this.BlockfaceID = BlockfaceID;
    }

    public String getBlockfaceID() {
        return BlockfaceID;
    }

    public void setActiveMeterFlag(String ActiveMeterFlag) {
        this.ActiveMeterFlag = ActiveMeterFlag;
    }

    public String getActiveMeterFlag() {
        return ActiveMeterFlag;
    }

    public void setReasonCode(String ReasonCode) {
        this.ReasonCode = ReasonCode;
    }

    public String getReasonCode() {
        return ReasonCode;
    }

    public void setMeterDetails(MeterModelsDO MeterDetails) {
        this.MeterDetails = MeterDetails;
    }

    public MeterModelsDO getMeterDetails() {
        return MeterDetails;
    }

    public void setCapColor(String CapColor) {
        this.CapColor = CapColor;
    }

    public String getCapColor() {
        return CapColor;
    }

    public void setPCOBeat(String PCOBeat) {
        this.PCOBeat = PCOBeat;
    }

    public String getPCOBeat() {
        return PCOBeat;
    }

    public void setOldRateArea(String OldRateArea) {
        this.OldRateArea = OldRateArea;
    }

    public String getOldRateArea() {
        return OldRateArea;
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

    public void setStreetNum(int StreetNum) {
        this.StreetNum = StreetNum;
    }

    public int getStreetNum() {
        return StreetNum;
    }

    public void setParityDigitPosition(int ParityDigitPosition) {
        this.ParityDigitPosition = ParityDigitPosition;
    }

    public int getParityDigitPosition() {
        return ParityDigitPosition;
    }

    public void setCNNID(String CNNID) {
        this.CNNID = CNNID;
    }

    public String getCNNID() {
        return CNNID;
    }

    public void setOrientation(int Orientation) {
        this.Orientation = Orientation;
    }

    public int getOrientation() {
        return Orientation;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLatitude() {
        return Latitude;
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

    public void setWorkOrder(String WorkOrder) {
        this.WorkOrder = WorkOrder;
    }

    public String getWorkOrder() {
        return WorkOrder;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getComments() {
        return Comments;
    }
}
