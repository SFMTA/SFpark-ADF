package sfpark.calendar.manager.view.flow;

public enum NavigationMode {
    ADD,
    EDIT,
    DELETE,

    READ_ONLY;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isAddMode() {
        return (this == ADD);
    }

    public boolean isEditMode() {
        return (this == EDIT);
    }

    public boolean isDeleteMode() {
        return (this == DELETE);
    }

    public boolean isReadOnlyMode() {
        return (this == READ_ONLY);
    }
}
