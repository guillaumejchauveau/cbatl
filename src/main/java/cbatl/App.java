package cbatl;

import cbatl.controller.Controller;
import cbatl.view.graphicalview.GraphicalView;
import cbatl.view.terminalview.TerminalView;
import java.awt.HeadlessException;
import java.io.File;

public class App {
  public static void main(String[] args) {
    File playerFile = new File("players.csv");
    Controller controller = new Controller(playerFile, args.length != 0);
    try {
      controller.attachView(new GraphicalView());
    } catch (HeadlessException e) {
      System.out.println(e);
    }
    controller.attachView(new TerminalView());
  }
}
