package sfpark.adf.tools.utilities.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Locale;

public enum PatternString {

    DATE(((SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT,
                                                       Locale.US)).toPattern()),
    TIME(((SimpleDateFormat)DateFormat.getTimeInstance(DateFormat.SHORT,
                                                       Locale.US)).toPattern()),
    DATE_TIME(((SimpleDateFormat)DateFormat.getDateTimeInstance(DateFormat.SHORT,
                                                                DateFormat.SHORT,
                                                                Locale.US)).toPattern());

    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++

    private final String Pattern;

    private PatternString(String Pattern) {
        this.Pattern = Pattern;
    }

    public String getPattern() {
        return Pattern;
    }
}
