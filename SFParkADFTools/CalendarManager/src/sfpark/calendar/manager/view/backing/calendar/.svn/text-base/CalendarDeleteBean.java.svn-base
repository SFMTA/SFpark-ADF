package sfpark.calendar.manager.view.backing.calendar;

import javax.faces.event.ActionEvent;

import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;

import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.CSSClasses;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.calendar.manager.application.key.PageFlowScopeKey;
import sfpark.calendar.manager.application.key.SessionScopeKey;
import sfpark.calendar.manager.view.backing.BaseBean;
import sfpark.calendar.manager.view.flow.NavigationFlow;
import sfpark.calendar.manager.view.provider.DMLOperationsProvider;

public class CalendarDeleteBean extends BaseBean implements RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public CalendarDeleteBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void clearPageFlowScopeCache() {
    }

    public void setInlineMessageText(String inlineMessageText) {
        this.setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey(),
                                   inlineMessageText);
    }

    public String getInlineMessageText() {
        return (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey());
    }

    public void setInlineMessageClass(String inlineMessageClass) {
        this.setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey(),
                                   inlineMessageClass);
    }

    public String getInlineMessageClass() {
        return (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public CalendarHeaderDTO getCalendarHeaderDTO() {
        CalendarHeaderDTO DTO =
            (CalendarHeaderDTO)getPageFlowScopeValue(PageFlowScopeKey.CALENDAR_HEADER_DTO.getKey());

        if (DTO == null) {
            // Should NOT happen
            DTO = new CalendarHeaderDTO();
        }

        return DTO;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void deleteButtonHandler(ActionEvent event) {
        CalendarHeaderDTO calendarHeaderDTO = getCalendarHeaderDTO();

        OperationStatus operationStatus =
            DMLOperationsProvider.INSTANCE.deleteCalendar(calendarHeaderDTO);

        if (operationStatus.isSuccess()) {
            // Move on to the next page
            // Reuse the ERROR_TITLE and ERROR_MESSAGE variables
            setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                  TranslationUtil.getCommonBundleString(CommonBundleKey.string_title_delete_operation_successful));
            setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                  TranslationUtil.getCommonBundleString(CommonBundleKey.string_message_delete_calendar_successful,
                                                                        calendarHeaderDTO.getCalendarName()));
            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                 NavigationFlow.AfterDeleteCalendar.name());

        } else {
            setInlineMessageText(TranslationUtil.getErrorBundleString(ErrorBundleKey.error_delete_failure));
            setInlineMessageClass(CSSClasses.INLINE_MESSAGE_FAILURE);
        }
    }
}
