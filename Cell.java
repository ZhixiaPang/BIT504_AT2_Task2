import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {
    //content of this cell (EMPTY, CROSS, NOUGHT)
	private Player content;
	//row and column of this cell
	private int row, col;
	
	// Constructor to initialise this cell with the specified row and col
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Player getContent() {
		return content;
	}


	//Paint itself on the graphics canvas, given the Graphics context g
	public void paint(Graphics g) {
		// Graphics2D allows setting of pen's stroke size
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		//draw the symbol in the position
		int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
		int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
		if (content == Player.CROSS) {
			g2d.setColor(Color.RED);
			int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
			int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
			g2d.drawLine(x1, y1, x2, y2);
			g2d.drawLine(x2, y1, x1, y2);
		} else if (content == Player.NOUGHT) {
			g2d.setColor(Color.BLUE);
			g2d.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
		}
	}
	}