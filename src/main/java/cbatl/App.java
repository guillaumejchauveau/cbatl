package cbatl;

import cbatl.controller.Controller;
import cbatl.view.terminalview.TerminalView;
import cbatl.view.graphicalview.views.GraphicView;
import java.io.File;

public class App {
  public static void main(String[] args) {
    File playerFile = new File("players.csv");
    Controller controller = new Controller(playerFile, args.length != 0);
    controller.attachView(new GraphicView());
    //controller.attachView(new TerminalView());
  }
}
