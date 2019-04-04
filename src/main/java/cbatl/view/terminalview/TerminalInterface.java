package cbatl.view.terminalview;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import javax.sql.rowset.spi.SyncResolver;

import java.util.ArrayList;
import cbatl.model.player.Player;
import cbatl.model.player.RandomPlayer;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;
import cbatl.view.events.*;
import cbatl.model.*;
import cbatl.model.events.StateChangedEvent;
import cbatl.view.View;

/**
 * Manages the command line Interface
 */
public class TerminalInterface extends View implements Runnable {

  private final PrintStream out;
  private final Scanner scanner;
  private Model model;
  private final Object answerLock;
  private String answer;
  private Player ourPlayer;
  private Player opponent;


  public TerminalInterface() {
    this.out = System.out;
    InputStream in = System.in;
    this.scanner = new Scanner(in);
    answerLock = new Object();
    answer = "";
    Thread thread = new Thread(this);
    thread.start();
  }


  @Override
  public void run() {
    while (!Thread.interrupted()) {
      String input = this.scanner.nextLine();
      parseInput(input);
      System.out.println("input = " + input);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void attachModel(Model m)
  {
    this.model = m;
    this.println("attaching model");
    this.println(model.getCurrentState() == Model.State.MAIN_MENU);
    this.model.addEventListener(StateChangedEvent.class, event -> this.whatToDo());

    this.whatToDo();

  }

  private void whatToDo(){
    switch(this.model.getCurrentState()){
      case MAIN_MENU : mainMenu(); break;
      case CREATING_GAME : createGameMenu(); break;
      case PLAYING_GAME : playGame(); break;
      case GAME_OVER : gameOver(); break;
    }
  }

  /**
   * Clear the Terminal.
   */
  private void clear() {
    this.out.print("\033[H\033[2J");
    this.out.flush();
  }

  /**
   * Prints in the terminal.
   */
  private void print() {
    this.out.print((Object) ">");
  }

  private void print(Object o)
  {
    this.out.print(o);
  }

  /**
   * Prints in the terminal and then \n.
   * @param text object to print
   */
  private void println(Object text) {
    this.out.println(text);
  }

  /**
   * Prints a header for the different menus.
   * @param head title of the menu to print
   */
  private void header(String head)
  {
    this.clear();
    this.println("    --BATTLESHIP--" + System.lineSeparator());
    this.println(head+ System.lineSeparator()+ System.lineSeparator());
  }

  /**
   * Prints the choices the player has to do. Used only for the menus.
   * @param hm Hash map containing different choices. 
   */
  private void waitingForAnswer(HashMap<String, String> hm)
  {
    for(int i = 0; i < hm.size(); i++)
    {
      this.println("  " + i +". " + hm.get("" +i));
    }
  }

  private void parseInput(String input)
  {
    switch (input){
      case "x" :
        this.exitGame();
        break;
      case "m" :
        this.dispatchEvent(new MainMenuEvent());
        break;
      default :
        synchronized (answerLock) {
          this.answer = input;
          this.println("answer = " + input);
          this.answerLock.notify();
        }
        break;
    }

  }

  /**
   * Prints Players and their score on screen.
   */
  private void freePlayers()
  {
    try{
      Collection<Player> lp = this.model.playerManager.getPlayers();
      if(lp.size() <= 0)
        this.println("    Aucun joueur reference." + System.lineSeparator());

      else{
        for(Player p : lp)
        {
          this.println(p.getName() + " | score: " + p.getScore());
        }
      }
     
    }
    catch(NullPointerException e){
      this.println("    Aucun joueur reference.");
    }

  }

  /**
   * Displays BATTLESHIP's Main Menu. From here, the player can leave or go to BATTLESHIP's game menu.
   */
  private void mainMenu()
  {
    this.header("Menu Principal");

    this.println("Joueurs disponibles:");
    this.freePlayers();
    HashMap<String,String> hm = new HashMap<>();
    hm.put("0", "Creer une partie");
    hm.put("1", "Quitter");

    this.waitingForAnswer(hm);
    while(true){
      String input;
      synchronized (answerLock) {
        try {
          this.answerLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        input = this.answer;
        System.out.println("main menu answer");
      }
      if (input != null) {
        switch (input) {
          case "0":
            this.dispatchEvent(new CreateGameMenuEvent()); break;
          case "1":
            this.exitGame();break;
          default :
            break;
        }
      }
    }
    //System.out.println("Leaving main menu.");
  }

  /**
   * Displays BATTLESHIP's game menu. Allows the player to create a new payer or to play a game with a chososen player.
   */
  private void createGameMenu()
  {
    this.header("Menu du jeu");
    this.println("Joueurs disponibles:");
    this.freePlayers();

    HashMap<String, String> hm = new HashMap<>();
    hm.put("0", "Incarner un joueur present.");
    hm.put("1", "Creer un nouveau joueur.");

    this.waitingForAnswer(hm);
    //this.answer = null;

    boolean breaker = false;
    while(!breaker){
      String input;
      synchronized (answerLock) {
        try {
          this.answerLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        input = this.answer;
      }
      if (input != null) {
        switch(input){
          case "0": breaker = true; break;
          case "1": this.createPlayer(); return;
        }
      }
    }


    this.println("Veuillez entrer le nom du joueur que vous voulez incarner.");
    breaker = false;
    while(!breaker)
    {

      String input;
      synchronized (answerLock) {
        try {
          answerLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        input = this.answer;
      }
      if( this.model.playerManager.hasPlayer(input))
      {
        ArrayList<Player> players = new ArrayList<>();
        this.ourPlayer = this.model.playerManager.getPlayer(input);
        this.opponent = new RandomPlayer();
        players.add(this.ourPlayer);
        players.add(this.opponent);
        this.dispatchEvent(new PlayGameEvent(players));
        breaker = true;
      }
      else
      {
        this.println("Le jeu ne contient pas ce joueur.");
      }
    }
  }


  /**
   * Creates a player and sends player to model
   */
  private void createPlayer()
  {
    this.answer = "";
    this.header("Creer un joueur");

    HashMap<String, String> hm = new HashMap<>();
    hm.put("0", "Retourner au menu principal.");
    hm.put("1", "Retourner au menu de jeu.");
    hm.put("2", "Quitter le jeu.");

    this.println("Entrer le nom du nouveau Joueur:");
    boolean breaker = false;
    while(!breaker)
    {
      synchronized (answerLock)
      {
        try {
          this.answerLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      
      String name = this.answer;
      try {
        this.model.playerManager.registerPlayer(new Player(name));
        breaker = true;
      } catch (NullPointerException e) {
        e.printStackTrace();
      } catch(IllegalArgumentException e) {
        this.println("Ce nom existe deja.") ;
      }
    }

    this.waitingForAnswer(hm);
    this.answer = "";
    breaker = false;
    while(!breaker)
    {
      synchronized (answerLock)
      {
        try {
          this.answerLock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      
      String input = this.answer;
      switch (input){
        case "0" : breaker = true; this.dispatchEvent(new MainMenuEvent()); break;
        case "1" : breaker = true; this.createGameMenu();break;
        case "2" : breaker = true; this.exitGame(); break;
      }

    }
  }

/**
 * Prints the two grid and the associated legend.
 * @param g1 the first grid to print.
 * @param g2the the second grid to print.
 */
  public void displayGrids(String[][] g1, String[][] g2, String[][] g3)
  {

    String legend = "Legende: . = case vide" +
                    "    / = bateau" + System.lineSeparator()+
                    "         o = touche   " +
                    "    x = pas touche" + System.lineSeparator();
    
    int col = g1.length;
    int row = g1[0].length;
    this.println(col + " " + row);
    String spacer = "  ";
    for(int x = 0; x < col; x++)
    {

      if(x == 0)
      {
        for(int i = 0; i < 3; i++)
        {

          this.print("/ ");
          for(int x2 = 0; x2 < row; x2++)
          {
            this.print((char) (65+x2));
            if(x2 -2 < col)
              this.print(" | ");
           
          }
          this.print("  ");
          
        }
      }
      this.println("");
      for(int y1 = 0; y1 < row; y1++){
        if(y1 == 0)
        this.print(x + " ");
        this.print(g1[y1][x]);
        if(y1 -1 < row)
            this.print(" | ");
      }
      this.print(spacer);
      for(int y2 = 0; y2 < row; y2++){
        if(y2 == 0)
          this.print(x + " ");
        this.print(g2[y2][x]);
        if(y2 -1 < row)
            this.print(" | ");
      }
      this.print(spacer);
      for(int y3 = 0; y3 < row; y3++){
        if(y3 == 0)
          this.print(x + " ");
        this.print(g3[y3][x]);
        if(y3 -1 < row)
            this.print(" | ");
      }


      
    }
    this.println("");
    this.println(legend);
  }

  public void playGame()
  {
    this.answer = null;
    

    Territory ourPlayerTerritory =  this.model.getCurrentGame().getPlayerTerritory(this.ourPlayer);
    Territory opponentTerritory = this.model.getCurrentGame().getPlayerTerritory(this.opponent);
    int x = ourPlayerTerritory.width;
    int y = ourPlayerTerritory.height;
    TerminalView ourPlayerGrid  = new TerminalView(x, y);
    TerminalView opponentHiddenGrid = new TerminalView(x, y);
    TerminalView opponentVisibleGrid = new TerminalView(x, y);
    ourPlayerGrid.init(ourPlayerTerritory.getBoats());
    opponentHiddenGrid.init(opponentTerritory.getBoats());
    opponentVisibleGrid.init();

    
    while(!this.model.getCurrentGame().isOver()){
      this.header("PLAYING GAME");
      this.displayGrids(ourPlayerGrid.getGrid(), opponentVisibleGrid.getGrid(), opponentHiddenGrid.getGrid());
      this.println("Entrer la ligne puis la colonne de votre coup:");
      boolean breaker = false;
      while(!breaker)
      {
        synchronized(answerLock)
        {
          try {
            this.answerLock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        String input = this.answer;
        x = 0;
        y = 0;
        if(input.length() != 2 || !input.matches("^[0-9][A-Z]$"))
          this.println("Je n'ai pas compris...");
        else
        {
          x = Character.getNumericValue(input.charAt(0));
          y = (int) input.charAt(1) - 65;
          this.println("x:" + x + " y: " + y);
          boolean success = true;
          try {
            this.dispatchEvent(new ShootEvent(this.ourPlayer, this.opponent, new Point(y,x)));
          } catch (IllegalArgumentException e) {
            success = false;
            this.println(e.getMessage());
          }
          if(success)
            breaker = true;
        }
      }
      opponentHiddenGrid.update(y, x);
      if(opponentHiddenGrid.getGridElement(y, x) == "o")
        opponentVisibleGrid.setGridElement(y, x, "o");
      else
        opponentVisibleGrid.setGridElement(y, x, "x");

      ourPlayerGrid.update(ourPlayerTerritory.getReceivedShots());
    }
    this.displayGrids(ourPlayerGrid.getGrid(), opponentVisibleGrid.getGrid(), opponentHiddenGrid.getGrid());
    this.println(this.model.getCurrentState());
  }

  /**
   * Show end game. The player can either go back to the main menu or quit.
   */
  private void gameOver()
  {
    this.answer = null;
    this.header("GAME OVER");

    this.println(this.model.getCurrentGame().getWinner().getName() + "a gange!");
    this.println("Bravo!");
    this.model.getCurrentGame().getWinner().incrementScore();
    for(Player p : this.model.getCurrentGame().getPlayers())
    {
      this.println(p.getName() + " score:" + p.getScore());
    }

    HashMap<String, String> hm = new HashMap<>();
    hm.put("0", "Revenir au menu principal");
    hm.put("1", "Quitter le jeu");

    this.waitingForAnswer(hm);
    this.print();
    boolean breaker = false;
    String input;
    while(!breaker)
    {
      synchronized (answerLock){
        try {
          this.answerLock.wait();
        } catch (InterruptedException e){
         e.printStackTrace();
        }
        input = this.answer;
        switch (input)
        {
          case "0" : breaker = true; this.dispatchEvent(new MainMenuEvent()); break;
          default : breaker = true; this.dispatchEvent(new ExitEvent()); break;
        }
      }
    }
  }

  public void exitGame()
  {
    this.header("Vous allez quitter le jeu.");
    this.println("Merci d'avoir jouer.");
    this.println("Merci de nous avoir surveille pendant le tp. zoubi.");
    this.println("Au revoir!");
    this.dispatchEvent(new ExitEvent());
  
  }

}