package CBatL.view.graphicalview;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetController implements ActionListener
{
  private BShipPanel panel;

  public ResetController (BShipPanel p)
  {
    this.panel = p;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    System.out.println("I reset all");
    this.panel.resetGrid();
    this.panel.updateUI();
  }
}
