package testcases;

import java.util.HashMap;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.annotations.Test;

import dependencyinjector.BaseTestClass;
import io.restassured.response.Response;
import testcasehelper.AllureUtils;
import testcasehelper.HTTPMethods.RESPONSE;

public class APIStatus extends BaseTestClass {
	
	@Test
	public void getAPIStatus(ITestContext testContext)
	{
		ITestNGMethod method = Reporter.getCurrentTestResult().getMethod();
		String strAPIUrl = getValues.getApiRequestUrl(method);
		HashMap<RESPONSE, Object> mapResponse = httpMethods.get(strAPIUrl, null);
		Response response =(Response)mapResponse.get(RESPONSE.RESPONSE_OBJECT);
		String strStatus = response.jsonPath().getString("status");
		AllureUtils.logResult(strStatus, "OK", "Validate Status", null, null);
		testContext.setAttribute(keyStore.API_STATUS.toString(), "OK");//available in test tag
		testContext.getSuite().setAttribute(keyStore.API_STATUS.toString(), "OK");//available in suit tag
	}

}
