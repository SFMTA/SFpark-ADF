package sfpark.calendar.manager.view.backing.common;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.fragment.RegionSite;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.model.RegionModel;

import sfpark.adf.tools.helper.Logger;

import sfpark.adf.tools.helper.SignedInUser;
import sfpark.adf.tools.model.data.dto.calendar.CalendarHeaderDTO;
import sfpark.adf.tools.model.data.helper.CalendarType;
import sfpark.adf.tools.model.helper.dto.CalendarHeaderDTOStatus;
import sfpark.adf.tools.model.provider.CalendarHeaderProvider;
import sfpark.adf.tools.model.provider.RateChangeHeaderProvider;
import sfpark.adf.tools.translation.ErrorBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;
import sfpark.adf.tools.utilities.constants.RequestParameter;

import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.calendar.manager.application.key.PageFlowScopeKey;
import sfpark.calendar.manager.application.key.ParameterKey;
import sfpark.calendar.manager.application.key.SessionScopeKey;
import sfpark.calendar.manager.application.value.OperationValue;
import sfpark.calendar.manager.view.backing.BaseBean;
import sfpark.calendar.manager.view.flow.NavigationFlow;
import sfpark.calendar.manager.view.flow.NavigationMode;

public class RateChangeNavigationBean extends BaseBean {

    private static final String CLASSNAME =
        RateChangeNavigationBean.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private RichRegion ContentRichRegion;

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public RateChangeNavigationBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ACCESSORS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS EXTRA

    public RegionModel getContentRegionModel() {
        RegionModel model = new RegionModel() {
            public String getViewId(FacesContext facesContext) {

                decideCurrentNavFlow();

                String currentNav =
                    (String)getSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey());

                if (currentNav == null) {
                    currentNav = NavigationFlow.ERROR.name();
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         currentNav);
                }

                NavigationFlow currentNavFlow =
                    NavigationFlow.valueOf(currentNav);

                return currentNavFlow.getFileName();
            }

            @Override
            public void processBeginRegion(FacesContext facesContext,
                                           RegionSite regionSite) {
                // RegionModel throws UnsupportedOperationException here, so
                // we need to override it.
            }

