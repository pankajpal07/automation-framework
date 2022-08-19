package com.thepankajpal.selenium.platform.utility;

import static java.util.Objects.nonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SINGLETON : This class is responsible for loading configuration from
 * properties files.
 * 
 * @author Jaikant
 *
 */
public class Config {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

	private static String CONFIG_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "config";

	// to make this singleton
	private Config() {

	}

	private static final Properties PROPERTIES = new Properties();

	static {
		loadPropertyFiles(CONFIG_FOLDER_PATH);
	}

	/**
	 * It will load all the properties files present in config folder.
	 * 
	 * @param configFolderPath
	 */
	private static void loadPropertyFiles(String configFolderPath) {
		LOGGER.info(CONFIG_FOLDER_PATH);
		File folder = new File(configFolderPath);
		File[] files = folder.listFiles();
		for (File file : files) {
			String filePath = file.getAbsolutePath();
			if (filePath.endsWith(".properties")) {
				loadPropertiesFile(file.getAbsolutePath());
			}
		}
		LOGGER.info(" All property configuration files loaded.");
	}

	/**
	 * It will load the given properties file
	 * 
	 * @param filePath
	 */
	private static void loadPropertiesFile(String filePath) {

		LOGGER.info(" Loading properties file : " + filePath);
		try {
			InputStream input = new FileInputStream(filePath);
			PROPERTIES.load(input);
			if (nonNull(input)) {
				input.close();
			}
		} catch (IOException e) {
			LOGGER.info("!!!! Exception while loading properties file : ", e.getMessage());
		}
	}

	/**
	 * It will give the property value present in properties file.
	 * 
	 * @param propertyName
	 * @return property value
	 */
	public static String getProperty(String propertyName) {

		return PROPERTIES.getProperty(propertyName);
	}

}