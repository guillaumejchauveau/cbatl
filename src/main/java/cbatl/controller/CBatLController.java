package cbatl.controller;

import cbatl.model.Model;
import cbatl.model.player.PlayerManager;
import cbatl.view.View;
import cbatl.view.events.CreateGameEvent;
import cbatl.view.events.GameCreatedEvent;
import cbatl.view.events.PlayerPlayedEvent;
import cbatl.view.events.ReturnToMainMenuEvent;

public class CBatLController {
  private final Model model;

  public CBatLController(PlayerManager playerManager) {
    this.model = new Model(playerManager);
  }

  public void attachView(View view) {
    view.addEventListener(GameCreatedEvent.class, event -> {
      if (this.model.getCurrentState() == Model.State.CREATING_GAME) {
        this.model.playGame(event.game);
      }
    });
    view.addEventListener(CreateGameEvent.class, event -> {
      if (this.model.getCurrentState() == Model.State.MAIN_MENU) {
        this.model.setCurrentState(Model.State.CREATING_GAME);
      }
    });
    view.addEventListener(PlayerPlayedEvent.class, event -> {
      if (this.model.getCurrentState() == Model.State.PLAYING_GAME &&
        event.player == this.model.getCurrentGame().getCurrentPlayer()) {
        this.model.getCurrentGame().shoot(event.targetedPlayer, event.shot);
      }
    });
    view.addEventListener(ReturnToMainMenuEvent.class, event -> {
      this.model.setCurrentState(Model.State.MAIN_MENU);
    });
  }
}
