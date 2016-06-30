package logicObjects;

import static org.junit.Assert.*;

import org.junit.Test;

import boardObjects.Board;
import boardObjects.Space;

public class GameTest {

	@Test
	public void testDefaultConstructor() {
		Game game = new Game();
		assertEquals(game.getFirstPlayer(), Space.X);
		assertEquals(game.getCurrentTurn(), 1);
		assertTrue(game.getBoard().isEmpty());
		assertTrue(game.getGameLog().isEmpty());
	}
	
	@Test
	public void testCustomConstructor() {
		Game game = new Game(Space.O);
		assertEquals(game.getFirstPlayer(), Space.O);
		assertEquals(game.getCurrentTurn(), 1);
		assertTrue(game.getBoard().isEmpty());
		assertTrue(game.getGameLog().isEmpty());

		game = new Game(Space.X);
		assertEquals(game.getFirstPlayer(), Space.X);
		assertEquals(game.getCurrentTurn(), 1);
		assertTrue(game.getBoard().isEmpty());
		assertTrue(game.getGameLog().isEmpty());
	}
	
	@Test
	public void testWhoseTurn() {
		Game game = new Game(Space.O);
		assertEquals(game.whoseTurn(), Space.O);

		game = new Game(Space.X);
		assertEquals(game.whoseTurn(), Space.X);
	}
	
	@Test
	public void testPlayAndThenWhoseTurn() {
		Game game = new Game();
		assertTrue(game.play(Board.NORTHWEST));
		assertEquals(game.whoseTurn(), Space.O);
	}
	
	@Test
	public void testInvalidPlay() {
		Game game = new Game();
		assertTrue(game.play(Board.NORTHWEST));
		assertFalse(game.play(Board.NORTHWEST));
	}
}
