package sfpark.adf.tools.model.util;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import sfpark.adf.tools.helper.DeveloperMode;
import sfpark.adf.tools.helper.Logger;

public final class ConnectUtil {

    private static final String CLASSNAME = ConnectUtil.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static Context initialContext = null;

    private static final boolean USE_DATA_SOURCE =
        DeveloperMode.DEPLOYED_ON_SERVER;

    private static final String DATA_SOURCE = "jdbc.SFParkADFDataSource";

    /**
     * To avoid instantiation
     */
    private ConnectUtil() {
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // NECESSARY DURING DEVELOPMENT
    private static final String DEV_CONNECTION =
        "jdbc:oracle:thin:@lnxoradev1:1521/sfpark1";
    private static final String TEST_CONNECTION =
        "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=sfparktrac1a)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=sfparktrac1b)(PORT=1521))(FAILOVER=on)(LOAD_BALANCE=off))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=prktrac)))";
    // TODO: Alternate between DEV and TEST depending on the situation
    private static final String CONNECTION_URL = DEV_CONNECTION;
    private static final String DRIVER_NAME = "oracle.jdbc.OracleDriver";
    private static final String USERNAME = "SFPARK_ODS";
    private static final String PASSWORD = "SFPARK_ODS";
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     * Returns the Database Connection
     *
     * @return Database Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {

        if (USE_DATA_SOURCE) {
            try {
                DataSource dataSource =
                    (DataSource)getInitialContext().lookup(DATA_SOURCE);
                return dataSource.getConnection();
            } catch (NamingException e) {
                throw new SQLException("Unable to get Initial Context. " +
                                       e.getMessage());
            }
        } else {
            try {
                Class.forName(DRIVER_NAME);
                return DriverManager.getConnection(CONNECTION_URL, USERNAME,
                                                   PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Unable to load DB Driver. " +
                                       e.getMessage());
            }
        }
    }

    /**
     * Performs the rollback operation on a Connection and consumes the exception
     * as a warning entry
     *
     * @param connection
     */
    public static void handleRollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.trace("IGNORE: Could not rollback. ", e);
            }
        }
    }

    /**
     * Closes all transaction components
     *
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void closeAll(ResultSet resultSet, Statement statement,
                                Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.trace("IGNORE: ResultSet close issue. ", e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.trace("IGNORE: Statement close issue. ", e);
            }
        }

        closeConnection(connection);
    }

    /**
     * Closes the Database Connection
     *
     * @param connection
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.trace("IGNORE: Connection close issue. ", e);
            }
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    /**
     * Returns the Initial Context using the local static variable. If its null,
     * then it creates a new one
     *
     * @return Initial Context
     * @throws NamingException
     */
    private static Context getInitialContext() throws NamingException {
        if (initialContext == null) {
            initialContext = new InitialContext();
        }

        return initialContext;
    }
}
