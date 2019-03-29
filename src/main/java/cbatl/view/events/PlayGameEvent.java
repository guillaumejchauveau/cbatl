package cbatl.view.events;

import cbatl.model.player.Player;
import events.Event;
import java.util.Collection;

/**
 * Dispatched when the user has created a game.
 */
public class PlayGameEvent extends Event {
  public final Collection<Player> players;

  public PlayGameEvent(Collection<Player> players) {
    this.players = players;
  }
}
