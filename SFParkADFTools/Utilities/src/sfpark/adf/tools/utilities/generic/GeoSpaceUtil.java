package sfpark.adf.tools.utilities.generic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sfpark.adf.tools.utilities.constants.RegularExpression;

public final class GeoSpaceUtil {
    /**
     * To avoid instantiation
     */
    private GeoSpaceUtil() {
    }

    private static final Map<String, Integer> DirectionToValueOrientation =
        Collections.unmodifiableMap(new HashMap<String, Integer>() {
            {
                put("N", 0);
                put("NE", 45);
                put("E", 90);
                put("SE", 135);
                put("S", 180);
                put("SW", 225);
                put("W", 270);
                put("NW", 315);
            }
        });

    private static final Map<String, String> ValueToDirectionOrientation =
        Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                put("0", "N");
                put("45", "NE");
                put("90", "E");
                put("135", "SE");
                put("180", "S");
                put("225", "SW");
                put("270", "W");
                put("315", "NW");
            }
        });

    /**
     * Checks if the string represents a valid Longitude or not
     *
     * @param longitude
     * @return
     */
    public static boolean isLongitudeValid(String longitude) {
        if (StringUtil.isBlank(longitude)) {
            return false;
        }

        return longitude.matches(RegularExpression.LONGITUDE_REGEX.getRegEx());
    }

    /**
     * Checks if the string represents a valid Latitude or not
     *
     * @param latitude
     * @return
     */
    public static boolean isLatitudeValid(String latitude) {
        if (StringUtil.isBlank(latitude)) {
            return false;
        }

        return latitude.matches(RegularExpression.LATITUDE_REGEX.getRegEx());
    }

    /**
     * Converts Orientation from Digit to String equivalent
     *
     * @param value
     * @return
     */
    public static String convertOrientationDirectionFromValue(String value) {
        String direction = ValueToDirectionOrientation.get(value);

        return direction;
    }

    /**
     * Converts Orientation from String to Digit equivalent
     *
     * @param direction
     * @return
     */
    public static int getOrientationValueFromDirection(String direction) {
        Integer value = DirectionToValueOrientation.get(direction);

        return value;
    }
}
