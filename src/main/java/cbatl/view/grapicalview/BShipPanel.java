package cbatl.view.graphicalview;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BShipPanel extends JPanel
{
  private int width;
  private int height;
  private JPanel gridP1;
  private JPanel gridP2;

  public BShipPanel (int w, int h)
  {
    this.width = w;
    this.height = h;
    this.init();
  }

  private final void init ()
  {
    this.gridP1 = new JPanel();
    this.gridP1.setLayout(new GridLayout(this.width + 1, this.height + 1));
    this.initGrid(this.gridP1);

    this.gridP2 = new JPanel();
    this.gridP2.setLayout(new GridLayout(this.width + 1, this.height + 1));
    this.initGrid(this.gridP2);

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.add(this.gridP1);
    this.add(Box.createRigidArea(new Dimension(20,0))); //Create space between both grid
    this.add(this.gridP2);
  }

  /**
   * Init method for each grid
   * @param grid
   */
  private final void initGrid (JPanel grid)
  {
    for (int i = 0; i < this.width + 1; i++)
    {
      for (int j = 0; j < this.height + 1; j++)
      {
        Case button;
        if(i == 0)
        {
          button = new Case();
          String text;
          if(j == 0)
          {
            text = "/";
          } else {
            text = "" + (char) ((int) '@' + j);
          }
          button.setText(text);
          button.setEnabled(false);
        }else if (j == 0) {
          button = new Case();
          button.setText("" + i);
          button.setEnabled(false);
        } else {
          button = new Case();
          button.addActionListener(new ShipController(button));
        }

        grid.add(button);
      }
    }
  }
}
