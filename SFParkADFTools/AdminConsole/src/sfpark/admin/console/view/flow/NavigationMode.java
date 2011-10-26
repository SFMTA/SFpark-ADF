package sfpark.admin.console.view.flow;

public enum NavigationMode {
    ADD,
    SHOW_DETAILS,
    DELETE,

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

    public boolean isDeleteMode() {
        return (this == DELETE);
    }

    public boolean isReadOnlyMode() {
        return (this == READ_ONLY);
    }
}
