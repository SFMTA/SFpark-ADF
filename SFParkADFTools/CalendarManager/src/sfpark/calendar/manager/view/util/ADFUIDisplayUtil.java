package sfpark.calendar.manager.view.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.helper.CalendarType;

public final class ADFUIDisplayUtil {

    /**
     * To avoid instantiation
     */
    private ADFUIDisplayUtil() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Date Type Util

    public static final List<SelectItem> CALENDAR_TYPE_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem(CalendarType.RATE_CHANGE, "Rate Change"));
                add(new SelectItem(CalendarType.SPECIAL_EVENT,
                                   "Special Event"));
            }
        });
}
