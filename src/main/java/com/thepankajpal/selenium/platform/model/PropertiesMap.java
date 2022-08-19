package com.thepankajpal.selenium.platform.model;

import java.io.File;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains functions to load properties files and get property values.
 */
public class PropertiesMap {

	private static CompositeConfiguration compositeConfiguration;
	private static Configurations configurations;
	private static Logger LOGGER = LoggerFactory.getLogger(PropertiesMap.class);

	/**
	 * Loads properties from the provided file.
	 * 
	 * @param file
	 */
	public static void loadProperties(File file) {
		LOGGER.info("Reading configuration file: {}", file.getName());
		configurations = new Configurations();
		compositeConfiguration = new CompositeConfiguration();
		try {
			compositeConfiguration.addConfiguration(configurations.properties(file));
		} catch (ConfigurationException e) {
			LOGGER.error("Exception while loading the properties file and exception text is :{}", e.getMessage());
		}
	}

	/**
	 * Method to get property value.
	 * 
	 * @param propertyName Property name.
	 * @return Property value.
	 */
	public static String getProperty(String propertyName) {
		return compositeConfiguration.getString(propertyName);
	}

	/**
	 * Retrieves property value or default value if not set
	 * 
	 * @param propertyName property name
	 * @param defaultValue default value
	 * @return property value
	 */
	public static String getProperty(String propertyName, String defaultValue) {
		return compositeConfiguration.getString(propertyName, defaultValue);
	}

	public static void setProperty(String key, String value) {
		compositeConfiguration.setProperty(key, value);
	}
    
}
