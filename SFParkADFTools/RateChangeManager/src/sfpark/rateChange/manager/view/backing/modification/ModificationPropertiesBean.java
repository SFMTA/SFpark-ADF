package sfpark.rateChange.manager.view.backing.modification;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

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

/**
 * Description:
 * This class controls behavior of the modificationPropertiesPage.jsff
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120131-01 Mark Piller - Oracle Consulting  add logic to set Final Rate to Adjusted Rate when user enters Adjustment Reason
 * 20120319-01 Mark Piller - Oracle Consulting  add logic to allow removal of Adjusted Rate - plus require it if Adjustment Reason is available
 * 20120319-02 Mark Piller - Oracle Consulting  add logic to support change of Adjusted Rate from float data type to String
 */
public class ModificationPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                    RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    private RichInputText finalRateAmount;
    private RichInputText adjustedRateAmount;
    private RichInputText adjustmentReason;
    private RichInputText newRateAmount;

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
    // ALL RENDER INFORMATION

    public boolean isRenderButtons() {
        return (!getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isRenderedRemoveRequiredAdjustedRateButton() {
        boolean isRendered = true;
        if (getCurrentPageMode().isReadOnlyMode() ||
                        getRateChangeHeaderDTO().getStatus().isSubmitted() ||
            getRateChangeHeaderDTO().getStatus().isApproved()){
                isRendered = false;
            }
        
        return isRendered;
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

    // 20120131-01 - added binding
    public void setFinalRateAmount(RichInputText finalRateAmount) {
        this.finalRateAmount = finalRateAmount;
    }

    // 20120131-01 - added binding
    public RichInputText getFinalRateAmount() {
        return finalRateAmount;
    }

    // 20120131-01 - added binding
    public void setAdjustedRateAmount(RichInputText adjustedRateAmount) {
        this.adjustedRateAmount = adjustedRateAmount;
    }

    // 20120131-01 - added binding
    public RichInputText getAdjustedRateAmount() {
        return adjustedRateAmount;
    }

    // 20120131-01 - added logic to set Final Rate to Adjusted Rate when user enters Adjustment Reason
    // 20120319-01 - add more logic to allow removal of Adjusted Rate - plus require it if Adjustment Reason is available
    // 20120319-02 - add more logic to support change of Adjusted Rate from float data type to String
    // This will be the event to copy the value from the Adjusted Rate to the Final Rate when the Adjusted Rate has a value
    // WHEN there is a value in Adjustment Reason
    // THEN there must be a value in Adjustment Rate
    public void AdjustmentReasonValueChange(ValueChangeEvent valueChangeEvent) {
        String newAdjustmentReason = null;
        String adjustedRate = null;

        // save a copy of the new value
        if (valueChangeEvent.getNewValue() instanceof String) {
            newAdjustmentReason = valueChangeEvent.getNewValue().toString();
        }

        // evaluate the new value
        if ( (newAdjustmentReason == null) || (newAdjustmentReason.equals("")) ) {
            // 20120319-01 - No Adjustment Reason... the Adjusted Rate is not required
            this.adjustedRateAmount.setRequired(false);
            this.adjustmentReason.setRequired(false);
            
        } else {
            // 20120319-01 - An Adjustment Reason is being entered... the Adjusted Rate and Adjustment Reason are both required
            this.adjustedRateAmount.setRequired(true);
            this.adjustmentReason.setRequired(true);

            // Also - business rule is: if the user enters an Adjusted Rate, then copy it to the Final Rate
            if (adjustmentReason.getValue() != null) {
                // 20120319-02
                // make a copy of the Adjusted Rate - it is a string value, not a float
                // must test to see if the $ symbol is present and strip it before
                // copying the string value to a float field (Final Rate Amount)
                if(( adjustedRateAmount.getValue() == null) || (adjustedRateAmount.getValue().toString().equals("")) ){
                    // do nothing... no data to work with
                } else {
                    adjustedRate = adjustedRateAmount.getValue().toString();
                    // strip the dollar sign if it exists in the adjustedRate
                    if (adjustedRate.indexOf('$') > -1) {
                        // assuming the $ is the first string
                        adjustedRate = adjustedRate.substring(1);
                    }
                    finalRateAmount.setValue(adjustedRate);
                }

            } // if (adjustmentReason.getValue() != null)
        } // if ( (newAdjustmentReason == null) || (newAdjustmentReason.equals("")) )
    } // AdjustmentReasonValueChange


    // 20120131-01 - added binding
    public void setAdjustmentReason(RichInputText adjustmentReason) {
        this.adjustmentReason = adjustmentReason;
    }


    // 20120131-01 - added binding
    public RichInputText getAdjustmentReason() {
        return adjustmentReason;
    }

    // 20120131-01 - added logic to set Final Rate to Adjusted Rate when user enters Adjusted Rate
    // This will be the event to copy the value from the Adjusted Rate to the Final Rate
    // When the Adjusted Rate is null - then Final Rate equals New Rate... copy New Rate to Final Rate
    // WHEN there is a value in Adjustment Reason
    // THEN there must be a value in Adjustment Rate
    public void AdjustedRateValueChange(ValueChangeEvent valueChangeEvent) {
        String currentAdjustmentReason = "";
        String newAdjustedRate = null;

        // find out what is in the Adjustment Reason
        if(this.adjustmentReason.getValue() == null) {
            // do nothing
        } else {
            currentAdjustmentReason = this.adjustmentReason.getValue().toString();
        }

        // save a copy of the new value
        if (valueChangeEvent.getNewValue() instanceof String) {
            newAdjustedRate = valueChangeEvent.getNewValue().toString();
        }

        // The Adjustment Reason is required when there is a value in the Adjustment Rate
        if (newAdjustedRate.equals("")) {
            // 20120319-01 - No Adjusted Rate... the Adjustment Reason is not required
            this.adjustedRateAmount.setRequired(false);
            this.adjustmentReason.setRequired(false);

            // also copy the New Rate to the Final Rate
            this.finalRateAmount.setValue(this.newRateAmount.getValue());
        } else {
            // 20120319-01 - An Adjusted Rate is being entered... the Adjusted Rate and Adjustment Reason are both required
            this.adjustedRateAmount.setRequired(true);
            this.adjustmentReason.setRequired(true);

            // Also - business rule is: if the user enters an Adjusted Rate, then copy it to the Final Rate
            // 20120319-02
            // The Adjusted Rate is a string value, not a float
            // must test to see if the $ symbol is present and strip it before
            // copying the string value to a float field (Final Rate Amount)
            // strip the dollar sign if it exists in the adjustedRate
            if (newAdjustedRate.indexOf('$') > -1) {
                // assuming the $ is the first string
                newAdjustedRate = newAdjustedRate.substring(1);
            }

            finalRateAmount.setValue(newAdjustedRate);
        } // if (newAdjustedRate.equals(""))
    } // AdjustedRateValueChange

    // 20120319-01 - added to allow the user to remove the required adjusted rate
    public void removeRequiredAdjustedRateButtonHandler(ActionEvent actionEvent) {
        // remove the data and set the fields to not required
        this.adjustedRateAmount.setValue("");
        this.adjustedRateAmount.setRequired(false);
        this.adjustmentReason.setValue("");
        this.adjustmentReason.setRequired(false);
        // Change the Final Rate to be equal to the New Rate
        this.finalRateAmount.setValue(this.newRateAmount.getValue());
    } // removeRequiredAdjustedRateButtonHandler

    public void setNewRateAmount(RichInputText newRateAmount) {
        this.newRateAmount = newRateAmount;
    }

    public RichInputText getNewRateAmount() {
        return newRateAmount;
    }
}
