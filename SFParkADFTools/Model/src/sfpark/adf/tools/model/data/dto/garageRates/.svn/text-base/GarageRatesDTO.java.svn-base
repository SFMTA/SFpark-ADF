package sfpark.adf.tools.model.data.dto.garageRates;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.EffectiveDateBaseDTO;

import sfpark.adf.tools.model.data.helper.GarageRatesDisplayCode;
import sfpark.adf.tools.model.data.helper.GarageRatesDisplayGroup;
import sfpark.adf.tools.model.data.helper.GarageRatesQualifier;
import sfpark.adf.tools.model.data.helper.GarageRatesTimeBand;
import sfpark.adf.tools.utilities.generic.CurrencyUtil;
import sfpark.adf.tools.utilities.generic.ObjectUtil;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;


public class GarageRatesDTO extends EffectiveDateBaseDTO {

    public static String getDatabaseTableName() {
        return "GARAGE_RATES";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public GarageRatesDTO() {
        super();
    }

    private GarageRatesDTO(ResultSet resultSet) throws SQLException {

        super(resultSet);

        this.setGarageRateID(resultSet.getString(GARAGE_RATE_ID));
        this.setOSPID(resultSet.getString(OSP_ID));
        this.setOverride(false);
        this.setRateStatus(resultSet.getString(RATE_STATUS));
        this.setDisplayGroup(GarageRatesDisplayGroup.extractGroup(resultSet.getInt(DISPLAY_GROUP)));
        this.setDisplaySequence(resultSet.getInt(DISPLAY_SEQ));
        this.setEffectiveFromDate(resultSet.getDate(EFF_FROM_DT));
        this.setEffectiveToDate(resultSet.getDate(EFF_TO_DT));
        this.setRateDescription(resultSet.getString(RATE_DESC));
        this.setTimeBand(GarageRatesTimeBand.extractTimeBand(TimeDisplayUtil.extractFromTimeForDisplay(resultSet.getString(FROM_TIME)),
                                                             TimeDisplayUtil.extractToTimeForDisplay(resultSet.getString(TO_TIME))));
        this.setRate(resultSet.getFloat(RATE));
        this.setRateQualifier(GarageRatesQualifier.extractQualifier(resultSet.getString(RATE_QUALIFIER)));
        this.setMaximumAmount(resultSet.getString(MAX_AMT));
        this.setRateRestrictions(resultSet.getString(RATE_RESTRICTIONS));
        this.setDisplayCode(GarageRatesDisplayCode.extract(resultSet.getString(DISPLAY_CODE)));
        this.setRateChangeReference(resultSet.getString(RATE_CHG_REF));

        switch (this.getDisplayGroup()) {

        case HOURLY_RATES:
            {
                // Hourly Rates
                //    --- RATE_QUALIFIER is always Per hour
                //    --- MAX_AMT is always NULL
                //    --- RATE_RESTRICTIONS is always NULL
                this.setEditableRateQualifier(false);
                this.setEditableMaximumAmount(false);
                this.setEditableRateRestrictions(false);

                this.setEditableTimeBand(true);
            }
            break;

        case MONTHLY_RATES:
            {
                // Monthly Rates
                //    --- RATE_QUALIFIER is always Per month
                //    --- MAX_AMT is always NULL
                //    --- FROM_TIME is always NULL
                //    --- TO_TIME is always NULL
                this.setEditableRateQualifier(false);
                this.setEditableMaximumAmount(false);
                this.setEditableTimeBand(false);

                this.setEditableRateRestrictions(true);
            }
            break;

        case FLAT_RATES_OR_DISCOUNTS:
            {
                // Flat Rates / Discounts
                //    --- FROM_TIME is always NULL
                //    --- TO_TIME is always NULL
                this.setEditableTimeBand(false);

                this.setEditableRateQualifier(true);
                this.setEditableMaximumAmount(true);
                this.setEditableRateRestrictions(true);
            }
            break;
        }
    }

    public static final String GARAGE_RATE_ID = "GARAGE_RATE_ID";
    public static final String OSP_ID = "OSP_ID";
    public static final String RATE_STATUS = "RATE_STATUS";
    public static final String EFF_FROM_DT = "EFF_FROM_DT";
    public static final String EFF_TO_DT = "EFF_TO_DT";
    public static final String DISPLAY_GROUP = "DISPLAY_GROUP";
    public static final String DISPLAY_SEQ = "DISPLAY_SEQ";
    public static final String RATE_DESC = "RATE_DESC";
    public static final String FROM_TIME = "FROM_TIME";
    public static final String TO_TIME = "TO_TIME";
    public static final String RATE = "RATE";
    public static final String RATE_QUALIFIER = "RATE_QUALIFIER";
    public static final String MAX_AMT = "MAX_AMT";
    public static final String RATE_RESTRICTIONS = "RATE_RESTRICTIONS";
    public static final String DISPLAY_CODE = "DISPLAY_CODE";
    public static final String RATE_CHG_REF = "RATE_CHG_REF";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(GARAGE_RATE_ID, OSP_ID, RATE_STATUS, EFF_FROM_DT,
                      EFF_TO_DT, DISPLAY_GROUP, DISPLAY_SEQ, RATE_DESC,
                      FROM_TIME, TO_TIME, RATE, RATE_QUALIFIER, MAX_AMT,
                      RATE_RESTRICTIONS, DISPLAY_CODE, RATE_CHG_REF,
                      CREATED_DT, LAST_UPD_DT, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(OSP_ID, RATE_STATUS, EFF_FROM_DT, EFF_TO_DT,
                      DISPLAY_GROUP, DISPLAY_SEQ, RATE_DESC, FROM_TIME,
                      TO_TIME, RATE, RATE_QUALIFIER, MAX_AMT,
                      RATE_RESTRICTIONS, DISPLAY_CODE, RATE_CHG_REF,
                      LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(OSP_ID, RATE_STATUS, EFF_FROM_DT, EFF_TO_DT,
                      DISPLAY_GROUP, DISPLAY_SEQ, RATE_DESC, FROM_TIME,
                      TO_TIME, RATE, RATE_QUALIFIER, MAX_AMT,
                      RATE_RESTRICTIONS, DISPLAY_CODE, RATE_CHG_REF,
                      LAST_UPD_USER, LAST_UPD_PGM);

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

    public static GarageRatesDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new GarageRatesDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(GarageRatesDTO dto) {

        if (StringUtil.areEqual(this.getRateStatus(), dto.getRateStatus()) &&
            SQLDateUtil.areEqual(this.getEffectiveFromDate(),
                                 dto.getEffectiveFromDate()) &&
            SQLDateUtil.areEqual(this.getEffectiveToDate(),
                                 dto.getEffectiveToDate()) &&
            (this.getDisplayGroup().equals(dto.getDisplayGroup())) &&
            (this.getDisplaySequence() == dto.getDisplaySequence()) &&
            StringUtil.areEqual(this.getRateDescription(),
                                dto.getRateDescription()) &&
            (this.getTimeBand().equals(dto.getTimeBand())) &&
            CurrencyUtil.areEqual(ObjectUtil.getNullSafe(this.getRate()),
                                  ObjectUtil.getNullSafe(dto.getRate())) &&
            (this.getRateQualifier().equals(dto.getRateQualifier())) &&
            StringUtil.areEqual(this.getMaximumAmount(),
                                dto.getMaximumAmount()) &&
            StringUtil.areEqual(this.getRateRestrictions(),
                                dto.getRateRestrictions()) &&
            ObjectUtil.getNullSafe(this.getDisplayCode()).equals(ObjectUtil.getNullSafe(dto.getDisplayCode())) &&
            StringUtil.areEqual(this.getRateChangeReference(),
                                dto.getRateChangeReference())) {

            return true;
        }

        return false;
    }

    public WhatChanged getWhatChanged(GarageRatesDTO originalDTO) {

        if (this.isSameAs(originalDTO)) {
            return WhatChanged.NOTHING;
        }

        return super.getWhatChanged(originalDTO);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    private boolean Override;
    private boolean EditableRateQualifier;
    private boolean EditableMaximumAmount;
    private boolean EditableRateRestrictions;
    private boolean EditableTimeBand;

    public void setOverride(boolean Override) {
        this.Override = Override;
    }

    public boolean isOverride() {
        return Override;
    }

    public boolean isNewRecord() {
        return (GarageRateID == null);
    }

    public void setEditableRateQualifier(boolean EditableRateQualifier) {
        this.EditableRateQualifier = EditableRateQualifier;
    }

    public boolean isEditableRateQualifier() {
        return EditableRateQualifier;
    }

    public void setEditableMaximumAmount(boolean EditableMaximumAmount) {
        this.EditableMaximumAmount = EditableMaximumAmount;
    }

    public boolean isEditableMaximumAmount() {
        return EditableMaximumAmount;
    }

    public void setEditableRateRestrictions(boolean EditableRateRestrictions) {
        this.EditableRateRestrictions = EditableRateRestrictions;
    }

    public boolean isEditableRateRestrictions() {
        return EditableRateRestrictions;
    }

    public void setEditableTimeBand(boolean EditableTimeBand) {
        this.EditableTimeBand = EditableTimeBand;
    }

    public boolean isEditableTimeBand() {
        return EditableTimeBand;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String GarageRateID;
    private String OSPID;
    private String RateStatus;
    private GarageRatesDisplayGroup DisplayGroup;
    private int DisplaySequence;
    private String RateDescription;
    private GarageRatesTimeBand TimeBand;
    private float Rate;
    private GarageRatesQualifier RateQualifier;
    private String MaximumAmount;
    private String RateRestrictions;
    private GarageRatesDisplayCode DisplayCode;
    private String RateChangeReference;

    public void setGarageRateID(String GarageRateID) {
        this.GarageRateID = GarageRateID;
    }

    public String getGarageRateID() {
        return GarageRateID;
    }

    public void setOSPID(String OSPID) {
        this.OSPID = OSPID;
    }

    public String getOSPID() {
        return OSPID;
    }

    public void setRateStatus(String RateStatus) {
        this.RateStatus = RateStatus;
    }

    public String getRateStatus() {
        return RateStatus;
    }

    public void setDisplayGroup(GarageRatesDisplayGroup DisplayGroup) {
        this.DisplayGroup = DisplayGroup;
    }

    public GarageRatesDisplayGroup getDisplayGroup() {
        return DisplayGroup;
    }

    public void setDisplaySequence(int DisplaySequence) {
        this.DisplaySequence = DisplaySequence;
    }

    public int getDisplaySequence() {
        return DisplaySequence;
    }

    public void setRateDescription(String RateDescription) {
        this.RateDescription = RateDescription;
    }

    public String getRateDescription() {
        return RateDescription;
    }

    public void setTimeBand(GarageRatesTimeBand TimeBand) {
        this.TimeBand = TimeBand;
    }

    public GarageRatesTimeBand getTimeBand() {
        return TimeBand;
    }

    public void setRate(float Rate) {
        this.Rate = Rate;
    }

    public float getRate() {
        return Rate;
    }

    public void setRateQualifier(GarageRatesQualifier RateQualifier) {
        this.RateQualifier = RateQualifier;
    }

    public GarageRatesQualifier getRateQualifier() {
        return RateQualifier;
    }

    public void setMaximumAmount(String MaximumAmount) {
        this.MaximumAmount = MaximumAmount;
    }

    public String getMaximumAmount() {
        return MaximumAmount;
    }

    public void setRateRestrictions(String RateRestrictions) {
        this.RateRestrictions = RateRestrictions;
    }

    public String getRateRestrictions() {
        return RateRestrictions;
    }

    public void setRateChangeReference(String RateChangeReference) {
        this.RateChangeReference = RateChangeReference;
    }

    public String getRateChangeReference() {
        return RateChangeReference;
    }

    public void setDisplayCode(GarageRatesDisplayCode DisplayCode) {
        this.DisplayCode = DisplayCode;
    }

    public GarageRatesDisplayCode getDisplayCode() {
        return DisplayCode;
    }
}
