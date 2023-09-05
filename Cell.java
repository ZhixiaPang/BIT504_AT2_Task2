import java.awt.BasicStroke; // Import BasicStroke class for customizing stroke properties
import java.awt.Color;        // Import Color class for specifying colors
import java.awt.Graphics;     // Import Graphics class for 2D graphics rendering
import java.awt.Graphics2D;   // Import Graphics2D class for advanced 2D graphics rendering




public class Cell {
    //content of this cell (EMPTY, CROSS, NOUGHT)
    private final Player content;
    //row and column of this cell

// This class represents a cell in the Tic-Tac-Toe game grid.
public class Cell {
    // The content of this cell (EMPTY, CROSS, NOUGHT)
    private final Player content;
    // The row and column of this cell

    private final int row, col;

    // Constructor to initialize this cell with the specified row, col, and content
    public Cell(int row, int col, Player content) {
        this.row = row;
        this.col = col;
        this.content = content;
    }



    // Get the content of this cell (EMPTY, CROSS, NOUGHT)

    public Player getContent() {
        return content;
    }




    //Paint itself on the graphics canvas, given the Graphics context g
    public void paint(Graphics g) {
        // Graphics2D allows setting of pen's stroke size
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH *2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        //draw the symbol in the position

    // Paint itself on the graphics canvas, given the Graphics context g
    public void paint(Graphics g) {
        // Graphics2D allows setting the pen's stroke size
        Graphics2D g2d = (Graphics2D) g;
        // Set the stroke size for drawing symbols with a rounded end and join
        g2d.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the symbol in the position

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

            // If the content is CROSS, set the color to RED
            g2d.setColor(Color.RED);
            int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
            // Draw two lines to create a cross symbol
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y1, x1, y2);
        } else if (content == Player.NOUGHT) {
            // If the content is NOUGHT, set the color to BLUE
            g2d.setColor(Color.BLUE);
            // Draw an oval to create a circle symbol
            g2d.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
        }
    }
}

