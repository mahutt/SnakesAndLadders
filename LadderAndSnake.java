import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

// ideas to add: 
//don't let multiple players have the same name
//what random number generator should i use?

public class LadderAndSnake {
	
	public static final int BOARD_SIZE = 10;
	public static final int MAX_POSITION = BOARD_SIZE*BOARD_SIZE;
	private int[][] board;
	private int playerCount; // needed if we keep players in an array now?
	private Player[] players;
	
	// attempt at setting snakes / ladders - better to initalize inside of a method? - cant use the "shorthand" if i do it that way!
	private static final int SNAKE_HEAD = 0;
	private static final int SNAKE_TAIL = 1;
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

	private static final int LADDER_BOTTOM = 0;
	private static final int LADDER_TOP = 1;
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
	
	
	
	
	
	
	public boolean foundSnake(Player player) { 
		for (int i = 0; i < snakes.length; i++) {
			if (player.getPosition() == snakes[i][SNAKE_HEAD]) {
				player.setPosition(snakes[i][SNAKE_TAIL]);
				return true;
			}
		}
		return false;
	}

	public boolean foundLadder(Player player) { 
		for (int i = 0; i < ladders.length; i++) {
			if (player.getPosition() == ladders[i][LADDER_BOTTOM]) {
				player.setPosition(ladders[i][LADDER_TOP]);
				return true;
			}
		}
		return false;
	}
	
	public Player foundConflict(Player player) {
		for (int i = 0; i < players.length; i++) {
			if (player != players[i] && player.getPosition() == players[i].getPosition()) {
				players[i].setPosition(0);
				return players[i];
			}
		}
		return null;
	}
	
	
	
	// THESE 2 AREN'T BEING USED FOR FOUNDSNAKE OR FOUNDLATTER SO MAYBE GET RID OF THEM IDK 
	private boolean isSnake(int position) {
		for (int i = 0; i < snakes.length; i++) {
			if (position == snakes[i][SNAKE_HEAD])
				return true;
		}
		return false;
	}
	
	private boolean isLadder(int position) {
		for (int i = 0; i < ladders.length; i++) {
			if (position == ladders[i][LADDER_BOTTOM])
				return true;
		}
		return false;
	}
	
	
	
	public LadderAndSnake(int playerCount) {
		this.createGameBoard();
//		this.putSnakes();
//		this.putLadders();
		this.playerCount = playerCount;
		players = new Player[playerCount];
	}

