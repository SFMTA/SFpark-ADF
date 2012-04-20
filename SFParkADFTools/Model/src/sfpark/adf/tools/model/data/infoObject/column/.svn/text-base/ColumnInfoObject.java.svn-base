package sfpark.adf.tools.model.data.infoObject.column;

import java.sql.ResultSet;
import java.sql.SQLException;

import sfpark.adf.tools.model.data.infoObject.BaseInfoObject;

public class ColumnInfoObject extends BaseInfoObject {
    public ColumnInfoObject() {
        super();
    }

    private ColumnInfoObject(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setTableName(resultSet.getString(TABLE_NAME));
        this.setColumnName(resultSet.getString(COLUMN_NAME));
        this.setOrdinalPosition(resultSet.getInt(ORDINAL_POSITION));
        this.setDataType(resultSet.getInt(DATA_TYPE));
        this.setTypeName(resultSet.getString(TYPE_NAME));
        this.setColumnSize(resultSet.getInt(COLUMN_SIZE));
        this.setNullable(resultSet.getInt(NULLABLE));

    }

    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String ORDINAL_POSITION = "ORDINAL_POSITION";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String TYPE_NAME = "TYPE_NAME";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";
    private static final String NULLABLE = "NULLABLE";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static ColumnInfoObject extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new ColumnInfoObject(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String TableName;
    private String ColumnName;
    private int OrdinalPosition;
    private int DataType;
    private String TypeName;
    private int ColumnSize;
    private int Nullable;

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

    public void setOrdinalPosition(int OrdinalPosition) {
        this.OrdinalPosition = OrdinalPosition;
    }

    public int getOrdinalPosition() {
        return OrdinalPosition;
    }

    public void setDataType(int DataType) {
        this.DataType = DataType;
    }

    public int getDataType() {
        return DataType;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setColumnSize(int ColumnSize) {
        this.ColumnSize = ColumnSize;
    }

    public int getColumnSize() {
        return ColumnSize;
    }

    public void setNullable(int Nullable) {
        this.Nullable = Nullable;
    }

    public int getNullable() {
        return Nullable;
    }
}
