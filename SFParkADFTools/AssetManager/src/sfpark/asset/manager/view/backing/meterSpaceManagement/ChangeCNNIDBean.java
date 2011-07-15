package sfpark.asset.manager.view.backing.meterSpaceManagement;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dO.stcLines.STCLinesDO;

import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;

import sfpark.adf.tools.model.provider.STCLinesProvider;

import sfpark.asset.manager.application.key.PageFlowScopeKey;
import sfpark.asset.manager.application.key.SessionScopeKey;
import sfpark.asset.manager.view.backing.BaseBean;
import sfpark.asset.manager.view.backing.helper.PropertiesBeanInterface;
import sfpark.asset.manager.view.flow.NavigationFlow;
import sfpark.asset.manager.view.flow.NavigationMode;
import sfpark.asset.manager.view.provider.CommonUtils;

public class ChangeCNNIDBean extends BaseBean implements PropertiesBeanInterface {

    private RichTable cnnIDTable;

    private int cnnDistance;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public ChangeCNNIDBean() {
        super();

        setCnnDistance(50);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static ChangeCNNIDBean getInstance() {
        return (ChangeCNNIDBean)getCurrentInstanceFor("changeCNNIDBean");
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void saveButtonHandler(ActionEvent event) {
        //
        // Reuse as "Refresh Table" button
        //
        getCnnIDTable().setValue(getCNNIDList());
        addPartialTarget(getCnnIDTable());
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        STCLinesDO stcLinesDO =
            (STCLinesDO)getCnnIDTable().getSelectedRowData();

        if (stcLinesDO != null) {
            ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
                (ParkingSpaceInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey());

            parkingSpaceInventoryDTO.setCNNID(stcLinesDO.getCNNID());

            setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                  parkingSpaceInventoryDTO);

            moveOn();
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {

        String backTo = NavigationFlow.ERROR.name();

        NavigationMode currentPageMode = getCurrentPageMode();

        if (currentPageMode.isAddMode()) {
            backTo = NavigationFlow.AddParkingSpace.name();
        } else if (currentPageMode.isEditMode()) {
            backTo = NavigationFlow.EditParkingSpace.name();
        }

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(), backTo);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<STCLinesDO> getCNNIDList() {

        ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
            (ParkingSpaceInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey());

        String streetName = parkingSpaceInventoryDTO.getStreetName();
        String longitude = parkingSpaceInventoryDTO.getLongitude();
        String latitude = parkingSpaceInventoryDTO.getLatitude();
        int distance = getCnnDistance();

        return STCLinesProvider.INSTANCE.getSTCLinesDOs(streetName, longitude,
                                                        latitude, distance);
    }

    public int getStreetNumber() {

        ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
            (ParkingSpaceInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey());

        return CommonUtils.generateStreetNumFromPostID(parkingSpaceInventoryDTO.getPostID());
    }

    public String getParentBreadCrumbPageTitle() {
        NavigationMode currentPageMode = getCurrentPageMode();

        if (currentPageMode.isAddMode()) {
            return "ADD Parking Space";
        } else if (currentPageMode.isEditMode()) {
            return "EDIT Parking Space";
        }

        return "Parking Space";
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setCnnIDTable(RichTable cnnIDTable) {
        this.cnnIDTable = cnnIDTable;
    }

    public RichTable getCnnIDTable() {
        return cnnIDTable;
    }

    public void setCnnDistance(int cnnDistance) {
        this.cnnDistance = cnnDistance;
    }

    public int getCnnDistance() {
        return cnnDistance;
    }
}
