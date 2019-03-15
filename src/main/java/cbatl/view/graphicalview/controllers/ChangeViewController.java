package cbatl.view.graphicalview.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChangeViewController implements ActionListener
{
  private JFrame frame;
  private JPanel panel;
  private JMenuBar menubar;

  public ChangeViewController(JFrame f, JPanel p, JMenuBar m)
  {
    this.frame = f;
    this.panel = p;
    this.menubar = m;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("je suis la ");
    this.frame.getContentPane().remove(this.frame.getContentPane());
    this.frame.setContentPane(this.panel);
    this.frame.getJMenuBar().remove(this.frame.getJMenuBar());
    this.frame.setJMenuBar(this.menubar);
    this.frame.revalidate();
  }
}
