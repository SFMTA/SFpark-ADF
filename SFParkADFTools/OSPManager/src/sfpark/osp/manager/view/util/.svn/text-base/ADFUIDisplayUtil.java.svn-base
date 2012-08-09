package sfpark.osp.manager.view.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import sfpark.adf.tools.model.data.helper.GarageRatesQualifier;
import sfpark.adf.tools.model.data.helper.GarageRatesTimeBand;
import sfpark.adf.tools.utilities.generic.TimeDisplayUtil;

/**
 * This ADFUIDisplayUtil is ONLY for the OSPManager Project
 * 
 */
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
