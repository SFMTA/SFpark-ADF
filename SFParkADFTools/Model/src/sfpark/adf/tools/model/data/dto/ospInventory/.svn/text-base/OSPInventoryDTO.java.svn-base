package sfpark.adf.tools.model.data.dto.ospInventory;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;

import sfpark.adf.tools.model.data.helper.OSPDataFeedFlag;
import sfpark.adf.tools.model.data.helper.OSPFacilityType;
import sfpark.adf.tools.utilities.generic.CurrencyUtil;
import sfpark.adf.tools.utilities.generic.ObjectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class OSPInventoryDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "OSP_INVENTORY";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public OSPInventoryDTO() {
        super();
    }

    private OSPInventoryDTO(ResultSet resultSet) throws SQLException {

        super(resultSet);

        this.setOSPID(resultSet.getString(OSP_ID));
        this.setFacilityName(resultSet.getString(FACILITY_NAME));
        this.setStreetAddress(resultSet.getString(STREET_ADDRESS));
        this.setLocation(resultSet.getString(LOCATION));
        this.setPhone(resultSet.getString(PHONE));
        this.setPMDistrictID(resultSet.getInt(PM_DISTRICT_ID));
        this.setAreaType(resultSet.getString(AREA_TYPE));
        this.setBlockfaceID(resultSet.getString(BLOCKFACE_ID));
        this.setCNNID(resultSet.getString(STREET_SEG_CTRLN_ID));
        this.setFacilityType(OSPFacilityType.extract(resultSet.getString(FACILITY_TYPE)));
        this.setOwner(resultSet.getString(OWNER));

        this.setInternalSensorFlag(resultSet.getString(SENSOR_FLAG));
        this.setInternalMeterFlag(resultSet.getString(METER_FLAG));

        this.setDataFeedFlag(OSPDataFeedFlag.extract(resultSet.getString(DATA_FEED_FLAG)));

        this.setInternalServices(resultSet.getString(SERVICES));

        this.setWebSite(resultSet.getString(WEB_SITE));

        this.setNumberOfVehicleEntryLanes(resultSet.getInt(VEH_ENTRY_LANES));
        this.setNumberOfMotorcycleEntryLanes(resultSet.getInt(MC_ENTRY_LANES));
        this.setNumberOfVehicleExitLanes(resultSet.getInt(VEH_EXIT_LANES));
        this.setNumberOfMotorcycleExitLanes(resultSet.getInt(MC_EXIT_LANES));
        this.setSystemName(resultSet.getString(SYSTEM));

        this.setInternalHighVolumeDiscountFlag(resultSet.getString(HI_VOL_DISC_FLAG));

        this.setValidationProgram(resultSet.getString(VALIDATION_PGM));

        this.setInternalSpecialEventRates(resultSet.getString(SP_EVT_RATES));

        this.setActivationFee(resultSet.getFloat(ACTIVATION_FEE));
        this.setCardReplacementFee(resultSet.getFloat(CARD_REPLACE_FEE));
        this.setLateFee(resultSet.getFloat(LATE_FEE));
        this.setReopeningGarageFee(resultSet.getFloat(REOPEN_FEE));
        this.setValetServiceWithoutKeyFee(resultSet.getFloat(NO_KEY_VALET_FEE));
        this.setCapacity(resultSet.getInt(CAPACITY));
        this.setMainEntranceLongitude(resultSet.getString(MAIN_ENTRANCE_LONG));
        this.setMainEntranceLatitude(resultSet.getString(MAIN_ENTRANCE_LAT));
    }

    public static final String OSP_ID = "OSP_ID";
    public static final String FACILITY_NAME = "FACILITY_NAME";
    public static final String STREET_ADDRESS = "STREET_ADDRESS";
    public static final String LOCATION = "LOCATION";
    public static final String PHONE = "PHONE";
    public static final String PM_DISTRICT_ID = "PM_DISTRICT_ID";
    public static final String AREA_TYPE = "AREA_TYPE";
    public static final String BLOCKFACE_ID = "BLOCKFACE_ID";
    public static final String STREET_SEG_CTRLN_ID = "STREET_SEG_CTRLN_ID";
    public static final String FACILITY_TYPE = "FACILITY_TYPE";
    public static final String OWNER = "OWNER";
    public static final String SENSOR_FLAG = "SENSOR_FLAG";
    public static final String METER_FLAG = "METER_FLAG";
    public static final String DATA_FEED_FLAG = "DATA_FEED_FLAG";
    public static final String SERVICES = "SERVICES";
    public static final String WEB_SITE = "WEB_SITE";
    public static final String VEH_ENTRY_LANES = "VEH_ENTRY_LANES";
    public static final String MC_ENTRY_LANES = "MC_ENTRY_LANES";
    public static final String VEH_EXIT_LANES = "VEH_EXIT_LANES";
    public static final String MC_EXIT_LANES = "MC_EXIT_LANES";
    public static final String SYSTEM = "SYSTEM";
    public static final String HI_VOL_DISC_FLAG = "HI_VOL_DISC_FLAG";
    public static final String VALIDATION_PGM = "VALIDATION_PGM";
    public static final String SP_EVT_RATES = "SP_EVT_RATES";
    public static final String ACTIVATION_FEE = "ACTIVATION_FEE";
    public static final String CARD_REPLACE_FEE = "CARD_REPLACE_FEE";
    public static final String LATE_FEE = "LATE_FEE";
    public static final String REOPEN_FEE = "REOPEN_FEE";
    public static final String NO_KEY_VALET_FEE = "NO_KEY_VALET_FEE";
    public static final String CAPACITY = "CAPACITY";
    public static final String MAIN_ENTRANCE_LONG = "MAIN_ENTRANCE_LONG";
    public static final String MAIN_ENTRANCE_LAT = "MAIN_ENTRANCE_LAT";
    public static final String MAIN_ENTRANCE_GEOM = "MAIN_ENTRANCE_GEOM";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(OSP_ID, FACILITY_NAME, STREET_ADDRESS, LOCATION, PHONE,
                      PM_DISTRICT_ID, AREA_TYPE, BLOCKFACE_ID,
                      STREET_SEG_CTRLN_ID, FACILITY_TYPE, OWNER, SENSOR_FLAG,
                      METER_FLAG, DATA_FEED_FLAG, SERVICES, WEB_SITE,
                      VEH_ENTRY_LANES, MC_ENTRY_LANES, VEH_EXIT_LANES,
                      MC_EXIT_LANES, SYSTEM, HI_VOL_DISC_FLAG, VALIDATION_PGM,
                      SP_EVT_RATES, ACTIVATION_FEE, CARD_REPLACE_FEE, LATE_FEE,
                      REOPEN_FEE, NO_KEY_VALET_FEE, CAPACITY,
                      MAIN_ENTRANCE_LONG, MAIN_ENTRANCE_LAT, CREATED_DT,
                      LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(OSP_ID, FACILITY_NAME, STREET_ADDRESS, LOCATION, PHONE,
                      PM_DISTRICT_ID, AREA_TYPE, BLOCKFACE_ID,
                      STREET_SEG_CTRLN_ID, FACILITY_TYPE, OWNER, SENSOR_FLAG,
                      METER_FLAG, DATA_FEED_FLAG, SERVICES, WEB_SITE,
                      VEH_ENTRY_LANES, MC_ENTRY_LANES, VEH_EXIT_LANES,
                      MC_EXIT_LANES, SYSTEM, HI_VOL_DISC_FLAG, VALIDATION_PGM,
                      SP_EVT_RATES, ACTIVATION_FEE, CARD_REPLACE_FEE, LATE_FEE,
                      REOPEN_FEE, NO_KEY_VALET_FEE, CAPACITY,
                      MAIN_ENTRANCE_LONG, MAIN_ENTRANCE_LAT,
                      MAIN_ENTRANCE_GEOM, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(FACILITY_NAME, STREET_ADDRESS, LOCATION, PHONE,
                      PM_DISTRICT_ID, AREA_TYPE, BLOCKFACE_ID,
                      STREET_SEG_CTRLN_ID, FACILITY_TYPE, OWNER, SENSOR_FLAG,
                      METER_FLAG, DATA_FEED_FLAG, SERVICES, WEB_SITE,
                      VEH_ENTRY_LANES, MC_ENTRY_LANES, VEH_EXIT_LANES,
                      MC_EXIT_LANES, SYSTEM, HI_VOL_DISC_FLAG, VALIDATION_PGM,
                      SP_EVT_RATES, ACTIVATION_FEE, CARD_REPLACE_FEE, LATE_FEE,
                      REOPEN_FEE, NO_KEY_VALET_FEE, CAPACITY,
                      MAIN_ENTRANCE_LONG, MAIN_ENTRANCE_LAT,
                      MAIN_ENTRANCE_GEOM, LAST_UPD_USER, LAST_UPD_PGM);

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

    public static OSPInventoryDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new OSPInventoryDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void setMainEntranceLatLong(String latitude, String longitude) {
        this.setMainEntranceLatitude(latitude);
        this.setMainEntranceLongitude(longitude);
    }

    public boolean isSameAs(OSPInventoryDTO originalDTO) {

        if (StringUtil.areEqual(this.getOSPID(), originalDTO.getOSPID()) &&
            StringUtil.areEqual(this.getFacilityName(),
                                originalDTO.getFacilityName()) &&
            StringUtil.areEqual(this.getStreetAddress(),
                                originalDTO.getStreetAddress()) &&
            StringUtil.areEqual(this.getLocation(),
                                originalDTO.getLocation()) &&
            StringUtil.areEqual(this.getPhone(), originalDTO.getPhone()) &&
            (ObjectUtil.getNullSafe(this.getPMDistrictID()) ==
             ObjectUtil.getNullSafe(originalDTO.getPMDistrictID())) &&
            StringUtil.areEqual(this.getAreaType(),
                                originalDTO.getAreaType()) &&
            StringUtil.areEqual(this.getBlockfaceID(),
                                originalDTO.getBlockfaceID()) &&
            StringUtil.areEqual(this.getCNNID(), originalDTO.getCNNID()) &&
            (ObjectUtil.getNullSafe(this.getFacilityType()).equals(ObjectUtil.getNullSafe(originalDTO.getFacilityType()))) &&
            StringUtil.areEqual(this.getOwner(), originalDTO.getOwner()) &&
            (ObjectUtil.getNullSafe(this.isSensored()) ==
             ObjectUtil.getNullSafe(originalDTO.isSensored())) &&
            (ObjectUtil.getNullSafe(this.isMetered()) ==
             ObjectUtil.getNullSafe(originalDTO.isMetered())) &&
            (ObjectUtil.getNullSafe(this.getDataFeedFlag()).equals(ObjectUtil.getNullSafe(originalDTO.getDataFeedFlag()))) &&
            ObjectUtil.getNullSafe(this.getServices()).equals(ObjectUtil.getNullSafe(originalDTO.getServices())) &&
            StringUtil.areEqual(this.getWebSite(), originalDTO.getWebSite()) &&
            (ObjectUtil.getNullSafe(this.getNumberOfVehicleEntryLanes()) ==
             ObjectUtil.getNullSafe(originalDTO.getNumberOfVehicleEntryLanes())) &&
            (ObjectUtil.getNullSafe(this.getNumberOfMotorcycleEntryLanes()) ==
             ObjectUtil.getNullSafe(originalDTO.getNumberOfMotorcycleEntryLanes())) &&
            (ObjectUtil.getNullSafe(this.getNumberOfVehicleExitLanes()) ==
             ObjectUtil.getNullSafe(originalDTO.getNumberOfVehicleExitLanes())) &&
            (ObjectUtil.getNullSafe(this.getNumberOfMotorcycleExitLanes()) ==
             ObjectUtil.getNullSafe(originalDTO.getNumberOfMotorcycleExitLanes())) &&
            StringUtil.areEqual(this.getSystemName(),
                                originalDTO.getSystemName()) &&
            (ObjectUtil.getNullSafe(this.isHighVolumeDiscountOffered()) ==
             ObjectUtil.getNullSafe(originalDTO.isHighVolumeDiscountOffered())) &&
            StringUtil.areEqual(this.getValidationProgram(),
                                originalDTO.getValidationProgram()) &&
            (ObjectUtil.getNullSafe(this.isParticipatesSpecialEventPricing()) ==
             ObjectUtil.getNullSafe(originalDTO.isParticipatesSpecialEventPricing())) &&
            CurrencyUtil.areEqual(ObjectUtil.getNullSafe(this.getActivationFee()),
                                  ObjectUtil.getNullSafe(originalDTO.getActivationFee())) &&
            CurrencyUtil.areEqual(ObjectUtil.getNullSafe(this.getCardReplacementFee()),
                                  ObjectUtil.getNullSafe(originalDTO.getCardReplacementFee())) &&
            CurrencyUtil.areEqual(ObjectUtil.getNullSafe(this.getLateFee()),
                                  ObjectUtil.getNullSafe(originalDTO.getLateFee())) &&
            CurrencyUtil.areEqual(ObjectUtil.getNullSafe(this.getReopeningGarageFee()),
                                  ObjectUtil.getNullSafe(originalDTO.getReopeningGarageFee())) &&
            CurrencyUtil.areEqual(ObjectUtil.getNullSafe(this.getValetServiceWithoutKeyFee()),
                                  ObjectUtil.getNullSafe(originalDTO.getValetServiceWithoutKeyFee())) &&
            (ObjectUtil.getNullSafe(this.getCapacity()) ==
             ObjectUtil.getNullSafe(originalDTO.getCapacity())) &&
            StringUtil.areEqual(this.getMainEntranceLatitude(),
                                originalDTO.getMainEntranceLatitude()) &&
            StringUtil.areEqual(this.getMainEntranceLongitude(),
                                originalDTO.getMainEntranceLongitude())) {

            return true;
        }

        return false;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String OSPID;
    private String FacilityName;
    private String StreetAddress;
    private String Location;
    private String Phone;
    private int PMDistrictID;
    private String AreaType;
    private String BlockfaceID;
    private String CNNID;
    private OSPFacilityType FacilityType;
    private String Owner;
    private boolean Sensored;
    private boolean Metered;
    private OSPDataFeedFlag DataFeedFlag;
    private List<String> Services;
    private String WebSite;
    private int NumberOfVehicleEntryLanes;
    private int NumberOfMotorcycleEntryLanes;
    private int NumberOfVehicleExitLanes;
    private int NumberOfMotorcycleExitLanes;
    private String SystemName;
    private boolean HighVolumeDiscountOffered;
    private String ValidationProgram;
    private boolean ParticipatesSpecialEventPricing;
    private float ActivationFee;
    private float CardReplacementFee;
    private float LateFee;
    private float ReopeningGarageFee;
    private float ValetServiceWithoutKeyFee;
    private int Capacity;
    private String MainEntranceLongitude;
    private String MainEntranceLatitude;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void setInternalServices(String Services) {
        this.Services =
                StringUtil.convertStringToList(Services, StringUtil.SEPARATOR.COMMA_WITH_TRAILING_SPACE);
    }

    public String getInternalServices() {
        return StringUtil.convertListToString(Services,
                                              StringUtil.SEPARATOR.COMMA_WITH_TRAILING_SPACE);
    }

    public void setInternalSensorFlag(String SensorFlag) {
        this.Sensored = SensorFlag.contains("Y");
    }

    public String getInternalSensorFlag() {
        return (Sensored) ? "Y" : "N";
    }

    public void setInternalMeterFlag(String MeterFlag) {
        this.Metered = MeterFlag.contains("Y");
    }

    public String getInternalMeterFlag() {
        return (Metered) ? "Y" : "N";
    }

    public void setInternalHighVolumeDiscountFlag(String HighVolumeDiscountFlag) {
        this.HighVolumeDiscountOffered = HighVolumeDiscountFlag.contains("Y");
    }

    public String getInternalHighVolumeDiscountFlag() {
        return (HighVolumeDiscountOffered) ? "Y" : "-";
    }

    public void setInternalSpecialEventRates(String SpecialEventRates) {
        this.ParticipatesSpecialEventPricing = SpecialEventRates.contains("Y");
    }

    public String getInternalSpecialEventRates() {
        return (ParticipatesSpecialEventPricing) ? "Y" : "-";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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

    public void setStreetAddress(String StreetAddress) {
        this.StreetAddress = StreetAddress;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getLocation() {
        return Location;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPMDistrictID(int PMDistrictID) {
        this.PMDistrictID = PMDistrictID;
    }

    public int getPMDistrictID() {
        return PMDistrictID;
    }

    public void setAreaType(String AreaType) {
        this.AreaType = AreaType;
    }

    public String getAreaType() {
        return AreaType;
    }

    public void setBlockfaceID(String BlockfaceID) {
        this.BlockfaceID = BlockfaceID;
    }

    public String getBlockfaceID() {
        return BlockfaceID;
    }

    public void setCNNID(String CNNID) {
        this.CNNID = CNNID;
    }

    public String getCNNID() {
        return CNNID;
    }

    public void setOwner(String Owner) {
        this.Owner = Owner;
    }

    public String getOwner() {
        return Owner;
    }

    public void setSensored(boolean Sensored) {
        this.Sensored = Sensored;
    }

    public boolean isSensored() {
        return Sensored;
    }

    public void setMetered(boolean Metered) {
        this.Metered = Metered;
    }

    public boolean isMetered() {
        return Metered;
    }

    public void setWebSite(String WebSite) {
        this.WebSite = WebSite;
    }

    public String getWebSite() {
        return WebSite;
    }

    public void setNumberOfVehicleEntryLanes(int NumberOfVehicleEntryLanes) {
        this.NumberOfVehicleEntryLanes = NumberOfVehicleEntryLanes;
    }

    public int getNumberOfVehicleEntryLanes() {
        return NumberOfVehicleEntryLanes;
    }

    public void setNumberOfMotorcycleEntryLanes(int NumberOfMotorcycleEntryLanes) {
        this.NumberOfMotorcycleEntryLanes = NumberOfMotorcycleEntryLanes;
    }

    public int getNumberOfMotorcycleEntryLanes() {
        return NumberOfMotorcycleEntryLanes;
    }

    public void setNumberOfVehicleExitLanes(int NumberOfVehicleExitLanes) {
        this.NumberOfVehicleExitLanes = NumberOfVehicleExitLanes;
    }

    public int getNumberOfVehicleExitLanes() {
        return NumberOfVehicleExitLanes;
    }

    public void setNumberOfMotorcycleExitLanes(int NumberOfMotorcycleExitLanes) {
        this.NumberOfMotorcycleExitLanes = NumberOfMotorcycleExitLanes;
    }

    public int getNumberOfMotorcycleExitLanes() {
        return NumberOfMotorcycleExitLanes;
    }

    public void setSystemName(String SystemName) {
        this.SystemName = SystemName;
    }

    public String getSystemName() {
        return SystemName;
    }

    public void setValidationProgram(String ValidationProgram) {
        this.ValidationProgram = ValidationProgram;
    }

    public String getValidationProgram() {
        return ValidationProgram;
    }

    public void setActivationFee(float ActivationFee) {
        this.ActivationFee = ActivationFee;
    }

    public float getActivationFee() {
        return ActivationFee;
    }

    public void setCardReplacementFee(float CardReplacementFee) {
        this.CardReplacementFee = CardReplacementFee;
    }

    public float getCardReplacementFee() {
        return CardReplacementFee;
    }

    public void setLateFee(float LateFee) {
        this.LateFee = LateFee;
    }

    public float getLateFee() {
        return LateFee;
    }

    public void setReopeningGarageFee(float ReopeningGarageFee) {
        this.ReopeningGarageFee = ReopeningGarageFee;
    }

    public float getReopeningGarageFee() {
        return ReopeningGarageFee;
    }

    public void setValetServiceWithoutKeyFee(float ValetServiceWithoutKeyFee) {
        this.ValetServiceWithoutKeyFee = ValetServiceWithoutKeyFee;
    }

    public float getValetServiceWithoutKeyFee() {
        return ValetServiceWithoutKeyFee;
    }

    public void setCapacity(int Capacity) {
        this.Capacity = Capacity;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setMainEntranceLongitude(String MainEntranceLongitude) {
        this.MainEntranceLongitude = MainEntranceLongitude;
    }

    public String getMainEntranceLongitude() {
        return MainEntranceLongitude;
    }

    public void setMainEntranceLatitude(String MainEntranceLatitude) {
        this.MainEntranceLatitude = MainEntranceLatitude;
    }

    public String getMainEntranceLatitude() {
        return MainEntranceLatitude;
    }

    public void setHighVolumeDiscountOffered(boolean HighVolumeDiscountOffered) {
        this.HighVolumeDiscountOffered = HighVolumeDiscountOffered;
    }

    public boolean isHighVolumeDiscountOffered() {
        return HighVolumeDiscountOffered;
    }

    public void setParticipatesSpecialEventPricing(boolean ParticipatesSpecialEventPricing) {
        this.ParticipatesSpecialEventPricing = ParticipatesSpecialEventPricing;
    }

    public boolean isParticipatesSpecialEventPricing() {
        return ParticipatesSpecialEventPricing;
    }

    public void setServices(List<String> Services) {
        this.Services = Services;
    }

    public List<String> getServices() {
        return Services;
    }

    public void setFacilityType(OSPFacilityType FacilityType) {
        this.FacilityType = FacilityType;
    }

    public OSPFacilityType getFacilityType() {
        return FacilityType;
    }

    public void setDataFeedFlag(OSPDataFeedFlag DataFeedFlag) {
        this.DataFeedFlag = DataFeedFlag;
    }

    public OSPDataFeedFlag getDataFeedFlag() {
        return DataFeedFlag;
    }
}
