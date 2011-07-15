package sfpark.osp.manager.view.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.helper.GarageRatesQualifier;
import sfpark.adf.tools.model.data.helper.GarageRatesTimeBand;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

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
    // Data Feed Flag Util

    public static final List<SelectItem> DATA_FEED_FLAG_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                add(new SelectItem("Y", "Y - Yes"));
                add(new SelectItem("N", "N - No"));
                add(new SelectItem("S", "S - Suspend"));
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

    @Deprecated
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
                for (GarageRatesQualifier qualifier :
                     GarageRatesQualifier.values()) {
                    add(new SelectItem(qualifier,
                                       qualifier.getRateQualifierText()));
                }
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
    // Time Band Util

    public static final List<SelectItem> TIME_BAND_LIST =
        Collections.unmodifiableList(new ArrayList<SelectItem>() {
            {
                for (GarageRatesTimeBand timeBand :
                     GarageRatesTimeBand.values()) {

                    String label;

                    switch (timeBand) {

                    case NULL:
                        {
                            label = "-";
                        }
                        break;

                    default:
                        {
                            label =
                                    TimeDisplayUtil.extractFromTimeForHumans(timeBand.getFromTime()) +
                                    " - " +
                                    TimeDisplayUtil.extractToTimeForHumans(timeBand.getToTime());
                        }
                        break;
                    }

                    add(new SelectItem(timeBand, label));
                }
            }
        });
}
