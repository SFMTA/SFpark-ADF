package sfpark.calendar.manager.view.provider.helper;

public enum CalendarDetailOperationType {
    NO_OP("-"),
    INSERT("To be added"),
    DELETE("To be deleted");

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    public boolean isNoOp() {
        return (this == NO_OP);
    }

    public boolean isInsert() {
        return (this == INSERT);
    }

    public boolean isDelete() {
        return (this == DELETE);
    }

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String StringForDisplay;

    private CalendarDetailOperationType(String StringForDisplay) {
        this.StringForDisplay = StringForDisplay;
    }

    public String getStringForDisplay() {
        return StringForDisplay;
    }
}
