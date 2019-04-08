package cbatl.view.terminalview;

import cbatl.model.Model;
import cbatl.model.ModelException;
import cbatl.model.events.StateChangedEvent;
import cbatl.model.events.game.CurrentPlayerChangedEvent;
import cbatl.model.game.Game;
import cbatl.model.player.Player;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;
import cbatl.view.View;
import cbatl.view.events.CreateGameMenuEvent;
import cbatl.view.events.ExitEvent;
import cbatl.view.events.MainMenuEvent;
import cbatl.view.events.PlayGameEvent;
import cbatl.view.events.ShootEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A view using standard input and output.
 */
public class TerminalView extends View {
  private final static String spacer = "  ";
  private Model model;
  private Map<Player, TerritoryGrid> grids;

  public TerminalView() {
    Thread thread = new Thread(() -> {
      Scanner scanner = new Scanner(System.in);
      while (!Thread.interrupted()) {
        String input = scanner.nextLine();
        parseInput(input);
      }
    });
    thread.start();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void attachModel(Model model) {
    this.model = model;
    this.model.addEventListener(StateChangedEvent.class, event -> this.updateState());
    this.updateState();
  }

  /**
   * State change callback. Delegates to specialized methods for each state.
   */
  private void updateState() {
    try {

      switch (this.model.getCurrentState()) {
        case MAIN_MENU:
          printMainMenu();
          break;
        case CREATING_GAME:
          printCreateGameMenu();
          break;
        case PLAYING_GAME:
          Game currentGame = this.model.getCurrentGame();

          this.grids = new HashMap<>();
          try {
            for (Player player : currentGame.getPlayers()) {
              Territory territory = currentGame.getPlayerTerritory(player);
              this.grids.put(player, new TerritoryGrid(territory));
            }
          } catch (ModelException e) {
            throw new RuntimeException(e);
          }

          currentGame.addEventListener(CurrentPlayerChangedEvent.class, event -> {
            try {
              printPlayingGame();
            } catch (ModelException e) {
              throw new RuntimeException(e);
            }
          });
          printPlayingGame();
          break;
        case GAME_OVER:
          printGameOver();
          break;
      }
    } catch (ModelException e) {
      throw new RuntimeException(e);
    }
  }

  private void clear() {
    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
      return;
    }
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /**
   * Standard input callback. Delegates to specialized methods for each state or takes simple
   * decisions.
   *
   * @param input The data from the standard input
   */
  private void parseInput(String input) {
    switch (input) {
      case "x":
        this.dispatchEvent(new ExitEvent());
        break;
      case "m":
        this.dispatchEvent(new MainMenuEvent());
        break;
      default:
        switch (this.model.getCurrentState()) {
          case MAIN_MENU:
            this.dispatchEvent(new CreateGameMenuEvent());
            break;
          case CREATING_GAME:
            this.handleCreateGameMenu(input);
            break;
          case PLAYING_GAME:
            this.handlePlayingGame(input);
            break;
          case GAME_OVER:
            this.dispatchEvent(new MainMenuEvent());
            break;
        }
        break;
    }
  }

  private void printMainMenu() {
    this.clear();
    System.out.println("MENU PRINCIPAL");
    System.out.println("--------------");
    System.out.println();

    System.out.println("Tableau des scores :");
    for (Player player : this.model.playerManager.getPlayers()) {
      System.out.println(player.getName() + " : " + player.getScore());
    }
    System.out.println();
    System.out.println("Vous pouvez a tout moment quitter avec 'x' ou revenir au menu principal " +
      "avec 'm'.");
    System.out.println("Faites 'entree' pour commencer une partie.");
  }

  private void printCreateGameMenu() {
    this.clear();
    System.out.println("CREATION PARTIE");
    System.out.println("---------------");
    System.out.println();

    System.out.println("Entrez le nom des joueurs pour la partie (alice bob jane...)");
    System.out.println("Un bot sera ajoute si il n'y a qu'un seul joueur");
  }

  private void handleCreateGameMenu(String input) {
    ArrayList<Player> players = new ArrayList<>();
    for (String playerName : input.split(" ")) {
      if (playerName.length() == 0) {
        continue;
      }
      Player player;
      if (this.model.playerManager.hasPlayer(playerName)) {
        try {
          player = this.model.playerManager.getPlayer(playerName);
        } catch (ModelException e) {
          throw new RuntimeException(e);
        }
      } else {
        try {
          player = new Player(playerName);
        } catch (ModelException e) {
          System.out.println("Le nom de joueur '" + playerName + "' est incorrect");
          return;
        }
      }
      players.add(player);
    }
    try {
      this.dispatchEvent(new PlayGameEvent(players));
    } catch (IllegalArgumentException e) {
      System.out.println("Les joueurs indiques sont incorrects");
    }
  }

  /**
   * Prints the current game's grids.
   */
  private void printGrids() throws ModelException {
    int maxGridHeight = 0;

    Game currentGame = this.model.getCurrentGame();
    for (Player player : currentGame.getPlayers()) {
      TerritoryGrid grid = this.grids.get(player);
      if (grid.getHeight() > maxGridHeight) {
        maxGridHeight = grid.getHeight();
      }

      System.out.print("  " + spacer);

      for (char xChar = 'A'; xChar < 'A' + grid.getWidth(); xChar++) {
        System.out.print(xChar + spacer);
      }
      System.out.print(spacer);
    }
    System.out.println();

    for (int y = 0; y < maxGridHeight; y++) {
      // Display current player grid.
      this.printGridFrame(currentGame.getCurrentPlayer(), y, true);

      // Display other players grid.
      for (Player player : currentGame.getPlayers()) {
        if (player == currentGame.getCurrentPlayer()) {
          continue;
        }
        this.printGridFrame(player, y, this.model.cheat);
      }
      System.out.println();
    }

    System.out.println();

    this.printGridName(currentGame.getCurrentPlayer());
    for (Player player : currentGame.getPlayers()) {
      if (player == currentGame.getCurrentPlayer()) {
        continue;
      }
      this.printGridName(player);
    }
    System.out.println();
    System.out.println();
    System.out.println(
      "Legende: '" + TerritoryGrid.WATER + "': vide, '"
        + TerritoryGrid.SHOT_WATER + "': dans l'eau, '"
        + TerritoryGrid.BOAT + "': bateau, '"
        + TerritoryGrid.SHOT_BOAT + "': touche, '"
        + TerritoryGrid.SUNK_BOAT + "': coule"
    );
  }

  /**
   * Prints an horizontal line of a specific grid.
   *
   * @param player  The player associated with the grid
   * @param y       The line number
   * @param visible Defines if all the boats should be displayed
   */
  private void printGridFrame(Player player, int y, boolean visible) throws ModelException {
    TerritoryGrid grid = this.grids.get(player);
    boolean dead = !this.model.getCurrentGame().isPlayerAlive(player);
    // Enforce 2 characters wide line number.
    if (y + 1 < 10) {
      System.out.print(" " + (y + 1));
    } else {
      System.out.print(y + 1);
    }
    System.out.print(spacer);

    for (int x = 0; x < grid.getWidth(); x++) {
      if (dead && (x == y || x + y == grid.getWidth() - 1)) {
        System.out.print("@" + spacer);
      } else if (visible) {
        System.out.print(grid.visibleGrid[y][x] + spacer);
      } else {
        System.out.print(grid.hiddenGrid[y][x] + spacer);
      }
    }
    System.out.print(spacer);
  }

  /**
   * Prints the name corresponding to a grid.
   *
   * @param player The player associated with the grid
   */
  private void printGridName(Player player) throws ModelException {
    TerritoryGrid grid = this.grids.get(player);
    int gridTotalWidth = 2 + spacer.length() + (1 + spacer.length()) * grid.getWidth();
    String gridName = player.getName();
    if (!this.model.getCurrentGame().isPlayerAlive(player)) {
      gridName = "[MORT] " + gridName;
    } else {
      gridName = "[" + this.model.getCurrentGame().getPlayers().indexOf(player) + "] " + gridName;
    }

    int gridNamePadding = 4;
    if (gridName.length() > (gridTotalWidth - gridNamePadding)) {
      gridName = gridName.substring(0, gridTotalWidth - (gridNamePadding + 3)) + "...";
    }
    int gridNameSpacer = (gridTotalWidth - gridName.length()) / 2;
    for (int x = 0; x < gridTotalWidth; x++) {
      if (x < gridNameSpacer) {
        System.out.print(" ");
        continue;
      }
      if ((x - gridNameSpacer) < gridName.length()) {
        System.out.print(gridName.charAt(x - gridNameSpacer));
      } else {
        System.out.print(" ");
      }
    }
    System.out.print(spacer);
  }

  private void printPlayingGame() throws ModelException {
    this.clear();
    this.printGrids();
    System.out.println();
    System.out.println("Au tour de " + this.model.getCurrentGame().getCurrentPlayer().getName());
    System.out.println();
    if (this.model.getCurrentGame().getAlivePlayers().size() == 2) {
      System.out.println("Entrez les coordonnees pour tirer (A1) :");
    } else {
      System.out.println(
        "Entrez le numero ou le nom d'un adversaire ainsi que les coordonnees pour tirer (bob A1) :"
      );
    }
  }

  private void handlePlayingGame(String input) {
    Game currentGame = this.model.getCurrentGame();
    String[] inputs = input.trim().split(" ");
    if (inputs.length == 0) {
      return;
    }
    String point;
    Player targetedPlayer = null;

    // Determines the targeted player.
    // If there are only 2 remaining players, chooses automatically.
    if (currentGame.getAlivePlayers().size() == 2) {
      if (inputs.length > 2) {
        System.out.println("Trop de choses donnees");
        return;
      }
      for (Player player : currentGame.getAlivePlayers()) {
        if (player != currentGame.getCurrentPlayer()) {
          targetedPlayer = player;
        }
      }
      point = inputs[inputs.length == 1 ? 0 : 1];
    } else { // If there are more than 2 remaining players, parses the provided input.
      if (inputs.length != 2) {
        System.out.println("Il faut indiquer deux choses");
        return;
      }
      try {
        try {
          targetedPlayer = currentGame.getPlayers().get(Integer.parseInt(inputs[0]));
        } catch (NumberFormatException e) {
          targetedPlayer = currentGame.getPlayer(inputs[0]);
        }
      } catch (Exception e) {
        System.out.println("Ce joueur n'existe pas");
        return;
      }

      if (targetedPlayer == currentGame.getCurrentPlayer()) {
        System.out.println("Ne vous tirez pas dessus voyons");
        return;
      }
      try {
        if (!currentGame.isPlayerAlive(targetedPlayer)) {
          System.out.println("Ce joueur est mort");
          return;
        }
      } catch (ModelException e) {
        throw new RuntimeException(e);
      }
      point = inputs[1];
    }

    // Determines the shot point.
    point = point.toUpperCase();
    try {
      int x;
      int y;
      // Tries letter first.
      if (point.charAt(0) >= 'A') {
        x = point.charAt(0) - 'A';
        y = Integer.parseInt(point.substring(1)) - 1;
      } else { // Tries letter second.
        x = point.charAt(point.length() - 1) - 'A';
        y = Integer.parseInt(point.substring(0, point.length() - 1)) - 1;
      }
      this.dispatchEvent(new ShootEvent(
        targetedPlayer,
        new Point(x, y)
      ));
    } catch (Exception e) {
      System.out.println("Coordonnees incorrectes.");
    }
  }

  private void printGameOver() throws ModelException {
    this.clear();
    this.printGrids();

    System.out.println();
    System.out.println("Victoire de " + this.model.getCurrentGame().getWinner().getName() + " !");
    System.out.println("Faites 'entree' pour retourner au menu principal.");
  }
}
