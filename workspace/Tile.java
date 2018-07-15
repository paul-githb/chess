import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/** Represents a single square on the Chess board.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Tile extends JButton {
  /** The row of the tile on the chess board. */
  private int row;

  /** The column of the tile on the chess board. */
  private int col;

  /** The piece currently on the tile. */
  private Piece piece;

  /**
   * Constructor for the Tile class. Initializes a new Tile,
   * composed of a button that can handle moves based on the
   * current state in the sequence of events.
   * @param r The row of the Tile on the Chess board.
   * @param c The column of the Tile on the Chess board.
   */
  public Tile(int r, int c) {
    row = r;
    col = c;

    int u = Game.getUnit();

    setBounds(u * (c + 2), u * (r + 2), u, u);

    // Make the tile transparent.
    setOpaque(false);
    setContentAreaFilled(false);

    // Add an action listener to listen for button clicks.
    addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        // Only accept moves if no winner has been found.
        if (Game.getStatus() == -1) {
          // Retrieve the necessary variables to handle the response.
          Tile tile = (Tile) (e.getSource());
          Game.getBoard().handleRequest(tile);
        }
      }

    });

    // Create thicker borders around the edges.
    int up, down, left, right;
    int thickness = Game.getScale() / 200;
    up = (row == 0) ? thickness : 0;
    down = (row == 7) ? thickness : 0;
    left = (col == 0) ? thickness : 0;
    right = (col == 7) ? thickness : 0;
    setBorder(BorderFactory.createMatteBorder(up, left, down, right, Color.BLACK));
  }

  /**
   * Returns the row of the Tile on the Chess board.
   * @return The row of the Tile.
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column of the Tile on the Chess board.
   * @return The column of the Tile.
   */
  public int getCol() {
    return col;
  }

  /**
   * Returns the Piece that's currently on the Tile.
   * @return The Piece currently on the Tile.
   */
  public Piece getPiece() {
    return piece;
  }

  /**
   * Sets the image the button displays when painted.
   * @param icon The ImageIcon displayed on the Tile.
   */
  private void setImageIcon(ImageIcon icon) {
    setIcon(icon);
  }

  /**
   * Adds a new Piece onto the Tile. Returns the old Piece
   * which was replaced by the new one.
   * @param p The Piece to add to the Tile.
   * @return The Piece that was removed from the Tile.
   */
  public Piece setPiece(Piece p) {
    Piece oldPiece = piece;
    piece = p;
    
    if (p == null)
      setImageIcon(null);
    else
      setImageIcon(p.getImageIcon());

    return oldPiece;
  }
}