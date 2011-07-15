package sfpark.event.manager.view.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.event.manager.view.provider.helper.eventCalendar.EventCalendarDateDAO;

public final class ADFUIDisplayUtil {

    /**
     * To avoid instantiation
     */
    private ADFUIDisplayUtil() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Date Type Util

    public static final List<SelectItem> DATE_TYPE_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem(EventCalendarDateDAO.DateType.NO_OP, "-"));
                add(new SelectItem(EventCalendarDateDAO.DateType.INSERT,
                                   "To be added"));
                add(new SelectItem(EventCalendarDateDAO.DateType.DELETE,
                                   "To be deleted"));
            }
        });

    /*
    // Facility Type Util

    public static final List<SelectItem> FACILITY_TYPE_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("G", "G - Garage"));
                add(new SelectItem("L", "L - Lot"));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Owner Util

    public static final List<SelectItem> OWNER_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("CALTRANS"));
                add(new SelectItem("Port"));
                add(new SelectItem("RPD"));
                add(new SelectItem("SFMTA"));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Services Util

    public static final List<SelectItem> SERVICES_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("ATM"));
                add(new SelectItem("bike parking"));
                add(new SelectItem("carpool/carshare rate"));
                add(new SelectItem("early bird rate"));
                add(new SelectItem("electric vehicle charger"));
                add(new SelectItem("juror rate"));
                add(new SelectItem("monthly parker shuttle to CPMC"));
                add(new SelectItem("motorcycle rate"));
                add(new SelectItem("reduced night rate"));
                add(new SelectItem("reduced night/wknd rates"));
                add(new SelectItem("special event parking"));
                add(new SelectItem("student rate"));
                add(new SelectItem("valet parking"));
                add(new SelectItem("validation"));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Display Group Util

    public static final List<SelectItem> DISPLAY_GROUP_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem(new Integer(1), "1 - Hourly Rates"));
                add(new SelectItem(new Integer(2),
                                   "2 - Flat Rates / Discounts"));
                add(new SelectItem(new Integer(3), "3 - Monthly Rates"));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Rate Qualifier Util

    public static final List<SelectItem> RATE_QUALIFIER_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("Flat rate"));
                add(new SelectItem("Off total"));
                add(new SelectItem("Off per hour"));
                add(new SelectItem("Per day"));
                add(new SelectItem("Per hour"));
                add(new SelectItem("Per month"));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Display Code Util

    public static final List<SelectItem> DISPLAY_CODE_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("Y",
                                   "Y - Normal Display using RATE_DESC and RATE_RESTRICTIONS"));
                add(new SelectItem("N", "N - Do not use RATE_RESTRICTIONS"));
                add(new SelectItem("O",
                                   "O - Override the RATE_DESC with the value in RATE_RESTRICTIONS"));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Weekday Util

    public static final List<SelectItem> WEEKDAY_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("Monday"));
                add(new SelectItem("Tuesday"));
                add(new SelectItem("Wednesday"));
                add(new SelectItem("Thursday"));
                add(new SelectItem("Friday"));
                add(new SelectItem("Saturday"));
                add(new SelectItem("Sunday"));
            }
        });

    public static final List<SelectItem> WEEKDAY_WITH_NULL_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                addAll(WEEKDAY_LIST);
                add(new SelectItem(null, "-"));
            }
        });

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
}
