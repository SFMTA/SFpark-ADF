package sfpark.adf.tools.utilities.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.utilities.constants.WeekDays;

public final class DayUI {

    /**
     * To avoid instantiation
     */
    private DayUI() {
        super();
    }

    public static final List<String> DAYS_APPLIED_SINGLE_STRING_LIST =
        Collections.unmodifiableList(new ArrayList<String>() {
            {
                add(WeekDays.Mo.name());
            }
        });

    public static final List<SelectItem> DAYS_APPLIED_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                for (WeekDays weekDay : WeekDays.values()) {
                    add(new SelectItem(weekDay.name(),
                                       weekDay.getWeekDayName()));
                }
            }
        });

    public static final List<SelectItem> FROM_DAY_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                for (WeekDays weekDay : WeekDays.values()) {
                    add(new SelectItem(weekDay.getWeekDayName()));
                }
            }
        });

    public static final List<SelectItem> TO_DAY_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                addAll(FROM_DAY_LIST);
                add(new SelectItem("", "-"));
            }
        });
}
