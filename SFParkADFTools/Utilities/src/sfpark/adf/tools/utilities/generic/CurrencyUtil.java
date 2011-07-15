package sfpark.adf.tools.utilities.generic;

public final class CurrencyUtil {
    /**
     * To avoid instantiation
     */
    private CurrencyUtil() {
    }

    public static boolean areEqual(float value1, float value2) {

        if (value1 < 0 || value2 < 0) {
            throw new IllegalArgumentException("Currency cannot be negative.");
        }

        return FloatUtil.areEqual(value1, value2, 2);
    }
}
