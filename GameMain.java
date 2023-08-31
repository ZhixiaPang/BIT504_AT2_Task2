//import necessary classes from the AWT and SWing libraries.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Declares the GameMain class. It extends JPanel and implements the MouseListener interface
public class GameMain extends JPanel implements MouseListener{
	// Constants for the game dimensions and appearance
	public static final int ROWS = 3;
	public static final int COLS = 3;
	// defines a constant string named TITLE with the value "Tic Tac Toe"
	public static final String TITLE = "Tic Tac Toe";

	// defines each cell in the tic-tac-toe grid is a square with sides of length 100 pixels.
	public static final int CELL_SIZE = 100;
	//drawing canvas
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	//Noughts and Crosses are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	public static final int SYMBOL_STROKE_WIDTH = 8;


	//Declare game object variables
	//the game board
	private Board board;
	//create the enumeration for (GameState currentState)
	private enum GameState { PLAYING, DRAW, CROSS_WON, NOUGHT_WON }
	private GameState currentState = GameState.PLAYING;
	// variable keeps track of whose turn it is
	private Player currentPlayer;
	// for displaying game status message on the GUI
	private JLabel statusBar;

	//Constructor: Sets up the UI and game components on the panel
	public GameMain() {
		super(); // Call the constructor of the superclass (JPanel)
		initializeUI();
		initializeGame();
	}

	private void initializeUI() {
		JFrame frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		addMouseListener(this);

		statusBar = new JLabel("         ");
		statusBar.setFont(new Font("Arial", Font.BOLD, 14));

		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		statusBar.setOpaque(true);
		statusBar.setBackground(Color.LIGHT_GRAY);

		setLayout(new BorderLayout());
		add(statusBar, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));
	}

	private void initializeGame() {
		board = new Board();
		currentState = GameState.PLAYING;
		currentPlayer = Player.CROSS;
	}


	//The entry point of the program. It sets up and displays the graphical user interface.
	private void createAndShowGUI() {
		SwingUtilities.invokeLater(() -> {
			// Create a main window to contain the panel
			JFrame frame = new JFrame(TITLE);

			// Create the new GameMain panel and add it to the frame
			frame.add(this);

			// Set the default close operation of the frame to EXIT_ON_CLOSE
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
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
		String statusMessage = switch (currentState) {
            case PLAYING -> "'" + currentPlayer + "' Turn";
            case DRAW -> {
                statusBar.setForeground(Color.RED);
                yield "It's a Draw! Click to play again.";
            }
            case CROSS_WON -> "'Cross' Won! Click to play again.";
            case NOUGHT_WON -> "'Nought' Won! Click to play again.";
        };
        statusBar.setText(statusMessage);
	}

	public void initGame() {
		// Call the initializeGame() method to reset the game board
		initializeGame();
	}

	public void updateGame(Player thePlayer, int row, int col) {
		if (board.hasWon(thePlayer, row, col)) {
			currentState = thePlayer == Player.CROSS ? GameState.CROSS_WON: GameState.NOUGHT_WON;
		} else if (board.isDraw()) {
			currentState = GameState.DRAW;
		}
	}

	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		int rowSelected = mouseY / CELL_SIZE;
		int colSelected = mouseX / CELL_SIZE;

		if (currentState == GameState.PLAYING) {
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
					&& board.cells[rowSelected][colSelected] == Player.EMPTY) {
				board.cells[rowSelected][colSelected] = currentPlayer;
				updateGame(currentPlayer, rowSelected, colSelected);
				currentPlayer = currentPlayer == Player.CROSS ? Player.NOUGHT : Player.CROSS;
			}
		} else {
			initGame();
		}

		repaint();
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}




