package sfpark.admin.console.view.flow;

public enum NavigationMode {
    ADD,
    SHOW_DETAILS,

    READ_ONLY;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isAddMode() {
        return (this == ADD);
    }

    public boolean isShowDetailsMode() {
        return (this == SHOW_DETAILS);
    }

    public boolean isReadOnlyMode() {
        return (this == READ_ONLY);
    }
}
