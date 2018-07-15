import java.util.Scanner;
/**
 * PlayerTester.java  
 *
 * @author: Paul Shin
 * 
 * Brief Program Description: Tester for the Player class.
 */
public class PlayerTester
{
  public static void main(String[] args) {
    Game game = new Game(800);
    Board board = game.getBoard();
    Scanner scan = new Scanner(System.in);

    // Creating a new player, ID of 1 because the bottom player starts first.
    Player player = game.getPlayer(1);

    // Testing getMove()
    System.out.println("[1] Testing getMove(). Please enter a valid or invalid move.");
    System.out.println("Should keep prompting until a move is valid.");
    System.out.println("For this section, assume you are the bottom player.");
    int[][] move = player.getMove(board);
    System.out.println("\nEnter any character to continue...");
    scan.next();

    // Testing addCapturedPiece(), and sortCapturedPiece() internally.
    System.out.println("Adding new pieces to the set of captured pieces...");
    player.addCapturedPiece(new Pawn(0, 0, 0));
    player.addCapturedPiece(new Knight(0, 0, 0));
    player.addCapturedPiece(new Bishop(0, 0, 0));
    player.addCapturedPiece(new Queen(0, 0, 0));
    player.addCapturedPiece(new King(0, 0, 0));
    player.addCapturedPiece(new Rook(0, 0, 0));
    System.out.println("Additions complete.");
    System.out.println("\nEnter any character to continue...");
    scan.next();

    // Printing captured pieces using printCapturedPieces()
    System.out.println("Current set of captured pieces:\n");
    player.printCapturedPieces();
    System.out.println("\nEnter any character to continue...");
    scan.next();

    // Testing reset()
    System.out.println("Resetting the player's set of captured pieces.");
    player.reset();
    System.out.println("After the reset:\n");
    player.printCapturedPieces();
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    System.out.println("Testing hasKing().");
    System.out.println("\nCurrent set:");
    player.printCapturedPieces();
    System.out.println("Has the player captured the King? " + player.hasKing());
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    player.addCapturedPiece(new Pawn(0, 0, 0));
    System.out.println("\nCurrent set:");
    player.printCapturedPieces();
    System.out.println("Has the player captured the King? " + player.hasKing());
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    player.addCapturedPiece(new King(0, 0, 0));
    System.out.println("\nCurrent set:");
    player.printCapturedPieces();
    System.out.println("Has the player captured the King? " + player.hasKing());
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    System.out.println("Done with test.");
  }
}
