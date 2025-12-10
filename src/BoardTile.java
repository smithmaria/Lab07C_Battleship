import javax.swing.*;
import java.awt.*;

/**
 * Individual button tile for the Battleship game board.
 * Extends JButton to represent one cell in the 10x10 grid.
 */
public class BoardTile extends JButton {
    private int row;
    private int col;

    // Display constants
    private static final Color BLANK_COLOR = new Color(98, 163, 181);
    private static final Color HIT_COLOR = new Color(235, 169, 169);
    private static final Color MISS_COLOR = new Color(242, 217, 133);

    /**
     * Constructor for BoardTile
     * @param row Row position (0-9)
     * @param col Column position (0-9)
     */
    public BoardTile(int row, int col) {
        this.row = row;
        this.col = col;

        // Initial appearance
        setBackground(BLANK_COLOR);
        setOpaque(true);
        setPreferredSize(new Dimension(40, 40));
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setBorderPainted(true);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        setText("");
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    /**
     * Update tile display based on game state
     * @param isHit Whether this tile has been hit
     * @param isMiss Whether this tile is a miss
     * @param isAlreadyFired Whether player already fired here
     */
    public void updateDisplay(boolean isHit, boolean isMiss, boolean isAlreadyFired) {
        if (isHit) {
            setOpaque(true);
            setBackground(HIT_COLOR);
            setText("X");
            setEnabled(false);
        } else if (isMiss) {
            setOpaque(true);
            setBackground(MISS_COLOR);
            setText("M");
            setEnabled(false);
        } else if (isAlreadyFired) {
            setEnabled(false);
        } else {
            // Reset to blank state
            setOpaque(true);
            setBackground(BLANK_COLOR);
            setText("");
            setEnabled(true);
        }
        repaint();
    }

    /**
     * Reset tile to initial blank state
     */
    public void reset() {
        setBackground(BLANK_COLOR);
        setText("");
        setEnabled(true);
        repaint();
    }

    // Getter methods
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}