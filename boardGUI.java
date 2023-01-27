import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// GIVEN AN OBJECT OF LADDERANDSNAKE, THIS CLASS SHOULD PROVIDE METHODS TO THE MAIN TO DISPLAY THE GAME
// OR SHOULD IT ACT AS THE MAIN?
public class boardGUI {
	
	private LadderAndSnake game; // PROBABLY SHOULD RENAME
	
	private JFrame frame;
	private JPanel board;
	private JPanel[][] guiBoard;
	
	private static int boardSize = LadderAndSnake.BOARD_SIZE;
	
	public boardGUI(LadderAndSnake game) {
		
		this.game = game;
		

		
		createGuiBoard();
		
//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < 10; j++) {
//				guiBoard[i][j] = new JPanel();
//				guiBoard[i][j].add(new JLabel(i+""+j));
//			}
//		}
		
		
		
		
		board = new JPanel();
		//board.setSize(676, 676); // has no effect when container has non-null layout manager
		board.setSize(80*boardSize, 80*boardSize);
		board.setBackground(Color.LIGHT_GRAY); // just for visualization purposes
		board.setLayout(new GridLayout(10, 10)); // replace with constant representing size?
		
		addTiles();
		
		
		
		frame = new JFrame(); // this.frame?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900); 
		frame.setLayout(null);
		frame.setVisible(true);
		

		
		frame.add(board);
		
		
		
		
	}
	
	public void addTiles() {
		for (int row = boardSize - 1; row >= 0; row--) {
			if (row%2 == 0)
				addRowForwards(row);
			else
				addRowBackwards(row);
		}
	}
	
	private void addRowForwards(int rowNumber) {
		for (int i = 0; i < boardSize; i++) {
			if (game.getBoard()[rowNumber][i].getPlayer() == null)
				board.add(new JLabel(game.getBoard()[rowNumber][i].getPosition() + "", SwingConstants.CENTER));
			else
				board.add(new JLabel(game.getBoard()[rowNumber][i].getPlayer() + "", SwingConstants.CENTER));
		}
	}
	
	private void addRowBackwards(int rowNumber) {
		for (int i = boardSize - 1; i >= 0 ; i--) {
			if (game.getBoard()[rowNumber][i].getPlayer() == null)
				board.add(new JLabel(game.getBoard()[rowNumber][i].getPosition()+"", SwingConstants.CENTER)); // I dont like how i have to +"" to turn it into a string, consider defining a new method or smtg
			else
				board.add(new JLabel(game.getBoard()[rowNumber][i].getPlayer()+"", SwingConstants.CENTER)); 
		}
		
	} 
	
	
	private void createGuiBoard() {
		guiBoard = new JPanel[boardSize][boardSize];
		
		
	}
	
	// this will have to be called everytime before we print - after a move is made - needs some parameter, ex what tile changed
	private void updateGuiBoard() {
		
	}
	
	public static void main(String[] args) {

		new boardGUI(new LadderAndSnake(2));
		
	}
	
}
