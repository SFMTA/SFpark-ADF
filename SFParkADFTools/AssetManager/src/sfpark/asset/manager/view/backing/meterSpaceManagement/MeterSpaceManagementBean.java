package sfpark.asset.manager.view.backing.meterSpaceManagement;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;

import sfpark.adf.tools.model.data.dO.blockface.BlockfaceDO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.meterRateSchedule.MeterRateScheduleDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;
import sfpark.adf.tools.model.data.helper.EffectiveDateCalculator;
import sfpark.adf.tools.model.data.helper.MeterRateType;
import sfpark.adf.tools.model.data.helper.MeterScheduleType;
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dO.BlockfaceDOStatus;
import sfpark.adf.tools.model.helper.dto.ParkingSpaceInventoryDTOStatus;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.model.provider.BlockfacesProvider;
import sfpark.adf.tools.model.provider.MeterOPScheduleProvider;
import sfpark.adf.tools.model.provider.MeterRateScheduleProvider;
import sfpark.adf.tools.model.provider.ParkingSpaceInventoryProvider;

import sfpark.adf.tools.translation.AssetManagerBundleKey;
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

public class MeterSpaceManagementBean extends BaseBean implements RequestScopeBeanInterface,
                                                                  ListBeanInterface,
                                                                  PropertiesBeanInterface {

    private RichSelectOneRadio streetTypeSOR;

    private RichInputText meterVendorIT;
    private RichInputText meterModelIT;
    private RichInputText smartMeterIT;
    private RichInputText meterTypeIT;
    private RichInputText msPayStationIDIT;
    private RichInputText msNumberIT;

    private RichInputText cnnIDIT;

    private RichCommandButton deleteSchedButton;

    private RichTable activeMeterScheduleTable;
    private RichTable historicMeterScheduleTable;

    private RichCommandButton deleteRateButton;

    private RichTable activeMeterRateTable;
    private RichTable historicMeterRateTable;

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

    /*
    public void meterScheduleColorRuleAppliedValidator(FacesContext facesContext,
                                                       UIComponent uiComponent,
                                                       Object object) {

        String colorRuleApplied = (String)object;
        String capColor = getCurrentParkingSpaceInventoryDTO().getCapColor();

        if (!StringUtil.areEqual(capColor, colorRuleApplied)) {

            String ID = uiComponent.getClientId(facesContext);

            FacesMessage facesMessage =
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                 "Cap Color Different (" + ID + ")",
                                 "Color rule applied is different from Cap Color");

            facesContext.addMessage(uiComponent.getClientId(facesContext),
                                    facesMessage);
        }

    }
    */

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

    public List<SelectItem> getListTimeLimit() {
        return ADFUIDisplayUtil.TIME_LIMIT_LIST;
    }

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
        }

        // If moving from Anything to Unmetered, then reset the values
        if (newValue.contains("U")) {
            getCurrentParkingSpaceInventoryDTO().setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDONULLValue());
        }

        updateAllMeterDetails();
        addPartialTarget(getCnnIDIT());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void addButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("Sched")) {
            MeterScheduleType scheduleType = MeterScheduleType.OP;
            if (ID.contains("ALT")) {
                scheduleType = MeterScheduleType.ALT;
            } else if (ID.contains("TOW")) {
                scheduleType = MeterScheduleType.TOW;
            } else {
                scheduleType = MeterScheduleType.OP;
            }

            setPageFlowScopeValue(PageFlowScopeKey.METER_SCHEDULE_TEMPLATE_TYPE.getKey(),
                                  scheduleType);

            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getActiveMeterScheduleTable().getValue();
            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                                  meterSchedules);

            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.MeterScheduleTemplatePage.name());

        } else if (ID.contains("Rate")) {
            MeterRateType rateType = MeterRateType.B;
            if (ID.contains("Hourly")) {
                rateType = MeterRateType.H;
            } else {
                rateType = MeterRateType.B;
            }

            List<MeterRateScheduleDTO> meterRateScheduleDTOs =
                (List<MeterRateScheduleDTO>)getActiveMeterRateTable().getValue();

            MeterRateScheduleDTO meterRateScheduleDTO =
                DMLOperationsProvider.INSTANCE.getNewMeterRateScheduleDTO(rateType,
                                                                          getCurrentParkingSpaceInventoryDTO().getParkingSpaceID(),
                                                                          getNextPriorityInt(meterRateScheduleDTOs,
                                                                                             rateType),
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

    public void selectAllButtonHandler(ActionEvent event) {

        /*
        // System.out.println("Inside Select All");
        RichTable table = getMeterScheduleTable();

        int rowCount = table.getRowCount();
        // System.out.println("Row Count = " + rowCount);

        if (rowCount > 0) {
            RowKeySet rowKeySet = new RowKeySetImpl();

            for (int i = 0; i < rowCount; i++) {

                table.setRowIndex(i);
                Object key = table.getRowKey();
                rowKeySet.add(key);
            }

            table.setSelectedRowKeys(rowKeySet);
            addPartialTarget(table);

            setAllMeterScheduleTableButtons();
        }
        */
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        /*
        getMeterScheduleTable().getSelectedRowKeys().clear();
        addPartialTarget(getMeterScheduleTable());
        resetAllMeterScheduleTableButtons();
        */
    }

    public void tableRowSelectionHandler(SelectionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("MSTable")) {
            RichTable table = getActiveMeterScheduleTable();

            int selectedNumOfRows = table.getSelectedRowKeys().getSize();

            if (selectedNumOfRows > 0) {
                setAllMeterScheduleTableButtons();
            } else {
                resetAllMeterScheduleTableButtons();
            }

        } else if (ID.contains("MRTable")) {
            RichTable table = getActiveMeterRateTable();

            int selectedNumOfRows = table.getSelectedRowKeys().getSize();

            if (selectedNumOfRows > 0) {
                setAllMeterRateTableButtons();
            } else {
                resetAllMeterRateTableButtons();
            }

        }

    }

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

        // System.out.println("Check for Post ID = " + checkPostIDUniqueness);

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

        // System.out.println("After Blockface check = " + allValid);

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

        // System.out.println("After ON-OFF Street = " + allValid);

        if (allValid) {

            String cnnID = parkingSpaceInventoryDTO.getCNNID();

            if (StringUtil.isBlank(cnnID) || StringUtil.areEqual(cnnID, "0")) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_invalid_cnn_id));
            }
        }

        // System.out.println("After CNN ID = " + allValid);

        if (allValid && checkPostIDUniqueness) {
            ParkingSpaceInventoryDTOStatus parkingSpaceStatus =
                ParkingSpaceInventoryProvider.INSTANCE.checkForPostID(parkingSpaceInventoryDTO.getPostID());

            if (parkingSpaceStatus.existsDTO()) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exists_already_post_id));
            }
        }

        // System.out.println("After Post ID check = " + allValid);

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

        // System.out.println("After Meter Type check = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            // System.out.println("Under edit mode, test for Active Meter Schedules as well");
            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getActiveMeterScheduleTable().getValue();

            allValid =
                    areValidMeterSchedules(meterSchedules, parkingSpaceInventoryDTO.getCapColor(),
                                           "Active");
        }

        // System.out.println("After Active Meter OP Schedule = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            // System.out.println("Under edit mode, test for Historic Meter Schedules as well");
            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getHistoricMeterScheduleTable().getValue();

            allValid =
                    areValidMeterSchedules(meterSchedules, parkingSpaceInventoryDTO.getCapColor(),
                                           "Historic");
        }

        // System.out.println("After Historic Meter OP Schedule = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            // System.out.println("Under edit mode, test for Active Meter Rates as well");
            List<MeterRateScheduleDTO> meterRates =
                (List<MeterRateScheduleDTO>)getActiveMeterRateTable().getValue();

            allValid = areValidMeterRates(meterRates, "Active");
        }

        // System.out.println("After Active Meter Rate Schedule = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            // System.out.println("Under edit mode, test for Historic Meter Rates as well");
            List<MeterRateScheduleDTO> meterRates =
                (List<MeterRateScheduleDTO>)getHistoricMeterRateTable().getValue();

            allValid = areValidMeterRates(meterRates, "Historic");
        }

        // System.out.println("After Historic Meter Rate Schedule = " + allValid);

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
            // System.out.println("All entries are Valid. Proceed");

            if (currentPageMode.isAddMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ADD Mode
                // System.out.println("ADD Mode");

                ParkingSpaceInventoryDTO currentDTO =
                    getCurrentParkingSpaceInventoryDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addParkingSpace(currentDTO);

                if (operationStatus.isSuccess()) {
                    ParkingSpaceInventoryDTOStatus parkingSpaceStatus =
                        ParkingSpaceInventoryProvider.INSTANCE.checkForPostID(currentDTO.getPostID());

                    if (parkingSpaceStatus.existsDTO()) {
                        // System.out.println("ADD operation was successful");
                        setInlineMessageText(TranslationUtil.getAssetManagerBundleString(AssetManagerBundleKey.info_create_success));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                        clearPageFlowScopeCache();
                        setCurrentPageMode(NavigationMode.EDIT);
                        setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                              parkingSpaceStatus.getDTO());

                    } else {
                        // System.out.println("ADD operation failed");
                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_parking_space_failure));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    }
                } else {
                    // System.out.println("ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_parking_space_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }

            } else if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                // System.out.println("EDIT Mode");

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
                    // System.out.println("There were no changes. So nothing was saved");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_nothing_to_save));
                    setInlineMessageClass("");

                } else {
                    if (operationStatus.isSuccess()) {
                        // System.out.println("EDIT operation was successful");
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

                    } else {
                        // System.out.println("EDIT operation failed");
                        String errorMessage = "";

                        switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                        case UNIQUE_CONTRAINT:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_unique_constraint);
                            break;

                        case METER_OP_SCHEDULE_INSERT:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_schedule_insert);
                            break;

                        case METER_RATE_SCHEDULE_INSERT:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_rate_insert);
                            break;

                        case METER_OP_SCHEDULE_UPDATE:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_schedule_update);
                            break;

                        case METER_RATE_SCHEDULE_UPDATE:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_rate_update);
                            break;

                        case PARKING_SPACE_INVENTORY_UPDATE:
                            errorMessage =
                                    TranslationUtil.getErrorBundleString(ErrorBundleKey.error_parking_space_exception_inventory_update);
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

    public void anyValueChangeHandler(ValueChangeEvent event) {
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
            getCurrentParkingSpaceInventoryDTO().setCapColor(AllowedValuesProvider.getCapColorDefaultValue());

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
                if (fromTime == 0 && iDTO.getScheduleType().isScheduleALT() &&
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
            if (iDTO.getScheduleType().isScheduleOP()) {
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
                if (fromTime == 0 && iDTO.getRateType().isRateTypeB()) {
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

        // TODO

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

        // TODO

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
                                   MeterRateType rateType) {

        int nextPriority = 0;

        if (meterRateScheduleDTOs != null &&
            !meterRateScheduleDTOs.isEmpty()) {
            for (MeterRateScheduleDTO DTO : meterRateScheduleDTOs) {
                if (DTO.getRateType().equals(rateType)) {
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

    private void setAllMeterScheduleTableButtons() {
        updateAllMeterScheduleTableButtons(false);
    }

    private void resetAllMeterScheduleTableButtons() {
        updateAllMeterScheduleTableButtons(true);
    }

    private void updateAllMeterScheduleTableButtons(boolean disable) {

        if (getDeleteSchedButton() != null) {
            getDeleteSchedButton().setDisabled(disable);
            addPartialTarget(getDeleteSchedButton());
        }
    }

    private void setAllMeterRateTableButtons() {
        updateAllMeterRateTableButtons(false);
    }

    private void resetAllMeterRateTableButtons() {
        updateAllMeterRateTableButtons(true);
    }

    private void updateAllMeterRateTableButtons(boolean disable) {

        if (getDeleteRateButton() != null) {
            getDeleteRateButton().setDisabled(disable);
            addPartialTarget(getDeleteRateButton());
        }
    }

    private void updateAllMeterDetails() {
        addPartialTarget(getMeterVendorIT());
        addPartialTarget(getMeterModelIT());
        addPartialTarget(getMeterTypeIT());
        addPartialTarget(getSmartMeterIT());
        addPartialTarget(getMsPayStationIDIT());
        addPartialTarget(getMsNumberIT());
    }

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
}
