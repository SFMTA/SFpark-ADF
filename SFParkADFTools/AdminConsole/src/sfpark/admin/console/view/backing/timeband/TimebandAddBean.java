package sfpark.admin.console.view.backing.timeband;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import sfpark.adf.tools.model.data.dto.timeBandModel.TimeBandModelDTO;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.TimeBandModelProvider;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;
import sfpark.admin.console.view.helper.TimeBandModelWrapper;
import sfpark.admin.console.view.provider.DMLOperationsProvider;

public class TimebandAddBean extends TimebandAbstractBean implements PropertiesBeanInterface,
                                                                     RequestScopeBeanInterface {
    private RichTable InsertTimeBandModelsTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public TimebandAddBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        super.clearPageFlowScopeCache();
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

    public List<TimeBandModelDTO> getInsertableTimeBandModels() {
        List<TimeBandModelDTO> insertableTimeBandModelDTOs =
            (List<TimeBandModelDTO>)getPageFlowScopeValue(PageFlowScopeKey.ADD_TIME_BAND_MODEL_DTO_LIST.getKey());

        if (insertableTimeBandModelDTOs == null) {
            insertableTimeBandModelDTOs = new ArrayList<TimeBandModelDTO>();
            setPageFlowScopeValue(PageFlowScopeKey.ADD_TIME_BAND_MODEL_DTO_LIST.getKey(),
                                  insertableTimeBandModelDTOs);
        }

        return insertableTimeBandModelDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
        boolean allValid = true;
        NavigationMode currentPageMode = getCurrentPageMode();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (allValid) {
            List<TimeBandModelDTO> checkList =
                (List<TimeBandModelDTO>)getInsertTimeBandModelsTable().getValue();

            if (checkList == null || checkList.isEmpty()) {
                allValid = false;
                setInlineMessageText("There are no timebands to save."); // TODO
            }
        }

        printLog("After check list = " + allValid);

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

                List<TimeBandModelDTO> insertableTimeBandModelDTOs =
                    (List<TimeBandModelDTO>)getInsertTimeBandModelsTable().getValue();
                List<TimeBandModelDTO> deletableTimeBandModelDTOs =
                    getDeletableTimeBandModels();

                OperationStatus operationStatus =
                    DMLOperationsProvider.INSTANCE.addTimebands(insertableTimeBandModelDTOs,
                                                                deletableTimeBandModelDTOs);

                if (operationStatus.isSuccess()) {
                    printLog("ADD operation successful");

                    removePageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_LIST.getKey());
                    moveOn();

                } else {
                    printLog("ADD operation failed");
                    setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_save_failure));
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
                             NavigationFlow.TIME_BAND_MODEL_LIST.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<TimeBandModelDTO> getDeletableTimeBandModels() {
        TimeBandModelWrapper wrapper = getTimeBandModelWrapper();

        return TimeBandModelProvider.INSTANCE.getTimeBandModelDTOsFor(wrapper.getMeterClass(),
                                                                      wrapper.getDateType());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setInsertTimeBandModelsTable(RichTable InsertTimeBandModelsTable) {
        this.InsertTimeBandModelsTable = InsertTimeBandModelsTable;
    }

    public RichTable getInsertTimeBandModelsTable() {
        return InsertTimeBandModelsTable;
    }
}
