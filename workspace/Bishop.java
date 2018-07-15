
/** Represents a bishop piece.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Bishop extends Piece {
  /**
   * Constructor for the Bishop class. Initializes the
   * ownership of the bishop, as well as its initial row
   * and column values on the chess board.
   * @param playerNumber The ownership ID of the bishop.
   * @param row The initial row.
   * @param col The initial column.
   */
  public Bishop(int playerNumber, int row, int col) {
    super(playerNumber, row, col, 3);
    if (playerNumber == 0)
      setImageIcon("../images/pieces/blackBishop.png");
    else
      setImageIcon("../images/pieces/whiteBishop.png");
  }

  /**
   * Checks whether or not the proposed move is valid.
   * A bishop can be moved strictly in a diagonal path in
   * any direction, and cannot leap over other pieces.
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

    // Case 3: The bishop is moved diagonally.
    if (Math.abs(row - getRow()) == Math.abs(col - getCol())) {
      /*
       * For each of the following conditions, check whether or not
       * a piece exists in between the initial and final position.
       * If so, then the move is not valid.
       */
      int currentRow = getRow();
      int currentCol = getCol();
      // Condition 1: The bishop is moved down.
      if (row > getRow()) {
        // Subcondition 1: The bishop is moved right.
        if (col > getCol()) {
          currentRow++;
          currentCol++;
          while (currentRow < row) {
            if (board.getPiece(currentRow, currentCol) != null)
              return false;
            
            currentRow++;
            currentCol++;
          }
        }
        // Subcondition 2: The bishop is moved left.
        else {
          currentRow++;
          currentCol--;
          while (currentRow < row) {
            if (board.getPiece(currentRow, currentCol) != null)
              return false;
            
            currentRow++;
            currentCol--;
          }
        }
      }
      // Condition 2: The bishop is moved up.
      else {
        // Subcondition 1: The bishop is moved right.
        if (col > getCol()) {
          currentRow--;
          currentCol++;
          while (currentRow > row) {
            if (board.getPiece(currentRow, currentCol) != null)
              return false;
            
            currentRow--;
            currentCol++;
          }
        }
        // Subcondition 2: The bishop is moved left.
        else {
          currentRow--;
          currentCol--;
          while (currentRow > row) {
            if (board.getPiece(currentRow, currentCol) != null)
              return false;
            
            currentRow--;
            currentCol--;
          }
        }
      }

      return true;
    }

    // The bishop is not moved diagonally, and so the move is invalid.
    return false;
  }

  /**
   * Returns the String value representation of a bishop,
   * based on which player it belongs to.
   * @return The String value of a bishop.
   */
  public String getStringValue() {
    // Case 1: The bishop belongs to the top player.
    if (getPlayerNumber() == 0)
      return "`b`";
    
    // Case 2: The bishop belongs to the bottom player.
    else
      return "'b'";
  }
}