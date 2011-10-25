package sfpark.admin.console.application.value;

import java.util.ArrayList;
import java.util.List;

import sfpark.adf.tools.utilities.generic.StringUtil;

public enum OperationValue {

    // Used for ALLOWED_VALUES
    ALLOWED_VALUES("allowedValues"),

    // Used for TIME_BANDS_MODEL
    TIME_BANDS_MODEL("timeBandsModel"),

    UNKNOWN("");

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static List<String> getValidValues() {

        List<String> validValues = new ArrayList<String>();

        for (OperationValue operationValue : values()) {
            if (!operationValue.isUnknown()) {
                validValues.add(operationValue.getValueString());
            }
        }

        return validValues;
    }

    public static OperationValue extract(String operationStr) {

        if (StringUtil.isBlank(operationStr)) {
            return UNKNOWN;
        }

        for (OperationValue operationValue : values()) {
            if (StringUtil.areEqual(operationValue.getValueString(),
                                    operationStr)) {
                return operationValue;
            }
        }

        // Should not reach here
        return UNKNOWN;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isAllowedValues() {
        return (this == ALLOWED_VALUES);
    }

    public boolean isUnknown() {
        return (this == UNKNOWN);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String ValueString;

    private OperationValue(String ValueString) {
        this.ValueString = ValueString;
    }

    public String getValueString() {
        return ValueString;
    }
}
