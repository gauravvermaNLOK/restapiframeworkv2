package dependencyinjector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

import testcasehelper.GetValues;
import testcasehelper.HTTPMethods;
import testcasehelper.RequestBodyGenerator;
import testcasehelper.RunTimeDataStore;
import utils.PropertyReader;

/***
 * This class for dependency injection using Google Guice
 * DIModule stands for Dependency Injection module
 * 
 */
public class DIModule extends AbstractModule {
	private static final Logger logger =  LogManager.getLogger(DIModule.class);
	/**
	 * This enum is to hold object of PropertyReader class
	 */
	public enum OBJECT_NAME{
		pURI_READER, //To read requesturi.properties file
		pAUTHTOKEN_READER //To read authtoken.properties file
	}
	
	@Override
	protected void configure() {
		
		// Bind your helper classes to instruct Guice to create Singleton object of these classes.
		bind(HTTPMethods.class).asEagerSingleton();
		bind(RunTimeDataStore.class).asEagerSingleton();
		bind(RequestBodyGenerator.class).asEagerSingleton();
		bind(GetValues.class).asEagerSingleton();
		logger.info("Instructed Guice to use Single pattern for classes.");

		String strBasePropertyFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\";
		logger.info("Base path of all property files is {}", strBasePropertyFilePath);
		String strUriPropertyFilePath = strBasePropertyFilePath.concat("requesturi.properties");
		logger.info("Path of requesturi property file is {}", strUriPropertyFilePath);
		String strAuthTokenPropertyFilePath = strBasePropertyFilePath.concat("authtoken.properties");
		logger.info("Path of authtoken property file is {}", strAuthTokenPropertyFilePath);

		MapBinder<OBJECT_NAME, PropertyReader> mapBinder = MapBinder.newMapBinder(binder(), OBJECT_NAME.class,
				PropertyReader.class);
		mapBinder.addBinding(OBJECT_NAME.pURI_READER).toInstance(new PropertyReader(strUriPropertyFilePath));
		logger.info("Added object of PropertyReader in map with key {} to read requesturi property file.", OBJECT_NAME.pURI_READER.toString());
		mapBinder.addBinding(OBJECT_NAME.pAUTHTOKEN_READER).toInstance(new PropertyReader(strAuthTokenPropertyFilePath));
		logger.info("Added object of PropertyReader in map with key {} to read authtoken property file.", OBJECT_NAME.pAUTHTOKEN_READER.toString());
	}

}