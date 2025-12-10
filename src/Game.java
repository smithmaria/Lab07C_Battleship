/**
 * Main game controller for Battleship.
 * Coordinates between Board logic and GUI display.
 */
public class Game {
    private Board board;
    private BoardGUI gui;
    private boolean gameOver;

    /**
     * Constructor - initializes new game
     */
    public Game() {
        board = new Board();
        gameOver = false;
        gui = new BoardGUI(this);
        gui.setVisible(true);
    }

    /**
     * Handle player move at given coordinates
     * @param row Row coordinate
     * @param col Column coordinate
     */
    public void handleMove(int row, int col) {
        if (gameOver) return;

        Board.FireResult result = board.makeMove(row, col);

        // Update GUI
        gui.updateTile(row, col);

        // Show ship sunk dialog
        if (result == Board.FireResult.SUNK) {
            javax.swing.JOptionPane.showMessageDialog(gui, "Ship Sunk!", "Hit!", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

        // Check for game over
        if (board.isGameOver()) {
            gameOver = true;
            gui.showGameOverDialog();
        }
    }

    /**
     * Start new game
     */
    public void startNewGame() {
        board.reset();
        gameOver = false;
    }

    /**
     * Check if game is over
     * @return True if all ships are sunk
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Get the game board
     * @return Current board instance
     */
    public Board getBoard() {
        return board;
    }
}