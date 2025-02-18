package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtils {

	private static final Logger logger = LogManager.getLogger(FileUtils.class);

	/**
	 * Returns the content of file at given path
	 * 
	 * @param strFilePath - String : Full qualified path of text file
	 * @return String : Content of file
	 */
	public static String getFileContenet(String strFilePath) {
		logger.info("Reading content of file at {}", strFilePath);
		Path fileName = Path.of(strFilePath);
		String strFileContent = null;
		try {
			strFileContent = Files.readString(fileName);
		} catch (IOException e) {
			logger.error("Exception occurred while reading content of file at placed at {0}", strFilePath);
			logger.error("Exception message is {}", e.getMessage());
			e.printStackTrace();
		}
		logger.info("Content of file is {}", strFileContent);
		return strFileContent;

	}

	/**
	 * Deletes file at given path
	 * @param strFilePath - String : Full qualified path of file to be deleted
	 */
	public static void deleteFile(String strFilePath) {
		Path fileName = Path.of(strFilePath);
		try {
			Files.deleteIfExists(fileName);
			logger.info("File at {} deleted successfully", strFilePath);
		} catch (IOException e) {
			logger.error("Exception occurred while deleting file at placed at {}", strFilePath);
			logger.error("Exception message is {}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Deletes given folder, all sub folders and files
	 * @param folderPath - String : Full qualified path of Folder to be deleted
	 */
	public static void deleteFolder(String folderPath) {
		Path path = Paths.get(folderPath);

		if (Files.exists(path)) {
			// Java 8 and later approach (handles non-empty directories):
			try (Stream<Path> walk = Files.walk(path)) {
				walk.sorted(Comparator.reverseOrder()) // Important: Reverse order for deleting files/subfolders first
						.forEach(FileUtils::deleteFile);
			} catch (IOException e) {
				logger.error("Exception message is {}", e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.info("Folder does not exist: " + folderPath);
		}
	}

	/**
	 * Deletes file at given path
	 * @param path - Path : Full qualified path of file to be deleted
	 * Use Path.of(strFilePath) to get Path
	 */
	private static void deleteFile(Path path) {
		try {
			Files.deleteIfExists(path);
			logger.info("File at {} deleted successfully", path);
		} catch (IOException e) {
			logger.error("Exception occurred while deleting file at placed at {}", path.toString());
			logger.error("Exception message is {}", e.getMessage());
		}
	}

}
