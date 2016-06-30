package logicObjects;

import static org.junit.Assert.*;

import org.junit.Test;

import boardObjects.Board;

public class GameLogTest {

	@Test
	public void testIsEmpty() {
		GameLog gl = new GameLog();
		assertTrue(gl.isEmpty());
	}
	
	@Test
	public void testAdd() {
		GameLog gl = new GameLog();
		assertTrue(gl.add(Board.NORTHWEST));
		assertFalse(gl.isEmpty());
	}
	
	@Test
	public void testReconstructAll() {
		GameLog gl = setUpSampleGame();
		Board b = gl.reconstruct();
		assertEquals(b.toString(), "X|O| \n-----\nX|O| \n-----\nX| | ");
	}
	
	@Test
	public void testReconstructSome() {
		GameLog gl = setUpSampleGame();
		Board b = gl.reconstruct(4);
		assertEquals(b.toString(), "X|O| \n-----\nX|O| \n-----\n | | ");
	}
	
	@Test
	public void testToString() {
		GameLog gl = setUpSampleGame();
		assertEquals(gl.toString(), "X01346");
	}
	
	@Test
	public void testGetMove() {
		GameLog gl = setUpSampleGame();
		assertEquals(gl.getMove(3), Board.WEST);
	}

	private GameLog setUpSampleGame() {
		GameLog gl = new GameLog();
		assertTrue(gl.add(Board.NORTHWEST));
		assertTrue(gl.add(Board.NORTH));
		assertTrue(gl.add(Board.WEST));
		assertTrue(gl.add(Board.CENTER));
		assertTrue(gl.add(Board.SOUTHWEST));
		return gl;
	}

	@Test
	public void testCustomConstructor() {
		GameLog gl = new GameLog("X01346");
		assertEquals(gl.getMove(3), Board.WEST);
	}
	
	@Test
	public void testIgnoreDuplicates() {
		GameLog gl = new GameLog();
		assertTrue(gl.add(Board.NORTHWEST));
		assertFalse(gl.add(Board.NORTHWEST));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCustomConstructorDuplicate() {
		new GameLog("X00");
	}
	
	@Test
	public void testRotateClockwise() {
		GameLog gl = setUpSampleGame();
		GameLog rotated = gl.rotateClockwise();
		assertEquals(rotated.getMove(3), Board.NORTH);
	}
	
	@Test
	public void testRotateHalfway() {
		GameLog gl = setUpSampleGame();
		GameLog rotated = gl.rotate180();
		assertEquals(rotated.getMove(3), Board.EAST);
	}

	@Test
	public void testRotateCounterClockwise() {
		GameLog gl = setUpSampleGame();
		GameLog rotated = gl.rotateCounterClockwise();
		assertEquals(rotated.getMove(3), Board.SOUTH);
	}
	
	@Test
	public void testRotateUntilTemplateMatch() {
		GameLog gl = setUpSampleGame();
		GameLog rotated = gl.rotate180();
		rotated = rotated.rotateUntilTemplateMatch();
		assertEquals(rotated.getMove(1), Board.NORTHWEST);
		
		GameLog gl2 = new GameLog();
		gl2.add(Board.CENTER);
		gl2.add(Board.SOUTHEAST);
		rotated = gl2.rotateUntilTemplateMatch();
		assertEquals(rotated.getMove(1), Board.CENTER);
		assertEquals(rotated.getMove(2), Board.NORTHWEST);
	}
}
