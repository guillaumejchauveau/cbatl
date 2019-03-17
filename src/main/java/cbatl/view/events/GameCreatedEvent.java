package cbatl.view.events;

import cbatl.model.game.Game;
import events.Event;

public class GameCreatedEvent extends Event {
  public final Game game;

  public GameCreatedEvent(Game game) {
    this.game = game;
  }
}
