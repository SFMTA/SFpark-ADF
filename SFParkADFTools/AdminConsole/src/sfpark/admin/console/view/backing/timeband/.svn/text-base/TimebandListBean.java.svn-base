package sfpark.admin.console.view.backing.timeband;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import sfpark.adf.tools.model.data.dto.timeBandModel.TimeBandModelDTO;
import sfpark.adf.tools.model.provider.TimeBandModelProvider;

import sfpark.admin.console.application.key.PageFlowScopeKey;
import sfpark.admin.console.application.key.SessionScopeKey;
import sfpark.admin.console.view.backing.BaseBean;
import sfpark.admin.console.view.flow.NavigationFlow;
import sfpark.admin.console.view.flow.NavigationMode;

public class TimebandListBean extends BaseBean {

    private RichTable TimeBandModelsTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public TimebandListBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public List<TimeBandModelDTO> getTimeBandModels() {
        List<TimeBandModelDTO> timeBandModelDTOs =
            (List<TimeBandModelDTO>)getPageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_LIST.getKey());

        if (timeBandModelDTOs == null) {
            timeBandModelDTOs =
                    TimeBandModelProvider.INSTANCE.getAllTimeBandModelDTOs();
            setPageFlowScopeValue(PageFlowScopeKey.TIME_BAND_MODEL_LIST.getKey(),
                                  timeBandModelDTOs);
        }

        return timeBandModelDTOs;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void pickButtonHandler(ActionEvent event) {
        //
        // Reuse as various buttons
        //

        String ID = event.getComponent().getId();

        if (ID.contains("addTimeband")) {
            setCurrentPageMode(NavigationMode.ADD);
        } else if (ID.contains("deleteTimeband")) {
            setCurrentPageMode(NavigationMode.DELETE);
        }

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.PICK_TIMEBAND_TYPE.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setTimeBandModelsTable(RichTable TimeBandModelsTable) {
        this.TimeBandModelsTable = TimeBandModelsTable;
    }

    public RichTable getTimeBandModelsTable() {
        return TimeBandModelsTable;
    }
}
