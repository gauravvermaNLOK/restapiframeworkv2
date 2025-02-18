package testcasehelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.module.jsv.JsonSchemaValidator;
import utils.FileUtils;

/**
 * Utility to validate given json against a given schema
 */
public class SchemaValidator {
	private static final Logger logger = LogManager.getLogger(SchemaValidator.class);

	/**
	 * 
	 * @param jsonString - String : JsonString to validate
	 * @param jsonSchemaPath - String : Path of file have required schema
	 * @return
	 */
	public boolean jsonHasCorrectSchema(String jsonString, String jsonSchemaPath) {
		logger.info("Json String to validate is {}", jsonString);
		logger.info("Schema file path is {}", jsonSchemaPath);
		boolean isMatched = JsonSchemaValidator.matchesJsonSchema(FileUtils.getFileContenet(jsonSchemaPath))
				.matches(jsonString);
		logger.info("Does Json string have correct schema (true/false)? {}", isMatched);
		return isMatched;
	}
	
	
	

}
