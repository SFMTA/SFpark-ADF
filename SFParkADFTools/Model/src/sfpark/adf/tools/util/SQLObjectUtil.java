package sfpark.adf.tools.util;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public final class SQLObjectUtil {

    /**
     * To avoid instantiation
     */
    private SQLObjectUtil() {
        super();
    }

    private static final String POINT_STRUCT = "MDSYS.SDO_POINT_TYPE";
    private static final String GEOM_STRUCT = "MDSYS.SDO_GEOMETRY";

    /**
     * Generates the GEOM Object
     *
     * @param connection
     * @param longitude
     * @param latitude
     * @return
     * @throws SQLException
     */
    public static Object getGEOMObject(Connection connection, String longitude,
                                       String latitude) throws SQLException {
        Object pointAttributes[] = { longitude, latitude, null };

        StructDescriptor pointDescriptor =
            StructDescriptor.createDescriptor(POINT_STRUCT, connection);

        STRUCT pointStruct =
            new STRUCT(pointDescriptor, connection, pointAttributes);

        Object geomAttributes[] = { 2001, 8307, pointStruct, null, null };

        StructDescriptor geomDescriptor =
            StructDescriptor.createDescriptor(GEOM_STRUCT, connection);

        STRUCT geomStruct =
            new STRUCT(geomDescriptor, connection, geomAttributes);

        return geomStruct;
    }
}
