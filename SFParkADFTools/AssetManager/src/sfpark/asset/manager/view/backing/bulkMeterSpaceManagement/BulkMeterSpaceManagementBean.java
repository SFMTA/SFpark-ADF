package sfpark.asset.manager.view.backing.bulkMeterSpaceManagement;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import org.apache.myfaces.trinidad.model.RowKeySet;

import sfpark.adf.tools.model.data.dO.parkingSpaceGroups.ParkingSpaceGroupsDO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.meterRateSchedule.MeterRateScheduleDTO;

import sfpark.adf.tools.model.data.helper.MeterOPScheduleType;
import sfpark.adf.tools.model.data.helper.MeterRateScheduleType;
import sfpark.adf.tools.model.data.tO.meterOPSchedule.MeterOPScheduleBulkTO;
import sfpark.adf.tools.model.data.tO.meterRateSchedule.MeterRateScheduleBulkTO;
import sfpark.adf.tools.model.data.tO.parkingSpaceInventory.ParkingSpaceInventoryBulkTO;
import sfpark.adf.tools.model.exception.DTOInsertException;
import sfpark.adf.tools.model.exception.DTOUpdateException;
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.model.provider.MeterRateScheduleProvider;
import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
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
import sfpark.asset.manager.view.provider.DMLOperationsProvider;
import sfpark.asset.manager.view.util.ADFUIDisplayUtil;
import sfpark.asset.manager.view.util.DataRepositoryUtil;

