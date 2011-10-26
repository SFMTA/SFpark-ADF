package sfpark.admin.console.view.backing.timeband;

import sfpark.admin.console.view.backing.BaseBean;

public abstract class TimebandAbstractBean extends BaseBean {
    public TimebandAbstractBean() {
        super();
    }
}
/*
package sfpark.rateChange.manager.view.backing.blockTimeband;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.helper.BlockTimeBandsWrapper;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public abstract class BlockTimebandAbstractBean extends BaseBean {
    protected BlockTimebandAbstractBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    public BlockTimeBandsWrapper getBlockTimeBandsWrapper() {
        BlockTimeBandsWrapper wrapper =
            (BlockTimeBandsWrapper)getPageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BANDS_WRAPPER.getKey());

        if (wrapper == null) {
            wrapper =
                    DMLOperationsProvider.INSTANCE.getNewBlockTimeBandsWrapper(getBlockID());
            setPageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BANDS_WRAPPER.getKey(),
                                  wrapper);
        }

        return wrapper;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    protected void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BANDS_WRAPPER.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.ADD_BLOCK_TIME_BANDS_DTO_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.TIMEBAND_PICKER_CHOSEN_LIST.getKey());
    }

    protected String getBlockID() {
        String blockID =
            (String)getPageFlowScopeValue(PageFlowScopeKey.BLOCK_ID.getKey());

        if (blockID == null) {
            blockID = "0";
        }

        return blockID;
    }
}

 */