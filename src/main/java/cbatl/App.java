package cbatl;

import cbatl.controller.Controller;
import cbatl.view.terminalview.TerminalView;
import java.io.File;

public class App {
  public static void main(String[] args) {
    File playerFile = new File("players.csv");
    Controller controller = new Controller(playerFile, true);
    controller.attachView(new TerminalView());
  }
}
