package sfpark.event.manager.view.provider.helper;

import java.util.Date;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sfpark.event.manager.view.provider.helper.eventCalendar.EventCalendarDateDAO;

public final class DAOHelper {

    /**
     * To avoid instantiation
     */
    private DAOHelper() {
    }

    public static List<Date> retrieveDatesFrom(List<EventCalendarDateDAO> eventCalendarDateDAOs) {
        List<Date> dates = new ArrayList<Date>();

        for (EventCalendarDateDAO dao : eventCalendarDateDAOs) {
            dates.add(dao.getDate());
        }

        Collections.sort(dates);

        return dates;
    }
}
