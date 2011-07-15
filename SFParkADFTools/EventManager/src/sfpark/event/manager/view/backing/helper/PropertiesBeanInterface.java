package sfpark.event.manager.view.backing.helper;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

/**
 * To be implemented by those Bean Classes which display a Properties UI with
 * at least 'Save' and 'Cancel/Discard Changes" button
 */
public interface PropertiesBeanInterface {

    /**
     * To be called when Save Button is clicked in the Properties Page
     *
     * @param event
     */
    public void saveButtonHandler(ActionEvent event);

    /**
     * To be called when Cancel Button is clicked in the Properties Page
     *
     * @param event
     */
    public void cancelButtonHandler(ActionEvent event);

    /**
     * To be called when Any Value is changed in the Properties Page
     *
     * @param event
     */
    public void anyValueChangeHandler(ValueChangeEvent event);

}
