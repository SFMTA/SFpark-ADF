package sfpark.adf.tools.model.data.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import sfpark.adf.tools.utilities.generic.SQLDateUtil;

public abstract class EffectiveDateBaseDTO extends BaseDTO {

    protected EffectiveDateBaseDTO() {
        super();
    }

    protected EffectiveDateBaseDTO(EffectiveDateBaseDTO DTO) {
        super(DTO);

        this.setEffectiveFromDate(DTO.getEffectiveFromDate());
        this.setEffectiveToDate(DTO.getEffectiveToDate());
    }


    protected EffectiveDateBaseDTO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setEffectiveFromDate(resultSet.getDate(EFF_FROM_DT));
        this.setEffectiveToDate(resultSet.getDate(EFF_TO_DT));
    }

    public static final String EFF_FROM_DT = "EFF_FROM_DT";
    public static final String EFF_TO_DT = "EFF_TO_DT";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected WhatChanged getWhatChanged(EffectiveDateBaseDTO originalDTO) {

        // This is called only when something has changed for sure
        // So check to see what changed

        WhatChanged whatChanged = WhatChanged.NOT_DATES;

        if (SQLDateUtil.areEqual(this.getEffectiveFromDate(),
                                 originalDTO.getEffectiveFromDate()) &&
            SQLDateUtil.areEqual(this.getEffectiveToDate(),
                                 originalDTO.getEffectiveToDate())) {

            // Something already changed but NOT the dates
            whatChanged = WhatChanged.NOT_DATES;

        } else if (SQLDateUtil.areEqual(this.getEffectiveFromDate(),
                                        originalDTO.getEffectiveFromDate())) {

            // TO date has been changed
            whatChanged = WhatChanged.ONLY_TO_DATE;

        } else if (SQLDateUtil.areEqual(this.getEffectiveToDate(),
                                        originalDTO.getEffectiveToDate())) {

            // FROM date has been changed
            whatChanged = WhatChanged.ONLY_FROM_DATE;

        } else {

            // BOTH dates have been changed
            whatChanged = WhatChanged.BOTH_DATES;

        }

        return whatChanged;
    }

    public enum WhatChanged {
        BOTH_DATES,
        ONLY_FROM_DATE,
        ONLY_TO_DATE,
        NOT_DATES,
        NOTHING;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private Date EffectiveFromDate;
    private Date EffectiveToDate;

    public void setEffectiveFromDate(Date EffectiveFromDate) {
        this.EffectiveFromDate = EffectiveFromDate;
    }

    public Date getEffectiveFromDate() {
        return EffectiveFromDate;
    }

    public void setEffectiveToDate(Date EffectiveToDate) {
        this.EffectiveToDate = EffectiveToDate;
    }

    public Date getEffectiveToDate() {
        return EffectiveToDate;
    }
}
