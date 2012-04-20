package sfpark.rateChange.manager.view.backing.rateChange;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import javax.faces.validator.ValidatorException;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.component.UIXGroup;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.data.helper.RateChangeStatus;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dto.RateChangeHeaderDTOStatus;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;
import sfpark.adf.tools.model.provider.CalendarHeaderProvider;
import sfpark.adf.tools.model.provider.RateChangeHeaderProvider;

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
 * 20111109-01 Mark Piller - Oracle Consulting  added Status Message of Generating Details
 * 20111109-02 Mark Piller - Oracle Consulting  added logic to send Ref ID to ODI Function
 * 20111109-03 Mark Piller - Oracle Consulting  added logic to display message from ODI Function.
 * 20120221-01 Mark Piller - Oracle Consulting  added validator for Active Sensor Coverage Threshold field
 * 20120419-01 Mark Piller - Oracle Consulting  replace printLog with ADF Logger
 */
public class RateChangePropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                  RequestScopeBeanInterface {

    // 20120419-01
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(RateChangePropertiesBean.class);

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public RateChangePropertiesBean() {
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

    public RateChangeHeaderDTO getRateChangeHeaderDTO() {

        // 20120419-01 printLog("RateChangePropertiesBean getRateChangeHeaderDTO()");
        adfLogger.info("DEBUG >> Creating DTO object by getting DTO from PageFlowScope memory");
        // 20120419-01 printLog("Creating DTO object by getting DTO from PageFlowScope memory");
        RateChangeHeaderDTO DTO =
            (RateChangeHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey());

        if (DTO == null) {
            adfLogger.info("DEBUG >> DTO is null therefore was not in PageFlowScope and now creating DTO with DMLOperationsProvider");
            // 20120419-01 printLog("DTO is null therefore was not in PageFlowScope and now creating DTO with DMLOperationsProvider");
            DTO = DMLOperationsProvider.INSTANCE.getNewRateChangeHeaderDTO();
            adfLogger.info("DEBUG >> Saving new DTO to PageFlowScope memory");
            // 20120419-01 printLog("Saving new DTO to PageFlowScope memory");
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
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
    // LIST VALUES

    public List<SelectItem> getListRateChgPolicy() {
        List<SelectItem> listRateChgPolicy = new ArrayList<SelectItem>();

        for (AllowedValuesDTO allowedValuesDTO :
             AllowedValuesRetriever.getRateChgPolicyList()) {
            listRateChgPolicy.add(new SelectItem(allowedValuesDTO.getColumnValue(),
                                                 allowedValuesDTO.getDescription()));
        }

        return listRateChgPolicy;
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

        if (ID.contains("areaType")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickAreaType.name());
        } else if (ID.contains("calendar")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PickCalendar.name());
        }
    }

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Calendar ID should not be ZERO
     *    2. Rate Change Reference should NOT exist
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        // 20120419-01 printLog("RateChangePropertiesBean saveButtonHandler()");

        boolean allValid = true;
        String messageFromGenerateRates; // 20111109-03
        
        NavigationMode currentPageMode = getCurrentPageMode();

        RateChangeHeaderDTO rateChangeHeaderDTO = getRateChangeHeaderDTO();

        boolean checkForRateChgRefUniqueness = false;

        if (currentPageMode.isAddMode()) {
            checkForRateChgRefUniqueness = true;
        }

        adfLogger.info("DEBUG >> Check for RateChgRef = " + checkForRateChgRefUniqueness);
        // 20120419-01 printLog("Check for RateChgRef = " + checkForRateChgRefUniqueness);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            String calendarID = rateChangeHeaderDTO.getCalendarID();

            if (StringUtil.isBlank(calendarID) ||
                StringUtil.areEqual(calendarID, "0")) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_calendar_id));
            }
        }

        adfLogger.info("DEBUG >> After Calendar ID = " + allValid);
        // 20120419-01 printLog("After Calendar ID = " + allValid);

        if (allValid && checkForRateChgRefUniqueness) {
            RateChangeHeaderDTOStatus rateChangeHeaderStatus =
                RateChangeHeaderProvider.INSTANCE.checkForRateChangeReference(rateChangeHeaderDTO.getRateChangeReference());

            if (rateChangeHeaderStatus.existsDTO()) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exists_already_rate_change_reference));
            }
        }

        adfLogger.info("DEBUG >> After Rate Chg Ref check = " + allValid);
        // 20120419-01 printLog("After Rate Chg Ref check = " + allValid);

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
            adfLogger.info("DEBUG >> All entries are Valid. Proceed");
            // 20120419-01 printLog("All entries are Valid. Proceed");

            if (currentPageMode.isAddMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ADD Mode
                adfLogger.info("DEBUG >> ADD Mode");
                // 20120419-01 printLog("ADD Mode");

                RateChangeHeaderDTO currentDTO = getRateChangeHeaderDTO();
                adfLogger.info("DEBUG >> RateChangePropertiesBean - created currentDTO and ref id: " + currentDTO.getRateChangeReferenceID());
                // 20120419-01 printLog("RateChangePropertiesBean - created currentDTO and ref id: " + currentDTO.getRateChangeReferenceID());

                OperationStatus addOperation =
                    DMLOperationsProvider.INSTANCE.addRateChangeHeader(currentDTO);
                adfLogger.info("DEBUG >> RateChangePropertiesBean - currentDTO after addRateChangeHeader  ref id: " + currentDTO.getRateChangeReferenceID());
                // 20120419-01 printLog("RateChangePropertiesBean - currentDTO after addRateChangeHeader  ref id: " + currentDTO.getRateChangeReferenceID());

                if (addOperation.isSuccess()) {
                    adfLogger.info("DEBUG >> addOperation is successful");
                    // 20120419-01 printLog("addOperation is successful");

                    RateChangeHeaderDTO newRateChangeHeaderDTO =
                        RateChangeHeaderProvider.INSTANCE.checkForRateChangeReference(currentDTO.getRateChangeReference()).getDTO();

                    adfLogger.info("DEBUG >> RateChangePropertiesBean - newRateChangeHeaderDTO ref id: " + newRateChangeHeaderDTO.getRateChangeReferenceID());
                    // 20120419-01 printLog("RateChangePropertiesBean - newRateChangeHeaderDTO ref id: " + newRateChangeHeaderDTO.getRateChangeReferenceID());
                    String referenceIdForStoredProcedureCall = newRateChangeHeaderDTO.getRateChangeReferenceID();
                    adfLogger.info("DEBUG >> referenceIdForStoredProcedureCall: " + referenceIdForStoredProcedureCall);
                    // 20120419-01 printLog("referenceIdForStoredProcedureCall: " + referenceIdForStoredProcedureCall);
                    // 20111109-01
                    newRateChangeHeaderDTO.setStatus(RateChangeStatus.WORKING);

                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_create));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                    clearPageFlowScopeCache();
                    setCurrentPageMode(NavigationMode.READ_ONLY);
                    setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                          newRateChangeHeaderDTO);

                    // 20111109-02
                    // get the status of the call to the Function (stored procedure)
                    // that generates the new rates
                    // copy Reference ID to Stored Procedure object
                    currentDTO.setRateChangeReferenceID(referenceIdForStoredProcedureCall);
                    OperationStatus generateOperation =
                        DMLOperationsProvider.INSTANCE.generateRateChange(currentDTO);
                    
                    // 20111109-03
                    // get the message for the Generate Rates function / stored procedure
                    messageFromGenerateRates = generateOperation.getMessage();
                    // display the message
                    setInlineMessageText(messageFromGenerateRates);
                    
                    adfLogger.info("DEBUG >> testing success of operation - " + generateOperation.isSuccess());
                    // 20120419-01 printLog("testing success of operation - " + generateOperation.isSuccess());
                    if (!generateOperation.isSuccess()) {
                        adfLogger.info("DEBUG >> Generate operation failed - reason should be given by Stored Procedure message");
                        // 20120419-01 printLog("Generate operation failed - reason should be given by Stored Procedure message");
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    }

                } else {
                    adfLogger.info("DEBUG >> ADD operation failed");
                    // 20120419-01 printLog("ADD operation failed");
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

    // 20120221-01
    public void isValidActiveSensorThresholdValue(FacesContext facesContext,
                                                  UIComponent uIComponent, Object object) {
        // System.out.println("*** START isValidActiveSensorThresholdValue()");
        boolean validFormat = false;

        // get the value to validate
        String newSensorThreshold = (String)object;
        if ((newSensorThreshold == null) || (newSensorThreshold.length() == 0)) {
            // null values are allowed
            return;
        }
        // System.out.println("newSensorThreshold to validate: " + newSensorThreshold);

        // Active Sensor Coverage Threshold - if numeric must be in format of ##
        // value is at least 1 and at most 3 {1,3} and in range of 0 through 9
        String regExExpression = "([0-9]{1,3})";
        CharSequence inputStr = newSensorThreshold;
        Pattern pattern = Pattern.compile(regExExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        validFormat = matcher.matches();

        if (validFormat) {
            // do nothing
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid format for Active Sensor Coverage Threshold",
                                                          "The value you entered (" + newSensorThreshold +
                                                          ") is an invalid format. Please enter a value from 0 to 100."));
        } // if (validFormat)

    } // isValidActiveSensorThresholdValue
}
