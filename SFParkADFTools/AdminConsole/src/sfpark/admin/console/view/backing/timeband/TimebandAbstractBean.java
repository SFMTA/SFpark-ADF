package sfpark.admin.console.view.backing.timeband;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.view.backing.BaseBean;
import sfpark.admin.console.view.helper.TimeBandModelWrapper;
import sfpark.admin.console.view.provider.DMLOperationsProvider;

public abstract class TimebandAbstractBean extends BaseBean {

    protected TimebandAbstractBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public TimeBandModelWrapper getTimeBandModelWrapper() {
        TimeBandModelWrapper wrapper =
            (TimeBandModelWrapper)getPageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_WRAPPER.getKey());

        if (wrapper == null) {
            wrapper =
                    DMLOperationsProvider.INSTANCE.getNewTimeBandModelWrapper();
            setPageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_WRAPPER.getKey(),
                                  wrapper);
        }

        return wrapper;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    protected void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_WRAPPER.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.ADD_TIME_BAND_MODEL_DTO_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.TIMEBAND_PICKER_CHOSEN_LIST.getKey());
    }
}
