import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a ship in the Battleship game.
 * Handles position, orientation, hit tracking, and sunk status.
 */

public class Ship {
    private int size;
    private int startRow;
    private int startCol;
    private boolean isHorizontal;
    private boolean[] hitPositions;
    private boolean sunk;

    /**
     * Constructor for Ship
     * @param size Length of the ship (2, 3, 4, or 5)
     * @param startRow Starting row position (0-9)
     * @param startCol Starting column position (0-9)
     * @param isHorizontal True if ship horizontal, false if vertical
     *                     Ships start left to right or top to bottom
     */
    public Ship(int size, int startRow, int startCol, boolean isHorizontal) {
        this.size = size;
        this.startRow = startRow;
        this.startCol = startCol;
        this.isHorizontal = isHorizontal;
        this.hitPositions = new boolean[size];
        this.sunk = false;
    }

    /**
     * Check if the given coordinates hit this specific ship
     * @param row Row coordinate to check
     * @param col Column coordinate to check
     * @return True if coordinates hit this ship, false otherwise
     */
    public boolean isHit(int row, int col) {
        List<Point> coordinates = getOccupiedCoordinates();
        return coordinates.contains(new Point(row, col));
    }

    /**
     * Mark a position as hit on this ship
     * @param row Row coordinate that was hit
     * @param col Column coordiante that was hit
     */
    public void markHit(int row, int col) {
        if (!isHit(row, col)) {
            return;
        }

        int hitIndex;                       // The index of tile that was hit on ship, again- starts left to right, top to bottom
        if (isHorizontal) {
            hitIndex = row - startRow;
        } else {
            hitIndex = row - startRow;
        }

        if (hitIndex >= 0 && hitIndex < size) {
            hitPositions[hitIndex] = true;
            updateSunkStatus();
        }
    }

    /**
     * Get al coordinates this ship occupies
     * @return List of Point objects representing occupied coordinates
     */
    public List<Point> getOccupiedCoordinates() {
        List<Point> coordinates = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (isHorizontal) {
                coordinates.add(new Point(startRow, startCol + i));
            } else {
                coordinates.add(new Point(startRow + i, startCol));
            }
        }

        return coordinates;
    }

    /**
     * Check if this ship overlaps with another ship
     * @param other The other ship to check against
     * @return True if ships overlap, false otherwise
     */
    public boolean overlaps(Ship other) {
        List<Point> thisCoordinates = this.getOccupiedCoordinates();
        List<Point> otherCoordinates = other.getOccupiedCoordinates();

        for (Point point : thisCoordinates) {
            if (otherCoordinates.contains(point)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Update the sunk status based on hit positions
     */
    private void updateSunkStatus() {
        for (boolean hit : hitPositions) {
            if (!hit) {
                sunk = false;
                return;
            }
        }
        sunk = true;
    }

    public int getSize() {
        return size;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public boolean isSunk() {
        return sunk;
    }

    /**
     * String representation for debugging
     * @return String describing the ship
     */
    @Override
    public String toString() {
        return String.format("Ship[size=%d, start=(%d,%d), horizontal=%s, sunk=%s]",
                size, startRow, startCol, isHorizontal, sunk);
    }
}
