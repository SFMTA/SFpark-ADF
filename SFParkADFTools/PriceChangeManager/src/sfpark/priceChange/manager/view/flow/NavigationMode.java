package sfpark.priceChange.manager.view.flow;

public enum NavigationMode {
    ADD,
    EDIT,
    EXECUTE,
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

    public boolean isExecuteMode() {
        return (this == EXECUTE);
    }

    public boolean isReadOnlyMode() {
        return (this == READ_ONLY);
    }
}
