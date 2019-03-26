package cbatl.view.terminalview;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import cbatl.model.player.Player;
import cbatl.view.events.*;
import cbatl.App;
import cbatl.model.*;
import cbatl.model.events.player.PlayerListUpdatedEvent;
import cbatl.view.View;

  /**
  * Manages the command line Interface
  */
public class TerminalInterface extends View{

  private final PrintStream out;
  private final InputStream in;
  private final Scanner scanner;
  private Model model;

  public TerminalInterface() {
      this.out = System.out;
      this.in = System.in;
      this.scanner = new Scanner(this.in);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void attachModel(Model model){this.model = model;}

  /**
   * Clear the Terminal.
   */
    public void clear() {
        this.out.print("\033[H\033[2J");
        this.out.flush();
  }

  /**
   * Prints in the terminal.
   * @param text object to print
   */
    public void print(Object text) {
        this.out.print(text);
  }

/**
   * Prints in the terminal and then \n.
   * @param text object to print
   */
    public void println(Object text) {
        this.out.println(text);
  }

/**
 * Prints a header for the different menus.
 * @param head title of the menu to print
 */
  public void header(String head)
  {
    this.clear();
    this.println("    --BATTLESHIP--" + System.lineSeparator());
    this.println(head+ System.lineSeparator()+ System.lineSeparator());
  }

  /**
   * Prints the choices the player has to do and sends back its answer. Used only for the menus.
   * @param hm Hash map containing different choices. 
   * @return the player's answer via Scanner
   */
  public String waitingForAnswer(HashMap<String, String> hm)
  {
    ArrayList<String> ls = new ArrayList<>();
    String input = "";
    for(int i = 0; i < hm.size(); i++)
    {
        this.println("  " + i +". " + hm.get("" +i));
        ls.add("" + i);
    }

    while(true)
    {
        this.print(">");
        input = this.scanner.nextLine();
        if(ls.contains(input)) 
            break;
        else 
            this.println("Entree non conforme. Reessayz");
        
    }
    return input;
  }

  /**
   * Prints Players and their score on screen.
   */
  public void freePlayers()
  {
    Collection<Player> lp = this.model.playerManager.getPlayers();
    for(Player p : lp)
    {
      this.println(p.getName() + " | score: " + p.getScore());
    }
  }

  /**
   * Displays BATTLESHIP's Main Menu. From here, the player can leave or go to BATTLESHIP's game menu.
   */
  public void mainMenu()
  {
    this.header("Menu Principal");

    this.println("Joueurs disponibles:");
    this.freePlayers();
    HashMap<String,String> hm = new HashMap<>();
    hm.put("0", "Créer une partie");
    hm.put("1", "Quitter");

    String input = this.waitingForAnswer(hm);

    switch (input) {
        case "0": this.dispatchEvent(new CreateGameMenuEvent());
        case "1": this.dispatchEvent(new ExitEvent());
      }
  }

  /**
   * Displays BATTLESHIP's game menu. Allows the player to create a new payer or to play a game with a chososen player.
   */
  public void createGameMenu()
  {
    this.header("Menu du jeu");
    this.println("Joueurs disponibles:");
    this.freePlayers();
    
    HashMap<String, String> hm = new HashMap<>();
    hm.put("0", "Incarner un joueur present.");
    hm.put("1", "Creer un nouveau joueur.");

    String input = this.waitingForAnswer(hm);
    switch (input){
      case "0" : break;
      case "1" : this.createPlayer(); break;
    }

    this.println("Entrer le nom de joueur que vous voulez incarner:");
    this.print(">");
    String s = "";
    boolean stop = false;
    while(!stop)
    {
      s = this.scanner.nextLine();
      if(s.matches("*[a-zA-Z_0-9]"))
      {
        if(this.model.playerManager.hasPlayer(s))
        {
          this.model.playerManager.registerPlayer(new Player(s));
          stop = true;
        }
      }
      else
      {
        this.println("Le nom du joueur n'existe pas.");
        this.print(">");
      }
    }
    ArrayList<Player> player = new ArrayList<>();
    player.add(this.model.playerManager.getPlayer(s));
    this.dispatchEvent(new PlayGameEvent(player));
  }

  /**
   * Creates a player and send player to model
   */
  public void createPlayer()
  {
    this.header("Creer un joueur");

    HashMap<String, String> hm = new HashMap<>();
    hm.put("0", "Creer un nouveau joueur.");
    hm.put("1", "Retourner au menu principal.");
    hm.put("2", "Retourner au menu de jeu.");
    hm.put("3", "Quitter le jeu.");

    String input = "";
    String newPlayerName = "";
    this.print("Nom du nouveau Joueur: >");
    while(true){
      while(true)
      {
          newPlayerName = this.scanner.nextLine();
          if(newPlayerName.matches("*[a-zA-Z_0-9]"))
          {
              if(!this.model.playerManager.hasPlayer(newPlayerName))
              {
                this.model.playerManager.registerPlayer(new Player(newPlayerName));
                break;
              }
          }
          else 
              this.println("Le nom du joueur doit être numerico alphabetique");
          this.print(">");
      }
      this.println("");     
      input = this.waitingForAnswer(hm);
      if(input != "0")
        break;
    }
    
    switch (input){
      case "1" : this.dispatchEvent(new MainMenuEvent());
      case "2" : this.dispatchEvent(new CreateGameMenuEvent());
      case "3" : this.dispatchEvent(new ExitEvent());
    }

  }

  public void gameOver()
  {
    this.header("GAME OVER");

    this.println(this.model.getCurrentGame().getWinner().getName() + "a gangé!");
    this.println("Bravo!");

    HashMap<String, String> hm = new HashMap<>();
    hm.put("0", "Revenir au menu principal");
    hm.put("1", "Quitter le jeu");

    String input = this.waitingForAnswer(hm);
    switch (input)
    {
      case "0" : this.dispatchEvent(new MainMenuEvent());
      case "1" : this.dispatchEvent(new ExitEvent());
    }
  }

}