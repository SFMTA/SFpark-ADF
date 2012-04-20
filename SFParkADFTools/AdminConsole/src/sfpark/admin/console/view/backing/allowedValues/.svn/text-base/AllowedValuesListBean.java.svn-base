package sfpark.admin.console.view.backing.allowedValues;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;

import sfpark.adf.tools.view.backing.helper.ListBeanInterface;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.backing.BaseBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;

public class AllowedValuesListBean extends BaseBean implements ListBeanInterface {

    private RichCommandButton ShowDetailsButton;

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

    public void addButtonHandler(ActionEvent event) {
        getAllowedValuesTable().getSelectedRowKeys().clear();

        setCurrentPageMode(NavigationMode.ADD);
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.ALLOWED_VALUES_PROPERTIES.name());
    }

    public void editButtonHandler(ActionEvent event) {
        if (getAllowedValuesTable().getSelectedRowKeys().size() == 1) {

            AllowedValuesDTO selectedDTO =
                (AllowedValuesDTO)getAllowedValuesTable().getSelectedRowData();
            setPageFlowScopeValue(PageFlowScopeKey.SELECTED_ALLOWED_VALUES_DTO.getKey(),
                                  selectedDTO);

            setCurrentPageMode(NavigationMode.SHOW_DETAILS);
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.ALLOWED_VALUES_PROPERTIES.name());
        }
    }

    public void deleteButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        boolean disable =
            (getAllowedValuesTable().getSelectedRowKeys().size() != 1);

        getShowDetailsButton().setDisabled(disable);
        addPartialTarget(getShowDetailsButton());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setShowDetailsButton(RichCommandButton ShowDetailsButton) {
        this.ShowDetailsButton = ShowDetailsButton;
    }

    public RichCommandButton getShowDetailsButton() {
        return ShowDetailsButton;
    }

    public void setAllowedValuesTable(RichTable AllowedValuesTable) {
        this.AllowedValuesTable = AllowedValuesTable;
    }

    public RichTable getAllowedValuesTable() {
        return AllowedValuesTable;
    }
}
