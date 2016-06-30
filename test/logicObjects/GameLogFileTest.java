package logicObjects;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import boardObjects.Board;

public class GameLogFileTest {

	@Test
	public void testRead() throws IOException {
		File file = new File("GameLog.txt");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("X0\n");
		fileWriter.close();
		
		GameLogFile gameLogFile = GameLogFile.getInstance();
		gameLogFile.readFile();
		GameLog log = gameLogFile.getLog(1);
		assertEquals(log.getMove(1), Board.NORTHWEST);
		
		gameLogFile.eraseFile();
	}
	
	@Test (expected=IndexOutOfBoundsException.class)
	public void testEraseFile() {
		GameLogFile file = GameLogFile.getInstance();
		GameLog gl = new GameLog();
		gl.add(Board.NORTHWEST);
		gl.add(Board.NORTH);
		gl.add(Board.WEST);
		gl.add(Board.CENTER);
		gl.add(Board.SOUTHWEST);
		
		file.add(gl);
		
		gl = file.getLog(1);
		assertEquals(gl.getMove(3), Board.WEST);
		
		file.eraseFile();
		file.getLog(1);
	}
	
	@Test
	public void testAdd() {
		GameLogFile file = GameLogFile.getInstance();
		GameLog gl = new GameLog();
		gl.add(Board.NORTHWEST);
		gl.add(Board.NORTH);
		gl.add(Board.WEST);
		gl.add(Board.CENTER);
		gl.add(Board.SOUTHWEST);
		
		file.add(gl);
		
		gl = file.getLog(1);
		assertEquals(gl.getMove(3), Board.WEST);
		
		file.eraseFile();
	}
	
	@Test
	public void testWrite() throws IOException {
		GameLogFile file = GameLogFile.getInstance();
		file.eraseFile();
		
		GameLog gl = new GameLog();
		gl.add(Board.NORTHWEST);
		gl.add(Board.NORTH);
		gl.add(Board.WEST);
		gl.add(Board.CENTER);
		gl.add(Board.SOUTHWEST);
		
		file.add(gl);
		
		gl = new GameLog();
		gl.add(Board.NORTHEAST);
		gl.add(Board.SOUTH);
		gl.add(Board.SOUTHEAST);
		gl.add(Board.EAST);
		gl.add(Board.CENTER);
		gl.add(Board.SOUTHWEST);
		gl.add(Board.NORTH);
		gl.add(Board.NORTHWEST);
		gl.add(Board.WEST);
		
		file.add(gl);
		
		file.writeFile();
		
		file = GameLogFile.getInstance();
		
		gl = file.getLog(1);
		assertEquals(gl.getMove(3), Board.WEST);
		
		gl = file.getLog(2);
		assertEquals(gl.getMove(8), Board.NORTHWEST);
		
		file.eraseFile();
	}
	
	@Test
	public void testSize() throws IOException {
		GameLogFile file = GameLogFile.getInstance();
		file.eraseFile();
		
		GameLog gl = new GameLog();
		gl.add(Board.NORTHWEST);
		gl.add(Board.NORTH);
		gl.add(Board.WEST);
		gl.add(Board.CENTER);
		gl.add(Board.SOUTHWEST);
		
		file.add(gl);
		
		gl = new GameLog();
		gl.add(Board.NORTHEAST);
		gl.add(Board.SOUTH);
		gl.add(Board.SOUTHEAST);
		gl.add(Board.EAST);
		gl.add(Board.CENTER);
		gl.add(Board.SOUTHWEST);
		gl.add(Board.NORTH);
		gl.add(Board.NORTHWEST);
		gl.add(Board.WEST);
		
		file.add(gl);
		
		file.writeFile();
		
		file = GameLogFile.getInstance();
		
		assertEquals(file.size(), 2);
	}
}
