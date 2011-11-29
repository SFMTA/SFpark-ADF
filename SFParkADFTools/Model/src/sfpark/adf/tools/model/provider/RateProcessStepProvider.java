package sfpark.adf.tools.model.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- --------------------------------------------------------
 * 20111129-01 Mark Piller - Oracle Consulting  Created this class
 */
public final class RateProcessStepProvider {

    private static final String CLASSNAME = RateProcessStepProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    public RateProcessStepProvider() {
        super();
    }

    public static final RateProcessStepProvider INSTANCE = new RateProcessStepProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public String checkForProcessID(String processID) {
      LOGGER.entering(CLASSNAME, "checkForProcessID");

      Connection connection = null;
      Statement stmt = null;
      ResultSet rs = null;
      String processStep = null;

      try {
          connection = OracleDBConnection.getConnection();
          stmt = connection.createStatement();
          rs = stmt.executeQuery("SELECT PROCESS_STEP FROM RATE_CHG_PROCESS_CONTROL WHERE PROCESS_ID = '" + processID + "'");

          while (rs.next()) {
              processStep = rs.getString("PROCESS_STEP");
          }

      } catch (SQLException e) {
          LOGGER.warning(ErrorMessage.SELECT_DTO.getMessage(), e);
      } finally {
          OracleDBConnection.closeAll(rs, stmt, connection);
      }

      return (processStep);        
    }
}
