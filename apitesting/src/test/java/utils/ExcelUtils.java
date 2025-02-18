package utils;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * This utility is useful to interact with excel file
 */
public class ExcelUtils {

	public Workbook workbook = null;
	public Sheet sheet = null;
	public int intRowCountInSheet = 0;
	public int intHeaderColumnCountInSheet = 0; // Header is first row
	private static final Logger logger = LogManager.getLogger(ExcelUtils.class);

	/**
	 * Initialize the object of ExcelUtil class
	 * 
	 * @param filePath  - String : full qualified path of Excel file
	 * @param sheetName - String : Name of tab (in excel file) to read.
	 */
	public ExcelUtils(String filePath, String sheetName) {
		logger.info("Path of Excel file is {0} and sheet name to read is {1}", filePath, sheetName);
		setWorkbook(filePath);
		sheet = workbook.getSheet(sheetName);
		setRowCountInSheet();
		setHeaderColumnCountInSheet();

	}

	/**
	 * Initialize the object of ExcelUtil class
	 * 
	 * @param filePath  - String : full qualified path of Excel file
	 * @param sheetName - String : Index of tab (in excel file) to read. Index is
	 *                  zero based
	 */
	public ExcelUtils(String filePath, int sheetIndex) {
		setWorkbook(filePath);
		try {
			sheet = workbook.getSheetAt(sheetIndex);
		} catch (IllegalArgumentException e) {
			logger.error("Exception while initializing sheet at index {0} of excel workbook placed at {1}", sheetIndex,
					filePath);
			logger.error("Exception is {0}", e.getMessage());
			e.printStackTrace();
		}
		setHeaderColumnCountInSheet();
	}

	/**
	 * Initializes Workbook object of given excel file
	 * 
	 * @param strWorkbookPath - full qualified path of Excel file
	 */
	private void setWorkbook(String strWorkbookPath) {
		try {
			File excelFile = new File(strWorkbookPath);
			workbook = WorkbookFactory.create(excelFile);
			logger.info("Created object of excel workbook placed at {0}", strWorkbookPath);
		} catch (EncryptedDocumentException e) {
			logger.error("Exception while creating workbook object of excel placed at {0}", strWorkbookPath);
			logger.error("Exception is {0}", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Exception while creating workbook object of excel placed at {0}", strWorkbookPath);
			logger.error("Exception is {0}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Set intRowCountInSheet variable to number of rows in given excel sheet.
	 */
	private void setRowCountInSheet() {
		int intRowCount = sheet.getPhysicalNumberOfRows();
		logger.info("Nouber of rows in sheet are {0}", intRowCount);
		intRowCountInSheet = intRowCount;
	}

	/**
	 * Set intColumnCountInSheet variable to number of rows in given excel sheet.
	 */
	private void setHeaderColumnCountInSheet() {
		intHeaderColumnCountInSheet = getLastCoulmnIndexOfGivenRow(0) + 1;
		logger.info("Header column count is {0}", intHeaderColumnCountInSheet);
	}

	/**
	 * Gives the last column index of given row
	 * 
	 * @param intRowIndex - Index of row (zero based index)
	 * @return - int : Last column index (zero based index)
	 */
	public int getLastCoulmnIndexOfGivenRow(int intRowIndex) {
		int intLastColumnIndex = sheet.getRow(intRowIndex).getLastCellNum() + 1;
		logger.info("Last column index of row at index {0} is {1}", intRowIndex, intLastColumnIndex);
		return intLastColumnIndex;
	}

	/**
	 * Returns the data of given cell
	 * 
	 * @param intRowIndex    - Row number (zero based index)
	 * @param intColumnIndex - Column Number (zero based index.
	 * @return - String : Text in given cell
	 */
	public String getCellData(int intRowIndex, int intColumnIndex) {
		String strCellValue = null;
		Cell cell = sheet.getRow(intRowIndex).getCell(intColumnIndex);
		logger.info("Created cell object of row index {0} and column index {1}. All indexes are zero based.",
				intRowIndex, intColumnIndex);
		if (cell != null)
			strCellValue = getCellValueAsString(cell);
		return strCellValue;
	}

	/**
	 * Gives value of given cell in excel
	 * 
	 * @param cell - {@code Cell}
	 * @return - String : Value of Cell
	 */
	public String getCellValueAsString(Cell cell) {
		String strValue = "";
		CellType cellType = cell.getCellType();
		logger.info("Type of cell is {0}", cellType);
		switch (cellType) {
		case STRING:
			strValue = cell.getStringCellValue();
			logger.info("Value of cell is {0}", strValue);
			return strValue;
		case NUMERIC:
			strValue = String.valueOf(cell.getNumericCellValue());
			logger.info("Value of cell is {0}", strValue);
			return strValue;
		case BOOLEAN:
			strValue = String.valueOf(cell.getBooleanCellValue());
			logger.info("Value of cell is {0}", strValue);
			return strValue;
		case FORMULA:
			strValue = cell.getCellFormula();
			logger.info("Value of cell is {0}", strValue);
			return strValue;
		default:
			logger.info("Value of cell is {0}", strValue);
			return "";
		}
	}

}
