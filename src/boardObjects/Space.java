package boardObjects;

/**
 * An implementation of one Tic-Tac-Toe grid space.
 * @author JeremyMa
 *
 */
public class Space {
	
	/**
	 * A constant representing an empty space.
	 */
	public static final char EMPTY = ' ';
	/**
	 * A constant representing a space filled with an X
	 */
	public static final char X = 'X';
	/**
	 * A constant representing a space filled with an O
	 */
	public static final char O = 'O';
	
	private char theCurrentState = EMPTY;
	
	/**
	 * Specifies what this <code>Space</code> currently holds.
	 * @return A <code>Space</code> constant denoting what this <code>Space</code> currently holds.
	 */
	public char getTheCurrentState() {
		return theCurrentState;
	}
	
	/**
	 * Specifies whether or not this <code>Space</code> is empty.
	 * @return <code>true</code> if this <code>Space</code> is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return theCurrentState == EMPTY;
	}

	/**
	 * Sets this <code>Space</code> to hold X.
	 * @return <code>true</code> if setting this <code>Space</code> to X succeeds, <code>false</code> otherwise.
	 */
	public boolean setX() {
		if(!isEmpty()) {
			return false;
		}
		theCurrentState = X;
		return true;
	}

	/**
	 * Sets this <code>Space</code> to hold O.
	 * @return <code>true</code> if setting this <code>Space</code> to O succeeds, <code>false</code> otherwise.
	 */
	public boolean setO() {
		if(!isEmpty()) {
			return false;
		}
		theCurrentState = O;
		return true;
	}

	/**
	 * Clears this <code>Space</code>.
	 */
	public void reset() {
		theCurrentState = EMPTY;
		return;
	}
	
	/**
	 * Converts this <code>Space</code> into a <code>String</code>.
	 */
	@Override
	public String toString() {
		return Character.toString(theCurrentState);
	}

}
