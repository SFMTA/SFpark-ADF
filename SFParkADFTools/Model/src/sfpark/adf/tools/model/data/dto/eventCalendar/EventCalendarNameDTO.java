package sfpark.adf.tools.model.data.dto.eventCalendar;

import java.util.Arrays;
import java.util.List;

import sfpark.adf.tools.model.data.dto.BaseDTO;

public class EventCalendarNameDTO extends BaseDTO {
    public EventCalendarNameDTO() {
        super();
    }

    public EventCalendarNameDTO(String OriginalCalendarName,
                                String CurrentCalendarName) {
        super();

        this.setOriginalCalendarName(OriginalCalendarName);
        this.setCurrentCalendarName(CurrentCalendarName);
    }

    public static final String ORIGINAL_CALENDAR_NAME = "CALENDAR_NAME";
    public static final String CURRENT_CALENDAR_NAME = "CALENDAR_NAME";

    private static final List<String> AttributeListForUpdate =
        Arrays.asList(CURRENT_CALENDAR_NAME, LAST_UPD_USER, LAST_UPD_PGM);

    public static List<String> getAttributeListForUpdate() {
        return AttributeListForUpdate;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String OriginalCalendarName;
    private String CurrentCalendarName;

    public void setOriginalCalendarName(String OriginalCalendarName) {
        this.OriginalCalendarName = OriginalCalendarName;
    }

    public String getOriginalCalendarName() {
        return OriginalCalendarName;
    }

    public void setCurrentCalendarName(String CurrentCalendarName) {
        this.CurrentCalendarName = CurrentCalendarName;
    }

    public String getCurrentCalendarName() {
        return CurrentCalendarName;
    }
}
