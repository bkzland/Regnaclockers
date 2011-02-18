package interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * This class is used to read the content of a game archive.
 * 
 * @author regnaclockers
 */
public class ArchiveReader {

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
			e.printStackTrace();
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
	protected String getGameInformation() {
		String content;
		try {
			content = getTextFileContent(gameArchive.getInputStream(gameInformation));
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		}
		return content;
	}

	/**
	 * returns the content of Monster.xml.
	 * 
	 * @return content
	 */
	protected String getMonster() {
		String content;
		try {
			content = getTextFileContent(gameArchive.getInputStream(monster));
		} catch (IOException e) {
			e.printStackTrace();
			content = null;
		}
		return content;
	}

	/**
	 * returns the content of Item.xml.
	 * 
	 * @return content
	 */
	protected String getItem() {
		String content;
		try {
			content = getTextFileContent(gameArchive.getInputStream(item));
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content = builder.toString();
		return content;
	}
}
