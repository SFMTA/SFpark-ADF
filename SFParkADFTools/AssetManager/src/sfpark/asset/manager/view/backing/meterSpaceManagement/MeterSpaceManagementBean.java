package sfpark.asset.manager.view.backing.meterSpaceManagement;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;

import sfpark.adf.tools.model.data.dO.blockface.BlockfaceDO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.meterRateSchedule.MeterRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.data.helper.EffectiveDateCalculator;
import sfpark.adf.tools.model.data.helper.MeterOPScheduleType;
import sfpark.adf.tools.model.data.helper.MeterRateScheduleType;
import sfpark.adf.tools.model.exception.DTOInsertException;
import sfpark.adf.tools.model.exception.DTOUpdateException;
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dO.BlockfaceDOStatus;
import sfpark.adf.tools.model.helper.dto.ParkingSpaceInventoryDTOStatus;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;
import sfpark.adf.tools.model.provider.BlockfacesProvider;
import sfpark.adf.tools.model.provider.MeterOPScheduleProvider;
import sfpark.adf.tools.model.provider.MeterRateScheduleProvider;
import sfpark.adf.tools.model.provider.ParkingSpaceInventoryProvider;
import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.utilities.constants.RegularExpression;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;
import sfpark.adf.tools.utilities.ui.DayUI;
import sfpark.adf.tools.utilities.ui.TimeUI;
import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.asset.manager.application.key.PageFlowScopeKey;
import sfpark.asset.manager.application.key.SessionScopeKey;
import sfpark.asset.manager.view.backing.BaseBean;
import sfpark.asset.manager.view.flow.NavigationFlow;
import sfpark.asset.manager.view.flow.NavigationMode;
import sfpark.asset.manager.view.provider.CommonUtils;
import sfpark.asset.manager.view.provider.DMLOperationsProvider;
import sfpark.asset.manager.view.util.ADFUIDisplayUtil;
import sfpark.asset.manager.view.util.DataRepositoryUtil;
import sfpark.asset.manager.application.key.ViewScopeKey;

import sfpark.adf.tools.utilities.ui.ADFUtils;


/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111207-01 Mark Piller - Oracle Consulting  Change getListTimeLimit() logic to refer to Allowed Values list of values
 * 20120222-01 Mark Piller - Oracle Consulting  Add logic to warn user there are active Op/Rate Schedules when inactivating meter
 * 20120311-01 Mark Piller - Oracle Consulting  Add logic to remove warning after a save operation
 * 20120501-01 Mark Piller - Oracle Consulting  Add logic to disable buttons when user selects Edit Existing Record check boxes for Meter Schedule Table
 * 20120501-02 Mark Piller - Oracle Consulting  Add logic to disable buttons when user selects Edit Existing Record check boxes for Meter Rate Table
 * 20120521-01 Mark Piller - Oracle Consulting  Add logic to display warning when changing OP Schedule Start Time
 * 20120522-01 Mark Piller - Oracle Consulting  Add logic to support when and which warning messages are displayed for OP Schedule Start Time changes
 * 20120522-02 Mark Piller - Oracle Consulting  Add logic to preserve Multi Space Pay Station ID and Multi Space Number
 */
