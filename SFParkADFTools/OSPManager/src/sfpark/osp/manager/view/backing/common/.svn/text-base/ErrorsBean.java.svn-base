package sfpark.osp.manager.view.backing.common;

import sfpark.adf.tools.utilities.generic.ObjectUtil;

import sfpark.osp.manager.application.key.PageFlowScopeKey;
import sfpark.osp.manager.view.backing.BaseBean;

public class ErrorsBean extends BaseBean {

    public ErrorsBean() {
        super();
    }

    public String getErrorMessage() {
        String errorMessage =
            (String)getPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey());

        return ObjectUtil.getNullSafe(errorMessage);
    }
}
