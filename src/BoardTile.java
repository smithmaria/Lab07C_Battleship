import javax.swing.*;
import java.awt.*;

/**
 * Individual button tile for the Battleship game board.
 * Extends JButton to represent one cell in the 10x10 grid.
 */
public class BoardTile extends JButton {
    private int row;
    private int col;

    private static final Color BLANK_COLOR = Color.LIGHT_GRAY;
    private static final Color HIT_COLOR = Color.RED;
    private static final Color MISS_COLOR = Color.YELLOW;

    /**
     * Constructor for BoardTile
     * @param row Row position (0-9)
     * @param col Column position (0-9)
     */
    public BoardTile(int row, int col) {
        this.row = row;
        this.col = col;

        setBackground(BLANK_COLOR);
        setPreferredSize(new Dimension(40, 40));
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
        setText("");
    }

    /**
     * Update tile display based on game state
     * @param isHit Whether this tile has been hit
     * @param isMiss Whether this tile is a miss
     * @param isAlreadyFired Whether player already fired here
     */
    public void updateDisplay(boolean isHit, boolean isMiss, boolean isAlreadyFired) {
        if (isHit) {
            setBackground(HIT_COLOR);
            setText("X");
            setEnabled(false);
        } else if (isMiss) {
            setBackground(MISS_COLOR);
            setText("M");
            setEnabled(false);
        } else if (isAlreadyFired) {
            setEnabled(false);
        } else {
            setBackground(BLANK_COLOR);
            setText("");
            setEnabled(true);
        }
    }

    /**
     * Reset tile to initial blank state
     */
    public void reset() {
        setBackground(BLANK_COLOR);
        setText("");
        setEnabled(true);
    }

    // Getter methods
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}