package sfpark.osp.manager.view.backing.offStreetParkingManagement;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import org.apache.myfaces.trinidad.model.RowKeySet;

import org.apache.myfaces.trinidad.model.RowKeySetImpl;

import sfpark.adf.tools.model.data.dto.garageOPHours.GarageOPHoursDTO;
import sfpark.adf.tools.model.data.dto.garageRates.GarageRatesDTO;
import sfpark.adf.tools.model.data.dto.ospInventory.OSPInventoryDTO;

import sfpark.adf.tools.model.data.helper.GarageRatesDisplayGroup;
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.helper.dto.OSPInventoryDTOStatus;
import sfpark.adf.tools.model.provider.GarageOPHoursProvider;
import sfpark.adf.tools.model.provider.GarageRatesProvider;

import sfpark.adf.tools.model.provider.OSPInventoryProvider;
import sfpark.adf.tools.model.status.OperationStatus;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;

import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.adf.tools.utilities.ui.DayUI;

import sfpark.adf.tools.utilities.ui.TimeUI;

import sfpark.osp.manager.application.key.PageFlowScopeKey;
import sfpark.osp.manager.view.backing.BaseBean;
import sfpark.osp.manager.view.backing.BaseBeanInterface;
import sfpark.osp.manager.view.backing.helper.ListBeanInterface;
import sfpark.osp.manager.view.backing.helper.PropertiesBeanInterface;
import sfpark.osp.manager.view.flow.NavigationMode;
import sfpark.osp.manager.view.provider.DMLOperationsProvider;
import sfpark.osp.manager.view.util.ADFUIDisplayUtil;

