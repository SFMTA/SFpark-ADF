package sfpark.admin.console.view.backing.allowedValues;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.admin.console.view.backing.BaseBean;

public class AllowedValuesPropertiesBean extends BaseBean implements PropertiesBeanInterface,
                                                                     RequestScopeBeanInterface {
    public AllowedValuesPropertiesBean() {
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