public class MeterSpaceManagementBean extends BaseBean implements RequestScopeBeanInterface,
                                                                  ListBeanInterface,
                                                                  PropertiesBeanInterface {
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(MeterSpaceManagementBean.class);
    
    private RichSelectOneRadio streetTypeSOR;

    private RichInputText meterVendorIT;
    private RichInputText meterModelIT;
    private RichInputText smartMeterIT;
    private RichInputText meterTypeIT;
    private RichInputText msPayStationIDIT;
    private RichInputText msNumberIT;

    private RichInputText cnnIDIT;


    private RichTable activeMeterScheduleTable;
    private RichTable historicMeterScheduleTable;

    private RichCommandButton deleteSchedButton;
    private RichCommandButton deleteRateButton;
    private RichCommandToolbarButton addNewScheduleButton;
    private RichCommandToolbarButton addNewRateButton;

    private RichTable activeMeterRateTable;
    private RichTable historicMeterRateTable;
    private RichColumn meterOpScheduleEffToDateColumn;
    private RichColumn meterRateScheduleEffToDateColumn;
    private RichInputDate meterOpSchedEffToDateInputText;
    
    

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public MeterSpaceManagementBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static MeterSpaceManagementBean getInstance() {
        return (MeterSpaceManagementBean)getCurrentInstanceFor("meterSpaceManagementBean");
    }

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.HISTORIC_METER_SCHEDULE_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_RATE_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.HISTORIC_METER_RATE_LIST.getKey());
    }

    public void refreshTable() {

        if (getActiveMeterScheduleTable() != null) {

            clearPageFlowScopeCache();
            getActiveMeterScheduleTable().getSelectedRowKeys().clear();
            getActiveMeterScheduleTable().setSelectedRowKeys(null);
            resetAllMeterScheduleTableButtons();

            getActiveMeterScheduleTable().setValue(null);
            addPartialTarget(getActiveMeterScheduleTable());
        }

        if (getHistoricMeterScheduleTable() != null) {

            clearPageFlowScopeCache();
            getHistoricMeterScheduleTable().getSelectedRowKeys().clear();
            getHistoricMeterScheduleTable().setSelectedRowKeys(null);
            resetAllMeterScheduleTableButtons();

            getHistoricMeterScheduleTable().setValue(null);
            addPartialTarget(getHistoricMeterScheduleTable());
        }

        if (getActiveMeterRateTable() != null) {

            clearPageFlowScopeCache();
            getActiveMeterRateTable().getSelectedRowKeys().clear();
            getActiveMeterRateTable().setSelectedRowKeys(null);
            resetAllMeterRateTableButtons();

            getActiveMeterRateTable().setValue(null);
            addPartialTarget(getActiveMeterRateTable());
        }

        if (getHistoricMeterRateTable() != null) {

            clearPageFlowScopeCache();
            getHistoricMeterRateTable().getSelectedRowKeys().clear();
            getHistoricMeterRateTable().setSelectedRowKeys(null);
            resetAllMeterRateTableButtons();

            getHistoricMeterRateTable().setValue(null);
            addPartialTarget(getHistoricMeterRateTable());
        }

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public ParkingSpaceInventoryDTO getCurrentParkingSpaceInventoryDTO() {

        ParkingSpaceInventoryDTO DTO =
            (ParkingSpaceInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey());

        if (DTO == null) {
            BlockfaceDO blockfaceDO =
                (BlockfaceDO)getPageFlowScopeValue(PageFlowScopeKey.BLOCKFACE_DO.getKey());
            String longitude =
                (String)getPageFlowScopeValue(PageFlowScopeKey.LONGITUDE.getKey());
            String latitude =
                (String)getPageFlowScopeValue(PageFlowScopeKey.LATITUDE.getKey());

            DTO =
DMLOperationsProvider.INSTANCE.getNewParkingSpaceInventoryDTO(blockfaceDO,
                                                              longitude,
                                                              latitude);

            setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                  DTO);
        }

        return DTO;

    }

    public List<MeterOPScheduleDTO> getActiveMeterSchedules() {

        List<MeterOPScheduleDTO> meterScheduleDTOs =
            (List<MeterOPScheduleDTO>)getPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey());

        if (meterScheduleDTOs == null) {
            meterScheduleDTOs =
                    MeterOPScheduleProvider.INSTANCE.getActiveMeterOPScheduleDTOs(getCurrentParkingSpaceInventoryDTO().getParkingSpaceID());
            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                                  meterScheduleDTOs);
        }

        return meterScheduleDTOs;
    }

    public List<MeterOPScheduleDTO> getHistoricMeterSchedules() {

        List<MeterOPScheduleDTO> meterScheduleDTOs =
            (List<MeterOPScheduleDTO>)getPageFlowScopeValue(PageFlowScopeKey.HISTORIC_METER_SCHEDULE_LIST.getKey());

        if (meterScheduleDTOs == null) {
            meterScheduleDTOs =
                    MeterOPScheduleProvider.INSTANCE.getHistoricMeterOPScheduleDTOs(getCurrentParkingSpaceInventoryDTO().getParkingSpaceID());
            setPageFlowScopeValue(PageFlowScopeKey.HISTORIC_METER_SCHEDULE_LIST.getKey(),
                                  meterScheduleDTOs);
        }

        return meterScheduleDTOs;
    }

    public List<MeterRateScheduleDTO> getActiveMeterRates() {

        List<MeterRateScheduleDTO> meterRateScheduleDTOs =
            (List<MeterRateScheduleDTO>)getPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_RATE_LIST.getKey());

        if (meterRateScheduleDTOs == null) {
            meterRateScheduleDTOs =
                    MeterRateScheduleProvider.INSTANCE.getActiveMeterRateScheduleDTOs(getCurrentParkingSpaceInventoryDTO().getParkingSpaceID());
            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_RATE_LIST.getKey(),
                                  meterRateScheduleDTOs);
        }

        return meterRateScheduleDTOs;
    }

    public List<MeterRateScheduleDTO> getHistoricMeterRates() {

        List<MeterRateScheduleDTO> meterRateScheduleDTOs =
            (List<MeterRateScheduleDTO>)getPageFlowScopeValue(PageFlowScopeKey.HISTORIC_METER_RATE_LIST.getKey());

        if (meterRateScheduleDTOs == null) {
            meterRateScheduleDTOs =
                    MeterRateScheduleProvider.INSTANCE.getHistoricMeterRateScheduleDTOs(getCurrentParkingSpaceInventoryDTO().getParkingSpaceID());
            setPageFlowScopeValue(PageFlowScopeKey.HISTORIC_METER_RATE_LIST.getKey(),
                                  meterRateScheduleDTOs);
        }

        return meterRateScheduleDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALIDATORS

    public void postIDValidator(FacesContext facesContext,
                                UIComponent uiComponent, Object object) {

        String postIDRegex = RegularExpression.POST_ID_REGEX.getRegEx();
        if (getCurrentPageMode().isAddMode()) {
            postIDRegex = RegularExpression.POST_ID_REGEX.getRegEx();
        } else if (getCurrentPageMode().isEditMode()) {
            postIDRegex =
                    (getCurrentParkingSpaceInventoryDTO().getOnOffStreetType().contains("ON")) ?
                    "([0-9]{3})" : "([0-9]{5})";
        }

        String enteredPostID = (String)object;
        if (!enteredPostID.matches(postIDRegex)) {
            FacesMessage facesMessage =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Post ID Error",
                                 "Should follow a specific format");
            facesContext.addMessage(uiComponent.getClientId(facesContext),
                                    facesMessage);
            ((RichInputText)uiComponent).setValid(false);
        }
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

    public boolean isRenderDeleteParkingSpacePLM() {
        return (getCurrentPageMode().isEditMode());
    }

    public boolean isRenderMeterSchedPanel() {
        return (getCurrentPageMode().isEditMode() ||
                getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isRenderMeterRatePanel() {
        return (getCurrentPageMode().isEditMode() ||
                getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isRenderStreetTypePLM() {
        return (getCurrentPageMode().isAddMode());
    }

    public boolean isRenderPassiveInfo() {
        return (getCurrentPageMode().isEditMode() ||
                getCurrentPageMode().isReadOnlyMode());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL VISIBLE INFORMATION

    public boolean isVisibleDeleteParkingSpaceButton() {
        return (!getCurrentParkingSpaceInventoryDTO().isDeleted());
    }

    public boolean isVisibleUndeleteParkingSpaceButton() {
        return (getCurrentParkingSpaceInventoryDTO().isDeleted());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    public boolean isDisableOSPIDSOC() {
        boolean disabled =
            getCurrentParkingSpaceInventoryDTO().getOnOffStreetType().contains("ON");

        return disabled;
    }

    public boolean isDisableCapColorSOC() {
        boolean disabled = getCurrentParkingSpaceInventoryDTO().isDeleted();

        return disabled;
    }

    public boolean isDisableActiveMeterStatusSOC() {
        boolean disabled = getCurrentParkingSpaceInventoryDTO().isDeleted();

        return disabled;
    }

    public boolean isDisableMeterModelsButton() {
        boolean disabled =
            getCurrentParkingSpaceInventoryDTO().isUnmetered() ||
            getCurrentParkingSpaceInventoryDTO().isDeleted();

        return disabled;
    }

    public boolean isDisableMSPayStationIDIT() {
        boolean disabled =
            getCurrentParkingSpaceInventoryDTO().isUnmetered() ||
            getCurrentParkingSpaceInventoryDTO().isDeleted() ||
            !getCurrentParkingSpaceInventoryDTO().getMeterDetails().getMeterType().isMultiSpace();

        return disabled;
    }

    public boolean isDisableMSNumberIT() {
        boolean disabled =
            getCurrentParkingSpaceInventoryDTO().isUnmetered() ||
            getCurrentParkingSpaceInventoryDTO().isDeleted() ||
            !getCurrentParkingSpaceInventoryDTO().getMeterDetails().getMeterType().isMultiSpace();

        return disabled;
    }

    public boolean isDisableParityDigitPositionSOC() {
        boolean disabled =
            getCurrentParkingSpaceInventoryDTO().getOnOffStreetType().contains("OFF");

        return disabled;
    }

    public boolean isDisableCNNIDButton() {
        boolean disabled =
            getCurrentParkingSpaceInventoryDTO().getOnOffStreetType().contains("OFF") ||
            getCurrentParkingSpaceInventoryDTO().isDeleted();

        return disabled;
    }

    public boolean isDisableStreetNumberIT() {
        boolean disabled =
            getCurrentParkingSpaceInventoryDTO().getOnOffStreetType().contains("ON");

        return disabled;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListStreetType() {
        return ADFUIDisplayUtil.STREET_TYPE_LIST;
    }

    public List<SelectItem> getListOSPInventory() {
        return ADFUIDisplayUtil.getGeneralOSPInventoryDisplayList();
    }

    public List<SelectItem> getListJurisdiction() {
        return ADFUIDisplayUtil.getJurisdictionDisplayList();
    }

    public List<SelectItem> getListSensorStatus() {
        return ADFUIDisplayUtil.getSensorFlagDisplayList();
    }

    public List<SelectItem> getListCapColorSOC() {
        return ADFUIDisplayUtil.getCapColorDisplayList();
    }

    public List<SelectItem> getListActiveMeterStatus() {
        return ADFUIDisplayUtil.getActiveMeterFlagDisplayList();
    }

    public List<SelectItem> getListReasonCode() {
        return ADFUIDisplayUtil.getReasonCodeDisplayList();
    }

    public List<SelectItem> getListParityDigitPosition() {
        return ADFUIDisplayUtil.PARITY_DIGIT_POSITION_LIST;
    }

    public List<SelectItem> getListColorRuleAppliedSOC() {
        return ADFUIDisplayUtil.getColorRuleAppliedDisplayList();
    }

    public List<SelectItem> getListFromTime() {
        return TimeUI.FROM_TIME_LIST;
    }

    public List<SelectItem> getListToTime() {
        return TimeUI.TO_TIME_LIST;
    }

    public List<SelectItem> getListWeekDaysApplied() {
        return DayUI.DAYS_APPLIED_LIST;
    }

    // 20111207-01 change logic to refer to Allowed Values list of values
    public List<SelectItem> getListTimeLimit() {
        return ADFUIDisplayUtil.getTimeLimitDisplayList();
    }

    // 20111208-01 comment out - replaced by new logic
//    public List<SelectItem> getListTimeLimit() {
//        return ADFUIDisplayUtil.TIME_LIMIT_LIST;
//    }

    public List<SelectItem> getListPrePaymentTime() {
        return ADFUIDisplayUtil.getPrepaymentTimeList();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALUE CHANGE HANDLERS

    public void streetTypeValueChangeHandler(ValueChangeEvent event) {

        String ospID = "0";
        String cnnID = "0";
        int parityDigitPosition = 2;

        if (((String)event.getNewValue()).contains("ON")) {
            ospID = "0";
            cnnID = "0";
            parityDigitPosition = 2;
        } else {
            ospID =
                    DataRepositoryUtil.getOSPInventoryDODefaultValue().getOSPID();
            cnnID =
                    DataRepositoryUtil.getOSPInventoryDODefaultValue().getCNNID();
            parityDigitPosition = 0;
        }

        getCurrentParkingSpaceInventoryDTO().setOSPID(ospID);
        getCurrentParkingSpaceInventoryDTO().setCNNID(cnnID);
        getCurrentParkingSpaceInventoryDTO().setParityDigitPosition(parityDigitPosition);

        addPartialTarget(getCnnIDIT());
    }

    public void ospIDValueChangeHandler(ValueChangeEvent event) {

        String ospID = (String)event.getNewValue();

        getCurrentParkingSpaceInventoryDTO().setCNNID(DataRepositoryUtil.getCNNIDForOSPID(ospID));

        addPartialTarget(getCnnIDIT());
    }

    public void activeMeterStatusValueChangeHandler(ValueChangeEvent event) {

        String oldValue = (String)event.getOldValue();
        String newValue = (String)event.getNewValue();

        // If moving from Unmetered to Anything, then set the values
        if (oldValue.contains("U")) {
            getCurrentParkingSpaceInventoryDTO().setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDODefaultValue());

            // 20120311-01 Remove any highlighting when saving changes
            // use ADF Faces Context to use Partial Triggers for Partial Page Refresh (PPR)
            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();

            meterOpScheduleEffToDateColumn.setInlineStyle("background-color:White;");
            meterRateScheduleEffToDateColumn.setInlineStyle("background-color:White;");
            // place highlights on the page
            adfFacesCtx.addPartialTarget(activeMeterScheduleTable);
            adfFacesCtx.addPartialTarget(activeMeterRateTable);
        }

        // If moving from Anything to Unmetered, then reset the values
        if (newValue.contains("U")) {
            getCurrentParkingSpaceInventoryDTO().setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDONULLValue());

            // 20120222-01 logic from here to end of method is new
            // use ADF Faces Context to use Partial Triggers for Partial Page Refresh (PPR)
            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();

            // get table row count for Meter Operating Schedule
            int meterOpSchedRowCount = activeMeterScheduleTable.getRowCount();
            // get table row count for Meter Rate Schedule
            int meterRateRowCount = activeMeterRateTable.getRowCount();

            // initialize the warning message
            String warningMessage = "";
            StringBuilder sbWarningMessage = new StringBuilder("<html><body>");
            
            // determine the content of the message 
            // based upon existance of meter operation and rate schedules
            if ((meterOpSchedRowCount > 0) && (meterRateRowCount > 0) ){
                // both multiple operation and multiple rate schedules are assigned
                sbWarningMessage.append("Active Op and Rate Schedules exist.<br />");
                sbWarningMessage.append("Do you want to end date the schedules?<br />");
                meterOpScheduleEffToDateColumn.setInlineStyle("background-color:Yellow;");
                meterRateScheduleEffToDateColumn.setInlineStyle("background-color:Yellow;");
                // place highlights on the page
                adfFacesCtx.addPartialTarget(activeMeterScheduleTable);
                adfFacesCtx.addPartialTarget(activeMeterRateTable);
            } else if(meterOpSchedRowCount == 1) {
                // only one operation schedule assigned
                sbWarningMessage.append("Active Op Schedule exist.<br />");
                sbWarningMessage.append("Do you want to end date the schedule?<br />");
                meterOpScheduleEffToDateColumn.setInlineStyle("background-color:Yellow;");
                // place highlights on the page
                adfFacesCtx.addPartialTarget(activeMeterScheduleTable);
            } else if (meterRateRowCount == 1) {
                // only one rate schedule assigned
                sbWarningMessage.append("Active Rate Schedule exist.<br />");
                sbWarningMessage.append("Do you want to end date the schedule?<br />");
                meterRateScheduleEffToDateColumn.setInlineStyle("background-color:Yellow;");
                // place highlights on the page
                adfFacesCtx.addPartialTarget(activeMeterRateTable);
            } else if (meterOpSchedRowCount > 1) {
                // only operation schedules assigned (but more than one)
                sbWarningMessage.append("Active Op Schedules exist.<br />");
                sbWarningMessage.append("Do you want to end date the schedules?<br />");
                meterOpScheduleEffToDateColumn.setInlineStyle("background-color:Yellow;");
                // place highlights on the page
                adfFacesCtx.addPartialTarget(activeMeterScheduleTable);
            } else if (meterRateRowCount > 1) {
                // only rate schedules assigned (but more than one)
                sbWarningMessage.append("Active Rate Schedules exist.<br />");
                sbWarningMessage.append("Do you want to end date the schedules?<br />");
                meterRateScheduleEffToDateColumn.setInlineStyle("background-color:Yellow;");
                // place highlights on the page
                adfFacesCtx.addPartialTarget(activeMeterRateTable);
            }

            // complete the building of the warning message
            sbWarningMessage.append("<SPAN style=\"BACKGROUND-COLOR: #ffff00\">(see yellow highlight below)</SPAN><br />");
            sbWarningMessage.append("</body></html>");
            warningMessage = sbWarningMessage.toString();

            // add the message to the display
            FacesContext fctx = FacesContext.getCurrentInstance();
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Summary", warningMessage);
            String clientId = meterOpScheduleEffToDateColumn.getClientId(fctx);
            fctx.addMessage(clientId, fmsg);
//          String id = meterOpScheduleEffToDateColumn.getId();
//          fctx.addMessage(meterOpScheduleEffToDateColumn.getClientId(fctx), fmsg);
        }

        updateAllMeterDetails();
        addPartialTarget(getCnnIDIT());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void addButtonHandler(ActionEvent event) {
        setAddedTWOSchedule(false);

        String ID = event.getComponent().getId();

        if (ID.contains("Sched")) {
            MeterOPScheduleType meterOPScheduleType = MeterOPScheduleType.OP;
            if (ID.contains("ALT")) {
                meterOPScheduleType = MeterOPScheduleType.ALT;
            } else if (ID.contains("TOW")) {
                meterOPScheduleType = MeterOPScheduleType.TOW;
                setAddedTWOSchedule(true);
            } else {
                meterOPScheduleType = MeterOPScheduleType.OP;
            }

            setPageFlowScopeValue(PageFlowScopeKey.METER_SCHEDULE_TEMPLATE_TYPE.getKey(),
                                  meterOPScheduleType);

            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getActiveMeterScheduleTable().getValue();
            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                                  meterSchedules);

            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.MeterScheduleTemplatePage.name());

        } else if (ID.contains("Rate")) {
            MeterRateScheduleType meterRateScheduleType =
                MeterRateScheduleType.BASE;
            if (ID.contains("Hourly")) {
                meterRateScheduleType = MeterRateScheduleType.HOURLY;
            } else if (ID.contains("Special")) {
                meterRateScheduleType = MeterRateScheduleType.SPECIAL;
            } else {
                meterRateScheduleType = MeterRateScheduleType.BASE;
            }

            List<MeterRateScheduleDTO> meterRateScheduleDTOs =
                (List<MeterRateScheduleDTO>)getActiveMeterRateTable().getValue();

            MeterRateScheduleDTO meterRateScheduleDTO =
                DMLOperationsProvider.INSTANCE.getNewMeterRateScheduleDTO(meterRateScheduleType,
                                                                          getCurrentParkingSpaceInventoryDTO().getParkingSpaceID(),
                                                                          getNextPriorityInt(meterRateScheduleDTOs,
                                                                                             meterRateScheduleType),
                                                                          SQLDateUtil.getTodaysDate(),
                                                                          getCurrentParkingSpaceInventoryDTO().getBlockfaceID());

            meterRateScheduleDTOs.add(0, meterRateScheduleDTO);

            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_RATE_LIST.getKey(),
                                  meterRateScheduleDTOs);

            getActiveMeterRateTable().getSelectedRowKeys().clear();
            resetAllMeterRateTableButtons();
            getActiveMeterRateTable().setValue(null);
            // getMeterRateTable().setValue(meterRateScheduleDTOs);

            addPartialTarget(getActiveMeterRateTable());

        }

    }

    public void editButtonHandler(ActionEvent event) {
        //
        // Reuse as various buttons
        //

        String ID = event.getComponent().getId();

        // Preserve the current table data
        preserveTableData();

        if (ID.equalsIgnoreCase("meterModelsButton")) {
            // 20120522-02
            // save the action to view scope memory - 20120522-02
            setViewScopeValue(ViewScopeKey.ACTION.getKey(),"ChangeMeterModel");

            // get the Meter Type (Multi Space or Single Space) - 20120522-02
            String meterTypeValue = meterTypeIT.getValue().toString();
            setViewScopeValue(ViewScopeKey.METER_TYPE.getKey(),meterTypeValue);
            
            // get the Multi Space Pay Station ID and Multi Space Number - 20120522-02
            String msPayStationIdValue = msPayStationIDIT.getValue().toString();
            int msNumberValue = (Integer)msNumberIT.getValue();
            setViewScopeValue(ViewScopeKey.MS_PAY_STATION_ID.getKey(),msPayStationIdValue);
            setViewScopeValue(ViewScopeKey.MS_NUMBER.getKey(), msNumberValue);
            
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.MeterModelsPage.name());
        } else if (ID.equalsIgnoreCase("cnnIDButton")) {
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.ChangeCNNIDPage.name());
        }
    }

    public void deleteButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("Sched")) {

            RichTable table = getActiveMeterScheduleTable();
            RowKeySet rowKeySet = table.getSelectedRowKeys();
            int selectedNumOfRows = rowKeySet.getSize();

            if (selectedNumOfRows > 0) {

                List<MeterOPScheduleDTO> tempList =
                    new ArrayList<MeterOPScheduleDTO>();

                for (Object rowKey : rowKeySet) {
                    table.setRowKey(rowKey);

                    MeterOPScheduleDTO dto =
                        (MeterOPScheduleDTO)table.getRowData();

                    if (dto != null) {
                        if (dto.isNewRecord()) {
                            tempList.add(dto);
                        } else {

                            java.sql.Date newToDate =
                                EffectiveDateCalculator.getEffectiveToDateWhenDeleting(dto.getEffectiveFromDate(),
                                                                                       dto.getEffectiveToDate());

                            dto.setEffectiveToDate(newToDate);

                        }
                    }
                }

                List<MeterOPScheduleDTO> meterSchedules =
                    (List<MeterOPScheduleDTO>)table.getValue();

                meterSchedules.removeAll(tempList);

                setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                                      meterSchedules);

                table.getSelectedRowKeys().clear();
                resetAllMeterScheduleTableButtons();
                table.setValue(null);
                addPartialTarget(table);
            }

        } else if (ID.contains("Rate")) {

            RichTable table = getActiveMeterRateTable();
            RowKeySet rowKeySet = table.getSelectedRowKeys();
            int selectedNumOfRows = rowKeySet.getSize();

            if (selectedNumOfRows > 0) {

                List<MeterRateScheduleDTO> tempList =
                    new ArrayList<MeterRateScheduleDTO>();

                for (Object rowKey : rowKeySet) {
                    table.setRowKey(rowKey);

                    MeterRateScheduleDTO dto =
                        (MeterRateScheduleDTO)table.getRowData();

                    if (dto != null) {
                        if (dto.isNewRecord()) {
                            tempList.add(dto);
                        } else {

                            java.sql.Date newToDate =
                                EffectiveDateCalculator.getEffectiveToDateWhenDeleting(dto.getEffectiveFromDate(),
                                                                                       dto.getEffectiveToDate());

                            dto.setEffectiveToDate(newToDate);

                        }
                    }
                }

                List<MeterRateScheduleDTO> meterRates =
                    (List<MeterRateScheduleDTO>)table.getValue();

                meterRates.removeAll(tempList);

                setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_RATE_LIST.getKey(),
                                      meterRates);

                table.getSelectedRowKeys().clear();
                resetAllMeterRateTableButtons();
                table.setValue(null);
                addPartialTarget(table);
            }

        }
    }

    /**
     *  20120501-01 completely revised
     *  
     *  This controls which buttons are activated above the Meter Schedule Table and Meter Rate Tables
     *  
     * @param event
     */
    public void tableRowSelectionHandler(SelectionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("MSTable")) {
            // automatically reset the Meter Rate Table buttons
            resetAllMeterRateTableButtons();


            RichTable table = getActiveMeterScheduleTable();
            int selectedNumOfRows = table.getSelectedRowKeys().getSize();

            // When user selects a row....
            // Should have enabled Add New Schedule Button and End Schedule Button
            // BUT no buttons are active if the Edit Existing Record is checked
            if (selectedNumOfRows > 0) {  // a row was selected
                int rowIndexOfSelectionEvent = table.getRowIndex();
                // if any table rows has the Edit Existing Record checked... disable buttons
                if (isMeterScheduleTableEditExistingRecordSelected(rowIndexOfSelectionEvent) == true){
                    // 20120501-01 added disableAllMeterScheduleTableButtons()
                    // disable the Add New Schedule button
                    // disable the End Schedule button
                    disableAllMeterScheduleTableButtons();
                } else { // The user has selected the table row AND NO Edit Existing Records are checked
                    // therefore enable the buttons
                    enableAllMeterScheduleTableButtons();  // enables Add New Schedule and End Schedule buttons
                }
            } else {
                // enable the Add New Schedule button
                resetAllMeterScheduleTableButtons();
            }

        } else if (ID.contains("MRTable")) {
            // Automatically reset the Meter Schedule Buttons
            resetAllMeterScheduleTableButtons();
            addPartialTarget(activeMeterScheduleTable);
            
            RichTable table = getActiveMeterRateTable();

            int selectedNumOfRows = table.getSelectedRowKeys().getSize();

            // When user selects a row....
            // Should have enabled Add New Rate Button and End Date Rate Button
            // BUT no buttons are active if the Edit Existing Record is checked
            if (selectedNumOfRows > 0) { // a row was selected
                int rowIndexOfSelectionEvent = table.getRowIndex();
                // if any table rows has the Edit Existing Record checked... disable buttons
                if (isMeterRateTableEditExistingRecordSelected(rowIndexOfSelectionEvent) == true){
                    // 20120501-01 added disableAllMeterScheduleTableButtons()
                    // disable the Add New Rate button
                    // disable the End Date Rate button
                    disableAllMeterRateTableButtons();
                } else { // The user has selected the table row AND NO Edit Existing Records are checked
                    // therefore enable the buttons
                    enableAllMeterRateTableButtons();  // enables Add New Rate and End Date Rate buttons
                }
            } else {
                // enable the Add New Rate button
                resetAllMeterRateTableButtons();
            }

        } // if (ID.contains("MSTable"))

    } // tableRowSelectionHandler

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Blockface should exist
     *    2. Street Type
     *       ---ON
     *          ---Combination of Blockface ID and Post ID should work
     *       ---OFF
     *          ---OSP ID should NOT be zero
     *    3. Post ID should NOT exist
     *    4. Meter Type
     *       ---MS
     *          ---Combination of Post ID and MS Pay Station ID should work
     *
     * Edit Mode Steps:
     * ===============
     *    1. Check for Meter Schedule validations
     *    2. Check for Meter Rate validations
     *
     * When all is valid, perform the following
     *
     * Common Steps:
     * ============
     *    1. Copy attributes of Blockface into Parking Space
     *    2. Retrieve OLD_RATE_AREA
     *    3. Retrieve PCO_BEAT
     *    4. Street Type
     *       ---ON
     *          ---Street number should be generated from Post ID
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
            getCurrentParkingSpaceInventoryDTO();

        boolean checkPostIDUniqueness = false;

        String originalBlockfaceID = null;

        if (currentPageMode.isAddMode()) {
            checkPostIDUniqueness = true;
            originalBlockfaceID =
                    ((BlockfaceDO)getPageFlowScopeValue(PageFlowScopeKey.BLOCKFACE_DO.getKey())).getBlockfaceID();

        } else if (currentPageMode.isEditMode()) {
            ParkingSpaceInventoryDTO originalParkingSpaceInventoryDTO =
                ParkingSpaceInventoryProvider.INSTANCE.checkForParkingSpace(parkingSpaceInventoryDTO.getParkingSpaceID()).getDTO();

            checkPostIDUniqueness =
                    !StringUtil.areEqual(originalParkingSpaceInventoryDTO.getPostID(),
                                         parkingSpaceInventoryDTO.getPostID());

            originalBlockfaceID =
                    originalParkingSpaceInventoryDTO.getBlockfaceID();
        }

        boolean checkBlockfaceExistance =
            !StringUtil.areEqual(originalBlockfaceID,
                                 parkingSpaceInventoryDTO.getBlockfaceID());

        //
        //        checkPostIDUniqueness =
        //            (currentPageMode.isAddMode()) || (currentPageMode.isEditMode() &&
        //                                              !StringUtil.areEqual(originalParkingSpaceInventoryDTO.getPostID(),
        //                                                                   parkingSpaceInventoryDTO.getPostID()));

        adfLogger.log(adfLogger.TRACE, "Check for Post ID = " + checkPostIDUniqueness);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid && checkBlockfaceExistance) {
            BlockfaceDOStatus blockfaceStatus =
                BlockfacesProvider.INSTANCE.checkForBlockface(parkingSpaceInventoryDTO.getBlockfaceID());

            if (blockfaceStatus.existsDO()) {
                parkingSpaceInventoryDTO.setBlockfaceDO(blockfaceStatus.getDO());

            } else {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_exists_blockface));
            }
        }

        adfLogger.log(adfLogger.TRACE,"After Blockface check = " + allValid);

        if (allValid) {

            String streetType = parkingSpaceInventoryDTO.getOnOffStreetType();
            if (streetType.contains("ON")) {

                if (!CommonUtils.willPostIDAndBlockfaceIDWork(parkingSpaceInventoryDTO.getPostID(),
                                                              parkingSpaceInventoryDTO.getBlockfaceID(),
                                                              parkingSpaceInventoryDTO.getParityDigitPosition())) {
                    allValid = false;
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_work_postid_and_blockface,
                                                                              parkingSpaceInventoryDTO.getParityDigitPosition()));
                }

            } else if (streetType.contains("OFF")) {

                if (StringUtil.isBlank(parkingSpaceInventoryDTO.getOSPID()) ||
                    parkingSpaceInventoryDTO.getOSPID().equalsIgnoreCase("0")) {
                    allValid = false;
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_off_street_needs_osp_id));
                }

            }

        }

        adfLogger.log(adfLogger.TRACE,"After ON-OFF Street = " + allValid);

        if (allValid) {

            String cnnID = parkingSpaceInventoryDTO.getCNNID();

            if (StringUtil.isBlank(cnnID) || StringUtil.areEqual(cnnID, "0")) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_cnn_id));
            }
        }

        adfLogger.log(adfLogger.TRACE,"After CNN ID = " + allValid);

        if (allValid && checkPostIDUniqueness) {
            ParkingSpaceInventoryDTOStatus parkingSpaceStatus =
                ParkingSpaceInventoryProvider.INSTANCE.checkForPostID(parkingSpaceInventoryDTO.getPostID());

            if (parkingSpaceStatus.existsDTO()) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exists_already_post_id));
            }
        }

        adfLogger.log(adfLogger.TRACE,"After Post ID check = " + allValid);

        if (allValid) {

            if (parkingSpaceInventoryDTO.getMeterDetails().getMeterType().isMultiSpace()) {

                if (!CommonUtils.willPostIDAndPayStationIDWork(parkingSpaceInventoryDTO.getPostID(),
                                                               parkingSpaceInventoryDTO.getMSPayStationID(),
                                                               parkingSpaceInventoryDTO.getOnOffStreetType())) {
                    allValid = false;
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_not_work_postid_and_mspaystationid));
                }

            }
        }

        adfLogger.log(adfLogger.TRACE,"After Meter Type check = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            adfLogger.log(adfLogger.TRACE,"Under edit mode, test for Active Meter Schedules as well");
            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getActiveMeterScheduleTable().getValue();

            allValid =
                    areValidMeterSchedules(meterSchedules, parkingSpaceInventoryDTO.getCapColor(),
                                           "Active");
        }

        adfLogger.log(adfLogger.TRACE,"After Active Meter OP Schedule = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            adfLogger.log(adfLogger.TRACE,"Under edit mode, test for Historic Meter Schedules as well");
            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getHistoricMeterScheduleTable().getValue();

            allValid =
                    areValidMeterSchedules(meterSchedules, parkingSpaceInventoryDTO.getCapColor(),
                                           "Historic");
        }

        adfLogger.log(adfLogger.TRACE,"After Historic Meter OP Schedule = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            adfLogger.log(adfLogger.TRACE,"Under edit mode, test for Active Meter Rates as well");
            List<MeterRateScheduleDTO> meterRates =
                (List<MeterRateScheduleDTO>)getActiveMeterRateTable().getValue();

            allValid = areValidMeterRates(meterRates, "Active");
        }

        adfLogger.log(adfLogger.TRACE,"After Active Meter Rate Schedule = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            adfLogger.log(adfLogger.TRACE,"Under edit mode, test for Historic Meter Rates as well");
            List<MeterRateScheduleDTO> meterRates =
                (List<MeterRateScheduleDTO>)getHistoricMeterRateTable().getValue();

            allValid = areValidMeterRates(meterRates, "Historic");
        }

        adfLogger.log(adfLogger.TRACE,"After Historic Meter Rate Schedule = " + allValid);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {

            String oldRateArea =
                DMLOperationsProvider.INSTANCE.getOldRateArea(parkingSpaceInventoryDTO);
            parkingSpaceInventoryDTO.setOldRateArea(oldRateArea);

            String pcoBeat =
                DMLOperationsProvider.INSTANCE.getPCOBeat(parkingSpaceInventoryDTO);
            parkingSpaceInventoryDTO.setPCOBeat(pcoBeat);

            String streetType = parkingSpaceInventoryDTO.getOnOffStreetType();
            if (streetType.contains("ON")) {
                parkingSpaceInventoryDTO.setStreetNum(CommonUtils.generateStreetNumFromPostID(parkingSpaceInventoryDTO.getPostID()));
            }
        }

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
            adfLogger.log(adfLogger.TRACE,"All entries are Valid. Proceed");

            if (currentPageMode.isAddMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ADD Mode
                adfLogger.log(adfLogger.TRACE,"ADD Mode");

                ParkingSpaceInventoryDTO currentDTO =
                    getCurrentParkingSpaceInventoryDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addParkingSpace(currentDTO);

                if (operationStatus.isSuccess()) {
                    ParkingSpaceInventoryDTOStatus parkingSpaceStatus =
                        ParkingSpaceInventoryProvider.INSTANCE.checkForPostID(currentDTO.getPostID());

                    if (parkingSpaceStatus.existsDTO()) {
                        adfLogger.log(adfLogger.TRACE,"ADD operation was successful");
                        setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_create));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                        clearPageFlowScopeCache();
                        setCurrentPageMode(NavigationMode.EDIT);
                        setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                              parkingSpaceStatus.getDTO());

                    } else {
                        adfLogger.log(adfLogger.TRACE,"ADD operation failed");
                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_parking_space_failure));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    }
                } else {
                    adfLogger.log(adfLogger.TRACE,"ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_parking_space_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }

            } else if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                adfLogger.log(adfLogger.TRACE,"EDIT Mode");

                ParkingSpaceInventoryDTO currentDTO =
                    getCurrentParkingSpaceInventoryDTO();

                List<MeterOPScheduleDTO> meterSchedules =
                    (List<MeterOPScheduleDTO>)getActiveMeterScheduleTable().getValue();
                List<MeterOPScheduleDTO> historicMeterSchedules =
                    (List<MeterOPScheduleDTO>)getHistoricMeterScheduleTable().getValue();
                meterSchedules.addAll(historicMeterSchedules);

                List<MeterRateScheduleDTO> meterRates =
                    (List<MeterRateScheduleDTO>)getActiveMeterRateTable().getValue();
                List<MeterRateScheduleDTO> historicMeterRates =
                    (List<MeterRateScheduleDTO>)getHistoricMeterRateTable().getValue();
                meterRates.addAll(historicMeterRates);

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.editParkingSpace(currentDTO,
                                                                    meterSchedules,
                                                                    meterRates);

                if (operationStatus == null) {
                    adfLogger.log(adfLogger.TRACE,"There were no changes. So nothing was saved");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_nothing_to_save));
                    setInlineMessageClass("");

                } else {
                    if (operationStatus.isSuccess()) {
                        adfLogger.log(adfLogger.TRACE,"EDIT operation was successful");
                        setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                        ParkingSpaceInventoryDTOStatus parkingSpaceStatus =
                            ParkingSpaceInventoryProvider.INSTANCE.checkForParkingSpace(currentDTO.getParkingSpaceID());

                        setCurrentPageMode(NavigationMode.EDIT);
                        setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                              parkingSpaceStatus.getDTO());

                        clearPageFlowScopeCache();
                        getActiveMeterScheduleTable().setValue(null);
                        getHistoricMeterScheduleTable().setValue(null);
                        getActiveMeterRateTable().setValue(null);
                        getHistoricMeterRateTable().setValue(null);

                        // 20120311-01 Remove any highlighting when saving changes
                        // use ADF Faces Context to use Partial Triggers for Partial Page Refresh (PPR)
                        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();

                        meterOpScheduleEffToDateColumn.setInlineStyle("background-color:White;");
                        meterRateScheduleEffToDateColumn.setInlineStyle("background-color:White;");
                        // place highlights on the page
                        adfFacesCtx.addPartialTarget(activeMeterScheduleTable);
                        adfFacesCtx.addPartialTarget(activeMeterRateTable);


                    } else {
                        adfLogger.log(adfLogger.TRACE,"EDIT operation failed");
                        String errorMessage = "";

                        switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                        case UNIQUE_CONSTRAINT:
                            {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_unique_constraint);
                            }
                            break;

                        case DTO_INSERT:
                            {
                                String tableName =
                                    ((DTOInsertException)operationStatus.getException()).getTableName();

                                if (StringUtil.areEqual(tableName,
                                                        MeterOPScheduleDTO.getDatabaseTableName())) {
                                    errorMessage =
                                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_schedule_insert);
                                } else if (StringUtil.areEqual(tableName,
                                                               MeterRateScheduleDTO.getDatabaseTableName())) {
                                    errorMessage =
                                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_rate_insert);
                                } else {
                                    errorMessage =
                                            operationStatus.getException().getMessage();
                                }
                            }
                            break;

                        case DTO_UPDATE:
                            {
                                String tableName =
                                    ((DTOUpdateException)operationStatus.getException()).getTableName();

                                if (StringUtil.areEqual(tableName,
                                                        MeterOPScheduleDTO.getDatabaseTableName())) {
                                    errorMessage =
                                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_schedule_update);
                                } else if (StringUtil.areEqual(tableName,
                                                               MeterRateScheduleDTO.getDatabaseTableName())) {
                                    errorMessage =
                                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_rate_update);
                                } else if (StringUtil.areEqual(tableName,
                                                               ParkingSpaceInventoryDTO.getDatabaseTableName())) {
                                    errorMessage =
                                            TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_inventory_update);
                                } else {
                                    errorMessage =
                                            operationStatus.getException().getMessage();
                                }
                            }
                            break;

                        default:
                            {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exception_save_failure);
                            }
                            break;

                        }

                        setInlineMessageText(errorMessage);
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);

                    }
                }

                refreshTablesAfterClickingSave();

            }
        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);

            if (currentPageMode.isEditMode()) {
                refreshTablesAfterClickingSave();
            }
        }
    }

    public void cancelButtonHandler(ActionEvent event) {
        // Do nothing
    }

    /**
     * Deletes/Undeletes the Parking Space by setting the appropriate values
     *
     * Delete Parking Space:
     * ====================
     *    1. Set CAP_COLOR = "-"
     *    2. Set ACTIVE_METER_FLAG = "U"
     *    3. Update Meter Model as per "U"
     *    4. End-Date Meter Schedules
     *    5. End-Date Meter Rates
     *
     * Undelete Parking Space:
     * ======================
     *    1. Set CAP_COLOR = Default Valid Value
     *
     * @param event
     */
    public void deleteUndeleteParkingSpaceButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.equals("deleteParkingSpaceButton")) {
            getCurrentParkingSpaceInventoryDTO().setCapColor("-");
            getCurrentParkingSpaceInventoryDTO().setActiveMeterFlag("U");
            getCurrentParkingSpaceInventoryDTO().setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDONULLValue());

            endDateAllSchedules();
            endDateAllRates();

        } else if (ID.equals("undeleteParkingSpaceButton")) {
            getCurrentParkingSpaceInventoryDTO().setCapColor(AllowedValuesRetriever.getCapColorDefaultValue());

        }

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    /**
     * Validates the Meter Schedule Table and returns true if all entries are valid
     *
     * Validity Tests:
     * ==============
     *    1. All TO dates should occur after FROM dates
     *    2. All TO times should occur after FROM times
     *    3. Current OP Schedule cannot have a color different from Cap Color
     *    4. No two schedules should be exactly similar
     *
     * @param meterSchedules
     * @param capColor
     * @return
     */
    private boolean areValidMeterSchedules(List<MeterOPScheduleDTO> meterSchedules,
                                           String capColor,
                                           String uiTableName) {

        if (meterSchedules == null || meterSchedules.isEmpty()) {
            // Nothing to validate
            return true;
        }

        for (int i = 0; i < meterSchedules.size(); i++) {

            MeterOPScheduleDTO iDTO = meterSchedules.get(i);

            // For each row, To date should be after From date
            if (iDTO.getEffectiveToDate().before(iDTO.getEffectiveFromDate())) {
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_schedule_date_to_before_from,
                                                                          uiTableName,
                                                                          (i +
                                                                           1)));
                return false;
            }

            // For each row,
            //    ---TO_TIME can NOT be zero
            //    ---TO_TIME should be AFTER FROM_TIME
            //
            // Exception:
            //    ---If
            //         ---FROM_TIME = 0
            //         ---SCHED_TYPE = ALT
            //         ---ALT_ADDL_DESC = As Posted
            //       Then, TO_TIME should be zero
            int fromTime =
                Integer.parseInt(TimeDisplayUtil.extractFromTimeForUpdate(iDTO.getFromTime()));
            int toTime =
                Integer.parseInt(TimeDisplayUtil.extractAnyTimeForUpdate(iDTO.getToTime()));

            if (toTime == 0) {
                if (fromTime == 0 && iDTO.getScheduleType().isALT() &&
                    iDTO.isAlternateAddlDescFixedToSpecificValues()) {
                    // Valid
                    // Continue

                } else {
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_schedule_time_to_zero,
                                                                              uiTableName,
                                                                              (i +
                                                                               1)));
                    return false;

                }
            } else if (fromTime >= toTime) {
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_schedule_time_to_before_from,
                                                                          uiTableName,
                                                                          (i +
                                                                           1)));
                return false;

            }

            // For each row,
            //    ---If
            //         ---SCHED_TYPE = OP
            //         ---EFF_TO_DT after Today
            //       Then, COLOR_RULE_APPLIED should be same as CAP_COLOR
            if (iDTO.getScheduleType().isOP()) {
                if (iDTO.getEffectiveToDate().after(SQLDateUtil.getTodaysDate())) {
                    if (!StringUtil.areEqual(iDTO.getColorRuleApplied(),
                                             capColor)) {

                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_schedule_op_invalid_cap_color,
                                                                                  uiTableName,
                                                                                  (i +
                                                                                   1)));

                        return false;
                    }
                }
            }

            // No two rows should be similar
            for (int j = i + 1; j < meterSchedules.size(); j++) {
                MeterOPScheduleDTO jDTO = meterSchedules.get(j);

                if (iDTO.getScheduleType().equals(jDTO.getScheduleType()) &&
                    (iDTO.getSchedulePriority() ==
                     jDTO.getSchedulePriority()) &&
                    SQLDateUtil.areEqual(iDTO.getEffectiveFromDate(),
                                         jDTO.getEffectiveFromDate())) {

                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_schedule_invalid_unique_constraints,
                                                                              uiTableName,
                                                                              (i +
                                                                               1),
                                                                              (j +
                                                                               1)));
                    return false;
                }
            }

        }

        return true;
    }

    /**
     * Validates the Meter Rate Table and returns true if all entries are valid
     *
     * Validity Tests:
     * ==============
     *    1. All TO dates should occur after FROM dates
     *    2. All TO times should occur after FROM times
     *    3. No two schedules should be exactly similar
     *
     * @param meterRates
     * @return
     */
    private boolean areValidMeterRates(List<MeterRateScheduleDTO> meterRates,
                                       String uiTableName) {

        if (meterRates == null || meterRates.isEmpty()) {
            // Nothing to validate
            return true;
        }

        for (int i = 0; i < meterRates.size(); i++) {

            MeterRateScheduleDTO iDTO = meterRates.get(i);

            // For each row, To date should be after From date
            if (iDTO.getEffectiveToDate().before(iDTO.getEffectiveFromDate())) {
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_rate_date_to_before_from,
                                                                          uiTableName,
                                                                          (i +
                                                                           1)));
                return false;
            }

            // For each row,
            //    ---TO_TIME can NOT be zero
            //    ---TO_TIME should be AFTER FROM_TIME
            //
            // Exception:
            //    ---If
            //         ---FROM_TIME = 0
            //         ---RATE_TYPE = B
            //       Then, TO_TIME should be zero
            int fromTime =
                Integer.parseInt(TimeDisplayUtil.extractFromTimeForUpdate(iDTO.getFromTime()));
            int toTime =
                Integer.parseInt(TimeDisplayUtil.extractAnyTimeForUpdate(iDTO.getToTime()));

            if (toTime == 0) {
                if (fromTime == 0 &&
                    (iDTO.getRateType().isBase() || iDTO.getRateType().isSpecial())) {
                    // Valid
                    // Continue

                } else {
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_rate_time_to_zero,
                                                                              uiTableName,
                                                                              (i +
                                                                               1)));
                    return false;
                }
            } else if (fromTime >= toTime) {
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_rate_time_to_before_from,
                                                                          uiTableName,
                                                                          (i +
                                                                           1)));
                return false;
            }

            // No two rows should be similar
            for (int j = i + 1; j < meterRates.size(); j++) {
                MeterRateScheduleDTO jDTO = meterRates.get(j);

                if (iDTO.getRateType().equals(jDTO.getRateType()) &&
                    (iDTO.getSchedulePriority() ==
                     jDTO.getSchedulePriority()) &&
                    SQLDateUtil.areEqual(iDTO.getEffectiveFromDate(),
                                         jDTO.getEffectiveFromDate())) {

                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_rate_invalid_unique_constraints,
                                                                              uiTableName,
                                                                              (i +
                                                                               1),
                                                                              (j +
                                                                               1)));
                    return false;
                }
            }

        }

        return true;
    }

    private void endDateAllSchedules() {

        RichTable table = getActiveMeterScheduleTable();

        List<MeterOPScheduleDTO> currentMeterSchedules =
            (List<MeterOPScheduleDTO>)table.getValue();

        if (currentMeterSchedules != null &&
            !currentMeterSchedules.isEmpty()) {

            List<MeterOPScheduleDTO> finalList =
                new ArrayList<MeterOPScheduleDTO>();

            for (MeterOPScheduleDTO dto : currentMeterSchedules) {
                if (dto != null && !dto.isNewRecord()) {

                    java.sql.Date newToDate =
                        EffectiveDateCalculator.getEffectiveToDateWhenDeleting(dto.getEffectiveFromDate(),
                                                                               dto.getEffectiveToDate());

                    dto.setEffectiveToDate(newToDate);

                    finalList.add(dto);
                }
            }

            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                                  finalList);

            table.getSelectedRowKeys().clear();
            resetAllMeterScheduleTableButtons();
            table.setValue(null);
            addPartialTarget(table);

        }
    }

    private void endDateAllRates() {

        RichTable table = getActiveMeterRateTable();

        List<MeterRateScheduleDTO> currentMeterRates =
            (List<MeterRateScheduleDTO>)table.getValue();

        if (currentMeterRates != null && !currentMeterRates.isEmpty()) {

            List<MeterRateScheduleDTO> tempList =
                new ArrayList<MeterRateScheduleDTO>();

            for (MeterRateScheduleDTO dto : currentMeterRates) {
                if (dto != null) {
                    if (dto.isNewRecord()) {
                        tempList.add(dto);
                    } else {

                        java.sql.Date newToDate =
                            EffectiveDateCalculator.getEffectiveToDateWhenDeleting(dto.getEffectiveFromDate(),
                                                                                   dto.getEffectiveToDate());

                        dto.setEffectiveToDate(newToDate);
                    }
                }
            }

            currentMeterRates.removeAll(tempList);

            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_RATE_LIST.getKey(),
                                  currentMeterRates);

            table.getSelectedRowKeys().clear();
            resetAllMeterRateTableButtons();
            table.setValue(null);
            addPartialTarget(table);

        }
    }

    private int getNextPriorityInt(List<MeterRateScheduleDTO> meterRateScheduleDTOs,
                                   MeterRateScheduleType meterRateScheduleType) {

        int nextPriority = 0;

        if (meterRateScheduleDTOs != null &&
            !meterRateScheduleDTOs.isEmpty()) {
            for (MeterRateScheduleDTO DTO : meterRateScheduleDTOs) {
                if (DTO.getRateType().equals(meterRateScheduleType)) {
                    if (DTO.getSchedulePriority() > nextPriority) {
                        nextPriority = DTO.getSchedulePriority();
                    }
                }
            }
        }

        return (nextPriority + 1);
    }

    private void refreshTablesAfterClickingSave() {
        resetAllMeterScheduleTableButtons();
        resetAllMeterRateTableButtons();

        getActiveMeterScheduleTable().getSelectedRowKeys().clear();
        addPartialTarget(getActiveMeterScheduleTable());
        addPartialTarget(getHistoricMeterScheduleTable());

        getActiveMeterRateTable().getSelectedRowKeys().clear();
        addPartialTarget(getActiveMeterRateTable());
        addPartialTarget(getHistoricMeterRateTable());
    }

    private void preserveTableData() {
        // TODO: Figure out the best way

        List<MeterOPScheduleDTO> meterSchedules =
            (List<MeterOPScheduleDTO>)getActiveMeterScheduleTable().getValue();
        setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                              meterSchedules);

        List<MeterRateScheduleDTO> meterRates =
            (List<MeterRateScheduleDTO>)getActiveMeterRateTable().getValue();
        setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_RATE_LIST.getKey(),
                              meterRates);

    }

    /**
     * METER SCHEDULE TABLE BUTTON METHODS
     */

    /**
     *  20120501-01 changed
     *  
     *  This enables and disables buttons for the Meter Schedule table.
     *  If the Edit Existing Record is selected > no buttons are active.
     *  Else activate the Add Schedule button.
     *  
     */
    private void resetAllMeterScheduleTableButtons() {
        boolean overrideIsSet = false;

        // 20120501-01 removed --> updateAllMeterScheduleTableButtons(true);
        
        try {
            overrideIsSet =
                   (Boolean)getPageFlowScopeValue(PageFlowScopeKey.METER_SCHEDULE_EDIT_EXISTING_RECORDS_SELECTED.getKey());
        } catch (Exception e) {
            // do nothing
        }
        
        if (overrideIsSet == true){
            disableAddNewScheduleButton(true);   // disable this button
            disableDeleteSchedButton(true);      // disable this button
        } else {
            disableAddNewScheduleButton(false);  // enable this button
            disableDeleteSchedButton(true);      // disable this button
        }

        // make certain no rows are selected in the Meter Schedule Table
        activeMeterScheduleTable.getSelectedRowKeys().clear();
        addPartialTarget(activeMeterScheduleTable);
    }


    // 20120501-01 added
    private void disableAllMeterScheduleTableButtons() {
        disableAddNewScheduleButton(true);
        disableDeleteSchedButton(true);
    }

    // 20120501-01 added
    private void enableAllMeterScheduleTableButtons() {
        disableAddNewScheduleButton(false);
        disableDeleteSchedButton(false);
    }


    // 20120501-01 added
    // note: this is the button with label "End Schedule"
    private void disableDeleteSchedButton(boolean disabled) {
        // if disabled = true then button is disabled
        // else enable the button
        if (getDeleteSchedButton() != null) {
            getDeleteSchedButton().setDisabled(disabled);
            addPartialTarget(getDeleteSchedButton());
        }
    }

    // 20120501-01 added
    private void disableAddNewScheduleButton(boolean disabled) {
        // if disabled = true then button is disabled
        // else enable the button
        if (getAddNewScheduleButton() != null) {
            getAddNewScheduleButton().setDisabled(disabled);
            addPartialTarget(getAddNewScheduleButton());
        }
    }


    /**
     * METER RATE TABLE BUTTON METHODS
     */

     /**
      *  20120501-02 changed
      *  
      *  This enables and disables buttons for the Meter Rate table.
      *  If the Edit Existing Record is selected > no buttons are active.
      *  Else activate the Add Rate button.
      *  
      */
     private void resetAllMeterRateTableButtons() {
        boolean overrideIsSet = false;
        
        // 201205-02 removed ->  updateAllMeterRateTableButtons(true);
        try {
            overrideIsSet =
                    (Boolean)getPageFlowScopeValue(PageFlowScopeKey.METER_RATE_EDIT_EXISTING_RECORDS_SELECTED.getKey());
        } catch (Exception e) {
            // do nothing
        }
        
        if( overrideIsSet == true){
            disableAddNewRateButton(true);   // disable this button
            disableDeleteRateButton(true);   // disable this button
        } else {
            disableAddNewRateButton(false);  // enable this button
            disableDeleteRateButton(true);   // disable this button
        }

        // make certain no rows are selected in the Meter Rate table
        activeMeterRateTable.getSelectedRowKeys().clear();
        addPartialTarget(activeMeterRateTable);

     }

     // 20120501-02 added
    private void disableAllMeterRateTableButtons(){
        disableAddNewRateButton(true);
        disableDeleteRateButton(true);
    }

    // 20120501-02 added
    private void enableAllMeterRateTableButtons(){
       disableAddNewRateButton(false);
       disableDeleteRateButton(false);
    }

    // 20120501-02 added
    private void disableAddNewRateButton(boolean disabled){
        // if disabled = true then button is disabled
        // else enable the button
        if (getAddNewRateButton() != null) {
            getAddNewRateButton().setDisabled(disabled);
            addPartialTarget(getAddNewRateButton());
        }
    }

    // 20120501-02 added
    private void disableDeleteRateButton(boolean disabled){
        // if disabled = true then button is disabled
        // else enable the button
        if (getDeleteRateButton() != null) {
            getDeleteRateButton().setDisabled(disabled);
            addPartialTarget(getDeleteRateButton());
        }
    }