public class OffStreetParkingManagementBean extends BaseBean implements BaseBeanInterface,
                                                                        ListBeanInterface,
                                                                        PropertiesBeanInterface {

    private static final String RATES = "Rates";
    private static final String OP_HOURS = "OPHours";

    private RichTable activeGarageRatesTable;
    private RichTable historicGarageRatesTable;
    private RichCommandButton deleteActiveGarageRatesButton;
    private RichCommandButton selectAllActiveGarageRatesButton;
    private RichCommandButton unselectAllActiveGarageRatesButton;

    private RichTable activeGarageOPHoursTable;
    private RichTable historicGarageOPHoursTable;
    private RichCommandButton deleteActiveGarageOPHoursButton;
    private RichCommandButton selectAllActiveGarageOPHoursButton;
    private RichCommandButton unselectAllActiveGarageOPHoursButton;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public OffStreetParkingManagementBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static OffStreetParkingManagementBean getInstance() {
        return (OffStreetParkingManagementBean)getCurrentInstanceFor("offStreetParkingManagementBean");
    }

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.HISTORIC_GARAGE_RATES_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_RATES_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.HISTORIC_GARAGE_OP_HOURS_LIST.getKey());
        removePageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_OP_HOURS_LIST.getKey());
    }

    /*
  public void refreshTable() {
      if (getMeterScheduleTable() != null) {

          clearPageFlowScopeCache();
          getMeterScheduleTable().getSelectedRowKeys().clear();
          getMeterScheduleTable().setSelectedRowKeys(null);
          resetAllMeterScheduleTableButtons();

          getMeterScheduleTable().setValue(null);
          addPartialTarget(getMeterScheduleTable());
      }
  }
   */

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public OSPInventoryDTO getCurrentOSPInventoryDTO() {
        OSPInventoryDTO DTO =
            (OSPInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.OSP_INVENTORY_DTO.getKey());

        if (DTO == null) {
            // This is an invalid situation. This will occur if the Navigation Flow is broken.
            DTO = new OSPInventoryDTO();
            // TODO: replace with something like DMLOperationsProvider.INSTANCE.getEmptyDTO()

            setPageFlowScopeValue(PageFlowScopeKey.OSP_INVENTORY_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    public List<GarageRatesDTO> getHistoricGarageRates() {
        List<GarageRatesDTO> historicGarageRatesDTOs =
            (List<GarageRatesDTO>)getPageFlowScopeValue(PageFlowScopeKey.HISTORIC_GARAGE_RATES_LIST.getKey());

        if (historicGarageRatesDTOs == null) {
            historicGarageRatesDTOs =
                    GarageRatesProvider.INSTANCE.getHistoricGarageRatesDTOs(getCurrentOSPInventoryDTO().getOSPID());
            setPageFlowScopeValue(PageFlowScopeKey.HISTORIC_GARAGE_RATES_LIST.getKey(),
                                  historicGarageRatesDTOs);
        }

        return historicGarageRatesDTOs;
    }

    public List<GarageRatesDTO> getActiveGarageRates() {
        List<GarageRatesDTO> activeGarageRatesDTOs =
            (List<GarageRatesDTO>)getPageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_RATES_LIST.getKey());

        if (activeGarageRatesDTOs == null) {
            activeGarageRatesDTOs =
                    GarageRatesProvider.INSTANCE.getActiveGarageRatesDTOs(getCurrentOSPInventoryDTO().getOSPID());
            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_RATES_LIST.getKey(),
                                  activeGarageRatesDTOs);
        }

        return activeGarageRatesDTOs;
    }

    public List<GarageOPHoursDTO> getHistoricGarageOPHours() {
        List<GarageOPHoursDTO> historicGarageOPHoursDTOs =
            (List<GarageOPHoursDTO>)getPageFlowScopeValue(PageFlowScopeKey.HISTORIC_GARAGE_OP_HOURS_LIST.getKey());

        if (historicGarageOPHoursDTOs == null) {
            historicGarageOPHoursDTOs =
                    GarageOPHoursProvider.INSTANCE.getHistoricGarageOPHoursDTOs(getCurrentOSPInventoryDTO().getOSPID());
            setPageFlowScopeValue(PageFlowScopeKey.HISTORIC_GARAGE_OP_HOURS_LIST.getKey(),
                                  historicGarageOPHoursDTOs);
        }

        return historicGarageOPHoursDTOs;
    }

    public List<GarageOPHoursDTO> getActiveGarageOPHours() {
        List<GarageOPHoursDTO> activeGarageOPHoursDTOs =
            (List<GarageOPHoursDTO>)getPageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_OP_HOURS_LIST.getKey());

        if (activeGarageOPHoursDTOs == null) {
            activeGarageOPHoursDTOs =
                    GarageOPHoursProvider.INSTANCE.getActiveGarageOPHoursDTOs(getCurrentOSPInventoryDTO().getOSPID());
            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_OP_HOURS_LIST.getKey(),
                                  activeGarageOPHoursDTOs);
        }

        return activeGarageOPHoursDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    public boolean isRenderGarageOPHoursPanel() {
        return (getCurrentPageMode().isEditMode() &&
                !getCurrentOSPInventoryDTO().isMetered());
    }

    public boolean isRenderGarageRatesPanel() {
        return (getCurrentPageMode().isEditMode() &&
                !getCurrentOSPInventoryDTO().isMetered());
    }

    public boolean isRenderPassiveInfo() {
        return (getCurrentPageMode().isEditMode());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    public boolean isDisableVehicleEntryIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableVehicleExitIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableMotorcycleEntryIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableMotorcycleExitIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableActivationFeeIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableCardReplaceFeeIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableLateFeeIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableReopenFeeIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableNoKeyValetFeeIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisablePhoneIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableServicesSMC() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableWebsiteIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    public boolean isDisableSystemIT() {
        return getCurrentOSPInventoryDTO().isMetered();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListFacilityType() {
        return ADFUIDisplayUtil.FACILITY_TYPE_LIST;
    }

    public List<SelectItem> getListOwner() {
        return ADFUIDisplayUtil.OWNER_LIST;
    }

    public List<SelectItem> getListDataFeedFlag() {
        return ADFUIDisplayUtil.DATA_FEED_FLAG_LIST;
    }

    public List<SelectItem> getListServices() {
        return ADFUIDisplayUtil.SERVICES_LIST;
    }

    public List<SelectItem> getListDisplayCode() {
        return ADFUIDisplayUtil.DISPLAY_CODE_LIST;
    }

    public List<SelectItem> getListTimeBand() {
        return ADFUIDisplayUtil.TIME_BAND_LIST;
    }

    public List<SelectItem> getListRateQualifier() {
        return ADFUIDisplayUtil.RATE_QUALIFIER_LIST;
    }

    public List<SelectItem> getListFromDay() {
        return DayUI.FROM_DAY_LIST;
    }

    public List<SelectItem> getListToDay() {
        return DayUI.TO_DAY_LIST;
    }
    
    public List<SelectItem> getListFromTime() {
        return TimeUI.FROM_TIME_LIST;
    }

    public List<SelectItem> getListToTime() {
        return TimeUI.TO_TIME_LIST;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void addButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains(RATES)) {

            GarageRatesDisplayGroup displayGroup =
                GarageRatesDisplayGroup.HOURLY_RATES;
            if (ID.contains("3")) {
                displayGroup = GarageRatesDisplayGroup.MONTHLY_RATES;
            } else if (ID.contains("2")) {
                displayGroup = GarageRatesDisplayGroup.FLAT_RATES_OR_DISCOUNTS;
            } else {
                displayGroup = GarageRatesDisplayGroup.HOURLY_RATES;
            }

            List<GarageRatesDTO> garageRates =
                (List<GarageRatesDTO>)getActiveGarageRatesTable().getValue();

            GarageRatesDTO garageRatesDTO =
                DMLOperationsProvider.INSTANCE.getNewGarageRatesDTO(displayGroup,
                                                                    getCurrentOSPInventoryDTO().getOSPID(),
                                                                    getNextDisplaySequenceInt(garageRates,
                                                                                              displayGroup));

            garageRates.add(0, garageRatesDTO);

            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_RATES_LIST.getKey(),
                                  garageRates);

            getActiveGarageRatesTable().getSelectedRowKeys().clear();
            resetAllActiveGarageRatesTableButtons();
            getActiveGarageRatesTable().setValue(null);
            addPartialTarget(getActiveGarageRatesTable());

        } else if (ID.contains(OP_HOURS)) {
            GarageOPHoursDTO garageOPHoursDTO =
                DMLOperationsProvider.INSTANCE.getNewGarageOPHoursDTO(getCurrentOSPInventoryDTO().getOSPID(),
                                                                      1);

            List<GarageOPHoursDTO> garageOPHours =
                (List<GarageOPHoursDTO>)getActiveGarageOPHoursTable().getValue();

            garageOPHours.add(0, garageOPHoursDTO);

            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_OP_HOURS_LIST.getKey(),
                                  garageOPHours);

            getActiveGarageOPHoursTable().getSelectedRowKeys().clear();
            resetAllActiveGarageOPHoursTableButtons();
            getActiveGarageOPHoursTable().setValue(null);
            addPartialTarget(getActiveGarageOPHoursTable());

        }
    }

    public void editButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void deleteButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains(RATES)) {
            RichTable table = getActiveGarageRatesTable();
            RowKeySet rowKeySet = table.getSelectedRowKeys();
            int selectedNumOfRows = rowKeySet.getSize();

            if (selectedNumOfRows > 0) {

                List<GarageRatesDTO> tempList =
                    new ArrayList<GarageRatesDTO>();

                for (Object rowKey : rowKeySet) {
                    table.setRowKey(rowKey);

                    GarageRatesDTO dto = (GarageRatesDTO)table.getRowData();

                    if (dto != null) {
                        if (dto.isNewRecord()) {
                            tempList.add(dto);
                        } else {
                            java.sql.Date newToDate =
                                SQLDateUtil.getYesterdaysDate();
                            java.sql.Date fromDate =
                                dto.getEffectiveFromDate();

                            if (newToDate.before(fromDate)) {
                                newToDate = fromDate;
                            }

                            dto.setEffectiveToDate(newToDate);
                        }
                    }
                }

                List<GarageRatesDTO> garageRatesDTOs =
                    (List<GarageRatesDTO>)table.getValue();

                garageRatesDTOs.removeAll(tempList);

                setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_RATES_LIST.getKey(),
                                      garageRatesDTOs);

                table.getSelectedRowKeys().clear();
                resetAllActiveGarageRatesTableButtons();
                table.setValue(null);
                addPartialTarget(table);
            }

        } else if (ID.contains(OP_HOURS)) {
            RichTable table = getActiveGarageOPHoursTable();
            RowKeySet rowKeySet = table.getSelectedRowKeys();
            int selectedNumOfRows = rowKeySet.getSize();

            if (selectedNumOfRows > 0) {
                List<GarageOPHoursDTO> tempList =
                    new ArrayList<GarageOPHoursDTO>();

                for (Object rowKey : rowKeySet) {
                    table.setRowKey(rowKey);

                    GarageOPHoursDTO dto =
                        (GarageOPHoursDTO)table.getRowData();

                    if (dto != null) {
                        if (dto.isNewRecord()) {
                            tempList.add(dto);
                        } else {
                            java.sql.Date newToDate =
                                SQLDateUtil.getYesterdaysDate();
                            java.sql.Date fromDate =
                                dto.getEffectiveFromDate();

                            if (newToDate.before(fromDate)) {
                                newToDate = fromDate;
                            }

                            dto.setEffectiveToDate(newToDate);
                        }
                    }
                }

                List<GarageOPHoursDTO> garageOPHoursDTOs =
                    (List<GarageOPHoursDTO>)table.getValue();

                garageOPHoursDTOs.removeAll(tempList);

                setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_GARAGE_OP_HOURS_LIST.getKey(),
                                      garageOPHoursDTOs);

                table.getSelectedRowKeys().clear();
                resetAllActiveGarageOPHoursTableButtons();
                table.setValue(null);
                addPartialTarget(table);
            }
        }
    }

    public void selectAllButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains(RATES)) {
            RichTable table = getActiveGarageRatesTable();

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

                setAllActiveGarageRatesTableButtons();
            }

        } else if (ID.contains(OP_HOURS)) {
            RichTable table = getActiveGarageOPHoursTable();

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

                setAllActiveGarageOPHoursTableButtons();
            }
        }
    }

    public void unselectAllButtonHandler(ActionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains(RATES)) {

            getActiveGarageRatesTable().getSelectedRowKeys().clear();
            addPartialTarget(getActiveGarageRatesTable());
            resetAllActiveGarageRatesTableButtons();

        } else if (ID.contains(OP_HOURS)) {

            getActiveGarageOPHoursTable().getSelectedRowKeys().clear();
            addPartialTarget(getActiveGarageOPHoursTable());
            resetAllActiveGarageOPHoursTableButtons();

        }
    }

    public void tableRowSelectionHandler(SelectionEvent event) {

        String ID = event.getComponent().getId();

        if (ID.contains("GRTable")) {
            RichTable table = getActiveGarageRatesTable();

            int selectedNumOfRows = table.getSelectedRowKeys().getSize();

            if (selectedNumOfRows > 0) {
                setAllActiveGarageRatesTableButtons();
                getSelectAllActiveGarageRatesButton().setDisabled(selectedNumOfRows ==
                                                                  table.getRowCount());

            } else {
                resetAllActiveGarageRatesTableButtons();
            }

        } else if (ID.contains("GOHTabl")) {
            RichTable table = getActiveGarageOPHoursTable();

            int selectedNumOfRows = table.getSelectedRowKeys().getSize();

            if (selectedNumOfRows > 0) {
                setAllActiveGarageOPHoursTableButtons();
                getSelectAllActiveGarageOPHoursButton().setDisabled(selectedNumOfRows ==
                                                                    table.getRowCount());

            } else {
                resetAllActiveGarageOPHoursTableButtons();
            }

        }
    }

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Edit Mode Steps:
     * ===============
     *    1. Check for Garage Rates validations
     *    2. Check for Garage OP Hours validations
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {

        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        OSPInventoryDTO OSPInventoryDTO = getCurrentOSPInventoryDTO();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid && currentPageMode.isEditMode() &&
            !OSPInventoryDTO.isMetered()) {
            // For Unmetered, test for Rates
            List<GarageRatesDTO> DTOs =
                (List<GarageRatesDTO>)getActiveGarageRatesTable().getValue();

            allValid = areValidGarageRates(DTOs);
        }

        if (allValid && currentPageMode.isEditMode() &&
            !OSPInventoryDTO.isMetered()) {
            // For Unmetered, test for OP Hours
            List<GarageOPHoursDTO> DTOs =
                (List<GarageOPHoursDTO>)getActiveGarageOPHoursTable().getValue();

            allValid = areValidGarageOPHours(DTOs);
        }

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        //        allValid = false;
        //        String tttt = StringUtil.isBlank(getInlineMessageText()) ? "All are valid" : getInlineMessageText();
        //        setInlineMessageText(tttt);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            // LOGGER.debug("All entries are Valid. Proceed");

            if (currentPageMode.isAddMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ADD Mode
                // Not implemented

            } else if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode

                OSPInventoryDTO currentOSPInventoryDTO =
                    getCurrentOSPInventoryDTO();

                OperationStatus operationStatus = null;

                if (currentOSPInventoryDTO.isMetered()) {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // Metered OSP
                    // Update ONLY OSP Inventory

                    operationStatus =
                            DMLOperationsProvider.INSTANCE.editMeteredOSP(currentOSPInventoryDTO);

                } else {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // Unmetered OSP
                    // Update OSP Inventory, Garage Rates and Garage OP Hours

                    operationStatus =
                            DMLOperationsProvider.INSTANCE.editUnMeteredOSP(currentOSPInventoryDTO,
                                                                            (List<GarageRatesDTO>)getActiveGarageRatesTable().getValue(),
                                                                            (List<GarageOPHoursDTO>)getActiveGarageOPHoursTable().getValue());

                }

                if (operationStatus == null) {
                    setInlineMessageText("There were no changes. So nothing was saved.");
                    setInlineMessageClass("");

                } else {
                    if (operationStatus.getType().isSuccess()) {
                        // System.out.println("EDIT operation was successful");
                        setInlineMessageText("Successfully saved all the details.");
                        setInlineMessageClass(OperationStatus.STYLECLASS_SUCCESSFUL);

                        OSPInventoryDTOStatus ospStatus =
                            OSPInventoryProvider.INSTANCE.checkForOSP(currentOSPInventoryDTO.getOSPID());

                        // this.ospInventoryDTO = null;
                        setCurrentPageMode(NavigationMode.EDIT);
                        setPageFlowScopeValue(PageFlowScopeKey.OSP_INVENTORY_DTO.getKey(),
                                              ospStatus.getDTO());

                        clearPageFlowScopeCache();

                        if (!ospStatus.getDTO().isMetered()) {
                            // this.activeGarageRatesDTOs = null;
                            // this.historicGarageRatesDTOs = null;
                            getActiveGarageRatesTable().setValue(null);
                            getHistoricGarageRatesTable().setValue(null);

                            // this.activeGarageOPHoursDTOs = null;
                            // this.historicGarageOPHoursDTOs = null;
                            getActiveGarageOPHoursTable().setValue(null);
                            getHistoricGarageOPHoursTable().setValue(null);
                        }

                    } else {
                        // LOGGER.debug("EDIT operation failed");
                        String errorMessage = "";

                        switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                        case UNIQUE_CONTRAINT:
                            errorMessage =
                                    "Failed due to invalid rates and/or op hours during archiving. Try overriding.";
                            break;

                        case GARAGE_RATES_INSERT:
                            errorMessage =
                                    "Failed due to invalid new rates. Try overriding or removing.";
                            break;

                        case GARAGE_RATES_UPDATE:
                            errorMessage =
                                    "Failed due to invalid existing rates. Try overriding.";
                            break;

                        case GARAGE_OP_HOURS_INSERT:
                            errorMessage =
                                    "Failed due to invalid new op hours. Try overriding or removing.";
                            break;

                        case GARAGE_OP_HOURS_UPDATE:
                            errorMessage =
                                    "Failed due to invalid existing op hours. Try overriding.";
                            break;

                        case OSP_INVENTORY_UPDATE:
                            errorMessage =
                                    "Failed due to invalid OSP details.";
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

                resetAllActiveGarageRatesTableButtons();
                getActiveGarageRatesTable().getSelectedRowKeys().clear();
                addPartialTarget(getActiveGarageRatesTable());

                resetAllActiveGarageOPHoursTableButtons();
                getActiveGarageOPHoursTable().getSelectedRowKeys().clear();
                addPartialTarget(getActiveGarageOPHoursTable());

            }
        } else {
            setInlineMessageClass(OperationStatus.STYLECLASS_FAILURE);

            if (currentPageMode.isEditMode()) {
                resetAllActiveGarageRatesTableButtons();
                getActiveGarageRatesTable().getSelectedRowKeys().clear();
                addPartialTarget(getActiveGarageRatesTable());

                resetAllActiveGarageOPHoursTableButtons();
                getActiveGarageOPHoursTable().getSelectedRowKeys().clear();
                addPartialTarget(getActiveGarageOPHoursTable());
            }
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

    /**
     * Validates the Garage Rates and returns true if all entries are valid
     *
     * Validity Tests:
     * ==============
     *    1. All TO dates should occur after FROM dates
     *    2. Display Group
     *       ---HOURLY_RATES
     *          ---If RATE_DESC is NULL then TIME_BAND should NOT be NULL
     *          ---If RATE_DESC is NOT NULL then TIME_BAND should be NULL
     *       ---else
     *          ---RATE_DESC should NOT be NULL
     *    3. No two rows should be exactly similar
     *
     * @param garageRates
     * @return
     */
    private boolean areValidGarageRates(List<GarageRatesDTO> garageRates) {

        if (garageRates == null || garageRates.isEmpty()) {
            // Nothing to validate
            return true;
        }

        for (int i = 0; i < garageRates.size(); i++) {

            GarageRatesDTO iDTO = garageRates.get(i);

            // For each row, TO date should be after FROM date
            if (iDTO.getEffectiveToDate().before(iDTO.getEffectiveFromDate())) {
                setInlineMessageText("Rates Table: TO date should be after FROM date in row " +
                                     (i + 1));
                return false;
            }

            // For each row,
            // If Display Group is HOURLY_RATES
            //    Perform Exclusive OR operation on RATE_DESC and TIME_BAND
            // Else
            //    RATE_DESC should NOT be NULL
            if (iDTO.getDisplayGroup().isHourlyRates()) {
                if (!(StringUtil.isBlank(iDTO.getRateDescription()) ^
                      iDTO.getTimeBand().isNULL())) {

                    setInlineMessageText("Rates Table: Rate Description and Time Band should alternately be NULL in row " +
                                         (i + 1));
                    return false;
                }
            } else {
                if (StringUtil.isBlank(iDTO.getRateDescription())) {

                    setInlineMessageText("Rates Table: Rate Description cannot be null in row " +
                                         (i + 1));
                    return false;
                }
            }

            // No two rows should be similar
            for (int j = i + 1; j < garageRates.size(); j++) {

                GarageRatesDTO jDTO = garageRates.get(j);

                if ((iDTO.getDisplayGroup() == jDTO.getDisplayGroup()) &&
                    (iDTO.getDisplaySequence() == jDTO.getDisplaySequence()) &&
                    SQLDateUtil.areEqual(iDTO.getEffectiveFromDate(),
                                         jDTO.getEffectiveFromDate())) {

                    setInlineMessageText("Rates Table: Rows " + (i + 1) +
                                         " and " + (j + 1) +
                                         " have same unique constraint values");
                    return false;
                }
            }


        }

        return true;
    }

    /**
     * Validates the Garage OP Hours and returns true if all entries are valid
     *
     * Validity Tests:
     * ==============
     *    1. All TO dates should occur after FROM dates
     *    2. No two rows should be exactly similar
     *
     * @param garageOPHours
     * @return
     */
    private boolean areValidGarageOPHours(List<GarageOPHoursDTO> garageOPHours) {

        if (garageOPHours == null || garageOPHours.isEmpty()) {
            // Nothing to validate
            return true;
        }

        for (int i = 0; i < garageOPHours.size(); i++) {

            GarageOPHoursDTO iDTO = garageOPHours.get(i);

            // For each row, TO date should be after FROM date
            if (iDTO.getEffectiveToDate().before(iDTO.getEffectiveFromDate())) {
                setInlineMessageText("OP Hours Table: TO date should be after FROM date in row " +
                                     (i + 1));
                return false;
            }

            // No two rows should be similar
            for (int j = i + 1; j < garageOPHours.size(); j++) {

                GarageOPHoursDTO jDTO = garageOPHours.get(j);

                if ((iDTO.getScheduleSequence() ==
                     jDTO.getScheduleSequence()) &&
                    SQLDateUtil.areEqual(iDTO.getEffectiveFromDate(),
                                         jDTO.getEffectiveFromDate()) &&
                    StringUtil.areEqual(iDTO.getFromDay(),
                                        jDTO.getFromDay())) {

                    setInlineMessageText("OP Hours Table: Rows " + (i + 1) +
                                         " and " + (j + 1) +
                                         " have same unique constraint values");
                    return false;

                }
            }
        }

        return true;
    }

    private int getNextDisplaySequenceInt(List<GarageRatesDTO> garageRatesDTOs,
                                          GarageRatesDisplayGroup displayGroup) {

        int nextDisplaySequence = 0;

        if (garageRatesDTOs != null && !garageRatesDTOs.isEmpty()) {
            for (GarageRatesDTO DTO : garageRatesDTOs) {
                if (DTO.getDisplayGroup().equals(displayGroup)) {
                    if (DTO.getDisplaySequence() > nextDisplaySequence) {
                        nextDisplaySequence = DTO.getDisplaySequence();
                    }
                }
            }
        }

        return (nextDisplaySequence + 1);
    }

    private void setAllActiveGarageRatesTableButtons() {
        updateAllActiveGarageRatesTableButtons(false);
    }

    private void resetAllActiveGarageRatesTableButtons() {
        updateAllActiveGarageRatesTableButtons(true);
    }

    private void updateAllActiveGarageRatesTableButtons(boolean disable) {

        if (getDeleteActiveGarageRatesButton() != null) {
            getDeleteActiveGarageRatesButton().setDisabled(disable);
            addPartialTarget(getDeleteActiveGarageRatesButton());
        }

        if (getSelectAllActiveGarageRatesButton() != null) {
            getSelectAllActiveGarageRatesButton().setDisabled(!disable);
            addPartialTarget(getSelectAllActiveGarageRatesButton());
        }

        if (getUnselectAllActiveGarageRatesButton() != null) {
            getUnselectAllActiveGarageRatesButton().setDisabled(disable);
            addPartialTarget(getUnselectAllActiveGarageRatesButton());
        }
    }

    private void setAllActiveGarageOPHoursTableButtons() {
        updateAllActiveGarageOPHoursTableButtons(false);
    }

    private void resetAllActiveGarageOPHoursTableButtons() {
        updateAllActiveGarageOPHoursTableButtons(true);
    }

    private void updateAllActiveGarageOPHoursTableButtons(boolean disable) {

        if (getDeleteActiveGarageOPHoursButton() != null) {
            getDeleteActiveGarageOPHoursButton().setDisabled(disable);
            addPartialTarget(getDeleteActiveGarageOPHoursButton());
        }

        if (getSelectAllActiveGarageOPHoursButton() != null) {
            getSelectAllActiveGarageOPHoursButton().setDisabled(!disable);
            addPartialTarget(getSelectAllActiveGarageOPHoursButton());
        }

        if (getUnselectAllActiveGarageOPHoursButton() != null) {
            getUnselectAllActiveGarageOPHoursButton().setDisabled(disable);
            addPartialTarget(getUnselectAllActiveGarageOPHoursButton());
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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setDeleteActiveGarageRatesButton(RichCommandButton deleteActiveGarageRatesButton) {
        this.deleteActiveGarageRatesButton = deleteActiveGarageRatesButton;
    }

    public RichCommandButton getDeleteActiveGarageRatesButton() {
        return deleteActiveGarageRatesButton;
    }

    public void setSelectAllActiveGarageRatesButton(RichCommandButton selectAllActiveGarageRatesButton) {
        this.selectAllActiveGarageRatesButton =
                selectAllActiveGarageRatesButton;
    }

    public RichCommandButton getSelectAllActiveGarageRatesButton() {
        return selectAllActiveGarageRatesButton;
    }

    public void setUnselectAllActiveGarageRatesButton(RichCommandButton unselectAllActiveGarageRatesButton) {
        this.unselectAllActiveGarageRatesButton =
                unselectAllActiveGarageRatesButton;
    }

    public RichCommandButton getUnselectAllActiveGarageRatesButton() {
        return unselectAllActiveGarageRatesButton;
    }

    public void setDeleteActiveGarageOPHoursButton(RichCommandButton deleteActiveGarageOPHoursButton) {
        this.deleteActiveGarageOPHoursButton = deleteActiveGarageOPHoursButton;
    }

    public RichCommandButton getDeleteActiveGarageOPHoursButton() {
        return deleteActiveGarageOPHoursButton;
    }

    public void setSelectAllActiveGarageOPHoursButton(RichCommandButton selectAllActiveGarageOPHoursButton) {
        this.selectAllActiveGarageOPHoursButton =
                selectAllActiveGarageOPHoursButton;
    }

    public RichCommandButton getSelectAllActiveGarageOPHoursButton() {
        return selectAllActiveGarageOPHoursButton;
    }

    public void setUnselectAllActiveGarageOPHoursButton(RichCommandButton unselectAllActiveGarageOPHoursButton) {
        this.unselectAllActiveGarageOPHoursButton =
                unselectAllActiveGarageOPHoursButton;
    }

    public RichCommandButton getUnselectAllActiveGarageOPHoursButton() {
        return unselectAllActiveGarageOPHoursButton;
    }

    public void setActiveGarageRatesTable(RichTable activeGarageRatesTable) {
        this.activeGarageRatesTable = activeGarageRatesTable;
    }

    public RichTable getActiveGarageRatesTable() {
        return activeGarageRatesTable;
    }

    public void setActiveGarageOPHoursTable(RichTable activeGarageOPHoursTable) {
        this.activeGarageOPHoursTable = activeGarageOPHoursTable;
    }

    public RichTable getActiveGarageOPHoursTable() {
        return activeGarageOPHoursTable;
    }

    public void setHistoricGarageRatesTable(RichTable historicGarageRatesTable) {
        this.historicGarageRatesTable = historicGarageRatesTable;
    }

    public RichTable getHistoricGarageRatesTable() {
        return historicGarageRatesTable;
    }

    public void setHistoricGarageOPHoursTable(RichTable historicGarageOPHoursTable) {
        this.historicGarageOPHoursTable = historicGarageOPHoursTable;
    }

    public RichTable getHistoricGarageOPHoursTable() {
        return historicGarageOPHoursTable;
    }
}
