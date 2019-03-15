package cbatl.model.events.territory;

import cbatl.model.territory.Boat;
import cbatl.model.territory.Point;
import events.Event;

/**
 * Dispatched by a {@link cbatl.model.territory.Territory} when shot, regardless if a boat was hit.
 */
public class TerritoryReceivedShotEvent extends Event {
  /**
   * The point of the territory where the shot struck.
   */
  public final Point shot;
  /**
   * The boat shot if there is one. Null otherwise.
   */
  public final Boat shotBoat;

  /**
   * Initializes the event only with the point of the shot.
   *
   * @param shot The point of the territory where the shot struck.
   */
  public TerritoryReceivedShotEvent(Point shot) {
    this(shot, null);
  }

  /**
   * Initializes the event with the point and the boat hit by the shot.
   *
   * @param shot     The point of the territory where the shot struck.
   * @param shotBoat The boat shot.
   */
  public TerritoryReceivedShotEvent(Point shot, Boat shotBoat) {
    this.shot = shot;
    this.shotBoat = shotBoat;
  }
}