public class BulkMeterSpaceManagementBean extends BaseBean implements RequestScopeBeanInterface,
                                                                      ListBeanInterface,
                                                                      PropertiesBeanInterface {

    private RichCommandButton deleteSchedButton;

    private RichTable meterScheduleTable;

    private RichCommandButton deleteRateButton;

    private RichTable meterRateTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public BulkMeterSpaceManagementBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static BulkMeterSpaceManagementBean getInstance() {
        return (BulkMeterSpaceManagementBean)getCurrentInstanceFor("bulkMeterSpaceManagementBean");
    }

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_TO.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_TO.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_TO.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_LIST.getKey());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL TO and DTO INFORMATION

    public ParkingSpaceInventoryBulkTO getParkingSpaceInventoryBulkTO() {
        ParkingSpaceInventoryBulkTO TO =
            (ParkingSpaceInventoryBulkTO)getPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_TO.getKey());

        if (TO == null) {

            ParkingSpaceGroupsDO parkingSpaceGroupsDO =
                (ParkingSpaceGroupsDO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_GROUPS_DO.getKey());

            TO =
 DMLOperationsProvider.INSTANCE.getNewParkingSpaceInventoryBulkTO();

            TO.setParkingSpaceIDList(parkingSpaceGroupsDO.getParkingSpaceIDList());

            setPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_TO.getKey(),
                                  TO);
        }

        return TO;
    }

    public MeterOPScheduleBulkTO getMeterOPScheduleBulkTO() {
        MeterOPScheduleBulkTO TO =
            (MeterOPScheduleBulkTO)getPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_TO.getKey());

        if (TO == null) {
            TO = DMLOperationsProvider.INSTANCE.getNewMeterOPScheduleBulkTO();

            setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_TO.getKey(),
                                  TO);
        }

        return TO;
    }

    public List<MeterOPScheduleDTO> getMeterSchedules() {

        List<MeterOPScheduleDTO> meterScheduleDTOs =
            (List<MeterOPScheduleDTO>)getPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey());

        if (meterScheduleDTOs == null) {
            meterScheduleDTOs = new ArrayList<MeterOPScheduleDTO>();
            setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey(),
                                  meterScheduleDTOs);
        }

        return meterScheduleDTOs;
    }

    public MeterRateScheduleBulkTO getMeterRateScheduleBulkTO() {
        MeterRateScheduleBulkTO TO =
            (MeterRateScheduleBulkTO)getPageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_TO.getKey());

        if (TO == null) {
            TO =
 DMLOperationsProvider.INSTANCE.getNewMeterRateScheduleBulkTO();

            setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_TO.getKey(),
                                  TO);
        }

        return TO;
    }

    public List<MeterRateScheduleDTO> getMeterRates() {

        List<MeterRateScheduleDTO> meterRateDTOs =
            (List<MeterRateScheduleDTO>)getPageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_LIST.getKey());

        if (meterRateDTOs == null) {
            meterRateDTOs = new ArrayList<MeterRateScheduleDTO>();
            setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_LIST.getKey(),
                                  meterRateDTOs);
        }

        return meterRateDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALIDATORS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    public boolean isDisableActiveMeterStatusSBCB() {
        boolean disabled = getParkingSpaceInventoryBulkTO().isUnmetered();

        return disabled;
    }

    public boolean isDisableActiveMeterStatusSOC() {
        boolean disabled =
            getParkingSpaceInventoryBulkTO().isUnmetered() || !getParkingSpaceInventoryBulkTO().isToBeUpdatedActiveMeterFlag();

        return disabled;
    }

    public boolean isDisableMeterDetailsSBCB() {
        boolean disabled = getParkingSpaceInventoryBulkTO().isUnmetered();

        return disabled;
    }

    public boolean isDisableMeterDetailsButton() {
        boolean disabled =
            getParkingSpaceInventoryBulkTO().isUnmetered() || !getParkingSpaceInventoryBulkTO().isToBeUpdatedMeterDetails();

        return disabled;
    }

    public boolean isDisableMSPayStationIDIT() {
        boolean disabled =
            getParkingSpaceInventoryBulkTO().isUnmetered() || !getParkingSpaceInventoryBulkTO().isToBeUpdatedMeterDetails() ||
            !getParkingSpaceInventoryBulkTO().getMeterDetails().getMeterType().isMultiSpace() ||
            getParkingSpaceInventoryBulkTO().getMeterDetails().getMeterType().isNone();

        return disabled;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListSensorStatus() {
        return ADFUIDisplayUtil.getSensorFlagDisplayList();
    }

    public List<SelectItem> getListCapColorSOC() {
        return ADFUIDisplayUtil.getCapColorDisplayList();
    }

    public List<SelectItem> getListActiveMeterStatus() {
        return ADFUIDisplayUtil.getActiveMeterFlagBulkDisplayList();
    }

    public List<SelectItem> getListReasonCode() {
        return ADFUIDisplayUtil.getReasonCodeDisplayList();
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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void addButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("Sched")) {
            MeterOPScheduleType meterOPScheduleType = MeterOPScheduleType.OP;
            if (ID.contains("ALT")) {
                meterOPScheduleType = MeterOPScheduleType.ALT;
            } else if (ID.contains("TOW")) {
                meterOPScheduleType = MeterOPScheduleType.TOW;
            } else {
                meterOPScheduleType = MeterOPScheduleType.OP;
            }

            setPageFlowScopeValue(PageFlowScopeKey.METER_SCHEDULE_TEMPLATE_TYPE.getKey(),
                                  meterOPScheduleType);

            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getMeterScheduleTable().getValue();
            setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey(),
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
                (List<MeterRateScheduleDTO>)getMeterRateTable().getValue();

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Set the checkboxes properly

            getMeterRateScheduleBulkTO().setProperBoolean(meterRateScheduleType,
                                                          true);

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Retrieve the next effective from date

            java.sql.Date nextEffectiveFromDate =
                MeterRateScheduleProvider.INSTANCE.getNextEffectiveFromDate(getParkingSpaceInventoryBulkTO().getParkingSpaceIDList(),
                                                                            meterRateScheduleType);

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Perform the addition

            MeterRateScheduleDTO meterRateScheduleDTO =
                DMLOperationsProvider.INSTANCE.getNewMeterRateScheduleDTO(meterRateScheduleType,
                                                                          null,
                                                                          getNextPriorityInt(meterRateScheduleDTOs,
                                                                                             meterRateScheduleType),
                                                                          nextEffectiveFromDate,
                                                                          null);

            meterRateScheduleDTOs.add(0, meterRateScheduleDTO);

            setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_LIST.getKey(),
                                  meterRateScheduleDTOs);

            getMeterRateTable().getSelectedRowKeys().clear();
            resetAllMeterRateTableButtons();
            getMeterRateTable().setValue(null);
            // getMeterRateTable().setValue(meterRateScheduleDTOs);

            addPartialTarget(getMeterRateTable());

        }
    }

    public void editButtonHandler(ActionEvent event) {
        //
        // Reuse as "Meter Models Button"
        //

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.MeterModelsPage.name());

    }

    public void deleteButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("Sched")) {

            RichTable table = getMeterScheduleTable();
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
                        tempList.add(dto);
                    }
                }

                List<MeterOPScheduleDTO> meterSchedules =
                    (List<MeterOPScheduleDTO>)table.getValue();

                meterSchedules.removeAll(tempList);

                setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey(),
                                      meterSchedules);

                for (MeterOPScheduleType meterOPScheduleType :
                     MeterOPScheduleType.values()) {
                    getMeterOPScheduleBulkTO().setProperBoolean(meterOPScheduleType,
                                                                containsScheduleType(meterSchedules,
                                                                                     meterOPScheduleType));
                }

                table.getSelectedRowKeys().clear();
                resetAllMeterScheduleTableButtons();
                table.setValue(null);
                addPartialTarget(table);
            }

        } else if (ID.contains("Rate")) {

            RichTable table = getMeterRateTable();
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
                        tempList.add(dto);
                    }
                }

                List<MeterRateScheduleDTO> meterRates =
                    (List<MeterRateScheduleDTO>)table.getValue();

                meterRates.removeAll(tempList);

                setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_LIST.getKey(),
                                      meterRates);

                for (MeterRateScheduleType meterRateScheduleType :
                     MeterRateScheduleType.values()) {
                    getMeterRateScheduleBulkTO().setProperBoolean(meterRateScheduleType,
                                                                  containsRateType(meterRates,
                                                                                   meterRateScheduleType));
                }

                table.getSelectedRowKeys().clear();
                resetAllMeterRateTableButtons();
                table.setValue(null);
                addPartialTarget(table);
            }

        }

    }

    public void selectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("MSTable")) {
            RichTable table = getMeterScheduleTable();

            int selectedNumOfRows = table.getSelectedRowKeys().getSize();

            if (selectedNumOfRows > 0) {
                setAllMeterScheduleTableButtons();
            } else {
                resetAllMeterScheduleTableButtons();
            }

        } else if (ID.contains("MRTable")) {
            RichTable table = getMeterRateTable();

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
     *    1. Check for Cap Color
     *       ---Black/Brown are NOT allowed
     *    2. Check for Meter Schedule validations
     *    3. Check for Meter Rate validations
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;

        ParkingSpaceInventoryBulkTO parkingSpaceInventoryBulkTO =
            getParkingSpaceInventoryBulkTO();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            if (parkingSpaceInventoryBulkTO.isToBeUpdatedCapColor() &&
                parkingSpaceInventoryBulkTO.isInvalidCapColor()) {

                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_bulk_meter_invalid_to_cap_color));

                allValid = false;
            }
        }

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            // System.out.println("Under edit mode, test for Meter Schedules as well");
            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getMeterScheduleTable().getValue();

            allValid =
                    areValidMeterSchedules(meterSchedules, parkingSpaceInventoryBulkTO,
                                           "Current");
        }

        // System.out.println("After Meter OP Schedule = " + allValid);

        if (allValid) {
            // System.out.println("Under edit mode, test for Meter Rates as well");
            List<MeterRateScheduleDTO> meterRates =
                (List<MeterRateScheduleDTO>)getMeterRateTable().getValue();

            allValid = areValidMeterRates(meterRates, "Current");
        }

        // System.out.println("After Meter Rate Schedule = " + allValid);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        //        allValid = false;
        //        String oldText = getInlineMessageText();
        //        String newText =
        //            StringUtil.isBlank(oldText) ? "All are valid" : oldText;
        //        setInlineMessageText(newText);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            // System.out.println("All entries are Valid. Proceed");

            ParkingSpaceInventoryBulkTO currentParkingSpaceInventoryBulkTO =
                getParkingSpaceInventoryBulkTO();

            MeterOPScheduleBulkTO currentMeterOPScheduleBulkTO =
                getMeterOPScheduleBulkTO();
            List<MeterOPScheduleDTO> currentMeterSchedules =
                (List<MeterOPScheduleDTO>)getMeterScheduleTable().getValue();

            MeterRateScheduleBulkTO currentMeterRateScheduleBulkTO =
                getMeterRateScheduleBulkTO();
            List<MeterRateScheduleDTO> currentMeterRates =
                (List<MeterRateScheduleDTO>)getMeterRateTable().getValue();

            OperationStatus operationStatus =
                DMLOperationsProvider.INSTANCE.editBulkParkingSpace(currentParkingSpaceInventoryBulkTO,
                                                                    currentMeterOPScheduleBulkTO,
                                                                    currentMeterSchedules,
                                                                    currentMeterRateScheduleBulkTO,
                                                                    currentMeterRates);

            if (operationStatus == null) {
                // System.out.println("There were no changes. So nothing was saved");
                setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_nothing_to_save));
                setInlineMessageClass("");

            } else {
                if (operationStatus.isSuccess()) {
                    // System.out.println("EDIT operation was successful");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                    clearPageFlowScopeCache();
                    getMeterScheduleTable().setValue(null);
                    getMeterRateTable().setValue(null);

                } else {
                    // System.out.println("EDIT operation failed");
                    String errorMessage = "";

                    switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                    case UNIQUE_CONTRAINT:
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
                            } else {
                                errorMessage =
                                        operationStatus.getException().getMessage();
                            }
                        }
                        break;

                    case GENERAL:
                        {
                            errorMessage =
                                    operationStatus.getException().getMessage();
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

        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);

        }

        resetAllMeterScheduleTableButtons();
        resetAllMeterRateTableButtons();

        getMeterScheduleTable().getSelectedRowKeys().clear();
        addPartialTarget(getMeterScheduleTable());

        getMeterRateTable().getSelectedRowKeys().clear();
        addPartialTarget(getMeterRateTable());

    }

    public void cancelButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    /**
     * Unmeters/Remeter the Parking Space by setting the appropriate values
     *
     * Unmeter Parking Space:
     * ====================
     *    1. Set ACTIVE_METER_FLAG = "U"
     *    3. Update Meter Model as per "U"
     *    4. End-Date Meter Schedules
     *    5. End-Date Meter Rates
     *
     * Remeter Parking Space:
     * =====================
     *    1. Set ACTIVE_METER_FLAG = "M"
     *
     *
     * @param event
     */
    public void unmeterRemeterParkingSpaceButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.equals("unmeterParkingSpaceButton")) {

            getParkingSpaceInventoryBulkTO().setToBeUpdatedActiveMeterFlag(true);
            getParkingSpaceInventoryBulkTO().setActiveMeterFlag("U");

            getParkingSpaceInventoryBulkTO().setToBeUpdatedMeterDetails(true);
            getParkingSpaceInventoryBulkTO().setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDONULLValue());

            getMeterOPScheduleBulkTO().setAllBoolean(true);
            endDateAllSchedules();

            getMeterRateScheduleBulkTO().setAllBoolean(true);
            endDateAllRates();

        } else {

            getParkingSpaceInventoryBulkTO().setToBeUpdatedActiveMeterFlag(false);
            getParkingSpaceInventoryBulkTO().setActiveMeterFlag(AllowedValuesProvider.getActiveMeterFlagBulkDefaultValue());

            getParkingSpaceInventoryBulkTO().setToBeUpdatedMeterDetails(false);
            getParkingSpaceInventoryBulkTO().setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDODefaultValue());

            getMeterOPScheduleBulkTO().setAllBoolean(false);

            getMeterRateScheduleBulkTO().setAllBoolean(false);

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
     * @param bulkTO
     * @param uiTableName
     * @return
     */
    private boolean areValidMeterSchedules(List<MeterOPScheduleDTO> meterSchedules,
                                           ParkingSpaceInventoryBulkTO bulkTO,
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
            //    ---If SCHED_TYPE = OP
            //       ---If Cap Color is being updated
            //          ---COLOR_RULE_APPLIED should be same as CAP_COLOR
            //       ---Else
            //          ---COLOR_RULE_APPLIED should be NULL
            if (iDTO.getScheduleType().isOP()) {
                if (bulkTO.isToBeUpdatedCapColor()) {
                    if (!StringUtil.areEqual(iDTO.getColorRuleApplied(),
                                             bulkTO.getCapColor())) {

                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_meter_schedule_op_invalid_cap_color,
                                                                                  uiTableName,
                                                                                  (i +
                                                                                   1)));

                        return false;
                    }
                } else {
                    if (StringUtil.isNotBlank(iDTO.getColorRuleApplied())) {

                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_bulk_meter_schedule_op_invalid_cap_color,
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
     * @param uiTableName
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
        RichTable table = getMeterScheduleTable();

        removePageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey());

        table.getSelectedRowKeys().clear();
        resetAllMeterScheduleTableButtons();
        table.setValue(null);
        addPartialTarget(table);
    }

    private void endDateAllRates() {
        RichTable table = getMeterRateTable();

        removePageFlowScopeValue(PageFlowScopeKey.BULK_METER_RATE_LIST.getKey());

        table.getSelectedRowKeys().clear();
        resetAllMeterRateTableButtons();
        table.setValue(null);
        addPartialTarget(table);
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

    private boolean containsScheduleType(List<MeterOPScheduleDTO> meterSchedules,
                                         MeterOPScheduleType meterOPScheduleType) {

        if (meterSchedules == null || meterSchedules.isEmpty()) {
            return false;
        }

        for (MeterOPScheduleDTO DTO : meterSchedules) {
            if (DTO.getScheduleType().equals(meterOPScheduleType)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsRateType(List<MeterRateScheduleDTO> meterRates,
                                     MeterRateScheduleType meterRateScheduleType) {

        if (meterRates == null || meterRates.isEmpty()) {
            return false;
        }

        for (MeterRateScheduleDTO DTO : meterRates) {
            if (DTO.getRateType().equals(meterRateScheduleType)) {
                return true;
            }
        }

        return false;
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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public void setInlineMessageText(String inlineMessageText) {
        setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey(),
                              inlineMessageText);
    }

    public String getInlineMessageText() {
        return (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey());
    }

    public void setInlineMessageClass(String inlineMessageClass) {
        setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey(),
                              inlineMessageClass);
    }

    public String getInlineMessageClass() {
        return (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey());
    }

    public java.sql.Date getMinimumEffectiveToDate() {
        return SQLDateUtil.getTodaysDate();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setDeleteSchedButton(RichCommandButton deleteSchedButton) {
        this.deleteSchedButton = deleteSchedButton;
    }

    public RichCommandButton getDeleteSchedButton() {
        return deleteSchedButton;
    }

    public void setMeterScheduleTable(RichTable meterScheduleTable) {
        this.meterScheduleTable = meterScheduleTable;
    }

    public RichTable getMeterScheduleTable() {
        return meterScheduleTable;
    }

    public void setMeterRateTable(RichTable meterRateTable) {
        this.meterRateTable = meterRateTable;
    }

    public RichTable getMeterRateTable() {
        return meterRateTable;
    }

    public void setDeleteRateButton(RichCommandButton deleteRateButton) {
        this.deleteRateButton = deleteRateButton;
    }

    public RichCommandButton getDeleteRateButton() {
        return deleteRateButton;
    }
}
