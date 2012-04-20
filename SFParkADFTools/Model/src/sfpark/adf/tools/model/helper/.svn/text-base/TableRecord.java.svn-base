package sfpark.adf.tools.model.helper;

import sfpark.adf.tools.model.data.dto.BaseDTO;

public class TableRecord {

    public enum SQLOperation {

        INSERT,
        UPDATE,
        DELETE;

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        public boolean isInsert() {
            return (this == INSERT);
        }

        public boolean isUpdate() {
            return (this == UPDATE);
        }

        public boolean isDelete() {
            return (this == DELETE);
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private SQLOperation operation;
    private BaseDTO record;

    public TableRecord(TableRecord.SQLOperation operation, BaseDTO record) {
        super();
        this.operation = operation;
        this.record = record;
    }

    public TableRecord.SQLOperation getOperation() {
        return operation;
    }

    public BaseDTO getRecord() {
        return record;
    }
}
