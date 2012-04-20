package sfpark.admin.console.view.backing.common;

import sfpark.adf.tools.utilities.generic.ObjectUtil;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.view.backing.BaseBean;

public class ErrorsBean extends BaseBean {
    public ErrorsBean() {
        super();
    }

    public String getErrorTitle() {
        String errorTitle =
            (String)getPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey());

        return ObjectUtil.getNullSafe(errorTitle);
    }

    public String getErrorMessage() {
        String errorMessage =
            (String)getPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey());

        return ObjectUtil.getNullSafe(errorMessage);
    }
}
