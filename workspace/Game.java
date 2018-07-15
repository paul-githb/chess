import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/** Represents a full game of Chess.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class Game {
  /** The window of the game. */
  private JFrame frame;

  /** The chess board carrying all of the pieces of the game. */
  private static Board board;

  /** The top player in the game. */
  private static Player player0;

  /** The bottom player in the game. */
  private static Player player1;

  /** The current turn number: 0 for top player, 1 for bottom player. */
  private static int turnNumber;

  /** The castle buttons for both players. */
  private static CastleButton[] castleButtons;

  /** The scaling value of the frame in pixels*. */
  private static int SCALE;

  /** Button that resets the board and game. */
  private JButton resetButton;

  /**
   * The constructor for the Game class. Creates the Game object
   * which holds all of the players, the board, and the frame.
   * @param s The scale/dimensions of the frame.
   */
  public Game(int s) {
    // Constructs the window.
    frame = new JFrame();

    // Sets the scaling value.
    SCALE = s;

    // Sets the turn number.
    turnNumber = 1;

    // Instantiates the players
    player0 = new Player(0);
    player1 = new Player(1);

    // Sets the players' names based on their location on the board.
    player0.setName("TOP");
    player1.setName("BOTTOM");

    // Creates the castle buttons.
    castleButtons = new CastleButton[4];
    for (int n = 0; n < 4; n++)
      castleButtons[n] = new CastleButton((n % 2 == 0) ? "left" : "right", n / 2);

    // Creates the reset button.
    int u = getUnit();
    int th = getScale() / 200;

    resetButton = new JButton("Reset");
    resetButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, u / 3));
    resetButton.setOpaque(false);
    resetButton.setContentAreaFilled(false);
    resetButton.setBorder(BorderFactory.createMatteBorder(th, th, th, th, Color.BLACK));
    resetButton.setBounds(u / 4, 3 * u / 4, 3 * u / 2, u / 2);

    resetButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        Game.reset();
        frame.repaint();
      }
      
    });

    // Instantiates the chess board
    board = new Board(player0, player1);
  }

  /**
   * Returns the width and height of the window frame.
   * @return The width and height of the frame.
   */
  public static int getScale() {
    return SCALE;
  }

  /**
   * Returns one unit size of the game. The frame is divided
   * up into 12x12 squares, and so one unit is one-twelfth of
   * the size of the frame.
   * @return The unit size of the frame.
   */
  public static int getUnit() {
    return getScale() / 12;
  }

  /** 
   * Prints the board and the pieces captured by the top player
   * and the bottom player. Used in the text-based chess game.
   */
  public static void printContents() {
    // Prints the chess board.
    board.print();

    // Prints the captured pieces of both players in order.
    player0.printCapturedPieces();
    player1.printCapturedPieces();
  }

  /** 
   * Resets the board to the initial setup, and also wipes
   * all of the captured pieces held by both players.
   */
  public static void reset() {
    player0.reset();
    player1.reset();
    board.reset();
    board.setSelectedPiece(null);
    turnNumber = 1;
  }

  /**
   * Sets a CastleButton on or off, specified by the parameters.
   * If "left" or "right" is sent in, a left or right CastleButton will
   * be turned on or off, respectively. The player ID sent in determines
   * whether the top or the bottom CastleButton is set on or off.
   * @param side The side of the button to set on or off, left or right.
   * @param n The player ID of the button to set on or off.
   * @param flag True to set the button on, false to turn it off.
   */
  public static void setCastleButtonVisible(String side, int n, boolean flag) {
    if (side.equals("left")) {
      if (n == 0)
        castleButtons[0].setVisible(flag);
      else if (n == 1)
        castleButtons[2].setVisible(flag);
    }
    else if (side.equals("right")) {
      if (n == 0)
        castleButtons[1].setVisible(flag);
      else if (n == 1)
        castleButtons[3].setVisible(flag);
    }
  }

  /** 
   * Switches the turn from top player to bottom or bottom
   * player to top.
   */
  public static void incrementTurn() {
    turnNumber = (turnNumber + 1) % 2;
  }

  /** 
   * Returns the ID of the player whose turn it is currently.
   * @return The current turn.
   */
  public static int getTurn() {
    return turnNumber;
  }

  /** 
   * Returns an indicator that represents the current state of the game.
   * Returns 0 if the top player has captured the King, 1 if the bottom
   * player has captured the King, or -1 otherwise.
   * @return The current state of the game.
   */
  public static int getStatus() {
    if (player0.hasKing())
      return 0;
    else if (player1.hasKing())
      return 1;
    else
      return -1;
  }

  /** 
   * Returns the requested player based on the player's ID.
   * @param n The player ID requested.
   * @return An instance of the Player with the corresponding ID.
   */
  public static Player getPlayer(int n) {
    if (n == 0)
      return player0;
    else if (n == 1)
      return player1;
    else
      return null;
  }

  /** 
   * Returns the current board being played.
   * @return The Board of the Chess game.
   */
  public static Board getBoard() {
    return board;
  }

  /** Starts the text-based version of the Chess game. */
  public void playText() {
    // Determines whether to play another round.
    boolean continuePlay = true;

    while (continuePlay) {
      // Determines whether a winner has been found.
      boolean found = false;

      // Gets the game ready for a new round.
      reset();

      // Prompts the players for their names.
      System.out.print("Name of Top Player? ");
      player0.setName((new Scanner(System.in)).nextLine());
      System.out.print("Name of Bottom Player? ");
      player1.setName((new Scanner(System.in)).nextLine());

      // The main game loop that checks for a winner after each move.
      while (!found) {
        // Array representing initial position and final position.
        int[][] move = null;

        // Gets the current player's move.
        move = getPlayer(getTurn()).getMove(board);

        // Moves the piece and retrieves the piece previously occupying that space.
        Piece captured = board.movePiece(move[0][0], move[0][1], move[1][0], move[1][1]);

        // If a piece has been captured, then save that captured piece.
        if (captured != null)
          getPlayer(getTurn()).addCapturedPiece(captured);
        
        // Switch off players.
        Game.incrementTurn();

        // Check for a checkmate.
        if (getStatus() != -1 || board.isCheckmate())
          found = true;
      }

      // Prints the final positions and determines the winner of the match.
      System.out.println();
      printContents();
      System.out.println("\nAnd the winner is..." + getPlayer(1 - getTurn()).getName() + "!");

      // Prompts the users on whether or not they want to play again.
      System.out.print("\nWould you like to play another game? (y/n)\n> ");
      String input = (new Scanner(System.in)).next();
      if (!input.equals("y") && !input.equals("Y"))
        continuePlay = false;
      else
        System.out.println();
    }
  }

  /** Starts the graphics version of the Chess game. */
  public void playGraphics() {
    // Sets the logo and title
    frame.setIconImage(new ImageIcon("../images/logo/chessLogo.png").getImage());
    frame.setTitle("Chess");

    // Sets the frame to exit on close
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Get the content pane
    Container content = frame.getContentPane();

    // Adds the components of the game
    for (CastleButton button : castleButtons)
      content.add(button);
    content.add(resetButton);
    content.add(board);

    // Set the background color
    content.setBackground(Color.WHITE);

    // Restricts the size of the window
    frame.setResizable(false);
    frame.pack();

    // Sets the location of the window to the center
    frame.setLocationRelativeTo(null);

    // Displays the frame
    frame.setVisible(true);
  }
}