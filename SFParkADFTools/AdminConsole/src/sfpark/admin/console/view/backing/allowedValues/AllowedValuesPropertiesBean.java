package sfpark.admin.console.view.backing.allowedValues;

import javax.faces.event.ActionEvent;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.backing.BaseBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;

public class AllowedValuesPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                     RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public AllowedValuesPropertiesBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.SELECTED_ALLOWED_VALUES_DTO.getKey());
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

    public AllowedValuesDTO getAllowedValuesDTO() {
        AllowedValuesDTO DTO =
            (AllowedValuesDTO)getPageFlowScopeValue(PageFlowScopeKey.SELECTED_ALLOWED_VALUES_DTO.getKey());

        if (DTO == null) {
            DTO = new AllowedValuesDTO();
            setPageFlowScopeValue(PageFlowScopeKey.SELECTED_ALLOWED_VALUES_DTO.getKey(),
                                  DTO);
        }

        return DTO;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL READ-ONLY INFORMATION

    public boolean isReadOnly() {
        return (getCurrentPageMode().isReadOnlyMode() ||
                getCurrentPageMode().isShowDetailsMode());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    public boolean isRenderDeleteButton() {
        return (getCurrentPageMode().isShowDetailsMode());
    }

    public boolean isRenderPassiveInfo() {
        return (getCurrentPageMode().isReadOnlyMode() ||
                getCurrentPageMode().isShowDetailsMode());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
        // TODO
    }

    public void deleteButtonHandler(ActionEvent event) {
        // TODO
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {
        clearPageFlowScopeCache();

        setCurrentPageMode(NavigationMode.READ_ONLY);
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.ALLOWED_VALUES_LIST.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

}

/*
    //*
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
     //
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

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    //*
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
     //
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



    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

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
            printLog("All entries are Valid. Proceed");

            if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                printLog("EDIT Mode");

                List<BlockTimeBandsDTO> blockTimeBandsDTOs =
                    (List<BlockTimeBandsDTO>)getBlockTimeBandsTable().getValue();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.editBlockTimeBands(blockTimeBandsDTOs);

                if (operationStatus.isSuccess()) {
                    printLog("EDIT operation was successful");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                    setCurrentPageMode(NavigationMode.EDIT);

                    clearPageFlowScopeCache();

                    getBlockTimeBandsTable().setValue(null);

                } else {
                    printLog("EDIT operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_edit_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }
            }

        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }

    }
 */