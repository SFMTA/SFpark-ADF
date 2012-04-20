package sfpark.adf.tools.model.data.tO.meterRateSchedule;

import sfpark.adf.tools.model.data.helper.MeterRateScheduleType;
import sfpark.adf.tools.model.data.tO.BaseTO;
import sfpark.adf.tools.utilities.generic.ObjectUtil;

public class MeterRateScheduleBulkTO extends BaseTO {
    public MeterRateScheduleBulkTO() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public boolean isSameAs(MeterRateScheduleBulkTO originalTO) {

        if ((ObjectUtil.getNullSafe(this.isDeleteAllBaseRates()) ==
             ObjectUtil.getNullSafe(originalTO.isDeleteAllBaseRates())) &&
            (ObjectUtil.getNullSafe(this.isDeleteAllHourlyRates()) ==
             ObjectUtil.getNullSafe(originalTO.isDeleteAllHourlyRates()))) {

            return true;
        }

        return false;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR DISPLAY PURPOSES

    private boolean DisableDeleteAllBaseRates;
    private boolean DisableDeleteAllHourlyRates;
    private boolean DisableDeleteAllSpecialRates;

    public void setDisableDeleteAllBaseRates(boolean DisableDeleteAllBaseRates) {
        this.DisableDeleteAllBaseRates = DisableDeleteAllBaseRates;
    }

    public boolean isDisableDeleteAllBaseRates() {
        return DisableDeleteAllBaseRates;
    }

    public void setDisableDeleteAllHourlyRates(boolean DisableDeleteAllHourlyRates) {
        this.DisableDeleteAllHourlyRates = DisableDeleteAllHourlyRates;
    }

    public boolean isDisableDeleteAllHourlyRates() {
        return DisableDeleteAllHourlyRates;
    }

    public void setDisableDeleteAllSpecialRates(boolean DisableDeleteAllSpecialRates) {
        this.DisableDeleteAllSpecialRates = DisableDeleteAllSpecialRates;
    }

    public boolean isDisableDeleteAllSpecialRates() {
        return DisableDeleteAllSpecialRates;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR EASE

    public void setAllBoolean(boolean value) {
        setDeleteAllBaseRates(value);
        setDisableDeleteAllBaseRates(value);

        setDeleteAllHourlyRates(value);
        setDisableDeleteAllHourlyRates(value);

        setDeleteAllSpecialRates(value);
        setDisableDeleteAllSpecialRates(value);
    }

    public void setProperBoolean(MeterRateScheduleType rateType,
                                 boolean value) {
        if (rateType.isBase()) {
            setDeleteAllBaseRates(value);
            setDisableDeleteAllBaseRates(value);
        } else if (rateType.isHourly()) {
            setDeleteAllHourlyRates(value);
            setDisableDeleteAllHourlyRates(value);
        } else if (rateType.isSpecial()) {
            setDeleteAllSpecialRates(value);
            setDisableDeleteAllSpecialRates(value);
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private boolean DeleteAllBaseRates;
    private boolean DeleteAllHourlyRates;
    private boolean DeleteAllSpecialRates;

    public void setDeleteAllBaseRates(boolean DeleteAllBaseRates) {
        this.DeleteAllBaseRates = DeleteAllBaseRates;
    }

    public boolean isDeleteAllBaseRates() {
        return DeleteAllBaseRates;
    }

    public void setDeleteAllHourlyRates(boolean DeleteAllHourlyRates) {
        this.DeleteAllHourlyRates = DeleteAllHourlyRates;
    }

    public boolean isDeleteAllHourlyRates() {
        return DeleteAllHourlyRates;
    }

    public void setDeleteAllSpecialRates(boolean DeleteAllSpecialRates) {
        this.DeleteAllSpecialRates = DeleteAllSpecialRates;
    }

    public boolean isDeleteAllSpecialRates() {
        return DeleteAllSpecialRates;
    }
}
