import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tracks the game board for Battleship on back-end.
 * Manages ship placement, firing attempts, and game state.
 */
public class Board {
    public static final int BOARD_SIZE = 10;
    private static final int[] SHIP_SIZES = {2, 3, 3, 4, 5};

    private List<Ship> ships;
    private boolean[][] firedAt;    // Where player has fired
    private int totalHits;
    private int totalMisses;
    private boolean gameWon;
    private Random random;

    public enum FireResult {
        HIT, MISS, SUNK, ALREADY_FIRED
    }

    /**
     * Constructor - creates empty board
     */
    public Board() {
        ships = new ArrayList<>();
        firedAt = new boolean[BOARD_SIZE][BOARD_SIZE];
        totalHits = 0;
        totalMisses = 0;
        gameWon = false;
        random = new Random();
        placeShipsRandomly();
    }

    /**
     * Attempt to fire at given coordinates
     * @param row Row coordinate (0-9)
     * @param col Column coordinate (0-9)
     * @return Result of the fire attempt
     */
    public FireResult makeMove(int row, int col) {
        // Check for out of bounds
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return FireResult.MISS;
        }

        // Check if already fired at
        if (firedAt[row][col]) {
            return FireResult.MISS;
        }

        firedAt[row][col] = true;

        // Check if ship is hit, mark hit, check sunk
        Ship hitShip = getShipAt(row, col);
        if (hitShip != null) {
            hitShip.markHit(row, col);
            totalHits++;

            if (hitShip.isSunk()) {
                checkGameWon();
                return FireResult.SUNK;
            }
            return FireResult.HIT;
        } else {
            totalMisses++;
            return FireResult.MISS;
        }
    }

    /**
     * Get the result without making a move (for preview when debugging)
     * @param row Row coordinate
     * @param col Column coordinate
     * @return What the result would be
     */
    public FireResult getMoveResult(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return FireResult.MISS;
        }

        if (firedAt[row][col]) {
            return FireResult.ALREADY_FIRED;
        }

        Ship ship = getShipAt(row, col);
        return (ship != null) ? FireResult.HIT : FireResult.MISS;
    }

    /**
     * Reset the board for a new game
     */
    public void reset() {
        ships.clear();
        firedAt = new boolean[BOARD_SIZE][BOARD_SIZE];
        totalHits = 0;
        totalMisses = 0;
        gameWon = false;
        placeShipsRandomly();
    }

    /**
     * Randomly place all ships on the board
     */
    public void placeShipsRandomly() {
        ships.clear();

        for (int shipSize : SHIP_SIZES) {
            Ship ship;
            int attempts = 0;
            do {
                boolean horizontal = random.nextBoolean();

                // So ship does not get placed off of edge
                int maxRow = horizontal ? BOARD_SIZE : BOARD_SIZE - shipSize;
                int maxCol = horizontal ? BOARD_SIZE - shipSize : BOARD_SIZE;

                int row = random.nextInt(maxRow);
                int col = random.nextInt(maxCol);

                ship = new Ship(shipSize, row, col, horizontal);
                attempts++;
            } while (!isValidPlacement(ship) && attempts < 100);

            if (attempts < 100) {
                ships.add(ship);
            }
        }
    }

    /**
     * Check if a ship palcement is valid (doesn't overlap)
     * @param ship Ship to check
     * @return True if placement is valid
     */
    private boolean isValidPlacement(Ship ship) {
        // Check bounds
        List<Point> coordinates = ship.getOccupiedCoordinates();
        for (Point p : coordinates) {
            if (p.x < 0 || p.x >= BOARD_SIZE || p.y < 0 || p.y >= BOARD_SIZE) {
                return false;
            }
        }

        // Check for overlap
        for (Ship existingShip : ships) {
            if (ship.overlaps(existingShip)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Get ship at given coordinates
     * @param row Row coordinate
     * @param col Column coordinate
     * @return Ship at coordinates, or null if none
     */
    private Ship getShipAt(int row, int col) {
        for (Ship ship : ships) {
            if (ship.isHit(row, col)) {
                return ship;
            }
        }
        return null;
    }

    /**
     * Check if all ships are sunk
     */
    private void checkGameWon() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                gameWon = false;
                return;
            }
        }
        gameWon = true;
    }

    // Getters
    public boolean isGameOver() {
        return gameWon;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public int getTotalMisses() {
        return totalMisses;
    }

    public boolean hasFiredAt(int row, int col) {
        return firedAt[row][col];
    }

    public List<Ship> getShips() {
        return new ArrayList<>(ships); // Returns copy for safety
    }
}
