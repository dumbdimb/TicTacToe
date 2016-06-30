package boardObjects;

import static org.junit.Assert.*;

import org.junit.Test;

import boardObjects.Board;
import boardObjects.Space;

public class BoardTest {

	@Test
	public void testIsEmpty() {
		Board tttb = new Board();
		assertTrue(tttb.isEmpty());
	}
	
	@Test
	public void testSetOneX() {
		Board tttb = new Board();
		assertTrue(tttb.setX(Board.NORTHWEST));
		assertFalse(tttb.isEmpty());
		assertEquals(Space.X, tttb.getBoard()[Board.NORTHWEST].getTheCurrentState());
	}
	
	@Test
	public void testSetOneO() {
		Board tttb = new Board();
		assertTrue(tttb.setO(Board.NORTHWEST));
		assertFalse(tttb.isEmpty());
		assertEquals(Space.O, tttb.getBoard()[Board.NORTHWEST].getTheCurrentState());
	}

	@Test
	public void testToString() {
		Board tttb = new Board();
		tttb.setX(Board.NORTHWEST);
		tttb.setX(Board.EAST);
		tttb.setX(Board.SOUTH);
		tttb.setO(Board.NORTHEAST);
		tttb.setO(Board.WEST);
		tttb.setO(Board.SOUTHEAST);
		assertEquals("X| |O\n-----\nO| |X\n-----\n |X|O", tttb.toString());
	}
	
	@Test
	public void testEquals() {
		Board board1 = new Board();
		Board board2 = new Board();
		Board board3 = new Board();

		board1.setX(Board.NORTHWEST);
		board2.setX(Board.NORTHWEST);
		
		board1.setX(Board.EAST);
		board2.setX(Board.EAST);
		
		board1.setX(Board.SOUTH);
		board2.setX(Board.SOUTH);
		
		board1.setO(Board.NORTHEAST);
		board2.setO(Board.NORTHEAST);
		
		board1.setO(Board.WEST);
		board2.setO(Board.WEST);
		
		board1.setO(Board.SOUTHEAST);
		board2.setO(Board.SOUTHEAST);
		
		assertTrue(board1.equals(board2));
		assertFalse(board1.equals(board3));
		assertFalse(board2.equals(board3));
	}
	
	@Test
	public void testGetEmptySpaces() {
		Board tttb = new Board();
		tttb.setX(Board.NORTHWEST);
		tttb.setX(Board.EAST);
		tttb.setX(Board.SOUTH);
		tttb.setO(Board.NORTHEAST);
		tttb.setO(Board.WEST);
		tttb.setO(Board.SOUTHEAST);
		int[] freeSpaces = tttb.getEmptySpaces();
		assertTrue(freeSpaces[0] == Board.NORTH || freeSpaces[0] == Board.CENTER || freeSpaces[0] == Board.SOUTHWEST);
		assertTrue(freeSpaces[1] == Board.NORTH || freeSpaces[1] == Board.CENTER || freeSpaces[1] == Board.SOUTHWEST);
		assertTrue(freeSpaces[2] == Board.NORTH || freeSpaces[2] == Board.CENTER || freeSpaces[2] == Board.SOUTHWEST);
		assertEquals(freeSpaces.length, 3);
		
	}
}
