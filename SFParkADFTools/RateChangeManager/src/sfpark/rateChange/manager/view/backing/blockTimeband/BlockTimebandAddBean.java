package sfpark.rateChange.manager.view.backing.blockTimeband;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import sfpark.adf.tools.model.data.dto.blockTimeBands.BlockTimeBandsDTO;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.BlockTimeBandsProvider;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.helper.BlockTimeBandsWrapper;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class BlockTimebandAddBean extends BlockTimebandAbstractBean implements PropertiesBeanInterface,
                                                                               RequestScopeBeanInterface {
    private RichTable InsertBlockTimeBandsTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public BlockTimebandAddBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        super.clearPageFlowScopeCache();
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

    public List<BlockTimeBandsDTO> getInsertableBlockTimeBands() {

        List<BlockTimeBandsDTO> insertableBlockTimeBandsDTOs =
            (List<BlockTimeBandsDTO>)getPageFlowScopeValue(PageFlowScopeKey.ADD_BLOCK_TIME_BANDS_DTO_LIST.getKey());

        if (insertableBlockTimeBandsDTOs == null) {
            insertableBlockTimeBandsDTOs = new ArrayList<BlockTimeBandsDTO>();
            setPageFlowScopeValue(PageFlowScopeKey.ADD_BLOCK_TIME_BANDS_DTO_LIST.getKey(),
                                  insertableBlockTimeBandsDTOs);
        }
        return insertableBlockTimeBandsDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            List<BlockTimeBandsDTO> checkList =
                (List<BlockTimeBandsDTO>)getInsertBlockTimeBandsTable().getValue();

            if (checkList == null || checkList.isEmpty()) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_no_timebands_to_insert));
            }
        }

        printLog("After check list = " + allValid);

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

            if (currentPageMode.isAddMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ADD Mode
                printLog("ADD Mode");

                List<BlockTimeBandsDTO> insertableBlockTimeBandsDTOs =
                    (List<BlockTimeBandsDTO>)getInsertBlockTimeBandsTable().getValue();
                List<BlockTimeBandsDTO> deletableBlockTimeBandsDTOs =
                    getDeletableBlockTimeBands();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addBlockTimeBands(insertableBlockTimeBandsDTOs,
                                                                     deletableBlockTimeBandsDTOs);

                if (operationStatus.isSuccess()) {
                    printLog("ADD operation successful");

                    removePageFlowScopeValue(PageFlowScopeKey.EDIT_BLOCK_TIME_BANDS_DTO_LIST.getKey());
                    moveOn();

                } else {
                    printLog("ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_save_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }

            }

        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {
        clearPageFlowScopeCache();

        setCurrentPageMode(NavigationMode.EDIT);
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.EditTimeband.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<BlockTimeBandsDTO> getDeletableBlockTimeBands() {

        BlockTimeBandsWrapper wrapper = getBlockTimeBandsWrapper();

        return BlockTimeBandsProvider.INSTANCE.getToBeDeletedBlockTimeBandsDTOs(wrapper.getBlockID(),
                                                                                wrapper.getMeterClass(),
                                                                                wrapper.getDateType());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setInsertBlockTimeBandsTable(RichTable InsertBlockTimeBandsTable) {
        this.InsertBlockTimeBandsTable = InsertBlockTimeBandsTable;
    }

    public RichTable getInsertBlockTimeBandsTable() {
        return InsertBlockTimeBandsTable;
    }
}
