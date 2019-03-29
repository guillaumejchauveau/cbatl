package cbatl.model.territory;

import cbatl.model.events.territory.TerritoryReceivedShotEvent;
import events.EventTarget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class Territory extends EventTarget {
  public final Integer width;
  public final Integer height;
  private Collection<Boat> boats;
  private List<Point> receivedShots;

  /**
   *
   */
  public Territory(Integer width, Integer height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Dimensions must be positive");
    }
    this.width = width;
    this.height = height;
    this.boats = new ArrayList<>();
    this.receivedShots = new ArrayList<>();
  }

  /**
   * @param point
   * @return
   */
  public Boolean isPointInTerritory(Point point) {
    return 0 <= point.x && point.x < this.width
      && 0 <= point.y && point.y < this.height;
  }

  /**
   * @return
   */
  public Collection<Boat> getBoats() {
    return this.boats;
  }

  /**
   * @return
   */
  public List<Point> getReceivedShots() {
    return this.receivedShots;
  }

  public void generateFleet() {

  }

  /**
   * @param boat
   */
  public void addBoat(Boat boat) {
    if (
      !this.isPointInTerritory(boat.getHead()) ||
        !this.isPointInTerritory(boat.translateSectionToPoint(boat.getLength() - 1))
    ) {
      throw new IllegalArgumentException("Boat is not in territory");
    }
    this.boats.add(boat);
  }

  /**
   * @param shot
   * @return
   */
  public Boat receiveShot(Point shot) {
    if (!this.isPointInTerritory(shot)) {
      throw new IllegalArgumentException("Shot is not in territory");
    }
    Boat shotBoat = null;

    for (Boat boat : this.getBoats()) {
      for (Point sectionPoint : boat.getSectionsPoints()) {
        if (sectionPoint.equals(shot)) {
          boat.addShotSection(boat.translatePointToSection(sectionPoint));
          shotBoat = boat;
        }
      }
    }
    this.receivedShots.add(shot);
    this.dispatchEvent(new TerritoryReceivedShotEvent(shot, shotBoat));
    return shotBoat;
  }
}
