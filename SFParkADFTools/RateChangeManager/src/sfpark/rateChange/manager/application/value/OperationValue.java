package sfpark.rateChange.manager.application.value;

import java.util.Locale;

import sfpark.adf.tools.utilities.generic.StringUtil;


/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111129-01 Mark Piller - Oracle Consulting  add FINALIZE, isFinalize()
 * 20111130-01 Mark Piller - Oracle Consulting  add RESET, isReset()
 */
public enum OperationValue {

    // Used for RATE_CHG_HEADER
    // 20111129-01
    ADD,
    UPDATE,
    DELETE,
    DEPLOY,
    FINALIZE,
    RESET,

    // Used for RATE_CHG_PROCESS_CONTROL
    EDIT,
    // DELETE (Already available from above)

    // Used for BLOCK_RATE_SCHED
    MODIFY,

    UNKNOWN;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static OperationValue extract(String operationStr) {

        if (StringUtil.isBlank(operationStr)) {
            return UNKNOWN;
        }

        for (OperationValue operationValue : values()) {
            if (operationStr.equalsIgnoreCase(operationValue.name().toLowerCase(Locale.ENGLISH))) {
                return operationValue;
            }
        }

        // Should not reach here
        return UNKNOWN;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isAdd() {
        return (this == ADD);
    }

    public boolean isUpdate() {
        return (this == UPDATE);
    }

    public boolean isDelete() {
        return (this == DELETE);
    }

    public boolean isDeploy() {
        return (this == DEPLOY);
    }

    public boolean isEdit() {
        return (this == EDIT);
    }

    public boolean isModify() {
        return (this == MODIFY);
    }

    public boolean isUnknown() {
        return (this == UNKNOWN);
    }

    // 20111129-01    
    public boolean isFinalize() {
        return (this == FINALIZE);
    }

    // 20111130-01    
    public boolean isReset() {
        return (this == RESET);
    }

}
