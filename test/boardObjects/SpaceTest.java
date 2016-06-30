package boardObjects;

import static org.junit.Assert.*;

import org.junit.Test;

import boardObjects.Space;

public class SpaceTest {
	
	@Test
	public void testEmpty() {
		Space ttts = new Space();
		assertTrue(ttts.isEmpty());
	}
	
	@Test
	public void testX() {
		Space ttts = new Space();
		ttts.setX();
		assertFalse(ttts.isEmpty());
		assertEquals(Space.X, ttts.getTheCurrentState());
	}
	
	@Test
	public void testO() {
		Space ttts = new Space();
		ttts.setO();
		assertFalse(ttts.isEmpty());
		assertEquals(Space.O, ttts.getTheCurrentState());
	}
	
	@Test
	public void testOneSet() {
		Space tttsX = new Space();
		assertTrue(tttsX.setX());
		assertFalse(tttsX.setO());
		assertEquals(Space.X, tttsX.getTheCurrentState());
		
		Space tttsO = new Space();
		assertTrue(tttsO.setO());
		assertFalse(tttsO.setX());
		assertEquals(Space.O, tttsO.getTheCurrentState());
	}
	
	@Test
	public void testReset() {
		Space ttts = new Space();
		ttts.setO();
		assertEquals(Space.O, ttts.getTheCurrentState());
		ttts.reset();
		assertTrue(ttts.isEmpty());
	}
	
	@Test
	public void testToString() {
		Space ttts = new Space();
		
		ttts.setX();
		assertEquals("X", ttts.toString());
		
		ttts.reset();
		assertEquals(" ", ttts.toString());
		
		ttts.setO();
		assertEquals("O", ttts.toString());
	}

}
