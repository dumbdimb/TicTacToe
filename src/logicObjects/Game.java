package logicObjects;

import boardObjects.Board;
import boardObjects.Space;

/**
 * This class is an implementation of a game of Tic-Tac-Toe.
 * @author JeremyMa
 *
 */
public class Game {

	private char firstPlayer;
	
	private Board board;
	private GameLog gameLog;
	
	private int currentTurn;
	
	/**
	 * Constructs a new <code>Game</code> where X goes first.
	 */
	public Game() {
		setFirstPlayer(Space.X);
		currentTurn = 1;
		board = new Board();
		gameLog = new GameLog();
	}
	
	/**
	 * Constructs a new <code>Game</code> where the <code>GameController</code> chooses who goes first.
	 * @param newFirstPlayer
	 */
	public Game(char newFirstPlayer) {
		setFirstPlayer(newFirstPlayer);
		currentTurn = 1;
		board = new Board();
		gameLog = new GameLog(newFirstPlayer);
	}

	private void setFirstPlayer(char newFirstPlayer) {
		if(!(newFirstPlayer == Space.X || newFirstPlayer == Space.O)) {
			throw new IllegalArgumentException("Invalid player found while instantiating Move.");
		}
		firstPlayer = newFirstPlayer;
	}

	/**
	 * Gives back who the first player is.
	 * @return <code>X</code> or <code>O</code> if the game has started, an empty character (<code>' '</code>) otherwise.
	 */
	public char getFirstPlayer() {
		return firstPlayer;
	}
	
	/**
	 * Gives back the current turn number.
	 * @return The current turn number.
	 */
	public int getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * Gives back the <code>Board</code> associated with this <code>Game</code>.
	 * @return The <code>Board</code> associated with this <code>Game</code>.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Gives back the <code>GameLog</code> associated with this <code>Game</code>.
	 * @return The <code>GameLog</code> associated with this <code>Game</code>.
	 */
	public GameLog getGameLog() {
		return gameLog;
	}

	/**
	 * Gives back who the current player is.
	 * @return <code>X</code> or <code>O</code>, depending on whose turn it is.
	 */
	public char whoseTurn() {
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
	 * Marks the board with the appropriate player's mark at the specified position.
	 * @param position One of the <code>Space</code> constants.
	 * @return <code>true</code> if the play is valid, <code>false</code> otherwise.
	 */
	public boolean play(int position) {
		boolean b = gameLog.add(position);
		if (b) {
			if (whoseTurn() == Space.X) {
				board.setX(position);
			} else {
				board.setO(position);
			}
			currentTurn++;
		}
		return b;
	}
}
