package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyReader {

	Properties properties = null;
	private static final Logger logger = LogManager.getLogger(PropertyReader.class);
	private String strPropertyFilePath = null;

	public PropertyReader(String strPropertyFilePath) {
		this.strPropertyFilePath = strPropertyFilePath;
		loadProperties();
	}

	/** 
	 * Loads property file at run time
	 */
	private void loadProperties() {
		logger.info("Started with parameter {}", strPropertyFilePath);
		properties = new Properties();
		try (FileInputStream input = new FileInputStream(strPropertyFilePath)) { // Use try-with-resources to ensure the
																					// // stream is closed
			properties.load(input);
		} catch (IOException io) {
			logger.error("Error loading property file " + strPropertyFilePath + ": " + io.getMessage());
		}
	}

	/**
	 * Gives value of given property from property file
	 * @param strPropertyName - String : Name of property.
	 * @return - String : Value of property.
	 */
	public String getPropertyValue(String strPropertyName) {
		logger.info("Reading value of property {}.", strPropertyName);
		String strPropertyValue = properties.getProperty(strPropertyName);
		logger.info("Property value is {}.", strPropertyValue);
		return strPropertyValue;
	}

}
