package sfpark.adf.tools.model.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.adf.share.logging.ADFLogger;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.helper.OperationStatus;

/**
 * Description:
 * This class calls an ODI function that generates Parking Rates.  The call to
 * a function is similar to that of a stored procedure, thus the name of this class.
 * 
 * This class is used by the Calendar NavigationBean and Rate Change DMLOperationsProvider classes
 * 
 * 
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- --------------------------------------------------------
 * 20111109-01 Mark Piller - Oracle Consulting  added messageFromODIFunction
 * 20111109-02 Mark Piller - Oracle Consulting  added logic to receive message from ODI function
 * 20111109-03 Mark Piller - Oracle Consulting  added logic to evaluate message returned from ODI function.
 * 20111115-01 Mark Piller - Oracle Consulting  added test for "0" returned by ODI function
 * 20111129-01 Mark Piller - Oracle Consulting  add finalizeRateChange(), FINALIZE_RATE_CHG
 * 20111130-01 Mark Piller - Oracle Consulting  add resetRateChange(), RESET_RATE_CHG_PROCESS
 * 20120419-01 Mark Piller - Oracle Consulting  Chg LOGGER to ADFLogger
 * 20120419-02 Mark Piller - Oracle Consulting  Chg from 1 parameter to 3 parameters on stored procedure DELETE_RATE_CHG
 */
public final class StoredProcedureProvider {

    private static final String CLASSNAME =
        StoredProcedureProvider.class.getName();
    // 20120419-01  private static final Logger LOGGER = Logger.getLogger(CLASSNAME);
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(StoredProcedureProvider.class);

    private StoredProcedureProvider() {
        super();
    }

    public static final StoredProcedureProvider INSTANCE =
        new StoredProcedureProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public OperationStatus isCalendarDeletable(String calendarID) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> isCalendarDeletable");

