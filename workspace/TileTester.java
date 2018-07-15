
/**
 * TileTester.java  
 *
 * @author: Paul Shin
 * 
 * Brief Program Description: Tester for the Tile class.
 */
public class TileTester {
  public static void main(String[] args) {
    System.out.println("Start test for Tile.java:");
    System.out.println("*~*~*~*~*~*~*~*~*~*~*~*~*");
    Game game = new Game(800);
    Tile tile = new Tile(0, 0);

    // Testing setPiece()
    System.out.println("\nAdding pawn to the tile...");
    Piece previousPiece = tile.setPiece(new Pawn(0, 0, 0));
    System.out.println("Current piece: " + ((tile.getPiece() == null) ? tile.getPiece() : tile.getPiece().getStringValue()));
    System.out.println("Previous piece: " + ((previousPiece == null) ? previousPiece : previousPiece.getStringValue()));

    System.out.println("\nAdding queen to the tile...");
    previousPiece = tile.setPiece(new Queen(0, 0, 0));
    System.out.println("Current piece: " + ((tile.getPiece() == null) ? tile.getPiece() : tile.getPiece().getStringValue()));
    System.out.println("Previous piece: " + ((previousPiece == null) ? previousPiece : previousPiece.getStringValue()));

    System.out.println("\nAdding rook to the tile...");
    previousPiece = tile.setPiece(new Rook(0, 0, 0));
    System.out.println("Current piece: " + ((tile.getPiece() == null) ? tile.getPiece() : tile.getPiece().getStringValue()));
    System.out.println("Previous piece: " + ((previousPiece == null) ? previousPiece : previousPiece.getStringValue()));

    System.out.println("\nSetting tile to null twice...");
    previousPiece = tile.setPiece(null);
    System.out.println("Current piece: " + ((tile.getPiece() == null) ? tile.getPiece() : tile.getPiece().getStringValue()));
    System.out.println("Previous piece: " + ((previousPiece == null) ? previousPiece : previousPiece.getStringValue()));

    previousPiece = tile.setPiece(null);
    System.out.println("\nCurrent piece: " + ((tile.getPiece() == null) ? tile.getPiece() : tile.getPiece().getStringValue()));
    System.out.println("Previous piece: " + ((previousPiece == null) ? previousPiece : previousPiece.getStringValue()));
  }
}