
/** Represents a rook piece.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Rook extends Piece {
  /**
   * Constructor for the Rook class. Initializes the
   * ownership of the rook, as well as its initial row
   * and column values on the chess board.
   * @param playerNumber The ownership ID of the rook.
   * @param row The initial row.
   * @param col The initial column.
   */
  public Rook(int playerNumber, int row, int col) {
    super(playerNumber, row, col, 5);
    if (playerNumber == 0)
      setImageIcon("../images/pieces/blackRook.png");
    else
      setImageIcon("../images/pieces/whiteRook.png");
  }

  /**
   * Checks whether or not the proposed move is valid.
   * A rook can be moved in a straight line vertically
   * or horizontally, and cannot leap over other pieces.
   * Any request for a move to a position out of bounds
   * returns false.
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

    // Case 3: The rook is moved horizontally.
    if (row == getRow()) {
      /*
       * For each of the following conditions, check whether or not
       * a piece exists in between the initial and final position.
       * If so, then the move is not valid.
       */
      // Subcase 1: The rook is moved to the right.
      if (getCol() < col) {
        for (int i = getCol() + 1; i < col; i++)
          if (board.getPiece(row, i) != null)
            return false;
      }

      // Subcase 2: The rook is moved to the left.
      else
        for (int i = col + 1; i < getCol(); i++)
          if (board.getPiece(row, i) != null)
            return false;

      return true;
    }
    // Case 4: The rook is moved vertically.
    else if (col == getCol()) {
      /*
       * For each of the following conditions, check whether or not
       * a piece exists in between the initial and final position.
       * If so, then the move is not valid.
       */
      // Subcase 1: The rook is moved down.
      if (getRow() < row) {
        for (int i = getRow() + 1; i < row; i++)
          if (board.getPiece(i, col) != null)
            return false;
      }

      // Subcase 2: The rook is moved up.
      else
        for (int i = row + 1; i < getRow(); i++)
          if (board.getPiece(i, col) != null)
            return false;

      return true;
    }
    
    // The piece is neither to be moved horizontally
    // or vertically, and so the move is invalid.
    return false;
  }

  /**
   * Returns the String value representation of a rook,
   * based on which player it belongs to.
   * @return The String value of a rook.
   */
  public String getStringValue() {
    // Case 1: The rook belongs to the top player.
    if (getPlayerNumber() == 0)
      return "`H`";
    
    // Case 2: The rook belongs to the bottom player.
    else
      return "'H'";
  }
}