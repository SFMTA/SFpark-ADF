package sfpark.asset.manager.view.backing.shared;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dO.meterModels.MeterModelsDO;

import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;

import sfpark.adf.tools.model.data.tO.parkingSpaceInventory.ParkingSpaceInventoryBulkTO;

import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;

import sfpark.asset.manager.application.key.PageFlowScopeKey;
import sfpark.asset.manager.application.key.SessionScopeKey;
import sfpark.asset.manager.view.backing.BaseBean;

import sfpark.asset.manager.view.flow.NavigationFlow;
import sfpark.asset.manager.view.flow.NavigationMode;
import sfpark.asset.manager.view.flow.NavigationType;
import sfpark.asset.manager.view.util.DataRepositoryUtil;
import sfpark.asset.manager.application.key.ViewScopeKey;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120522-01 Mark Piller - Oracle Consulting  Add logic to preserve Multi Space Pay Station ID and Multi Space Number
 */

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

    public void saveButtonHandler(ActionEvent event) {
        // Do nothing
    }

    public void cancelButtonHandler(ActionEvent event) {
        moveOn();
    }

    public void tableRowSelectionHandler(SelectionEvent event) {
        boolean preserveMsPayStationId = false;
        
        MeterModelsDO meterModelsDO =
            (MeterModelsDO)getMeterModelsTable().getSelectedRowData();

        if (getCurrentPageType().isSingle()) {
            ParkingSpaceInventoryDTO parkingSpaceInventoryDTO =
                (ParkingSpaceInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey());

            // 20120522-01
            // if the action calling this is "ChangeMeterModel"
            // AND if the Meter Type was "Multi Space"
            // THEN preserve the msPayStationId value
            String meterTypeValue = (String)getViewScopeValue(ViewScopeKey.METER_TYPE.getKey());
            String action = (String)getViewScopeValue(ViewScopeKey.ACTION.getKey());
            String msPayStationId = (String)getViewScopeValue(ViewScopeKey.MS_PAY_STATION_ID.getKey());
            int msNumber = (Integer)getViewScopeValue(ViewScopeKey.MS_NUMBER.getKey());
            
            if(action != null && action.equals("ChangeMeterModel")){
                if(meterTypeValue != null && meterTypeValue.equals("Multi Space")){
                    preserveMsPayStationId = true;
                }
            } // if(action != null && action.equals("ChangeMeterModel"))

            // 20120522-01
            if(preserveMsPayStationId){
                parkingSpaceInventoryDTO.setDisplayMeterDetails(meterModelsDO, msPayStationId, msNumber);
            } else {
                parkingSpaceInventoryDTO.setDisplayMeterDetails(meterModelsDO);
            }

            setPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey(),
                                  parkingSpaceInventoryDTO);

        } else if (getCurrentPageType().isBulk()) {
            ParkingSpaceInventoryBulkTO parkingSpaceInventoryBulkTO =
                (ParkingSpaceInventoryBulkTO)getPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_TO.getKey());

            parkingSpaceInventoryBulkTO.setDisplayMeterDetails(meterModelsDO);

            setPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_TO.getKey(),
                                  parkingSpaceInventoryBulkTO);

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
