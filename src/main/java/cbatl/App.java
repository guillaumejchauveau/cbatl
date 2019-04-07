package cbatl;

import cbatl.controller.Controller;
import cbatl.view.terminalview.TerminalView;
import cbatl.view.graphicalview.GraphicalView;
import java.io.File;

public class App {
  public static void main(String[] args) {
    File playerFile = new File("players.csv");
    Controller controller = new Controller(playerFile, false);
    controller.attachView(new GraphicalView());
    controller.attachView(new TerminalView());
  }
}
