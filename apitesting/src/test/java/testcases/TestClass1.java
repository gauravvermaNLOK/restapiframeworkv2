package testcases;


import java.lang.reflect.Method;
import java.util.HashMap;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import dependencyinjector.BaseTestClass;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import testcasehelper.AllureUtils;

import testcasehelper.HTTPMethods.RESPONSE;

import utils.FileUtils;

public class TestClass1 extends BaseTestClass {

	private static final Logger logger =  LogManager.getLogger(TestClass1.class);
	

	@BeforeSuite
	public void beforeSuite() {
		FileUtils.deleteFolder(System.getProperty("user.dir") + "\\allure-results");
	}

	@BeforeMethod()
	@Step("This step validates schema of request body")
	public void beforeMethod(Method method) {
		String strMethodName = method.getName();
		logger.info("About to execute test method: " + strMethodName);

	}
	
	
	
	@Test(priority = 1)
	@Description("This test case is to check API Status")
	public void getAPIStatus( ITestContext context) {
		ITestNGMethod method = Reporter.getCurrentTestResult().getMethod();
		logger.info("Started test {}", method.getMethodName());
		//Attaching request details in Report
		String strAPIRequestURL = getValues.getApiRequestUrl(method);
		AllureUtils.logResult(null, null, "Request details", strAPIRequestURL, "");
		AllureUtils.attachRequestDetails(null, null, strAPIRequestURL);
		//Hitting request and validating response 
		HashMap<RESPONSE, Object> mapResponse = httpMethods.get(strAPIRequestURL, null);
		int intActualResponseCode = (int)mapResponse.get(RESPONSE.RESPONSE_CODE);
		AllureUtils.assertValues(Integer.toString(intActualResponseCode) , "200");
		//validate Response Schema
		String strResponseJsonSchemaFilePath =  getValues.getFullQualifiedPathOfSchema(method, "Response");
		boolean isResponseSchemaValid = schemaValidator.jsonHasCorrectSchema(mapResponse.get(RESPONSE.RESPONSE_BODY).toString(), strResponseJsonSchemaFilePath);
		Allure.attachment("Schema", FileUtils.getFileContenet(strResponseJsonSchemaFilePath));
		AllureUtils.attchResponse((Response)mapResponse.get(RESPONSE.RESPONSE_OBJECT), FileUtils.getFileContenet(strResponseJsonSchemaFilePath));
		AllureUtils.assertValues(Boolean.toString(isResponseSchemaValid) , "true");
		//Assert.assertEquals(IsResponseSchemaValid, true, "Please refer Json schema and Response body for more details");
	}
	
	

//	@Test(dataProvider = "excelDataProvider", dataProviderClass = testcasehelper.DataProvider.class, priority = 2)
//	@Description("This test case cancels the booking. Either you can pass the booking id this test "
//			+ "or you can call Create booking as a pre requisite.")
//	public void CancelBooking(Map<String, String> data, ITestContext context) {
//		String strMethodName = Reporter.getCurrentTestResult().getMethod().getMethodName();
//
//		String strBaseURL = System.getProperty("apiUrl");
//		String strAPIRequestURL = getValues.getApiRequestUrl(Reporter.getCurrentTestResult().getMethod()) ; //strBaseURL.concat(propertyReader.getPropertyValue(strMethodName));
//		String strRequestBody = requestBodyGenerator.getRequestBody(strMethodName, data);
//		Allure.addAttachment("API Response", "text/plain", strRequestBody);
//		System.out.println(strMethodName + "-Request body is " + strRequestBody);
//		System.out.println(strRequestBody);
//		Assert.assertEquals(true, true);
//
//	}
//
//	private String generateRandomNumberOf10Digits() {
//		long min = 1000000000L;
//		long max = 9999999999L;
//		long num = min + (long) (Math.random() * (max - min + 1));
//		return String.valueOf(num);
//	}

}
