import java.awt.*;

public class Board {
	// grid line width
	public static final int GRID_WIDTH = 8;
	// grid line half width
	public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

	//2D array of ROWS-by-COLS Cell instances
	Cell[][] cells;

	/**
	 * Constructor to create the game board
	 */
	public Board() {
		cells = new Cell[GameMain.ROWS][GameMain.COLS];// Initialize the 2D array of cells

		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}


	/**
	 * Return true if it is a draw (i.e., no more EMPTY cells)
	 */
	/** Return true if it is a draw (i.e., no more EMPTY cells) */
	public boolean isDraw() {
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				if (cells[row][col].content == Player.EMPTY) {
					return false; // If any cell is empty, the game is not a draw
				}
			}
		}
		return true; // If no empty cells are found, the game is a draw
	}


	/* Return true if the current player "thePlayer" has won after making their move */
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
		// Check if player has 3-in-that-row
		boolean rowWin = true;
		for (int col = 0; col < GameMain.COLS; ++col) {
			if (cells[playerRow][col].content != thePlayer) {
				rowWin = false;
				break;
			}
		}
		if (rowWin) return true;

		// Check if player has 3-in-that-column
		boolean colWin = true;
		for (int row = 0; row < GameMain.ROWS; ++row) {
			if (cells[row][playerCol].content != thePlayer) {
				colWin = false;
				break;
			}
		}
		if (colWin) return true;

		// Check the diagonal
		if (playerRow == playerCol) {
			boolean diagonalWin = true;
			for (int i = 0; i < GameMain.ROWS; ++i) {
				if (cells[i][i].content != thePlayer) {
					diagonalWin = false;
					break;
				}
			}
			if (diagonalWin) return true;
		}

		// Check the other diagonal
		if (playerRow + playerCol == GameMain.ROWS - 1) {
			boolean otherDiagonalWin = true;
			for (int i = 0; i < GameMain.ROWS; ++i) {
				if (cells[i][GameMain.ROWS - 1 - i].content != thePlayer) {
					otherDiagonalWin = false;
					break;
				}
			}
			if (otherDiagonalWin) return true;
		}

		// no winner, keep playing
		return false;
	}



	/**
	 * Draws the grid (rows then columns) using constant sizes, then call on the Cells
	 * to paint themselves into the grid
	 */
	public void paint(Graphics g) {
		// Draw the grid
		g.setColor(Color.gray);
		for (int row = 1; row < GameMain.ROWS; ++row) {
			g.fillRoundRect(
					0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF,
					GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
					GRID_WIDTH, GRID_WIDTH
			);
		}
		for (int col = 1; col < GameMain.COLS; ++col) {
			g.fillRoundRect(
					GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0,
					GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
					GRID_WIDTH, GRID_WIDTH
			);
		}

		// Draw the cells
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				cells[row][col].paint(g);
			}
		}
	}
}
