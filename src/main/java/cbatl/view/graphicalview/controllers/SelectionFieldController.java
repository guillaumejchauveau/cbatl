package cbatl.view.graphicalview.controllers;

import cbatl.view.graphicalview.views.PlayerConfigView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionFieldController implements ActionListener
{
  private PlayerConfigView panel;

  public SelectionFieldController(PlayerConfigView pane)
  {
    this.panel = pane;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    this.panel.setSelectionVisible();
  }
}
