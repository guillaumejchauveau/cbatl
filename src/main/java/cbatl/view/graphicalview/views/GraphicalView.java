package CBatL.view.graphicalview.views;

import CBatL.view.graphicalview.views.BShipPanel;
import CBatL.view.graphicalview.controllers.QuitController;
import CBatL.view.graphicalview.controllers.ResetController;
import CBatL.view.graphicalview.controllers.ChangeViewController;
import CBatL.view.graphicalview.views.HelpView;

import javax.swing.*;
import java.awt.Image;

public class GraphicalView extends JFrame
{
  public GraphicalView()
  {
    this.setTitle("BattleShip");
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    HelpView help = new HelpView(this);
    BShipPanel bShipP = new BShipPanel(this, 10, 10);

    ResetController reset = new ResetController(bShipP);
    QuitController quit = new QuitController(this);
    ChangeViewController cvcHelp = new ChangeViewController(this, help, help.getJMenuBar());
    ChangeViewController cvcBack = new ChangeViewController(this, bShipP, bShipP.getJMenuBar());

    bShipP.getResetButton().addActionListener(reset);
    bShipP.getQuitButton().addActionListener(quit);
    bShipP.getHelpButton().addActionListener(cvcHelp);

    help.getBackButton().addActionListener(cvcBack);

    this.setContentPane(bShipP);
    this.setVisible(true);
  }
}








