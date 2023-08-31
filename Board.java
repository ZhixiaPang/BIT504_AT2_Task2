import java.awt.Color;import java.awt.Graphics;public class Board {	// grid line width	public static final int GRID_WIDTH = 8;	// grid line half width	public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;	public Player[][] cells;	public Board() {		cells = new Player[GameMain.ROWS][GameMain.COLS];		initializeCells();	}	private void initializeCells() {		for (int row = 0; row < GameMain.ROWS; row++) {			for (int col = 0; col < GameMain.COLS; col++) {				cells[row][col] = Player.EMPTY;			}		}	}	/* Return true if it is a draw */	public boolean isDraw() {		// Iterate through each cell on the game board		for (int row = 0; row < GameMain.ROWS; ++row) {			for (int col = 0; col < GameMain.COLS; ++col) {				// If any cell is empty, the game is not a draw				if (cells[row][col] == Player.EMPTY) {					return false;				}			}		}		// If no empty cells are found, the game is a draw		return true;	}	private boolean checkArrayForWin(Player[] array, Player playerSymbol) {		for (Player cell : array) {			if (cell != playerSymbol) {				return false;			}		}		return true;	}		/* Return true if the current player "thePlayer" has won after making their move */	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {		// Check if player has three symbols in that row		if (checkArrayForWin(cells[playerRow], thePlayer)) {			return true;		}		Player[] colArray = new Player[GameMain.ROWS];		for (int row = 0; row < GameMain.ROWS; ++row) {			colArray[row] = cells[row][playerCol];		}		if (checkArrayForWin(colArray, thePlayer)) {			return true;		}		if (playerRow + playerCol == GameMain.ROWS - 1) {			Player[] antiDiagonalArray = new Player[GameMain.ROWS];			for (int i = 0; i < GameMain.ROWS; ++i) {				antiDiagonalArray[i] = cells[i][GameMain.ROWS - 1 - i];			}			return checkArrayForWin(antiDiagonalArray, thePlayer);		}		return false;	}	public void paint(Graphics g) {		g.setColor(Color.gray);		for (int row = 1; row < GameMain.ROWS; ++row) {			int y = row * GameMain.CELL_SIZE - GRID_WIDTH_HALF;			drawHorizontalLine(g, 0, y, GameMain.CANVAS_WIDTH );		}		for (int col = 1; col < GameMain.COLS; ++col) {			int x = col * GameMain.CELL_SIZE - GRID_WIDTH_HALF;			drawVerticalLine(g, x, 0, GameMain.CANVAS_HEIGHT );		}		for (int row = 0; row < GameMain.ROWS; ++row) {			for (int col = 0; col < GameMain.COLS; ++col) {				int x = col * GameMain.CELL_SIZE;				int y = row * GameMain.CELL_SIZE;				if (cells[row][col] == Player.CROSS) {					drawCross(g, x, y);				} else if (cells[row][col] == Player.NOUGHT) {					drawNought(g, x, y);				}			}		}	}	// Method to draw a cross (X)	private void drawCross(Graphics g, int x, int y) {		g.setColor(Color.BLACK);		g.drawLine(x + GRID_WIDTH_HALF, y + GRID_WIDTH_HALF,				x + GameMain.CELL_SIZE - GRID_WIDTH_HALF, y + GameMain.CELL_SIZE - GRID_WIDTH_HALF);		g.drawLine(x + GRID_WIDTH_HALF, y + GameMain.CELL_SIZE - GRID_WIDTH_HALF,				x + GameMain.CELL_SIZE - GRID_WIDTH_HALF, y + GRID_WIDTH_HALF);	}	private void drawNought(Graphics g, int x, int y) {		g.setColor(Color.BLACK);		g.drawOval(x + GRID_WIDTH_HALF, y + GRID_WIDTH_HALF,				GameMain.CELL_SIZE - GRID_WIDTH, GameMain.CELL_SIZE - GRID_WIDTH);	}	private void drawHorizontalLine(Graphics g, int x, int y, int width) {		g.fillRect(x, y, width, GRID_WIDTH);	}	private void drawVerticalLine(Graphics g, int x, int y, int height) {		g.fillRect(x, y, GRID_WIDTH, height);	}}