// 20120502 - Disabled
//    private void setAllMeterRateTableButtons() {
//        updateAllMeterRateTableButtons(false);
//    }


// 20120502 - Disabled
//    private void updateAllMeterRateTableButtons(boolean disable) {
//        // 20120501-02
//        if (getDeleteRateButton() != null) {
//            getDeleteRateButton().setDisabled(disable);
//            addPartialTarget(getDeleteRateButton());
//        }
//    }

    private void updateAllMeterDetails() {
        addPartialTarget(getMeterVendorIT());
        addPartialTarget(getMeterModelIT());
        addPartialTarget(getMeterTypeIT());
        addPartialTarget(getSmartMeterIT());
        addPartialTarget(getMsPayStationIDIT());
        addPartialTarget(getMsNumberIT());
    }



    /** 
     * 20120501-01 added 
     * 
     * This captures the change event on the CheckBox "Edit Existing Record"
     * In the Meter Schedule Table.
     * 
     * If the checkbox is selected   (checked)... then disable the End Schedule Button
     * If the checkbox is deselected (unchecked), then evaluate all rows in the table for the checked box
     */
    public void editExistingScheduleRecordValueChangeHandler(ValueChangeEvent valueChangeEvent) {
        boolean editExistingRecordIsSelected = (Boolean)valueChangeEvent.getNewValue();

        // identify the row that the change was made in
        UIComponent chkBoxComponent = valueChangeEvent.getComponent();
        UIComponent colComponent = chkBoxComponent.getParent();
        RichTable tableComponent = (RichTable)colComponent.getParent();

        int rowIndexOfChangeEvent = tableComponent.getRowIndex(); // good

        if (editExistingRecordIsSelected == false){
            // this valueChangeEvent did not set the checkbox (editExistingRecordIsSelected == true)
            // Therefore...
            // review the table for any other row having Edit Existing Record checkbox selected
            editExistingRecordIsSelected = isMeterScheduleTableEditExistingRecordSelected(rowIndexOfChangeEvent);

        }

        // set buttons according to findings
        if (editExistingRecordIsSelected == true){
            // One or more Existing Records CheckBoxs are checked
            disableAllMeterScheduleTableButtons(); // disables Add New Schedule and End Schedule buttons
            // save to memory the current settings
            setPageFlowScopeValue(PageFlowScopeKey.METER_SCHEDULE_EDIT_EXISTING_RECORDS_SELECTED.getKey(),true);
        } else {
            // no Existing Records CheckBoxs are checked
            enableAllMeterScheduleTableButtons();  // enables Add New Schedule and End Schedule buttons
        }

    } // editExistingScheduleRecordValueChangeHandler()


    /** 
     * 20120501-02 added 
     * 
     * This captures the change event on the CheckBox "Edit Existing Record"
     * In the Meter Rate Table.
     * 
     * If the checkbox is selected   (checked)... then disable the End Date Rate Button
     * If the checkbox is deselected (unchecked), then evaluate all rows in the table for the checked box
     */
    public void editExistingRateRecordValueChangeHandler(ValueChangeEvent valueChangeEvent) {
        boolean editExistingRecordIsSelected = (Boolean)valueChangeEvent.getNewValue();

        // identify the row that the change was made in
        UIComponent chkBoxComponent = valueChangeEvent.getComponent();
        UIComponent colComponent = chkBoxComponent.getParent();
        RichTable tableComponent = (RichTable)colComponent.getParent();

        int rowIndexOfChangeEvent = tableComponent.getRowIndex(); // good

        if (editExistingRecordIsSelected == false){
            // this valueChangeEvent did not set the checkbox (editExistingRecordIsSelected == true)
            // Therefore...
            // review the table for any other row having Edit Existing Record checkbox selected
            editExistingRecordIsSelected = isMeterRateTableEditExistingRecordSelected(rowIndexOfChangeEvent);
        }

        // set buttons according to findings
        if (editExistingRecordIsSelected == true){
            // One or more Existing Records CheckBoxs are checked
            disableAllMeterRateTableButtons(); // disables Add New Rate and End Date Rate buttons
            // save to memory the current settings
            setPageFlowScopeValue(PageFlowScopeKey.METER_RATE_EDIT_EXISTING_RECORDS_SELECTED.getKey(),true);
        } else {
            // no Existing Records CheckBoxs are checked
            enableAllMeterRateTableButtons();  // enables Add New Rate and End Date Rate buttons
        }

    } // editExistingRateRecordValueChangeHandler()




    /**
     * 20120501-01 added 
     * 
     * This passes through all the rows in the Meter Schedule Table 
     * except if one of the rows is selected.
     * 
     * If the "Edit Existing Record" is selected in any of the table rows
     * then this returns true
     * 
     * @param rowIndexOfChangeEvent
     * @return
     */
    public boolean isMeterScheduleTableEditExistingRecordSelected(int rowIndexOfChangeEvent){
        boolean overrideIsSet = false;
        MeterOPScheduleDTO rowDataForChangeEvent = null;

        if(rowIndexOfChangeEvent > -1){
            rowDataForChangeEvent = (MeterOPScheduleDTO)activeMeterScheduleTable.getRowData(rowIndexOfChangeEvent);
        }

        // get MeterSchedule Table and see if the Override is set
        int rowCount = activeMeterScheduleTable.getRowCount();
        for(int rowIndex=0; rowIndex < rowCount; rowIndex++){
            MeterOPScheduleDTO rowData = (MeterOPScheduleDTO)activeMeterScheduleTable.getRowData(rowIndex);
            if ( (rowDataForChangeEvent != null) && (rowData.equals(rowDataForChangeEvent) )) {
                // skip this row, the change has taken place here
                // we only want to review unchanged rows for the isOverride setting
            } else {
                if (rowData.isOverride() == true){
                    overrideIsSet = true;
                    break;
                }
            }
        }

        // save to memory the current settings
        setPageFlowScopeValue(PageFlowScopeKey.METER_SCHEDULE_EDIT_EXISTING_RECORDS_SELECTED.getKey(),overrideIsSet);

        return overrideIsSet;
    } // isMeterScheduleTableEditExistingRecordSelected()


    /**
     * 20120501-02 added 
     * 
     * This passes through all the rows in the Meter Rate Table 
     * except if one of the rows is selected.
     * 
     * If the "Edit Existing Record" is selected in any of the table rows
     * then this returns true
     * 
     * @param rowIndexOfChangeEvent
     * @return
     */
    public boolean isMeterRateTableEditExistingRecordSelected(int rowIndexOfChangeEvent){
        boolean overrideIsSet = false;
        MeterRateScheduleDTO rowDataForChangeEvent = null;

        if(rowIndexOfChangeEvent > -1){
            rowDataForChangeEvent = (MeterRateScheduleDTO)activeMeterRateTable.getRowData(rowIndexOfChangeEvent);
        }

        // get Meter Rate Table and see if the Override is set
        int rowCount = activeMeterRateTable.getRowCount();
        for(int rowIndex=0; rowIndex < rowCount; rowIndex++){
            MeterRateScheduleDTO rowData = (MeterRateScheduleDTO)activeMeterRateTable.getRowData(rowIndex);
            if ( (rowDataForChangeEvent != null) && (rowData.equals(rowDataForChangeEvent) )) {
                // skip this row, the change has taken place here
                // we only want to review unchanged rows for the isOverride setting
            } else {
                if (rowData.isOverride() == true){
                    overrideIsSet = true;
                    break;
                }
            }
        }

        // save to memory the current settings
        setPageFlowScopeValue(PageFlowScopeKey.METER_RATE_EDIT_EXISTING_RECORDS_SELECTED.getKey(),overrideIsSet);

        return overrideIsSet;
    } // isMeterRateTableEditExistingRecordSelected()




    private int getPostIDTypePosition(int max) {
        return (max - getPostIDPosition());
    }

    private int getPostIDPosition() {
        int position =
            getCurrentParkingSpaceInventoryDTO().getOnOffStreetType().contains("ON") ?
            6 : 4;

        return position;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public int getPostIDITMaximumLength() {
        if (getCurrentPageMode().isAddMode()) {
            return 9;
        }

        return getPostIDTypePosition(9);
    }

    public int getPostIDITColumns() {
        if (getCurrentPageMode().isAddMode()) {
            return 10;
        }

        return getPostIDTypePosition(10);
    }

    public String getPostIDITLabel() {
        if (getCurrentPageMode().isAddMode()) {
            return "";
        }

        int position = getPostIDPosition();

        return getCurrentParkingSpaceInventoryDTO().getPostID().substring(0,
                                                                          position);
    }

    public void setPostIDITValue(String postIDITValue) {

        if (getCurrentPageMode().isAddMode()) {

            getCurrentParkingSpaceInventoryDTO().setPostID(postIDITValue);

        } else if (getCurrentPageMode().isEditMode()) {

            int position = getPostIDPosition();

            String temp =
                getCurrentParkingSpaceInventoryDTO().getPostID().substring(0,
                                                                           position) +
                postIDITValue;

            getCurrentParkingSpaceInventoryDTO().setPostID(temp);
        }
    }

    public String getPostIDITValue() {
        if (getCurrentPageMode().isAddMode()) {
            return getCurrentParkingSpaceInventoryDTO().getPostID();
        }

        int position = getPostIDPosition();

        return getCurrentParkingSpaceInventoryDTO().getPostID().substring(position);
    }

    public String getBlockfaceIDITLabel() {
        return getCurrentParkingSpaceInventoryDTO().getBlockfaceID().substring(0,
                                                                               5);
    }

    public void setBlockfaceIDITValue(String blockfaceIDITValue) {
        String temp =
            getCurrentParkingSpaceInventoryDTO().getBlockfaceID().substring(0,
                                                                            5) +
            blockfaceIDITValue;
        getCurrentParkingSpaceInventoryDTO().setBlockfaceID(temp);
    }

    public String getBlockfaceIDITValue() {
        return getCurrentParkingSpaceInventoryDTO().getBlockfaceID().substring(5);
    }

    public String getRowSelectionValue() {
        return (getCurrentPageMode().isReadOnlyMode()) ? "none" : "multiple";
    }

    public java.sql.Date getMaximumEffectiveToDate() {
        return SQLDateUtil.getYesterdaysDate();
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
    // UI BINDINGS

    public void setStreetTypeSOR(RichSelectOneRadio streetTypeSOR) {
        this.streetTypeSOR = streetTypeSOR;
    }

    public RichSelectOneRadio getStreetTypeSOR() {
        return streetTypeSOR;
    }

    public void setMeterVendorIT(RichInputText meterVendorIT) {
        this.meterVendorIT = meterVendorIT;
    }

    public RichInputText getMeterVendorIT() {
        return meterVendorIT;
    }

    public void setMeterModelIT(RichInputText meterModelIT) {
        this.meterModelIT = meterModelIT;
    }

    public RichInputText getMeterModelIT() {
        return meterModelIT;
    }

    public void setSmartMeterIT(RichInputText smartMeterIT) {
        this.smartMeterIT = smartMeterIT;
    }

    public RichInputText getSmartMeterIT() {
        return smartMeterIT;
    }

    public void setMeterTypeIT(RichInputText meterTypeIT) {
        this.meterTypeIT = meterTypeIT;
    }

    public RichInputText getMeterTypeIT() {
        return meterTypeIT;
    }

    public void setMsPayStationIDIT(RichInputText msPayStationIDIT) {
        this.msPayStationIDIT = msPayStationIDIT;
    }

    public RichInputText getMsPayStationIDIT() {
        return msPayStationIDIT;
    }

    public void setMsNumberIT(RichInputText msNumberIT) {
        this.msNumberIT = msNumberIT;
    }

    public RichInputText getMsNumberIT() {
        return msNumberIT;
    }

    public void setCnnIDIT(RichInputText cnnIDIT) {
        this.cnnIDIT = cnnIDIT;
    }

    public RichInputText getCnnIDIT() {
        return cnnIDIT;
    }

    public void setDeleteSchedButton(RichCommandButton deleteSchedButton) {
        this.deleteSchedButton = deleteSchedButton;
    }

    public RichCommandButton getDeleteSchedButton() {
        return deleteSchedButton;
    }

    public void setActiveMeterScheduleTable(RichTable activeMeterScheduleTable) {
        this.activeMeterScheduleTable = activeMeterScheduleTable;
    }

    public RichTable getActiveMeterScheduleTable() {
        return activeMeterScheduleTable;
    }

    public void setHistoricMeterScheduleTable(RichTable historicMeterScheduleTable) {
        this.historicMeterScheduleTable = historicMeterScheduleTable;
    }

    public RichTable getHistoricMeterScheduleTable() {
        return historicMeterScheduleTable;
    }

    public void setDeleteRateButton(RichCommandButton deleteRateButton) {
        this.deleteRateButton = deleteRateButton;
    }

    public RichCommandButton getDeleteRateButton() {
        return deleteRateButton;
    }

    public void setActiveMeterRateTable(RichTable activeMeterRateTable) {
        this.activeMeterRateTable = activeMeterRateTable;
    }

    public RichTable getActiveMeterRateTable() {
        return activeMeterRateTable;
    }

    public void setHistoricMeterRateTable(RichTable historicMeterRateTable) {
        this.historicMeterRateTable = historicMeterRateTable;
    }

    public RichTable getHistoricMeterRateTable() {
        return historicMeterRateTable;
    }

    public void setMeterOpScheduleEffToDateColumn(RichColumn meterOpScheduleEffToDateColumn) {
        this.meterOpScheduleEffToDateColumn = meterOpScheduleEffToDateColumn;
    }

    public RichColumn getMeterOpScheduleEffToDateColumn() {
        return meterOpScheduleEffToDateColumn;
    }

    public void setMeterRateScheduleEffToDateColumn(RichColumn meterRateScheduleEffToDateColumn) {
        this.meterRateScheduleEffToDateColumn = meterRateScheduleEffToDateColumn;
    }

    public RichColumn getMeterRateScheduleEffToDateColumn() {
        return meterRateScheduleEffToDateColumn;
    }

    public void setMeterOpSchedEffToDateInputText(RichInputDate meterOpSchedEffToDateInputText) {
        this.meterOpSchedEffToDateInputText = meterOpSchedEffToDateInputText;
    }

    public RichInputDate getMeterOpSchedEffToDateInputText() {
        return meterOpSchedEffToDateInputText;
    }

    // 20120501-01 added 
    public void setAddNewScheduleButton(RichCommandToolbarButton addNewScheduleButton) {
        this.addNewScheduleButton = addNewScheduleButton;
    }

    // 20120501-01 added 
    public RichCommandToolbarButton getAddNewScheduleButton() {
        return addNewScheduleButton;
    }

    // 20120501-01 added 
    public void setAddNewRateButton(RichCommandToolbarButton addNewRateButton) {
        this.addNewRateButton = addNewRateButton;
    }

    // 20120501-01 added 
    public RichCommandToolbarButton getAddNewRateButton() {
        return addNewRateButton;
    }
    

    /**
     * 20120521-01
     * 
     * This logic is used to warn the user when changing the From Time on a schedule
     *
     * @param valueChangeEvent
     */
    public void opSchedFromTimeValueChangeHandler(ValueChangeEvent valueChangeEvent) {
        String warningMessage = "";
        String scheduleType = "";
        String fromTime = "";
        StringBuilder sbWarningMessage = new StringBuilder("<html><body>");
        boolean addedTWOSchedule = getAddedTWOSchedule();

        // get the schedule type
        scheduleType = getScheduleTypeForCurrentRow();
        
        // get the new time value
        fromTime = valueChangeEvent.getNewValue().toString();
        
        // if the time entered is in the AM and 
        // the scheduleType is TOW then a special message
        // otherwise the "generic warning"
        int myComparison = fromTime.compareTo("12:00");
        if( (fromTime.compareTo("12:00") < 0) && (scheduleType.compareTo("TOW") == 0) && (addedTWOSchedule) ){
            // warning tied to TOW before AM
            sbWarningMessage.append("<b>TOW schedule has been added.</b><br /><br />");
            sbWarningMessage.append("Please review OP schedules<br />");
            sbWarningMessage.append("for PREPAY times starting before<br />");
            sbWarningMessage.append("TOW start times and change them<br />");
            sbWarningMessage.append("as appropriate.");
        } else {
            // generic warning message
            sbWarningMessage.append("<b>OP schedule start time has been changed.</b><br /><br />");
            sbWarningMessage.append("Please review HOURLY rate schedules below<br />");
            sbWarningMessage.append("and change start times as appropriate.");
        }
                
        // initialize the warning message
        sbWarningMessage.append("</body></html>");
        warningMessage = sbWarningMessage.toString();
        
        // add the message to the display
        FacesMessage fm = new FacesMessage(warningMessage);
        fm.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
    }
    

    /**
     * 20120522-01
     * 
     * This is called by opSchedFromTimeValueChangeHandler() which has an event
     * occur on an OP Schedule table row.  This logic will get the 
     * Schedule Type (TOW, ALT, OP) and return it to the calling method.
     * 
     * @return
     */
    public String getScheduleTypeForCurrentRow(){
        String scheduleType = (String)ADFUtils.evaluateEL("#{row.scheduleType.stringForDisplay}");
        
        return scheduleType;
    }

    /**
     * 20120522-01
     * 
     * Sets ViewScope memory variable to indicate whether a new
     * TOW Schedule is created by the user in the Meter OP Schedule
     * 
     * @param addedTWOSchedule
     */
    public void setAddedTWOSchedule(boolean addedTWOSchedule){
        Map viewScope = AdfFacesContext.getCurrentInstance().getViewScope();
        viewScope.put("addedTWOSchedule", addedTWOSchedule);
    }

    /**
     * 20120522-01
     * 
     * Reads ViewScope memory variable that indicates whether a new
     * TOW schedule is created by the user in the Meter OP Schedule
     * 
     * @return
     */
    public boolean getAddedTWOSchedule(){
        boolean addedTWOSchedule;
        Object scopeVariable = null;

        Map viewScope = AdfFacesContext.getCurrentInstance().getViewScope();
        scopeVariable = viewScope.get("addedTWOSchedule");
        if(scopeVariable == null){
            addedTWOSchedule = false;
        } else {
            addedTWOSchedule = (Boolean)scopeVariable;
        }

        return addedTWOSchedule;
    }
}
