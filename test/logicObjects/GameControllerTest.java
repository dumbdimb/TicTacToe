package logicObjects;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boardObjects.Board;
import boardObjects.Space;
import logicObjects.Game;
import logicObjects.GameController;
import logicObjects.GameLogFile;

public class GameControllerTest {
	
	private GameController ttt;
	
	@Before
	public void setUp() {
		ttt = GameController.getInstance();
		ttt.endGame();
	}
	
	@After
	public void tearDown() {
		ttt.endGame();
	}
	
	@Test
	public void testStartGetEndGame() {
		assertTrue(ttt.startGame());
		Game game = ttt.getGame();
		assertTrue(game.getGameLog().isEmpty());
		assertTrue(ttt.endGame());
		assertNull(ttt.getGame());
	}
	
	@Test
	public void testWhoGoesFirst() {
		assertTrue(ttt.setXGoesFirst());
		ttt.startGame();
		assertEquals(ttt.getGame().getFirstPlayer(),Space.X);
		ttt.endGame();
		assertTrue(ttt.setOGoesFirst());
		ttt.startGame();
		assertEquals(ttt.getGame().getFirstPlayer(),Space.O);
	}
	
	@Test
	public void getCurrentPlayer() {
		ttt.setXGoesFirst();
		ttt.startGame();
		assertTrue(ttt.play(Board.NORTHWEST));
		assertEquals(ttt.getCurrentPlayer(),Space.O);
	}
	
	@Test
	public void testPlay() {
		ttt.setXGoesFirst();
		ttt.startGame();
		assertTrue(ttt.play(Board.NORTHWEST));
		assertFalse(ttt.play(Board.NORTHWEST));
		assertTrue(ttt.play(Board.NORTH));
		assertEquals(ttt.getCurrentPlayer(),Space.X);
	}

	@Test
	public void testIsGameActive() {
		assertFalse(ttt.isGameActive());
		ttt.startGame();
		assertTrue(ttt.isGameActive());
		ttt.endGame();
		assertFalse(ttt.isGameActive());
	}
	
	@Test
	public void testCheckForWin() {
		assertFalse(ttt.isGameActive());
		ttt.startGame();
		
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.NORTH);
		ttt.play(Board.WEST);
		ttt.play(Board.CENTER);
		assertTrue(ttt.isGameActive());
		ttt.play(Board.SOUTHWEST);
		assertFalse(ttt.isGameActive());
		
		assertTrue(ttt.startGame());
		
		ttt.play(Board.SOUTHWEST);
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.CENTER);
		ttt.play(Board.WEST);
		assertTrue(ttt.isGameActive());
		ttt.play(Board.NORTHEAST);
		assertFalse(ttt.isGameActive());
	}
	
	@Test
	public void testCheckForTie() {
		ttt.startGame();
		ttt.play(Board.NORTHEAST);
		ttt.play(Board.SOUTH);
		ttt.play(Board.SOUTHEAST);
		ttt.play(Board.EAST);
		ttt.play(Board.CENTER);
		ttt.play(Board.SOUTHWEST);
		ttt.play(Board.NORTH);
		ttt.play(Board.NORTHWEST);
		assertTrue(ttt.isGameActive());
		ttt.play(Board.WEST);
		assertFalse(ttt.isGameActive());
	}
	
	@Test
	public void testWriteLossToFile() throws IOException, InterruptedException {
		GameLogFile.getInstance().eraseFile();
		
		ttt.setXGoesFirst();
		
		ttt.startGame();
		
		ttt.play(Board.SOUTHWEST);
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.CENTER);
		ttt.play(Board.WEST);
		ttt.play(Board.NORTHEAST);
		
		assertFalse(ttt.isGameActive());
		
		File manualReader = new File("GameLog.txt");
		FileReader fileReader = new FileReader(manualReader);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String firstLine = bufferedReader.readLine();
		assertEquals("X60432",firstLine);
		
		bufferedReader.close();
		fileReader.close();
		Files.delete(manualReader.toPath());
	}
	
	@Test
	public void testGetLastWinner() {
		GameLogFile.getInstance().eraseFile();
		
		ttt.setXGoesFirst();
		
		ttt.startGame();
		
		ttt.play(Board.SOUTHWEST);
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.CENTER);
		ttt.play(Board.WEST);
		ttt.play(Board.NORTHEAST);
		
		assertFalse(ttt.isGameActive());
		
		assertEquals(ttt.getLastWinner(), Space.X);
	}
}
