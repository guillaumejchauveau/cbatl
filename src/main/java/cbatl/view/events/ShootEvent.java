package cbatl.view.events;

import cbatl.model.player.Player;
import cbatl.model.territory.Point;
import events.Event;

/**
 * Dispatched when the user wants to play.
 */
public class ShootEvent extends Event {
  public final Player player;
  public final Player targetedPlayer;
  public final Point shot;

  public ShootEvent(Player player, Player targetedPlayer, Point shot) {
    this.player = player;
    this.targetedPlayer = targetedPlayer;
    this.shot = shot;
  }
}
