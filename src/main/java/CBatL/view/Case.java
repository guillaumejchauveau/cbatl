package CBatL.view;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.TransferHandler;
import java.awt.Color;

public class Case extends JButton
{
  private int row;
  private int col;

  public Case (int newRow, int newCol)
  {
    super();
    this.row = newRow;
    this.col = newCol;
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

  public final int getRow ()
  {
    return this.row;
  }

  public final int getCol ()
  {
    return this.col;
  }
}
