package sfpark.asset.manager.view.backing.bulkMeterSpaceManagement;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import org.apache.myfaces.trinidad.model.RowKeySet;

import org.apache.myfaces.trinidad.model.RowKeySetImpl;

import sfpark.adf.tools.model.data.dO.parkingSpaceGroups.ParkingSpaceGroupsDO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryBulkDTO;

import sfpark.adf.tools.model.data.helper.MeterScheduleType;
import sfpark.adf.tools.model.data.tO.meterOPSchedule.MeterOPScheduleBulkTO;
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.provider.MeterModelsProvider;
import sfpark.adf.tools.model.status.OperationStatus;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;
import sfpark.adf.tools.utilities.ui.DayUI;
import sfpark.adf.tools.utilities.ui.TimeUI;

import sfpark.asset.manager.application.key.PageFlowScopeKey;
import sfpark.asset.manager.application.key.SessionScopeKey;
import sfpark.asset.manager.view.backing.BaseBean;
import sfpark.asset.manager.view.backing.BaseBeanInterface;
import sfpark.asset.manager.view.backing.helper.ListBeanInterface;
import sfpark.asset.manager.view.backing.helper.PropertiesBeanInterface;
import sfpark.asset.manager.view.flow.NavigationFlow;
import sfpark.asset.manager.view.provider.DMLOperationsProvider;
import sfpark.asset.manager.view.util.ADFUIDisplayUtil;
import sfpark.asset.manager.view.util.DataRepositoryUtil;

