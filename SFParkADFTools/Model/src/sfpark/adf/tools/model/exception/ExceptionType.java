package sfpark.adf.tools.model.exception;

import java.sql.SQLException;

public enum ExceptionType {
    UNIQUE_CONTRAINT,

    CALENDAR_HEADER_INSERT,
    CALENDAR_HEADER_UPDATE,
    CALENDAR_HEADER_DELETE,

    CALENDAR_DETAIL_INSERT,
    CALENDAR_DETAIL_DELETE,

    RATE_CHANGE_HEADER_INSERT,
    RATE_CHANGE_HEADER_UPDATE,

    RATE_CHANGE_PROCESS_CONTROL_INSERT,
    RATE_CHANGE_PROCESS_CONTROL_UPDATE,

    OSP_INVENTORY_INSERT,
    OSP_INVENTORY_UPDATE,

    GARAGE_OP_HOURS_INSERT,
    GARAGE_OP_HOURS_UPDATE,

    GARAGE_RATES_INSERT,
    GARAGE_RATES_UPDATE,

    PARKING_SPACE_INVENTORY_BULK_UPDATE,

    PARKING_SPACE_INVENTORY_INSERT,
    PARKING_SPACE_INVENTORY_UPDATE,

    METER_OP_SCHEDULE_INSERT,
    METER_OP_SCHEDULE_UPDATE,

    METER_RATE_SCHEDULE_INSERT,
    METER_RATE_SCHEDULE_UPDATE,

    SQL_INSERT,
    SQL_UPDATE,
    SQL_DELETE,

    SQL,
    GENERAL;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static ExceptionType getExceptionType(Exception exception) {

        if (exception.getMessage().indexOf("ORA-00001: unique constraint") !=
            -1) {
            return UNIQUE_CONTRAINT;
        }

        if (exception instanceof ParkingSpaceInventoryInsertException) {
            return PARKING_SPACE_INVENTORY_INSERT;
        }

        if (exception instanceof ParkingSpaceInventoryUpdateException) {
            return PARKING_SPACE_INVENTORY_UPDATE;
        }

        if (exception instanceof ParkingSpaceInventoryBulkUpdateException) {
            return PARKING_SPACE_INVENTORY_BULK_UPDATE;
        }

        if (exception instanceof MeterOPScheduleInsertException) {
            return METER_OP_SCHEDULE_INSERT;
        }

        if (exception instanceof MeterOPScheduleUpdateException) {
            return METER_OP_SCHEDULE_UPDATE;
        }

        if (exception instanceof MeterRateScheduleInsertException) {
            return METER_RATE_SCHEDULE_INSERT;
        }

        if (exception instanceof MeterRateScheduleUpdateException) {
            return METER_RATE_SCHEDULE_UPDATE;
        }

        if (exception instanceof CalendarHeaderInsertException) {
            return CALENDAR_HEADER_INSERT;
        }

        if (exception instanceof CalendarHeaderUpdateException) {
            return CALENDAR_HEADER_UPDATE;
        }

        if (exception instanceof CalendarHeaderDeleteException) {
            return CALENDAR_HEADER_DELETE;
        }

        if (exception instanceof CalendarDetailInsertException) {
            return CALENDAR_DETAIL_INSERT;
        }

        if (exception instanceof CalendarDetailDeleteException) {
            return CALENDAR_DETAIL_DELETE;
        }

        if (exception instanceof RateChangeHeaderInsertException) {
            return RATE_CHANGE_HEADER_INSERT;
        }

        if (exception instanceof RateChangeHeaderUpdateException) {
            return RATE_CHANGE_HEADER_UPDATE;
        }

        if (exception instanceof RateChangeProcessControlInsertException) {
            return RATE_CHANGE_PROCESS_CONTROL_INSERT;
        }

        if (exception instanceof RateChangeProcessControlUpdateException) {
            return RATE_CHANGE_PROCESS_CONTROL_UPDATE;
        }

        if (exception instanceof OSPInventoryInsertException) {
            return OSP_INVENTORY_INSERT;
        }

        if (exception instanceof OSPInventoryUpdateException) {
            return OSP_INVENTORY_UPDATE;
        }

        if (exception instanceof GarageRatesInsertException) {
            return GARAGE_RATES_INSERT;
        }

        if (exception instanceof GarageRatesUpdateException) {
            return GARAGE_RATES_UPDATE;
        }

        if (exception instanceof GarageOPHoursInsertException) {
            return GARAGE_OP_HOURS_INSERT;
        }

        if (exception instanceof GarageOPHoursUpdateException) {
            return GARAGE_OP_HOURS_UPDATE;
        }

        if (exception instanceof SQLInsertException) {
            return SQL_INSERT;
        }

        if (exception instanceof SQLUpdateException) {
            return SQL_UPDATE;
        }

        if (exception instanceof SQLDeleteException) {
            return SQL_DELETE;
        }

        if (exception instanceof SQLException) {
            return SQL;
        }

        return GENERAL;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /*
     * public static ExceptionType getExceptionType(Exception exception) {
     *
     *      if (exception.getMessage().indexOf("ORA-00001: unique constraint") != -1) {
     *            return UNIQUE_CONTRAINT;
     *      }
     *
     *      for (ExceptionType exceptionType : ExceptionType.values()) {
     *
     *            switch (exceptionType) {
     *
     *            case UNIQUE_CONSTRAINT:
     *                  // Do Nothing
     *                  break;
     *
     *            default:
     *                  if (exception.getClass().isAssignableFrom(exceptionType.getExceptionClass())) {
     *                        return exceptionType;
     *                  }
     *                  break;
     *            }
     *      }
     *
     *      return ExceptionType.GENERAL;
     * }
     *
     *
     * private final Class exceptionClass;
     *
     * private ExceptionType(Class exceptionClass) {
     *      this.exceptionClass = exceptionClass;
     * }
     *
     * private Class getExceptionClass() {
     *      return exceptionClass;
     * }
     */
}
