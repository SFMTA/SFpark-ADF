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

    public static final List<SelectItem> FROM_TIME_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                for (int i = 0, time = 0; i < 48; i++) {
                    add(new SelectItem(TimeDisplayUtil.extractFromTimeForDisplay(time)));
                    time += (i % 2 == 0) ? 30 : 70;
                }
            }
        });

    public static final List<SelectItem> TO_TIME_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                for (int i = 0, time = 30; i < 48; i++) {
                    add(new SelectItem(TimeDisplayUtil.extractToTimeForDisplay(time)));
                    time += (i % 2 == 0) ? 70 : 30;
                }
            }
        });
}
