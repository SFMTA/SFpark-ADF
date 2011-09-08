package sfpark.adf.tools.model.helper.dto;

import sfpark.adf.tools.model.data.dto.blockRateSchedule.BlockRateScheduleDTO;

public class BlockRateScheduleDTOStatus implements DTOStatusInterface {

    private boolean exists;
    private BlockRateScheduleDTO DTO;

    public BlockRateScheduleDTOStatus(BlockRateScheduleDTO DTO) {
        super();

        this.exists = (DTO != null);
        this.DTO = DTO;
    }

    public boolean existsDTO() {
        return exists;
    }

    public BlockRateScheduleDTO getDTO() {
        return DTO;
    }
}
