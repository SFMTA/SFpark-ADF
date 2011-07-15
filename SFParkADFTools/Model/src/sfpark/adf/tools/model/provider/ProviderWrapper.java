package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.List;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.model.data.dto.eventCalendar.EventCalendarDTO;
import sfpark.adf.tools.model.data.dto.eventCalendar.EventCalendarNameDTO;
import sfpark.adf.tools.model.data.dto.garageOPHours.GarageOPHoursDTO;
import sfpark.adf.tools.model.data.dto.garageRates.GarageRatesDTO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.meterRateSchedule.MeterRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.ospInventory.OSPInventoryDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryBulkDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.exception.*;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.status.OperationStatus;
import sfpark.adf.tools.model.util.ConnectUtil;

public final class ProviderWrapper {

    private static final String CLASSNAME = ProviderWrapper.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private ProviderWrapper() {
        super();
    }

    public static final ProviderWrapper INSTANCE = new ProviderWrapper();

    public OperationStatus performOperationOnRecords(List<TableRecord> tableRecords,
                                                     final String lastUpdatedUser,
                                                     final String lastUpdatedProgram) {
        LOGGER.entering(CLASSNAME, "performOperationOnRecords");

        if (tableRecords.isEmpty()) {
            return new OperationStatus(OperationStatus.Type.GENERAL_FAILURE,
                                       new EmptyArgumentsException());
        }

        OperationStatus operationStatus = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = ConnectUtil.getConnection();
            connection.setAutoCommit(false);

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            for (TableRecord tableRecord : tableRecords) {

                BaseDTO DTO = tableRecord.getRecord();

                switch (tableRecord.getOperation()) {

                case INSERT:
                    {
                        preparedStatement =
                                prepareInsertStatement(connection, DTO,
                                                       lastUpdatedUser,
                                                       lastUpdatedProgram);

                        int executeResult = preparedStatement.executeUpdate();

                        if (executeResult == 0) {
                            throw insertException(DTO);
                        }
                    }
                    break;

                case UPDATE:
                    {
                        preparedStatement =
                                prepareUpdateStatement(connection, DTO,
                                                       lastUpdatedUser,
                                                       lastUpdatedProgram);

                        int executeResult = preparedStatement.executeUpdate();

                        if (executeResult == 0) {
                            throw updateException(DTO);
                        }
                    }
                    break;

                case DELETE:
                    {
                        preparedStatement =
                                prepareDeleteStatement(connection, DTO);

                        int executeResult = preparedStatement.executeUpdate();

                        if (executeResult == 0) {
                            throw deleteException(DTO);
                        }
                    }
                    break;
                }
            }

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            connection.commit();

            operationStatus =
                    new OperationStatus(OperationStatus.Type.BATCH_SUCCESSFUL);

        } catch (SQLException e) {
            LOGGER.warning("Could not perform the SQL Operation. ", e);

            ConnectUtil.handleRollback(connection);

            operationStatus =
                    new OperationStatus(OperationStatus.Type.BATCH_FAILURE, e);
        } finally {
            ConnectUtil.closeAll(resultSet, preparedStatement, connection);
        }

        LOGGER.exiting(CLASSNAME, "performOperationOnRecords");

        return operationStatus;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    private PreparedStatement prepareInsertStatement(Connection connection,
                                                     BaseDTO DTO,
                                                     final String lastUpdatedUser,
                                                     final String lastUpdatedProgram) throws SQLException {

        if (DTO instanceof GarageRatesDTO) {
            return GarageRatesProvider.INSTANCE.prepareInsertStatement(connection,
                                                                       (GarageRatesDTO)DTO,
                                                                       lastUpdatedUser,
                                                                       lastUpdatedProgram);
        } else if (DTO instanceof GarageOPHoursDTO) {
            return GarageOPHoursProvider.INSTANCE.prepareInsertStatement(connection,
                                                                         (GarageOPHoursDTO)DTO,
                                                                         lastUpdatedUser,
                                                                         lastUpdatedProgram);
        } else if (DTO instanceof EventCalendarDTO) {
            return EventCalendarProvider.INSTANCE.prepareInsertStatement(connection,
                                                                         (EventCalendarDTO)DTO,
                                                                         lastUpdatedUser,
                                                                         lastUpdatedProgram);
        } else if (DTO instanceof ParkingSpaceInventoryDTO) {
            return ParkingSpaceInventoryProvider.INSTANCE.prepareInsertStatement(connection,
                                                                                 (ParkingSpaceInventoryDTO)DTO,
                                                                                 lastUpdatedUser,
                                                                                 lastUpdatedProgram);
        } else if (DTO instanceof MeterOPScheduleDTO) {
            return MeterOPScheduleProvider.INSTANCE.prepareInsertStatement(connection,
                                                                           (MeterOPScheduleDTO)DTO,
                                                                           lastUpdatedUser,
                                                                           lastUpdatedProgram);
        } else if (DTO instanceof MeterRateScheduleDTO) {
            return MeterRateScheduleProvider.INSTANCE.prepareInsertStatement(connection,
                                                                             (MeterRateScheduleDTO)DTO,
                                                                             lastUpdatedUser,
                                                                             lastUpdatedProgram);
        } else {
            throw new SQLException("Requested DTO operation has not yet been implemented");
        }
    }

