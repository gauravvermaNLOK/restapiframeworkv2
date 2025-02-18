package testcasehelper;

import java.util.Map;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestNGMethod;
import dependencyinjector.DIModule.OBJECT_NAME;
import utils.PropertyReader;

/**
 * This class is provide values from property files
 */
public class GetValues {

	private static final Logger logger = LogManager.getLogger(GetValues.class);

	/**
	 * This map holds object of PropertyReader class
	 */
	private final Map<OBJECT_NAME, PropertyReader> propertyReaders;

	/**
	 * Inject annotation says to check in DIMOdule if Parameter is initialized. And
	 * inject that initialized object.
	 * 
	 * @param propertyReaders - Map<OBJECT_NAME, PropertyReader> : Automatically
	 *                        injected by Guice
	 */
	@Inject
	public GetValues(Map<OBJECT_NAME, PropertyReader> propertyReaders) {
		logger.info("Assigning {} created at run time", propertyReaders);
		this.propertyReaders = propertyReaders;
		logger.info("Assignment of {} is successful", propertyReaders);
	}

	/**
	 * This method returns the schema of request body of given method
	 * 
	 * @param method - ITestNGMethod : TestNG method object.
	 * @param strRequestOrResponse - String : possible values are Request, Response
	 * @return Path of text file containing schema of request body of given method
	 *         in form of String
	 */
	public String getFullQualifiedPathOfSchema(ITestNGMethod method, String strRequestOrResponse) {
		String strMethodName = method.getMethodName();
		logger.info("Method name is {}", strMethodName);
		String strSchemaPath =  System.getProperty("user.dir") + "\\src\\test\\resources\\";
		if(strRequestOrResponse!= null && strRequestOrResponse.equalsIgnoreCase("REQUEST"))
		 strSchemaPath = strSchemaPath.concat("requestjsonschema\\").concat(strMethodName).concat(".txt");		
		else
			 strSchemaPath = strSchemaPath.concat("responsejsonschema\\").concat(strMethodName).concat(".txt");	
		logger.info("Schema path of {} body is {}", strRequestOrResponse, strSchemaPath);
		return strSchemaPath;
	}

	/**
	 * This method returns the API request URL
	 * 
	 * @param method - ITestNGMethod : TestNG method object.
	 * @return URL of given request method in form of String
	 */
	public String getApiRequestUrl(ITestNGMethod method) {
		String strBaseURL = System.getProperty("apiUrl");
		logger.info("Value of Base URL is {}", strBaseURL);
		String strMethodName = method.getMethodName();
		logger.info("Method name is {}", strMethodName);
		String strAPIRequestURL = strBaseURL
				.concat(propertyReaders.get(OBJECT_NAME.pURI_READER).getPropertyValue(strMethodName));
		logger.info("API request URL is {}", strAPIRequestURL);
		return strAPIRequestURL;
	}

	/**
	 * This method returns the auth token
	 * 
	 * @return String: Auth token
	 */
	public String getAuthToken() {
		return propertyReaders.get(OBJECT_NAME.pURI_READER).getPropertyValue("authtoken");
	}
}
