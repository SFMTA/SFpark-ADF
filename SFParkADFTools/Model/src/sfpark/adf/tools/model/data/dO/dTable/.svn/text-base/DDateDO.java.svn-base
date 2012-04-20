package sfpark.adf.tools.model.data.dO.dTable;

import java.sql.Date;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class DDateDO extends BaseDO {

    public static String getDatabaseTableName() {
        return "SFPARK.D_DATE";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public DDateDO() {
        super();
    }

    private DDateDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setDateDT(resultSet.getDate(DATE_DT));
        this.setDateType(resultSet.getString(DATE_TYPE));

    }

    public static final String DATE_DT = "DATE_DT";
    public static final String DATE_TYPE = "DATE_TYPE";

    private static final List<String> AttributeListForSelect =
        Arrays.asList(DATE_DT, DATE_TYPE);

    public static List<String> getAttributeListForSelect() {
        return AttributeListForSelect;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static DDateDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new DDateDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private Date DateDT;
    private String DateType;

    public void setDateDT(Date DateDT) {
        this.DateDT = DateDT;
    }

    public Date getDateDT() {
        return DateDT;
    }

    public void setDateType(String DateType) {
        this.DateType = DateType;
    }

    public String getDateType() {
        return DateType;
    }
}
