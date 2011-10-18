package sfpark.rateChange.manager.view.backing.timeband;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import sfpark.adf.tools.model.data.dto.blockTimeBands.BlockTimeBandsDTO;
import sfpark.adf.tools.model.provider.BlockTimeBandsProvider;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;

public class TimebandPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                RequestScopeBeanInterface {

    private RichTable BlockTimeBandsTable;
    private RichTable DeletableTimeBandsTable;

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

    public String getBlockID() {
        String blockID =
            (String)getPageFlowScopeValue(PageFlowScopeKey.BLOCK_ID.getKey());

        if (blockID == null) {
            blockID = "0";
            setPageFlowScopeValue(PageFlowScopeKey.BLOCK_ID.getKey(), blockID);
        }

        return blockID;
    }

    public List<BlockTimeBandsDTO> getBlockTimeBands() {

        List<BlockTimeBandsDTO> blockTimeBandsDTOs =
            (List<BlockTimeBandsDTO>)getPageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BANDS_DTO_LIST.getKey());

        if (blockTimeBandsDTOs == null) {
            blockTimeBandsDTOs =
                    BlockTimeBandsProvider.INSTANCE.getAllBlockTimeBandsDTOs(getBlockID());
            setPageFlowScopeValue(PageFlowScopeKey.BLOCK_TIME_BANDS_DTO_LIST.getKey(),
                                  blockTimeBandsDTOs);
        }
        return blockTimeBandsDTOs;
    }

    public List<BlockTimeBandsDTO> getDeletableTimeBands() {

        List<BlockTimeBandsDTO> deletableTimeBandDTOs =
            (List<BlockTimeBandsDTO>)getPageFlowScopeValue(PageFlowScopeKey.DELETABLE_TIME_BANDS_LIST.getKey());

        if (deletableTimeBandDTOs == null) {
            deletableTimeBandDTOs = new ArrayList<BlockTimeBandsDTO>();
            setPageFlowScopeValue(PageFlowScopeKey.DELETABLE_TIME_BANDS_LIST.getKey(),
                                  deletableTimeBandDTOs);
        }
        return deletableTimeBandDTOs;
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
        // TODO
        /*
        //*
         * Validates the Form and Saves if all entries are valid
         *
         * Common Validity Tests:
         * =====================
         *    1. Table should not be empty
         *
         * @param event
         //
        public void saveButtonHandler(ActionEvent event) {
            boolean allValid = true;
            NavigationMode currentPageMode = getCurrentPageMode();

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++

            if (allValid) {
                List<RateChangeRulesDTO> checkList =
                    (List<RateChangeRulesDTO>)getRateChangeRulesTable().getValue();

                if (checkList == null || checkList.isEmpty()) {
                    allValid = false;
                    setInlineMessageText("There are no thresholds to save."); // TODO
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

                if (currentPageMode.isEditMode()) {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // EDIT Mode
                    printLog("EDIT Mode");

                    String rateChangeType = getRateChangeType();
                    Date effectiveFromDate = getEffectiveFromDate();
                    List<RateChangeRulesDTO> applyList =
                        (List<RateChangeRulesDTO>)getRateChangeRulesTable().getValue();
                    List<RateChangeRulesDTO> replaceList =
                        getReplaceableRateChangeRules();

                    OperationStatus operationStatus =
                        DMLOperationsProvider.INSTANCE.editRateChangeRules(rateChangeType,
                                                                           effectiveFromDate,
                                                                           applyList,
                                                                           replaceList);

                    if (operationStatus.isSuccess()) {
                        // Move on to the next page
                        // Reuse the ERROR_TITLE and ERROR_MESSAGE variables
                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                              TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.string_title_edit_operation_successful));
                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                              TranslationUtil.getRateChangeManagerBundleString(RateChangeManagerBundleKey.string_message_edit_rate_change_rules_successful,
                                                                                               getRateChangeType()));
                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                             NavigationFlow.AfterEditThreshold.name());

                    } else {
                        printLog("ADD operation failed");
                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_edit_failure));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    }

                }

            } else {
                setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
            }
        }
         */
    }

    public void cancelButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
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

    public void setDeletableTimeBandsTable(RichTable DeletableTimeBandsTable) {
        this.DeletableTimeBandsTable = DeletableTimeBandsTable;
    }

    public RichTable getDeletableTimeBandsTable() {
        return DeletableTimeBandsTable;
    }
}
