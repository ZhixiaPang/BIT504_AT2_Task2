/*These import statements are bringing in the necessary classes and components from the AWT and Swing libraries,
 which are essential for building graphical user interfaces in Java.
 These libraries allow you to create windows, buttons, labels, text fields, and other visual components and
 also provide mechanisms for handling user interactions with those components.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/*Declares a new class named GameMain which extends (inherits from) the JPanel class and
implements the MouseListener interface. By extending JPanel, the GameMain class inherits the properties
and behavior of a panel in the Swing framework.Implementing MouseListener means that this class
will be able to respond to mouse-related events.
 */
public class GameMain extends JPanel implements MouseListener{
	// Defines a constant integer named ROWS with a value of 3
	public static final int ROWS = 3;
	//Defines constant integer named COLS with a value of 3
	public static final int COLS = 3;
	// defines a constant string named TITLE with the value "Tic Tac Toe"
	public static final String TITLE = "Tic Tac Toe";

	//The constant defines each cell in the tic-tac-toe grid is a square with sides of length 100 pixels.
	public static final int CELL_SIZE = 100;


	// Constant: Canvas width based on column count and cell size
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	//// Constant: Canvas height based on row count and cell size
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	//Padding around symbols within each cell
	public static final int CELL_PADDING = CELL_SIZE / 6;
	// Size of the symbols drawn in each cell
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	// Width of the stroke used to draw the symbols
	public static final int SYMBOL_STROKE_WIDTH = 8;
	
	/*declare game object variables*/
	//  variable holds information about the game board layout
	private Board board;
	private enum GameState { PLAYING, DRAW, X_WON, O_WON }
	private GameState currentState;
	// variable keeps track of whose turn it is
	private Player currentPlayer;
	// for displaying game status message
	private JLabel statusBar;


	/** Constructor to set up the UI and game components on the panel */
	public GameMain() {
		super(); // Call the constructor of the superclass (JPanel)
		// Initialize the game state and current player
		currentState = GameState.PLAYING;
		currentPlayer = Player.X; // Assuming having a Player enum with X and O

		// Other initialization code for UI components
		statusBar = new JLabel("Current Status: " + currentState);
		// Add the panel to the frame and set it up
		JFrame frame = new JFrame("Game Title");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this); // Add the GameMain panel
		frame.pack();
		frame.setVisible(true);

		// Add mouse listener to this panel
		addMouseListener(this);

		// Set up the status bar (JLabel) to display status message
		statusBar = new JLabel("         ");       
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));       
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));       
		statusBar.setOpaque(true);       
		statusBar.setBackground(Color.LIGHT_GRAY);  
		
		//layout of the panel is in border layout
		setLayout(new BorderLayout());       
		add(statusBar, BorderLayout.SOUTH);
		// account for statusBar height in overall height
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

		// Initialize the game board
		board = new Board();
		board.initializeBoard();

	}
	
	public static void main(String[] args) {
		// Run GUI code in Event Dispatch thread for thread safety.
		SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
				//create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);

				//Create the new GameMain panel and add it to the frame
				 GameMain gameMainPanel = new GameMain();
				 frame.add(gameMainPanel);

				 //Set the default close operation of the fame to EXIT_ON_CLOSE
				 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 frame.pack();
				 frame.setLocationRelativeTo(null);
				 frame.setVisible(true);
	         }
		 });
	}
	/** Custom painting codes on this JPanel */
	public void paintComponent(Graphics g) {
		//fill background and set colour to white
		super.paintComponent(g);
		setBackground(Color.WHITE);
		//ask the game board to paint itself
		board.paint(g);
		
		//set status bar message
		if (currentState == GameState.Playing) {          
			statusBar.setForeground(Color.BLACK);          
			if (currentPlayer == Player.Cross) {   
				statusBar.setText("'X' Turn");
				
			} else {

				statusBar.setText("'O' Turn");
				
			}       
			} else if (currentState == GameState.Draw) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("It's a Draw! Click to play again.");       
			} else if (currentState == GameState.Cross_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'X' Won! Click to play again.");       
			} else if (currentState == GameState.Nought_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'O' Won! Click to play again.");       
			}
		}
		
	
	  /** Initialise the game-board contents and the current status of GameState and Player) */
		public void initGame() {
			for (int row = 0; row < ROWS; ++row) {          
				for (int col = 0; col < COLS; ++col) {  
					// all cells empty
					board.cells[row][col].content = Player.Empty;           
				}
			}
			 currentState = GameState.Playing;
			 currentPlayer = Player.Cross;
		}
		
		
		/**After each turn check to see if the current player hasWon by putting their symbol in that position, 
		 * If they have the GameState is set to won for that player
		 * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING
		 *   
		 */
		public void updateGame(Player thePlayer, int row, int col) {
			//check for win after play
			if(board.hasWon(thePlayer, row, col)) {

				if (thePlayer == Player.Cross) {
					currentState = GameState.X_WON;
				} else {
					currentState = GameState.O_WON;
				}



			} else 
				if (board.isDraw ()) {

					currentState = GameState.DRAW;


				}
			//otherwise no change to current state of playing
		}
		
				
	
		/** Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
		 *  UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
		 *  If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears*/
	
	public void mouseClicked(MouseEvent e) {  
	    // get the coordinates of where the click event happened            
		int mouseX = e.getX();             
		int mouseY = e.getY();             
		// Get the row and column clicked             
		int rowSelected = mouseY / CELL_SIZE;             
		int colSelected = mouseX / CELL_SIZE;               			
		if (currentState == GameState.Playing) {                
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
				// move  
				board.cells[rowSelected][colSelected].content = currentPlayer; 
				// update currentState                  
				updateGame(currentPlayer, rowSelected, colSelected); 
				// Switch player
				if (currentPlayer == Player.Cross) {
					currentPlayer =  Player.Nought;
				}
				else {
					currentPlayer = Player.Cross;
				}
			}             
		} else {        
			// game over and restart              
			initGame();            
		}

		repaint();


	}


		
	
	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated,event not used
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated, event not used
		
	}

}
