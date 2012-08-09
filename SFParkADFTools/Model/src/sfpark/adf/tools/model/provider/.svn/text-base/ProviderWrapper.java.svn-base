package sfpark.adf.tools.model.provider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.helper.OracleDBConnection;
import sfpark.adf.tools.model.data.dto.BaseDTO;
import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.data.dto.blockRateSchedule.BlockRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.blockTimeBands.BlockTimeBandsDTO;
import sfpark.adf.tools.model.data.dto.calendar.CalendarDetailDTO;
import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.data.dto.garageOPHours.GarageOPHoursDTO;
import sfpark.adf.tools.model.data.dto.garageRates.GarageRatesDTO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.meterRateSchedule.MeterRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.ospInventory.OSPInventoryDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeRulesDTO;
import sfpark.adf.tools.model.data.dto.timeBandModel.TimeBandModelDTO;
import sfpark.adf.tools.model.exception.*;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.TableRecord;

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
            return OperationStatus.failure(new IllegalArgumentException("Empty arguments received."));
        }

        OperationStatus operationStatus = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = OracleDBConnection.getConnection();
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

            operationStatus = OperationStatus.success();

        } catch (SQLException e) {
            LOGGER.warning("Could not perform the SQL Operation. ", e);

            OracleDBConnection.handleRollback(connection);

            operationStatus = OperationStatus.failure(e);
        } finally {
            OracleDBConnection.closeAll(resultSet, preparedStatement,
                                        connection);
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
        } else if (DTO instanceof CalendarHeaderDTO) {
            return CalendarHeaderProvider.INSTANCE.prepareInsertStatement(connection,
                                                                          (CalendarHeaderDTO)DTO,
                                                                          lastUpdatedUser,
                                                                          lastUpdatedProgram);
        } else if (DTO instanceof CalendarDetailDTO) {
            return CalendarDetailProvider.INSTANCE.prepareInsertStatement(connection,
                                                                          (CalendarDetailDTO)DTO,
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
        } else if (DTO instanceof RateChangeHeaderDTO) {
            return RateChangeHeaderProvider.INSTANCE.prepareInsertStatement(connection,
                                                                            (RateChangeHeaderDTO)DTO,
                                                                            lastUpdatedUser,
                                                                            lastUpdatedProgram);
        } else if (DTO instanceof RateChangeProcessControlDTO) {
            return RateChangeProcessControlProvider.INSTANCE.prepareInsertStatement(connection,
                                                                                    (RateChangeProcessControlDTO)DTO,
                                                                                    lastUpdatedUser,
                                                                                    lastUpdatedProgram);
        } else if (DTO instanceof RateChangeRulesDTO) {
            return RateChangeRulesProvider.INSTANCE.prepareInsertStatement(connection,
                                                                           (RateChangeRulesDTO)DTO,
                                                                           lastUpdatedUser,
                                                                           lastUpdatedProgram);
        } else if (DTO instanceof BlockTimeBandsDTO) {
            return BlockTimeBandsProvider.INSTANCE.prepareInsertStatement(connection,
                                                                          (BlockTimeBandsDTO)DTO,
                                                                          lastUpdatedUser,
                                                                          lastUpdatedProgram);
        } else if (DTO instanceof TimeBandModelDTO) {
            return TimeBandModelProvider.INSTANCE.prepareInsertStatement(connection,
                                                                         (TimeBandModelDTO)DTO,
                                                                         lastUpdatedUser,
                                                                         lastUpdatedProgram);
        } else if (DTO instanceof AllowedValuesDTO) {
            return AllowedValuesProvider.INSTANCE.prepareInsertStatement(connection,
                                                                         (AllowedValuesDTO)DTO,
                                                                         lastUpdatedUser,
                                                                         lastUpdatedProgram);
        } else {
            throw new SQLException("Requested DTO operation has not yet been implemented");
        }
    }

    private SQLException insertException(BaseDTO DTO) {

        if (DTO instanceof OSPInventoryDTO) {
            return new DTOInsertException(OSPInventoryDTO.getDatabaseTableName());
        } else if (DTO instanceof GarageOPHoursDTO) {
            return new DTOInsertException(GarageOPHoursDTO.getDatabaseTableName());
        } else if (DTO instanceof GarageRatesDTO) {
            return new DTOInsertException(GarageRatesDTO.getDatabaseTableName());
        } else if (DTO instanceof ParkingSpaceInventoryDTO) {
            return new DTOInsertException(ParkingSpaceInventoryDTO.getDatabaseTableName());
        } else if (DTO instanceof MeterOPScheduleDTO) {
            return new DTOInsertException(MeterOPScheduleDTO.getDatabaseTableName());
        } else if (DTO instanceof MeterRateScheduleDTO) {
            return new DTOInsertException(MeterRateScheduleDTO.getDatabaseTableName());
        } else if (DTO instanceof CalendarHeaderDTO) {
            return new DTOInsertException(CalendarHeaderDTO.getDatabaseTableName());
        } else if (DTO instanceof CalendarDetailDTO) {
            return new DTOInsertException(CalendarDetailDTO.getDatabaseTableName());
        } else if (DTO instanceof RateChangeHeaderDTO) {
            return new DTOInsertException(RateChangeHeaderDTO.getDatabaseTableName());
        } else if (DTO instanceof RateChangeProcessControlDTO) {
            return new DTOInsertException(RateChangeProcessControlDTO.getDatabaseTableName());
        } else if (DTO instanceof RateChangeRulesDTO) {
            return new DTOInsertException(RateChangeRulesDTO.getDatabaseTableName());
        } else if (DTO instanceof BlockTimeBandsDTO) {
            return new DTOInsertException(BlockTimeBandsDTO.getDatabaseTableName());
        } else if (DTO instanceof TimeBandModelDTO) {
            return new DTOInsertException(TimeBandModelDTO.getDatabaseTableName());
        } else if (DTO instanceof AllowedValuesDTO) {
            return new DTOInsertException(AllowedValuesDTO.getDatabaseTableName());
        }

        return new DTOInsertException();
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
        } else if (DTO instanceof CalendarHeaderDTO) {
            return CalendarHeaderProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                          (CalendarHeaderDTO)DTO,
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
        } else if (DTO instanceof RateChangeHeaderDTO) {
            return RateChangeHeaderProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                            (RateChangeHeaderDTO)DTO,
                                                                            lastUpdatedUser,
                                                                            lastUpdatedProgram);
        } else if (DTO instanceof RateChangeProcessControlDTO) {
            return RateChangeProcessControlProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                                    (RateChangeProcessControlDTO)DTO,
                                                                                    lastUpdatedUser,
                                                                                    lastUpdatedProgram);
        } else if (DTO instanceof BlockRateScheduleDTO) {
            return BlockRateScheduleProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                             (BlockRateScheduleDTO)DTO,
                                                                             lastUpdatedUser,
                                                                             lastUpdatedProgram);
        } else if (DTO instanceof RateChangeRulesDTO) {
            return RateChangeRulesProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                           (RateChangeRulesDTO)DTO,
                                                                           lastUpdatedUser,
                                                                           lastUpdatedProgram);
        } else if (DTO instanceof BlockTimeBandsDTO) {
            return BlockTimeBandsProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                          (BlockTimeBandsDTO)DTO,
                                                                          lastUpdatedUser,
                                                                          lastUpdatedProgram);
        } else if (DTO instanceof AllowedValuesDTO) {
            return AllowedValuesProvider.INSTANCE.prepareUpdateStatement(connection,
                                                                         (AllowedValuesDTO)DTO,
                                                                         lastUpdatedUser,
                                                                         lastUpdatedProgram);
        } else {
            throw new SQLException("Requested DTO operation has not yet been implemented");
        }

    }

    private SQLException updateException(BaseDTO DTO) {

        if (DTO instanceof OSPInventoryDTO) {
            return new DTOUpdateException(OSPInventoryDTO.getDatabaseTableName());
        } else if (DTO instanceof GarageOPHoursDTO) {
            return new DTOUpdateException(GarageOPHoursDTO.getDatabaseTableName());
        } else if (DTO instanceof GarageRatesDTO) {
            return new DTOUpdateException(GarageRatesDTO.getDatabaseTableName());
        } else if (DTO instanceof CalendarHeaderDTO) {
            return new DTOUpdateException(CalendarHeaderDTO.getDatabaseTableName());
        } else if (DTO instanceof ParkingSpaceInventoryDTO) {
            return new DTOUpdateException(ParkingSpaceInventoryDTO.getDatabaseTableName());
        } else if (DTO instanceof MeterOPScheduleDTO) {
            return new DTOUpdateException(MeterOPScheduleDTO.getDatabaseTableName());
        } else if (DTO instanceof MeterRateScheduleDTO) {
            return new DTOUpdateException(MeterRateScheduleDTO.getDatabaseTableName());
        } else if (DTO instanceof RateChangeHeaderDTO) {
            return new DTOUpdateException(RateChangeHeaderDTO.getDatabaseTableName());
        } else if (DTO instanceof RateChangeProcessControlDTO) {
            return new DTOUpdateException(RateChangeProcessControlDTO.getDatabaseTableName());
        } else if (DTO instanceof BlockRateScheduleDTO) {
            return new DTOUpdateException(BlockRateScheduleDTO.getDatabaseTableName());
        } else if (DTO instanceof RateChangeRulesDTO) {
            return new DTOUpdateException(RateChangeRulesDTO.getDatabaseTableName());
        } else if (DTO instanceof BlockTimeBandsDTO) {
            return new DTOUpdateException(BlockTimeBandsDTO.getDatabaseTableName());
        } else if (DTO instanceof AllowedValuesDTO) {
            return new DTOUpdateException(AllowedValuesDTO.getDatabaseTableName());
        }

        return new DTOUpdateException();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    private PreparedStatement prepareDeleteStatement(Connection connection,
                                                     BaseDTO DTO) throws SQLException {

        if (DTO instanceof CalendarHeaderDTO) {
            return CalendarHeaderProvider.INSTANCE.prepareDeleteStatement(connection,
                                                                          (CalendarHeaderDTO)DTO);
        } else if (DTO instanceof CalendarDetailDTO) {
            return CalendarDetailProvider.INSTANCE.prepareDeleteStatement(connection,
                                                                          (CalendarDetailDTO)DTO);
        } else if (DTO instanceof RateChangeProcessControlDTO) {
            return RateChangeProcessControlProvider.INSTANCE.prepareDeleteStatement(connection,
                                                                                    (RateChangeProcessControlDTO)DTO);
        } else if (DTO instanceof BlockTimeBandsDTO) {
            return BlockTimeBandsProvider.INSTANCE.prepareDeleteStatement(connection,
                                                                          (BlockTimeBandsDTO)DTO);
        } else if (DTO instanceof TimeBandModelDTO) {
            return TimeBandModelProvider.INSTANCE.prepareDeleteStatement(connection,
                                                                         (TimeBandModelDTO)DTO);
        } else if (DTO instanceof AllowedValuesDTO) {
            return AllowedValuesProvider.INSTANCE.prepareDeleteStatement(connection,
                                                                         (AllowedValuesDTO)DTO);
        } else {
            throw new SQLException("Requested DTO operation has not yet been implemented");
        }

    }

    private SQLException deleteException(BaseDTO DTO) {

        if (DTO instanceof CalendarHeaderDTO) {
            return new DTODeleteException(CalendarHeaderDTO.getDatabaseTableName());
        } else if (DTO instanceof CalendarDetailDTO) {
            return new DTODeleteException(CalendarDetailDTO.getDatabaseTableName());
        } else if (DTO instanceof RateChangeProcessControlDTO) {
            return new DTODeleteException(RateChangeProcessControlDTO.getDatabaseTableName());
        } else if (DTO instanceof BlockTimeBandsDTO) {
            return new DTODeleteException(BlockTimeBandsDTO.getDatabaseTableName());
        } else if (DTO instanceof TimeBandModelDTO) {
            return new DTODeleteException(TimeBandModelDTO.getDatabaseTableName());
        } else if (DTO instanceof AllowedValuesDTO) {
            return new DTODeleteException(AllowedValuesDTO.getDatabaseTableName());
        }

        return new DTODeleteException();
    }

}
