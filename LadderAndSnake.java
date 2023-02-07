// -----------------------------------------------------
// Assignment 1
// Written by: Thomas Mahut
// -----------------------------------------------------

/**
 * Thomas Mahut (40249811)
 * COMP249
 * Assignment 1
 * February 3rd, 2023
 */

import java.util.Scanner;
import java.util.Random;

/**
 * A Snakes And Ladders style game, consisting of a tiled board with specified
 * snake and ladder locations, playable by {@code Player} objects.
 * 
 * @author Thomas Mahut
 */
public class LadderAndSnake {
	
	/**
	 * Constant representing the width and height (in number of tiles)
	 * of the square board.
	 */
	public static final int BOARD_SIZE = 10;
	
	/**
	 * Constant representing the greatest position that a {@code Player} object
	 * could reach, based on the width and height of the board. The first 
	 * {@code Player} object with a position value of {@code MAX_POSITION}
	 * is declared the winner.
	 */
	public static final int MAX_POSITION = BOARD_SIZE*BOARD_SIZE;
	
	/**
	 * Constant representing the index position of a snake's head within a nested
	 * array in {@code snakes}. 
	 */
	private static final int SNAKE_HEAD = 0;
	
	/**
	 * Constant representing the index position of a snake's tail within a nested
	 * array in {@code snakes}. 
	 */
	private static final int SNAKE_TAIL = 1;
	
	/**
	 * Constant representing the index position of a ladder's bottom within a nested
	 * array in {@code ladders}. 
	 */
	private static final int LADDER_BOTTOM = 0;
	
	/**
	 * Constant representing the index position of a ladder's top within a nested
	 * array in {@code ladders}. 
	 */
	private static final int LADDER_TOP = 1;
	
	/**
	 * Two-dimensional array that holds the positions of every snake's head and
	 * tail. The positions of snake heads are stored at index 0 of every nested
	 * array, and the tails are stored at index 1 of every nested array.
	 */
	private static int[][] snakes = {
			{16, 6},
			{48, 30},
			{64, 60},
			{79, 19},
			{93, 68},
			{95, 24},
			{97, 76},
			{98, 78},
	};

	/**
	 * Two-dimensional array that holds the positions of every ladder's bottom and
	 * top. The positions of ladder bottoms are stored at index 0 of every nested
	 * array, and the tops are stored at index 1 of every nested array.
	 */
	private static int[][] ladders = {
			{1 , 38}, 
			{4 , 14},  
			{9 , 31},  
			{21, 42}, 
			{28, 84}, 
			{36, 44}, 
			{51, 67}, 
			{71, 91}, 
			{80, 100},
	};	
	
	// An instance of Random, used by all instances of LadderAndSnake through
	// the flipDice() method.
	private static Random dice = new Random();
	
	/**
	 * Two-dimensional array that represents a tiled game board. The nested arrays hold
	 * integer values from 1 to {@code MAX_VALUE}, each representing the positional-value
	 * of a tile. Tiles are specified, and ordered, by their index position in the array. 
	 */
	private int[][] board;
	
	/**
	 * Array of all active {@code Player} objects that are currently playing
	 * this instance of {@code LadderAndSnake}.
	 */
	private Player[] players;
	
	/**
	 * Constructs a new {@code LadderAndSnake} game object, whose player count is
	 * specified by the argument of the same name.
	 * @param playerCount the number of players that will play the game
	 */
	public LadderAndSnake(int playerCount) {
		initializeBoard();
		initializePlayers(playerCount);
	}
	
	/**
	 * Constructs a new {@code LadderAndSnake} game object, whose player count is
	 * set to 2 by default, where 2 is the minimum number of players required to play.
	 */
	public LadderAndSnake() {
		this(2);
	}
	