	public void createGameBoard() {
		this.board = new int[BOARD_SIZE][BOARD_SIZE]; // getBoard() doesn't work here, why?
		int tileCounter = 1;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = tileCounter;
				tileCounter++;
			}
		}
		
	}
	
	
	
	
	
	
	public void printBoard() {
		for (int row = BOARD_SIZE - 1; row >= 0; row--) {
			if (row%2 == 0)
				printRowForwards(row);
			else
				printRowBackwards(row);
			System.out.println();
		}
	}
	
	private void printRowForwards(int rowNumber) {
		for (int col = 0; col < BOARD_SIZE; col++) {
			printTile(board[rowNumber][col]);
		}
	}
	
	private void printRowBackwards(int rowNumber) {
		for (int col = BOARD_SIZE - 1; col >= 0; col--) {
			printTile(board[rowNumber][col]);
		}
	}
	
	private void printTile(int position) {
		if (isSnake(position))
			System.out.print("snake!"  + "\t|");
		else if (isLadder(position))
			System.out.print("ladder"  + "\t|");
		else if (playerOn(position) != null)
			System.out.print(playerOn(position) + "\t|");
		else
			System.out.print(position + "\t|");
	}
	
	
	
	
	
	
	
	public Player playerOn(int position) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getPosition() == position)
				return players[i];
		}
		return null;
	}
	
	
	
	// PRIVACY ISSUES WITH BOARD SETTERS/GETTERS?
	public int[][] getBoard() {
		return this.board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	// these should be okay
	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
	
	// should be static?
	public int flipDice() {
				
		// should i just use Random class? (im not doing concurrent programming)
		// if a create a Random instance instead (to generate random numbers) - should i only create one for the class and let it be static to all instances?
		return ThreadLocalRandom.current().nextInt(1, 7);
	}
			
		
	// not scalable at all for more than 2 players - 
	public int determineOrder() {	// make this static and put below main?
		int counter = 1;
		
		int roll1 = flipDice();
		int roll2 = flipDice();
		
		System.out.println(players[0] + " got a dice value of " + roll1);
		System.out.println(players[1] + " got a dice value of " + roll2);
		
		while (roll1 == roll2) {
			System.out.println("A tie! Let's try that again...\n");
			roll1 = flipDice();
			roll2 = flipDice();
			System.out.println(players[0] + " got a dice value of " + roll1);
			System.out.println(players[1] + " got a dice value of " + roll2);
			counter++;
		}
		
		System.out.print(players[(roll1 > roll2? 0 : 1)] + " goes first! ");
		System.out.println("(It took " + counter + " rolls before a decision could be made!)\n");

		return roll1 > roll2? 0 : 1;
	}	
	

	
	public Player getWinner() {
		return playerOn(board[BOARD_SIZE-1][BOARD_SIZE-1]); // REPLACE BY MAXPOSITION?
	}
	
	public int moveBy(Player player, int rollValue) {
		player.setPosition(player.getPosition() + rollValue);
		if (player.getPosition() > MAX_POSITION) {
			player.setPosition(MAX_POSITION - (player.getPosition() - MAX_POSITION)); // could be simplified further to 2*maxValue - position , but is that too unreadable?
		}
		return player.getPosition();
	}
	
	// MAIN ----------------------------------------------------------------
	public static void main(String[] args) {
		LadderAndSnake game = new LadderAndSnake(2);
		
		Scanner kb = new Scanner(System.in);
		
		for (int i = 0; i < game.players.length; i++) {
			System.out.print("Enter the name of player " + (i+1) + ": ");
			game.players[i] = new Player(kb.nextLine());
		}
		
		// TESTING ONLY
//		game.players[0] = new Player("jay");
//		game.players[1] = new Player("tom");
		
		
		System.out.println();
		System.out.println("Now deciding which player will go first:");
		
		int currentPlayerIndex = game.determineOrder();
		int currentPosition;
		Player currentPlayer;
		Player kickedPlayer;
		int playerCount = game.getPlayerCount();
		int roll;
		String wait;
		
		boolean gameOver = false;
		
		game.printBoard();
		
		while (!gameOver) {
			
			for (int i = 0; i < playerCount; i++) {
				
				currentPlayer = game.players[currentPlayerIndex%(playerCount)];
				
				roll = game.flipDice();
				System.out.print(currentPlayer + " got a dice value of " + roll + " - ");
				
				currentPosition = game.moveBy(currentPlayer, roll);
				
				if (game.foundSnake(currentPlayer)) {
					System.out.println("OH NO! A SNAKE caused " + currentPlayer + " to slip from " + currentPosition + " to " + currentPlayer.getPosition() + "!!!");
				}
					
				
				else if (game.foundLadder(currentPlayer)) {
					System.out.println("OH YEAH! A LADDER lifted " + currentPlayer + " from " + currentPosition + " to " + currentPlayer.getPosition() + "!!!");
				}
					
				
				else {
					System.out.println("now in square " + currentPosition);
				}
					
				
				kickedPlayer = game.foundConflict(currentPlayer);
				if (kickedPlayer != null) {
					System.out.println(kickedPlayer + " has been kicked off the board!");
				}
				kickedPlayer = null;
			
				currentPlayerIndex++;
			
			}
			//wait = kb.nextLine();
			gameOver = (game.getWinner() != null); 
			//game.printBoard();

			
			
			System.out.println();
			System.out.println();
			
		}
		
		System.out.println(game.getWinner() + " is the winner!");
		
		
		game.printBoard();
		kb.close();
		
	}
	
	
	
	
	
	

}
