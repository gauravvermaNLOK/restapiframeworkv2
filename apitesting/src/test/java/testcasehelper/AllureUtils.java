package testcasehelper;

import org.testng.Assert;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utils.FileUtils;

public class AllureUtils {

	@Attachment(value = "Request URL", type = "text/plain")
	private static String addRequestUrl(String strUrl) {
		return strUrl;
	}

	@Attachment(value = "Request Body", type = "application/json")
	private static String attachRequestBody(String strRequestBody) {
		return strRequestBody;
	}

	@Attachment(value = "Request Json schema", type = "application/json")
	private static String attachRequestJsonSchema(String strJsonSchema) {
		return strJsonSchema;
	}

	public static void attachRequestDetails(String strJsonSchema, String strRequestBody, String strURL) {
		addRequestUrl(strURL);
		attachRequestBody(strRequestBody);
		attachRequestJsonSchema(strJsonSchema);
	}

	@Attachment(value = "Response code", type = "text/plain")
	private static String addResponseCode(int intResponseCode) {
		return Integer.toString(intResponseCode);
	}

	@Attachment(value = "Response Status Line", type = "text/plain")
	private static String addResponseStatusLine(String strResponseStatusLine) {
		return strResponseStatusLine;
	}

	@Attachment(value = "Response body", type = "application/json")
	private static String attachResponseBody(String strResponseBody) {
		return strResponseBody;
	}

	public static void attchResponse(Response response, String strSchemaContent) {
		addResponseCode(response.getStatusCode());
		addResponseStatusLine(response.getStatusLine());
		attachResponseBody(response.getBody().asPrettyString());
		attachResponseSchema(strSchemaContent);
	}

	@Attachment(value = "Response Body Schema", type = "application/json")
	private static String attachResponseSchema(String strSchemaContent) {
	    System.out.println("Schema Content: " + strSchemaContent); // Debugging
	    return strSchemaContent;
	}

	@Step("Verify that actual value {actual} equals expected value '{expected}'")
	public static void assertValues(String actual, String expected) {
		Assert.assertEquals(actual, expected);
	}
	
	@Step("Verify that the execution result.")
	public static void logResult(String actual, String expected, String stepName, String attachment, String type)
	{
		if (stepName!= null && attachment != null && type!=null)
			Allure.addAttachment(stepName, type, attachment);
		if (stepName!= null && attachment!= null && type == null)
			Allure.attachment(stepName, attachment);
	
			if (actual!= null && expected!=null)
				try {
					Assert.assertEquals(actual, expected);
				} catch (Exception e) {
					//Assert.fail(String.format("Actual: {0} | Expected: {1}", actual, expected), e);
				}
		
			
	}
	
	public static void attachRequestDetails() {
		
	}

}
