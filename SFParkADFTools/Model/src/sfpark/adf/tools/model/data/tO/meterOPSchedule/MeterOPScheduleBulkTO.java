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
    // PURELY FOR DISPLAY PURPOSE

    private boolean DisableDeleteAllOPSchedules;
    private boolean DisableDeleteAllALTSchedules;
    private boolean DisableDeleteAllTOWSchedules;

    public void setDisableDeleteAllOPSchedules(boolean DisableDeleteAllOPSchedules) {
        this.DisableDeleteAllOPSchedules = DisableDeleteAllOPSchedules;
    }

    public boolean isDisableDeleteAllOPSchedules() {
        return DisableDeleteAllOPSchedules;
    }

    public void setDisableDeleteAllALTSchedules(boolean DisableDeleteAllALTSchedules) {
        this.DisableDeleteAllALTSchedules = DisableDeleteAllALTSchedules;
    }

    public boolean isDisableDeleteAllALTSchedules() {
        return DisableDeleteAllALTSchedules;
    }

    public void setDisableDeleteAllTOWSchedules(boolean DisableDeleteAllTOWSchedules) {
        this.DisableDeleteAllTOWSchedules = DisableDeleteAllTOWSchedules;
    }

    public boolean isDisableDeleteAllTOWSchedules() {
        return DisableDeleteAllTOWSchedules;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR EASE

    public void setAllBoolean(boolean value) {
        setDeleteAllOPSchedules(value);
        setDisableDeleteAllOPSchedules(value);

        setDeleteAllALTSchedules(value);
        setDisableDeleteAllALTSchedules(value);

        setDeleteAllTOWSchedules(value);
        setDisableDeleteAllTOWSchedules(value);
    }

    public void setProperBoolean(MeterScheduleType scheduleType,
                                 boolean value) {
        if (scheduleType.isScheduleOP()) {
            setDeleteAllOPSchedules(value);
            setDisableDeleteAllOPSchedules(value);
        } else if (scheduleType.isScheduleALT()) {
            setDeleteAllALTSchedules(value);
            setDisableDeleteAllALTSchedules(value);
        } else if (scheduleType.isScheduleTOW()) {
            setDeleteAllTOWSchedules(value);
            setDisableDeleteAllTOWSchedules(value);
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
