package sfpark.rateChange.manager.view.backing.rateChange;

import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dto.RateChangeProcessControlDTOStatus;
import sfpark.adf.tools.model.provider.RateChangeProcessControlProvider;

import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120531-01 Mark Piller - Oracle Consulting  Add logic for special handling of Pay By Phone
 * 20120531-02 Mark Piller - Oracle Consulting  replace printLog with ADF Logger
 * 
 */
public class RateChangeDeployBean extends BaseBean implements PropertiesBeanInterface,
                                                              RequestScopeBeanInterface {

    // 20120531-02
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(RateChangeDeployBean.class);

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public RateChangeDeployBean() {
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
            DTO = DMLOperationsProvider.INSTANCE.getNewRateChangeHeaderDTO();
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    public RateChangeProcessControlDTO getRateChangeProcessControlDTO() {

        RateChangeProcessControlDTO DTO =
            (RateChangeProcessControlDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey());

        if (DTO == null) {
            DTO =
DMLOperationsProvider.INSTANCE.getNewRateChangeProcessControlDTO(getRateChangeHeaderDTO());
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    public boolean isRenderButtons() {
        return (!getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isRenderCenterFormPanel() {
        return (!getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isRenderPassiveInfo() {
        return getCurrentPageMode().isReadOnlyMode();
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

        if (ID.contains("meterVendor")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickMeter.name());
        } else if (ID.contains("pmDistricts")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickPMDistrictsAndBlocks.name());
        }

    }

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Meter Vendor should be selected
     *    2. Rate Change Reference should NOT exist
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        RateChangeProcessControlDTO rateChangeProcessControlDTO =
            getRateChangeProcessControlDTO();

        boolean checkForRateChgRefUniqueness = false;

        if (currentPageMode.isDeployMode()) {
            checkForRateChgRefUniqueness = true;
        }

        // 20120531-02
        // printLog("Check for RateChgRef = " + checkForRateChgRefUniqueness);
        adfLogger.log(adfLogger.TRACE,"DEBUG >> Check for RateChgRef = \" + checkForRateChgRefUniqueness");

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            String meterVendor = rateChangeProcessControlDTO.getMeterVendor();

            if (StringUtil.isBlank(meterVendor)) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_meter_vendor));
            }
        }

        // 20120531-02
        // printLog("After Meter Vendor = " + allValid);
        adfLogger.log(adfLogger.TRACE,"DEBUG >> After Meter Vendor = \" + allValid");

        if (allValid && checkForRateChgRefUniqueness) {
            RateChangeProcessControlDTOStatus rateChangeProcessControlStatus =
                RateChangeProcessControlProvider.INSTANCE.checkForRateChangeReference(rateChangeProcessControlDTO.getRateChangeReference());

            if (rateChangeProcessControlStatus.existsDTO()) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exists_already_rate_change_reference));
            }
        }

        // 20120531-02
        // printLog("After Rate Chg Ref check = " + allValid);
        adfLogger.log(adfLogger.TRACE,"DEBUG >> After Rate Chg Ref check = \" + allValid");

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
            // 20120531-02
            // printLog("All entries are Valid. Proceed");
            adfLogger.log(adfLogger.TRACE,"DEBUG >> All entries are Valid. Proceed");

            if (currentPageMode.isDeployMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // DEPLOY Mode

                // 20120531-02
                // printLog("DEPLOY Mode");
                adfLogger.log(adfLogger.TRACE,"DEBUG >> DEPLOY Mode");

                RateChangeProcessControlDTO currentDTO =
                    getRateChangeProcessControlDTO();
                
                // 20120531-01
                // Pay By Phone Logic
                // If the Process Step is 10 AND the Meter Vendor is 'PBP' 
                // then change the process step to 20
                String meterVendor = currentDTO.getMeterVendor();
                String processStep = currentDTO.getProcessStep();
                if( (processStep.equals("10")) && (meterVendor.equals("PBP")) ){
                    currentDTO.setProcessStep("20");
                    adfLogger.log(adfLogger.TRACE,"DEBUG >> Pay By Phone... change process step from 10 to 20");
                }

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addRateChangeProcessControl(currentDTO);

                if (operationStatus.isSuccess()) {
                    RateChangeProcessControlDTOStatus rateChangeProcessControlStatus =
                        RateChangeProcessControlProvider.INSTANCE.checkForRateChangeReference(currentDTO.getRateChangeReference());

                    if (rateChangeProcessControlStatus.existsDTO()) {
                        // 20120531-02
                        // printLog("ADD operation was successful");
                        adfLogger.log(adfLogger.TRACE,"DEBUG >> ADD operation was successful");

                        setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_create));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                        clearPageFlowScopeCache();
                        setCurrentPageMode(NavigationMode.READ_ONLY);
                        setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_PROCESS_CONTROL_DTO.getKey(),
                                              rateChangeProcessControlStatus.getDTO());

                    } else {
                        // 20120531-02
                        // printLog("ADD operation failed");
                        adfLogger.log(adfLogger.TRACE,"DEBUG >> ADD operation failed");
                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_rate_change_reference_failure));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    }

                } else {
                    // 20120531-02
                    // printLog("ADD operation failed");
                    adfLogger.log(adfLogger.TRACE,"DEBUG >> ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_rate_change_reference_failure));
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
}
