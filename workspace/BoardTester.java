import java.util.Scanner;
/**
 * BoardTester.java  
 *
 * @author: Paul Shin
 * 
 * Brief Program Description: Tester for the Board class.
 */
public class BoardTester {
  public static void main(String[] args) {
    System.out.println("Board.java tester begins.\nDo not make any of your own changes to the board.");
    Game game = new Game(800);
    Board board = game.getBoard();
    Scanner scan = new Scanner(System.in);
    game.playGraphics();
    System.out.println("Enter any character to continue...");
    scan.next();

    // Testing print()
    System.out.println("\nTesting print().");
    board.print();
    System.out.println("Enter any character to continue...");
    scan.next();

    // Testing checkExists(), isCheckmate(), and castleIsValid() for the bottom player.
    System.out.println("\nDoes a check exist after moving R6C0 to R4C0? " + board.checkExists(board.getPiece(6, 0), 4, 0));
    System.out.println("Does a checkmate currently exist for the bottom player? " + board.isCheckmate());
    System.out.println("Is a castle currently valid on the bottom left?" + board.castleIsValid("left"));
    System.out.println("Is a castle currently valid on the bottom right? " + board.castleIsValid("right"));
    
    game.incrementTurn();
    System.out.println("Enter any character to continue...");
    scan.next();
    
    // Testing checkExists(), isCheckmate(), and castleIsValid() for the top player.
    System.out.println("\nDoes a check exist after moving R1C0 to R3C0? " + board.checkExists(board.getPiece(1, 0), 3, 0));
    System.out.println("Does a checkmate currently exist for the top player? " + board.isCheckmate());
    System.out.println("Is a castle currently valid on the top left? " + board.castleIsValid("left"));
    System.out.println("Is a castle currently valid on the top right? " + board.castleIsValid("right"));
    
    game.incrementTurn();
    System.out.println("Enter any character to continue...");
    scan.next();
    
    // Testing movePiece() among others.
    System.out.println("Testing movePiece() now.");
    System.out.println("Note: Lights will not change since repainting is handled in the handleRequest() method.");
    System.out.println("Enter any character to continue...");
    scan.next();
    
    testMovePiece(board, game, 7, 1, 5, 0);
    testMovePiece(board, game, 0, 1, 2, 0);
    testMovePiece(board, game, 6, 1, 5, 1);
    testMovePiece(board, game, 1, 1, 2, 1);
    testMovePiece(board, game, 7, 2, 6, 1);
    testMovePiece(board, game, 0, 2, 1, 1);
    testMovePiece(board, game, 6, 2, 5, 2);
    testMovePiece(board, game, 1, 2, 2, 2);
    testMovePiece(board, game, 7, 3, 6, 2);
    testMovePiece(board, game, 0, 3, 1, 2);
    testMovePiece(board, game, 6, 2, 7, 3);
    testMovePiece(board, game, 1, 2, 0, 3);
    testMovePiece(board, game, 6, 4, 4, 4);
    testMovePiece(board, game, 1, 4, 3, 4);
    testMovePiece(board, game, 6, 5, 4, 5);
    testMovePiece(board, game, 0, 5, 3, 2);
    testMovePiece(board, game, 4, 5, 3, 4);
    testMovePiece(board, game, 0, 3, 4, 7);
    testMovePiece(board, game, 7, 4, 6, 4);
    testMovePiece(board, game, 4, 7, 4, 4);
    
    System.out.println("\nNow we will test performCastle() for the top player.");
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    // Testing performCastle().
    board.performCastle("left", 0);
    System.out.println("\nCastle complete.");
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    // Testing print() again, then reset().
    System.out.println("\nPrinting out current board in text.");
    board.print();
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    System.out.println("\nResetting the board.\n");
    board.reset();
    board.print();
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    System.out.println("Now, to test promotionIsValid() and promote(),");
    System.out.println("we will bypass ALL validity checks and move a pawn to the other end.");
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    // Testing promotionIsValid() and promote().
    Piece currentPiece = board.getPiece(6, 0);
    boolean valid = board.promotionIsValid(currentPiece);
    System.out.println("Is the pawn at R6C0 valid for promotion? " + valid);
    if (valid) board.promote(currentPiece);
    
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    System.out.println("Now we will bypass validity checks and move it to the other end.");
    board.movePiece(6, 0, 0, 0);
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    currentPiece = board.getPiece(0, 0);
    valid = board.promotionIsValid(currentPiece);
    System.out.println("Is the pawn at R0C0 valid for promotion? " + valid);
    if (valid) board.promote(currentPiece);
    
    System.out.println("\nEnter any character to continue...");
    scan.next();
    
    System.out.println("\n\nDone with testing. In order to appropriately test handleRequest(),");
    System.out.println("it's best done simply by running VisualChess.java.");
    System.out.println("You may now close the game window.");
  }
  
  public static void testMovePiece(Board board, Game game, int ri, int ci, int rf, int cf) {
    // Testing movePiece().
    board.movePiece(ri, ci, rf, cf);
    
    // Testing castleIsValid() for the current player.
    System.out.print("Is a castle currently valid on the ");
    if (game.getTurn() == 0) System.out.print("top");
    else System.out.print("bottom");
    System.out.println(" left? " + board.castleIsValid("left"));
    
    System.out.print("Is a castle currently valid on the ");
    if (game.getTurn() == 0) System.out.print("top");
    else System.out.print("bottom");
    System.out.println(" right? " + board.castleIsValid("right"));
    
    game.incrementTurn();
    
    // Testing isCheckmate() for the next player.
     System.out.print("\nDoes a checkmate currently exist for the ");
    if (game.getTurn() == 0) System.out.print("top");
    else System.out.print("bottom");
    System.out.println(" player? " + board.isCheckmate());
    
    System.out.println("\nEnter any character to continue...");
    (new Scanner(System.in)).next();
  }
}