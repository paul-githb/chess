
/** Represents a knight piece.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Knight extends Piece {
  /**
   * Constructor for the Knight class. Initializes the
   * ownership of the knight, as well as its initial row
   * and column values on the chess board.
   * @param playerNumber The ownership ID of the knight.
   * @param row The initial row.
   * @param col The initial column.
   */
  public Knight(int playerNumber, int row, int col) {
    super(playerNumber, row, col, 3);
    if (playerNumber == 0)
      setImageIcon("../images/pieces/blackKnight.png");
    else
      setImageIcon("../images/pieces/whiteKnight.png");
  }

  /** 
   * Checks whether or not the proposed move is valid. 
   * A knight moves in an L-shape, i.e. 2 tiles in one
   * direction and 1 tile in a perpendicular direction.
   * A knight has the special property in that it can
   * leap over other pieces. Any request for a move to 
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

    return ((changeInX == 1 && changeInY == 2) || (changeInX == 2 && changeInY == 1));
  }

  /**
   * Returns the String value representation of a knight,
   * based on which player it belongs to.
   * @return The String value of a knight.
   */
  public String getStringValue() {
    // Case 1: The knight belongs to the top player.
    if (getPlayerNumber() == 0)
      return "`k`";

    // Case 2: The knight belongs to the bottom player.
    else
      return "'k'";
  }
}