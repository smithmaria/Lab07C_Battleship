import javax.swing.*;
import java.awt.*;

/**
 * GUI for the Battleship game board.
 * Creates and manages the visual 10x10 grid of tiles.
 */
public class BoardGUI extends JFrame {
    private Game game;
    private BoardTile[][] tiles;
    private JLabel statusLabel;
    private JLabel hitsLabel;
    private JLabel missesLabel;
    private JLabel shipsLabel;
    private JLabel strikeLabel;

    /**
     * Constructor for BoardGUI
     * @param game The game instance to interact with
     */
    public BoardGUI(Game game) {
        this.game = game;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Battleship");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initialize GUI components
     */
    private void initializeComponents() {
        tiles = new BoardTile[Board.BOARD_SIZE][Board.BOARD_SIZE];

        // Create all tiles
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                tiles[row][col] = new BoardTile(row, col);
            }
        }

        // Status labels
        statusLabel = new JLabel("Click to fire!");
        hitsLabel = new JLabel("Hits: 0");
        missesLabel = new JLabel("Misses: 0");
        shipsLabel = new JLabel("Ships Sunk: 0");
        strikeLabel = new JLabel("Strikes: 0");
    }

    /**
     * Setup the layout
     */
    private void setupLayout() {
        setLayout(new BorderLayout());

        // Game board panel
        JPanel boardPanel = new JPanel(new GridLayout(Board.BOARD_SIZE, Board.BOARD_SIZE, 1, 1));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                boardPanel.add(tiles[row][col]);
            }
        }

        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(hitsLabel);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(missesLabel);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(shipsLabel);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(strikeLabel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton playAgainButton = new JButton("Play Again");
        JButton quitButton = new JButton("Quit");

        playAgainButton.addActionListener(e -> {
            game.startNewGame();
            reset();
        });

        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);

        add(boardPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Setup event handlers for tiles
     */
    private void setupEventHandlers() {
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                final int r = row;
                final int c = col;

                tiles[row][col].addActionListener(e -> {
                    SwingUtilities.invokeLater(() -> game.handleMove(r, c));
                });
            }
        }
    }

    /**
     * Update a specific tile's display
     * @param row Row of tile to update
     * @param col Column of tile to update
     */
    public void updateTile(int row, int col) {
        if (game.getBoard().hasFiredAt(row, col)) {
            boolean isHit = false;
            for (Ship ship : game.getBoard().getShips()) {
                if (ship.isHit(row, col)) {
                    isHit = true;
                    break;
                }
            }

            tiles[row][col].updateDisplay(isHit, !isHit, true);
        }

        updateStatusLabels();
    }

    /**
     * Update status labels with current game stats
     */
    private void updateStatusLabels() {
        hitsLabel.setText("Hits: " + game.getBoard().getTotalHits());
        missesLabel.setText("Misses: " + game.getBoard().getMissCounter());
        strikeLabel.setText("Strikes: " + game.getBoard().getStrikeCounter());

        int sunkShips = 0;
        for (Ship ship : game.getBoard().getShips()) {
            if (ship.isSunk()) sunkShips++;
        }
        shipsLabel.setText("Ships Sunk: " + sunkShips);

        if (game.isGameOver()) {
            if (game.getBoard().isGameWon()) {
                statusLabel.setText("You Win! All ships sunk!");
            } else {
                statusLabel.setText("Game Over! You lost!");
            }
        } else {
            statusLabel.setText("Click to fire!");
        }
    }

    /**
     * Show game over dialog
     */
    public void showGameOverDialog() {
        int totalShots = game.getBoard().getTotalHits() + game.getBoard().getTotalMisses();
        String message = String.format("Congratulations! You sunk all ships!\nTotal shots: %d\nHits: %d\nMisses: %d",
                totalShots, game.getBoard().getTotalHits(), game.getBoard().getTotalMisses());

        int choice = JOptionPane.showConfirmDialog(this, message + "\n\nPlay again?",
                "Game Over", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            game.startNewGame();
            reset();
        } else {
            System.exit(0);
        }
    }

    /**
     * Reset GUI for new game
     */
    public void reset() {
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                tiles[row][col].reset();
            }
        }

        statusLabel.setText("Click to fire!");
        hitsLabel.setText("Hits: 0");
        missesLabel.setText("Misses: 0");
        shipsLabel.setText("Ships Sunk: 0");
        strikeLabel.setText("Strikes: 0");
    }
}