    private SQLException insertException(BaseDTO DTO) {

        if (DTO instanceof OSPInventoryDTO) {
            return new OSPInventoryInsertException();
        } else if (DTO instanceof GarageOPHoursDTO) {
            return new GarageOPHoursInsertException();
        } else if (DTO instanceof GarageRatesDTO) {
            return new GarageRatesInsertException();
        } else if (DTO instanceof ParkingSpaceInventoryDTO) {
            return new ParkingSpaceInventoryInsertException();
        } else if (DTO instanceof MeterOPScheduleDTO) {
            return new MeterOPScheduleInsertException();
        } else if (DTO instanceof MeterRateScheduleDTO) {
            return new MeterRateScheduleInsertException();
        }

        return new SQLInsertException();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    private PreparedStatement prepareUpdateStatement(Connection connection,
                                                     BaseDTO DTO,
                                                     final String lastUpdatedUser,
                                                     final String lastUpdatedProgram) throws SQLException {

        if (DTO instanceof OSPInventoryDTO) {
            return OSPInventoryProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                        (OSPInventoryDTO)DTO,
                                                                        lastUpdatedUser,
                                                                        lastUpdatedProgram);
        } else if (DTO instanceof GarageRatesDTO) {
            return GarageRatesProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                       (GarageRatesDTO)DTO,
                                                                       lastUpdatedUser,
                                                                       lastUpdatedProgram);
        } else if (DTO instanceof GarageOPHoursDTO) {
            return GarageOPHoursProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                         (GarageOPHoursDTO)DTO,
                                                                         lastUpdatedUser,
                                                                         lastUpdatedProgram);
        } else if (DTO instanceof EventCalendarNameDTO) {
            return EventCalendarProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                         (EventCalendarNameDTO)DTO,
                                                                         lastUpdatedUser,
                                                                         lastUpdatedProgram);
        } else if (DTO instanceof ParkingSpaceInventoryBulkDTO) {
            return ParkingSpaceInventoryBulkProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                                     (ParkingSpaceInventoryBulkDTO)DTO,
                                                                                     lastUpdatedUser,
                                                                                     lastUpdatedProgram);
        } else if (DTO instanceof ParkingSpaceInventoryDTO) {
            return ParkingSpaceInventoryProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                                 (ParkingSpaceInventoryDTO)DTO,
                                                                                 lastUpdatedUser,
                                                                                 lastUpdatedProgram);
        } else if (DTO instanceof MeterOPScheduleDTO) {
            return MeterOPScheduleProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                           (MeterOPScheduleDTO)DTO,
                                                                           lastUpdatedUser,
                                                                           lastUpdatedProgram);
        } else if (DTO instanceof MeterRateScheduleDTO) {
            return MeterRateScheduleProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                             (MeterRateScheduleDTO)DTO,
                                                                             lastUpdatedUser,
                                                                             lastUpdatedProgram);
        } else {
            throw new SQLException("Requested DTO operation has not yet been implemented");
        }

    }

    private SQLException updateException(BaseDTO DTO) {

        if (DTO instanceof OSPInventoryDTO) {
            return new OSPInventoryUpdateException();
        } else if (DTO instanceof GarageOPHoursDTO) {
            return new GarageOPHoursUpdateException();
        } else if (DTO instanceof GarageRatesDTO) {
            return new GarageRatesUpdateException();
        } else if (DTO instanceof EventCalendarNameDTO) {
            return new EventCalendarUpdateException();
        } else if (DTO instanceof ParkingSpaceInventoryBulkDTO) {
            return new ParkingSpaceInventoryBulkUpdateException();
        } else if (DTO instanceof ParkingSpaceInventoryDTO) {
            return new ParkingSpaceInventoryUpdateException();
        } else if (DTO instanceof MeterOPScheduleDTO) {
            return new MeterOPScheduleUpdateException();
        } else if (DTO instanceof MeterRateScheduleDTO) {
            return new MeterRateScheduleUpdateException();
        }

        return new SQLUpdateException();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    private PreparedStatement prepareDeleteStatement(Connection connection,
                                                     BaseDTO DTO) throws SQLException {

        if (DTO instanceof EventCalendarDTO) {
            return EventCalendarProvider.INSTANCE.prepareDeleteStatement(connection,
                                                                         (EventCalendarDTO)DTO);
        } else {
            throw new SQLException("Requested DTO operation has not yet been implemented");
        }

    }

    private SQLException deleteException(BaseDTO DTO) {

        if (DTO instanceof EventCalendarDTO) {
            return new EventCalendarDeleteException();
        }

        return new SQLUpdateException();
    }

}
