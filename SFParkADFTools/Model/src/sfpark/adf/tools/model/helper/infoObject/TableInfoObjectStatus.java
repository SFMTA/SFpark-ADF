package sfpark.adf.tools.model.helper.infoObject;

import sfpark.adf.tools.model.data.infoObject.table.TableInfoObject;

public class TableInfoObjectStatus implements InfoObjectStatusInterface {

    private boolean exists;
    private TableInfoObject tableInfoObject;

    public TableInfoObjectStatus(TableInfoObject tableInfoObject) {
        super();

        this.exists = (tableInfoObject != null);
        this.tableInfoObject = tableInfoObject;
    }

    public boolean existsInfoObject() {
        return exists;
    }

    public TableInfoObject getInfoObject() {
        return tableInfoObject;
    }
}
