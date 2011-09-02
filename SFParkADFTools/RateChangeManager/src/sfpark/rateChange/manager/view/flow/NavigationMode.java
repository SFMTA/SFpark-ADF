package sfpark.rateChange.manager.view.flow;

public enum NavigationMode {
    ADD,
    UPDATE,
    DELETE,
    DEPLOY,

    EDIT,
    EXECUTE,

    READ_ONLY;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isAddMode() {
        return (this == ADD);
    }

    public boolean isUpdateMode() {
        return (this == UPDATE);
    }

    public boolean isDeleteMode() {
        return (this == DELETE);
    }

    public boolean isDeployMode() {
        return (this == DEPLOY);
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
