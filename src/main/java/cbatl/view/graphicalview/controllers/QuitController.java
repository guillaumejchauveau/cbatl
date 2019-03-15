package cbatl.view.graphicalview.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class QuitController implements ActionListener
{
  private JFrame frame;

  public QuitController (JFrame frame)
  {
    this.frame = frame;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    this.frame.dispose();
  }
}
