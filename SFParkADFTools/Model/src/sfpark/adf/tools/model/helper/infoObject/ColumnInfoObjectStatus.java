package sfpark.adf.tools.model.helper.infoObject;

import sfpark.adf.tools.model.data.infoObject.column.ColumnInfoObject;

public class ColumnInfoObjectStatus implements InfoObjectStatusInterface {

    private boolean exists;
    private ColumnInfoObject columnInfoObject;

    public ColumnInfoObjectStatus(ColumnInfoObject columnInfoObject) {
        super();

        this.exists = (columnInfoObject != null);
        this.columnInfoObject = columnInfoObject;
    }

    public boolean existsInfoObject() {
        return exists;
    }

    public ColumnInfoObject getInfoObject() {
        return columnInfoObject;
    }
}
