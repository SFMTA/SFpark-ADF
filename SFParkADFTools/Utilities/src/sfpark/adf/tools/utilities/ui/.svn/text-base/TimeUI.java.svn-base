package sfpark.adf.tools.utilities.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

public final class TimeUI {

    /**
     * To avoid instantiation
     */
    private TimeUI() {
        super();
    }

    /**
     * Provides list of SelectItem objects for a time dropdown menu
     * From  00:00
     * To    24:00
     *
     * To be used when there is no restriction of From and To times
     *
     */
    public static final List<SelectItem> ANY_TIME_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                for (int i = 0, time = 0; i <= 48; i++) {
                    add(new SelectItem(time,
                                       TimeDisplayUtil.extractAnyTimeForDisplay(time)));
                    time += (i % 2 == 0) ? 30 : 70;
                }
            }
        });

    /**
     * Provides list of SelectItem objects, specifically for a FROM time dropdown menu
     * From  00:00
     * To    23:30
     *
     * To be used ONLY for FROM time UI
     *
     */
    public static final List<SelectItem> FROM_TIME_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                for (int i = 0, time = 0; i < 48; i++) {
                    add(new SelectItem(TimeDisplayUtil.extractAnyTimeForDisplay(time)));
                    time += (i % 2 == 0) ? 30 : 70;
                }
            }
        });

    /**
     * Provides list of SelectItem objects, specifically for a TO time dropdown menu
     * From  00:30
     * To    24:00
     *
     * To be used ONLY for TO time UI
     *
     */
    public static final List<SelectItem> TO_TIME_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                for (int i = 0, time = 30; i < 48; i++) {
                    add(new SelectItem(TimeDisplayUtil.extractAnyTimeForDisplay(time)));
                    time += (i % 2 == 0) ? 70 : 30;
                }
            }
        });
}
