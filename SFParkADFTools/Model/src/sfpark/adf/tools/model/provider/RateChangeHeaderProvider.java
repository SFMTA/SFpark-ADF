package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.constants.ErrorMessage;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.util.ConnectUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class RateChangeHeaderProvider {

    private static final String CLASSNAME =
        RateChangeHeaderProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RateChangeHeaderProvider() {
        super();
    }

    public static final RateChangeHeaderProvider INSTANCE =
        new RateChangeHeaderProvider();

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public List<RateChangeHeaderDTO> getRateChangeHeaderDTOsForCalendarID(String calendarID) {
        LOGGER.entering(CLASSNAME, "getRateChangeHeaderDTOsForCalendarID");

        List<RateChangeHeaderDTO> rateChangeHeaderDTOs =
            new ArrayList<RateChangeHeaderDTO>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectUtil.getConnection();

            preparedStatement =
                    connection.prepareStatement(getSelectStatementForCalendarID());
            preparedStatement.setString(1, calendarID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RateChangeHeaderDTO DTO =
                    RateChangeHeaderDTO.extract(resultSet);

                rateChangeHeaderDTOs.add(DTO);
            }

        } catch (SQLException e) {
            LOGGER.warning(ErrorMessage.SELECT_DTO_LIST.getMessage(), e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "getRateChangeHeaderDTOsForCalendarID");

        return rateChangeHeaderDTOs;
    }

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

    private String getSelectStatementForCalendarID() {
        LOGGER.entering(CLASSNAME, "getSelectStatementForCalendarID");

        String Attributes =
            StringUtil.convertListToString(RateChangeHeaderDTO.getAttributeListForSelect());

        String Where =
            StatementGenerator.equalToOperator(RateChangeHeaderDTO.CALENDAR_ID);

        LOGGER.exiting(CLASSNAME, "getSelectStatementForCalendarID");

        return StatementGenerator.selectStatement(Attributes,
                                                  RateChangeHeaderDTO.getDatabaseTableName(),
                                                  Where);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

}
