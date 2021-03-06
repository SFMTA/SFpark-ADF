
package sfpark.rateChange.manager.view.flow;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111129-01 Mark Piller - Oracle Consulting  add FINALIZE, isFinalize()
 * 20111130-01 Mark Piller - Oracle Consulting  add RESET, isReset()
 */
public enum NavigationMode {
    // 20111129-01 add FINALIZE
    // 20111130-01 add RESET
    ADD,
    UPDATE,
    DELETE,
    DEPLOY,
    FINALIZE,
    RESET,

    EDIT,
    EXECUTE,

    MODIFY,

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

    public boolean isModifyMode() {
        return (this == MODIFY);
    }

    public boolean isReadOnlyMode() {
        return (this == READ_ONLY);
    }

    // 20111129-01 added
    public boolean isFinalize() {
        return (this == FINALIZE);
    }

    // 20111130-01 added
    public boolean isReset() {
        return (this == RESET);
    }

}
