package sfpark.asset.manager.view.backing.shared;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.event.DialogEvent;

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
 * Backing bean for meterModelsPage.jsff
 * 
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120522-01 Mark Piller - Oracle Consulting  Add logic to preserve Multi Space Pay Station ID and Multi Space Number
 * 20120523-01 Mark Piller - Oracle Consulting  Add logic to work with a Multi Space to Single Space change
 * 20120530-01 Mark Piller - Oracle Consulting  Add Smart Meter Flag logic for warnings to user when selecting Single Space replacement to Multi Space
 */

public class MeterModelsBean extends BaseBean implements PropertiesBeanInterface {
    private RichTable meterModelsTable;
    private RichPopup warningMs2SsPopup;
    private RichDialog warningMs2SsDialog;

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
        boolean multiSpaceToMultiSpaceChange = false;  // 20120522-01
        boolean singleSpaceToMultiSpaceChange = false; // 20120523-01
        boolean multiSpaceToSingleSpaceChange = false; // 20120523-02
        boolean returnToCaller = true;
        
        String newMeterTypeValue = ""; // 20120522-02
        
        MeterModelsDO meterModelsDO = (MeterModelsDO)getMeterModelsTable().getSelectedRowData();

        // identify what meter type the user selected
        newMeterTypeValue = meterModelsDO.getMeterType().toString(); // 20120522-02

        if (getCurrentPageType().isSingle()) {
            ParkingSpaceInventoryDTO parkingSpaceInventoryDTO = (ParkingSpaceInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey());

            // 20120522-01
            // "Current" means what is currently displayed to the user
            // if the action calling this is "ChangeMeterModel"
            // AND if the Meter Type was "Multi Space"
            // THEN preserve the msPayStationId value
            String currentMeterTypeValue = (String)getViewScopeValue(ViewScopeKey.METER_TYPE.getKey());
            String currentMsPayStationId = (String)getViewScopeValue(ViewScopeKey.MS_PAY_STATION_ID.getKey());
            String currentBlockfaceId = (String)getViewScopeValue(ViewScopeKey.BLOCKFACE_ID.getKey()); //20120523-01
            String currentSmartMeterFlag = (String)getViewScopeValue(ViewScopeKey.SMART_METER_FLAG.getKey()); // 20120530-01
            int msNumber = (Integer)getViewScopeValue(ViewScopeKey.MS_NUMBER.getKey());
            
            if(currentMeterTypeValue != null){
                if(currentMeterTypeValue.equals("Multi Space")){
                    if(newMeterTypeValue.equals("MULTI_SPACE")){
                        // If changing from multi-space meter to multi-space meter, 
                        // leave the MsID and Space Number whatever they were before, 
                        // and it’s the responsibility of whoever is making the edit to 
                        // verify that they are correct or change them if necessary.
                        multiSpaceToMultiSpaceChange = true;
                    } else if(newMeterTypeValue.equals("SINGLE_SPACE") && currentSmartMeterFlag.equals("Yes") ){
                        // Multi Space to Single Space needs a warning message
                        // when the Smart Meter Flag is Yes and Meter Type is MS
                        multiSpaceToSingleSpaceChange = true;
                    }
                } else if(currentMeterTypeValue.equals("Single Space")) { //20120523-01
                    if(newMeterTypeValue.equals("MULTI_SPACE")){ // changing meter types
                        // If changing from single-space meter to multi-space meter, 
                        // have the MsID auto-populate the first five digits 
                        // (XXX-XX, predetermined by street and block), 
                        // leave the rest blank, and give a warning if I 
                        // forget to add the last three digits.  
                        // Also leave Space Number blank and give a warning 
                        // if I fail to put a number in that box.  
                        // That way I’m less likely to forget to change 
                        // it from “1” to whatever it’s supposed to be.
                        singleSpaceToMultiSpaceChange = true;
                    } // if(newMeterTypeValue.equals("MULTI_SPACE"))
                } // if(currentMeterTypeValue.equals("Multi Space"))
            } // if(currentMeterTypeValue != null && currentMeterTypeValue.equals("Multi Space"))

            if(multiSpaceToMultiSpaceChange){ // 20120522-01
                parkingSpaceInventoryDTO.setDisplayMeterDetails(meterModelsDO, currentMsPayStationId, msNumber);
            } else if (singleSpaceToMultiSpaceChange) { // 20120523-01
                parkingSpaceInventoryDTO.setDisplayMeterDetails(meterModelsDO, currentBlockfaceId);
            } else if (multiSpaceToSingleSpaceChange) {
                returnToCaller = false;
                setSessionScopeValue("meterModelsDO",meterModelsDO);
                setSessionScopeValue("parkingSpaceInventoryDTO",parkingSpaceInventoryDTO);
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                warningMs2SsPopup.show(hints);
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

        // 201205223-01 added to allow popup dialog to 
        // regulate user selections of multi space to single space changes
        if(returnToCaller){
            moveOn();
        }
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

    public void setWarningMs2SsPopup(RichPopup warningMs2SsPopup) {
        this.warningMs2SsPopup = warningMs2SsPopup;
    }

    public RichPopup getWarningMs2SsPopup() {
        return warningMs2SsPopup;
    }

    public void setWarningMs2SsDialog(RichDialog warningMs2SsDialog) {
        this.warningMs2SsDialog = warningMs2SsDialog;
    }

    public RichDialog getWarningMs2SsDialog() {
        return warningMs2SsDialog;
    }

    public void warningMs2SsDialogListenerHandler(DialogEvent dialogEvent) {
        String userResponse = dialogEvent.getOutcome().toString();
        if(userResponse.equals("yes")){
            // user wants to perform the update - change from multi space to single space meter
            MeterModelsDO meterModelsDO = (MeterModelsDO)getSessionScopeValue("meterModelsDO");
            ParkingSpaceInventoryDTO parkingSpaceInventoryDTO = (ParkingSpaceInventoryDTO)getSessionScopeValue("parkingSpaceInventoryDTO");
            // update the changes
            parkingSpaceInventoryDTO.setDisplayMeterDetails(meterModelsDO);
        } // if(userResponse.equals("yes"))

        // clean up memory - remove the items stored for this process
        removeSessionScopeValue("meterModelsDO");
        removeSessionScopeValue("parkingSpaceInventoryDTO");

        if(userResponse.equals("yes")){
            moveOn();  // return to the calling view
        } // if(userResponse.equals("yes"))
    } // warningMs2SsDialogListenerHandler

    public void showMyPopupButtonHandler(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        warningMs2SsPopup.show(hints);
    }
}
