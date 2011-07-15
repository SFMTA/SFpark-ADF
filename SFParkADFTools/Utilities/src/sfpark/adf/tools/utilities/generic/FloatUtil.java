package sfpark.adf.tools.utilities.generic;

public final class FloatUtil {
    /**
     * To avoid instantiation
     */
    private FloatUtil() {
    }

    public static boolean areEqual(float value1, float value2, int places) {

        int precision = (int)Math.pow(10, places);

        int intValue1 = Math.round(value1 * precision);
        int intValue2 = Math.round(value2 * precision);

        return (intValue1 == intValue2);
    }
}
