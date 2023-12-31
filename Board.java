import java.awt.*;      // Import AWT library for basic window and UI functionality
import java.util.Arrays; // Import Arrays class for array manipulation and stream operations


public class Board {
	// Constants for the grid appearance
	public static final int GRID_WIDTH = 8;
	public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

	private final Cell[][] cells; // Represents the game board as a grid of cells

	// Constructor to initialize the board
	public Board() {
		// Create a new grid of cells with dimensions defined in GameMain
		cells = new Cell[GameMain.ROWS][GameMain.COLS];
		initializeCells(); // Initialize the cells with empty values
	}

	// Get a specific cell from the grid
	public Cell getCell(int row, int col) {
		return cells[row][col];
	}

	// Set the content of a cell (used for player moves)
	public void setCell(int row, int col, Cell cell) {
		cells[row][col] = cell;
	}

	// Initialize all cells with empty values
	private void initializeCells() {
		for (int row = 0; row < GameMain.ROWS; row++) {
			for (int col = 0; col < GameMain.COLS; col++) {
				cells[row][col] = new Cell(row, col, Player.EMPTY);
			}
		}
	}

	// Check if the game is a draw (no empty cells left)
	public boolean isDraw() {
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				if (cell.getContent() == Player.EMPTY) {
					return false; // If any cell is empty, the game is not a draw
				}
			}
		}
		return true; // All cells are filled, indicating a draw
	}

	// Check if the specified player has won given their latest move
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
		// Check for win conditions in rows, columns, and diagonals
		return checkRowForWin(thePlayer, playerRow) ||
				checkColumnForWin(thePlayer, playerCol) ||
				checkDiagonalForWin(thePlayer, playerRow, playerCol);
	}

	// Check if all cells in a row contain the same player's symbol
	private boolean checkRowForWin(Player thePlayer, int playerRow) {
		return Arrays.stream(cells[playerRow]).allMatch(cell -> cell.getContent() == thePlayer);
	}

	// Check if all cells in a column contain the same player's symbol
	private boolean checkColumnForWin(Player thePlayer, int playerCol) {
		return Arrays.stream(cells).allMatch(row -> row[playerCol].getContent() == thePlayer);
	}

	// Check if a diagonal contains the same player's symbol
	private boolean checkDiagonalForWin(Player thePlayer, int playerRow, int playerCol) {
		if (playerRow == playerCol || playerRow + playerCol == GameMain.ROWS - 1) {
			return checkMainDiagonalForWin(thePlayer) || checkAntiDiagonalForWin(thePlayer);
		}
		return false;
	}

	// Check if the main diagonal contains the same player's symbol
	private boolean checkMainDiagonalForWin(Player thePlayer) {
		for (int i = 0; i < GameMain.ROWS; i++) {
			if (cells[i][i].getContent() != thePlayer) {
				return false;
			}
		}
		return true;
	}

	// Check if the anti-diagonal contains the same player's symbol
	private boolean checkAntiDiagonalForWin(Player thePlayer) {
		for (int i = 0; i < GameMain.ROWS; i++) {
			if (cells[i][GameMain.ROWS - 1 - i].getContent() != thePlayer) {
				return false;
			}
		}
		return true;
	}

	// Paint the game board and grid lines
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);

		// Draw horizontal grid lines
		for (int row = 1; row < GameMain.ROWS; row++) {
			int y = row * GameMain.CELL_SIZE - GRID_WIDTH_HALF;
			drawHorizontalLine(g, y);
		}

		// Draw vertical grid lines
		for (int col = 1; col < GameMain.COLS; col++) {
			int x = col * GameMain.CELL_SIZE - GRID_WIDTH_HALF;
			drawVerticalLine(g, x);
		}

		// Paint each cell in the grid
		for (int row = 0; row < GameMain.ROWS; row++) {
			for (int col = 0; col < GameMain.COLS; col++) {
				cells[row][col].paint(g);
			}
		}
	}

	// Helper method to draw horizontal grid lines
	private void drawHorizontalLine(Graphics g, int y) {
		g.fillRect(0, y, GameMain.CANVAS_WIDTH, GRID_WIDTH);
	}

	// Helper method to draw vertical grid lines
	private void drawVerticalLine(Graphics g, int x) {
		g.fillRect(x, 0, GRID_WIDTH, GameMain.CANVAS_HEIGHT);
	}
}
