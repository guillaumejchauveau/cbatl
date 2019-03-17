package cbatl.model;

import cbatl.model.events.StateChangedEvent;
import cbatl.model.events.game.GameOverEvent;
import cbatl.model.game.Game;
import cbatl.model.player.PlayerManager;
import events.EventTarget;

public class Model extends EventTarget {
  public enum State {
    MAIN_MENU,
    CREATING_GAME,
    PLAYING_GAME,
    GAME_OVER
  }

  private Game currentGame;
  private State currentState;
  public final PlayerManager playerManager;

  public Model(PlayerManager playerManager) {
    this.currentState = State.MAIN_MENU;
    this.playerManager = playerManager;
  }

  public Game getCurrentGame() {
    return this.currentGame;
  }

  public void playGame(Game game) {
    this.currentGame = game;
    this.currentState = State.PLAYING_GAME;
    this.getCurrentGame().addEventListener(GameOverEvent.class, event -> {
      this.currentState = State.GAME_OVER;
      this.dispatchEvent(new StateChangedEvent());
    });
    this.dispatchEvent(new StateChangedEvent());
  }

  public State getCurrentState() {
    return this.currentState;
  }
}
