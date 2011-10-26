package sfpark.admin.console.view.backing.timeband.helper;

import java.util.List;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;

import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.backing.timeband.TimebandAbstractBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;
import sfpark.admin.console.view.helper.ADFUIHelper;

public class PickTimebandTypeBean extends TimebandAbstractBean implements PropertiesBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickTimebandTypeBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // LIST VALUES

    public List<SelectItem> getListMeterClass() {
        return ADFUIHelper.getMeterClassDisplayList();
    }

    public List<SelectItem> getListDateType() {
        return ADFUIHelper.getDateTypeDisplayList();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
        //
        // Reuse as 'Continue'
        //

        NavigationMode currentPageMode = getCurrentPageMode();

        if (currentPageMode.isAddMode()) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ADD Mode
            printLog("ADD Mode");

            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.PICK_TIMEBAND_VALUES.name());

        } else if (currentPageMode.isDeleteMode()) {
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // DELETE Mode
            printLog("DELETE Mode");

            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.DELETE_TIME_BAND_MODEL.name());

        }
    }

    public void cancelButtonHandler(ActionEvent event) {
        clearPageFlowScopeCache();

        setCurrentPageMode(NavigationMode.READ_ONLY);
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.TIME_BAND_MODEL_LIST.name());
    }
}
