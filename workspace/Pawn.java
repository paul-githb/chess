
/** Represents a pawn piece.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Pawn extends Piece {
  /* # INHERITANCE # */

  /**
   * Constructor for the Pawn class. Initializes the
   * ownership of the pawn, as well as its initial row
   * and column values on the chess board.
   * @param playerNumber The ownership ID of the pawn.
   * @param row The initial row.
   * @param col The initial column.
   */
  public Pawn(int playerNumber, int row, int col) {
    super(playerNumber, row, col, 1);
    if (playerNumber == 0)
      setImageIcon("../images/pieces/blackPawn.png");
    else
      setImageIcon("../images/pieces/whitePawn.png");
  }

  /**
   * Checks whether or not the proposed move is valid.
   * A capture can only occur by moving in a simple
   * 1x1 diagonal line across the board. A normal move
   * can only occur by moving forward one tile, or two
   * if the pawn has not been played yet. Any request
   * for a move to a position out of bounds returns false.
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
    
    // Case 4: The outgoing tile is occupied.
    else if (board.getPiece(row, col) != null) {
      /*
       * Make sure the player has moved up or down one tile
       * diagonally, the direction depending on the player ID.
       */
      // Subcase 1: The player is on top.
      if (getPlayerNumber() == 0)
        return (Math.abs(getCol() - col) == 1
          && (row - getRow()) == 1);
      
      // Sub-subcase 2: The player is on bottom.
      else
        return (Math.abs(getCol() - col) == 1
          && (getRow() - row) == 1);
    }
    // Case 5: The outgoing tile is empty, and the pawn has not been moved.
    else if (!moved()) {
      // Subcase 1: The pawn moves two and there's a piece in between.
      if (Math.abs(row - getRow()) == 2 && getCol() - col == 0
        && board.getPiece((row + getRow()) / 2, col) != null)
        return false;
      
      /*
       * Make sure the pawn has moved up or down one or two
       * tiles only, the direction depending on the player ID.
       */
      // Subcase 2: The player is on top.
      else if (getPlayerNumber() == 0)
        return (getCol() - col == 0
          && (row - getRow() == 2 || row - getRow() == 1));
      
      // Subcase 3: The player is on bottom.
      else
        return (getCol() - col == 0
          && (getRow() - row == 2 || getRow() - row == 1));
    }
    // Case 6: The outgoing tile is empty, and the pawn has been moved already.
    else {
      /*
       * Make sure the pawn has moved up or down one tile only,
       * depending on which player the pawn belongs to.
       */
      // Subcase 1: The player is on top.
      if (getPlayerNumber() == 0)
        return (getCol() - col == 0
          && row - getRow() == 1);
      
      // Subcase 2: The player is on bottom.
      else
        return (getCol() - col == 0
          && getRow() - row == 1);
    }
  }

  /**
   * Returns the String value representation of a pawn,
   * based on which player it belongs to.
   * @return The String value of a pawn.
   */
  public String getStringValue() {
    // Case 1: The pawn belongs to the top player.
    if (getPlayerNumber() == 0)
      return "`o`";
    
    // Case 2: The pawn belongs to the bottom player.
    else
      return "'o'";
  }
}