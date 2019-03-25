package cbatl.model.view.terminalview;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import cbatl.model.view.*;

public class TerminalInterface{

    private final PrintStream out;
    private final InputStream in;
    private final Scanner scanner;

    /**
    * Manages the command line Interface
    */
    public TerminalInterface() {
        this.out = System.out;
        this.in = System.in;
        this.scanner = new Scanner(this.in);
  }

  /**
   * Clear the Terminal
   */
    public void clear() {
        this.out.print("\033[H\033[2J");
        this.out.flush();
  }

  /**
   * Print in the terminal
   * @param text object to print
   */
    public void print(Object text) {
        this.out.print(text);
  }

/**
   * Print in the terminal and then \n
   * @param text object to print
   */
    public void println(Object text) {
        this.out.println(text);
  }

/**
 * Prints a header
 * @param head the Header to print
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
   * Displays BATTLESHIP's Main Menu
   */
  public void mainMenu()
  {
    this.header("Menu Principal");

    this.println("Joueurs disponibles:");
    
    HashMap<String,String> hm = new HashMap<>();
    hm.put("0", "Créer une partie");
    hm.put("1", "Quitter");

    String input = this.waitingForAnswer(hm);

    switch (input) {
        case "0":
          this.dispatch(new CreateGameMenuEvent());
        case "1":
            this.print("Vous allez quitter le jeu");
            System.exit(0);
      }
  }

  /**
   * Displays BATTLESHIP's game menu
   */
  public void createGameMenu()
  {
    this.header("Menu du jeu");
    this.println("Joueurs disponibles:");

    //Fill

    this.println("");
  }

  /**
   * Creates a player and send event to model
   */
  public void createPlayer()
  {
    this.header("Creer un joueur");

    this.print("Nom du nouveau Joueur: >");
    String newPLayerName = "";
    while(true)
    {
        newPLayerName = this.scanner.nextLine();
        if(newPLayerName.matches("*[a-zA-Z_0-9]"))
            break;
        else 
            this.println("Le nom du joueur doit être numerico alphabetique");
        this.println(">");
    }
    
    this.dispatch(new CreatePlayerEvent());
    // Fill

  }

}