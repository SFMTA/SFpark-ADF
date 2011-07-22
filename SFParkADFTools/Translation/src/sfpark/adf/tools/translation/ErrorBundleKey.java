package sfpark.adf.tools.translation;

public enum ErrorBundleKey {

    error_insufficient_privileges,

    error_invalid_request_parameters_1,
    error_invalid_request_parameters_2,
    error_invalid_request_parameters_3,

    error_create_failure,
    error_save_failure,
    error_invalid_login,

    error_exception_save_failure,

    error_invalid_email,
    error_invalid_url,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Asset Manager Errors

    error_create_parking_space_failure,

    error_invalid_asset_manager_parameters,

    error_invalid_asset_manager_bulk_parameters,

    error_not_exists_parking_space,

    error_not_exists_parking_space_group,

    error_not_exists_blockface,
    error_not_exists_blockface_long_lat,

    error_not_work_postid_and_blockface,

    error_not_work_postid_and_mspaystationid,
    error_bulk_not_work_postid_and_mspaystationid,
    error_bulk_invalid_mspaystationid,

    error_off_street_needs_osp_id,

    error_invalid_cnn_id,

    error_exists_already_post_id,

    error_meter_schedule_date_to_before_from,
    error_meter_rate_date_to_before_from,

    error_meter_schedule_time_to_zero,
    error_meter_rate_time_to_zero,

    error_meter_schedule_time_to_before_from,
    error_meter_rate_time_to_before_from,

    error_meter_schedule_op_invalid_cap_color,

    error_bulk_meter_schedule_op_invalid_cap_color,

    error_meter_schedule_invalid_unique_constraints,
    error_meter_rate_invalid_unique_constraints,

    error_parking_space_exception_unique_constraint,
    error_parking_space_exception_schedule_insert,
    error_parking_space_exception_rate_insert,
    error_parking_space_exception_schedule_update,
    error_parking_space_exception_rate_update,
    error_parking_space_exception_inventory_update,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // OSP Manager Errors

    error_invalid_osp_manager_parameters,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // NULL Error

    error_null;
}
