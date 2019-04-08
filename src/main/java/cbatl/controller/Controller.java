package cbatl.controller;

import cbatl.model.Model;
import cbatl.model.ModelException;
import cbatl.model.game.Game;
import cbatl.model.player.Player;
import cbatl.model.player.PlayerManager;
import cbatl.model.player.RandomPlayer;
import cbatl.model.territory.Territory;
import cbatl.view.View;
import cbatl.view.events.CreateGameMenuEvent;
import cbatl.view.events.ExitEvent;
import cbatl.view.events.MainMenuEvent;
import cbatl.view.events.PlayGameEvent;
import cbatl.view.events.ShootEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The bridge between the views and the model.
 */
public class Controller {
  private File playerFile;
  private PlayerManager playerManager;
  private Model model;

  /**
   * Initializes the controller with a file to save players.
   *
   * @param playerFile The file used to save players
   * @param cheat      Cheat mode activation
   */
  public Controller(File playerFile, Boolean cheat) {
    this.playerFile = playerFile;
    try {
      this.playerFile.createNewFile();
      this.playerManager = PlayerManager.createFromFile(new FileReader(this.playerFile));
    } catch (IOException | IllegalStateException | IllegalArgumentException | ModelException e) {
      System.out.println("Attention: la tentative de chargement du fichier de sauvegarde a " +
        "echouee.");
      this.playerManager = new PlayerManager();
    }
    this.model = new Model(this.playerManager, cheat);
  }

  /**
   * Registers a view and attach event listeners.
   *
   * @param view The view to attach
   */
  public void attachView(View view) {
    // Game creation events.
    view.addEventListener(CreateGameMenuEvent.class, event -> {
      if (this.model.getCurrentState() == Model.State.MAIN_MENU) {
        this.model.setCurrentState(Model.State.CREATING_GAME);
      }
    });
    view.addEventListener(PlayGameEvent.class, event -> {
      if (this.model.getCurrentState() == Model.State.CREATING_GAME) {
        Game game = new Game();
        for (Player player : event.players) {
          Territory territory = new Territory();
          try {
            territory.generateFleet();
          } catch (ModelException e) {
            throw new RuntimeException(e);
          }
          try {
            game.addPlayer(player, territory);
          } catch (ModelException e) {
            throw new IllegalArgumentException(e);
          }
          try {
            if (!this.playerManager.hasPlayer(player)) {
              this.playerManager.registerPlayer(player);
            }
          } catch (ModelException e) {
            throw new RuntimeException(e);
          }
        }

        if (game.getPlayerCount() < 2) {
          Territory territory = new Territory();
          try {
            territory.generateFleet();
            game.addPlayer(new RandomPlayer(), territory);
          } catch (ModelException e) {
            throw new RuntimeException(e);
          }
        }
        this.model.playGame(game);
      }
    });

    // In-game events.
    view.addEventListener(ShootEvent.class, event -> {
      if (this.model.getCurrentState() == Model.State.PLAYING_GAME) {
        try {
          this.model.getCurrentGame().shoot(event.targetedPlayer, event.shot);
        } catch (ModelException e) {
          throw new IllegalArgumentException(e);
        }
      }
    });

    // Other...
    view.addEventListener(MainMenuEvent.class, event -> {
      this.model.setCurrentState(Model.State.MAIN_MENU);
    });
    view.addEventListener(ExitEvent.class, event -> {
      try {
        playerManager.saveToFile(new FileWriter(this.playerFile));
      } catch (IOException | IllegalStateException | IllegalArgumentException e) {
        System.out.println("Attention: la tentative de sauvegarde a echouee.");
      }
      System.exit(0);
    });
    view.attachModel(this.model);
  }
}
