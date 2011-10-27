package sfpark.admin.console.view.backing.allowedValues;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.data.helper.AllowedValuesDeletableFlag;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.dto.AllowedValuesDTOStatus;
import sfpark.adf.tools.model.helper.infoObject.ColumnInfoObjectStatus;
import sfpark.adf.tools.model.helper.infoObject.TableInfoObjectStatus;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.model.provider.InformationObjectRetriever;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.util.TableColumnUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.backing.BaseBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;
import sfpark.admin.console.view.provider.DMLOperationsProvider;

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
    // ALL LIST VALUES

    public List<SelectItem> getListDeletableIfUnused() {
        List<SelectItem> list = new ArrayList<SelectItem>();

        for (AllowedValuesDeletableFlag flag :
             AllowedValuesDeletableFlag.values()) {
            list.add(new SelectItem(flag, flag.getStringForDisplay()));
        }

        return list;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    /**
     * Validates the Form and Saves if all entries are valid
     *
     * Add Mode Steps
     * ==============
     *    1. TableName should exist
     *    2. ColumnName should exist
     *    3. ColumnValue should be compatible
     *    4. Entry should be unique
     *
     * Edit Mode Steps
     * ===============
     *    N/A
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        boolean checkExistance = currentPageMode.isAddMode();
        boolean checkDTOUniqueness = currentPageMode.isAddMode();

        AllowedValuesDTO allowedValuesDTO = getAllowedValuesDTO();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid && checkExistance) {

            TableInfoObjectStatus tableInfoObjectStatus =
                InformationObjectRetriever.checkForTable(allowedValuesDTO.getTableName());

            if (tableInfoObjectStatus.existsInfoObject()) {

                ColumnInfoObjectStatus columnInfoObjectStatus =
                    InformationObjectRetriever.checkForColumn(allowedValuesDTO.getTableName(),
                                                              allowedValuesDTO.getColumnName());

                if (columnInfoObjectStatus.existsInfoObject()) {

                    boolean compatible =
                        TableColumnUtil.isCompatible(columnInfoObjectStatus.getInfoObject(),
                                                     allowedValuesDTO.getColumnValue());

                    if (compatible) {
                        allValid = true;
                    } else {
                        allValid = false;
                        setInlineMessageText("Column Valus is NOT compatible. Column accepts " +
                                             columnInfoObjectStatus.getInfoObject().getTypeName() +
                                             "(" +
                                             columnInfoObjectStatus.getInfoObject().getColumnSize() +
                                             ") values. "); // TODO
                    }

                } else {
                    allValid = false;
                    setInlineMessageText("Column does NOT exist. "); // TODO
                }

            } else {
                allValid = false;
                setInlineMessageText("Table does NOT exist. "); // TODO
            }
        }

        printLog("After Existance check = " + allValid);

        if (allValid && checkDTOUniqueness) {
            AllowedValuesDTOStatus allowedValuesDTOStatus =
                AllowedValuesProvider.INSTANCE.checkForAllowedValuesData(allowedValuesDTO.getTableName(),
                                                                         allowedValuesDTO.getColumnName(),
                                                                         allowedValuesDTO.getColumnValue());

            if (allowedValuesDTOStatus.existsDTO()) {
                allValid = false;
                setInlineMessageText("The value already exists. "); // TODO
            }
        }

        printLog("After DTO check = " + allValid);

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

                AllowedValuesDTO DTO = getAllowedValuesDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addAllowedValue(DTO);

                if (operationStatus.isSuccess()) {
                    removePageFlowScopeValue(PageFlowScopeKey.ALLOWED_VALUES_LIST.getKey());

                    moveOn();
                } else {
                    printLog("ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_create_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }

            } else if (currentPageMode.isShowDetailsMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // SHOW_DETAILS Mode
                printLog("SHOW_DETAILS Mode");

                AllowedValuesDTO DTO = getAllowedValuesDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.editAllowedValue(DTO);

                if (operationStatus == null) {
                    printLog("There were no changes. So nothing was saved");
                    removePageFlowScopeValue(PageFlowScopeKey.ALLOWED_VALUES_LIST.getKey());

                    moveOn();

                } else {
                    if (operationStatus.isSuccess()) {
                        removePageFlowScopeValue(PageFlowScopeKey.ALLOWED_VALUES_LIST.getKey());

                        moveOn();
                    } else {
                        printLog("SHOW_DETAILS operation failed");
                        setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_save_failure));
                        setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                    }

                }

            }

        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }
    }

    /**
     * Validates the Form and Deletes if all conditions are satisfied
     *
     * Steps
     * =====
     *    1. ColumnValue should be unused
     *
     * @param event
     */
    public void deleteButtonHandler(ActionEvent event) {
        String ID = event.getComponent().getId();
        boolean allValid = ID.contains("delete");
        NavigationMode currentPageMode = getCurrentPageMode();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            AllowedValuesDTO DTO = getAllowedValuesDTO();

            long numOfOccurrences =
                AllowedValuesProvider.INSTANCE.getNumberOfOccurrencesFor(DTO);

            if (numOfOccurrences == 0L) {

                if (DTO.getDeletableIfUnused().isDeletable()) {
                    allValid = true;
                } else {
                    allValid = false;
                    setInlineMessageText("Cannot delete as DELETABLE_IF_UNUSED flag is not set. "); // TODO
                }

            } else if (numOfOccurrences < 0L) {
                allValid = false;
                setInlineMessageText("Cannot delete. Contact Administrator. "); // TODO
            } else {
                allValid = false;
                setInlineMessageText("Cannot delete. Used " +
                                     numOfOccurrences + " times. "); // TODO
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
            printLog("All entries are Valid. Proceed");

            if (currentPageMode.isShowDetailsMode()) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // SHOW_DETAILS Mode
                printLog("SHOW_DETAILS Mode");

                AllowedValuesDTO DTO = getAllowedValuesDTO();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.deleteAllowedValue(DTO);

                if (operationStatus.isSuccess()) {
                    removePageFlowScopeValue(PageFlowScopeKey.ALLOWED_VALUES_LIST.getKey());

                    moveOn();
                } else {
                    printLog("SHOW_DETAILS operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_delete_failure));
                    setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
                }

            }

        } else {
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }
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
}
