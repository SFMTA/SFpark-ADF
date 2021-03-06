package sfpark.adf.tools.translation;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111130-01 Mark Piller - Oracle Consulting  add string_title_finalize_operation_successful
 *                                              add string_message_finalize_operation_successful
 *                                              add string_title_reset_operation_successful
 *                                              add string_message_reset_rate_change_process_control_successful
 */
public enum CommonBundleKey {

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Title Strings

    string_title_delete_operation_successful,
    string_title_finalize_operation_successful,
    string_title_execute_operation_successful,
    string_title_edit_operation_successful,
    string_title_reset_operation_successful,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Message Strings

    string_message_delete_operation_successful,
    string_message_execute_operation_successful,
    string_message_finalize_operation_successful,

    string_message_delete_calendar_successful,
    string_message_delete_rate_change_successful,
    string_message_delete_rate_change_process_control_successful,
    string_message_reset_rate_change_process_control_successful,

    string_message_edit_rate_change_rules_successful,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Warning Strings

    warning_title_finalised_eff_date,
    warning_message_finalised_eff_date,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    info_only_digits_allowed,
    info_from_time,
    info_to_time,

    info_us_phone_number,
    info_website_url,

    info_max_amount,

    info_nothing_to_save,

    info_success_save,

    info_success_create,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    feedback_unsaved_data_title,
    feedback_unsaved_data_text,

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    info_null;
}
