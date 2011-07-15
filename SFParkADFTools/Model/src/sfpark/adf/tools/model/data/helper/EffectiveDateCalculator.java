package sfpark.adf.tools.model.data.helper;

import java.sql.Date;

import sfpark.adf.tools.utilities.generic.SQLDateUtil;

public final class EffectiveDateCalculator {

    /**
     * To avoid instantiation
     */
    private EffectiveDateCalculator() {
        super();
    }

    @Deprecated
    public static Date getBulkEffectiveToDateWhenDeleting(Date deleteDTOFromDate,
                                                          Date deleteDTOToDate) {

        Date newToDate = SQLDateUtil.getYesterdaysDate();

        if (newToDate.after(deleteDTOToDate)) {
            newToDate = deleteDTOToDate;
        }

        if (newToDate.before(deleteDTOFromDate)) {
            newToDate = deleteDTOFromDate;
        }

        return newToDate;
    }

    public static Date getEffectiveToDateWhenDeleting(Date deleteDTOFromDate,
                                                      Date deleteDTOToDate) {
        Date newToDate = SQLDateUtil.getYesterdaysDate();

        if (newToDate.before(deleteDTOFromDate)) {
            newToDate = deleteDTOFromDate;
        }

        if (newToDate.after(deleteDTOToDate)) {
            newToDate = deleteDTOToDate;
        }

        return newToDate;
    }

    public static Date getEffectiveToDateWhenWhatChangedIsBothDates(Date modifiedDTOFromDate,
                                                                    Date originalDTOFromDate) {

        // Update original DTO with TO date as previous date to modified DTO's FROM date

        Date newToDate = SQLDateUtil.getPreviousDateFor(modifiedDTOFromDate);

        if (newToDate.before(originalDTOFromDate)) {
            newToDate = originalDTOFromDate;
        }

        return newToDate;
    }

    public static Date getEffectiveToDateWhenWhatChangedIsOnlyFromDate(Date modifiedDTOFromDate,
                                                                       Date originalDTOFromDate) {

        // Update original DTO with TO date as previous date to modified DTO's FROM date

        Date newToDate = SQLDateUtil.getPreviousDateFor(modifiedDTOFromDate);

        if (newToDate.before(originalDTOFromDate)) {
            newToDate = originalDTOFromDate;
        }

        return newToDate;
    }

    public static EffectiveDates getEffectiveDatesWhenWhatChangedIsNotDates(Date originalDTOFromDate,
                                                                            Date originalDTOToDate) {

        Date newToDate = SQLDateUtil.getYesterdaysDate();
        Date newFromDate = SQLDateUtil.getTodaysDate();

        if (newToDate.before(originalDTOFromDate)) {
            newToDate = originalDTOFromDate;
        }

        if (newToDate.after(originalDTOToDate)) {
            newToDate = originalDTOToDate;
        }

        if (newFromDate.after(originalDTOToDate)) {
            newFromDate = originalDTOToDate;
        }

        return new EffectiveDates(newFromDate, newToDate);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER CLASSES

    public static final class EffectiveDates {
        private Date NewFromDate;
        private Date NewToDate;

        public EffectiveDates(Date NewFromDate, Date NewToDate) {
            super();

            this.NewFromDate = NewFromDate;
            this.NewToDate = NewToDate;
        }

        public void setNewFromDate(Date NewFromDate) {
            this.NewFromDate = NewFromDate;
        }

        public Date getNewFromDate() {
            return NewFromDate;
        }

        public void setNewToDate(Date NewToDate) {
            this.NewToDate = NewToDate;
        }

        public Date getNewToDate() {
            return NewToDate;
        }
    }
}
