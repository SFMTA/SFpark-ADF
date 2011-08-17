package sfpark.rateChange.manager.application.value;

import java.util.Locale;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum OperationValue {
    ADD,
    UPDATE,
    DELETE,
    DEPLOY,

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

    public boolean isUnknown() {
        return (this == UNKNOWN);
    }
}
