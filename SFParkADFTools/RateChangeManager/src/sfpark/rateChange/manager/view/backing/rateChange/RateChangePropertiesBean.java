package sfpark.rateChange.manager.view.backing.rateChange;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;
import sfpark.rateChange.manager.view.provider.DMLOperationsProvider;

public class RateChangePropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                  RequestScopeBeanInterface {

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
        // TODO
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

        RateChangeHeaderDTO DTO =
            (RateChangeHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey());

        if (DTO == null) {
            DTO = DMLOperationsProvider.INSTANCE.getNewRateChangeHeaderDTO();
            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                  DTO);
        }

        return DTO;
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
        return getCurrentPageMode().isReadOnlyMode();
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
    // LIST VALUES

    public List<SelectItem> getListRateChgPolicy() {
        List<SelectItem> listRateChgPolicy = new ArrayList<SelectItem>();

        for (AllowedValuesDTO allowedValuesDTO :
             AllowedValuesProvider.getRateChgPolicyList()) {
            listRateChgPolicy.add(new SelectItem(allowedValuesDTO.getColumnValue(),
                                                 allowedValuesDTO.getDescription()));
        }

        return listRateChgPolicy;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALUE CHANGE HANDLERS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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

    public void saveButtonHandler(ActionEvent event) {

        /*
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

         */
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

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public Date getMinimumAllowedDate() {
        return SQLDateUtil.getTodaysDate();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

}

/*
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



    private void updateAllMeterDetails() {
        addPartialTarget(getMeterVendorIT());
        addPartialTarget(getMeterModelIT());
        addPartialTarget(getMeterTypeIT());
        addPartialTarget(getSmartMeterIT());
        addPartialTarget(getMsPayStationIDIT());
        addPartialTarget(getMsNumberIT());
    }



 */