package logic;

import java.util.ArrayList;

import boardObjects.Board;
import logicObjects.GameController;
import logicObjects.GameLog;
import logicObjects.GameLogFile;

/**
 * This class contains the artificial intelligence that the computer player uses against the human player.
 * @author JeremyMa
 *
 */
public class AI {
	private static AI instance = new AI();
	
	private int previousMove = 9;
	
	private AI() {}
	
	/**
	 * Gets the current AI.
	 * @return The current AI.
	 */
	public static AI getInstance() {
		return instance;
	}

	/**
	 * The AI uses this to pick an appropriate space.
	 * @return <code>true</code> if picking a space succeeded, <code>false</code> otherwise.
	 */
	public boolean pickSpace() {
		int[] freeSpaces = GameController.getInstance().getGame().getBoard().getEmptySpaces();
		int avoidTrapMove = avoidTraps();
		if(freeSpaces.length == 0) {
			return false;
		} else if (freeSpaces.length == 1) {
			GameController.getInstance().play(freeSpaces[0]);
		} else if (avoidTrapMove != -1 && GameController.getInstance().getGame().getBoard().getBoard()[avoidTrapMove].isEmpty()) {
			GameController.getInstance().play(avoidTrapMove);
			previousMove = avoidTrapMove;
		} else {
			int space = 9;
			ArrayList<Integer> badMoves = new ArrayList<Integer>();
			ArrayList<GameLog> badLogs = checkLogs();
			if(badLogs.size() > 0) {
				badMoves = getBadMoves(badLogs);
			};
			do {
				space = (int)(Math.random() * 9);
			} while(!(freeSpacesContains(freeSpaces, space)) || badMoves.contains(space));
			GameController.getInstance().play(space);
			previousMove = space;
		}
		return true;
	}
	
	private ArrayList<Integer> getBadMoves(ArrayList<GameLog> badLogs) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(GameLog gl : badLogs) {
			result.add(getBadMove(gl));
		}
		return result;
	}

	private int getBadMove(GameLog logWithBadMove) {
		int target1 = logWithBadMove.getMove(1);
		int target2 = GameController.getInstance().getGame().getGameLog().getMove(1);
		if(target1 == Board.CENTER) {
			target1 = logWithBadMove.getMove(2);
			target2 = GameController.getInstance().getGame().getGameLog().getMove(2);
		}
		while (target1 != target2) {
			logWithBadMove = logWithBadMove.rotateClockwise();
			target1 = logWithBadMove.getMove(1);
			target2 = GameController.getInstance().getGame().getGameLog().getMove(1);
			if(target1 == Board.CENTER) {
				target1 = logWithBadMove.getMove(2);
				target2 = GameController.getInstance().getGame().getGameLog().getMove(2);
			}	
		}
		
		String logStr = logWithBadMove.toString();
		int badMove = Integer.parseInt(logStr.substring(logStr.length()-2, logStr.length()-1)); 
		return badMove;
	}

	private ArrayList<GameLog> checkLogs() {
		GameLogFile file = GameLogFile.getInstance();
		ArrayList<GameLog> listOfLogs = new ArrayList<GameLog>();
		for(int i = 1; i <= file.size(); i++) {
			GameLog log = file.getLog(i).rotateUntilTemplateMatch();
			String logStr = log.toString();
			String truncated = logStr.substring(0, logStr.length()-2);
			if (GameController.getInstance().getGame().getGameLog().rotateUntilTemplateMatch().toString().equals(truncated)) {
				listOfLogs.add(log);
			}
		}
		return listOfLogs;
	}

	private boolean freeSpacesContains(int[] freeSpaces, int chosenSpace) {
		for(int freeSpace : freeSpaces) {
			if(freeSpace == chosenSpace) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gives the previous move that was made.
	 * @return One of the <code>Board</code> constants that specify the location of the previous move on the board.
	 */
	public int getPreviousMove() {
		return previousMove;
	}
	
	private int avoidTraps() {
		int currentTurnNumber = 9-GameController.getInstance().getGame().getBoard().getEmptySpaces().length;
		int logIndex = currentTurnNumber+1;
		GameLogFile file = GameLogFile.getInstance();
		int numberOfLogs = file.size();
		String currentPattern;
		ArrayList<String> triedPatterns = new ArrayList<String>();
		for(int i = 1; i <= numberOfLogs; i++) {
			String currentLog = file.getLog(i).toString(); 
			if(currentLog.length() == logIndex + 6) {
				currentPattern = currentLog.substring(0, logIndex + 4);
				if(triedPatterns.contains(currentPattern)) {
					continue;
				}
				ArrayList<GameLog> gameLogArray = new ArrayList<GameLog>();
				gameLogArray.add(file.getLog(i));
				for(int j = i + 1; j <= numberOfLogs; j++) {
					if(file.getLog(j).toString().startsWith(currentPattern)) {
						gameLogArray.add(file.getLog(j));
					} else if (file.getLog(j).rotateClockwise().toString().startsWith(currentPattern)) {
						gameLogArray.add(file.getLog(j).rotateClockwise());
					} else if (file.getLog(j).rotate180().toString().startsWith(currentPattern)) {
						gameLogArray.add(file.getLog(j).rotate180());
					} else if (file.getLog(j).rotateCounterClockwise().toString().startsWith(currentPattern)) {
						gameLogArray.add(file.getLog(j).rotateCounterClockwise());
					}
				}
				
				if(gameLogArray.size() >= 4) {
					if(currentTurnNumber == 0) {
						return -1;
					} else if(currentTurnNumber == 1 && GameController.getInstance().getGame().getGameLog().getMove(1) == Board.CENTER) {
						return Integer.parseInt(gameLogArray.get(0).toString().substring(logIndex+1, logIndex+2));
					} else {
						if(Integer.parseInt(gameLogArray.get(0).toString().substring(logIndex-1, logIndex)) == GameController.getInstance().getGame().getGameLog().getMove(currentTurnNumber)) {
							return Integer.parseInt(gameLogArray.get(0).toString().substring(logIndex+1, logIndex+2));
						} else if (Integer.parseInt(gameLogArray.get(0).rotateClockwise().toString().substring(logIndex-1, logIndex)) == GameController.getInstance().getGame().getGameLog().getMove(currentTurnNumber)) {
							return Integer.parseInt(gameLogArray.get(0).rotateClockwise().toString().substring(logIndex+1, logIndex+2));
						} else if (Integer.parseInt(gameLogArray.get(0).rotate180().toString().substring(logIndex-1, logIndex)) == GameController.getInstance().getGame().getGameLog().getMove(currentTurnNumber)) {
							return Integer.parseInt(gameLogArray.get(0).rotate180().toString().substring(logIndex+1, logIndex+2));
						} else if (Integer.parseInt(gameLogArray.get(0).rotateCounterClockwise().toString().substring(logIndex-1, logIndex)) == GameController.getInstance().getGame().getGameLog().getMove(currentTurnNumber)) {
							return Integer.parseInt(gameLogArray.get(0).rotateCounterClockwise().toString().substring(logIndex+1, logIndex+2));
						}
					}
					
				}
				
				triedPatterns.add(currentPattern);
			}
		}
		return -1;
	}
}
