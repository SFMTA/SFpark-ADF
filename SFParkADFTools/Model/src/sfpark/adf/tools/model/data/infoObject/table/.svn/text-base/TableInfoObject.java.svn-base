package sfpark.adf.tools.model.data.infoObject.table;

import java.sql.ResultSet;
import java.sql.SQLException;

import sfpark.adf.tools.model.data.infoObject.BaseInfoObject;

public class TableInfoObject extends BaseInfoObject {
    public TableInfoObject() {
        super();
    }

    private TableInfoObject(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setTableSchem(resultSet.getString(TABLE_SCHEM));
        this.setTableName(resultSet.getString(TABLE_NAME));
        this.setTableType(resultSet.getString(TABLE_TYPE));

    }

    private static final String TABLE_SCHEM = "TABLE_SCHEM";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String TABLE_TYPE = "TABLE_TYPE";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static TableInfoObject extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new TableInfoObject(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String TableSchem;
    private String TableName;
    private String TableType;

    public void setTableSchem(String TableSchem) {
        this.TableSchem = TableSchem;
    }

    public String getTableSchem() {
        return TableSchem;
    }

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableType(String TableType) {
        this.TableType = TableType;
    }

    public String getTableType() {
        return TableType;
    }
}
