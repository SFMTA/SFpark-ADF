package sfpark.rateChange.manager.application.value;

import java.util.Locale;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum OperationValue {

    // Used for RATE_CHG_HEADER
    ADD,
    UPDATE,
    DELETE,
    DEPLOY,

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
}
