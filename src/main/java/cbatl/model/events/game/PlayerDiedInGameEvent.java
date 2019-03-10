package cbatl.model.events.game;

import cbatl.model.player.Player;
import events.Event;

/**
 * Dispatched by a {@link cbatl.model.game.Game} when a player lost his last boat.
 */
public class PlayerDiedInGameEvent extends Event {
  /**
   * The player who died.
   */
  public final Player player;

  /**
   * Initializes the event with a player.
   *
   * @param player The player who died.
   */
  public PlayerDiedInGameEvent(Player player) {
    this.player = player;
  }
}
