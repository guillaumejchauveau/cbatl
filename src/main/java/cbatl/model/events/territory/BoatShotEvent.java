package cbatl.model.events.territory;

import events.Event;

/**
 * Dispatched by a {@link cbatl.model.territory.Boat} when one of it's sections is shot.
 */
public class BoatShotEvent extends Event {
  /**
   * The section of the boat that was shot.
   */
  public final Integer section;

  /**
   * Initializes the event.
   *
   * @param section The shot section of the boat
   */
  public BoatShotEvent(Integer section) {
    this.section = section;
  }
}
