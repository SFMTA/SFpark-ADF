package sfpark.adf.tools.model.data.tO.meterOPSchedule;

import sfpark.adf.tools.model.data.helper.MeterScheduleType;
import sfpark.adf.tools.model.data.tO.BaseTO;
import sfpark.adf.tools.utilities.generic.ObjectUtil;

public class MeterOPScheduleBulkTO extends BaseTO {
    public MeterOPScheduleBulkTO() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(MeterOPScheduleBulkTO originalTO) {

        if ((ObjectUtil.getNullSafe(this.isDeleteAllOPSchedules()) ==
             ObjectUtil.getNullSafe(originalTO.isDeleteAllOPSchedules())) &&
            (ObjectUtil.getNullSafe(this.isDeleteAllALTSchedules()) ==
             ObjectUtil.getNullSafe(originalTO.isDeleteAllALTSchedules())) &&
            (ObjectUtil.getNullSafe(this.isDeleteAllTOWSchedules()) ==
             ObjectUtil.getNullSafe(originalTO.isDeleteAllTOWSchedules()))) {

            return true;
        }

        return false;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR EASE

    public void setAllBoolean(boolean value) {
        setDeleteAllOPSchedules(value);
        setDeleteAllALTSchedules(value);
        setDeleteAllTOWSchedules(value);
    }

    public void setProperBoolean(MeterScheduleType scheduleType) {
        if (scheduleType.isScheduleOP()) {
            setDeleteAllOPSchedules(true);
        } else if (scheduleType.isScheduleALT()) {
            setDeleteAllALTSchedules(true);
        } else if (scheduleType.isScheduleTOW()) {
            setDeleteAllTOWSchedules(true);
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private boolean DeleteAllOPSchedules;
    private boolean DeleteAllALTSchedules;
    private boolean DeleteAllTOWSchedules;

    public void setDeleteAllOPSchedules(boolean DeleteAllOPSchedules) {
        this.DeleteAllOPSchedules = DeleteAllOPSchedules;
    }

    public boolean isDeleteAllOPSchedules() {
        return DeleteAllOPSchedules;
    }

    public void setDeleteAllALTSchedules(boolean DeleteAllALTSchedules) {
        this.DeleteAllALTSchedules = DeleteAllALTSchedules;
    }

    public boolean isDeleteAllALTSchedules() {
        return DeleteAllALTSchedules;
    }

    public void setDeleteAllTOWSchedules(boolean DeleteAllTOWSchedules) {
        this.DeleteAllTOWSchedules = DeleteAllTOWSchedules;
    }

    public boolean isDeleteAllTOWSchedules() {
        return DeleteAllTOWSchedules;
    }
}
