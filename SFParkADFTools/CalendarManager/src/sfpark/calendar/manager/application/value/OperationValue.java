package sfpark.calendar.manager.application.value;

import java.util.Locale;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum OperationValue {
    ADD,
    EDIT,
    DELETE,

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

    public boolean isEdit() {
        return (this == EDIT);
    }

    public boolean isDelete() {
        return (this == DELETE);
    }

    public boolean isUnknown() {
        return (this == UNKNOWN);
    }
}
