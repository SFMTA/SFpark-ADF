package sfpark.event.manager.view.backing.helper;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;

/**
 * To be implemented by those Bean Classes supporting the UI with
 *
 * (1) Table UI component - Row Selection
 * (2) At least 'Add', 'Edit' and/or 'Delete' button
 */
public interface ListBeanInterface {

    /**
     * To be called when Add Button is clicked in the Listing Page
     *
     * @param event
     */
    public void addButtonHandler(ActionEvent event);

    /**
     * To be called when Edit Button is clicked in the Listing Page
     *
     * @param event
     */
    public void editButtonHandler(ActionEvent event);

    /**
     * To be called when Delete Button is clicked in the Listing Page
     *
     * @param event
     */
    public void deleteButtonHandler(ActionEvent event);

    /**
     * To be called when Table row is Selected in the Listing Page
     *
     * @param event
     */
    public void tableRowSelectionHandler(SelectionEvent event);

}
