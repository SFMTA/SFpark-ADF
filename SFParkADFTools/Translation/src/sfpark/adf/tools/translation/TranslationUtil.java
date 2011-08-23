package sfpark.adf.tools.translation;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.UUID;

public final class TranslationUtil {
    private static final String QUOTE_REPLACEMENT =
        UUID.randomUUID().toString();

    /**
     * To avoid instantiation
     */
    private TranslationUtil() {
        super();
    }

    public static String getAssetManagerBundleString(AssetManagerBundleKey Key,
                                                     Object... args) {
        return getTranslatedString(TranslationBundle.AssetManagerBundle.getBaseName(),
                                   Key.name(), args);
    }

    public static String getCalendarManagerBundleString(CalendarManagerBundleKey Key,
                                                        Object... args) {
        return getTranslatedString(TranslationBundle.CalendarManagerBundle.getBaseName(),
                                   Key.name(), args);
    }

    public static String getRateChangeManagerBundleString(RateChangeManagerBundleKey Key,
                                                          Object... args) {
        return getTranslatedString(TranslationBundle.RateChangeManagerBundle.getBaseName(),
                                   Key.name(), args);
    }

    public static String getCommonBundleString(CommonBundleKey Key,
                                               Object... args) {
        return getTranslatedString(TranslationBundle.CommonBundle.getBaseName(),
                                   Key.name(), args);
    }

    public static String getErrorBundleString(ErrorBundleKey Key,
                                              Object... args) {
        return getTranslatedString(TranslationBundle.ErrorBundle.getBaseName(),
                                   Key.name(), args);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private static String getTranslatedString(String baseName, String Key,
                                              Object... args) {

        ResourceBundle rb = ResourceBundle.getBundle(baseName, Locale.US);

        String pattern = null;

        try {
            pattern = rb.getString(Key);
        } catch (MissingResourceException e) {
            return "NOT_FOUND_FOR_" + Key;
        }

        String result =
            MessageFormat.format(pattern.replaceAll("'", QUOTE_REPLACEMENT),
                                 args);

        return result.replaceAll(QUOTE_REPLACEMENT, "'");
    }
}
