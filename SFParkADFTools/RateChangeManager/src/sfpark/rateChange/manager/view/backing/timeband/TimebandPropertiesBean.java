package sfpark.rateChange.manager.view.backing.timeband;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import sfpark.adf.tools.model.data.dto.blockTimeBands.BlockTimeBandsDTO;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.BlockTimeBandsProvider;
import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class TimebandPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                RequestScopeBeanInterface {

    private RichTable BlockTimeBandsTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public TimebandPropertiesBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
    }

    public void setInlineMessageText(String inlineMessageText) {
        this.setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey(),
                                   inlineMessageText);
    }

    public String getInlineMessageText() {
        return (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey());
    }

    public void setInlineMessageClass(String inlineMessageClass) {
        this.setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey(),
                                   inlineMessageClass);
    }

    public String getInlineMessageClass() {
        return (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public List<BlockTimeBandsDTO> getBlockTimeBands() {

        List<BlockTimeBandsDTO> blockTimeBandsDTOs =
            (List<BlockTimeBandsDTO>)getPageFlowScopeValue(PageFlowScopeKey.EDIT_BLOCK_TIME_BANDS_DTO_LIST.getKey());

        if (blockTimeBandsDTOs == null) {
            blockTimeBandsDTOs =
                    BlockTimeBandsProvider.INSTANCE.getAllBlockTimeBandsDTOs(getBlockID());
            setPageFlowScopeValue(PageFlowScopeKey.EDIT_BLOCK_TIME_BANDS_DTO_LIST.getKey(),
                                  blockTimeBandsDTOs);
        }
        return blockTimeBandsDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS


    public void pickButtonHandler(ActionEvent event) {
        //
        // Reuse as various buttons
        //

        String ID = event.getComponent().getId();

        if (ID.contains("addTimeband")) {
            setCurrentPageMode(NavigationMode.ADD);
        } else if (ID.contains("deleteTimeband")) {
            setCurrentPageMode(NavigationMode.DELETE);
        }

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.PickTimebandType.name());

    }

    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        //        allValid = false;
        //        String tttt =
        //            StringUtil.isBlank(getInlineMessageText()) ? "All are valid" :
        //            getInlineMessageText();
        //        setInlineMessageText(tttt);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            printLog("All entries are Valid. Proceed");

            if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                printLog("EDIT Mode");

                List<BlockTimeBandsDTO> blockTimeBandsDTOs =
                    (List<BlockTimeBandsDTO>)getBlockTimeBandsTable().getValue();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.editBlockTimeBands(blockTimeBandsDTOs);

                if (operationStatus.isSuccess()) {
                    printLog("EDIT operation was successful");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                    setCurrentPageMode(NavigationMode.EDIT);

                    clearPageFlowScopeCache();

                    getBlockTimeBandsTable().setValue(null);

                } else {
                    printLog("EDIT operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_edit_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }
            }

        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }

    }

    public void cancelButtonHandler(ActionEvent event) {
        // Do nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private String getBlockID() {
        String blockID =
            (String)getPageFlowScopeValue(PageFlowScopeKey.BLOCK_ID.getKey());

        if (blockID == null) {
            blockID = "0";
            setPageFlowScopeValue(PageFlowScopeKey.BLOCK_ID.getKey(), blockID);
        }

        return blockID;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setBlockTimeBandsTable(RichTable BlockTimeBandsTable) {
        this.BlockTimeBandsTable = BlockTimeBandsTable;
    }

    public RichTable getBlockTimeBandsTable() {
        return BlockTimeBandsTable;
    }
}
