package game;

import java.util.Scanner;

import boardObjects.Space;
import logic.AI;
import logicObjects.GameController;

/**
 * This is the driver class. It interacts with the player and the computer to play a game of Tic-Tac-Toe.
 * @author JeremyMa
 *
 */
public class TicTacToe {
	
	private static final Scanner s = new Scanner(System.in);
	
	private static final String[] spaceNames = {"Northwest", "North", "Northeast", "West", "Center", "East", "Southwest", "South", "Southeast"};
	
	public static void main(String args[]) {
		GameController gameController = GameController.getInstance();
		
		System.out.println("Tic-Tac-Toe");
		while(true) {
			System.out.println();
			System.out.println("Main menu");
			System.out.println("1) Play game");
			System.out.println("2) Change turn order");
			System.out.println("3) About");
			System.out.println("4) Exit");
			
			String response = getResponse();
			
			if(response.equals("1")) {
				gameController.startGame();
				AI ai = AI.getInstance();
				char currentPlayer = ' ';
				while(gameController.isGameActive()) {
					currentPlayer = gameController.getCurrentPlayer();
					System.out.println();
					System.out.println(gameController.getGame().getBoard().toString());
					if(currentPlayer == Space.O) {
						System.out.println();
						System.out.println("Computer's turn.");
						ai.pickSpace();
						int previousMove = ai.getPreviousMove();
						System.out.println("Computer chose: "+previousMove+") "+spaceNames[previousMove]);
					} else {
						System.out.println();
						System.out.println("Your turn.");
						while(true) {
							System.out.println();
							System.out.println("Remaining spaces:");
							int[] emptySpaces = gameController.getGame().getBoard().getEmptySpaces();
							for(int emptySpace : emptySpaces) {
								System.out.println(Integer.toString(emptySpace)+") "+spaceNames[emptySpace]);
							}
							
							try{
								int playerChoice = Integer.parseInt(getResponse());
								if (!gameController.play(playerChoice)) {
									System.out.println("That was an invalid selection.\nPlease try again.");
								} else {
									break;
								}
							} catch (NumberFormatException e) {
								System.out.println("That was an invalid selection.\nPlease try again.");
							}
						}
					}
				}
				
				System.out.println();
				System.out.print("You ");
				switch (gameController.getLastWinner()) {
				case Space.X:
					System.out.println("won!");
					break;
				case Space.O:
					System.out.println("lost...");
					break;
				case Space.EMPTY:
					System.out.println("tied.");
					break;
				default:
					System.out.println("didn't win, tie, or lose? This is some kind of error.");
					break;
				}
			} else if (response.equals("2")) {
				while(true) {
					System.out.println();
					System.out.println("Change Turn Order");
					System.out.println("1) You");
					System.out.println("2) The computer");
					
					String turnResponse = getResponse();
									
					if(turnResponse.equals("1")) {
						gameController.setXGoesFirst();
						System.out.println("Turn order saved.");
						break;
					} else if (turnResponse.equals("2")) {
						gameController.setOGoesFirst();
						System.out.println("Turn order saved.");
						break;
					} else {
						System.out.println("That was an invalid selection.\nPlease try again.");
					}
				}
			} else if (response.equals("3")) {
				System.out.println();
				System.out.println("Tic-Tac-Toe");
				System.out.println("Developed by Jeremy Ma");
			} else if (response.equals("4")) {
				break;
			} else {
				System.out.println("That was an invalid selection.\nPlease try again.");
			}
			
		}
		s.close();
	}
	
	private static String getResponse() {
		System.out.println();
		System.out.println("Please enter one of the options above and press ENTER.");
		
		return s.nextLine();
	}
}
