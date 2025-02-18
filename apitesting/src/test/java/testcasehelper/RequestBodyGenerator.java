package testcasehelper;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.inject.Inject;
import utils.FileUtils;

/**
 * This class is to convert RequestBody template to actual request by replacing
 * place holder with actual values.
 */
public class RequestBodyGenerator {
	private static final Logger logger = LogManager.getLogger(RequestBodyGenerator.class);

	/**
	 * Initialize object of {@code}RunTimeDataStore
	 */
	@Inject
	public RunTimeDataStore runTimeDataStore;

	/**
	 * Converts Request body template to actual request body
	 * 
	 * @param strMethodName - String : Name of test method
	 * @param data          - Map<String, String> - Key is column name, Value is
	 *                      value of the column in excel
	 * @return - String : Actual request body
	 */
	public String getRequestBody(String strMethodName, Map<String, String> data) {
		logger.info("Reading request body template content of test method {}", strMethodName);
		String strTemplatePath = System.getProperty("user.dir") + "\\src\\test\\resources\\requestbodytemplate\\"
				+ strMethodName + ".txt";
		logger.info("Request body template is placed at {}", strTemplatePath);
		String strRequestTemplateBody = FileUtils.getFileContenet(strTemplatePath);
		logger.info("Content of template is {}", strRequestTemplateBody);
		for (String strColumnName : data.keySet()) {
			String strPlaceHolder = "{" + strColumnName + "}";
			String strValueToReplace = getValueToReplace(data.get(strColumnName));
			logger.info("Replacing {} with {}", strPlaceHolder, strValueToReplace);
			strRequestTemplateBody = strRequestTemplateBody.replace(strPlaceHolder, strValueToReplace);
			logger.info("template body after replacing {} with {} is {}", strPlaceHolder, strValueToReplace,
					strRequestTemplateBody);
		}
		return strRequestTemplateBody;
	}

	/**
	 * It checks whether value in Excel Test data file is just a placeholder to
	 * store runtime data Or it is actual value. It it is placeholder to store
	 * runtime data then fetch the value from {@link RunTimeDataStore} else returns
	 * the as is value from excel.
	 * 
	 * @param strValueInExcel - String : value in Excel
	 * @return String
	 */
	private String getValueToReplace(String strValueInExcel) {
		String strRunTimeValue = (String) runTimeDataStore.getValue(strValueInExcel);
		String strValueToReturn = null;
		logger.info("Value of {} in RunTimeDataStore is {}", strValueInExcel, strRunTimeValue);
		if (strRunTimeValue == null)
			strValueToReturn = strValueInExcel;
		else
			strValueToReturn = strRunTimeValue;
		logger.info("Retuning value {}", strValueToReturn);
		return strValueToReturn;

	}

}
