package sfpark.adf.tools.translation;

public enum ErrorBundleKey {

    error_title_invalid_parameters,
    error_title_insufficient_privileges,
    error_title_nonexistent_resource,
    error_title_unsupported_operation,
    error_title_unsupported_resource,

    error_message_insufficient_privileges,
    error_message_unsupported_operation,

    error_insufficient_privileges,

    error_invalid_request_parameters_1,
    error_invalid_request_parameters_2,
    error_invalid_request_parameters_3,

    error_create_failure,
    error_save_failure,
    error_delete_failure,
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

    error_bulk_meter_invalid_to_cap_color,
    error_bulk_meter_invalid_from_cap_color,

    error_bulk_meter_invalid_from_active_meter_status,

    error_bulk_meter_invalid_meter_details,

    error_bulk_could_not_load_parking_spaces,

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
    // Calendar Manager Errors

    error_message_invalid_calendar_manager_parameters,

    error_message_nonexistent_calendar_id_resource,

    error_message_unsupported_calendar_resource_not_rate_change,

    error_message_unsupported_calendar_delete_operation,

    error_exists_already_calendar_name,

    error_empty_calendar_detail_table,

    error_create_calendar_name_failure,

    error_calendar_exception_calendar_header_update,
    error_calendar_exception_unique_constraint,
    error_calendar_exception_calendar_detail_insert,
    error_calendar_exception_calendar_detail_delete,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Rate Change Manager Errors

    error_message_invalid_rate_change_manager_parameters,
    error_message_invalid_rate_change_manager_deploy_parameters,
    error_message_invalid_rate_change_manager_modify_parameters,

    error_message_nonexistent_rate_change_reference_id_resource,
    error_message_nonexistent_process_id_resource,
    error_message_nonexistent_block_rate_sched_id_resource,

    error_message_unsupported_block_rate_sched_modify_operation,

    error_message_unsupported_rate_change_update_operation,
    error_message_unsupported_rate_change_delete_operation,
    error_message_unsupported_rate_change_deploy_operation,

    error_message_unsupported_rate_change_process_edit_operation,
    error_message_unsupported_rate_change_process_delete_operation,

    error_exists_already_rate_change_reference,

    error_create_rate_change_reference_failure,

    error_invalid_calendar_id,

    error_invalid_meter_vendor,

    error_exists_already_rate_chg_ref,

    error_date_approved_before_submitted,

    error_exception_rate_change_update,

    error_exception_rate_change_process_control_update,

    error_exception_rate_change_process_control_execute,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // NULL Error

    error_null;
}
