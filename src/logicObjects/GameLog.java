package logicObjects;

import boardObjects.Board;
import boardObjects.Space;

/**
 * This class is an implementation of the log of one game of Tic-Tac-Toe.
 * @author JeremyMa
 *
 */
public class GameLog {

	private int size = 0;
	private char firstPlayer;
	private int[] moves = new int[9];
	
	/**
	 * Creates a default new <code>GameLog</code> where the first player is X.
	 */
	public GameLog() {
		firstPlayer = Space.X;
	}
	
	/**
	 * Creates a new <code>GameLog</code> where the first player is specified by the caller.
	 * @param newFirstPlayer The <code>Space</code> constant denoting who the first player will be.
	 */
	public GameLog(char newFirstPlayer) {
		if(!(newFirstPlayer == Space.X || newFirstPlayer == Space.O)) {
			throw new IllegalArgumentException("Invalid player found while instantiating Move.");
		}
		firstPlayer = newFirstPlayer;
	}
	
	/**
	 * Creates a new <code>GameLog</code> from a <code>String</code> that was already generated.
	 * @param string A <code>String</code> containing the data from a game of Tic-Tac-Toe.
	 */
	public GameLog(String string) {
		firstPlayer = string.charAt(0);
		for(int i = 1; i < string.length(); i++) {
			boolean b = this.add(Integer.parseInt(string.substring(i,i+1)));
			if(!b) {
				throw new IllegalArgumentException("Bad data while initializing GameLog");
			}
		}
	}

	/**
	 * Specifies whether or not there are entries in this <code>GameLog</code>.
	 * @return <code>true</code> if there are no entries in this <code>GameLog</code>, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Adds an entry to this <code>GameLog</code>.
	 * @param move A <code>Board</code> constant specifying the location on the board of this move.
	 * @return <code>true</code> if the <code>GameLog</code> is not full and does not already have this move, <code>false</code> otherwise.
	 */
	public boolean add(int move) {
		if(size>=9) {
			return false;
		}
		for(int i = 0; i < size; i++) {
			if(moves[i] == move) {
				return false;
			}
		}
		moves[size++] = move;
		return true;
	}

	/**
	 * Reconstructs the board layout using all the entries of this <code>GameLog</code>
	 * @return A <code>Board</code> containing the reconstructed board.
	 */
	public Board reconstruct() {
		return this.reconstruct(size);
	}

	/**
	 * Reconstructs the board layout using the entries of this <code>GameLog</code> up to a specified number of moves.
	 * @param The number of moves to reconstruct.
	 * @return A <code>Board</code> containing the reconstructed board.
	 */
	public Board reconstruct(int numberOfMoves) {
		Board reconstructedBoard = new Board();
		
		for(int i = 0; i < numberOfMoves; i++) {
			int tempMove = moves[i];
			if (whoseTurn(i+1) == Space.X) {
				reconstructedBoard.setX(tempMove);
			} else {
				reconstructedBoard.setO(tempMove);
			}
		}
		
		return reconstructedBoard;
	}

	/**
	 * Converts this <code>GameLog</code> to a <code>String</code>.
	 */
	@Override
	public String toString() {
		String result = Character.toString(firstPlayer);
		for(int i = 0; i < size; i++) {
			result = result + moves[i];
		}
		return result;
	}

	/**
	 * Gives the position of the <code>i</code>th move.
	 * @param i A move number.
	 * @return A <code>Board</code> constant representing the position of the specified move.
	 */
	public int getMove(int i) {
		if (i > size) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return moves[i-1];
	}
	
	private GameLog rotateReplace(int[] rotation) {
		GameLog result = new GameLog();
		for(int i = 0; i < size; i++) {
			result.add(rotation[moves[i]]);
		}
		return result;
	}

	/**
	 * Returns a copy of this <code>GameLog</code>, with all the positions rotated 90 degrees clockwise.
	 * @return A copy of this <code>GameLog</code>, with all the positions rotated 90 degrees clockwise.
	 */
	public GameLog rotateClockwise() {
		int[] clockwiseRotation =
				{Board.NORTHEAST	,Board.EAST		,Board.SOUTHEAST
				,Board.NORTH		,Board.CENTER	,Board.SOUTH
				,Board.NORTHWEST	,Board.WEST		,Board.SOUTHWEST};
		return rotateReplace(clockwiseRotation);
	}

	/**
	 * Returns a copy of this <code>GameLog</code>, with all the positions rotated 180 degrees.
	 * @return A copy of this <code>GameLog</code>, with all the positions rotated 180 degrees.
	 */
	public GameLog rotate180() {
		int[] halfRotation =
				{Board.SOUTHEAST	,Board.SOUTH	,Board.SOUTHWEST
				,Board.EAST			,Board.CENTER	,Board.WEST
				,Board.NORTHEAST	,Board.NORTH	,Board.NORTHWEST};
		return rotateReplace(halfRotation);
	}

	/**
	 * Returns a copy of this <code>GameLog</code>, with all the positions rotated 90 degrees counterclockwise.
	 * @return A copy of this <code>GameLog</code>, with all the positions rotated 90 degrees counterclockwise.
	 */
	public GameLog rotateCounterClockwise() {
		int[] counterClockwiseRotation =
				{Board.SOUTHWEST	,Board.WEST		,Board.NORTHWEST
				,Board.SOUTH		,Board.CENTER	,Board.NORTH
				,Board.SOUTHEAST	,Board.EAST		,Board.NORTHEAST};
		return rotateReplace(counterClockwiseRotation);
	}
	
	private char whoseTurn(int currentTurn) {
		if(firstPlayer == Space.X) {
			if(currentTurn % 2 == 0) {
				return Space.O;
			}
			return Space.X;
		}
		if(currentTurn % 2 == 0) {
			return Space.X;
		}
		return Space.O;
	}

	/**
	 * Returns a copy of this <code>GameLog</code> with all the positions rotated so that the first move is in the top-left, top-center, or center position. If the first move is in the center position, the <code>GameLog</code> will be rotated so that the second move is in the top-left or the top-center position.
	 * @return A copy of this <code>GameLog</code> with all the positions rotated so that it follows the above-mentioned rules.
	 */
	public GameLog rotateUntilTemplateMatch() {
		int target = moves[0];
		if(target == Board.CENTER) {
			target = moves[1];
		}
		
		if(target == Board.NORTHWEST || target == Board.NORTH) {
			return this;
		} else if (target == Board.NORTHEAST || target == Board.EAST) {
			return rotateCounterClockwise();
		} else if (target == Board.SOUTHEAST || target == Board.SOUTH) {
			return rotate180();
		} else if (target == Board.SOUTHWEST || target == Board.WEST) {
			return rotateClockwise();
		} else {
			return null;
		}
	}
}
