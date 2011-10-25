package sfpark.admin.console.view.backing.allowedValues;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.backing.BaseBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;

public class AllowedValuesListBean extends BaseBean {

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

                AllowedValuesDTO selectedDTO =
                    (AllowedValuesDTO)getAllowedValuesTable().getSelectedRowData();
                setPageFlowScopeValue(PageFlowScopeKey.SELECTED_ALLOWED_VALUES_DTO.getKey(),
                                      selectedDTO);

                setCurrentPageMode(NavigationMode.SHOW_DETAILS);
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.ALLOWED_VALUES_PROPERTIES.name());
            }
        }
    }

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
