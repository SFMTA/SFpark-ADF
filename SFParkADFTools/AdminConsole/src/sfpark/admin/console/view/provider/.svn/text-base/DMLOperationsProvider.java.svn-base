package sfpark.admin.console.view.provider;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.share.ADFContext;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.model.data.dto.allowedValues.AllowedValuesDTO;
import sfpark.adf.tools.model.data.dto.timeBandModel.TimeBandModelDTO;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.helper.TableRecord;
import sfpark.adf.tools.model.provider.AllowedValuesProvider;
import sfpark.adf.tools.model.provider.AllowedValuesRetriever;

import sfpark.adf.tools.model.provider.ProviderWrapper;

import sfpark.admin.console.view.helper.TimeBandModelWrapper;

public class DMLOperationsProvider {
    private static final String CLASSNAME =
        DMLOperationsProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private DMLOperationsProvider() {
        super();
    }

    public static final DMLOperationsProvider INSTANCE =
        new DMLOperationsProvider();

    private static final String LAST_UPDATED_PROGRAM = "Admin Console";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Methods to create new DTOs

    public TimeBandModelWrapper getNewTimeBandModelWrapper() {
        LOGGER.entering(CLASSNAME, "getNewTimeBandModelWrapper");

        TimeBandModelWrapper wrapper = new TimeBandModelWrapper();

        wrapper.setMeterClass(AllowedValuesRetriever.getMeterClassDefaultValue());
        wrapper.setDateType(AllowedValuesRetriever.getDateTypeDefaultValue());

        LOGGER.exiting(CLASSNAME, "getNewTimeBandModelWrapper");

        return wrapper;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Actual operations

    public OperationStatus addAllowedValue(AllowedValuesDTO allowedValuesDTO) {
        LOGGER.entering(CLASSNAME, "addAllowedValue");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                         allowedValuesDTO));

        LOGGER.exiting(CLASSNAME, "addAllowedValue");

        return performOperation(tableRecords);
    }

    public OperationStatus editAllowedValue(AllowedValuesDTO allowedValuesDTO) {
        LOGGER.entering(CLASSNAME, "editAllowedValue");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        AllowedValuesDTO originalDTO =
            AllowedValuesProvider.INSTANCE.checkForAllowedValuesData(allowedValuesDTO.getTableName(),
                                                                     allowedValuesDTO.getColumnName(),
                                                                     allowedValuesDTO.getColumnValue()).getDTO();

        if (!allowedValuesDTO.isSameAs(originalDTO)) {
            tableRecords.add(new TableRecord(TableRecord.SQLOperation.UPDATE,
                                             allowedValuesDTO));
        }

        LOGGER.exiting(CLASSNAME, "editAllowedValue");

        return performOperation(tableRecords);
    }

    public OperationStatus deleteAllowedValue(AllowedValuesDTO allowedValuesDTO) {
        LOGGER.entering(CLASSNAME, "deleteAllowedValue");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                         allowedValuesDTO));

        LOGGER.exiting(CLASSNAME, "deleteAllowedValue");

        return performOperation(tableRecords);
    }

    public OperationStatus addTimebands(List<TimeBandModelDTO> toBeInsertedTimeBandModelDTOs,
                                        List<TimeBandModelDTO> toBeDeletedTimeBandModelDTOs) {
        LOGGER.entering(CLASSNAME, "addTimebands");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // DELETE old DTOs
        // Old rows need to be deleted before new rows can be added to avoid
        // unique constraint exception

        if (toBeDeletedTimeBandModelDTOs != null &&
            !toBeDeletedTimeBandModelDTOs.isEmpty()) {

            for (TimeBandModelDTO deleteDTO : toBeDeletedTimeBandModelDTOs) {
                tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                                 deleteDTO));
            }
        }

        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++
        // ADD new DTOs

        if (toBeInsertedTimeBandModelDTOs != null &&
            !toBeInsertedTimeBandModelDTOs.isEmpty()) {

            for (TimeBandModelDTO insertDTO : toBeInsertedTimeBandModelDTOs) {
                tableRecords.add(new TableRecord(TableRecord.SQLOperation.INSERT,
                                                 insertDTO));
            }
        }

        LOGGER.exiting(CLASSNAME, "addTimebands");

        return performOperation(tableRecords);
    }

    public OperationStatus deleteTimebands(List<TimeBandModelDTO> toBeDeletedTimeBandModelDTOs) {
        LOGGER.entering(CLASSNAME, "deleteTimeBands");

        List<TableRecord> tableRecords = new ArrayList<TableRecord>();

        if (toBeDeletedTimeBandModelDTOs != null &&
            !toBeDeletedTimeBandModelDTOs.isEmpty()) {

            for (TimeBandModelDTO deleteDTO : toBeDeletedTimeBandModelDTOs) {
                tableRecords.add(new TableRecord(TableRecord.SQLOperation.DELETE,
                                                 deleteDTO));
            }
        }

        LOGGER.exiting(CLASSNAME, "deleteTimeBands");

        return performOperation(tableRecords);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private OperationStatus performOperation(List<TableRecord> tableRecords) {
        OperationStatus operationStatus = null;

        if (!tableRecords.isEmpty()) {

            operationStatus =
                    ProviderWrapper.INSTANCE.performOperationOnRecords(tableRecords,
                                                                       getLoggedInUsername(),
                                                                       LAST_UPDATED_PROGRAM);
        }

        return operationStatus;
    }

    private String getLoggedInUsername() {
        return ADFContext.getCurrent().getSecurityContext().getUserName();
    }
}
