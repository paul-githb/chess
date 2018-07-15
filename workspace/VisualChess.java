
/** The graphics version of the Chess game.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class VisualChess {
  public static void main(String[] args) {
    int scale = 0;

    if (args.length < 1) {
      System.out.println("Creating a new game at the default scale of 800.");
      System.out.println("If you'd like to set your own scale, send it as an argument.");
      scale = 800;
    } else {
      try {
        scale = Integer.parseInt(args[0]);
        if (scale < 300) {
          System.out.println("Too small. Setting scale to the minimum size 300.");
          scale = 300;
        }
      } catch (NumberFormatException e) {
        System.out.println("Not a valid argument. Setting scale to the default 800.");
        scale = 800;
      }
    }

    (new Game(scale)).playGraphics();
  }
}