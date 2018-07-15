import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/** Button which allows for the castling move.
 * @author Eric Wu
 * @author Yatin Ravi
 * @author Paul Shin
 * @version 1.0
 */
public class CastleButton extends JButton {

  /** The side the button is located, left or right. */
  private String side;

  /** The ID of the player the button belongs to. */
  private int playerID;

  /**
   * The constructor for the CastleButton class. Constructs a
   * new CastleButton at the location specified by the given
   * parameters, and sets its visibility to false to begin with.
   * @param s The side the button is located, left or right.
   * @param id The ID of the player it belongs to.
   */
  public CastleButton(String s, int id) {
    super("CASTLE");

    int x, y, u = Game.getUnit();

    if (s.equals("left"))
      x = 11 * u / 4;
    else
      x = 29 * u / 4;

    y = 9 * u * id + 5 * u / 4;

    side = s;
    playerID = id;

    setBounds(x, y, 2 * u, u / 2);
    setVisible(false);
    setOpaque(false);
    setContentAreaFilled(false);
    setFont(new Font(Font.SANS_SERIF, Font.BOLD, u / 3));
    setForeground(new Color(75, 160, 255));

    int th = Game.getScale() / 200;
    setBorder(BorderFactory.createMatteBorder(th, th, th, th, new Color(75, 160, 255)));
    
    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Board board = Game.getBoard();
        board.performCastle(side, playerID);

        // Make sure the castle buttons are turned off.
        Game.setCastleButtonVisible("left", Game.getTurn(), false);
        Game.setCastleButtonVisible("right", Game.getTurn(), false);

        // Switch players.
        Game.incrementTurn();
        board.repaint();

        if (board.castleIsValid("left"))
          Game.setCastleButtonVisible("left", Game.getTurn(), true);
        if (board.castleIsValid("right"))
          Game.setCastleButtonVisible("right", Game.getTurn(), true);
      }
    });
  }
}