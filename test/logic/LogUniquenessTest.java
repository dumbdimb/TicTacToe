package logic;

import static org.junit.Assert.*;

import org.junit.Test;

import logicObjects.GameLogFile;

public class LogUniquenessTest {

	@Test
	public void test() {
		GameLogFile glf = GameLogFile.getInstance();
		for(int i = 1; i <= glf.size(); i++) {
			for(int j = i+1; j<=glf.size(); j++) {
				assertNotEquals(glf.getLog(i).toString(), glf.getLog(j).toString());
				assertNotEquals(glf.getLog(i).toString(), glf.getLog(j).rotateClockwise().toString());
				assertNotEquals(glf.getLog(i).toString(), glf.getLog(j).rotate180().toString());
				assertNotEquals(glf.getLog(i).toString(), glf.getLog(j).rotateCounterClockwise().toString());
			}
		}
	}

}
