package sfpark.rateChange.manager.view.backing.rateChange.helper;

import java.sql.Date;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.data.dto.rateChange.RateChangeHeaderDTO;
import sfpark.adf.tools.model.provider.CalendarHeaderProvider;
import sfpark.adf.tools.utilities.generic.SQLDateUtil;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;

import sfpark.rateChange.manager.application.key.PageFlowScopeKey;
import sfpark.rateChange.manager.application.key.SessionScopeKey;
import sfpark.rateChange.manager.view.backing.BaseBean;
import sfpark.rateChange.manager.view.flow.NavigationFlow;

public class PickCalendarBean extends BaseBean implements PropertiesBeanInterface {

    private RichTable CalendarIDTable;

    private String SearchString;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public PickCalendarBean() {
        super();

        setSearchString("%");
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void saveButtonHandler(ActionEvent event) {
        //
        // Reuse as "Refresh Table" button
        //
        getCalendarIDTable().setValue(getListCalendarID());
        addPartialTarget(getCalendarIDTable());
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        CalendarHeaderDTO DTO =
            (CalendarHeaderDTO)getCalendarIDTable().getSelectedRowData();

        if (DTO != null) {
            RateChangeHeaderDTO rateChangeHeaderDTO =
                (RateChangeHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey());

            rateChangeHeaderDTO.setCalendarID(DTO.getCalendarID());
            rateChangeHeaderDTO.setDisplayCalendarName(DTO.getCalendarName());

            setPageFlowScopeValue(PageFlowScopeKey.RATE_CHANGE_HEADER_DTO.getKey(),
                                  rateChangeHeaderDTO);

            moveOn();
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                             NavigationFlow.AddRateChange.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<CalendarHeaderDTO> getListCalendarID() {

        String searchString = getSearchString();
        String searchType = "RateChg";
        Date searchDate = SQLDateUtil.getTodaysDate();

        return CalendarHeaderProvider.INSTANCE.getCalendarHeaderDTOs(searchString,
                                                                     searchType,
                                                                     searchDate);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setCalendarIDTable(RichTable CalendarIDTable) {
        this.CalendarIDTable = CalendarIDTable;
    }

    public RichTable getCalendarIDTable() {
        return CalendarIDTable;
    }

    public void setSearchString(String SearchString) {
        this.SearchString = SearchString;
    }

    public String getSearchString() {
        return SearchString;
    }
}
