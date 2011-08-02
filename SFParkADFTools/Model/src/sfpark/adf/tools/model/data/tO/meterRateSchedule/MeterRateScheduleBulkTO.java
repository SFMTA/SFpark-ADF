package sfpark.adf.tools.model.data.tO.meterRateSchedule;

import sfpark.adf.tools.model.data.helper.MeterRateType;
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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PURELY FOR EASE

    public void setAllBoolean(boolean value) {
        setDeleteAllBaseRates(value);
        setDisableDeleteAllBaseRates(value);

        setDeleteAllHourlyRates(value);
        setDisableDeleteAllHourlyRates(value);
    }

    public void setProperBoolean(MeterRateType rateType, boolean value) {
        if (rateType.isRateTypeB()) {
            setDeleteAllBaseRates(value);
            setDisableDeleteAllBaseRates(value);
        } else if (rateType.isRateTypeH()) {
            setDeleteAllHourlyRates(value);
            setDisableDeleteAllHourlyRates(value);
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private boolean DeleteAllBaseRates;
    private boolean DeleteAllHourlyRates;

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
}
