package cbatl.view.graphicalview.controllers;

import cbatl.view.graphicalview.Case;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class ShipController implements ActionListener {
  private Case c;

  public ShipController (Case c)
  {
    this.c = c;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    System.out.println("Button clicked");
    this.c.setBackground(Color.RED);
    this.c.setEnabled(false);
    System.out.println(this.c.getRow() + ", " + this.c.getCol());
  }
}
