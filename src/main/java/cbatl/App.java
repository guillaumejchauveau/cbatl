package cbatl;

import cbatl.controller.Controller;
import cbatl.view.terminalview.*;
import java.io.File;

public class App {
  public static void main(String[] args) {
    File playerFile = new File("players.csv");
    Controller controller = new Controller(playerFile);
    controller.attachView(new TerminalInterface());
    System.out.println("done");/*
    TerminalInterface ti = new TerminalInterface();
    ti.mainMenu();*/
  }
}
