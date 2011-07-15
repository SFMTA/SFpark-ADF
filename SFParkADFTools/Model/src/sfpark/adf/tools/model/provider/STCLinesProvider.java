package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.stcLines.STCLinesDO;
import sfpark.adf.tools.model.util.ConnectUtil;

public class STCLinesProvider {

    private static final String CLASSNAME = STCLinesProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "STCLINES";

    private STCLinesProvider() {
        super();
    }

    public static final STCLinesProvider INSTANCE = new STCLinesProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<STCLinesDO> getSTCLinesDOs(String streetName, String longitude,
                                           String latitude, int distance) {
        LOGGER.entering(CLASSNAME, "getSTCLinesDOs");

        List<STCLinesDO> stcLinesDOs = new ArrayList<STCLinesDO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForSTCLinesDOs(distance));

            preparedStatement.setString(1, streetName);
            preparedStatement.setString(2, longitude);
            preparedStatement.setString(3, latitude);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                STCLinesDO DO = STCLinesDO.extract(resultSet);

                stcLinesDOs.add(DO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getSTCLinesDOs");

        return stcLinesDOs;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatementForSTCLinesDOs(int distance) {
        LOGGER.entering(CLASSNAME, "getSelectStatementForSTCLinesDOs");

        String Attributes =
            STCLinesDO.CNN + ", " + STCLinesDO.STREETNAME + ", " +
            "round(sdo_nn_distance (1),2) " + STCLinesDO.DISTANCE + ", " +
            "case " +
            "when LF_TOADD <> 0 and mod(LF_TOADD, 10) in (1,3,5,7,9) then LF_FADD||\'-\'||LF_TOADD " +
            "when RT_TOADD <> 0 and mod(RT_TOADD, 10) in (1,3,5,7,9) then RT_FADD||\'-\'||RT_TOADD " +
            "else \' \' end " + STCLinesDO.ODD + ", " + "case " +
            "when LF_TOADD <> 0 and mod(LF_TOADD, 10) in (0,2,4,6,8) then LF_FADD||\'-\'||LF_TOADD " +
            "when RT_TOADD <> 0 and mod(RT_TOADD, 10) in (0,2,4,6,8) then RT_FADD||\'-\'||RT_TOADD " +
            "else \' \' end " + STCLinesDO.EVEN;

        String string1 = STCLinesDO.STREETNAME + "=?";
        String string2 =
            "sdo_nn(GEOM, MDSYS.SDO_GEOMETRY(2001, 8307, MDSYS.SDO_POINT_TYPE(?, ?, null), null, null), \'distance=" +
            Integer.toString(distance) + " unit=foot\', 1) = \'TRUE\'";

        String Where = StatementGenerator.andOperator(string1, string2);

        String OrderBy = STCLinesDO.DISTANCE;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForSTCLinesDOs");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where, OrderBy);
    }
}
