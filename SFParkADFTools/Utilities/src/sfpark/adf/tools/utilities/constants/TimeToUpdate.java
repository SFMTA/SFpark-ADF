package sfpark.adf.tools.utilities.constants;

public enum TimeToUpdate {
    DEFAULT(4),

    ONE_HOUR(1),
    TWO_HOURS(2),
    THREE_HOURS(3),
    FOUR_HOURS(4),
    FIVE_HOURS(5),
    DAY(24);

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final long TimeInMillis;

    private TimeToUpdate(int amount) {
        this.TimeInMillis = amount * 60 * 60 * 1000;
    }

    public long getTimeInMillis() {
        return TimeInMillis;
    }
}
