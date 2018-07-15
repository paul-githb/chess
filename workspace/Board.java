import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/** Represents a Chess board containing all of the pieces.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Board extends JLabel {
  /* # DATA STRUCTURES # */

  /** A two-dimensional array representing the tiles of the chess board. */
  private Tile[][] board;

  /** The current selected piece, for the graphical version of Chess. */
  private Piece selectedPiece;

  /** An instance of the top player's king piece. */
  private King blackKing;

  /** An instance of the bottom player's king piece. */
  private King whiteKing;

  /** The top player in the game. */
  private Player player0;

  /** The bottom player in the game. */
  private Player player1;

  /**
   * Contructor for the Board class. It creates a board which is represented in the
   * form of a double array which is 8 units up and and 8 units down.
   * @param p0 The player on top.
   * @param p1 The player on bottom.
   */
  public Board(Player p0, Player p1) {
    // Create the board.
    board = new Tile[8][8];
    selectedPiece = null;
    player0 = p0;
    player1 = p1;

    // Add the tile buttons.
    for (int r = 0; r < 8; r++)
      for (int c = 0; c < 8; c++) {
        board[r][c] = new Tile(r, c);
        add(board[r][c]);
      }

    // Set up the pieces.
    reset();
  }

  /** 
   * Returns the King belonging to the bottom player.
   * @return The white King.
   */
  public King getWhiteKing() {
    return whiteKing;
  }

  /** 
   * Returns the King belonging to the top player.
   * @return  The black King.
   */
  public King getBlackKing() {
    return blackKing;
  }

  /**
   * A void type method that clears the board when called, then sets up the 
   * rows of pawns, the rooks, the knights, the bishop and then the king and the queen, in the respective
   * positions.
   */
  public void reset() {
    // Step 0: Clear the board.
    for (int i = 2; i < 6; i++)
      for (int j = 0; j < 8; j++)
        board[i][j].setPiece(null);

    // Step 1: Set up the players' rows of pawns.
    for (int i = 0; i < 8; i++)
      board[1][i].setPiece(new Pawn(0, 1, i));
    for (int i = 0; i < 8; i++)
      board[6][i].setPiece(new Pawn(1, 6, i));

    // Step 2: Set up the rooks on the far corners.
    board[0][0].setPiece(new Rook(0, 0, 0));
    board[0][7].setPiece(new Rook(0, 0, 7));
    board[7][0].setPiece(new Rook(1, 7, 0));
    board[7][7].setPiece(new Rook(1, 7, 7));

    // Step 3: Set up the knights beside the rooks.
    board[0][1].setPiece(new Knight(0, 0, 1));
    board[0][6].setPiece(new Knight(0, 0, 6));
    board[7][1].setPiece(new Knight(1, 7, 1));
    board[7][6].setPiece(new Knight(1, 7, 6));

    // Step 4: Set up the bishops beside the knights.
    board[0][2].setPiece(new Bishop(0, 0, 2));
    board[0][5].setPiece(new Bishop(0, 0, 5));
    board[7][2].setPiece(new Bishop(1, 7, 2));
    board[7][5].setPiece(new Bishop(1, 7, 5));

    // Step 5: Set up the king on the right, queen on the left.
    blackKing = new King(0, 0, 4);
    whiteKing = new King(1, 7, 4);
    board[0][4].setPiece(blackKing);
    board[7][4].setPiece(whiteKing);
    board[0][3].setPiece(new Queen(0, 0, 3));
    board[7][3].setPiece(new Queen(1, 7, 3));
  }

  /** 
   * Returns the Piece that is currently selected.
   * @return The currently selected Piece.
   */
  public Piece getSelectedPiece() {
    return selectedPiece;
  }

  /** 
   * Sets the currently selected Piece to whichever Piece was sent in.
   * @param p The Piece to select.
   */
  public void setSelectedPiece(Piece p) {
    selectedPiece = p;
  }

  /**
   * A void type method that prints the board on the console. It firsts prints the 
   * top border of the chess board. The method then Iterates through each row and prints the pieces
   * respectively. It then prints a line barrier between each of the previously printed rows.
   */
  public void print() {
    // Set up the border character types.
    char verticalBorderType = '|';
    char horizontalBorderType = '-';

    // Print the column numbers.
    for (int i = 0; i < 9; i++)
      System.out.print(" " + i + "  ");
    System.out.print("\n   ");

    // Print the top border.
    for (int i = 0; i < 33; i++)
      System.out.print(horizontalBorderType);
    System.out.println();

    for (int row = 0; row < 8; row++) {
      // Print the row number.
      System.out.print(" " + (row + 1) + " ");
      System.out.print(verticalBorderType);

      // Print the pieces: blank if empty, or the appropriate character if not.
      for (int col = 0; col < 8; col++) {
        if (board[row][col].getPiece() == null)
          System.out.print("   ");
        else
          System.out.print(board[row][col].getPiece().getStringValue());
        System.out.print(verticalBorderType);
      }
      System.out.println();

      // Print a line between rows.
      System.out.print("   ");
      for (int i = 0; i < 33; i++)
        System.out.print(horizontalBorderType);
      System.out.println();
    }
  }

  /** 
   * Determines whether or not moving the specified Piece(s) to a certain
   * Tile(s) will result in the player's King to be in check.
   * @param   piece The piece to check movement of.
   * @param   outRow The row to which the piece is to be moved.
   * @param   outCol The column to which the piece is to be moved.
   * @param piece2 The second piece to be moved.
   * @param   outRow2 The row to which the second piece is to be moved.
   * @param   outCol2 The column to which the second piece is to be moved.
   * @return  Whether or not the movement of pieces will result in a check.
   */
  public boolean checkExists(Piece piece, int outRow, int outCol, Piece piece2, int outRow2, int outCol2) {
    // Retrieve the necessary prelimilary information.
    int playerID = piece.getPlayerNumber();
    King king = (playerID == 0) ? blackKing : whiteKing;

    // Retrieve the initial state of the piece to move.
    int inRow = piece.getRow();
    int inCol = piece.getCol();
    boolean pieceMoved = piece.moved();

    // Move the piece to the desired location and retrieve the initial state of the piece that was there.
    Piece prev = movePiece(inRow, inCol, outRow, outCol);
    boolean prevMoved = (prev != null && prev != piece) ? prev.moved() : false;

    // Optional: Moving a second piece for castling.
    int inRow2 = 0, inCol2 = 0;
    Piece prev2 = null;
    boolean piece2Moved = false, prev2Moved = false;

    if (piece2 != null) {
      // Retrieve the initial state of the piece to move.
      inRow2 = piece2.getRow();
      inCol2 = piece2.getCol();
      piece2Moved = piece2.moved();

      // Move the piece to the desired location and retrieve the initial state of the piece that was there.
      prev2 = movePiece(inRow2, inCol2, outRow2, outCol2);
      prev2Moved = (prev2 != null && prev2 != piece2) ? prev2.moved() : false;
    }

    /*
     * Check each piece belonging to the opponent.
     * Then, for each of them, see if they can capture the player's king.
     * If so, then a check exists.
     * Regardless of the outcome, reset the pieces back to normal.
     */
    for (Tile[] row : board)
      for (Tile tile : row) {
        Piece comp = tile.getPiece();
        if (comp != null && comp.getPlayerNumber() != playerID)
          if (comp.moveIsValid(king.getRow(), king.getCol(), this)) {
            resetLocations(piece, inRow, inCol, pieceMoved, prev, outRow, outCol, prevMoved);
            if (piece2 != null)
              resetLocations(piece2, inRow2, inCol2, piece2Moved, prev2, outRow2, outCol2, prev2Moved);
            return true;
          }
      }
    resetLocations(piece, inRow, inCol, pieceMoved, prev, outRow, outCol, prevMoved);
    if (piece2 != null)
      resetLocations(piece2, inRow2, inCol2, piece2Moved, prev2, outRow2, outCol2, prev2Moved);
    return false;
  }

  /** 
   * Moves the pieces altered by the checkExists() method back to their original locations on the board.
   * @param p1 The first Piece affected.
   * @param r1 The row of the first Piece affected.
   * @param c1 The column of the first Piece affected.
   * @param b1 The initial movement state of the first Piece.
   * @param p2 The second Piece affected.
   * @param r2 The row of the second Piece affected.
   * @param c2 The column of the second Piece affected.
   * @param b2 The initial movement state of the second Piece.
   */
  private void resetLocations(Piece p1, int r1, int c1, boolean b1, Piece p2, int r2, int c2, boolean b2) {
    // Reset the original positions of both pieces.
    setPiece(p1, r1, c1);
    setPiece(p2, r2, c2);

    // Reset the initial movement state of both pieces.
    p1.setMoved(b1);
    if (p2 != null && p2 != p1)
      p2.setMoved(b2);
  }

  /** 
   * Determines whether or not moving the specified Piece to a certain
   * Tile will result in the player's King to be in check.
   * @param   piece The piece to check the movement of.
   * @param   outRow The row to which the piece is to be moved
   * @param   outCol The column to which the piece is to be moved
   * @return  Whether or not the movement of the Piece will result in a check.
   */
  public boolean checkExists(Piece piece, int outRow, int outCol) {
    return checkExists(piece, outRow, outCol, null, 0, 0);
  }

  /**
   * Determines whether or not the current player is under a checkmate.
   * A checkmate occurs if the player cannot move out of a check.
   * @return  Whether or not the current Player is under checkmate.
   */
  public boolean isCheckmate() {
    // Retrieve the necessary prelimilary values for the test.
    King king = (Game.getTurn() == 0) ? blackKing : whiteKing; // the King piece
    int row = king.getRow(); // the King's row
    int col = king.getCol(); // the King's column
    int kingID = king.getPlayerNumber(); // the player's ID

    /* 
     * Case 1: The king is not under check.
     * See if any other piece belonging to the player is able to move.
     * If so, then the player is not under a checkmate.
     */
    if (!checkExists(king, row, col)) {
      for (int r = 0; r < 8; r++)
        for (int c = 0; c < 8; c++) {
          Piece p = board[r][c].getPiece();
          if (p != null && p.getPlayerNumber() == kingID) {
            int startRow = (p.getRow() > 1) ? p.getRow() - 2 : 0;
            int endRow = (p.getRow() < 6) ? p.getRow() + 2 : 7;
            for (int i = startRow; i <= endRow; i++) {
              int startCol = (p.getCol() > 1) ? p.getCol() - 2 : 0;
              int endCol = (p.getCol() < 6) ? p.getCol() + 2 : 7;
              for (int j = startCol; j <= endCol; j++)
                if (p.moveIsValid(i, j, this) && !checkExists(p, i, j))
                  return false;
            }
          }
        }
    }

    /*
     * Case 2: The king is under a check.
     * See if the king can move itself out of check.
     * To do so, check each of the 8 tiles around the king.
     * If the king can move itself out of check,
     * then the player is not under a checkmate.
     */
    for (int r = row - 1; r <= row + 1; r++)
      for (int c = col - 1; c <= col + 1; c++)
        if ((r != row || c != col) && king.moveIsValid(r, c, this) && !checkExists(king, r, c))
          return false;

    /*
     * Case 3: The king is under a check, and cannot move itself out of check.
     * See if any other of the player's pieces can move in order to block the check.
     * To do so, go through each of the player's pieces on the board,
     * then go through each of the possible valid moves that piece can make.
     * If any move results in the king not being under check,
     * then the player is not under a checkmate.
     */
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        Piece currentPiece = board[r][c].getPiece();
        if (currentPiece != king && currentPiece != null && currentPiece.getPlayerNumber() == kingID)
          for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
              if (currentPiece.moveIsValid(i, j, this) && !checkExists(currentPiece, i, j))
                return false;
      }
    }
    return true;
  }

  /**
   * Determines whether or not the current Player can castle on the specified side.
   * The left side deals with the Player's King and the left Rook, whereas the right
   * side deals with the Player's King and the right Rook.
   * @param side The corner on which to check the validity of a castle.
   * @return Whether or not the current Player can perform a castle on the specified side.
   */
  public boolean castleIsValid(String side) {
    int row = (Game.getTurn() == 0) ? 0 : 7; // The row of both pieces.
    King king = (Game.getTurn() == 0) ? blackKing : whiteKing; // The King to castle.
    Piece rook = (side.equals("left")) ? getPiece(row, 0) : getPiece(row, 7); // The Rook to castle.

    /*
     * Make sure that the corner piece exists and is a Rook.
     * Then make sure neither piece has moved.
     * Then, make sure the following two conditions hold:
     * (i) There exists no piece in between those pieces.
     * (ii) There will not exist a check as a result of castling.
     * If the above conditions hold, then a castle is valid.
     * Otherwise, a castle is not valid.
     */
    if (rook != null && rook instanceof Rook)
      if (!king.moved() && !rook.moved()) {
        int rookCol = rook.getCol(); // The column of the Rook.
        int kingCol = king.getCol(); // The column of the King.
        int startCol = (rookCol < kingCol) ? rookCol + 1 : kingCol + 1;
        int endCol = (rookCol < kingCol) ? kingCol - 1 : rookCol - 1;

        for (int col = startCol; col <= endCol; col++)
          if (getPiece(row, col) != null)
            return false;

        int direction = (side.equals("left")) ? -1 : 1;
        int magnitude = (side.equals("left")) ? 3 : 2;
        return !checkExists(king, row, kingCol + direction * 2, rook, row, rookCol - direction * magnitude);
      }

    return false;
  }

  /**
   * Determines whether or not a certain piece is deserving of a promotion.
   * A promotion occurs when a Player's pawn makes it over to the other end
   * of the board, and must immediately be replaced by a Queen, Rook, Knight,
   * or Bishop.
   * @param piece The piece to check for validity of a promotion.
   * @return Whether or not the piece should be promoted.
   */
  public boolean promotionIsValid(Piece piece) {
    if (piece instanceof Pawn && (piece.getRow() == 0 || piece.getRow() == 7))
      return true;

    return false;
  }

  /**
   * Prompts the Player with a valid promotion to pick which Piece to promote his or her Pawn to.
   * @param piece The piece to promote to a higher rank.
   */
  public void promote(Piece piece) {
    int row = piece.getRow(); // The row of the piece to promote.
    int col = piece.getCol(); // The column of the piece to promote.
    int id = piece.getPlayerNumber(); // Who the piece belongs to.

    String[] options = {"Queen", "Knight", "Rook", "Bishop"};
    String decision = (String) JOptionPane.showInputDialog (new JFrame(),
                      "Choose a piece to " +
                      "replace your pawn:",
                      "Promotion",
                      JOptionPane.PLAIN_MESSAGE,
                      null,
                      options,
                      "Queen");
    switch (decision) {
      case "Queen" :  setPiece(new Queen(id, row, col), row, col);
      break;
      case "Knight" : setPiece(new Knight(id, row, col), row, col);
      break;
      case "Rook" : setPiece(new Rook(id, row, col), row, col);
      break;
      case "Bishop" : setPiece(new Bishop(id, row, col), row, col);
      break;
    }
  }

  /**
   * Performs a castle move based on the corner specified by the parameters.
   * If the side specified is left, then the castle is performed on a left
   * corner; otherwise, it is performed on a right corner. If the playerID
   * specified is 0, then the castle is performed on a top corner; otherwise,
   * it is performed on a bottom corner.
   * @param side The side of the pieces to castle, left or right.
   * @param playerID The Player for which to perform the castle.
   */
  public void performCastle(String side, int playerID) {
    King king = (playerID == 0) ? blackKing : whiteKing;
    int kingRow = king.getRow();
    int kingCol = king.getCol();
    int rookRow = kingRow;
    int rookCol = (side.equals("left")) ? 0 : 7;
    int direction = (side.equals("left")) ? -1 : 1;
    int magnitude = (side.equals("left")) ? 3 : 2;

    // Move the King and Rook to the appropriate locations.
    movePiece(kingRow, kingCol, kingRow, kingCol + direction * 2);
    movePiece(rookRow, rookCol, rookRow, rookCol - direction * magnitude);
  }

  /**
   * This method returns the piece in the position that was prompted, it returns a 
   * null value if the value that was inputed is invalid (if the value is greater than 7 or lesser
   * than 0 for either the row or the column). 
   * @param row The row of the piece to retrieve.
   * @param col The column of the piece to retrieve.
   * @return The Piece at the specified row and column.
   */
  public Piece getPiece(int row, int col) {
    if (row < 0 || row > 7 || col < 0 || col > 7)
      return null;

    return board[row][col].getPiece();
  }

  /**
   * This method moves a piece from one row to another.
   * The first pair of parameters specifies the position of the piece
   * to move, and the second pair specifies the position to move it to.
   * The method returns the piece that was replaced, if applicable.
   * @param irow The row of the piece to move.
   * @param icol The column of the piece to move.
   * @param frow The row of the Tile to move the piece to.
   * @param fcol The column of the Tile to move the piece to.
   * @return: The piece that was removed from the second Tile.
   */
  public Piece movePiece(int irow, int icol, int frow, int fcol) {
    // Move the piece in the first tile to the second tile.
    Piece pieceToMove = board[irow][icol].getPiece();
    Piece pieceToReplace = board[frow][fcol].setPiece(pieceToMove);
    pieceToMove.setPosition(frow, fcol);

    /*
     * Make sure you're not moving the piece to the same tile.
     * If that's not the case, then set the previous tile empty.
     */
    if (irow != frow || icol != fcol)
      board[irow][icol].setPiece(null);

    // Return the piece that was removed from the previous tile.
    return pieceToReplace;
  }

  /** 
   * Allows a piece outside of the Chess board to take on a position
   * on the board, then returns the piece previously in that position.
   * @param   piece The piece to add to the board.
   * @param   row The row of the position in which to insert the Piece.
   * @param   col The column of the position in which to insert the Piece.
   * @return  The Piece that was removed from the specified Tile.
   */
  private Piece setPiece(Piece piece, int row, int col) {
    // Insert the piece into the desired tile given by the parameters.
    Piece pieceToReplace = board[row][col].setPiece(piece);
    if (piece != null)
      piece.setPosition(row, col);

    // Return the piece that was removed from its position.
    return pieceToReplace;
  }

  /**
   * Handles the changes in settings of the board based on which Tile was selected.
   * Takes in a Tile that was clicked and makes appropriate changes to the board
   * and player settings based on the current state of the board.
   * @param tile The tile that was selected.
   */
  public void handleRequest(Tile tile) {
    /*
     * Case 1: No piece is currently selected.
     * If so, then make sure the following two conditions hold:
     * (i) The given tile is not empty.
     * (ii) The piece on that tile belongs to the player whose turn it is currently.
     * If both of these conditions hold, then make that piece the selected piece.
     */
    if (selectedPiece == null) {
      Piece p = tile.getPiece();

      if (p != null && p.getPlayerNumber() == Game.getTurn()) {
        setSelectedPiece(p);
        repaint();
      }
    }
    /*
     * Case 2: A piece is currently selected.
     * If so, then there are three possible situations:
     * [I] The piece on the given tile belongs to the same player as the selected piece.
     * [II] Moving the selected piece to that tile is valid and does not result in a check.
     * [III] The conditions defined in [II] do not hold for the currently selected piece.
     * After handling each of these situations, repaint the board with the appropriate updates.
     * Also, check for a winner if it hasn't been determined in situation [II] already.
     */
    else {
      // Retrieve the necessary prelimilary conditions to handle each situation.
      Piece p = tile.getPiece();
      int outRow = tile.getRow();
      int outCol = tile.getCol();
      int inRow = selectedPiece.getRow();
      int inCol = selectedPiece.getCol();

      /*
       * Situation [I]: The piece on the given tile belongs to the same player as the selected piece.
       * If so, then set that piece to be the new selected piece.
       */
      if (p != null && p.getPlayerNumber() == Game.getTurn())
        setSelectedPiece(p);

      /*
       * Situation [II]: Moving the selected piece to that tile is valid and does not result in a check.
       * If so, then perform the following tasks:
       * > Move the selected piece to the requested tile.
       * > Save the captured piece if applicable.
       * > Promote the selected piece if applicable.
       * > Turn off any CastleButtons if applicable.
       * > Switch players and deselect the currently selected piece.
       * > Look for a checkmate, meaning the recent player has won.
       * > If no checkmate occurs, turn on any CastleButtons if applicable.
       */
      else if (selectedPiece.moveIsValid(outRow, outCol, this) && !checkExists(selectedPiece, outRow, outCol)) {
        movePiece(inRow, inCol, outRow, outCol);

        if (p != null)
          Game.getPlayer(Game.getTurn()).addCapturedPiece(p);

        if (promotionIsValid(selectedPiece))
          promote(selectedPiece);

        Game.setCastleButtonVisible("left", Game.getTurn(), false);
        Game.setCastleButtonVisible("right", Game.getTurn(), false);

        Game.incrementTurn();
        setSelectedPiece(null);

        if (isCheckmate()) {
          int result = JOptionPane.showConfirmDialog (null,
                       "Would you like to play again?",
                       "Checkmate! " +
                       Game.getPlayer(1 - Game.getTurn()).getName()
                       + " wins!",
                       JOptionPane.YES_NO_OPTION);
          if (result == JOptionPane.YES_OPTION)
            Game.reset();
        } else {
          if (castleIsValid("left"))
            Game.setCastleButtonVisible("left", Game.getTurn(), true);
          if (castleIsValid("right"))
            Game.setCastleButtonVisible("right", Game.getTurn(), true);
        }
      }
      /*
       * Situation [III]: The conditions defined in [II] do not hold for the currently selected piece.
       * If so, then the requested move is not valid. So, deselect the currently selected piece.
       */
      else
        setSelectedPiece(null);

      repaint();

      if (Game.getStatus() != -1) {
        int result = JOptionPane.showConfirmDialog (null,
                     "Would you like to play again?",
                     Game.getPlayer(Game.getStatus()).getName()
                     + " wins!",
                     JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
          Game.reset();
      }
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(Game.getScale(), Game.getScale());
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int u = Game.getUnit();
    Graphics2D g2d = (Graphics2D) (g);

    g2d.setStroke(new BasicStroke(Game.getScale() / 200));
    g2d.setColor(Color.BLACK);

    // [1] Display dark horizontal bars on the top and bottom of the screen.
    g2d.fillRect(0, 0, Game.getScale(), u / 2);
    g2d.fillRect(0, 23 * u / 2, Game.getScale(), u / 2);

    // [2] Display the chess board, making sure the tile with the selected piece is green.
    for (int r = 0; r < 8; r++)
      for (int c = 0; c < 8; c++) {
        Color color;

        if (selectedPiece != null && r == selectedPiece.getRow() && c == selectedPiece.getCol())
          color = new Color(173, 255, 47);
        else if (r % 2 == 0)
          color = (c % 2 == 0) ? new Color(255, 222, 173) : new Color(205, 133, 63);
        else
          color = (c % 2 == 0) ? new Color(205, 133, 63) : new Color(255, 222, 173);

        g2d.setColor(color);
        g2d.fillRect(u * (c + 2), u * (r + 2), u, u);
      }

    // [3] Display the light turn indicators for each player.
    for (int n = 0; n <= 1; n++) {
      if (Game.getTurn() == n)
        g2d.setColor(Color.GREEN);
      else
        g2d.setColor(Color.RED);

      g2d.fillOval(47 * u / 8, 9 * u * n + 11 * u / 8, u / 4, u / 4);
      g2d.setColor(Color.BLACK);
      g2d.draw(new Ellipse2D.Float(47 * u / 8, 9 * u * n + 11 * u / 8, u / 4, u / 4));
      g2d.draw(new Rectangle2D.Float(11 * u / 2, 9 * u * n + 5 * u / 4, u, u / 2));
    }

    // [4] Display the captured piece sets for each player.
    player0.paintComponent(g2d);
    player1.paintComponent(g2d);
  }
}