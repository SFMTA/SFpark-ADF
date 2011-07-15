package sfpark.adf.tools.model.helper.dto;

import sfpark.adf.tools.model.data.dto.ospInventory.OSPInventoryDTO;

public class OSPInventoryDTOStatus implements DTOStatusInterface {

    private boolean exists;
    private OSPInventoryDTO DTO;

    public OSPInventoryDTOStatus(OSPInventoryDTO DTO) {
        super();

        this.exists = (DTO != null);
        this.DTO = DTO;
    }

    public boolean existsDTO() {
        return exists;
    }

    public OSPInventoryDTO getDTO() {
        return DTO;
    }
}
