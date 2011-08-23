package sfpark.rateChange.manager.view.backing.rateChangeDeployment;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.view.backing.helper.ListBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.view.backing.BaseBean;

public class DeploymentListBean extends BaseBean implements ListBeanInterface,
                                                            RequestScopeBeanInterface {
    public DeploymentListBean() {
        super();
    }

    public void addButtonHandler(ActionEvent event) {
    }

    public void editButtonHandler(ActionEvent event) {
    }

    public void deleteButtonHandler(ActionEvent event) {
    }

    public void selectAllButtonHandler(ActionEvent event) {
    }

    public void unselectAllButtonHandler(ActionEvent event) {
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
    }

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
}
