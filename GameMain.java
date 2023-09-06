import javax.swing.*;         // Import Swing library for creating GUI components
import java.awt.*;            // Import AWT library for basic window and UI functionality
import java.awt.event.*;     // Import AWT event library for handling user events


public class GameMain extends JPanel implements MouseListener {
    // Constants for the game dimensions and appearance
    public static final int ROWS = 3;                 // Number of rows on the game board
    public static final int COLS = 3;                 // Number of columns on the game board
    public static final String TITLE = "Tic Tac Toe"; // Title of the game window
    public static final int CELL_SIZE = 100;         // Size of each cell in pixels
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;   // Width of the game canvas
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;  // Height of the game canvas
    public static final int CELL_PADDING = CELL_SIZE / 6;      // Padding around symbols in each cell
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // Size of X and O symbols
    public static final int SYMBOL_STROKE_WIDTH = 8; // Width of the symbol stroke

    private Board board;           // Game board to keep track of moves
    private enum GameState { PLAYING, DRAW, CROSS_WON, NOUGHT_WON } // Possible game states
    private GameState currentState; // Current game state
    private Player currentPlayer;   // Current player's turn
    private JLabel statusBar;      // Status bar for displaying game messages

    public GameMain() {
        super();
        initializeUI();
        initializeGame();
        createAndShowGUI();
        startGameLoop();
    }

    private void initializeUI() {
        JFrame frame = new JFrame(TITLE); // Create the main game window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the game on window close
        frame.add(this); // Add the game panel to the window
        frame.pack(); // Pack the frame to fit the content
        frame.setVisible(true); // Make the frame visible
        addMouseListener(this); // Listen for mouse events in the game panel

        statusBar = new JLabel("         "); // Create a status bar with initial content
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14)); // Set the font for the status bar
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5)); // Set border for the status bar
        statusBar.setOpaque(true); // Make the status bar opaque
        statusBar.setBackground(Color.LIGHT_GRAY); // Set background color for the status bar

        setLayout(new BorderLayout()); // Use BorderLayout for arranging components
        add(statusBar, BorderLayout.SOUTH); // Add the status bar to the bottom of the game panel
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30)); // Set preferred panel size
    }


    private void initializeGame() {
        board = new Board();             // Initialize the game board
        currentState = GameState.PLAYING; // Set the initial game state to playing
        currentPlayer = Player.CROSS;     // Set the initial player to Cross
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMain::new); // Create and show the game UI in the Event Dispatch Thread
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame(TITLE); // Create the game window with the specified title
        frame.add(this);                 // Add the game panel to the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the game on window close
        frame.pack();                    // Pack the frame to fit the content
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);          // Make the frame visible
    }


    private void startGameLoop() {
        int delay = 100; // Delay in milliseconds
        Timer gameTimer = new Timer(delay, (ActionEvent e) -> {
            if (currentState == GameState.PLAYING) {
                // Game logic for the current player's turn

                // Check for game over conditions and update currentState
                repaint(); // Request repainting of the game panel
            }
        });

        gameTimer.start(); // Start the timer
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE); // Set the background color of the panel to white
        board.paint(g); // Paint the game board

        statusBar.setForeground(Color.BLACK); // Set the status bar text color to black
        String statusMessage = switch (currentState) {
            case PLAYING -> "'" + currentPlayer + "' Turn"; // Display the current player's turn
            case DRAW -> {
                statusBar.setForeground(Color.RED); // Set status bar text color to red for a draw
                yield "It's a Draw! Click to play again.";
            }
            case CROSS_WON -> "'Cross' Won! Click to play again.";
            case NOUGHT_WON -> "'Nought' Won! Click to play again.";
        };
        statusBar.setText(statusMessage); // Set the status bar message
    }

    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Calculate the row and column selected based on the mouse click
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        // Restart the game if it's not in the playing state
        if (currentState != GameState.PLAYING) {
            initGame();   // Reset the game board and state
            repaint();    // Repaint the panel to show the initial state
            return;
        }

        // Check if the click is valid and update the game state
        if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
                && board.getCell(rowSelected, colSelected).getContent() == Player.EMPTY) {

            // Set the selected cell to the current player's symbol (X or O)
            board.setCell(rowSelected, colSelected, new Cell(rowSelected, colSelected, currentPlayer));

            // Check if the game is over due to a win or draw and update the state accordingly
            updateGame(currentPlayer, rowSelected, colSelected);

            repaint(); // Repaint the panel to reflect the updated game state

            // Handle game over conditions
            if (currentState == GameState.DRAW) {
                String message = "It's a Draw! Do you want to play again?";
                int option = JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Game Over",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    initGame(); // Reset the game if the player wants to play again
                } else if (option == JOptionPane.NO_OPTION) {
                    System.exit(0); // Exit the application if the player chooses not to play again
                }
            } else {
                // Switch to the other player's turn
                currentPlayer = (currentPlayer == Player.CROSS) ? Player.NOUGHT : Player.CROSS;
            }
        }

        // Handle game over conditions (again)
        if (currentState != GameState.PLAYING) {
            String message = switch (currentState) {
                case CROSS_WON -> "'Cross' Won! Do you want to play again?";
                case NOUGHT_WON -> "'Nought' Won! Do you want to play again?";
                default -> "";
            };

            int option = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "Game Over",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                initGame(); // Reset the game if the player wants to play again
            } else if (option == JOptionPane.NO_OPTION) {
                System.exit(0); // Exit the application if the player chooses not to play again
            }
        }
    }

    public void initGame() {
        initializeGame(); // Initialize the game by calling the 'initializeGame' method
    }

    public void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            // Check if the current player has won and update the game state accordingly
            currentState = thePlayer == Player.CROSS ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (board.isDraw()) {
            // Check if the game is a draw and update the game state accordingly
            currentState = GameState.DRAW;
        }
    }

    public void mousePressed(MouseEvent e) {
        // This method is triggered when a mouse button is pressed but not yet released
        // (This implementation does not use this event, so it remains empty)
    }

    public void mouseReleased(MouseEvent e) {
        // This method is triggered when a mouse button is released after being pressed
        // (This implementation does not use this event, so it remains empty)
    }

    public void mouseEntered(MouseEvent e) {
        // This method is triggered when the mouse enters the component (the game panel)
        // (This implementation does not use this event, so it remains empty)
    }

    public void mouseExited(MouseEvent e) {
        // This method is triggered when the mouse exits the component (the game panel)
        // (This implementation does not use this event, so it remains empty)
    }




}