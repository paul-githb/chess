
/** Represents a queen piece.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Queen extends Piece {
  /**
   * Constructor for the Queen class. Initializes the
   * ownership of the queen, as well as its initial row
   * and column values on the chess board.
   * @param playerNumber The ownership ID of the queen.
   * @param row The initial row.
   * @param col The initial column.
   */
  public Queen(int playerNumber, int row, int col) {
    super(playerNumber, row, col, 9);
    if (playerNumber == 0)
      setImageIcon("../images/pieces/blackQueen.png");
    else
      setImageIcon("../images/pieces/whiteQueen.png");
  }

  /**
   * Checks whether or not the proposed move is valid.
   * A queen can be moved either in a straight line
   * vertically (up or down) or horizontally (left or
   * right), or strictly diagonally. This piece cannot
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

    // Case 4: The queen is moved vertically.
    else if (getCol() == col) {
      /*
       * For each of the following conditions, check whether or not
       * a piece exists in between the initial and final position.
       * If so, then the move is not valid.
       */
      // Condition 1: The queen is moved down.
      if (row > getRow()) {
        for (int i = getRow() + 1; i < row; i++)
          if (board.getPiece(i, col) != null)
            return false;
      }
      // Condition 2: The queen is moved up.
      else
        for (int i = row + 1; i < getRow(); i++)
          if (board.getPiece(i, col) != null)
            return false;

      return true;
    }
    // Case 5: The queen is moved horizontally.
    else if (getRow() == row) {
      /*
       * For each of the following conditions, check whether or not
       * a piece exists in between the initial and final position.
       * If so, then the move is not valid.
       */
      // Condition 1: The queen is moved right.
      if (col > getCol()) {
        for (int i = getCol() + 1; i < col; i++)
          if (board.getPiece(row, i) != null)
            return false;
      }
      // Condition 2: The queen is moved left.
      else
        for (int i = col + 1; i < getCol(); i++)
          if (board.getPiece(row, i) != null)
            return false;

      return true;
    }
    // Case 6: The queen is moved diagonally.
    else if (Math.abs(row - getRow()) == Math.abs(col - getCol())) {
      /*
       * For each of the following conditions, check whether or not
       * a piece exists in between the initial and final position.
       * If so, then the move is not valid.
       */
      int currentRow = getRow();
      int currentCol = getCol();
      // Subcase 1: The queen moves down.
      if (row > getRow()) {
        // Sub-subcase 1: The queen moves right.
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
        // Sub-subcase 2: The queen moves left.
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
      // Subcase 2: The queen moves up.
      else {
        // Sub-subcase 1: The queen moves right.
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
        // Sub-subcase 2: The queen moves left.
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

    // Since the queen's move is not horizontal, vertical, or diagonal, it's invalid.
    return false;
  }

  /**
   * Returns the String value representation of a queen,
   * based on which player it belongs to.
   * @return The String value of a queen.
   */
  public String getStringValue() {
    // Case 1: The queen belongs to the top player.
    if (getPlayerNumber() == 0)
      return "`Q`";
    
    // Case 2: The queen belongs to the bottom player.
    else
      return "'Q'";
  }
}