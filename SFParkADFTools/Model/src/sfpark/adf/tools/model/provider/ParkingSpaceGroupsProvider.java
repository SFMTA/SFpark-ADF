package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dO.parkingSpaceGroups.ParkingSpaceGroupsDO;
import sfpark.adf.tools.model.helper.dO.ParkingSpaceGroupsDOStatus;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class ParkingSpaceGroupsProvider {

    private static final String CLASSNAME =
        ParkingSpaceGroupsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String TABLE_NAME = "PARKING_SPACE_GROUPS";

    private ParkingSpaceGroupsProvider() {
        super();
    }

    public static final ParkingSpaceGroupsProvider INSTANCE =
        new ParkingSpaceGroupsProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public ParkingSpaceGroupsDOStatus checkForParkingSpaceGroupID(String parkingSpaceGroupID) {
        LOGGER.entering(CLASSNAME, "checkForParkingSpaceGroupID");

        ParkingSpaceGroupsDO DO = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatement());
            preparedStatement.setString(1, parkingSpaceGroupID);

            resultSet = preparedStatement.executeQuery();

            List<String> parkingSpaceIDList = new ArrayList<String>();

            while (resultSet.next()) {
                String parkingSpaceID =
                    resultSet.getString(ParkingSpaceGroupsDO.PARKING_SPACE_ID);

                parkingSpaceIDList.add(parkingSpaceID);
            }

            if (!parkingSpaceIDList.isEmpty()) {
                DO =
 new ParkingSpaceGroupsDO(parkingSpaceGroupID, parkingSpaceIDList);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DO.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "checkForParkingSpaceGroupID");

        return new ParkingSpaceGroupsDOStatus(DO);
    }

    /*


            while (resultSet.next()) {
                int numberOfDates =
                    resultSet.getInt(EventCalendarDO.NUMBER_OF_DATES);

                if (numberOfDates > 0) {
                    DO = new EventCalendarDO(calendarName, numberOfDates);
                }
            }
 */

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    private String getSelectStatement() {
        LOGGER.entering(CLASSNAME, "getSelectStatement");

        String Attributes =
            StringUtil.convertListToString(ParkingSpaceGroupsDO.getAttributeListForSelect());

        String Where = ParkingSpaceGroupsDO.PS_GROUP_ID + " = ?";

        String OrderBy = ParkingSpaceGroupsDO.PARKING_SPACE_ID;

        LOGGER.exiting(CLASSNAME, "getSelectStatement");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME,
                                                  Where, OrderBy);
    }

    /*
    private String getSelectStatementForDOs() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForDOs");

        String Attributes =
            StringUtil.convertListToString(EventCalendarDO.getAttributeListForSelect());

        String GroupBy = EventCalendarDO.CALENDAR_NAME;

        String OrderBy = EventCalendarDO.CALENDAR_NAME;

        LOGGER.exiting(CLASSNAME, "getSelectStatementForDOs");

        return StatementGenerator.selectStatement(Attributes, TABLE_NAME, null,
                                                  OrderBy, GroupBy);
    }

 */
}