        return callStoredProcedure(StoredProcedure.IS_CALENDAR_DELETABLE,
                                   calendarID);
    }

    public OperationStatus generateRateChange(String rateChgRefID,
                                              String lastUpdatedUser) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> generateRateChange");
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> rateChgRefID = " + rateChgRefID);
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> lastUpdatedUser = " + lastUpdatedUser);

        return callStoredProcedure(StoredProcedure.GENERATE_RATE_CHG,
                                   rateChgRefID, lastUpdatedUser);
    }

    // 20111129-01 added
    public OperationStatus finalizeRateChange(String rateChgRefID,
                                              String lastUpdatedUser) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> finalizeRateChange");
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> rateChgRefID = " + rateChgRefID);
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> lastUpdatedUser = " + lastUpdatedUser);
  
        return callStoredProcedure(StoredProcedure.FINALIZE_RATE_CHG,
                                   rateChgRefID, lastUpdatedUser);
    }

    // 20111130-01 added
    public OperationStatus resetRateChange(String rateChgRefID,
                                              String lastUpdatedUser) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> resetRateChange");
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> rateChgRefId = " + rateChgRefID);
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> lastUpdatedUser = " + lastUpdatedUser);
    
        return callStoredProcedure(StoredProcedure.RESET_RATE_CHG_PROCESS,
                                   rateChgRefID, lastUpdatedUser);
    }

    // 20120419-02 changed callStoredProcedure from 1 paramater to 3 parameters
    // was only rateChgRefID... added lastUpdatedUser, lastUpdatedProgram
    public OperationStatus deleteRateChange(String rateChgRefID,
                                            String lastUpdatedUser,
                                            String lastUpdatedProgram) {
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> deleteRateChange");
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> rateChgRefID = " + rateChgRefID);
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> lastUpdatedUser = " + lastUpdatedUser);
        adfLogger.log(adfLogger.TRACE,"DEBUG  >> lastUpdatedProgram = " + lastUpdatedProgram);

        // 20120419-02
        //return callStoredProcedure(StoredProcedure.DELETE_RATE_CHG,
        //                           rateChgRefID);

        // 20120419-02
        return callStoredProcedure(StoredProcedure.DELETE_RATE_CHG,
                                   rateChgRefID, lastUpdatedUser, lastUpdatedProgram);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    /*
     * This actually is calling a Function in ODI - not a Stored Procedure.
     * The logic still works the same.
     * 
     * There are 5 functions: FINALIZE_RATE_CHG, RESET_RATE_CHG_PROCESS, DELETE_RATE_CHG, GENERATE_RATE_CHG, IS_CALENDAR_DELETABLE.
     * FINALIZE_RATE_CHG, DELETE_RATE_CHG and GENERATE_RATE_CHG return string messages that must be interpreted.
     * 
     * GENERATE_RATE_CHG messages:
     *      Detail generation succeeded: 1259 rows expected, 1259 rows generated. Data committed.
     *      Detail generation failed validation: 1259 rows expected, 1260 rows generated. Rollback executed.
     *      Detail generation failed prior to validation step.
     * 
     * DELETE_RATE_CHG messages:
     *      Deletion of Rate Chg Ref ID 1065 completed successfully.
     *      Deletion of Rate Chg Ref ID 1065 did not complete successfully.
     *      Execution of Rate Chg Ref ID 1065 delete failed
     *
     * IS_CALENDAR_DELETABLE returns 
     *      0 if a failure (locked calendar) and 
     *      1 if successful
     *      
     * FINALIZE_RATE_CHG messages:
     *      Rate Change ID 1048 cannot be finalized due to 25 blocks not deployed and/or 2 open deployments.
     *      Finalization of Rate Change ID 1048 successful.  All updates committed.
     *      Finalization of Rate Change ID 1048 failed.  All updates rolled back.
     */
    private OperationStatus callStoredProcedure(StoredProcedure procedure,
                                                Object... args) {
        // 20120419-01  LOGGER.entering(CLASSNAME, "callStoredProcedure");

        // 20111109-01
        String messageFromODIFunction = null;
        OperationStatus operationStatus = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();
            adfLogger.log(adfLogger.TRACE,"DEBUG  >> Calling getPreparedStatement()");
            adfLogger.log(adfLogger.TRACE,"DEBUG  >> connection: " + connection.toString());
            adfLogger.log(adfLogger.TRACE,"DEBUG  >> procedure: " + procedure.toString());
            preparedStatement = getPreparedStatement(connection, procedure, args);

            adfLogger.log(adfLogger.TRACE,"DEBUG  >> Executing executeQuery()");
            resultSet = preparedStatement.executeQuery();

            adfLogger.log(adfLogger.TRACE,"DEBUG  >> Begin to pass through resultSet");
            while (resultSet.next()) {
                adfLogger.log(adfLogger.TRACE,"DEBUG  >> Fetching resultSet from stored procedure");
                // 20111109-02
                // Get the message returned from the stored procedure.
                Object colObject = resultSet.getObject(1);
                messageFromODIFunction = colObject.toString();
                adfLogger.log(adfLogger.TRACE,"DEBUG  >> Results from stored procedure: " + messageFromODIFunction);
            }

            // 20111109-03
            // evaluate the returned message from the stored procedure
            // determine if it is a success or failure
            // If the string "FAIL" is not found then there is success
            adfLogger.log(adfLogger.TRACE,"DEBUG  >> Setting operation status to success");
            // 20111115-01 added test for "0"
            if (messageFromODIFunction.equals("0")) {
                // FAILURE REPORTED BY FUNCTION
                // Cannot delete calendar
                operationStatus = OperationStatus.failure(new SQLException("Stored procedure returned false."));
            }
            // this test is for a special failure message from the Finalize Rate ODI function
            else if (messageFromODIFunction.toUpperCase().indexOf("CANNOT BE FINALIZED") > 0) {
                // FAILURE REPORTED BY FUNCTION
                adfLogger.log(adfLogger.TRACE,"DEBUG  >> Finalize Rate - ERROR");
                operationStatus = OperationStatus.failure(messageFromODIFunction);
            }
            else if (messageFromODIFunction.toUpperCase().indexOf("FAIL") == -1) {
                // SUCCESS REPORTED BY FUNCTION
                // if the word FAIL does not exist or the result returned is "0" then success
                operationStatus = OperationStatus.success(messageFromODIFunction);
            } else {
                // FAILURE REPORTED BY FUNCTION
                // any occurence of the word FAIL is a failure
                operationStatus = OperationStatus.failure(messageFromODIFunction);
            }

        } catch (SQLException e) {
            adfLogger.warning(ErrorMessage.SELECT_STORED_PROCEDURE.getMessage(), e);
            operationStatus = OperationStatus.failure(e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement, connection);
        }

        adfLogger.log(adfLogger.TRACE,"DEBUG  >> Exiting callStoredProcedure");

        return operationStatus;
    }

    private PreparedStatement getPreparedStatement(Connection connection,
                                                   StoredProcedure procedure,
                                                   Object... args) throws SQLException {

        PreparedStatement preparedStatement =
            connection.prepareStatement(getSelectStatement(procedure.getName()));

        for (int i = 1; i <= procedure.getNumOfArguments(); i++) {
            preparedStatement.setString(i, (String)args[i - 1]);
        }

        return preparedStatement;
    }

    // 20111129-01 added FINALIZE_RATE_CHG - takes 2 parameters
    // 20111130-01 added RESET_RATE_CHG_PROCESS - takes 2 parameters
    // 20120419-02 chg DELETE_RATE_CHG - from 1 parameter to 3 parameters
    private enum StoredProcedure {
        IS_CALENDAR_DELETABLE("IS_CALENDAR_DELETABLE (?)", 1),

        DELETE_RATE_CHG("DELETE_RATE_CHG (?, ?, ?)", 3),  // 20120419-02

        GENERATE_RATE_CHG("GENERATE_RATE_CHG (?, ?)", 2),

        FINALIZE_RATE_CHG("FINALIZE_RATE_CHG (?, ?)", 2),
        
        RESET_RATE_CHG_PROCESS("RESET_RATE_CHG_PROCESS (?, ?)", 2);

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++

        private final String Name;
        private final int NumOfArguments;

        private StoredProcedure(String Name, int NumOfArguments) {
            this.Name = Name;
            this.NumOfArguments = NumOfArguments;
        }

        public String getName() {
            return Name;
        }

        public int getNumOfArguments() {
            return NumOfArguments;
        }
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatement(String procedure) {
        String Attributes = procedure;
        return StatementGenerator.selectStatement(Attributes, "DUAL");
    }
}
