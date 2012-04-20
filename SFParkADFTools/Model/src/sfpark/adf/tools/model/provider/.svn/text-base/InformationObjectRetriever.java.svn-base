package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.infoObject.column.ColumnInfoObject;
import sfpark.adf.tools.model.data.infoObject.table.TableInfoObject;
import sfpark.adf.tools.model.helper.infoObject.ColumnInfoObjectStatus;
import sfpark.adf.tools.model.helper.infoObject.TableInfoObjectStatus;

public final class InformationObjectRetriever {

    private static final String CLASSNAME =
        InformationObjectRetriever.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    /**
     * To avoid instantiation
     */
    private InformationObjectRetriever() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public static TableInfoObjectStatus checkForTable(String tableName) {
        LOGGER.entering(CLASSNAME, "checkForTable");

        TableInfoObject tableInfoObject = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            resultSet =
                    databaseMetaData.getTables(null, OracleDBConnection.DB_SCHEMA,
                                               tableName, null);

            while (resultSet.next()) {
                tableInfoObject = TableInfoObject.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning("Table Retrieval Exception. ", e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForTable");

        return new TableInfoObjectStatus(tableInfoObject);
    }

    public static ColumnInfoObjectStatus checkForColumn(String tableName,
                                                        String columnName) {
        LOGGER.entering(CLASSNAME, "checkForColumn");

        ColumnInfoObject columnInfoObject = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = OracleDBConnection.getConnection();

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            resultSet =
                    databaseMetaData.getColumns(null, OracleDBConnection.DB_SCHEMA,
                                                tableName, columnName);

            while (resultSet.next()) {
                columnInfoObject = ColumnInfoObject.extract(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warning("Column Retrieval Exception. ", e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForColumn");

        return new ColumnInfoObjectStatus(columnInfoObject);
    }
}
