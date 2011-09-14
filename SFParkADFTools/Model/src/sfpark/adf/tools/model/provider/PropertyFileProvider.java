package sfpark.adf.tools.model.provider;

import org.apache.commons.configuration.ConfigurationException;

import sfpark.adf.tools.helper.Logger;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import sfpark.adf.tools.utilities.generic.StringUtil;

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
        LOGGER.entering(CLASSNAME, "getPropertyValue");

        if (StringUtil.isBlank(key)) {
            throw new IllegalArgumentException("Key should not be NULL or empty. ");
        }

        String propertyValue = null;

        try {
            propertyValue = getPropertiesConfiguration().getString(key);

            if (StringUtil.isBlank(propertyValue)) {
                LOGGER.info(PROPERTY_FILE + " file does not contain the " +
                            key + " key. ");
            }

        } catch (ConfigurationException e) {
            LOGGER.info(PROPERTY_FILE +
                        " file was not found in the weblogic domain directory. ",
                        e);
        }

        LOGGER.exiting(CLASSNAME, "getPropertyValue");

        return propertyValue;
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS

    private static PropertiesConfiguration propertiesConfiguration = null;

    private static PropertiesConfiguration getPropertiesConfiguration() throws ConfigurationException {

        if (propertiesConfiguration == null) {
            propertiesConfiguration =
                    new PropertiesConfiguration(PROPERTY_FILE);
            propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        }

        return propertiesConfiguration;
    }
}
