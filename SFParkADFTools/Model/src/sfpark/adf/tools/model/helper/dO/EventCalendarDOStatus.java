package sfpark.adf.tools.model.helper.dO;

import sfpark.adf.tools.model.data.dO.eventCalendar.EventCalendarDO;

@Deprecated
public class EventCalendarDOStatus implements DOStatusInterface {

    private boolean exists;
    private EventCalendarDO DO;

    public EventCalendarDOStatus(EventCalendarDO DO) {
        super();

        this.exists = (DO != null);
        this.DO = DO;
    }

    public boolean existsDO() {
        return exists;
    }

    public EventCalendarDO getDO() {
        return DO;
    }
}
