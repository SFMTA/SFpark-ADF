package sfpark.rateChange.manager.view.backing.common;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.helper.dto.BlockRateScheduleDTOStatus;
import sfpark.adf.tools.model.helper.dto.RateChangeHeaderDTOStatus;
import sfpark.adf.tools.model.provider.BlockRateScheduleProvider;
import sfpark.adf.tools.model.provider.RateChangeHeaderProvider;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.RequestParameter;

import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.ParameterKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.application.value.OperationValue;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.flow.NavigationMode;

public class ModificationBean extends BaseBean {

    private static final String CLASSNAME = ModificationBean.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RichRegion ContentRichRegion;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public ModificationBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS EXTRA

    public RegionModel getContentRegionModel() {
        RegionModel model = new RegionModel() {
            public String getViewId(FacesContext facesContext) {

                decideCurrentNavFlow();

                String currentNav =
                    (String)getSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey());

                if (currentNav == null) {
                    currentNav = NavigationFlow.ERROR.name();
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         currentNav);
                }

                NavigationFlow currentNavFlow =
                    NavigationFlow.valueOf(currentNav);

                return currentNavFlow.getFileName();
            }

            @Override
            public void processBeginRegion(FacesContext facesContext,
                                           RegionSite regionSite) {
                // RegionModel throws UnsupportedOperationException here, so
                // we need to override it.
            }

            @Override
            public void processEndRegion(FacesContext facesContext,
                                         RegionSite regionSite) {
                // RegionModel throws UnsupportedOperationException here, so
                // we need to override it.
            }

        };

        return model;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    public void setContentRichRegion(RichRegion ContentRichRegion) {
        this.ContentRichRegion = ContentRichRegion;
    }

    public RichRegion getContentRichRegion() {
        return ContentRichRegion;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void decideCurrentNavFlow() {
        LOGGER.entering(CLASSNAME, "decideCurrentNavFlow");

        HttpServletRequest request = getHttpRequest();
        String httpMethod = request.getMethod();
        String ADFCtrlCurrent = getCurrentADFControl();
        String ADFCtrlFromURL =
            getRequestParameterValue(RequestParameter.ADF_CTRL_STATE.getKey());

        if ( // HTTP Method is GET
            httpMethod.equalsIgnoreCase("GET") &&

            // It is a new Faces Context Control State
            !StringUtil.areEqual(ADFCtrlCurrent, ADFCtrlFromURL)) {

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // NEW Request encountered
            // Start all processes from scratch

            // Clear all cache
            clearAllCache();

            // Set the ADF Control Value
            setCurrentADFControl(ADFCtrlFromURL);

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // Retrieve the parameters

            String operationStr =
                getRequestParameterValue(ParameterKey.OPERATION.getKey());
            String blockRateSchedID =
                getRequestParameterValue(ParameterKey.BLOCK_RATE_SCHED_ID.getKey());

            // Interpret NULL ID as ZERO
            if (StringUtil.isBlank(blockRateSchedID)) {
                blockRateSchedID = "0";
            }

            // Convert operation String into OperationValue
            OperationValue operation = OperationValue.extract(operationStr);

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++

            if ( // VALID Parameters
                !operation.isUnknown() &&
                StringUtil.isDigitsONLY(blockRateSchedID)) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++

                BlockRateScheduleDTOStatus blockRateSchedule =
                    BlockRateScheduleProvider.INSTANCE.checkForBlockRateSchedID(blockRateSchedID);

                if ( // Block Rate Schedule EXISTS
                    blockRateSchedule.existsDTO()) {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++

                    String rateChangeRefID =
                        blockRateSchedule.getDTO().getRateChangeReferenceID();

                    RateChangeHeaderDTOStatus rateChangeHeader =
                        RateChangeHeaderProvider.INSTANCE.checkForRateChangeReferenceID(rateChangeRefID);

                    if ( // Rate Change EXISTS
                        rateChangeHeader.existsDTO()) {
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++

                        RateChangeHeaderDTO rateChangeHeaderDTO =
                            rateChangeHeader.getDTO();

                        if ( // MODIFY Mode
                            operation.isModify()) {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++

                            if ( // NOT Approved
                                !rateChangeHeaderDTO.getStatus().isApproved()) {
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                LOGGER.debug("MODIFY Mode");
                                setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                                      rateChangeHeaderDTO);
                                setPageFlowScopeValue(PageFlowScopeKey.BLOCK_RATE_SCHED_DTO.getKey(),
                                                      blockRateSchedule.getDTO());
                                setCurrentPageMode(NavigationMode.MODIFY);
                                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                     NavigationFlow.ModifyBlockRateSched.name());

                            } else {
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                LOGGER.warning("Unsupported Operation - Can not modify, already approved");
                                setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_unsupported_operation));
                                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_unsupported_block_rate_sched_modify_operation,
                                                                                           blockRateSchedID));
                                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                     NavigationFlow.ERROR.name());
                            }

                        } else {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            LOGGER.warning("Unsupported Operation");
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_unsupported_operation));
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_unsupported_operation,
                                                                                       operationStr));
                            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                 NavigationFlow.ERROR.name());
                        }

                    } else {
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        LOGGER.warning("Rate Change Reference does not exist");
                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                              TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_nonexistent_resource));
                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                              TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_nonexistent_rate_change_reference_id_resource,
                                                                                   rateChangeRefID));
                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                             NavigationFlow.ERROR.name());
                    }

                } else {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    LOGGER.warning("Block Rate Schedule does not exist");
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_nonexistent_resource));
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_nonexistent_block_rate_sched_id_resource,
                                                                               blockRateSchedID));
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.ERROR.name());
                }

            } else {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                LOGGER.warning("Invalid Parameters");
                setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_invalid_parameters));

                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_invalid_rate_change_manager_modify_parameters,
                                                                           operationStr,
                                                                           blockRateSchedID));
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.ERROR.name());
            }

        } else if ( // HTTP Method is POST
            httpMethod.equalsIgnoreCase("POST")) {

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++

            setCurrentADFControl(ADFCtrlFromURL);
        }

        LOGGER.exiting(CLASSNAME, "decideCurrentNavFlow");
    }

    private void clearAllCache() {

        for (PageFlowScopeKey pfsKey : PageFlowScopeKey.values()) {
            removePageFlowScopeValue(pfsKey.getKey());
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CURRENT ADF CONTROL MECHANISM

    private String currentADFControl = "";

    private void setCurrentADFControl(String currentADFControl) {
        this.currentADFControl = currentADFControl;
    }

    private String getCurrentADFControl() {
        return currentADFControl;
    }
}
