/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package CBatL;

import CBatL.view.graphicalview.views.BShipPanel;
import CBatL.view.graphicalview.controllers.QuitController;
import CBatL.view.graphicalview.controllers.ResetController;
import CBatL.view.graphicalview.controllers.ChangeViewController;
import CBatL.view.graphicalview.views.HelpView;

import javax.swing.*;

/**
 * Coucou
 */
public class App {
  public static void main(String[] args)
  {
    System.out.println(new App().getGreeting());
    JFrame frame = new JFrame();
    frame.setTitle("BattleShip");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    HelpView help = new HelpView(frame);
    BShipPanel bShipP = new BShipPanel(frame, 10, 10);

    ResetController reset = new ResetController(bShipP);
    QuitController quit = new QuitController(frame);
    ChangeViewController cvcHelp = new ChangeViewController(frame, help, help.getJMenuBar());
    ChangeViewController cvcBack = new ChangeViewController(frame, bShipP, bShipP.getJMenuBar());

    bShipP.getResetButton().addActionListener(reset);
    bShipP.getQuitButton().addActionListener(quit);
    bShipP.getHelpButton().addActionListener(cvcHelp);

    help.getBackButton().addActionListener(cvcBack);

    frame.setContentPane(bShipP);
    frame.setVisible(true);
  }

  public String getGreeting() {
    return "Hello world.";
  }
}
