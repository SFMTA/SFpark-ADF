package sfpark.adf.tools.model.exception;

import java.sql.SQLException;

public enum ExceptionType {
    UNIQUE_CONTRAINT,

    CALENDAR_HEADER_INSERT,

    CALENDAR_DETAIL_INSERT,

    RATE_CHANGE_HEADER_INSERT,

    RATE_CHANGE_PROCESS_CONTROL_INSERT,

    OSP_INVENTORY_INSERT,

    GARAGE_OP_HOURS_INSERT,

    GARAGE_RATES_INSERT,

    PARKING_SPACE_INVENTORY_INSERT,

    METER_OP_SCHEDULE_INSERT,

    METER_RATE_SCHEDULE_INSERT,

    SQL_INSERT,

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

        if (exception instanceof MeterOPScheduleInsertException) {
            return METER_OP_SCHEDULE_INSERT;
        }

        if (exception instanceof MeterRateScheduleInsertException) {
            return METER_RATE_SCHEDULE_INSERT;
        }

        if (exception instanceof CalendarHeaderInsertException) {
            return CALENDAR_HEADER_INSERT;
        }

        if (exception instanceof CalendarDetailInsertException) {
            return CALENDAR_DETAIL_INSERT;
        }

        if (exception instanceof RateChangeHeaderInsertException) {
            return RATE_CHANGE_HEADER_INSERT;
        }

        if (exception instanceof RateChangeProcessControlInsertException) {
            return RATE_CHANGE_PROCESS_CONTROL_INSERT;
        }

        if (exception instanceof OSPInventoryInsertException) {
            return OSP_INVENTORY_INSERT;
        }

        if (exception instanceof GarageRatesInsertException) {
            return GARAGE_RATES_INSERT;
        }

        if (exception instanceof GarageOPHoursInsertException) {
            return GARAGE_OP_HOURS_INSERT;
        }

        if (exception instanceof SQLInsertException) {
            return SQL_INSERT;
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
