package sfpark.admin.console.view.backing.allowedValues;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.backing.BaseBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;

public class AllowedValuesListBean extends BaseBean implements RequestScopeBeanInterface {

    private RichTable AllowedValuesTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public AllowedValuesListBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
    }

    public void setInlineMessageText(String inlineMessageText) {
    }

    public String getInlineMessageText() {
        return null;
    }

    public void setInlineMessageClass(String inlineMessageClass) {
    }

    public String getInlineMessageClass() {
        return null;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public List<AllowedValuesDTO> getAllowedValues() {
        List<AllowedValuesDTO> allowedValuesDTOs =
            (List<AllowedValuesDTO>)getPageFlowScopeValue(PageFlowScopeKey.ALLOWED_VALUES_LIST.getKey());

        if (allowedValuesDTOs == null) {
            allowedValuesDTOs =
                    AllowedValuesProvider.INSTANCE.getAllAllowedValuesDTOs();
            setPageFlowScopeValue(PageFlowScopeKey.ALLOWED_VALUES_LIST.getKey(),
                                  allowedValuesDTOs);
        }

        return allowedValuesDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void buttonHandler(ActionEvent event) {
        //
        // Reuse as various buttons
        //

        String ID = event.getComponent().getId();

        if (ID.contains("add")) {
            getAllowedValuesTable().getSelectedRowKeys().clear();

            setCurrentPageMode(NavigationMode.ADD);
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.ALLOWED_VALUES_PROPERTIES.name());
        } else if (ID.contains("show")) {

            if (getAllowedValuesTable().getSelectedRowKeys().size() == 1) {
                // TODO
                // Get the selection
                setCurrentPageMode(NavigationMode.SHOW_DETAILS);
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.ALLOWED_VALUES_PROPERTIES.name());

            }
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setAllowedValuesTable(RichTable AllowedValuesTable) {
        this.AllowedValuesTable = AllowedValuesTable;
    }

    public RichTable getAllowedValuesTable() {
        return AllowedValuesTable;
    }
}
/*
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