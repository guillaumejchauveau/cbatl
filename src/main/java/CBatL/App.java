/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package CBatL;

import CBatL.view.BShipPanel;

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
    BShipPanel bShipP = new BShipPanel(10, 10);

    frame.setSize(900, 400);
    frame.setContentPane(bShipP);
    frame.setVisible(true);
  }

  public String getGreeting() {
    return "Hello world.";
  }
}
