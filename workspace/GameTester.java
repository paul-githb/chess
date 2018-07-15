import java.util.Scanner;
/**
 * GameTester.java  
 *
 * @author: Paul Shin
 * 
 * Brief Program Description: Tester for the Game class.
 */
public class GameTester
{
  public static void main(String[] args) {
    Game game = new Game(800);
    Scanner scan = new Scanner(System.in);
    
    // Check Game.printContents()
    System.out.println("Checking the print contents method.");
    game.printContents();
    System.out.println("\n\nEnter any character to continue...");
    scan.next();
    
    // Check Game.incrementTurn()
    System.out.println("\n\nCurrent Turn: " + game.getTurn());
    System.out.println("Incrementing turn...");
    game.incrementTurn();
    System.out.println("New current turn: " + game.getTurn());
    System.out.println("Enter any character to continue.");
    scan.next();
    
    // Testing Game.setCastleButtonVisible()
    System.out.println("\n\nTesting Game.setCastleButtonVisible(String,int,boolean).");
    System.out.println("Close the window when all CastleButtons are visible.\n");
    game.playGraphics();
    
    System.out.println("Setting the top left button visible...enter any character.");
    scan.next();
    game.setCastleButtonVisible("left", 0, true);
    
    System.out.println("Setting the top right button visible...enter any character.");
    scan.next();
    game.setCastleButtonVisible("right", 0, true);
    
    System.out.println("Setting the bottom left button visible...enter any character.");
    scan.next();
    game.setCastleButtonVisible("left", 1, true);
    
    System.out.println("Setting the bottom right button visible...enter any character.");
    scan.next();
    game.setCastleButtonVisible("right", 1, true);
    
    System.out.println("Done with the test! Close the window.");
    System.out.println("To test the playText() or playGraphics() methods,");
    System.out.println("Run \"TextBasedChess.java\" or \"VisualChess.java\", respectively.");
  }
}
