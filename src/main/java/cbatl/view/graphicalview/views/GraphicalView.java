package cbatl.view.graphicalview.views;

import cbatl.view.graphicalview.controllers.QuitController;
import cbatl.view.graphicalview.controllers.ResetController;
import cbatl.view.graphicalview.controllers.ChangeViewController;

import javax.swing.*;

public class GraphicalView extends JFrame
{
  public GraphicalView()
  {
    this.setTitle("BattleShip");
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    HelpView help = new HelpView(this);
    BShipPanel bShipP = new BShipPanel(this, 10, 10);

    /*ResetController reset = new ResetController(bShipP);
    QuitController quit = new QuitController(this);
    ChangeViewController cvcHelp = new ChangeViewController(this, help, help.getJMenuBar());
    ChangeViewController cvcBack = new ChangeViewController(this, bShipP, bShipP.getJMenuBar());

    bShipP.getResetButton().addActionListener(reset);
    bShipP.getQuitButton().addActionListener(quit);
    bShipP.getHelpButton().addActionListener(cvcHelp);

    help.getBackButton().addActionListener(cvcBack);*/

    EndGame end = new EndGame(this, true);

    Launcher l = new Launcher(this);

    this.setContentPane(end);
    this.setVisible(true);
  }
}








