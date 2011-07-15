package sfpark.adf.tools.model.data.helper;

public enum MeterScheduleType {
    OP,
    TOW,
    ALT;

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isScheduleOP() {
        return (this == OP);
    }

    public boolean isScheduleTOW() {
        return (this == TOW);
    }

    public boolean isScheduleALT() {
        return (this == ALT);
    }
}
