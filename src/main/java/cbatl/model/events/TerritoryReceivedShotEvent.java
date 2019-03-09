package cbatl.model.events;

import cbatl.model.Boat;
import cbatl.model.Point;
import cbatl.model.Territory;
import events.Event;

public class TerritoryReceivedShotEvent extends Event<Territory> {
  public final Point shot;
  public final Boat shotBoat;

  public TerritoryReceivedShotEvent(Territory target, Point shot, Boat shotBoat) {
    super(target);
    this.shot = shot;
    this.shotBoat = shotBoat;
  }
}
