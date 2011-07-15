package sfpark.adf.tools.model.helper.dO;

import sfpark.adf.tools.model.data.dO.BaseDO;

interface DOStatusInterface {

    public boolean existsDO();

    public <T extends BaseDO> T getDO();
}
