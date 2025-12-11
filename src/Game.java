import javax.swing.*;

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

        // Handle different results
        if (result == Board.FireResult.SUNK) {
            JOptionPane.showMessageDialog(gui, "Ship Sunk!", "Hit!", JOptionPane.INFORMATION_MESSAGE);
        } else if (result == Board.FireResult.STRIKE) {
            JOptionPane.showMessageDialog(gui, "Strike! " + board.getStrikeCounter() + " strike(s) total.", "Strike!", JOptionPane.WARNING_MESSAGE);
        } else if (result == Board.FireResult.GAME_LOST) {
            gameOver = true;
            int choice = JOptionPane.showConfirmDialog(gui, "Game Over! You lost after 3 strikes!\nPlay again?",
                    "Game Lost", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                startNewGame();
                gui.reset();
            } else {
                System.exit(0);
            }
            return;
        }

        // Check for game won
        if (board.isGameWon()) {
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