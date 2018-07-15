import java.awt.Image;
import javax.swing.ImageIcon;

/** Represents a generic Chess piece.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public abstract class Piece {
  /* # ABSTRACTION # */

  /** The ownership ID of the piece. */
  private int playerNumber;

  /** The row of the piece on the chess board. */
  private int row;

  /** The column of the piece on the chess board. */
  private int col;

  /** The numerical value of the piece. */
  private int pieceValue;

  /** The image representing the piece. */
  private ImageIcon icon;

  /** Indicator of whether or not the piece has moved. */
  private boolean hasMoved;

  /**
   * Constructor for the piece class. All other chess pieces
   * should derive from this parent class. Sets all of the
   * necessary values for each piece.
   * @param pn The ownership ID of the piece.
   * @param r The initial row.
   * @param c The initial column.
   * @param value The numerical value of the piece.
   */
  public Piece(int pn, int r, int c, int value) {
    playerNumber = pn;
    row = r;
    col = c;
    pieceValue = value;
    hasMoved = false;
  }

  /** Sets the image representation of the Piece.
   * @param file The location of the image file.
   */
  protected void setImageIcon(String file) {
    int u = Game.getUnit();
    icon = new ImageIcon(new ImageIcon(file).getImage().getScaledInstance(u, u, Image.SCALE_SMOOTH));
  }

  /** Returns the image representation of the Piece.
   * @return The ImageIcon representing the look of the Piece.
   */
  public ImageIcon getImageIcon() {
    return icon;
  }

  /**
   * Returns the ownership ID of the piece.
   * @return The ownership ID of the piece.
   */
  public int getPlayerNumber() {
    return playerNumber;
  }

  /** 
   * Returns the current row of the piece.
   * @return The current row.
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the current column of the piece.
   * @return The current column.
   */
  public int getCol() {
    return col;
  }

  /** 
   * Sets the row and column positions of the piece.
   * @param r The new row value.
   * @param c The new column value.
   */
  public void setPosition(int r, int c) {
    row = r;
    col = c;
    hasMoved = true;
  }

  /** 
   * Allows for a manual setting of whether or not the
   * piece has been moved. Should only be used when checking
   * for a check in a Chess game.
   * @param flag True to set hasMoved to true, false otherwise.
   */
  public void setMoved(boolean flag) {
    hasMoved = flag;
  }

  /**
   * Returns whether or not the piece has been moved.
   * @return  Whether or not the piece has been moved.
   */
  public boolean moved() {
    return hasMoved;
  }

  /**
   * Returns the numerical value of the piece in points.
   * @return The numerical value of the piece.
   */
  public int getPieceValue() {
    return pieceValue;
  }

  /**
   * Checks whether or not the proposed move is valid.
   * @param row The row to send the piece to.
   * @param col The column to send the piece to.
   * @param board The Chess board being played.
   * @return  True if the move is valid, false if not.
   */
  public abstract boolean moveIsValid(int row, int col, Board board);

  /**
   * Returns the String value representation of the piece,
   * based on which player it belongs to.
   * @return The String value of the piece.
   */
  public abstract String getStringValue();
}