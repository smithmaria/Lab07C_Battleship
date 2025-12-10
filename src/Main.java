/**
 * Entry point for the Battleship game application.
 */
public class Main {
    /**
     * Main method - launches the Battleship game
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        // Ensure GUI runs on Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Game();
        });
    }
}