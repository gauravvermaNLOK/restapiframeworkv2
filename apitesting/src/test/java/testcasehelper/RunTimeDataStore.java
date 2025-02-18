package testcasehelper;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dependencyinjector.DIModule;

/**
 * To store test data created at runtime.
 */
public class RunTimeDataStore {
	private static final Logger logger = LogManager.getLogger(DIModule.class);

	@Inject
	private HashMap<String, Object> runTimeData;

	/**
	 * Returns value of given key
	 * 
	 * @param strKey - String
	 * @return - Object
	 */
	public Object getValue(String strKey) {
		Object strValue = runTimeData.get(strKey);
		logger.info("Key is {} and value is {}", strKey, strValue);
		return strValue;
	}

	/**
	 * Stores run time data in key value form
	 * 
	 * @param strKey - String : key
	 * @param value  - String : value
	 */
	public void setValue(String strKey, Object value) {
		logger.info("Putting data in run time data store {{},{}}", strKey, value);
		runTimeData.put(strKey, value);
	}
}