	/**
	 * Constructs a clone of a specific {@code LadderAndSnake} game object.
	 * @param ladderAndSnake the {@code LadderAndSnake} game object to copy.
	 */
	public LadderAndSnake(LadderAndSnake ladderAndSnake) {
		this(ladderAndSnake.getPlayerCount());
		for (int i = 0; i < players.length; i++) {
			this.players[i] = new Player(ladderAndSnake.players[i]);
		}
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				this.board[row][col] = ladderAndSnake.board[row][col];
			}
		}
	}
	
	// Initializes the board attribute for the LadderAndSnake
    // constructor. Tiles are given their corresponding 
	// position values.
	private void initializeBoard() {
		board = new int[BOARD_SIZE][BOARD_SIZE];
		int tileCounter = 1;
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				board[row][col] = tileCounter;
				tileCounter++;
			}
		}
	}
	
	// Initializes the players attribute for the LadderAndSnake
    // constructor. Player objects are initialized but aren't 
	// assigned a name - only a position value of 0. 
	private void initializePlayers(int playerCount) {
		players = new Player[playerCount];
		for (int i = 0; i < playerCount; i++) {
			players[i] = new Player();
		}
	}
	
	/**
	 * Returns a copy of this {@code LadderAndSnake} object's {@code players} attribute.
	 * @return a copy of the {@code players} array of type {@code Player}
	 */
	public Player[] getPlayers() {
		Player[] playersCopy = new Player[players.length];
		for (int i = 0; i < players.length; i++) {
			playersCopy[i] = new Player(players[i]);
		}
		return playersCopy;
	}
	
	/**
	 * Sets the {@code players} attribute to a different array of {@code Player} objects.
	 * @param newPlayers the array of new {@code Player} objects to replace {@code players} with
	 */
	public void setPlayers(Player[] newPlayers) {
		this.players = new Player[newPlayers.length];
		for (int i = 0; i < this.players.length; i++) {
			this.players[i] = new Player(newPlayers[i]);
		}
	}
	
	/**
	 * Replaces the {@code Player} object at a specified index of {@code players} with
	 * another specified {@code Player} object.
	 * @param index the index of the {@code Player} object to replace
	 * @param player the {@code Player} object to replace the previous {@code Player} with
	 */
	public void setPlayer(int index, Player player) {
		this.players[index] = new Player(player);
	}
	
	/**
	 * Returns a copy of this {@code LadderAndSnake} object's {@code board} attribute.
	 * @return a copy of the {@code board} array of type {@code int}
	 */
	public int[][] getBoard() {
		int[][] boardCopy = new int[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				boardCopy[row][col] = this.board[row][col];
			}
		}
		return boardCopy;
	}
	
	/**
	 * Sets the {@code board} attribute to a different array of {@code int} values.
	 * @param newBoard the array of new {@code int} values to replace {@code board} with
	 */
	public void setBoard(int[][] newBoard) {
		this.board = new int[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				this.board[row][col] = newBoard[row][col];
			}
		}
	}
	
	/**
	 * Returns a random number between 1 and 6, effectively simulating a
	 * standard dice roll.
	 * @return a random number between 1 and 6
	 */
	public static int flipDice() {
		return dice.nextInt(1, 7);
	}

	/**
	 * Checks whether or not a given {@code Player} has a the same
	 * positional value as the head of a snake. If it does, the method 
	 * will set the {@code Player} object's position to that of
	 * the corresponding snake's tail. The method returns true if 
	 * repositioning occurs, and false otherwise. 
	 * @param player the {@code Player} to test
	 * @return {@code true} if the specified {@code Player} object
	 * shares its location with a snake's head; {@code false} otherwise.
	 */
	public boolean snakeScan(Player player) { 
		for (int i = 0; i < snakes.length; i++) {
			if (player.getPosition() == snakes[i][SNAKE_HEAD]) {
				player.setPosition(snakes[i][SNAKE_TAIL]);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether or not a given {@code Player} has a the same
	 * positional value as the bottom of a ladder. If it does, the method 
	 * will set the {@code Player} object's position to that of
	 * the corresponding ladder's top. The method returns true if 
	 * repositioning occurs, and false otherwise. 
	 * @param player the {@code Player} to test
	 * @return {@code true} if the specified {@code Player} object
	 * shares its location with a latter's bottom; {@code false} otherwise.
	 */
	public boolean ladderScan(Player player) { 
		for (int i = 0; i < ladders.length; i++) {
			if (player.getPosition() == ladders[i][LADDER_BOTTOM]) {
				player.setPosition(ladders[i][LADDER_TOP]);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether or not a given {@code Player} has a the same
	 * positional value as any other {@code Player} object. If such another 
	 * {@code Player} is found, that {@code Player} object's position is set 
	 * to 0 and the method returns the {@code Player} in question.
	 * The method returns {@code null} otherwise.
	 * @param player the {@code Player} to test
	 * @return {@code Player} whose position has been reset in the case of such
	 * a reset; {@code null} otherwise.
	 */
	public Player conflictScan(Player player) {
		for (int i = 0; i < players.length; i++) {
			if (player != players[i] && player.getPosition() == players[i].getPosition()) {
				players[i].setPosition(0);
				return players[i];
			}
		}
		return null;
	}
	
	/**
	 * Prints a String representation of the board to the console, row by row.
	 * Every other row is printed backwards (positions increase right to left) so
	 * that adjacent positional values also appear adjacent in their string
	 * representation.  
	 */
	public void printBoard() {
		printRowLine();
		for (int row = BOARD_SIZE - 1; row >= 0; row--) {
			if (row%2 == 0) 
				printRowForwards(row);
			else
				printRowBackwards(row);
			System.out.println();
			printRowLine();
		}
	}
	
	// Prints a row-dividing line to the console, so that rows are visually 
	// divided from one another when the board is printed.
	private void printRowLine() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			System.out.print("________");
		}
		System.out.println("_");
	}
	
	// Prints a given row to the console, tile by tile 
	// in ascending order (left to right).
	private void printRowForwards(int row) {
		for (int col = 0; col < BOARD_SIZE; col++) {
			printTile(board[row][col]);
		}
	}
	
	// Prints a given row to the console, tile by tile
	// in descending order (right to left).
	private void printRowBackwards(int row) {
		for (int col = BOARD_SIZE - 1; col >= 0; col--) {
			printTile(board[row][col]);
		}
	}
	
	// Prints a tile of a given position to the console. The tile may 
	// visually hold a snake icon, a ladder icon, a Player object's name
	// or simply the positional value of that tile.
	private void printTile(int position) {
		if (isSnake(position))
			System.out.print(position + "(:>-"  + "\t|");
		else if (isLadder(position))
			System.out.print(position + "|=|"  + "\t|");
		else if (playerOn(position) != null)
			System.out.print(playerOn(position) + "\t|");
		else
			System.out.print(position + "\t|");
	}
	
	// Checks whether the given positional value is occupied by a snake head,
	// so that the printTile() method knows whether to print a snake icon 
	// or not.
	private boolean isSnake(int position) {
		for (int i = 0; i < snakes.length; i++) {
			if (position == snakes[i][SNAKE_HEAD])
				return true;
		}
		return false;
	}
	
	// Checks whether the given positional value is occupied by a latter bottom,
	// so that the printTile() method knows whether to print a ladder icon 
	// or not.
	private boolean isLadder(int position) {
		for (int i = 0; i < ladders.length; i++) {
			if (position == ladders[i][LADDER_BOTTOM])
				return true;
		}
		return false;
	}
	
	/**
	 * Returns the {@code Player} object that occupies the specified position.
	 * If not such {@code Player} exists, the method returns null.
	 * @param position the position to test for {@code Player} objects
	 * @return {@code Player} that occupies the position; {@code null} if none exist.
	 */
	public Player playerOn(int position) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getPosition() == position)
				return players[i];
		}
		return null;
	}

	/**
	 * Returns the number of players active in this instance of 
	 * {@code LadderAndSnake}, based on the size of the {@code players} attribute.
	 * @return {@code int} number of players active in this instance.
	 */
	public int getPlayerCount() {
		return players.length;
	}
			
	/**
	 * Assigns a dice flip value to each active {@code Player} object. So long as two
	 * or more players are tied for having the greatest roll value, new dice flip
	 * values will continuously be assigned to each {@code Player} until there is a
	 * clear winner. The index position of that {@code Player} is returned.
	 * @return the index position of the first {@code Player} object to move.
	 */
	public int determineOrder() {
		System.out.println("Now deciding which player will go first:");
		int counter = 1;
		int[] rolls = new int[getPlayerCount()];
		int starter;
		
		for (int i = 0; i < getPlayerCount(); i++) {
				rolls[i] = flipDice();
				System.out.println(players[i] + " got a dice value of " + rolls[i]);
		}
		starter = getStartingPlayer(rolls);
		
		while (starter == -1) {
			System.out.println("A tie! Let's try that again...\n");
			for (int i = 0; i < getPlayerCount(); i++) {
				rolls[i] = flipDice();
				System.out.println(players[i] + " got a dice value of " + rolls[i]);
			}
			counter++;
			starter = getStartingPlayer(rolls);
		}

		System.out.print(players[starter] + " goes first! ");
		System.out.println("(It took " + counter + " rolls before a decision could be made)\n");
		return starter;
	}
	
	// Checks for a winning dice roll among a specified array of dice roll values.
	// Returns the index position of the winning roll, or -1 in the case of a tie.
	private int getStartingPlayer(int[] rolls) {
		int starter = 0;
		for (int i = 1; i < rolls.length; i++) {
			if (rolls[i] > rolls[starter]) {
				starter = 1;
			}
			else if (rolls[i] == rolls[starter]) {
				return -1;
			}
		}
		return starter;
	}
	
	/**
	 * Checks whether there is a {@code Player} object with a positional value equal to the winning
	 * position. If so, that {@code Player} object is returned. Otherwise, {@code null} is returned.
	 * @return {@code Player} with a winning positional value if one exists; {@code null} otherwise.
	 */
	public Player getWinner() {
		return playerOn(MAX_POSITION);
	}
	
	/**
	 * Advances the position of a specified {@code Player} by a specified dice roll value.
	 * If the new position is beyond the board, the {@code Player} will move backwards from
	 * the winning position by whatever number of spaces it was originally surpassed by.
	 * @param player the {@code Player} whose position is to be altered
	 * @param rollValue the value by which to to move the specified {@code Player}
	 * @return the specified {@code Player} object's new position on the board
	 */
	public int moveBy(Player player, int rollValue) {
		player.setPosition(player.getPosition() + rollValue);
		if (player.getPosition() > MAX_POSITION) {
			player.setPosition(MAX_POSITION - (player.getPosition() - MAX_POSITION));
		}
		return player.getPosition();
	}
	
	/**
	 * Assigns every {@code Player} object in {@code players} a name. Requires a
	 * {@code Scanner} object so that names can be passed from the user through the
	 * console. The {@code Scanner} object may be passed from the driver to achieve this.
	 * @param keyboard the {@code Scanner} used to accept names through the console
	 */
	public void setPlayerNames(Scanner keyboard) {
		for (int i = 0; i < getPlayerCount(); i++) {
			System.out.print("Enter the name of player " + (i+1) + ": ");
			players[i].setName(keyboard.nextLine());
		}
	}
	
	/**
	 * Initiates the {@code LadderAndSnake} game logic, and the progression of 
	 * previously named {@code Player} objects in the {@code players} array are tracked
	 * and printed to the console. If {@code Player} objects have not yet been named, this
	 * method will instead abort the program.
	 * @param startingPlayerIndex the {@code int} index of the first {@code Player} object to make a move,
	 * meant to be decided using the {@code determineOrder()} method.
	 * @param keyboard the {@code Scanner} instance to be used for grabbing user input
	 */
	public void play(int startingPlayerIndex, Scanner keyboard) {
		
		for (int i = 0; i < getPlayerCount(); i++) {
			if (players[i].getName() == null) {
				System.out.println("Error: players haven't been named yet");
				System.exit(0);
			}
		}
		
		boolean gameOver = false;
		int turnCounter = 0;
		int currentRoll;
		int currentPlayerIndex = startingPlayerIndex;
		int currentPlayerPosition;
		
		Player currentPlayer;
		Player kickedPlayer;
		String wait;
		
		System.out.println("Take turns pressing ENTER to roll!");
		
		while (!gameOver) {
			
			for (int i = 0; i < getPlayerCount(); i++) {
				
				turnCounter++;
				wait = keyboard.nextLine();
				currentPlayer = players[currentPlayerIndex%(getPlayerCount())];
				
				currentRoll = flipDice();
				System.out.print(currentPlayer + " got a dice value of " + currentRoll + " - ");
				
				currentPlayerPosition = moveBy(currentPlayer, currentRoll);
				
				if (snakeScan(currentPlayer)) {
					System.out.println("OH NO! A SNAKE caused " + currentPlayer + " to slip from " + currentPlayerPosition + " to " + currentPlayer.getPosition() + "!!!");
				}
				else if (ladderScan(currentPlayer)) {
					System.out.println("OH YEAH! A LADDER lifted " + currentPlayer + " from " + currentPlayerPosition + " to " + currentPlayer.getPosition() + "!!!");
				}
				else {
					System.out.println("now in square " + currentPlayerPosition);
				}
				
				kickedPlayer = conflictScan(currentPlayer);
				if (kickedPlayer != null) {
					System.out.println(kickedPlayer + " has been kicked off the board!");
				}
				kickedPlayer = null;
			
				currentPlayerIndex++;
				
				if (getWinner() != null) {
					break;
				}
			
			}
			printBoard();
			gameOver = (getWinner() != null);
			//System.out.println();
		}
		System.out.print(getWinner() + " is the winner! ");
		System.out.println("(after a total of " + turnCounter + " moves played)");
	}

}
