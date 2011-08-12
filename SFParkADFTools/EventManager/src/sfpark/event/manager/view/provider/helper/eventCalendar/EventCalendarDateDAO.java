package sfpark.event.manager.view.provider.helper.eventCalendar;

import java.sql.Date;

public class EventCalendarDateDAO {
    public EventCalendarDateDAO(boolean newDate,
                                EventCalendarDateDAO.DateType dateType,
                                Date date) {
        super();
        this.newDate = newDate;
        this.dateType = dateType;
        this.date = date;
    }

    public enum DateType {
        NO_OP,
        INSERT,
        DELETE;

        // ++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++

        public boolean isNoOp() {
            return (this == NO_OP);
        }

        public boolean isInsert() {
            return (this == INSERT);
        }

        public boolean isDelete() {
            return (this == DELETE);
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private boolean newDate;
    private DateType dateType;
    private Date date;

    public void setNewDate(boolean newDate) {
        this.newDate = newDate;
    }

    public boolean isNewDate() {
        return newDate;
    }

    public void setDateType(EventCalendarDateDAO.DateType dateType) {
        this.dateType = dateType;
    }

    public EventCalendarDateDAO.DateType getDateType() {
        return dateType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

}
