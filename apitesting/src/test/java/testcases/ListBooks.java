package testcases;

import java.util.HashMap;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.annotations.Test;

import dependencyinjector.BaseTestClass;
import testcasehelper.AllureUtils;
import testcasehelper.HTTPMethods.RESPONSE;

public class ListBooks extends BaseTestClass{
	
	@Test()
	public void getAllBooks(ITestContext testContext)
	{
		System.out.println("Suite Context : " + testContext.getSuite().getAttribute(keyStore.API_STATUS.toString()));
		String strAPIStatus = (String) testContext.getAttribute(keyStore.API_STATUS.toString());
		System.out.println("Test Context : " + strAPIStatus);

		
		ITestNGMethod method = Reporter.getCurrentTestResult().getMethod();
		String strUrl = getValues.getApiRequestUrl(method);
		
		HashMap<RESPONSE, Object> response = httpMethods.get(strUrl, null);
		AllureUtils.logResult(response.get(RESPONSE.RESPONSE_CODE).toString(), "200", "List books (GET)", strUrl, null);
	}
	
	@Test
	public void getFictionBooks()
	{
		ITestNGMethod method = Reporter.getCurrentTestResult().getMethod();
		String strUrl = getValues.getApiRequestUrl(method);
		HashMap<RESPONSE, Object> response = httpMethods.get(strUrl, null);
		AllureUtils.logResult(response.get(RESPONSE.RESPONSE_CODE).toString(), "200", "List fiction books books (GET)", strUrl, null);
	}
	
	@Test
	public void getNonFictionBooks()
	{
		ITestNGMethod method = Reporter.getCurrentTestResult().getMethod();
		String strUrl = getValues.getApiRequestUrl(method);
		HashMap<RESPONSE, Object> response = httpMethods.get(strUrl, null);
		AllureUtils.logResult(response.get(RESPONSE.RESPONSE_CODE).toString(), "200", "List fiction books books (GET)", strUrl, null);
	}

}
