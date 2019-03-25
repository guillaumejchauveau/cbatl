package cbatl.model.events.game;

import cbatl.model.player.Player;
import cbatl.model.territory.Point;
import events.Event;

/**
 * Dispatched by a {@link cbatl.model.game.Game} when it's current player shot another player.
 */
public class CurrentPlayerShotEvent extends Event {
  /**
   * The player that was targeted.
   */
  public final Player targetedPlayer;
  /**
   * The point that was shot.
   */
  public final Point shot;

  /**
   * Initializes the event.
   *
   * @param targetedPlayer The player that was targeted.
   * @param shot           The point that was shot.
   */
  public CurrentPlayerShotEvent(Player targetedPlayer, Point shot) {
    this.targetedPlayer = targetedPlayer;
    this.shot = shot;
  }
}
