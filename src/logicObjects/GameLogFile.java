package logicObjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * This class is used for file I/O with the file that stores losses. 
 * @author JeremyMa
 *
 */
public class GameLogFile {
	private static final String FILE_LOCATION = "GameLog.txt";
	private static File FILE = new File(FILE_LOCATION);
	
	private ArrayList<GameLog> gameLogs = new ArrayList<GameLog>();
	
	private static final GameLogFile instance = new GameLogFile();
	
	private GameLogFile() {
		try {
			readFile();
		} catch (IOException e) { }
	}

	/**
	 * Gets the instance of <code>GameLogFile</code>.
	 * @return The instance of <code>GameLogFile</code>.
	 */
	public static GameLogFile getInstance() {
		return instance;
	}

	/**
	 * Imports the data from the losses file.
	 * @throws IOException Thrown when the file could not be found, or for another I/O error.
	 */
	public void readFile() throws IOException {
		FileReader fileReader = new FileReader(FILE);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String currentLine;
		while((currentLine = bufferedReader.readLine()) != null) {
			gameLogs.add(new GameLog(currentLine));
		}
		bufferedReader.close();
		fileReader.close();
	}

	/**
	 * Returns the <code>GameLog</code> on line <code>i</code> of the losses file.
	 * @param i The line number of the <code>GameLog</code> to retrieve.
	 * @return The <code>GameLog</code> that was requested.
	 */
	public GameLog getLog(int i) {
		return gameLogs.get(i-1);
	}

	/**
	 * Adds a <code>GameLog</code> to this file.
	 * @param gl The <code>GameLog</code> to add to this file.
	 */
	public void add(GameLog gl) {
		gameLogs.add(gl);
	}

	/**
	 * Writes the logs stored in this <code>GameLogFile</code> to the file.
	 * @throws IOException Thrown if there is an I/O error.
	 */
	public void writeFile() throws IOException {
		if(!FILE.exists()) {
			FILE = new File(FILE_LOCATION);
		}
		FileWriter fileWriter = new FileWriter(FILE);
		for(GameLog gameLog : gameLogs) {
			fileWriter.write(gameLog+"\n");
		}
		fileWriter.close();
	}

	/**
	 * Deletes the losses file and clears this <code>GameLogFile</code>.
	 */
	public void eraseFile() {
		gameLogs.clear();
		try {
			Files.delete(FILE.toPath());
		} catch (IOException e) { }
	}

	/**
	 * Specifies how many <code>GameLog</code>s are contained within this <code>GameLogFile</code>.
	 * @return The number of <code>GameLog</code>s are contained within this <code>GameLogFile</code>.
	 */
	public int size() {
		return gameLogs.size();
	}
}
