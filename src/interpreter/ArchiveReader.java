package interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

import java.util.zip.ZipFile;

/**
 * This class is used to read the content of a game archive.
 * 
 * @author regnaclockers
 */
public class ArchiveReader {
	private final static Logger LOGGER = Logger.getLogger(interpreter.ArchiveReader.class.getName());

	private ZipFile gameArchive;
	private ZipEntry gameInformation;
	private ZipEntry monster;
	private ZipEntry item;
	private ZipEntry mapFolder;
	private ZipEntry tilesetFolder;
	private ZipEntry charsetFolder;

	/**
	 * reads and loads a zipped game archive.
	 * 
	 * @param fileName
	 *            filename of the game archive.
	 */
	public ArchiveReader(String fileName) {
		try {
			gameArchive = new ZipFile(fileName);
		} catch (IOException e) {
			LOGGER.severe("Cannot get \"" + fileName + '"');
		}
		gameInformation = gameArchive.getEntry("GameInformation.xml");
		monster = gameArchive.getEntry("Monster.xml");
		item = gameArchive.getEntry("Item.xml");

		mapFolder = gameArchive.getEntry("maps");
		tilesetFolder = gameArchive.getEntry("tilesets");
		charsetFolder = gameArchive.getEntry("charsets");
	}

	/**
	 * returns the content of GameInformation.xml.
	 * 
	 * @return content
	 */
	public String getGameInformation() {
		String content;
		try {
			content = getTextFileContent(gameArchive.getInputStream(gameInformation));
		} catch (IOException e) {
			LOGGER.severe("Cannot get GameInformation.xml content");
			content = null;
		}
		return content;
	}

	/**
	 * returns the content of Monster.xml.
	 * 
	 * @return content
	 */
	public String getMonster() {
		String content;
		try {
			content = getTextFileContent(gameArchive.getInputStream(monster));
		} catch (IOException e) {
			LOGGER.severe("Cannot get Monster.xml content");
			content = null;
		}
		return content;
	}

	/**
	 * returns the content of Item.xml.
	 * 
	 * @return content
	 */
	public String getItem() {
		String content;
		try {
			content = getTextFileContent(gameArchive.getInputStream(item));
		} catch (IOException e) {
			LOGGER.severe("Cannot get Item.xml content");
			content = null;
		}
		return content;
	}

	/**
	 * returns the content of a text file.
	 * 
	 * @param inputStream
	 *            inputStream of a game file.
	 * @return content
	 */
	private String getTextFileContent(InputStream inputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder builder = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				builder.append(line.trim());
			}
		} catch (IOException e) {
			LOGGER.severe("Cannot get Line");
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			LOGGER.severe("Cannot close input stream");
		}
		return builder.toString();
	}
}