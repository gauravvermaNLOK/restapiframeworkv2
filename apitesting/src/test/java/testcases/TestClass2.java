package testcases;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import dependencyinjector.BaseTestClass;

public class TestClass2 extends BaseTestClass{

	
	
	@Test
	public void GetValueFromTestClass1(ITestContext context )
	{
		String strValueFromTestClass1 = (String) context.getAttribute("Value1 for TestClass2");
		System.out.println("Value passed from TestClass1 to TestClass2 at runtime is->" + strValueFromTestClass1);
		
	}
}
