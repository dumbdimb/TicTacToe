package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import boardObjects.Board;
import logicObjects.GameController;
import logicObjects.GameLogFile;

public class AITest {

	@Test
	public void testPickSpace() {
		GameLogFile.getInstance().eraseFile();
		AI ai = AI.getInstance();
		GameController ttt = GameController.getInstance();
		ttt.startGame();
		
		assertTrue(ai.pickSpace());
		
		assertEquals(ttt.getGame().getBoard().getEmptySpaces().length, 8);
	}
	
	@Test
	public void testPickFreeSpace() {
		GameLogFile.getInstance().eraseFile();
		AI ai = AI.getInstance();
		GameController ttt = GameController.getInstance();
		ttt.endGame();
		ttt.setXGoesFirst();
		ttt.startGame();
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.CENTER);
		ttt.play(Board.EAST);
		
		assertTrue(ai.pickSpace());
		assertEquals(5, ttt.getGame().getBoard().getEmptySpaces().length);
	}

	@Test
	public void testIntelligence() {
		GameLogFile.getInstance().eraseFile();
		AI ai = AI.getInstance();
		GameController ttt = GameController.getInstance();
		ttt.endGame();
		ttt.setXGoesFirst();
		ttt.startGame();
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.NORTH);
		ttt.play(Board.NORTHEAST);
		ttt.play(Board.CENTER);
		ttt.play(Board.WEST);
		ttt.play(Board.SOUTHEAST);
		ttt.play(Board.SOUTH);
		ttt.play(Board.EAST);
		ttt.play(Board.SOUTHWEST);
		assertFalse(ttt.isGameActive());
		
		ttt.startGame();
		ttt.play(Board.SOUTHEAST);
		ttt.play(Board.SOUTH);
		ttt.play(Board.SOUTHWEST);
		ttt.play(Board.CENTER);
		ttt.play(Board.EAST);
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.NORTH);
		assertTrue(ai.pickSpace());
		assertNotEquals(Board.WEST,ttt.getGame().getGameLog().getMove(8));
		assertTrue(ttt.play(Board.WEST));
		assertFalse(ttt.isGameActive());
		
		ttt.startGame();
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.NORTH);
		ttt.play(Board.CENTER);
		ttt.play(Board.WEST);
		ttt.play(Board.SOUTHEAST);
		assertFalse(ttt.isGameActive());
		
		ttt.startGame();
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.NORTH);
		ttt.play(Board.CENTER);
		assertTrue(ai.pickSpace());
		boolean found = false;
		for(int i : ttt.getGame().getBoard().getEmptySpaces()) {
			if(i == Board.WEST) {
				found = true;
				break;
			}
		}
		if(!found) {
			fail("WEST not found.");
		}
	}
	
	@Test
	public void testGetLastMove() {
		GameLogFile.getInstance().eraseFile();
		AI ai = AI.getInstance();
		GameController ttt = GameController.getInstance();
		ttt.endGame();
		ttt.setXGoesFirst();
		ttt.startGame();
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.NORTH);
		ttt.play(Board.NORTHEAST);
		ttt.play(Board.CENTER);
		ttt.play(Board.WEST);
		ttt.play(Board.SOUTHEAST);
		ttt.play(Board.SOUTH);
		ttt.play(Board.EAST);
		ttt.play(Board.SOUTHWEST);
		assertFalse(ttt.isGameActive());
		
		ttt.startGame();
		ttt.play(Board.SOUTHEAST);
		ttt.play(Board.SOUTH);
		ttt.play(Board.SOUTHWEST);
		ttt.play(Board.CENTER);
		ttt.play(Board.EAST);
		ttt.play(Board.NORTHWEST);
		ttt.play(Board.NORTH);
		assertTrue(ai.pickSpace());
		assertEquals(Board.NORTHEAST,ai.getPreviousMove());
	}
}
