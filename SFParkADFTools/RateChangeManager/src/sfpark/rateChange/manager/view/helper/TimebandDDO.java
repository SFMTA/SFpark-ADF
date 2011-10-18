package sfpark.rateChange.manager.view.helper;

import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

/**
 * Display Date Object (DDO) used to modify the primitive datatype Object to
 * incorporate the display requirements.
 *
 * This acts like a wrapper for the "From" and "To" times to display properly
 * on the UI
 *
 */
public class TimebandDDO {
    public TimebandDDO(int TimeValue) {
        super();

        this.TimeValue = TimeValue;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DISPLAY PURPOSES ONLY

    public String getDisplayTimeValue() {
        return TimeDisplayUtil.extractAnyTimeForDisplay(getTimeValue());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final int TimeValue;

    public int getTimeValue() {
        return TimeValue;
    }
}
