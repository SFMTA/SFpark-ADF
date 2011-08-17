package sfpark.rateChange.manager.view.flow;

public enum NavigationMode {
    ADD,
    UPDATE,
    DELETE,
    DEPLOY,
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

    public boolean isReadOnlyMode() {
        return (this == READ_ONLY);
    }
}
