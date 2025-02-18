package testcasehelper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import utils.ExcelUtils;

public class DataProvider {
	private static final Logger logger = LogManager.getLogger(DataProvider.class);
	/**
	 * DataProvider method to read data from Excel
	 * 
	 * @param method : Method - Test Method instance
	 * @return : Iterator<Object[]> - iterator to iterate test data
	 * @throws IOException
	 */
	@org.testng.annotations.DataProvider(name = "excelDataProvider")
	public Iterator<Object[]> provideExcelData(Method method) throws IOException {
		// testdatafilepath is parameter in pom.xml
		String strExcelPath = System.getProperty("testdatafilepath");
		logger.info("Test data file path is {}", strExcelPath);
		List<Map<String, String>> testData = readExcelData(strExcelPath, method.getName());
		
		// Convert List<Map<String, String>> to Iterator<Object[]>
		return testData.stream().map(data -> new Object[] { data }).iterator();
	}

	/**
	 * Read the test data from given excel file
	 * 
	 * @param strExcelPath : String - Full qualified path of excel file containing
	 *                     test data
	 * @param strSheetName : String - Name of sheet in excel file
	 * @return List<Map<String, String>> - Create a Map for every row of excel where
	 *         column name represents key and corresponding cell's data represents
	 *         value. After reading data of all rows, it adds those in list and
	 *         returns the list
	 */
	private List<Map<String, String>> readExcelData(String strExcelPath, String strSheetName) {
		List<Map<String, String>> data = new ArrayList<>();
		ExcelUtils excelUtility = new ExcelUtils(strExcelPath, strSheetName);
		// Get the header row (column names)
		Row headerRow = excelUtility.sheet.getRow(0);
		logger.info("Created object of header row {}", headerRow);
		List<String> columnNames = new ArrayList<>();
		logger.info("Created empty list to store header columns.");
		for (Cell cell : headerRow) {
			columnNames.add(excelUtility.getCellValueAsString(cell));
		}
		logger.info("List of columns is {}", columnNames);
		// Iterate through rows (skip the header row)
		for (int i = 1; i <= excelUtility.sheet.getLastRowNum(); i++) {
			Row row = excelUtility.sheet.getRow(i);
			logger.info("Reading data of row at index {}", i);
			Map<String, String> rowData = new HashMap<>();
			// Iterate through columns
			for (int j = 0; j < columnNames.size(); j++) {
				Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String cellValue = excelUtility.getCellValueAsString(cell);
				rowData.put(columnNames.get(j), getRandomValue(cellValue));
			}
			logger.info("Data of row at index {} is {}", i, rowData);
			data.add(rowData);
		}
		logger.info("Test data is {}", data);
		return data;
	}
	
	/**
	 * Generates random text based on Given Cell value
	 * @param cellValue - String
	 * RANDOM_TEXT - Generates Random word of 10 chars containing lower/upper case alphabets and/or numbers
	 * RANDOM_NUMBER - Generates 10 digit number,
	 * RANDOM_EMAIL - Random email in format <RANDOM_TEXT>@email.com
	 * Or Cell value as is.
	 * @return - String : Random value if passed argument is either RANDOM_TEXT or RANDOM_NUMBER or RANDOM_EMAIL
	 * else returns Cell value as is.
	 */
	private String getRandomValue(String cellValue) {
		switch (cellValue) {
		case "RANDOM_TEXT":
			return generateRandomText(10);
		case "RANDOM_NUMBER":
			return generateRandomNumberOf10Digits();
		case "RANDOM_EMAIL":
			return generateRandomEmail();
		default:
			return cellValue;
		}
	}

	/**
	 * Generates Random word of 10 chars containing lower/upper case alphabets and/or numbers of given length
	 * @param length - Integer : length of word
	 * @return - String : Random text of given length
	 */
	private String generateRandomText(int length) {
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			sb.append(randomChar);
		}
		String strRandomText =  sb.toString();
		logger.info("Random text is {}", strRandomText );
		return strRandomText;
	}

	/**
	 * Random number of 10 digits
	 * @return - String : Random number of 10 digits in form of String.
	 */
	private String generateRandomNumberOf10Digits() {
		long min = 1000000000L;
		long max = 9999999999L;
		long num = min + (long) (Math.random() * (max - min + 1));
		String strRandomNumber =  String.valueOf(num);
		logger.info("Random number is {}", strRandomNumber );
		return strRandomNumber;
	}

	/**
	 * Generates Random email in form of <RANDOm_TEXT>@email.com
	 * @return - String Random email
	 */
	private String generateRandomEmail() {
		String strRandomEmail = generateRandomText(10) + "@email.com";
		logger.info("Random email is {}", strRandomEmail );
		return strRandomEmail;
	}

}