            @Override
            public void processEndRegion(FacesContext facesContext,
                                         RegionSite regionSite) {
                // RegionModel throws UnsupportedOperationException here, so
                // we need to override it.
            }

        };

        return model;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    public void setContentRichRegion(RichRegion ContentRichRegion) {
        this.ContentRichRegion = ContentRichRegion;
    }

    public RichRegion getContentRichRegion() {
        return ContentRichRegion;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void decideCurrentNavFlow() {
        LOGGER.entering(CLASSNAME, "decideCurrentNavFlow");

        HttpServletRequest request = getHttpRequest();
        String httpMethod = request.getMethod();
        String ADFCtrlCurrent = getCurrentADFControl();
        String ADFCtrlFromURL =
            getRequestParameterValue(RequestParameter.ADF_CTRL_STATE.getKey());

        if ( // HTTP Method is GET
            httpMethod.equalsIgnoreCase("GET") &&

            // It is a new Faces Context Control State
            !StringUtil.areEqual(ADFCtrlCurrent, ADFCtrlFromURL)) {

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // NEW Request encountered
            // Start all processes from scratch

            // Clear all cache
            clearAllCache();

            // Set the ADF Control Value
            setCurrentADFControl(ADFCtrlFromURL);

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // Retrieve the parameters

            String operationStr =
                getRequestParameterValue(ParameterKey.OPERATION.getKey());
            String calendarID =
                getRequestParameterValue(ParameterKey.CALENDAR_ID.getKey());

            // Interpret NULL calendar ID as ZERO
            if (StringUtil.isBlank(calendarID)) {
                calendarID = "0";
            }

            // Convert operation String into OperationValue
            OperationValue operation = OperationValue.extract(operationStr);

            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++
            // +++++++++++++++++++++++++++++++++++++++++++++++++++++

            if ( // VALID Parameters
                !operation.isUnknown() &&
                StringUtil.isDigitsONLY(calendarID)) {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++

                if ( // Can Operate Rate Change Calendars
                    SignedInUser.canOperateRateChangeCalendars()) {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++

                    if ( // ADD Operation
                        operation.isAdd()) {
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        LOGGER.debug("ADD Mode");
                        setPageFlowScopeValue(PageFlowScopeKey.CALENDAR_TYPE.getKey(),
                                              CalendarType.RATE_CHANGE.name());
                        setCurrentPageMode(NavigationMode.ADD);
                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                             NavigationFlow.AddCalendar.name());

                    } else { // Other Modes
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        // ++++++++++++++++++++++++++++++++++
                        CalendarHeaderDTOStatus calendarStatus =
                            CalendarHeaderProvider.INSTANCE.checkForCalendarID(calendarID);

                        if ( // Calendar EXISTS
                            calendarStatus.existsDTO()) {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            CalendarHeaderDTO calendarHeaderDTO =
                                calendarStatus.getDTO();

                            if ( // Rate Change Calendar
                                calendarHeaderDTO.getCalendarType().isRateChange()) {
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++

                                if ( // EDIT Mode
                                    operation.isEdit()) {
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    setPageFlowScopeValue(PageFlowScopeKey.CALENDAR_HEADER_DTO.getKey(),
                                                          calendarHeaderDTO);

                                    if ( // LOCKED Calendar
                                        calendarHeaderDTO.getStatus().isLocked()) {
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        LOGGER.debug("READ_ONLY Mode");
                                        setCurrentPageMode(NavigationMode.READ_ONLY);
                                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                             NavigationFlow.ReadOnlyCalendar.name());
                                    } else {
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        LOGGER.debug("EDIT Mode");
                                        setCurrentPageMode(NavigationMode.EDIT);
                                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                             NavigationFlow.EditCalendar.name());
                                    }

                                } else if ( // DELETE Mode
                                    operation.isDelete()) {
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    boolean notUsed =
                                        RateChangeHeaderProvider.INSTANCE.getRateChangeHeaderDTOsForCalendarID(calendarID).isEmpty();

                                    if ( // NOT used
                                        notUsed &&
                                        // NOT locked
                                        !calendarHeaderDTO.getStatus().isLocked()) {
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        LOGGER.debug("DELETE Mode");
                                        setPageFlowScopeValue(PageFlowScopeKey.CALENDAR_HEADER_DTO.getKey(),
                                                              calendarHeaderDTO);
                                        setCurrentPageMode(NavigationMode.DELETE);
                                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                             NavigationFlow.DeleteCalendar.name());

                                    } else {
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        // ++++++++++++++++++++++++++++++++++
                                        LOGGER.warning("Unsupported Operation - Can not delete, already in use");
                                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                                              TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_unsupported_operation));
                                        setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                              TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_unsupported_calendar_delete_operation,
                                                                                                   calendarID));
                                        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                             NavigationFlow.ERROR.name());
                                    }

                                } else {
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    // ++++++++++++++++++++++++++++++++++
                                    LOGGER.warning("Unsupported Operation");
                                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_unsupported_operation));
                                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_unsupported_operation,
                                                                                               operationStr));
                                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                         NavigationFlow.ERROR.name());
                                }

                            } else {
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                // ++++++++++++++++++++++++++++++++++
                                LOGGER.warning("NOT a Rate Change Calendar");
                                setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_unsupported_resource));
                                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_unsupported_calendar_resource_not_rate_change,
                                                                                           calendarID));
                                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                     NavigationFlow.ERROR.name());
                            }

                        } else {
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            // ++++++++++++++++++++++++++++++++++
                            LOGGER.warning("Calendar does not exist");
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_nonexistent_resource));
                            setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                                  TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_nonexistent_calendar_id_resource,
                                                                                       calendarID));
                            setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                                 NavigationFlow.ERROR.name());
                        }

                    }

                } else {
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    // ++++++++++++++++++++++++++++++++++
                    LOGGER.warning("Insuffient Privileges: User without RATE CHANGE access.");
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_insufficient_privileges));
                    setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                          TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_insufficient_privileges));
                    setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                         NavigationFlow.ERROR.name());
                }

            } else {
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                // ++++++++++++++++++++++++++++++++++
                LOGGER.warning("Invalid Parameters");
                setPageFlowScopeValue(PageFlowScopeKey.ERROR_TITLE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_title_invalid_parameters));

                setPageFlowScopeValue(PageFlowScopeKey.ERROR_MESSAGE.getKey(),
                                      TranslationUtil.getErrorBundleString(ErrorBundleKey.error_message_invalid_calendar_manager_parameters,
                                                                           operationStr,
                                                                           calendarID));
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO.getKey(),
                                     NavigationFlow.ERROR.name());
            }

        } else if ( // HTTP Method is POST
            httpMethod.equalsIgnoreCase("POST")) {

            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++

            setCurrentADFControl(ADFCtrlFromURL);
        }

        LOGGER.exiting(CLASSNAME, "decideCurrentNavFlow");
    }

    private void clearAllCache() {

        for (PageFlowScopeKey pfsKey : PageFlowScopeKey.values()) {
            removePageFlowScopeValue(pfsKey.getKey());
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CURRENT ADF CONTROL MECHANISM

    private String currentADFControl = "";

    protected void setCurrentADFControl(String currentADFControl) {
        this.currentADFControl = currentADFControl;
    }

    protected String getCurrentADFControl() {
        return currentADFControl;
    }
}
