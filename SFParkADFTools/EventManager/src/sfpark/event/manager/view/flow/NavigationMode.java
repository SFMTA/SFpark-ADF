package sfpark.event.manager.view.flow;

public enum NavigationMode {
    ERROR,
    LIST,
    ADD,
    EDIT,
    ADD_OR_EDIT;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isErrorMode() {
        return (this == ERROR);
    }

    public boolean isListMode() {
        return (this == LIST);
    }

    public boolean isAddMode() {
        return (this == ADD);
    }

    public boolean isEditMode() {
        return (this == EDIT);
    }

    public boolean isAddOrEditMode() {
        return (this == ADD || this == EDIT || this == ADD_OR_EDIT);
    }
}
