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
  /**
   *
   */
  public final PlayerManager playerManager;

  public Model(PlayerManager playerManager) {
    this.setCurrentState(State.MAIN_MENU);
    this.playerManager = playerManager;
  }

  public Game getCurrentGame() {
    return this.currentGame;
  }

  public void playGame(Game game) {
    this.currentGame = game;

    this.getCurrentGame().addEventListener(GameOverEvent.class, event -> {
      this.setCurrentState(State.GAME_OVER);
    });

    this.setCurrentState(State.PLAYING_GAME);
  }

  public State getCurrentState() {
    return this.currentState;
  }

  public void setCurrentState(State state) {
    this.currentState = state;
    this.dispatchEvent(new StateChangedEvent());
  }
}
