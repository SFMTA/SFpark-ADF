package sfpark.adf.tools.model.helper.dto;

import sfpark.adf.tools.model.data.dto.parkingSpaceInventory.ParkingSpaceInventoryDTO;

public class ParkingSpaceInventoryDTOStatus implements DTOStatusInterface {
    
  private boolean exists;
  private ParkingSpaceInventoryDTO DTO;

    public ParkingSpaceInventoryDTOStatus(ParkingSpaceInventoryDTO DTO) {
        super();
        
        this.exists = (DTO != null);
        this.DTO = DTO;
    }

    public boolean existsDTO() {
        return exists;
    }

    public ParkingSpaceInventoryDTO getDTO() {
        return DTO;
    }
}
