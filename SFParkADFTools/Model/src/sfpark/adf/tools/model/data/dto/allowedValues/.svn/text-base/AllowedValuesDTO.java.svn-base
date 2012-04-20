package sfpark.adf.tools.model.data.dto.allowedValues;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.model.data.helper.AllowedValuesDeletableFlag;
import sfpark.adf.tools.utilities.generic.StringUtil;

public class AllowedValuesDTO extends BaseDTO {

    public static String getDatabaseTableName() {
        return "ALLOWED_VALUES";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public AllowedValuesDTO() {
        super();
    }

    private AllowedValuesDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setTableName(resultSet.getString(TABLE_NAME));
        this.setColumnName(resultSet.getString(COLUMN_NAME));
        this.setColumnValue(resultSet.getString(COLUMN_VALUE));
        this.setDescription(resultSet.getString(DESCRIPTION));
        this.setDeletableIfUnused(AllowedValuesDeletableFlag.extract(resultSet.getString(DELETABLE_IF_UNUSED)));

    }

    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_VALUE = "COLUMN_VALUE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String DELETABLE_IF_UNUSED = "DELETABLE_IF_UNUSED";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(TABLE_NAME, COLUMN_NAME, COLUMN_VALUE, DESCRIPTION,
                      DELETABLE_IF_UNUSED, CREATED_DT, LAST_UPD_DT,
                      LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForInsert =
        Arrays.asList(TABLE_NAME, COLUMN_NAME, COLUMN_VALUE, DESCRIPTION,
                      DELETABLE_IF_UNUSED, LAST_UPD_USER, LAST_UPD_PGM);

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(DESCRIPTION, LAST_UPD_USER, LAST_UPD_PGM);

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

    public static AllowedValuesDTO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new AllowedValuesDTO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(AllowedValuesDTO originalDTO) {

        if (StringUtil.areEqual(this.getTableName(),
                                originalDTO.getTableName()) &&
            StringUtil.areEqual(this.getColumnName(),
                                originalDTO.getColumnName()) &&
            StringUtil.areEqual(this.getColumnValue(),
                                originalDTO.getColumnValue()) &&
            StringUtil.areEqual(this.getDescription(),
                                originalDTO.getDescription()) &&
            this.getDeletableIfUnused().equals(originalDTO.getDeletableIfUnused())) {

            return true;
        }

        return false;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    public int getMaximumLengthTableName() {
        return 30;
    }

    public int getMaximumLengthColumnName() {
        return 30;
    }

    public int getMaximumLengthColumnValue() {
        return 30;
    }

    public int getMaximumLengthDescription() {
        return 100;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String TableName;
    private String ColumnName;
    private String ColumnValue;
    private String Description;
    private AllowedValuesDeletableFlag DeletableIfUnused;

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public String getTableName() {
        return TableName;
    }

    public void setColumnName(String ColumnName) {
        this.ColumnName = ColumnName;
    }

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnValue(String ColumnValue) {
        this.ColumnValue = ColumnValue;
    }

    public String getColumnValue() {
        return ColumnValue;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return Description;
    }

    public void setDeletableIfUnused(AllowedValuesDeletableFlag DeletableIfUnused) {
        this.DeletableIfUnused = DeletableIfUnused;
    }

    public AllowedValuesDeletableFlag getDeletableIfUnused() {
        return DeletableIfUnused;
    }
}
