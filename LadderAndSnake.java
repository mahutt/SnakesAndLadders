import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class LadderAndSnake {
	
	public static final int BOARD_SIZE = 10;
	private Tile[][] board;
	private int playerCount; // needed if we keep players in an array now?
	private String[] players;
	
	public LadderAndSnake(int playerCount) {
		this.createGameBoard();
		this.putSnakes();
		this.putLadders();
		this.playerCount = playerCount;
		players = new String[playerCount];
	}

	public void createGameBoard() {
		this.board = new Tile[BOARD_SIZE][BOARD_SIZE]; //use getter in this.board?
		
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = new Tile(i, j);
			}
		}
		
	}

	public void putSnakes() {
		getTile(16).setSnake(getTile(6));
		getTile(48).setSnake(getTile(30));
		getTile(64).setSnake(getTile(60));
		getTile(79).setSnake(getTile(19));
		getTile(93).setSnake(getTile(68));
		getTile(95).setSnake(getTile(24));
		getTile(97).setSnake(getTile(76));
		getTile(98).setSnake(getTile(78));
	}
	
	
	
	public void putLadders() {
		getTile(1).setLadder(getTile(38));
		getTile(4).setLadder(getTile(14));
		getTile(9).setLadder(getTile(31));
		getTile(21).setLadder(getTile(42));
		getTile(28).setLadder(getTile(84));
		getTile(36).setLadder(getTile(44));
		getTile(51).setLadder(getTile(67));
		getTile(71).setLadder(getTile(91));
		getTile(80).setLadder(getTile(100));
		
	}
	
	
	
	
	
	// TEMPORARY console print board (before using UI) :
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
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[rowNumber][i].getType() != null)
				System.out.print(board[rowNumber][i].getType() + "\t|");
			else if (board[rowNumber][i].getPlayer() == null)
				System.out.print(board[rowNumber][i].getPosition() + "\t|");
			else
				System.out.print(board[rowNumber][i].getPlayer() + "\t|");
		}
	}
	
	private void printRowBackwards(int rowNumber) {
		for (int i = BOARD_SIZE - 1; i >= 0; i--) {
			if (board[rowNumber][i].getType() != null)
				System.out.print(board[rowNumber][i].getType() + "\t|");
			else if (board[rowNumber][i].getPlayer() == null)
				System.out.print(board[rowNumber][i].getPosition() + "\t|");
			else
				System.out.print(board[rowNumber][i].getPlayer() + "\t|");
		}
	}
	
	
	
	// PRIVACY ISSUES WITH BOARD SETTERS/GETTERS?
	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}

	// these should be okay
	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
	
	
	// 3 get tile methods based on whatever works best
	public Tile getTile(int position) {
		int rowIndex; int colIndex;
		
//		if (position%BOARD_SIZE == 0) {
//			rowIndex = position/BOARD_SIZE;
//			colIndex = (position-1)%BOARD_SIZE;
//		}
//		else {
//			rowIndex = (position-1)/BOARD_SIZE;
//			colIndex = (position-1)%BOARD_SIZE;
//		}
		
		rowIndex = (position-1)/BOARD_SIZE;
		colIndex = (position-1)%BOARD_SIZE;
		
		return board[rowIndex][colIndex];
	}
	
	public Tile getTile(int row, int col) {
		return board[row][col];
	}
	
	public Tile getTile(String player) {
		for (int i = 1; i <= BOARD_SIZE*BOARD_SIZE; i++) {
			if (getTile(i).getPlayer() != null && getTile(i).getPlayer().equals(player))
				return getTile(i);
		}
		return null;
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
	
	public int moveBy(String player, int roll) {
		
		Tile oldTile = getTile(player);
		if (oldTile != null) {
			oldTile.setPlayer(null);
			roll += oldTile.getPosition();
		}
		
		if (roll > 100)
			roll = 100 - (roll - 100);
		
		getTile(roll).setPlayer(player);
		
		Tile newTile = getTile(player);
		
		if (newTile.getType() == "SH" || newTile.getType() == "LB") {
			newTile.setPlayer(null);
			newTile.getSendTo().setPlayer(player);
			return newTile.getSendTo().getPosition();
		}
		
		
		
		
		return newTile.getPosition();

	}
	
	public boolean checkWinner() {
		return (getTile(100).getPlayer() != null);
	}
	
	public static void main(String[] args) {
		LadderAndSnake game = new LadderAndSnake(2);
		
		Scanner kb = new Scanner(System.in);
		
		for (int i = 0; i < game.players.length; i++) {
			System.out.print("Enter the name of player " + (i+1) + ": ");
			game.players[i] = kb.next(); 
			if (game.players[i].length() > 6)
				game.players[i] = game.players[i].substring(0, 6);
			game.players[i] = game.players[i].toUpperCase(); 
		}
		
		System.out.println();
		System.out.println("Now deciding which player will go first:");
		
		int currentPlayer = game.determineOrder();
		int playerCount = game.getPlayerCount();
		int roll;
		String wait;
		
		boolean gameOver = false;
		
		while (!gameOver) {
			
			for (int i = 0; i < playerCount; i++) {
				
				roll = game.flipDice();
				System.out.print(game.players[currentPlayer%(playerCount)] + " got a dice value of " + roll + " - ");
				System.out.println("now in square " + game.moveBy(game.players[currentPlayer%(playerCount)], roll));
				
				
				
				currentPlayer++;
				
				wait = kb.nextLine();
				
				
			}
			
			gameOver = game.checkWinner();
			System.out.println();

			
		}
		
		System.out.println(game.getTile(100).getPlayer() + " is the winner!");
		
		
		//game.printBoard();
		
		
		
		
		
	}
	
	
	
	
	
	

}
