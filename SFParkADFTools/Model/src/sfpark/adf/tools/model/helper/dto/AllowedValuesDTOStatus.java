package sfpark.adf.tools.model.helper.dto;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;

public class AllowedValuesDTOStatus implements DTOStatusInterface {

    private boolean exists;
    private AllowedValuesDTO DTO;

    public AllowedValuesDTOStatus(AllowedValuesDTO DTO) {
        super();

        this.exists = (DTO != null);
        this.DTO = DTO;
    }

    public boolean existsDTO() {
        return exists;
    }

    public AllowedValuesDTO getDTO() {
        return DTO;
    }
}
