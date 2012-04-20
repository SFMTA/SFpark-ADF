package sfpark.adf.tools.model.data.dO.meterModels;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;
import sfpark.adf.tools.model.data.helper.MeterSmartFlag;
import sfpark.adf.tools.model.data.helper.MeterType;

public class MeterModelsDO extends BaseDO {

    public static String getDatabaseTableName() {
        return "METER_MODELS";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public MeterModelsDO() {
        super();
    }

    public MeterModelsDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setMeterVendor(resultSet.getString(METER_VENDOR));
        this.setMeterModel(resultSet.getString(METER_MODEL));
        this.setMeterType(MeterType.extract(resultSet.getString(METER_TYPE)));
        this.setSmartMeterFlag(MeterSmartFlag.extract(resultSet.getString(SMART_METER_FLAG)));

    }

    public static final String METER_VENDOR = "METER_VENDOR";
    public static final String METER_MODEL = "METER_MODEL";
    public static final String METER_TYPE = "METER_TYPE";
    public static final String SMART_METER_FLAG = "SMART_METER_FLAG";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(METER_VENDOR, METER_MODEL, METER_TYPE, SMART_METER_FLAG);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static MeterModelsDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new MeterModelsDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String MeterVendor;
    private String MeterModel;
    private MeterType meterType;
    private MeterSmartFlag SmartMeterFlag;

    public void setMeterVendor(String MeterVendor) {
        this.MeterVendor = MeterVendor;
    }

    public String getMeterVendor() {
        return MeterVendor;
    }

    public void setMeterModel(String MeterModel) {
        this.MeterModel = MeterModel;
    }

    public String getMeterModel() {
        return MeterModel;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setSmartMeterFlag(MeterSmartFlag SmartMeterFlag) {
        this.SmartMeterFlag = SmartMeterFlag;
    }

    public MeterSmartFlag getSmartMeterFlag() {
        return SmartMeterFlag;
    }
}
