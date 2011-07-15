package sfpark.osp.manager.view.flow;

public enum NavigationMode {
    ADD,
    EDIT,
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

    public boolean isReadOnlyMode() {
        return (this == READ_ONLY);
    }
}
