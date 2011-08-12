package sfpark.calendar.manager.view.backing.calendar;

import java.sql.Date;

import java.util.List;

import java.util.TreeSet;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.data.helper.CalendarType;
import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.calendar.manager.application.key.PageFlowScopeKey;
import sfpark.calendar.manager.application.key.SessionScopeKey;
import sfpark.calendar.manager.view.backing.BaseBean;
import sfpark.calendar.manager.view.flow.NavigationFlow;
import sfpark.calendar.manager.view.provider.DMLOperationsProvider;
import sfpark.calendar.manager.view.provider.helper.CalendarDetailDDO;

public class CalendarPropertiesBean extends BaseBean implements ListBeanInterface,
                                                                PropertiesBeanInterface,
                                                                RequestScopeBeanInterface {

    private RichCommandButton DeleteDateButton;
    private RichCommandButton UndeleteDateButton;

    private RichTable CalendarDetailTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public CalendarPropertiesBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.CALENDAR_DETAIL_DDO_LIST.getKey());
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

    public CalendarHeaderDTO getCalendarHeaderDTO() {
        CalendarHeaderDTO DTO =
            (CalendarHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.CALENDAR_HEADER_DTO.getKey());

        if (DTO == null) {

            String calendarTypeString =
                (String)getPageFlowScopeValue(PageFlowScopeKey.CALENDAR_TYPE.getKey());

            CalendarType calendarType =
                CalendarType.valueOf(calendarTypeString);

            DTO =
DMLOperationsProvider.INSTANCE.getNewCalendarHeaderDTO(calendarType);
            setPageFlowScopeValue(PageFlowScopeKey.CALENDAR_HEADER_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    public List<CalendarDetailDDO> getCalendarDetailDDOs() {

        List<CalendarDetailDDO> calendarDetailDDOs =
            (List<CalendarDetailDDO>)getPageFlowScopeValue(PageFlowScopeKey.CALENDAR_DETAIL_DDO_LIST.getKey());

        if (calendarDetailDDOs == null) {
            calendarDetailDDOs =
                    DMLOperationsProvider.INSTANCE.getCalendarDetailDDOs(getCalendarHeaderDTO().getCalendarID());
            setPageFlowScopeValue(PageFlowScopeKey.CALENDAR_DETAIL_DDO_LIST.getKey(),
                                  calendarDetailDDOs);
        }

        return calendarDetailDDOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALIDATORS

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

    public boolean isRenderPassiveInfo() {
        return (getCurrentPageMode().isEditMode() ||
                getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isRenderCalendarDetailPanel() {
        return (getCurrentPageMode().isEditMode() ||
                getCurrentPageMode().isReadOnlyMode());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL VISIBLE INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DISPLAY LIST VALUES

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALUE CHANGE HANDLERS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
    }

    public void cancelButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    public void addButtonHandler(ActionEvent event) {

        List<CalendarDetailDDO> calendarDetailDDOs =
            (List<CalendarDetailDDO>)getCalendarDetailTable().getValue();

        TreeSet<Date> disabledDateTreeSet = new TreeSet<Date>();

        for (CalendarDetailDDO DDO : calendarDetailDDOs) {
            disabledDateTreeSet.add(DDO.getDisplayDateDT());
        }

        setPageFlowScopeValue(PageFlowScopeKey.DATE_PICKER_DISABLED_DATES_SET.getKey(),
                              disabledDateTreeSet);
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.PickCalendarDates.name());

    }

    public void editButtonHandler(ActionEvent event) {
    }

    public void deleteButtonHandler(ActionEvent event) {
    }

    public void selectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void refreshTablesAfterClickingSave() {
        resetAllCalendarDetailTableButtons();

        getCalendarDetailTable().getSelectedRowKeys().clear();
        addPartialTarget(getCalendarDetailTable());
    }

    private void setAllCalendarDetailTableButtons() {
        updateAllCalendarDetailTableButtons(false);
    }

    private void resetAllCalendarDetailTableButtons() {
        updateAllCalendarDetailTableButtons(true);
    }

    private void updateAllCalendarDetailTableButtons(boolean disable) {

        if (getDeleteDateButton() != null) {
            getDeleteDateButton().setDisabled(disable);
            addPartialTarget(getDeleteDateButton());
        }

        if (getUndeleteDateButton() != null) {
            getUndeleteDateButton().setDisabled(disable);
            addPartialTarget(getUndeleteDateButton());
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public String getRowSelectionValue() {
        return (getCurrentPageMode().isReadOnlyMode()) ? "none" : "single";
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setDeleteDateButton(RichCommandButton DeleteDateButton) {
        this.DeleteDateButton = DeleteDateButton;
    }

    public RichCommandButton getDeleteDateButton() {
        return DeleteDateButton;
    }

    public void setUndeleteDateButton(RichCommandButton UndeleteDateButton) {
        this.UndeleteDateButton = UndeleteDateButton;
    }

    public RichCommandButton getUndeleteDateButton() {
        return UndeleteDateButton;
    }

    public void setCalendarDetailTable(RichTable CalendarDetailTable) {
        this.CalendarDetailTable = CalendarDetailTable;
    }

    public RichTable getCalendarDetailTable() {
        return CalendarDetailTable;
    }
}

/*
TODO
    private RichCommandButton deleteSchedButton;

    private RichTable activeMeterScheduleTable;

    private RichCommandButton deleteRateButton;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void refreshTable() { // TODO Not sure if this would work

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

        }
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

        }

    }

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

            if (parkingSpaceInventoryDTO.getMeterDetails().isMeterMultiSpace()) {

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

                if (operationStatus.getType().isSuccess()) {
                    ParkingSpaceInventoryDTOStatus parkingSpaceStatus =
                        ParkingSpaceInventoryProvider.INSTANCE.checkForPostID(currentDTO.getPostID());

                    if (parkingSpaceStatus.existsDTO()) {
                        // System.out.println("ADD operation was successful");
                        setInlineMessageText(TranslationUtil.getAssetManagerBundleString(AssetManagerBundleKey.info_create_success));
                        setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);

                        clearPageFlowScopeCache();
                        setCurrentPageMode(NavigationMode.EDIT);
                        setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                              parkingSpaceStatus.getDTO());

                    } else {
                        // System.out.println("ADD operation failed");
                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_parking_space_failure));
                        setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
                    }
                } else {
                    // System.out.println("ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_parking_space_failure));
                    setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);
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
                    if (operationStatus.getType().isSuccess()) {
                        // System.out.println("EDIT operation was successful");
                        setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                        setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);

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
                        setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);

                    }
                }

                refreshTablesAfterClickingSave();

            }
        } else {
            setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);

            if (currentPageMode.isEditMode()) {
                refreshTablesAfterClickingSave();
            }
        }
    }

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
}
 */