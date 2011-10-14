package sfpark.rateChange.manager.view.helper;

import sfpark.adf.tools.model.provider.AllowedValuesRetriever;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class ADFUIHelper {

    /**
     * To avoid instantiation
     */
    private ADFUIHelper() {
        super();
    }

    public static String getDisplayableProcessStep(String possibleStep) {
        return getDisplayableString(true, possibleStep);
    }

    public static String getDisplayableStepExecutionStatus(String stepExecStatus) {
        return getDisplayableString(false, stepExecStatus);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private static String getDisplayableString(boolean forProcessStep,
                                              String forString) {
        if (StringUtil.isBlank(forString)) {
            return "N/A";
        }

        String displayString =
            (forProcessStep) ? AllowedValuesRetriever.getProcessStepTreeMap().get(forString) :
            AllowedValuesRetriever.getProcessStepExecStatusTreeMap().get(forString);

        if (StringUtil.isBlank(displayString)) {
            displayString = "";
        }

        return displayString;
    }
}
