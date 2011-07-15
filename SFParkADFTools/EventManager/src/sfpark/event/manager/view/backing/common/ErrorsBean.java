package sfpark.event.manager.view.backing.common;

import sfpark.adf.tools.utilities.generic.ObjectUtil;

import sfpark.event.manager.application.key.PageFlowScopeKey;
import sfpark.event.manager.view.backing.BaseBean;

public class ErrorsBean extends BaseBean {
    public ErrorsBean() {
        super();
    }

    public String getErrorMessage() {
        String errorMessage =
            (String)this.getPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey());

        return ObjectUtil.getNullSafe(errorMessage);
    }
}
