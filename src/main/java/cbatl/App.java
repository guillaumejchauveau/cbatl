package cbatl;

import cbatl.controller.Controller;
import cbatl.view.terminalview.TerminalView;
import cbatl.view.graphicalview.GraphicalView;
import java.awt.HeadlessException;
import java.io.File;

public class App {
  public static void main(String[] args) {
    File playerFile = new File("players.csv");
    Controller controller = new Controller(playerFile, false);
    try {
      controller.attachView(new GraphicalView());
    } catch (HeadlessException e) {
      System.out.println("Erreur lors dans la vue graphique : ");
      System.out.println(e);
    }
    controller.attachView(new TerminalView());
  }
}
