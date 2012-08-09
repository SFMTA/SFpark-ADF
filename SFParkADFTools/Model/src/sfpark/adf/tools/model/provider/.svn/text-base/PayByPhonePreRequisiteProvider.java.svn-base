package sfpark.adf.tools.model.provider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.adf.share.logging.ADFLogger;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.OracleDBConnection;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- --------------------------------------------------------
 * 20120604-01 Mark Piller - Oracle Consulting  Created this class for Pay By Phone logic
 */
public final class PayByPhonePreRequisiteProvider {

    private static ADFLogger adfLogger =
        ADFLogger.createADFLogger(PayByPhonePreRequisiteProvider.class);

    public PayByPhonePreRequisiteProvider() {
        super();
    }

    public static final PayByPhonePreRequisiteProvider INSTANCE =
        new PayByPhonePreRequisiteProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public int checkForPayByPhoneImplementations(String rateChangeRefID) {
        adfLogger.log(adfLogger.TRACE,
                      "DEBUG >> entering checkForPayByPhoneImplementations() - checking for rateChangeRefID " +
                      rateChangeRefID);

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        int implementationCount = 0;

        try {
            connection = OracleDBConnection.getConnection();
            stmt = connection.createStatement();
            rs =
 stmt.executeQuery("SELECT COUNT(*) FROM RATE_CHG_PROCESS_CONTROL WHERE RATE_CHG_REF_ID = '" +
                   rateChangeRefID +
                   "' AND SUBSTR(RATE_CHG_REF,7,1) <> '%' AND (PROCESS_STEP = 10 OR (PROCESS_STEP = 20 AND STEP_EXEC_STATUS < 30))");

            while (rs.next()) {
                adfLogger.log(adfLogger.TRACE, "DEBUG >> getting implementation count ");
                ResultSetMetaData rsmd = rs.getMetaData();
                adfLogger.log(adfLogger.TRACE, "DEBUG >> column count: " + rsmd.getColumnCount());
                try {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        adfLogger.log(adfLogger.TRACE,
                                      "DEBUG >> column " + i + " name: " + rsmd.getColumnName(i));
                    }
                    implementationCount = rs.getInt(1);
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
            }
            adfLogger.log(adfLogger.TRACE,
                          "DEBUG >> implementationCount is " + implementationCount);

        } catch (SQLException e) {
            adfLogger.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
            // LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
        } finally {
            OracleDBConnection.closeAll(rs, stmt, connection);
        } // try

        return (implementationCount);
    } // checkForPayByPhoneImplementations

} // PayByPhonePreRequisiteProvider