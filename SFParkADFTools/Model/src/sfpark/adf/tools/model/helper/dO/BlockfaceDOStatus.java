package sfpark.adf.tools.model.helper.dO;

import sfpark.adf.tools.model.data.dO.blockface.BlockfaceDO;

public class BlockfaceDOStatus implements DOStatusInterface {

    private boolean exists;
    private BlockfaceDO blockfaceDO;
    private String distance;

    public BlockfaceDOStatus(BlockfaceDO blockfaceDO, String distance) {
        super();

        this.exists = (blockfaceDO != null);
        this.blockfaceDO = blockfaceDO;
        this.distance = distance;
    }

    public boolean existsDO() {
        return exists;
    }

    public BlockfaceDO getDO() {
        return blockfaceDO;
    }

    public String getDistance() {
        return distance;
    }
}
