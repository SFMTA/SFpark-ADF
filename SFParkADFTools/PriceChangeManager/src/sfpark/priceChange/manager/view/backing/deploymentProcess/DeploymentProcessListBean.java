package sfpark.priceChange.manager.view.backing.deploymentProcess;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

public class DeploymentProcessListBean implements ListBeanInterface,
                                                                   RequestScopeBeanInterface {
    
    /*

    private RichCommandButton DeleteProcessButton;
    private RichTable ActiveDeploymentProcessTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public DeploymentProcessListBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static DeploymentProcessListBean getInstance() {
        return (DeploymentProcessListBean)ADFUtil.getCurrentInstanceFor("deploymentProcessListBean");
    }

    // */

    public void clearPageFlowScopeCache() {
        // TODO
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

    /*
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public List<RateChangeProcessControlDTO> getActiveDeploymentProcesses() {
        List<RateChangeProcessControlDTO> activeList =
            (List<RateChangeProcessControlDTO>)getPageFlowScopeValue(PageFlowScopeKey.ACTIVE_DEPLOY_PROCESS_LIST.getKey());

        if (activeList == null) {
            activeList =
                    RateChangeProcessControlProvider.INSTANCE.getActiveRateChangeProcessControlDTOs();
            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_DEPLOY_PROCESS_LIST.getKey(),
                                  activeList);
        }

        return activeList;
    }
    
    public List<RateChangeProcessControlDTO> getHistoricDeploymentProcesses() {
        List<RateChangeProcessControlDTO> historicList =
            (List<RateChangeProcessControlDTO>)getPageFlowScopeValue(PageFlowScopeKey.HISTORIC_DEPLOY_PROCESS_LIST.getKey());

        if (historicList == null) {
            historicList =
                    RateChangeProcessControlProvider.INSTANCE.getHistoricRateChangeProcessControlDTOs();
            setPageFlowScopeValue(PageFlowScopeKey.HISTORIC_DEPLOY_PROCESS_LIST.getKey(),
                                  historicList);
        }

        return historicList;
    }
    
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALIDATORS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL READ-ONLY INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

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
    // DISPLAY LIST VALUES

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALUE CHANGE HANDLERS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    // */
    
    public void addButtonHandler(ActionEvent event) {
    }

    public void editButtonHandler(ActionEvent event) {
        // TODO revise later
        /*

        RateChangeProcessControlDTO selectedDTO =
            (RateChangeProcessControlDTO)getActiveDeploymentProcessTable().getSelectedRowData();

        if (selectedDTO != null) {
            setPageFlowScopeValue(PageFlowScopeKey.SELECTED_ACTIVE_DEPLOY_PROCESS_FOR_PROPS.getKey(),
                                  selectedDTO);
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.EDIT_DEPLOYMENT_PROCESS.name());
        }
        // */
    }

    public void deleteButtonHandler(ActionEvent event) {
    }

    public void selectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void unselectAllButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
    }

    /*
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

    public void setActiveDeploymentProcessTable(RichTable ActiveDeploymentProcessTable) {
        this.ActiveDeploymentProcessTable = ActiveDeploymentProcessTable;
    }

    public RichTable getActiveDeploymentProcessTable() {
        return ActiveDeploymentProcessTable;
    }

    public void setDeleteProcessButton(RichCommandButton DeleteProcessButton) {
        this.DeleteProcessButton = DeleteProcessButton;
    }

    public RichCommandButton getDeleteProcessButton() {
        return DeleteProcessButton;
    }
    
    // */
}
