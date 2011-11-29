package sfpark.adf.tools.model.provider;

import org.apache.commons.configuration.ConfigurationException;

import sfpark.adf.tools.helper.Logger;

import org.apache.commons.configuration.PropertiesConfiguration;

import sfpark.adf.tools.utilities.generic.StringUtil;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20111121-01 Mark Piller - Oracle Consulting  added logging debug statements
 */
public final class PropertyFileProvider {

    private static final String CLASSNAME =
        PropertyFileProvider.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String PROPERTY_FILE = "sfparkADFTools.properties";

    /**
     * To avoid instantiation
     */
    private PropertyFileProvider() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS

    /**
     * Returns the Property Value from the PROPERTY_FILE
     *
     * @param key
     * @return
     * @throws IllegalArgumentException
     */
    public static String getPropertyValue(String key) {
        // LOGGER.entering(CLASSNAME, "getPropertyValue");

        if (StringUtil.isBlank(key)) {
            throw new IllegalArgumentException("Key should not be NULL or empty. ");
        }

        String propertyValue = null;

        try {
            propertyValue =
                    (new PropertiesConfiguration(PROPERTY_FILE)).getString(key);
            // 20111121-01 added debug
            // LOGGER.debug("PropertyFileProvider > key: " + key.toString());
            // LOGGER.debug("PropertyFileProvider > PROPERTY_FILE: " + PROPERTY_FILE);

            if (StringUtil.isBlank(propertyValue)) {
                LOGGER.info(PROPERTY_FILE + " file does not contain the " +
                            key + " key. ");
            } else {
              // 20111121-01 added debug
              // LOGGER.debug("PropertyFileProvider > propertyValue: " + propertyValue);
            }

        } catch (ConfigurationException e) {
            LOGGER.info(PROPERTY_FILE +
                        " file was not found in the weblogic domain directory. ",
                        e);
        }

        // LOGGER.exiting(CLASSNAME, "getPropertyValue");

        return propertyValue;
    }
}
