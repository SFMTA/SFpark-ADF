package sfpark.asset.manager.view.backing.shared;

import java.sql.Date;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import sfpark.adf.tools.model.data.dO.meterSchedValidations.MeterSchedValidationsDO;

import sfpark.adf.tools.model.data.dO.parkingSpaceGroups.ParkingSpaceGroupsDO;
import sfpark.adf.tools.model.data.dto.meterOPSchedule.MeterOPScheduleDTO;
import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;

import sfpark.adf.tools.model.data.helper.MeterScheduleType;

import sfpark.adf.tools.model.data.tO.meterOPSchedule.MeterOPScheduleBulkTO;
import sfpark.adf.tools.model.data.tO.parkingSpaceInventory.ParkingSpaceInventoryBulkTO;
import sfpark.adf.tools.model.provider.MeterOPScheduleProvider;
import sfpark.adf.tools.model.provider.MeterScheduleValidationsProvider;

import sfpark.adf.tools.utilities.generic.SQLDateUtil;

import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;

import sfpark.asset.manager.application.key.PageFlowScopeKey;
import sfpark.asset.manager.application.key.SessionScopeKey;
import sfpark.asset.manager.view.backing.BaseBean;

import sfpark.asset.manager.view.flow.NavigationFlow;
import sfpark.asset.manager.view.flow.NavigationMode;
import sfpark.asset.manager.view.flow.NavigationType;
import sfpark.asset.manager.view.provider.DMLOperationsProvider;

public class MeterScheduleTemplateBean extends BaseBean implements PropertiesBeanInterface {

    private RichTable schedTemplateTable;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public MeterScheduleTemplateBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static MeterScheduleTemplateBean getInstance() {
        return (MeterScheduleTemplateBean)getCurrentInstanceFor("meterScheduleTemplateBean");
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
        removePageFlowScopeValue(PageFlowScopeKey.METER_SCHEDULE_TEMPLATE_TYPE.getKey());
    }

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

        MeterSchedValidationsDO meterSchedValidationsDO =
            (MeterSchedValidationsDO)getSchedTemplateTable().getSelectedRowData();

        if (getCurrentPageType().isSingle()) {
            ParkingSpaceInventoryDTO currentParkingSpaceInventoryDTO =
                (ParkingSpaceInventoryDTO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_DTO.getKey());

            List<MeterOPScheduleDTO> meterScheduleDTOs =
                (List<MeterOPScheduleDTO>)getPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey());

            MeterOPScheduleDTO meterScheduleDTO =
                DMLOperationsProvider.INSTANCE.getNewMeterScheduleDTO(meterSchedValidationsDO,
                                                                      currentParkingSpaceInventoryDTO.getParkingSpaceID(),
                                                                      getNextPriorityInt(meterScheduleDTOs,
                                                                                         meterSchedValidationsDO.getScheduleType()),
                                                                      SQLDateUtil.getTodaysDate(),
                                                                      currentParkingSpaceInventoryDTO.getCapColor());

            meterScheduleDTOs.add(0, meterScheduleDTO);

            setPageFlowScopeValue(PageFlowScopeKey.ACTIVE_METER_SCHEDULE_LIST.getKey(),
                                  meterScheduleDTOs);


        } else if (getCurrentPageType().isBulk()) {

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Set the checkboxes properly

            MeterOPScheduleBulkTO meterOPScheduleBulkTO =
                (MeterOPScheduleBulkTO)getPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_TO.getKey());

            meterOPScheduleBulkTO.setProperBoolean(meterSchedValidationsDO.getScheduleType(), true);

            setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_TO.getKey(),
                                  meterOPScheduleBulkTO);

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Retrieve the next effective from date

            ParkingSpaceGroupsDO parkingSpaceGroupsDO =
                (ParkingSpaceGroupsDO)getPageFlowScopeValue(PageFlowScopeKey.PARKING_SPACE_GROUPS_DO.getKey());

            Date nextEffectiveFromDate =
                MeterOPScheduleProvider.INSTANCE.getNextEffectiveFromDate(parkingSpaceGroupsDO.getParkingSpaceIDList(),
                                                                          meterSchedValidationsDO.getScheduleType());

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Retrieve the best possible colour option

            ParkingSpaceInventoryBulkTO currentParkingSpaceInventoryBulkTO =
                (ParkingSpaceInventoryBulkTO)getPageFlowScopeValue(PageFlowScopeKey.BULK_PARKING_SPACE_TO.getKey());

            String capColor =
                (currentParkingSpaceInventoryBulkTO.isToBeUpdatedCapColor()) ?
                currentParkingSpaceInventoryBulkTO.getCapColor() : null;

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // Perform the addition

            List<MeterOPScheduleDTO> meterScheduleDTOs =
                (List<MeterOPScheduleDTO>)getPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey());

            MeterOPScheduleDTO meterScheduleDTO =
                DMLOperationsProvider.INSTANCE.getNewMeterScheduleDTO(meterSchedValidationsDO,
                                                                      null,
                                                                      getNextPriorityInt(meterScheduleDTOs,
                                                                                         meterSchedValidationsDO.getScheduleType()),
                                                                      nextEffectiveFromDate,
                                                                      capColor);

            meterScheduleDTOs.add(0, meterScheduleDTO);

            setPageFlowScopeValue(PageFlowScopeKey.BULK_METER_SCHEDULE_LIST.getKey(),
                                  meterScheduleDTOs);

        }

        moveOn();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private int getNextPriorityInt(List<MeterOPScheduleDTO> meterScheduleDTOs,
                                   MeterScheduleType scheduleType) {

        int nextPriority = 0;

        if (!meterScheduleDTOs.isEmpty()) {
            for (MeterOPScheduleDTO DTO : meterScheduleDTOs) {
                if (DTO.getScheduleType().equals(scheduleType)) {
                    if (DTO.getSchedulePriority() > nextPriority) {
                        nextPriority = DTO.getSchedulePriority();
                    }
                }
            }
        }

        return (nextPriority + 1);
    }

    private MeterScheduleType getMeterScheduleType() {
        MeterScheduleType meterScheduleType =
            (MeterScheduleType)getPageFlowScopeValue(PageFlowScopeKey.METER_SCHEDULE_TEMPLATE_TYPE.getKey());

        if (meterScheduleType == null) {
            meterScheduleType = MeterScheduleType.OP;
        }

        return meterScheduleType;
    }

    private void moveOn() {
        clearPageFlowScopeCache();

        String backTo = NavigationFlow.ERROR.name();

        if (getCurrentPageType().isSingle()) {
            // Since this can be accessed only in EDIT mode
            backTo = NavigationFlow.EditParkingSpace.name();

        } else if (getCurrentPageType().isBulk()) {
            backTo = NavigationFlow.BulkEditParkingSpace.name();

        }

        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(), backTo);

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

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

    public String getBreadCrumbPageTitle() {
        MeterScheduleType templateType = getMeterScheduleType();

        if (templateType.isScheduleTOW()) {
            return "TOW Schedule Templates";
        } else if (templateType.isScheduleALT()) {
            return "ALT Schedule Templates";
        }

        return "OP Schedule Templates";
    }

    public List<MeterSchedValidationsDO> getSchedTemplates() {
        return MeterScheduleValidationsProvider.INSTANCE.getMeterSchedValidationDOs(getMeterScheduleType());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

    public void setSchedTemplateTable(RichTable schedTemplateTable) {
        this.schedTemplateTable = schedTemplateTable;
    }

    public RichTable getSchedTemplateTable() {
        return schedTemplateTable;
    }
}
