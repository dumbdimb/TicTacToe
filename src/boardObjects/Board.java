package boardObjects;

/**
 * An implementation of a Tic-Tac-Toe board
 * @author Jeremy Ma
 *
 */
public class Board {
	
	/**
	 * A constant representing the top-left space of the board.
	 */
	public static final int NORTHWEST	= 0;
	/**
	 * A constant representing the top-center space of the board.
	 */
	public static final int NORTH		= 1;
	/**
	 * A constant representing the top-right space of the board.
	 */
	public static final int NORTHEAST	= 2;
	/**
	 * A constant representing the center-left space of the board.
	 */
	public static final int WEST		= 3;
	/**
	 * A constant representing the center space of the board.
	 */
	public static final int CENTER		= 4;
	/**
	 * A constant representing the center-right space of the board.
	 */
	public static final int EAST		= 5;
	/**
	 * A constant representing the bottom-left space of the board.
	 */
	public static final int SOUTHWEST	= 6;
	/**
	 * A constant representing the bottom-center space of the board.
	 */
	public static final int SOUTH		= 7;
	/**
	 * A constant representing the bottom-right space of the board.
	 */
	public static final int SOUTHEAST	= 8;
	
	private Space[] board = {new Space(), new Space(), new Space(), new Space(), new Space(), new Space(), new Space(), new Space(), new Space()};

	/**
	 * Specifies whether or not this <code>Board</code> is empty.
	 * @return <code>true</code> if this <code>Board</code> is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		for(Space ttts : board) {
			if(!ttts.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the contents of this <code>Board</code>.
	 * @return The contents of this <code>Board</code>.
	 */
	public Space[] getBoard() {
		return board;
	}

	/**
	 * Sets one of the board spaces to X.
	 * @param position The position to set.
	 * @return <code>true</code> if the position is valid to be set to X, <code>false</code> otherwise.
	 */
	public boolean setX(int position) {
		return board[position].setX();
	}

	/**
	 * Sets one of the board spaces to O.
	 * @param position The position to set.
	 * @return <code>true</code> if the position is valid to be set to O, <code>false</code> otherwise.
	 */
	public boolean setO(int position) {
		return board[position].setO();
	}
	
	/**
	 * Converts this <code>Board</code> into a <code>String</code>
	 */
	@Override
	public String toString() {
		return	board[NORTHWEST]+	"|"+	board[NORTH]+	"|"+	board[NORTHEAST]+"\n"+
				"-----\n"+
				board[WEST]+		"|"+	board[CENTER]+	"|"+	board[EAST]+"\n"+
				"-----\n"+
				board[SOUTHWEST]+	"|"+	board[SOUTH]+	"|"+	board[SOUTHEAST];
	}

	/**
	 * Compares this <code>Board</code> to another <code>Board</code>.
	 */
	@Override
	public boolean equals(Object other) {
		Board otherBoard = (Board) other;
		return this.toString().equals(otherBoard.toString());
	}

	/**
	 * Specifies which spaces in this <code>Board</code> are still empty.
	 * @return An array that includes the <code>Board</code> constants that correspond to empty spaces in this <code>Board</code>
	 */
	public int[] getEmptySpaces() {
		int size = 0;
		for(int i = 0; i < 9; i++) {
			if(board[i].isEmpty()) {
				size++;
			}
		}
		
		int[] result = new int[size];
		int j = 0;
		for(int i = 0; i < 9; i++) {
			if(board[i].isEmpty()) {
				result[j++] = i;
			}
		}
		
		return result;
	}
}
