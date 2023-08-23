//import necessary classes from the AWT and SWing libraries.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* declares the GameMain class. It extends JPanel and implements the MouseListener interface*/
public class GameMain extends JPanel implements MouseListener{
	// Constants for the game dimensions and appearance
	public static final int ROWS = 3;
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
	
	//Declare game object variables
	private Board board;
	private enum GameState { PLAYING, DRAW, X_WON, O_WON }
	private GameState currentState;
	// variable keeps track of whose turn it is
	private Player currentPlayer;
	// for displaying game status message on the GUI
	private JLabel statusBar;

	//Constructor: Sets up the UI and game components on the panel
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

	}

	//The entry point of the program. It sets up and displays the graphical user interface.
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

	//Custom painting on the panel. It paints the game board and updates the status bar based on the current state of the game.
	public void paintComponent(Graphics g) {
		//fill background and set colour to white
		super.paintComponent(g);
		setBackground(Color.WHITE);

		//ask the game board to paint itself
		board.paint(g);

		// Simplify status bar update using switch
		statusBar.setForeground(Color.BLACK);
		String statusMessage = "";
		switch (currentState) {
			case PLAYING:
				statusMessage = "'" + currentPlayer + "' Turn";
				break;
			case DRAW:
				statusBar.setForeground(Color.RED);
				statusMessage = "It's a Draw! Click to play again.";
				break;
			case X_WON:
				statusMessage = "'X' Won! Click to play again.";
				break;
			case O_WON:
				statusMessage = "'O' Won! Click to play again.";
				break;
		}
		statusBar.setText(statusMessage);
	}

	public void initGame() {
		// Initialize the cells and reset game state
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				board.cells[row][col].content = Player.EMPTY; // Use proper enum value
			}
		}
		currentState = GameState.PLAYING;
		currentPlayer = Player.X;
	}

	public void updateGame(Player thePlayer, int row, int col) {
		if (board.hasWon(thePlayer, row, col)) {
			currentState = thePlayer == Player.X ? GameState.X_WON : GameState.O_WON;
		} else if (board.isDraw()) {
			currentState = GameState.DRAW;
		}
	}

	// implementation and handles mouse click events on the panel.
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		int rowSelected = mouseY / CELL_SIZE;
		int colSelected = mouseX / CELL_SIZE;

		if (currentState == GameState.PLAYING) {
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
					&& board.cells[rowSelected][colSelected].content == Player.EMPTY) {
				board.cells[rowSelected][colSelected].content = currentPlayer;
				updateGame(currentPlayer, rowSelected, colSelected);

				// Simplify switching players
				currentPlayer = (currentPlayer == Player.X) ? Player.O: Player.X;
			}
		} else {
			// Restart the game
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
