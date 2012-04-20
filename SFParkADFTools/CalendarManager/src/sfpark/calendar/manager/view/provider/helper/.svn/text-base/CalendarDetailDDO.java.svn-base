package sfpark.calendar.manager.view.provider.helper;

import java.sql.Date;

/**
 * Display Data Object (DDO) used to modify the Data Object (DO) to incorporate
 * the display requirements.
 *
 * This acts like a wrapper for the "DDateDO" to diplay properly on the UI.
 *
 */
public class CalendarDetailDDO {

    public CalendarDetailDDO(boolean NewDate,
                             CalendarDetailOperationType OperationType,
                             Date DisplayDateDT, String DisplayDateType) {
        super();

        this.NewDate = NewDate;
        this.OperationType = OperationType;
        this.DisplayDateDT = DisplayDateDT;
        this.DisplayDateType = DisplayDateType;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     * Used for easy deleting
     */
    private boolean NewDate;

    /**
     * Helps relay the appropriate information through the UI
     */
    private CalendarDetailOperationType OperationType;

    /**
     * Wrapper for DDateDO$DateDT
     */
    private Date DisplayDateDT;

    /**
     * Wrapper for DDateDO$DateType
     */
    private String DisplayDateType;

    public void setNewDate(boolean NewDate) {
        this.NewDate = NewDate;
    }

    public boolean isNewDate() {
        return NewDate;
    }

    public void setOperationType(CalendarDetailOperationType OperationType) {
        this.OperationType = OperationType;
    }

    public CalendarDetailOperationType getOperationType() {
        return OperationType;
    }

    public void setDisplayDateDT(Date DisplayDateDT) {
        this.DisplayDateDT = DisplayDateDT;
    }

    public Date getDisplayDateDT() {
        return DisplayDateDT;
    }

    public void setDisplayDateType(String DisplayDateType) {
        this.DisplayDateType = DisplayDateType;
    }

    public String getDisplayDateType() {
        return DisplayDateType;
    }
}
