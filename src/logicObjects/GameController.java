package logicObjects;

import java.io.IOException;

import boardObjects.Board;
import boardObjects.Space;

/**
 * This class controls a game of Tic-Tac-Toe.
 * @author JeremyMa
 *
 */
public class GameController {
	
	private static final GameController instance = new GameController();
	
	private Game game;
	
	private boolean humanGoesFirst = true;
	private char currentPlayer;
	private char lastWinner = Space.EMPTY;
	
	private GameController() {}
	
	/**
	 * Gets the instance of <code>GameController</code>.
	 * @return The instance of <code>GameController</code>.
	 */
	public static GameController getInstance() {
		return instance;
	}
	
	/**
	 * Starts a game. This will return <code>false</code> if there is already a game in progress.
	 * @return <code>true</code> if the game can be started, <code>false</code> otherwise.
	 */
	public boolean startGame() {
		if(game != null) {
			return false;
		}
		game = new Game(humanGoesFirst?Space.X:Space.O);
		currentPlayer = game.getFirstPlayer();
		return true;
	}

	/**
	 * Gets the current <code>Game</code> that this <code>GameController</code> is controlling.
	 * @return The current <code>Game</code> that this <code>GameController</code> is controlling.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Ends the current game. This will return <code>false</code> if there is no game in progress.
	 * @return <code>true</code> if the game can be stopped, <code>false</code> otherwise.
	 */
	public boolean endGame() {
		if(game == null) {
			return false;
		}
		game = null;
		return true;
	}

	/**
	 * Makes X go first in this game. This will not work while a game is already in progress.
	 * @return <code>true</code> if the game has already started, <code>false</code> otherwise.
	 */
	public boolean setXGoesFirst() {
		if(game != null) {
			return false;
		}
		humanGoesFirst = true;
		return true;
	}

	/**
	 * Makes O go first in this game. This will not work while a game is already in progress.
	 * @return <code>true</code> if the game has already started, <code>false</code> otherwise.
	 */
	public boolean setOGoesFirst() {
		if(game != null) {
			return false;
		}
		humanGoesFirst = false;
		return true;
	}

	/**
	 * Marks the board with the appropriate player's mark at the specified position.
	 * @param position One of the <code>Space</code> constants.
	 * @return <code>true</code> if the play is valid, <code>false</code> otherwise.
	 */
	public boolean play(int position) {
		boolean playSuccess = game.play(position);
		if (playSuccess) {
			currentPlayer = currentPlayer == Space.X? Space.O : Space.X;
			lastWinner = Space.EMPTY;
			if ((lastWinner = checkForWin()) != Space.EMPTY) {
				if (lastWinner == Space.X) {
					try {
						writeLossToFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				endGame();
			} else if (checkForTie()) {
				lastWinner = Space.EMPTY;
				endGame();
			}
		}
		return playSuccess;
	}

	/**
	 * Gives back who the current player is.
	 * @return <code>X</code> or <code>O</code>, depending on whose turn it is.
	 */
	public char getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Specifies whether or not a game is currently in progress.
	 * @return <code>true</code> if a game is currently in progress, <code>false</code> otherwise.
	 */
	public boolean isGameActive() {
		return game != null;
	}
	
	private char checkForWin() {
		Space[] currentBoard = game.getBoard().getBoard();
		
		char northwest = currentBoard[Board.NORTHWEST].getTheCurrentState();
		char north = currentBoard[Board.NORTH].getTheCurrentState();
		char northeast = currentBoard[Board.NORTHEAST].getTheCurrentState();
		char west = currentBoard[Board.WEST].getTheCurrentState();
		char center = currentBoard[Board.CENTER].getTheCurrentState();
		char east = currentBoard[Board.EAST].getTheCurrentState();
		char southwest = currentBoard[Board.SOUTHWEST].getTheCurrentState();
		char south = currentBoard[Board.SOUTH].getTheCurrentState();
		char southeast = currentBoard[Board.SOUTHEAST].getTheCurrentState();
		
		//Check for vertical win
		if (northwest == west && west == southwest && northwest != Space.EMPTY) {
			return northwest;
		} else if (north == center && center == south && north != Space.EMPTY) {
			return north;
		} else if (northeast == east && east == southeast && northeast != Space.EMPTY) {
			return northeast;
				
		//Check for horizontal win
		} else if (northwest == north && north == northeast && northwest != Space.EMPTY) {
			return northwest;
		} else if (west == center && center == east && west != Space.EMPTY) {
			return west;
		} else if (southwest == south && south == southeast && southwest != Space.EMPTY) {
			return southwest;
			
		//Check for diagonal win
		} else if (northwest == center && center == southeast && northwest != Space.EMPTY) {
			return northwest;
		} else if (southwest == center && center == northeast && southwest != Space.EMPTY) {
			return southwest;
		}
		return Space.EMPTY;
	}
	
	private boolean checkForTie() {
		return game.getBoard().getEmptySpaces().length == 0;
	}
	
	private void writeLossToFile() throws IOException {
		GameLogFile gameLogFile = GameLogFile.getInstance();
		gameLogFile.add(game.getGameLog());
		gameLogFile.writeFile();
	}

	/**
	 * Specifies who won the last game.
	 * @return <code>X</code> or <code>O</code>, depending on who won the previous game, or the space character (<code>' '</code>) if the last game ended in a tie.
	 */
	public char getLastWinner() {
		return lastWinner;
	}
}
