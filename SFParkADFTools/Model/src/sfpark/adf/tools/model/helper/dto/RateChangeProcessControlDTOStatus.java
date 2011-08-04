package sfpark.adf.tools.model.helper.dto;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;

public class RateChangeProcessControlDTOStatus implements DTOStatusInterface {

    private boolean exists;
    private RateChangeProcessControlDTO DTO;

    public RateChangeProcessControlDTOStatus(RateChangeProcessControlDTO DTO) {
        super();

        this.exists = (DTO != null);
        this.DTO = DTO;
    }

    public boolean existsDTO() {
        return exists;
    }

    public RateChangeProcessControlDTO getDTO() {
        return DTO;
    }
}
