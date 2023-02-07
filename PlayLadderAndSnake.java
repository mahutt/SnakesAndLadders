// -----------------------------------------------------
// Assignment 1
// Written by: Thomas Mahut
// -----------------------------------------------------

import java.util.Scanner;

/**
 * Thomas Mahut (40249811)
 * COMP249
 * Assignment 1
 * February 3rd, 2023
 */

/**
 * Driver program designed to launch and play an instance of {@code LadderAndSnake},
 * which is a Snakes And Ladders style game where {@code Player} objects move about
 * a tiled board with predetermined snake and ladder locations. The user(s) can
 * represent themselves through {@code Player} objects, which make dice rolls and
 * move about the board on each user's behalf.
 * 
 * @author Thomas Mahut
 *
 */
public class PlayLadderAndSnake {
	
	/**
	 * {@code main} method that welcomes the player(s), receives an {@code int}
	 * number of players and {@code String} name for each, before finally running the 
	 * {@code LadderAndSnake} game. 
	 * @param args the code describing game progression
	 */
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		// JUNK STRING MEANT TO CATCH CARRIAGE RETURN FOLLOWING THE USER'S
		// INPUT (WHEN SPECIFYING THE NUMBER OF PLAYERS) SO THAT THE FIRST INPUT
		// TAKEN BY SETPLAYERNAMES() ISN'T EMPTY.
		String junk;
		
		// WELCOME/EXPLANATORY MESSAGES
		System.out.println("Welcome to Thomas Mahut's Snakes & Ladders!");
		System.out.print("Please enter the number of players: ");
		
			
		int playerCount = 0;
		
		if (!keyboard.hasNextInt()) {
			System.out.println("Error - expected integer value - program terminated");
			System.exit(0);
		}
		
		do {
			playerCount = keyboard.nextInt();
			if (playerCount < 2) {
				System.out.print("Error: Cannot execute the game with less than 2 players! try again: ");
			}
			else if (playerCount > 10) {
				System.out.print("Error: Cannot execute the game with more than 10 players! try again: ");
			}
		} while (playerCount < 2 || playerCount > 10);
		
		LadderAndSnake game = new LadderAndSnake(playerCount);
		junk = keyboard.nextLine();
		game.setPlayerNames(keyboard);
		System.out.println();
		
		game.play(game.determineOrder(), keyboard);
		System.out.println("Thanks for playing LadderAndSnake!");
		keyboard.close();
	}

}
