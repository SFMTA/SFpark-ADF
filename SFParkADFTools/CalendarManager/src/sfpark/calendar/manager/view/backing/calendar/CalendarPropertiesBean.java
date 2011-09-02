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
import sfpark.adf.tools.model.exception.ExceptionType;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dto.CalendarHeaderDTOStatus;
import sfpark.adf.tools.model.provider.CalendarHeaderProvider;

import sfpark.adf.tools.translation.CalendarManagerBundleKey;
import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.utilities.generic.StringUtil;
import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.calendar.manager.application.key.PageFlowScopeKey;
import sfpark.calendar.manager.application.key.SessionScopeKey;
import sfpark.calendar.manager.view.backing.BaseBean;
import sfpark.calendar.manager.view.flow.NavigationFlow;
import sfpark.calendar.manager.view.flow.NavigationMode;
import sfpark.calendar.manager.view.provider.DMLOperationsProvider;
import sfpark.calendar.manager.view.provider.helper.CalendarDetailDDO;
import sfpark.calendar.manager.view.provider.helper.CalendarDetailOperationType;

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

            DTO = DMLOperationsProvider.INSTANCE.getNewCalendarHeaderDTO();
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
    // ALL READ-ONLY INFORMATION

    public boolean isReadOnly() {
        return (getCurrentPageMode().isReadOnlyMode());
    }

    public boolean isReadOnlyCalendarType() {
        return (!getCurrentPageMode().isAddMode());
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
    // EVENT HANDLERS

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Common Validity Tests:
     * =====================
     *    1. Calendar Name for the given Calendar Type should NOT exist
     *
     * Edit Mode Steps:
     * ===============
     *    1. Calender Detail Table should NOT be empty
     *    Check for Meter Schedule validations
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

        CalendarHeaderDTO calendarHeaderDTO = getCalendarHeaderDTO();

        boolean checkCalendarNameUniqueness = false;

        if (currentPageMode.isAddMode()) {
            checkCalendarNameUniqueness = true;

        } else if (currentPageMode.isEditMode()) {
            CalendarHeaderDTO originalCalendarHeaderDTO =
                CalendarHeaderProvider.INSTANCE.checkForCalendarID(calendarHeaderDTO.getCalendarID()).getDTO();

            checkCalendarNameUniqueness =
                    !StringUtil.areEqual(originalCalendarHeaderDTO.getCalendarName(),
                                         calendarHeaderDTO.getCalendarName());
        }

        printLog("Check for Calendar Name = " + checkCalendarNameUniqueness);

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid && checkCalendarNameUniqueness) {
            CalendarHeaderDTOStatus calendarHeaderStatus =
                CalendarHeaderProvider.INSTANCE.checkForCalendarNameAndType(calendarHeaderDTO.getCalendarName(),
                                                                            calendarHeaderDTO.getCalendarType());

            if (calendarHeaderStatus.existsDTO()) {
                allValid = false;
                setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_exists_already_calendar_name));
            }
        }

        printLog("After Calendar Name check = " + allValid);

        if (allValid && currentPageMode.isEditMode()) {
            printLog("Under EDIT mode, test to see that Calendar Detail Table is NOT empty");

            allValid =
                    areValidCalendarDetails((List<CalendarDetailDDO>)getCalendarDetailTable().getValue());
        }

        printLog("After Calendar Table = " + allValid);

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

            if (currentPageMode.isAddMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ADD Mode
                printLog("ADD Mode");

                CalendarHeaderDTO currentDTO = getCalendarHeaderDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addCalendar(currentDTO);

                if (operationStatus.isSuccess()) {
                    CalendarHeaderDTOStatus calendarHeaderStatus =
                        CalendarHeaderProvider.INSTANCE.checkForCalendarNameAndType(currentDTO.getCalendarName(),
                                                                                    currentDTO.getCalendarType());

                    if (calendarHeaderStatus.existsDTO()) {
                        printLog("ADD operation was successful");
                        setInlineMessageText(TranslationUtil.getCalendarManagerBundleString(CalendarManagerBundleKey.info_create_success));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                        clearPageFlowScopeCache();
                        setCurrentPageMode(NavigationMode.EDIT);
                        setPageFlowScopeValue(PageFlowScopeKey.CALENDAR_HEADER_DTO.getKey(),
                                              calendarHeaderStatus.getDTO());

                    } else {
                        printLog("ADD operation failed");
                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_calendar_name_failure));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    }
                } else {
                    printLog("ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_calendar_name_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }

            } else if (currentPageMode.isEditMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // EDIT Mode
                printLog("EDIT Mode");

                CalendarHeaderDTO currentDTO = getCalendarHeaderDTO();

                List<CalendarDetailDDO> calendarDetails =
                    (List<CalendarDetailDDO>)getCalendarDetailTable().getValue();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.editCalendar(currentDTO,
                                                                calendarDetails);

                if (operationStatus == null) {
                    printLog("There were no changes. So nothing was saved");
                    setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_nothing_to_save));
                    setInlineMessageClass("");
                } else {

                    if (operationStatus.isSuccess()) {
                        printLog("EDIT operation was successful");
                        setInlineMessageText(TranslationUtil.getCommonBundleString(CommonBundleKey.info_success_save));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_SUCCESS);

                        CalendarHeaderDTOStatus calendarHeaderStatus =
                            CalendarHeaderProvider.INSTANCE.checkForCalendarID(currentDTO.getCalendarID());

                        setCurrentPageMode(NavigationMode.EDIT);
                        setPageFlowScopeValue(PageFlowScopeKey.CALENDAR_HEADER_DTO.getKey(),
                                              calendarHeaderStatus.getDTO());

                        clearPageFlowScopeCache();
                        getCalendarDetailTable().setValue(null);

                    } else {
                        printLog("EDIT operation failed");

                        String errorMessage = "";

                        switch (ExceptionType.getExceptionType(operationStatus.getException())) {

                        case UNIQUE_CONTRAINT:
                            {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_calendar_exception_unique_constraint);
                            }
                            break;

                        case CALENDAR_HEADER_UPDATE:
                            {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_calendar_exception_calendar_header_update);
                            }
                            break;

                        case CALENDAR_DETAIL_INSERT:
                            {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_calendar_exception_calendar_detail_insert);
                            }
                            break;

                        case CALENDAR_DETAIL_DELETE:
                            {
                                errorMessage =
                                        TranslationUtil.getErrorBundleString(ErrorBundleKey.error_calendar_exception_calendar_detail_delete);
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
        //
        // Reuse as Undelete button
        //
        if (getCalendarDetailTable().getSelectedRowKeys().size() == 1) {
            CalendarDetailDDO DDO =
                (CalendarDetailDDO)getCalendarDetailTable().getSelectedRowData();

            if (DDO.isNewDate()) {
                // Not possible
                DDO.setOperationType(CalendarDetailOperationType.INSERT);
            } else {
                DDO.setOperationType(CalendarDetailOperationType.NO_OP);
            }
        }

        getCalendarDetailTable().getSelectedRowKeys().clear();
        resetAllCalendarDetailTableButtons();

    }

    public void deleteButtonHandler(ActionEvent event) {

        if (getCalendarDetailTable().getSelectedRowKeys().size() == 1) {
            CalendarDetailDDO DDO =
                (CalendarDetailDDO)getCalendarDetailTable().getSelectedRowData();

            if (DDO.isNewDate()) {
                ((List<CalendarDetailDDO>)getCalendarDetailTable().getValue()).remove(DDO);
            } else {
                DDO.setOperationType(CalendarDetailOperationType.DELETE);
            }
        }

        getCalendarDetailTable().getSelectedRowKeys().clear();
        resetAllCalendarDetailTableButtons();

    }

    public void selectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {

        boolean disableDelete, disableUndelete;

        if (getCalendarDetailTable().getSelectedRowKeys().size() == 1) {
            CalendarDetailDDO DDO =
                (CalendarDetailDDO)getCalendarDetailTable().getSelectedRowData();

            disableDelete = DDO.getOperationType().isDelete();
            disableUndelete = !DDO.getOperationType().isDelete();
        } else {
            disableDelete = true;
            disableUndelete = true;
        }

        // Update Delete button
        getDeleteDateButton().setDisabled(disableDelete);
        addPartialTarget(getDeleteDateButton());

        // Update Undelete button
        getUndeleteDateButton().setDisabled(disableUndelete);
        addPartialTarget(getUndeleteDateButton());

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    /**
     * Validates the Calendar Details and returns true if all entries are valid
     *
     * Validity Tests
     * ==============
     *    1. Should NOT be empty
     *    2. If dates are present, NOT all of them should be "To be deleted"
     *
     * @param calendarDetails
     * @return
     */
    private boolean areValidCalendarDetails(List<CalendarDetailDDO> calendarDetails) {

        if (calendarDetails != null && !calendarDetails.isEmpty()) {
            for (CalendarDetailDDO DDO : calendarDetails) {
                if (!DDO.getOperationType().isDelete()) {

                    return true;
                }
            }
        }

        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_empty_calendar_detail_table));

        return false;
    }

    private void refreshTablesAfterClickingSave() {
        resetAllCalendarDetailTableButtons();

        getCalendarDetailTable().getSelectedRowKeys().clear();
        addPartialTarget(getCalendarDetailTable());
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

    private void printLog(String message) {
        System.out.println(message);
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
