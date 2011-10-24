package sfpark.admin.console.view.flow;

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

    public boolean isDeleteMode() {
        return (this == DELETE);
    }

    public boolean isEditMode() {
        return (this == EDIT);
    }

    public boolean isReadOnlyMode() {
        return (this == READ_ONLY);
    }
}
