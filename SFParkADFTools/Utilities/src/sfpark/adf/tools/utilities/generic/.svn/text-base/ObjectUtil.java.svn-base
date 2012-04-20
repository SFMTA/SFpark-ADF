package sfpark.adf.tools.utilities.generic;

import java.sql.Date;

import java.util.ArrayList;
import java.util.Collection;

public final class ObjectUtil {

    /**
     * To avoid instantiation
     */
    private ObjectUtil() {
        super();
    }

    /**
     * Returns a non-null Object of the type 'T'
     * Used for derived data types
     *
     * @param <T>
     * @param value
     * @return non-null Derived data type
     */
    public static <T extends Object> T getNullSafe(T value) {
        return (value == null) ? (T)new Object() {
            public String toString() {
                return "NULL Object";
            }
        } : value;
    }

    /**
     * Returns a non-null Collection of the type 'T'
     *
     * @param <T>
     * @param value
     * @return non-null Collection
     */
    public static <T extends Object> Collection<T> getNullSafe(Collection<T> value) {
        return (value == null) ? (Collection<T>)new ArrayList<T>() : value;
    }

    /**
     * Returns a non-null String
     *
     * @param value
     * @return non-null String
     */
    public static String getNullSafe(String value) {
        return (value == null) ? "" : value;
    }

    /**
     * Returns a non-null boolean value
     *
     * @param value
     * @return non-null boolean value
     */
    public static boolean getNullSafe(boolean value) {
        return ((Boolean)value == null) ? Boolean.FALSE : value;
    }

    /**
     * Returns a non-null long value
     *
     * @param value
     * @return non-null long value
     */
    public static long getNullSafe(long value) {
        return ((Long)value == null) ? 0L : value;
    }

    /**
     * Returns a non-null int value
     *
     * @param value
     * @return non-null int value
     */
    public static int getNullSafe(int value) {
        return ((Integer)value == null) ? 0 : value;
    }

    /**
     * Returns a non-null Date
     *
     * @param value
     * @return non-null Date
     */
    public static Date getNullSafe(Date value) {
        return (value == null) ? SQLDateUtil.getTodaysDate() : value;
    }

}
