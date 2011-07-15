package sfpark.asset.manager.view.backing.shared;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;

import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryBulkDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;

import sfpark.asset.manager.application.key.PageFlowScopeKey;
import sfpark.asset.manager.application.key.SessionScopeKey;
import sfpark.asset.manager.view.backing.BaseBean;
import sfpark.asset.manager.view.backing.helper.PropertiesBeanInterface;
import sfpark.asset.manager.view.flow.NavigationFlow;
import sfpark.asset.manager.view.flow.NavigationMode;
import sfpark.asset.manager.view.flow.NavigationType;
import sfpark.asset.manager.view.util.DataRepositoryUtil;

public class MeterModelsBean extends BaseBean implements PropertiesBeanInterface {

    private RichTable meterModelsTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public MeterModelsBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static MeterModelsBean getInstance() {
        return (MeterModelsBean)getCurrentInstanceFor("meterModelsBean");
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void saveButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // Do nothing
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        MeterModelsDO meterModelsDO =
            (MeterModelsDO)getMeterModelsTable().getSelectedRowData();

        if (getCurrentPageType().isSingle()) {
            ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
                (ParkingSpaceInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey());

            parkingSpaceInventoryDTO.setDisplayMeterDetails(meterModelsDO);

            setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                  parkingSpaceInventoryDTO);

        } else if (getCurrentPageType().isBulk()) {
            ParkingSpaceInventoryBulkDTO parkingSpaceInventoryBulkDTO =
                (ParkingSpaceInventoryBulkDTO)getPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_DTO.getKey());

            parkingSpaceInventoryBulkDTO.setDisplayMeterDetails(meterModelsDO);

            setPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_DTO.getKey(),
                                  parkingSpaceInventoryBulkDTO);

        }

        moveOn();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void moveOn() {

        String backTo = NavigationFlow.ERROR.name();

        NavigationType currentPageType = getCurrentPageType();

        if (currentPageType.isSingle()) {
            NavigationMode currentPageMode = getCurrentPageMode();

            if (currentPageMode.isAddMode()) {
                backTo = NavigationFlow.AddParkingSpace.name();
            } else if (currentPageMode.isEditMode()) {
                backTo = NavigationFlow.EditParkingSpace.name();
            }

        } else if (currentPageType.isBulk()) {
            backTo = NavigationFlow.BulkEditParkingSpace.name();
        }

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(), backTo);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    public List<MeterModelsDO> getMeterModels() {
        return DataRepositoryUtil.getMeterModelsDOList();
    }

    public String getParentBreadCrumbPageTitle() {
        NavigationType currentPageType = getCurrentPageType();

        if (currentPageType.isBulk()) {
            return "Bulk Parking Space Management";

        } else if (currentPageType.isSingle()) {
            NavigationMode currentPageMode = getCurrentPageMode();

            if (currentPageMode.isAddMode()) {
                return "ADD Parking Space";
            } else if (currentPageMode.isEditMode()) {
                return "EDIT Parking Space";
            }
        }

        return "Parking Space";
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setMeterModelsTable(RichTable meterModelsTable) {
        this.meterModelsTable = meterModelsTable;
    }

    public RichTable getMeterModelsTable() {
        return meterModelsTable;
    }
}
