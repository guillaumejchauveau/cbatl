package CBatL.view;

import javax.swing.*;
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
    this.gridP1.setLayout(new GridLayout(this.width, this.height));
    this.initGrid(this.gridP1);

    this.gridP2 = new JPanel();
    this.gridP2.setLayout(new GridLayout(this.width, this.height));
    this.initGrid(this.gridP2);

    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    this.add(this.gridP1);
    this.add(this.gridP2);
  }

  private final void initGrid (JPanel grid)
  {
    for (int i = 0; i < this.width; i++)
    {
      for (int j = 0; j < this.height; j++)
      {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(40, 40));
        grid.add(button);
      }
    }
  }
}
