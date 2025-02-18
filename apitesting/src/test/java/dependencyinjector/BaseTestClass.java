package dependencyinjector;

import javax.inject.Inject;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import testcasehelper.GetValues;
import testcasehelper.HTTPMethods;
import testcasehelper.SchemaValidator;
import testcasehelper.RequestBodyGenerator;
import testcasehelper.RunTimeDataStore;

/**
 * This class is to provide necessary instances of Helper classes required to
 * run Test Cases. Instance creation is happening using Guice. This class should
 * be extended by each Test Class.
 */
@Guice(modules = DIModule.class)
public class BaseTestClass {
	
	public enum keyStore{
		API_STATUS
	}

	@BeforeSuite
	public void setup() {
		RestAssured.filters(new AllureRestAssured());
	}

	// This will create object of GetValues class automatically using Guice
	@Inject
	protected GetValues getValues;

	// This will create object of HTTPMethods class automatically using Guice
	@Inject
	protected HTTPMethods httpMethods;

	// This will create object of RequestBodyGenerator class automatically using
	// Guice
	@Inject
	protected RequestBodyGenerator requestBodyGenerator;

	// This will create object of RequestBodyGenerator class automatically using
	// Guice. This class will store runtime data
	@Inject
	protected RunTimeDataStore runTimeDataStore;

	// This will create object of SchemaValidator class automatically using Guice
	@Inject
	protected SchemaValidator schemaValidator;
}
