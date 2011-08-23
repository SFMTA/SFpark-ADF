package sfpark.rateChange.manager.view.backing.rateChangeDeployment;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.rateChange.manager.view.backing.BaseBean;

public class DeploymentPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                  RequestScopeBeanInterface {
    public DeploymentPropertiesBean() {
        super();
    }

    public void saveButtonHandler(ActionEvent event) {
    }

    public void cancelButtonHandler(ActionEvent event) {
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
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
