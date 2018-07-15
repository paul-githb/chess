
/** Represents a king piece.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class King extends Piece {
  /**
   * Constructor for the King class. Initializes the
   * ownership of the king, as well as its initial row
   * and column values on the chess board.
   * @param playerNumber The ownership ID of the king.
   * @param row The initial row.
   * @param col The initial column.
   */
  public King(int playerNumber, int row, int col) {
    super(playerNumber, row, col, 0);
    if (playerNumber == 0)
      setImageIcon("../images/pieces/blackKing.png");
    else
      setImageIcon("../images/pieces/whiteKing.png");
  }

  /**
   * Checks whether or not the proposed move is valid.
   * The King can only move within one distance of its
   * current position, and trivially cannot leap over
   * other pieces. If the king is in check, it must be
   * moved in the next turn. Any request for a move to
   * a position out of bounds returns false.
   * @param row The row to send the piece to.
   * @param col The column to send the piece to.
   * @param board The Chess board being played.
   * @return  True if the move is valid, false if not.
   */
  public boolean moveIsValid(int row, int col, Board board) {
    // Case 1: The piece isn't moved.
    if (row == getRow() && col == getCol())
      return false;
    
    // Case 2: The outgoing tile is out of bounds.
    else if (row < 0 || row > 7 || col < 0 || col > 7)
      return false;
    
    // Case 3: The outgoing tile contains a piece belonging to the same player.
    else if (board.getPiece(row, col) != null &&
    board.getPiece(row, col).getPlayerNumber() == getPlayerNumber())
      return false;

    // Determines how far the piece has moved
    // in the horizontal and vertical direction.
    int changeInX = Math.abs(col - getCol());
    int changeInY = Math.abs(row - getRow());

    // If the king only moves one tile in either direction, the move is valid.
    return changeInX <= 1 && changeInY <= 1;
  }

  /**
   * Returns the String value representation of a king,
   * based on which player it belongs to.
   * @return The String value of a king.
   */
  public String getStringValue() {
    // Case 1: The king belongs to the top player.
    if (getPlayerNumber() == 0)
      return "`K`";
    
    // Case 2: The king belongs to the bottom player.
    else
      return "'K'";
  }
}