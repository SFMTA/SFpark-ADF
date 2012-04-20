package sfpark.adf.tools.util;

import java.sql.Types;

import sfpark.adf.tools.model.data.infoObject.column.ColumnInfoObject;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class TableColumnUtil {

    /**
     * To avoid instantiation
     */
    private TableColumnUtil() {
        super();
    }

    public static boolean isCompatible(ColumnInfoObject columnInfoObject,
                                       String columnValue) {

        if (StringUtil.isBlank(columnValue)) {
            // It cannot be null or empty
            return false;
        }

        boolean compatible = false;

        switch (columnInfoObject.getDataType()) {

        case Types.CHAR:
            {
                // Length of the Column Value should be = 1
                compatible = (columnValue.length() == 1);
            }
            break;

        case Types.BIT:
        case Types.TINYINT:
        case Types.BIGINT:
        case Types.NUMERIC:
        case Types.DECIMAL:
        case Types.INTEGER:
        case Types.SMALLINT:
        case Types.FLOAT:
        case Types.REAL:
            {
                // Column Value should only be digits
                compatible = StringUtil.isDigitsONLY(columnValue);
            }
            break;

        case Types.VARCHAR:
            {
                // Length of the Column Value should be <= COLUMN_SIZE
                compatible =
                        (columnValue.length() <= columnInfoObject.getColumnSize());
            }
            break;

        default:
            {
                // NOT supported yet
                compatible = false;
            }
            break;
        }

        return compatible;
    }
}
