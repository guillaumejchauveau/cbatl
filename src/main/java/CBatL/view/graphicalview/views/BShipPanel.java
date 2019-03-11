package CBatL.view.graphicalview.views;

import CBatL.view.graphicalview.Case;
import CBatL.view.graphicalview.controllers.ShipController;

import javax.swing.*;
import java.awt.*;

public class BShipPanel extends JPanel
{
  private JFrame frame;
  private int width;
  private int height;
  private JPanel gridP1;
  private JPanel gridP2;
  private JPanel boatBoard;
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem helpMenu;
  private JMenuItem replay;
  private JMenuItem quit;
  private Component boxAreaSpace;

  public BShipPanel (JFrame frame, int w, int h)
  {
    this.frame = frame;
    this.frame.setSize(new Dimension(900, 450));
    this.frame.setResizable(false);

    this.width = w;
    this.height = h;
    this.boxAreaSpace = Box.createRigidArea(new Dimension(40,0));
    this.initMenu();
    this.init();
    this.initBoard();
  }

  public final int getGridWidth ()
  {
    return this.width;
  }

  public final void setGriWidth (int newWidth)
  {
    this.width = newWidth;
  }

  public final int getGriHeight ()
  {
    return this.height;
  }

  public final void setGriHeight (int newHeight)
  {
    this.height = newHeight;
  }

  public final JMenuItem getResetButton ()
  {
    return this.replay;
  }

  public final JMenuItem getQuitButton ()
  {
    return this.quit;
  }

  public final JMenuItem getHelpButton ()
  {
    return this.helpMenu;
  }

  public final JMenuBar getJMenuBar ()
  {
    return this.menuBar;
  }

  /**
   * Initialize the MenuBar of the Jframe passed in constructor
   */
  private final void initMenu ()
  {
    this.menuBar = new JMenuBar();

    this.fileMenu = new JMenu("File");
    this.helpMenu = new JMenuItem("Help");

    this.replay = new JMenuItem("Replay");
    this.quit = new JMenuItem("Quit");

    this.fileMenu.add(this.replay);
    this.fileMenu.add(this.quit);

    this.menuBar.add(this.fileMenu);
    this.menuBar.add(this.helpMenu);

    this.frame.setJMenuBar(this.menuBar);
  }

  /**
   * Initialize the main Panel
   */
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
    this.add(this.boxAreaSpace); //Create space between both grid
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
          button = new Case(i, j);
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
          button = new Case(i, j);
          button.setText("" + i);
          button.setEnabled(false);
        } else {
          button = new Case(i, j);
          button.addActionListener(new ShipController(button));
        }

        grid.add(button);
      }
    }
  }

  private void initBoard () {
    this.boatBoard = new JPanel();
    this.add(this.boatBoard);
  }

  /**
   * Reset both grid
   */
  public final void resetGrid ()
  {
    this.remove(this.gridP1);
    this.remove(this.gridP2);
    this.remove(this.boatBoard);
    this.remove(this.boxAreaSpace);
    this.init();
    this.initBoard();
  }
}
