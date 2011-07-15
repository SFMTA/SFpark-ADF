package sfpark.event.manager.i18n;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.UUID;

public final class ResourceBundleUtil {

    private static final String QUOTE_REPLACEMENT =
        UUID.randomUUID().toString();

    private static final String ApplicationBundle =
        "sfpark.event.manager.i18n.ApplicationBundle";

    /**
     * To avoid Instantiation
     */
    private ResourceBundleUtil() {
        super();
    }

    public static String getApplicationBundleString(String Key,
                                                    Object... args) {
        return getTranslatedString(ApplicationBundle, Key, args);
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
