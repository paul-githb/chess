import java.awt.Graphics;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JLabel;

/** Represents a Chess player.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Player extends JLabel {
  /* # SORTING # */

  /** The total number of points the player has, based on piece values. */
  private int points;

  /** The set of captured pieces belonging to the opponent. */
  private ArrayList<Piece> capturedPieces;

  /** The player's set name. */
  private String name;

  /** The player's ID: 0 for top player, 1 for bottom player. */
  private int playerNumber;

  /**
   * This is the constructor for the Player class, when called it creates a new 
   * player object. This constructor provides the players with "0" points and initiates an arrayList
   * of Pieces which holds all the captured pieces. It also defaults the player's name to "Player",
   * uses the player number that was inputed by the user. 
   * @param pn The player ID of the player.
   */ 
  public Player(int pn) {
    playerNumber = pn;
    name = "Player";
    reset();
  }

  /**
   * This method returns the player's name when called.
   * @return The name of the player.
   */
  public String getName() {
    return name;
  }

  /** 
   * Returns the number of points the player has based on the Pieces captured.
   * @return The total number of points.
   */
  public int getPoints() {
    return points;
  }

  /** 
   * Method which takes in a Board and a Player, then prompts
   * the player to make a move until a correct input is provided.
   * @param board The chess board being played.
   * @return A double array holding the initial and final positions of the move.
   */
  public int[][] getMove(Board board) {
    int[][] move      = new int[2][2];
    boolean done      = false;
    Scanner scan      = new Scanner(System.in);
    Piece pieceToMove = null;

    while (!done) {
      // Prints all the necessary information.
      System.out.println();
      Game.printContents();
      System.out.println("\nIt is now " + getName() + "'s turn!");

      try {
        System.out.println("\nSELECT YOUR PIECE");
        System.out.println("*~*~*~*~*~*~*~*~*");

        // Ask for current piece row.
        System.out.print("Row? ");
        move[0][0] = scan.nextInt() - 1;

        // Ask for current piece column.
        System.out.print("Column? ");
        move[0][1] = scan.nextInt() - 1;

        System.out.println("\nSELECT LOCATION");
        System.out.println("*~*~*~*~*~*~*~*");

        // Ask for outgoing tile row.
        System.out.print("Row? ");
        move[1][0] = scan.nextInt() - 1;

        // Ask for outgoing tile column.
        System.out.print("Column? ");
        move[1][1] = scan.nextInt() - 1;

        // Retrive the piece existing on the requested tile.
        pieceToMove = board.getPiece(move[0][0], move[0][1]);

        // Case 1: The tile contains no piece.
        if (pieceToMove == null)
          System.out.println("\nYou can't move nothing!");

        // Case 2: The tile doesn't contain the player's own piece.
        else if (pieceToMove.getPlayerNumber() != playerNumber)
          System.out.println("\nYou can only move your own piece!");

        // Case 3: The tile contains the player's piece.
        else {
          // Subcase 1: The move is invalid.
          if (!pieceToMove.moveIsValid(move[1][0], move[1][1], board))
            System.out.println("\nInvalid location!");

          // Subcase 2: The move puts the player's king in check.
          else if (board.checkExists(pieceToMove, move[1][0], move[1][1]))
            System.out.println("\nYou can't leave your king in check!");

          // Subcase 3: The move is valid.
          else
            done = true;
        }
      } catch (InputMismatchException e) {
        scan = new Scanner(System.in);
        System.out.println("\nNot a valid key!");
      }
    }
    return move;
  }

  /**
   * This is a void type method that allows the player to change the name of the player
   * object when called.
   * @param n The intended name.
   */
  public void setName(String n) {
    name = n;
  }

  /** 
   * Adds a piece to the set of captured pieces.
   * Then, re-sorts the set and adds the value of the piece
   * to the total number of points.
   * @param piece The captured piece to add to the set.
   */
  public void addCapturedPiece(Piece piece) {
    // Add the piece to the set.
    capturedPieces.add(piece);

    // Sort the set of pieces.
    sortCapturedPieces();

    // Add the point value of the piece to the total.
    points += piece.getPieceValue();
  }

  /** 
   * Sorts all of the pieces in the set of captured pieces
   * in ascending order based on their value and rank. Although
   * bishops and knights have the same value, a piece belonging
   * to the knight rank goes before any bishop-ranked pieces.
   * The king is the first piece in the sorted set, if it exists.
   */
  public void sortCapturedPieces() {
    // Apply insertion sort to the set of pieces.
    for (int i = 1; i < capturedPieces.size(); i++) {
      int current = i;

      /* Note: If there exists a bishop to the left of a knight,
      swap those two pieces to bring the knight over to the left. */
      while (current > 0 && (capturedPieces.get(current - 1).getPieceValue() >
        capturedPieces.get(current).getPieceValue() ||
        (capturedPieces.get(current - 1) instanceof Bishop &&
            capturedPieces.get(current) instanceof Knight))) {
        swapCapturedPieces(current - 1, current);
        current--;
      }
    }
  }

  /** 
   * Swaps two pieces in the set of captured pieces based on
   * their current indices.
   * @param 	pos1 The index of the first Piece to swap.
   * @param 	pos2 the index of the second Piece to swap.
   */
  private void swapCapturedPieces(int pos1, int pos2) {
    Piece temp = capturedPieces.get(pos1);
    capturedPieces.set(pos1, capturedPieces.get(pos2));
    capturedPieces.set(pos2, temp);
  }

  /** 
   * Prints the set of captured pieces to the console. To be
   * used for the text-based version of the Chess game.
   */
  public void printCapturedPieces() {
    String label = name + "'s CAPTURED PIECES: ";

    // Case 1: No pieces have been captured.
    if (capturedPieces.size() == 0)
      System.out.println(label + "None");

    // Case 2: The player has captured some pieces.
    else {
      // Print the upper border.
      for (int i = 0; i < label.length(); i++)
        System.out.print(' ');
      for (int i = 0; i < 4 * capturedPieces.size() + 1; i++)
        System.out.print('-');

      // Print the ownership, the captured pieces, and the score.
      System.out.print("\n" + label + "|");
      for (int i = 0; i < capturedPieces.size(); i++)
        System.out.print(capturedPieces.get(i).getStringValue() + "|");
      System.out.println("     Score: " + points);

      // Print the lower border.
      for (int i = 0; i < label.length(); i++)
        System.out.print(' ');
      for (int i = 0; i < 4 * capturedPieces.size() + 1; i++)
        System.out.print('-');
      System.out.println();
    }
  }

  /** 
   * Resets the player's points to zero and wipes the
   * player's set of captured pieces.
   */
  public void reset() {
    points = 0;
    capturedPieces = new ArrayList<Piece>();
  }

  /** 
   * Returns whether or not the player has captured the
   * other player's king, i.e. the player is currently
   * holding the opponent's king in the set of captured pieces.
   * @return 	Whether or not the Player has captured the King.
   */
  public boolean hasKing() {
    if (capturedPieces.size() == 0)
      return false;

    return (capturedPieces.get(0) instanceof King);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int x, y; // Coordinates of the image on the content pane.
    int u = Game.getUnit();
    int x0 = u * (9 * playerNumber + 1); // Initial x-coordinate.
    int y0 = u * (7 * playerNumber + 2); // Initial y-coordinate.
    int xs = u * (2 * playerNumber - 1); // x- Scale factor.
    int ys = -1 * xs; // y- Scale factor.

    for (int n = 0; n < capturedPieces.size(); n++) {
      x = x0 + (n / 8) * xs;
      y = y0 + (n % 8) * ys;
      capturedPieces.get(n).getImageIcon().paintIcon(this, g, x, y);
    }
  }
}