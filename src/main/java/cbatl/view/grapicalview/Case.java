package cbatl.view.graphicalview;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Case extends JButton
{
  public Case ()
  {
    super();
    this.init();
  }

  public void init ()
  {
    //this.setOpaque(false);
    this.setContentAreaFilled(true);
    this.setBackground(Color.WHITE);
    this.setFocusPainted(false);
    this.setBorder(new LineBorder(Color.GRAY));
  }
}