public class BulkMeterSpaceManagementBean extends BaseBean implements BaseBeanInterface,
                                                                      ListBeanInterface,
                                                                      PropertiesBeanInterface {

    private RichInputText meterVendorIT;
    private RichInputText meterModelIT;
    private RichInputText smartMeterIT;
    private RichInputText meterTypeIT;
    private RichInputText msPayStationIDIT;

    private RichCommandButton deleteSchedButton;
    private RichCommandButton selectAllSchedButton;
    private RichCommandButton unselectAllSchedButton;

    private RichTable meterScheduleTable;

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
        removePageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_DTO.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_TO.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public ParkingSpaceInventoryBulkDTO getCurrentParkingSpaceInventoryBulkDTO() {
        ParkingSpaceInventoryBulkDTO DTO =
            (ParkingSpaceInventoryBulkDTO)getPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_DTO.getKey());

        if (DTO == null) {

            ParkingSpaceGroupsDO parkingSpaceGroupsDO =
                (ParkingSpaceGroupsDO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_GROUPS_DO.getKey());

            DTO =
DMLOperationsProvider.INSTANCE.getNewParkingSpaceInventoryBulkDTO();

            DTO.setParkingSpaceIDList(parkingSpaceGroupsDO.getParkingSpaceIDList());

            setPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_DTO.getKey(),
                                  DTO);
        }
        return DTO;
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

    /*
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
     */

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    public boolean isDisableSensorStatusSBCB() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D");

        return disabled;
    }

    public boolean isDisableSensorStatusSOC() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D") ||
            !getCurrentParkingSpaceInventoryBulkDTO().isToBeUpdatedSensorFlag();

        return disabled;
    }

    public boolean isDisableCapColorSBCB() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D");

        return disabled;
    }

    public boolean isDisableCapColorSOC() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D") ||
            !getCurrentParkingSpaceInventoryBulkDTO().isToBeUpdatedCapColor();

        return disabled;
    }

    public boolean isDisableOldRateAreaSBCB() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("U") ||
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D");

        return disabled;
    }

    public boolean isDisableOldRateAreaSOC() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("U") ||
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D") ||
            !getCurrentParkingSpaceInventoryBulkDTO().isToBeUpdatedOldRateArea();

        return disabled;
    }

    public boolean isDisablePCOBeatSBCB() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D");

        return disabled;
    }

    public boolean isDisablePCOBeatIT() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D") ||
            !getCurrentParkingSpaceInventoryBulkDTO().isToBeUpdatedPCOBeat();

        return disabled;
    }

    public boolean isDisableMeterDetailsSBCB() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("U") ||
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D");

        return disabled;
    }

    public boolean isDisableMeterDetailsButton() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("U") ||
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D") ||
            !getCurrentParkingSpaceInventoryBulkDTO().isToBeUpdatedMeterDetails();

        return disabled;
    }

    public boolean isDisableMSPayStationIDIT() {
        boolean disabled =
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("U") ||
            getCurrentParkingSpaceInventoryBulkDTO().getActiveMeterFlag().contains("D") ||
            getCurrentParkingSpaceInventoryBulkDTO().getMeterDetails().getMeterType().contains("SS") ||
            getCurrentParkingSpaceInventoryBulkDTO().getMeterDetails().getMeterType().contains("-") ||
            !getCurrentParkingSpaceInventoryBulkDTO().isToBeUpdatedMeterDetails();

        return disabled;
    }

    public boolean isDisableDeleteAllOPSBCB() {
        boolean disabled = disableComponent(MeterScheduleType.OP);

        return disabled;
    }

    public boolean isDisableDeleteAllALTSBCB() {
        boolean disabled = disableComponent(MeterScheduleType.ALT);

        return disabled;
    }

    public boolean isDisableDeleteAllTOWSBCB() {
        boolean disabled = disableComponent(MeterScheduleType.TOW);

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
        return ADFUIDisplayUtil.getActiveMeterFlagDisplayList();
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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALUE CHANGE HANDLERS

    public void activeMeterStatusValueChangeHandler(ValueChangeEvent event) {

        String oldValue = (String)event.getOldValue();
        String newValue = (String)event.getNewValue();

        // If moving from Unmetered to Anything, then set the values
        if (oldValue.contains("U")) {
            getCurrentParkingSpaceInventoryBulkDTO().setDisplayMeterDetails(DataRepositoryUtil.getMeterModelsDODefaultValue());
        }

        // If moving from Anything to Unmetered, then reset the values
        if (newValue.contains("U")) {
            getCurrentParkingSpaceInventoryBulkDTO().setDisplayMeterDetails(MeterModelsProvider.INSTANCE.getNullMeterModelsDO());

            getCurrentParkingSpaceInventoryBulkDTO().setToBeUpdatedOldRateArea(false);
            getCurrentParkingSpaceInventoryBulkDTO().setToBeUpdatedMeterDetails(false);
        }

        updateAllMeterDetails();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void addButtonHandler(ActionEvent event) {
        String ID = event.getComponent().getId();

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
            (List<MeterOPScheduleDTO>)getMeterScheduleTable().getValue();
        setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                              meterSchedules);

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.MeterScheduleTemplatePage.name());
    }

    public void editButtonHandler(ActionEvent event) {
        //
        // Reuse as "Meter Models Button"
        //

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.MeterModelsPage.name());
    }

    public void deleteButtonHandler(ActionEvent event) {
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

            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                                  meterSchedules);

            table.getSelectedRowKeys().clear();
            resetAllMeterScheduleTableButtons();
            table.setValue(null);
            addPartialTarget(table);

        }
    }

    public void selectAllButtonHandler(ActionEvent event) {
        RichTable table = getMeterScheduleTable();

        int rowCount = table.getRowCount();

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
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        getMeterScheduleTable().getSelectedRowKeys().clear();
        addPartialTarget(getMeterScheduleTable());
        resetAllMeterScheduleTableButtons();
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        RichTable table = getMeterScheduleTable();

        int selectedNumOfRows = table.getSelectedRowKeys().getSize();

        if (selectedNumOfRows > 0) {
            setAllMeterScheduleTableButtons();

            getSelectAllSchedButton().setDisabled((selectedNumOfRows ==
                                                   table.getRowCount()));

        } else {
            resetAllMeterScheduleTableButtons();
        }
    }

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Check for Meter Schedule validations
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;

        ParkingSpaceInventoryBulkDTO bulkDTO =
            getCurrentParkingSpaceInventoryBulkDTO();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            if (bulkDTO.isToBeUpdatedMeterDetails() &&
                bulkDTO.getMeterDetails().getMeterType().contains("MS")) {
                if (StringUtil.areEqual(bulkDTO.getMSPayStationID(),
                                        "000-00000")) {

                    setInlineMessageText("Invalid MS Pay Station ID");
                    allValid = false;
                }
            }
        }

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            // System.out.println("Under edit mode, test for Meter Schedules as well");
            List<MeterOPScheduleDTO> meterSchedules =
                (List<MeterOPScheduleDTO>)getMeterScheduleTable().getValue();


            allValid = areValidMeterSchedules(meterSchedules, bulkDTO);
        }

        // System.out.println("After Meter OP Schedule = " + allValid);

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

            ParkingSpaceInventoryBulkDTO currentDTO =
                getCurrentParkingSpaceInventoryBulkDTO();
            List<MeterOPScheduleDTO> currentMeterSchedules =
                (List<MeterOPScheduleDTO>)getMeterScheduleTable().getValue();
            MeterOPScheduleBulkTO currentTO = getMeterOPScheduleBulkTO();

            OperationStatus operationStatus =
                DMLOperationsProvider.INSTANCE.editBulkParkingSpace(currentDTO,
                                                                    currentTO,
                                                                    currentMeterSchedules);

            if (operationStatus == null) {
                setInlineMessageText("There were no changes. So nothing was saved.");
                setInlineMessageClass("");

            } else {
                if (operationStatus.getType().isSuccess()) {
                    // System.out.println("EDIT operation was successful");
                    setInlineMessageText("Successfully saved all the details.");
                    setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);

                    clearPageFlowScopeCache();
                    getMeterScheduleTable().setValue(null);

                } else {
                    // System.out.println("EDIT operation failed");
                    String errorMessage = "";

                    switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                    case UNIQUE_CONTRAINT:
                        errorMessage =
                                "Failed due to invalid Meter Schedules during archiving.";
                        break;

                    case METER_OP_SCHEDULE_INSERT:
                        errorMessage =
                                "Failed due to invalid new Meter Schedules. Try removing.";
                        break;

                    case METER_OP_SCHEDULE_UPDATE:
                        errorMessage =
                                "Failed due to invalid existing Meter Schedules.";
                        break;

                    case PARKING_SPACE_INVENTORY_BULK_UPDATE:
                        errorMessage =
                                "Failed due to invalid Parking Space details.";
                        break;

                    case GENERAL:
                        errorMessage =
                                operationStatus.getException().getMessage();
                        break;

                    default:
                        errorMessage =
                                "Failed to save details due to unknown reasons.";
                        break;

                    }

                    setInlineMessageText(errorMessage);
                    setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);

                }
            }

        } else {
            setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);

        }

        getMeterScheduleTable().getSelectedRowKeys().clear();
        resetAllMeterScheduleTableButtons();
        addPartialTarget(getMeterScheduleTable());

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
     * @return
     */
    private boolean areValidMeterSchedules(List<MeterOPScheduleDTO> meterSchedules,
                                           ParkingSpaceInventoryBulkDTO bulkDTO) {

        if (meterSchedules == null || meterSchedules.isEmpty()) {
            // Nothing to validate
            return true;
        }


        for (int i = 0; i < meterSchedules.size(); i++) {

            MeterOPScheduleDTO iDTO = meterSchedules.get(i);

            // For each row, To date should be after From date
            if (iDTO.getEffectiveToDate().before(iDTO.getEffectiveFromDate())) {
                setInlineMessageText("TO date should be after FROM date in row " +
                                     (i + 1));
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
                    setInlineMessageText("TO time can be zero only under certain conditions in row " +
                                         (i + 1));
                    return false;

                }
            } else if (fromTime >= toTime) {
                setInlineMessageText("TO time should be after FROM time in row " +
                                     (i + 1));
                return false;

            }

            // For each row,
            //    ---If SCHED_TYPE = OP
            //       ---If Cap Color is being updated
            //          ---COLOR_RULE_APPLIED should be same as CAP_COLOR
            //       ---Else
            //          ---COLOR_RULE_APPLIED should be NULL
            if (iDTO.getScheduleType().isScheduleOP()) {
                if (bulkDTO.isToBeUpdatedCapColor()) {
                    if (!StringUtil.areEqual(iDTO.getColorRuleApplied(),
                                             bulkDTO.getCapColor())) {

                        setInlineMessageText("OP Schedule should have same Cap Color. Delete row " +
                                             (i + 1));

                        return false;
                    }
                } else {
                    if (StringUtil.isNotBlank(iDTO.getColorRuleApplied())) {

                        setInlineMessageText("OP Schedule should not have a Color Rule Applied as Cap Color is not being updated. Delete row " +
                                             (i + 1));

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
                                         jDTO.getEffectiveToDate())) {

                    setInlineMessageText("Rows " + (i + 1) + " and " +
                                         (j + 1) +
                                         " have same unique constraint values");
                    return false;
                }
            }

        }

        return true;
    }

    private boolean disableComponent(MeterScheduleType scheduleType) {

        RichTable table = getMeterScheduleTable();

        if (table == null) {
            // Not yet initialised, so nothing to check
            return false;
        }

        List<MeterOPScheduleDTO> meterSchedules =
            (List<MeterOPScheduleDTO>)table.getValue();

        return containsScheduleType(meterSchedules, scheduleType);
    }

    private boolean containsScheduleType(List<MeterOPScheduleDTO> meterSchedules,
                                         MeterScheduleType scheduleType) {

        if (meterSchedules == null || meterSchedules.isEmpty()) {
            return false;
        }

        for (MeterOPScheduleDTO DTO : meterSchedules) {
            if (DTO.getScheduleType().equals(scheduleType)) {
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

        if (getSelectAllSchedButton() != null) {
            getSelectAllSchedButton().setDisabled(!disable);
            addPartialTarget(getSelectAllSchedButton());
        }

        if (getUnselectAllSchedButton() != null) {
            getUnselectAllSchedButton().setDisabled(disable);
            addPartialTarget(getUnselectAllSchedButton());
        }
    }

    private void updateAllMeterDetails() {
        addPartialTarget(getMeterVendorIT());
        addPartialTarget(getMeterModelIT());
        addPartialTarget(getMeterTypeIT());
        addPartialTarget(getSmartMeterIT());
        addPartialTarget(getMsPayStationIDIT());
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

    public void setSelectAllSchedButton(RichCommandButton selectAllSchedButton) {
        this.selectAllSchedButton = selectAllSchedButton;
    }

    public RichCommandButton getSelectAllSchedButton() {
        return selectAllSchedButton;
    }

    public void setUnselectAllSchedButton(RichCommandButton unselectAllSchedButton) {
        this.unselectAllSchedButton = unselectAllSchedButton;
    }

    public RichCommandButton getUnselectAllSchedButton() {
        return unselectAllSchedButton;
    }

    public void setMeterScheduleTable(RichTable meterScheduleTable) {
        this.meterScheduleTable = meterScheduleTable;
    }

    public RichTable getMeterScheduleTable() {
        return meterScheduleTable;
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
}
