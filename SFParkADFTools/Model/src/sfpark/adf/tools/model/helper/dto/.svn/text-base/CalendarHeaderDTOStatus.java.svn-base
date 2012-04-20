package sfpark.adf.tools.model.helper.dto;

import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;

public class CalendarHeaderDTOStatus implements DTOStatusInterface {

    private boolean exists;
    private CalendarHeaderDTO DTO;

    public CalendarHeaderDTOStatus(CalendarHeaderDTO DTO) {
        super();

        this.exists = (DTO != null);
        this.DTO = DTO;
    }

    public boolean existsDTO() {
        return exists;
    }

    public CalendarHeaderDTO getDTO() {
        return DTO;
    }
}
