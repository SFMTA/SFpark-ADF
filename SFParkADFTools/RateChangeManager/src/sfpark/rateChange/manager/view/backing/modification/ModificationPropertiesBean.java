package sfpark.rateChange.manager.view.backing.modification;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import sfpark.adf.tools.model.data.dto.blockRateSchedule.BlockRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.exception.DTOUpdateException;
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dto.BlockRateScheduleDTOStatus;
import sfpark.adf.tools.model.provider.BlockRateScheduleProvider;
import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class ModificationPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                    RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public ModificationPropertiesBean() {
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

    private RateChangeHeaderDTO getRateChangeHeaderDTO() {

        RateChangeHeaderDTO DTO =
            (RateChangeHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey());

        if (DTO == null) {
            // Should NOT happen
            // Just in case
            DTO = new RateChangeHeaderDTO();
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    public BlockRateScheduleDTO getBlockRateScheduleDTO() {
        BlockRateScheduleDTO DTO =
            (BlockRateScheduleDTO)getPageFlowScopeValue(PageFlowScopeKey.BLOCK_RATE_SCHED_DTO.getKey());

        if (DTO == null) {
            // Should not happen
            // Just in case
            DTO = new BlockRateScheduleDTO();
            setPageFlowScopeValue(PageFlowScopeKey.BLOCK_RATE_SCHED_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL READ-ONLY INFORMATION

    public boolean isReadOnly() {
        return (getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isReadOnlyAdjustedInfo() {
        return (getCurrentPageMode().isReadOnlyMode() ||
                getRateChangeHeaderDTO().getStatus().isSubmitted() ||
                getRateChangeHeaderDTO().getStatus().isApproved());
    }

    public boolean isReadOnlyFinalInfo() {
        return (getCurrentPageMode().isReadOnlyMode() ||
                getRateChangeHeaderDTO().getStatus().isWorking() ||
                getRateChangeHeaderDTO().getStatus().isApproved());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL VISIBLE INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    public boolean isRenderButtons() {
        return (!getCurrentPageMode().isReadOnlyMode());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;

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

            if (getCurrentPageMode().isModifyMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // MODIFY Mode
                printLog("MODIFY Mode");

                BlockRateScheduleDTO currentDTO = getBlockRateScheduleDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.modifyBlockRateSchedule(currentDTO);

                if (operationStatus.isSuccess()) {
                    printLog("MODIFY operation was successful");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                    BlockRateScheduleDTOStatus blockRateScheduleStatus =
                        BlockRateScheduleProvider.INSTANCE.checkForBlockRateSchedID(currentDTO.getBlockRateSchedID());

                    setCurrentPageMode(NavigationMode.READ_ONLY);
                    setPageFlowScopeValue(PageFlowScopeKey.BLOCK_RATE_SCHED_DTO.getKey(),
                                          blockRateScheduleStatus.getDTO());

                    clearPageFlowScopeCache();

                } else {
                    printLog("MODIFY operation failed");

                    String errorMessage = "";

                    switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                    case DTO_UPDATE:
                        {
                            String tableName =
                                ((DTOUpdateException)operationStatus.getException()).getTableName();

                            if (StringUtil.areEqual(tableName,
                                                    BlockRateScheduleDTO.getDatabaseTableName())) {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_block_rate_sched_update);
                            } else {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                            }
                        }
                        break;

                    default:
                        errorMessage =
                                TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                        break;
                    }

                    setInlineMessageText(errorMessage);
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

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

}
